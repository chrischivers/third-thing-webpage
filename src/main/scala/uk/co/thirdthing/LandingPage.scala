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
import uk.co.thirdthing.content.*
import uk.co.thirdthing.content.Section

import java.time.{LocalDate, ZoneId}

object LandingPage:

  private def navbar(sections: List[Section]) = nav(
    cls := "navbar navbar-expand-lg bg-secondary text-uppercase fixed-top",
    idAttr("mainNav"),
    div(
      cls := "container",
      a(cls := "navbar-brand", href("#page-top"), "Third Thing Ltd"),
      button(
        cls                   := "navbar-toggler text-uppercase font-weight-bold bg-primary text-white rounded",
        dataAttr("bs-toggle") := "collapse",
        dataAttr("bs-target") := "#navbarResponsive",
        aria.controls         := "navbarResponsive",
        aria.expanded         := false,
        aria.label            := "Toggle navigation",
        "Menu",
        i(cls := "fas fa-bars")
      ),
      div(
        cls := "collapse navbar-collapse",
        idAttr("navbarResponsive"),
        ul(
          cls := "navbar-nav ms-auto",
          sections.map { section =>
            li(cls := "nav-item mx-0 mx-lg-1", a(cls := "nav-link py-3 px-0 px-lg-3 rounded", href(s"#${section.id}"), section.title))
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

  private val footer_ = footer(
    cls := "footer text-center",
    div(
      cls := "container",
      div(
        cls := "row",
        div(
          cls := "col-lg-4 mb-5 mb-lg-0",
          h4(cls := "text-uppercase mb-4", "Registered office"),
          p(cls  := "lead mb-0", "80 Ashton Road, Denton, Manchester, Lancashire, United Kingdom, M34 3JF")
        ),
        div(
          cls := "col-lg-4 mb-5 mb-lg-0",
          a(
            cls    := "btn btn-outline-light btn-social mx-1",
            href   := "https://github.com/chrischivers",
            target := "_blank",
            i(cls := "fa-brands fa-github")
          ),
          a(
            cls    := "btn btn-outline-light btn-social mx-1",
            href   := "https://www.linkedin.com/in/chrisjchivers",
            target := "_blank",
            i(cls := "fab fa-fw fa-linkedin-in")
          )
        ),
        div(
          cls := "col-lg-4 mb-5 mb-lg-0",
          p(cls := "lead mb-0", "Company registration number 14078101")
        )
      )
    )
  )

  private val copyright = div(
    cls := "copyright py-4 text-center text-white",
    div(cls := "container", small(s"Copyright © Third Thing Ltd ${LocalDate.now(ZoneId.of("UTC")).getYear}"))
  )

  private def renderSection(s: Section) = section(
    cls := s"page-section ${s.id}",
    idAttr(s.id),
    div(
      cls := "container",
      h2(cls := "page-section-heading text-center text-uppercase text-secondary mb-0", s.title),
      div(
        cls := "divider-custom",
        div(cls := "divider-custom-line"),
        div(cls := "divider-custom-icon", i(cls := "fas fa-folder")),
        div(cls := "divider-custom-line")
      ),
      s.content.render
    )
  )

  def load(sections: List[Section], portfolioItems: List[PortfolioItem]) =
    val portfolioModals = portfolioItems.zipWithIndex.map { case (item, idx) => PortfolioModal.render(idx, item) }
    val content         = List(navbar(sections), masthead) ++ sections.map(renderSection) ++ List(footer_, copyright) ++ portfolioModals
    div(content: _*)
