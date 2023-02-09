package uk.co.thirdthing.content

import com.raquo.airstream.state.Var
import org.scalajs.dom.html
import com.raquo.airstream.web.AjaxEventStream
import com.raquo.airstream.web.AjaxEventStream.{AjaxStreamError, post}
import com.raquo.laminar.api.L.*
import org.scalajs.dom

import scala.io.Codec
import com.raquo.laminar.builders.HtmlTag
import com.raquo.laminar.nodes.ReactiveHtmlElement
import uk.co.thirdthing.content.*
import cats.syntax.all.*
import io.circe.syntax.*
import org.scalajs.dom.ext.Ajax
import com.raquo.airstream.ownership.Owner
import org.scalajs.dom.XMLHttpRequest

object Contact extends Content[dom.html.Div] with Owner:
  private val stateVar               = Var(ContactFormState.empty)
  private val formSubmissionStateVar = Var(FormSubmissionState.NotSubmitted)
  private val nameWriter             = stateVar.updater[String]((state, name) => state.copy(name = Some(name)))
  private val emailWriter            = stateVar.updater[String]((state, email) => state.copy(email = Some(email)))
  private val messageWriter          = stateVar.updater[String]((state, message) => state.copy(message = Some(message)))
  private val formSubmissionObserver = Observer[XMLHttpRequest] { req =>
    if (req.status == 200) formSubmissionStateVar.set(FormSubmissionState.SubmittedSuccessfully)
    else formSubmissionStateVar.set(FormSubmissionState.SubmittedWithError)
  }

  private val submitter = Observer[ContactFormState] { state =>
    state.validate.fold(
      errs => stateVar.update(_.copy(validationErrors = errs.toList)),
      validatedState =>
        AjaxEventStream
          .post(
            url = "https://formkeep.com/f/9230a08de79dsx",
            data = Ajax.InputData.str2ajax(validatedState.formPayload),
            headers = Map("Accept" -> "application/javascript", "Content-Type" -> "application/x-www-form-urlencoded")
          )
          .completeEvents
          .addObserver(formSubmissionObserver)(this)
    )
  }

  private def renderInputField(associatedValidationError: Option[ValidationError])(content: Modifier[HtmlElement]*): HtmlElement =
    val errorOpt = stateVar.signal.map(state => associatedValidationError.flatMap(err => state.validationErrors.find(_ == err)))
    div(
      cls.toggle("x-hasError") <-- errorOpt.map(_.isDefined),
      content,
      child.maybe <-- errorOpt.map(_.map(err => div(cls("invalid-feedback d-block"), err.message)))
    )

  private val formElem = form(
    onSubmit.preventDefault.mapTo(stateVar.now()) --> submitter,
    idAttr  := "contactForm",
    encType := "multipart/form-data",
    method  := "POST",
    renderInputField(ValidationError.Name.some)(
      label(forId := "name", cls := "form-label", "Name"),
      input(
        cls         := "form-control",
        idAttr      := "name",
        typ         := "text",
        placeholder := "Enter your name...",
        value <-- stateVar.signal.map(_.name.getOrElse("")),
        onInput.mapToValue --> nameWriter,
        disabled <-- formSubmissionStateVar.signal.map(_ == FormSubmissionState.SubmittedSuccessfully)
      )
    ),
    renderInputField(ValidationError.Email.some)(
      label(forId := "email", cls := "form-label", "Email Address"),
      input(
        cls         := "form-control",
        idAttr      := "email",
        typ         := "text",
        placeholder := "name@example.com",
        value <-- stateVar.signal.map(_.email.getOrElse("")),
        onInput.mapToValue --> emailWriter,
        disabled <-- formSubmissionStateVar.signal.map(_ == FormSubmissionState.SubmittedSuccessfully)
      )
    ),
    renderInputField(ValidationError.Message.some)(
      label(forId := "message", cls := "form-label", "Message"),
      textArea(
        cls         := "form-control",
        idAttr      := "message",
        typ         := "text",
        placeholder := "Enter your message here...",
        styleAttr   := "height: 10rem",
        value <-- stateVar.signal.map(_.message.getOrElse("")),
        onInput.mapToValue --> messageWriter,
        disabled <-- formSubmissionStateVar.signal.map(_ == FormSubmissionState.SubmittedSuccessfully)
      )
    ),
    div(cls := "d-none", idAttr := "submitSuccessMessage", div(cls := "text-center mb-3", div(cls := "fw-bolder", "Form submission successful"))),
    div(cls := "d-none", idAttr := "submitErrorMessage", div(cls := "text-center text danger mb-3", "Error submitting form")),
    button(
      cls    := "btn btn-primary btn-xl",
      idAttr := "submitButton",
      typ    := "submit",
      "Send",
      disabled <-- formSubmissionStateVar.signal.map(_ == FormSubmissionState.SubmittedSuccessfully)
    )
  )

  override def render = div(
    cls := "row justify-content-center",
    child <-- formSubmissionStateVar.signal.map {
      case FormSubmissionState.NotSubmitted => div(cls := "col-lg-8 col-xl-7", formElem)
      case FormSubmissionState.SubmittedSuccessfully =>
        div(
          cls := "col-lg-6",
          p(cls := "lead", "Thank you. Your message has been submitted")
        )
      case FormSubmissionState.SubmittedWithError =>
        div(
          cls := "col-lg-6",
          p(cls := "lead", "There has been an error submitting the form. Please try again.")
        )

    }
  )
