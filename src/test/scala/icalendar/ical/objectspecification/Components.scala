package icalendar
package ical
package objectspecification

import java.time.{ZonedDateTime, ZoneOffset}

import org.scalatest.wordspec._
import org.scalatest.matchers.should._

import Properties._
import ValueTypes._
import Writer._

class Components extends AnyWordSpec with Matchers {
  "3.6 Calendar Components" should {
    "3.6.1 Event" in {
      asIcal(
        Event(
          dtstamp = ZonedDateTime.of(1997, 9, 1, 13, 0, 0, 0, ZoneOffset.UTC),
          uid = Uid("19970901T130000Z-123401@example.com"),
          dtstart = ZonedDateTime.of(1997, 9, 3, 16, 30, 0, 0, ZoneOffset.UTC),
          classification = Classification(Private),
          summary = Summary("Annual Employee Review"),
          categories = List(Categories(ListType("BUSINESS", "HUMAN RESOURCES")))
        )
      ) should
        haveLines(
          "BEGIN:VEVENT",
          "DTSTAMP:19970901T130000Z",
          "UID:19970901T130000Z-123401@example.com",
          "DTSTART:19970903T163000Z",
          // TODO add dtend
          //"DTEND:19970903T190000Z",
          "CLASS:PRIVATE",
          "SUMMARY:Annual Employee Review",
          "CATEGORIES:BUSINESS,HUMAN RESOURCES",
          "END:VEVENT"
        )
    }
  }
}
