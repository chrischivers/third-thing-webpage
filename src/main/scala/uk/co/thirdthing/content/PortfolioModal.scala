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

object PortfolioModal {

  def render(idx: Int, item: PortfolioItem) = div(
    cls             := "portfolio-modal modal fade",
    idAttr          := s"portfolioModal$idx",
    tabIndex        := -1,
    aria.labelledBy := s"portfolioModal$idx",
    aria.hidden     := true,
    div(
      cls := "modal-dialog modal-xml",
      div(
        cls := "modal-content",
        div(cls := "modal-header border-0", button(cls := "btn-close", typ := "button", dataAttr("bs-dismiss") := "modal", aria.label := "Close")),
        div(
          cls := "modal-body text-center pb-5",
          div(
            cls := "container",
            div(
              cls := "row justify-content-center",
              div(
                cls := "col-lg-8",
                h2(cls := "portfolio-modal-title text-secondary text-uppercase mb-0", item.title),
                div(
                  cls := "divider-custom",
                  div(cls := "divider-custom-line"),
                  div(cls := "divider-custom-icon", i(cls := "fas fa-star")),
                  div(cls := "divider-custom-line")
                ),
                img(cls := "img-fluid rounded mb-5", src := item.imageSrc),
                p(cls   := "mb-4", item.description),
                div(
                  img(
                    cls    := "img-fluid",
                    src    := "github-mark.png",
                    width  := "50px",
                    height := "auto"
                  )
                ),
                button(cls := "btn btn-primary", dataAttr("bs-dismiss") := "modal", i(cls := "fas fa-xmark fa-fw"), "Close window")
              )
            )
          )
        )
      )
    )
  )
}
