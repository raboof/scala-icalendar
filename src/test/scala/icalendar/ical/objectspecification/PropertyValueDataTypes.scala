package icalendar
package ical
package objectspecification

import java.time.{ZonedDateTime, ZoneId}

import org.scalatest._

import Properties._
import Writer._

class PropertyValueDataTypes extends WordSpec with Matchers {
  "3.3 Property Value Data Types" should {
    "3.3.5 Date-Time" should {
      "with time provided as UTC+3.00" in {
        asIcal(
          Dtstart(ZonedDateTime.of(1997, 9, 3, 19, 30, 0, 0, ZoneId.of("Europe/Sofia")))
        ) should haveLines (
          // expect time back as UTC
          "DTSTART:19970903T163000Z"
        )
      }
    }
  }
}
