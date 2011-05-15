package bootstrap.liftweb

import _root_.net.liftweb.util._
import _root_.net.liftweb.sitemap._
import _root_.org.miq.model._
import net.liftweb.common._
import net.liftweb.http._
import org.miq.liftkopf.api.{Sheet, PlayerOverview}
import net.liftweb.mapper._

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor = new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
          Props.get("db.url") openOr "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
          Props.get("db.user"),
          Props.get("db.password"))
      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)
      DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
    }
    // where to search snippet
    LiftRules.addToPackages("org.miq")
    Schemifier.schemify(true, Schemifier.infoF _, User)
    // Build SiteMap
    val entries = Menu(Loc("Home", List("index"), S.?("Home"))) :: Menu(Loc("Statistics", List("statistics"), S.?("Statistics"))) :: User.sitemap
    LiftRules.setSiteMap(SiteMap(entries: _*))
    /*
     * Show the spinny image when an Ajax call starts
     */
    LiftRules.ajaxStart = Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    /*
     * Make the spinny image go away when it ends
     */
    LiftRules.ajaxEnd = Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)
    LiftRules.early.append{ _.setCharacterEncoding("UTF-8") }
    LiftRules.resourceNames = "liftkopf" :: LiftRules.resourceNames
    LiftRules.dispatch.append(PlayerOverview)
    LiftRules.dispatch.prepend(Sheet)

    S.addAround(DB.buildLoanWrapper)
  }

  ResponseInfo.docType = {
        case _ if S.getDocType._1 => S.getDocType._2
        case _ => Full(DocType.xhtmlStrict)
      }
}
