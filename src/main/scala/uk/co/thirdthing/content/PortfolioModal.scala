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

object PortfolioModal:

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
                cls := "col-lg-10",
                img(cls := "img-fluid rounded mb-2", src := item.imageSrc, width := "100px", height := "auto"),
                h2(cls  := "portfolio-modal-title text-secondary text-uppercase mb-0", item.title),
                div(
                  cls := "divider-custom",
                  div(cls := "divider-custom-line"),
                  div(
                    cls := "divider-custom-icon",
                    a(
                      href   := item.githubUrl,
                      target := "_blank",
                      alt    := "Link to project on Github",
                      img(
                        cls    := "img-fluid",
                        src    := "github-mark.png",
                        width  := "50px",
                        height := "auto"
                      )
                    )
                  ),
                  div(cls := "divider-custom-line")
                ),
                buildDescription(item.description),
                div(
                  cls := "row",
                  buildItemListing("Libraries/Frameworks", item.librariesAndFrameworks),
                  buildItemListing("Infrastructure", item.infrastructure)
                ),
                button(cls := "btn btn-primary", dataAttr("bs-dismiss") := "modal", i(cls := "fas fa-xmark fa-fw"), "Close window")
              )
            )
          )
        )
      )
    )
  )

  private def buildDescription(desc: String) =
    val elements = List(cls("mb-4")) ++ desc.split("\n").map(p(_))
    p(elements: _*)

private def buildItemListing(header: String, listing: List[String]) =
  val elements = List(cls("col-lg-6"), h5(header)) ++ listing.map(div(_))
  div(elements)
