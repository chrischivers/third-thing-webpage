package uk.co.thirdthing.content

import cats.data.{Validated, ValidatedNel}
import cats.syntax.all._
import com.raquo.airstream.state.Var
import org.scalajs.dom.html
import com.raquo.airstream.web.AjaxEventStream
import com.raquo.airstream.web.AjaxEventStream.AjaxStreamError
import com.raquo.laminar.api.L.*
import org.scalajs.dom

import scala.io.Codec
import com.raquo.laminar.builders.HtmlTag
import com.raquo.laminar.nodes.ReactiveHtmlElement
import monix.newtypes.NewtypeWrapped
import uk.co.thirdthing.content.ValidatedContactForm._
import io.circe.generic.semiauto.*
import io.circe.Encoder
import monix.newtypes.integrations.DerivedCirceCodec

final case class Section(name: String, content: Content[dom.html.Div])
final case class PortfolioItem(title: String, description: String, imageSrc: String, githubLink: String)

trait Content[T <: dom.html.Element]:
  def render: ReactiveHtmlElement[T]

case class ValidatedContactForm(name: Name, email: Email, message: Message) {
  def formPayload: String = s"name=${name.value}&email=${email.value}&message=${message.value}"
}

object ValidatedContactForm:

  implicit val encoder: Encoder[ValidatedContactForm] = deriveEncoder

  type Name = Name.Type
  object Name extends NewtypeWrapped[String] with DerivedCirceCodec

  type Email = Email.Type
  object Email extends NewtypeWrapped[String] with DerivedCirceCodec

  type Message = Message.Type
  object Message extends NewtypeWrapped[String] with DerivedCirceCodec

enum ValidationError(val message: String):
  case Name    extends ValidationError("Name must not be empty")
  case Email   extends ValidationError("Email must be valid and must not be empty")
  case Message extends ValidationError("Message field must not be empty")

enum FormSubmissionState:
  case NotSubmitted          extends FormSubmissionState
  case SubmittedSuccessfully extends FormSubmissionState
  case SubmittedWithError    extends FormSubmissionState

case class ContactFormState(
  name: Option[String],
  email: Option[String],
  message: Option[String],
  validationErrors: List[ValidationError]
):
  private def validateName: ValidatedNel[ValidationError, Name] =
    name.fold(ValidationError.Name.invalidNel)(n => Validated.condNel(n.nonEmpty, Name(n), ValidationError.Name))
  private def validateEmail: ValidatedNel[ValidationError, Email] =
    email.fold(ValidationError.Email.invalidNel)(e => Validated.condNel(".+\\@.+\\..+".r.matches(e), Email(e), ValidationError.Email))
  private def validateMessage: ValidatedNel[ValidationError, Message] =
    message.fold(ValidationError.Message.invalidNel)(m => Validated.condNel(m.nonEmpty, Message(m), ValidationError.Message))

  def validate: ValidatedNel[ValidationError, ValidatedContactForm] =
    (validateName, validateEmail, validateMessage).mapN((n, e, m) => ValidatedContactForm(n, e, m))

object ContactFormState:
  val empty = ContactFormState(None, None, None, List.empty)
