package icalendar
package ical
package objectspecification

import java.time.{ LocalDate, ZonedDateTime, ZoneId }

import org.scalatest._

import ValueTypes._
import Properties._
import Writer._

class PropertyValueDataTypes extends WordSpec with Matchers {
  "3.3 Property Value Data Types" should {
    "3.3.4 Date" should {
      "correctly format July 14, 1997" in {
        valueAsIcal(Date(LocalDate.parse("1997-07-14"))) should equal("19970714")
      }
    }

    "3.3.5 Date-Time" should {
      "with time provided as UTC+3.00" in {
        asIcal(
          Dtstart(DateTime(ZonedDateTime.of(1997, 9, 3, 19, 30, 0, 0, ZoneId.of("Europe/Sofia"))))) should haveLines(
            // expect time back as UTC
            "DTSTART:19970903T163000Z")
      }
    }
  }
}
