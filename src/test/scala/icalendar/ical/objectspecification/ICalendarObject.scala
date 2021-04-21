package icalendar
package ical
package objectspecification

import java.time.{ ZonedDateTime, ZoneOffset }

import org.scalatest.wordspec._
import org.scalatest.matchers.should._

import CalendarProperties._
import Properties._
import ValueTypes._
import Writer._

class ICalendarObject extends AnyWordSpec with Matchers {
  "3.4 iCalendar Object" should {
    "Simple example" in {
      asIcal(
        Calendar(
          prodid = Prodid("-//hacksw/handcal//NONSGML v1.0//EN"),
          events = List(Event(
            uid = Uid("19970610T172345Z-AF23B2@example.com"),
            dtstamp = ZonedDateTime.of(1997, 6, 10, 17, 23, 45, 0, ZoneOffset.UTC),
            dtstart = ZonedDateTime.of(1997, 7, 14, 17, 0, 0, 0, ZoneOffset.UTC),
            // TODO DTEND
            summary = Summary("Bastille Day Party"))))) should
        haveLines(
          "BEGIN:VCALENDAR",
          "PRODID:-//hacksw/handcal//NONSGML v1.0//EN",
          "VERSION:2.0",
          "BEGIN:VEVENT",
          "DTSTAMP:19970610T172345Z",
          "UID:19970610T172345Z-AF23B2@example.com",
          "DTSTART:19970714T170000Z",
          // TODO
          // "DTEND:19970715T040000Z",
          "SUMMARY:Bastille Day Party",
          "END:VEVENT",
          "END:VCALENDAR")
    }
  }
}
