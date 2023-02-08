package uk.co.thirdthing.content

import com.raquo.airstream.state.Var
import org.scalajs.dom.html
import com.raquo.airstream.web.AjaxEventStream
import com.raquo.airstream.web.AjaxEventStream.AjaxStreamError
import com.raquo.laminar.api.L.*
import org.scalajs.dom
import scala.io.Codec
import com.raquo.laminar.builders.HtmlTag
import com.raquo.laminar.nodes.ReactiveHtmlElement

object About extends Content[dom.html.Div] {
  override def render = div(
    cls := "row justify-content-center",
    div(
      cls := "col-lg-6",
      p(
        cls := "lead",
        "Third Thing Limited is a software engineering consultancy based in London. " +
          "They specialise in building robust, maintainable and well-documented software solutions, with an emphasis on functional programming"
      )
    )
  )
}
