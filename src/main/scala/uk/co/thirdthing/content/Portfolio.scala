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

object Portfolio {

  def apply(items: List[PortfolioItem]) = new Content[dom.html.Div] {

    // private def portfolioItem(imageFileName: String, )
    override def render = div(
      cls := "row justify-content-center",
      items.zipWithIndex.map { case (item, idx) =>
        div(
          cls := "col-md-3 col-lg-2 mb-5",
          div(
            cls                   := "portfolio-item mx-auto",
            dataAttr("bs-toggle") := "modal",
            dataAttr("bs-target") := s"#portfolioModal$idx",
            div(
              cls := "portfolio-item-caption d-flex align-items-center justify-content-center h-100 w-100",
              div(
                cls := "portfolio-item-caption-content text-center text-white", 
              i(cls := "fas fa-plus fa-3x"),
              p(item.title)
              )
            ),
            img(
              cls    := "img-fluid",
              src    := item.imageSrc
            )
          )
        )
      }
    )
  }
}
