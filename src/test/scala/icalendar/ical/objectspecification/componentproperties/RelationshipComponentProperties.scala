package icalendar
package ical
package objectspecification

import org.scalatest._
import matchers._

import Properties._
import Writer._

class RelationshipComponentProperties extends WordSpec with Matchers {
  "3.8.4 Relationship Component Properties" should {
    "3.8.4.6 Uniform Resource Locator" in {
      asIcal(
        Url("http://example.com/pub/calendars/jsmith/mytime.ics")) should
        haveLines("URL:http://example.com/pub/calendars/jsmith/mytime.ics")
    }
  }
}
