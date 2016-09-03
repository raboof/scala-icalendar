package icalendar
package ical
package objectspecification

import org.scalatest._
import matchers._

import ValueTypes._
import Properties._
import PropertyParameters._
import Writer._

class PropertyParameters extends WordSpec with Matchers {
  "3.2 Property Parameters" should {
    "3.2.1 Alternate Text Representation" in {
      asIcal(Description("Project XYZ Review Meeting will include the following agenda items: (a) Market Overview, (b) Finances, (c) Project Management",
                    Altrep("CID:part3.msg.970415T083000@example.com"))) should
        haveLines(
          """DESCRIPTION;ALTREP="CID:part3.msg.970415T083000@example.com":Project XYZ Re""",
          """ view Meeting will include the following agenda items: (a) Market Overview\,""",
          """  (b) Finances\, (c) Project Management""")
    }

    "3.2.2 Common Name" in {
      asIcal(Organizer(CalAddress("mailto:jsmith@example.com", Cn("John Smith")))) should haveLines(
        "ORGANIZER;CN=John Smith:mailto:jsmith@example.com"
      )
    }

    "3.2.3 Calendar User Type" in {
      asIcal(Attendee(CalAddress("mailto:ietf-calsch@example.org", cutype = Group))) should haveLines(
        "ATTENDEE;CUTYPE=GROUP:mailto:ietf-calsch@example.org"
      )
    }
  }
}
