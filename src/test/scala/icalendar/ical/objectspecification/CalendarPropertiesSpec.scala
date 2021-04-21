package icalendar
package ical
package objectspecification

import org.scalatest.wordspec._
import org.scalatest.matchers.should._

import CalendarProperties._
import Writer._

class CalendarPropertiesSpec extends AnyWordSpec with Matchers {
  "3.7 Calendar Properties" should {
    "3.7.3 Product Identifier" in {
      asIcal(Prodid("-//ABC Corporation//NONSGML My Product//EN")) should
        haveLines("""PRODID:-//ABC Corporation//NONSGML My Product//EN""")
    }
  }
}
