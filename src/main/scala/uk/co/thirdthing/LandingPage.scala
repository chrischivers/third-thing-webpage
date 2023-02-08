package uk.co.thirdthing

import com.raquo.airstream.state.Var
import org.scalajs.dom.html
import com.raquo.airstream.web.AjaxEventStream
import com.raquo.airstream.web.AjaxEventStream.AjaxStreamError
import com.raquo.laminar.api.L.*
import org.scalajs.dom
import scala.io.Codec
import com.raquo.laminar.builders.HtmlTag
import com.raquo.laminar.nodes.ReactiveHtmlElement
import uk.co.thirdthing.content._

object LandingPage:

  private def navbar(sections: List[String]) = nav(
    cls := "navbar navbar-expand-lg bg-secondary text-uppercase fixed-top",
    idAttr("mainNav"),
    div(
      cls := "container",
      a(cls := "navbar-brand", href("#page-top"), "Third Thing Ltd"),
      button(
        cls                   := "navbar-toggler text-uppercase font-weight-bold bg-primary text-white rounded",
        dataAttr("bs-toggle") := "collapse",
        dataAttr("bs-target") := "#navbarResponsive",
        aria.controls := "navbarResponsive",
        aria.expanded := false,
        aria.label := "Toggle navigation",
        "Menu",
        i(cls := "fas fa-bars")
      ),
      div(
        cls := "collapse navbar-collapse",
        idAttr("navbarResponsive"),
        ul(
          cls := "navbar-nav ms-auto",
          sections.map { sectionName =>
            li(cls := "nav-item mx-0 mx-lg-1", a(cls := "nav-link py-3 px-0 px-lg-3 rounded", href(s"#$sectionName"), sectionName))
          }
        )
      )
    )
  )

  private val masthead = header(
    cls := "masthead bg-primary text-white text-center",
    div(
      cls := "container d-flex align-items-center flex-column",
      h1(cls := "masthead-heading text-uppercase mb-0", "Third Thing Ltd"),
      div(
        cls := "divider-custom",
        div(cls := "divider-custom-line"),
        div(cls := "divider-custom-icon", i(cls := "fas fa-folder")),
        div(cls := "divider-custom-line")
      ),
      p(cls := "masthead-subheading font-weight-light mb-0", "Software Engineering Consultancy"),
      p(cls := "masthead-subheading font-weight-light mb-0", "London, UK")
    )
  )

  private def render(s: Section) = section(
    cls := s"page-section ${s.name}",
    idAttr(s.name),
    div(
      cls := "container",
      h2(cls := "page-section-heading text-center text-uppercase text-secondary mb-0", s.name),
      div(
        cls := "divider-custom",
        div(cls := "divider-custom-line"),
        div(cls := "divider-custom-icon", i(cls := "fas fa-folder")),
        div(cls := "divider-custom-line")
      ),
      s.content.render
    )
  )

  def load(sections: List[Section], portfolioItems: List[PortfolioItem]) = {
    val portfolioModals = portfolioItems.zipWithIndex.map { case (item, idx) => PortfolioModal.render(idx, item) }
    val content         = List(navbar(sections.map(_.name)), masthead) ++ sections.map(render) ++ portfolioModals
    div(content: _*)
  }
