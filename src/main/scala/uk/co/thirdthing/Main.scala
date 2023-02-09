package uk.co.thirdthing

import com.raquo.airstream.state.Var
import com.raquo.laminar.api.L.*
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.html
import com.raquo.airstream.web.AjaxEventStream
import com.raquo.airstream.web.AjaxEventStream.AjaxStreamError
import com.raquo.laminar.api.L.*
import org.scalajs.dom
import uk.co.thirdthing.content._
import uk.co.thirdthing.content.Copy._
import uk.co.thirdthing.content.Portfolio

object Main extends App:

  val baseElement = dom.document.getElementById("page-top")

  val sections =
    List(Section("about", "About", About), Section("portfolio", "Portfolio", Portfolio(porfolioItems)), Section("contact", "Contact", Contact))
  render(baseElement, LandingPage.load(sections, porfolioItems))
