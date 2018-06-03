package me.freidoon.serenity

// import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.vdom.all._
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react._

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportTopLevel, JSImport}
import scala.scalajs.LinkingInfo

import slinky.core._
import slinky.web.ReactDOM
import slinky.hot

import org.scalajs.dom
//import slinky.scalajsreact.Converters._

@JSImport("resources/index.css", JSImport.Default)
@js.native
object IndexCSS extends js.Object

object Main {
  val css = IndexCSS

  sealed trait MyPages
  case object Hello                      extends MyPages
  case object Home                      extends MyPages
  case object Search                    extends MyPages
  case object Products                  extends MyPages
  case class ProductInfo(id: Int) extends MyPages

  @JSExportTopLevel("entrypoint.main")
  def main(): Unit = {
    if (LinkingInfo.developmentMode) {
      hot.initialize()
    }

    val NoArgs =
      ScalaComponent.builder[Unit]("No args")
        .renderStatic(
           div(className := "Center-container")(header("serenity"),
           p("this is Fereidoon's react-based static website.")))
        .build
    val routerConfig = RouterConfigDsl[MyPages].buildConfig { dsl =>
      import dsl._

      (emptyRule
         | staticRoute(root,     Home)  ~> render(NoArgs())
         | staticRoute("#hello#l", Hello) ~> render(a("f", href := "./#hello#f"))
         | staticRoute("#hello#f", Search) ~> render(div("foooo!ap"))
      ) .notFound(redirectToPage(Home)(Redirect.Push))
    }
    val router = Router(BaseUrl.until_#, routerConfig)
    // tell React to render the router in the document body
    router().renderIntoDOM(dom.document.getElementById("root"))

    return

    val container = Option(dom.document.getElementById("root")).getOrElse {
      val elem = dom.document.createElement("div")
      elem.id = "root"
      dom.document.body.appendChild(elem)
      elem
    }

    ReactDOM.render(App(), container)
  }
}
