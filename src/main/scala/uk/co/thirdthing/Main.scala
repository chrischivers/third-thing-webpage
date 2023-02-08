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

import uk.co.thirdthing.content.Portfolio

object Main extends App:
  val baseElement    = dom.document.getElementById("page-top")
  val portfolioItems = List(
    PortfolioItem("Property History", "Somethign here", "simple-house-silhouette.jpg", "https://github.com/chrischivers/property-history"),
    PortfolioItem("This website", "Somethign here", "website-icon.png", "https://github.com/chrischivers/property-history"),
    PortfolioItem("Multi-city flight scraper", "Somethign here", "Plane-Taking-Off-Silhouette.png", "https://github.com/chrischivers/multi-city-flight-scraper"),
    PortfolioItem("Master archer automation", "Somethign here", "bow-and-arrow-2.png", "https://github.com/chrischivers/master-archer-automation")
    
    )
  val sections       = List(Section("about", About), Section("portfolio", Portfolio(portfolioItems)), Section("contact", Contact))

  render(baseElement, LandingPage.load(sections, portfolioItems))
