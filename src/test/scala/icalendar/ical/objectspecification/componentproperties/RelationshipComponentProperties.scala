package icalendar
package ical
package objectspecification

import org.scalatest.wordspec._
import org.scalatest.matchers.should._

import Properties._
import Writer._

class RelationshipComponentProperties extends AnyWordSpec with Matchers {
  "3.8.4 Relationship Component Properties" should {
    "3.8.4.6 Uniform Resource Locator" in {
      asIcal(
        Url("http://example.com/pub/calendars/jsmith/mytime.ics")) should
        haveLines("URL:http://example.com/pub/calendars/jsmith/mytime.ics")
    }
  }
}
