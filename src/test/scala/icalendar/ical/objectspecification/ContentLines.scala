package icalendar
package ical
package objectspecification

import org.scalatest.wordspec._
import org.scalatest.matchers.should._

import Properties._
import Writer._

class ContentLines extends AnyWordSpec with Matchers {
  "3.1 Content Lines" should {
    "format a long description" in {
      asIcal(Description(
        "This is a long description that exists on a long line. This is a long description that exists on a long line.")) should
        haveLines(
          "DESCRIPTION:This is a long description that exists on a long line. This is ",
          " a long description that exists on a long line.")
    }
  }
}
