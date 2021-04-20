package icalendar
package ical
package objectspecification

import org.scalatest._
import matchers._

import CalendarProperties._
import Writer._

class CalendarProperties extends WordSpec with Matchers {
  "3.7 Calendar Properties" should {
    "3.7.3 Product Identifier" in {
      asIcal(
        Prodid("-//ABC Corporation//NONSGML My Product//EN")) should
        haveLines("""PRODID:-//ABC Corporation//NONSGML My Product//EN""")
    }
  }
}
