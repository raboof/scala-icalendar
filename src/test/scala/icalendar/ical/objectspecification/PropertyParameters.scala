package icalendar
package ical
package objectspecification

import java.time.{ LocalDate, ZonedDateTime, ZoneOffset }

import org.scalatest._
import matchers._

import ValueTypes._
import Properties._
import PropertyParameters._
import Writer._

class PropertyParameters extends WordSpec with Matchers {
  "3.2 Property Parameters" should {
    "3.2.1 Alternate Text Representation" in {
      asIcal(
        Description(
          "Project XYZ Review Meeting will include the following agenda items: (a) Market Overview, (b) Finances, (c) Project Management",
          Altrep("CID:part3.msg.970415T083000@example.com"))) should
        haveLines(
          """DESCRIPTION;ALTREP="CID:part3.msg.970415T083000@example.com":Project XYZ Re""",
          """ view Meeting will include the following agenda items: (a) Market Overview\,""",
          """  (b) Finances\, (c) Project Management""")
    }

    "3.2.2 Common Name" in {
      asIcal(Organizer(CalAddress("mailto:jsmith@example.com", Cn("John Smith")))) should haveLines(
        "ORGANIZER;CN=John Smith:mailto:jsmith@example.com")
    }

    "3.2.3 Calendar User Type" in {
      asIcal(Attendee(CalAddress("mailto:ietf-calsch@example.org", cutype = Group))) should haveLines(
        "ATTENDEE;CUTYPE=GROUP:mailto:ietf-calsch@example.org")
    }

    "3.2.4 Delegators" in {
      asIcal(
        Attendee(
          CalAddress(
            "mailto:jdoe@example.com",
            delegatedFrom = DelegatedFrom(List(CalAddress("mailto:jsmith@example.com")))))) should haveLines(
          """ATTENDEE;DELEGATED-FROM="mailto:jsmith@example.com":mailto:jdoe@example.com""")
    }

    "3.2.4 Delegatees" in {
      asIcal(
        Attendee(
          CalAddress(
            "mailto:jsmith@example.com",
            delegatedTo = DelegatedTo(
              List(
                CalAddress("mailto:jdoe@example.com"),
                CalAddress("mailto:jqpublic@example.com")))))) should haveLines(
          """ATTENDEE;DELEGATED-TO="mailto:jdoe@example.com","mailto:jqpublic@example.co""",
          """ m":mailto:jsmith@example.com""")
    }

    "3.2.6 Directory Entry Reference" in {
      asIcal(
        Organizer(
          CalAddress(
            "mailto:jimdo@example.com",
            dir = Dir("ldap://example.com:6666/o=ABC%20Industries,c=US???(cn=Jim%20Dolittle)")))) should haveLines(
          """ORGANIZER;DIR="ldap://example.com:6666/o=ABC%20Industries,c=US???(cn=Jim%20""",
          """ Dolittle)":mailto:jimdo@example.com""")
    }

    "3.2.9 Free/Busy Time Type" in {
      asIcal(
        FreeBusy(
          ListType(
            Period(
              ZonedDateTime.of(1998, 4, 15, 13, 30, 0, 0, ZoneOffset.UTC),
              ZonedDateTime.of(1998, 4, 15, 17, 0, 0, 0, ZoneOffset.UTC))),
          fbtype = Busy)) should haveLines(
          "FREEBUSY;FBTYPE=BUSY:19980415T133000Z/19980415T170000Z")
    }

    "3.2.10 Language" in {
      asIcal(
        Summary("Company Holiday Party", language = Language("en", "US"))) should haveLines(
          "SUMMARY;LANGUAGE=en-US:Company Holiday Party")

      asIcal(
        Location("Tyskland", language = Language("no"))) should haveLines(
          "LOCATION;LANGUAGE=no:Tyskland")
    }

    "3.2.11 Group or List Membership" in {
      asIcal(
        Attendee(
          CalAddress(
            "mailto:jsmith@example.com",
            member = Member(
              List(
                CalAddress("mailto:ietf-calsch@example.org")))))) should haveLines(
          """ATTENDEE;MEMBER="mailto:ietf-calsch@example.org":mailto:jsmith@example.com""")

      asIcal(
        Attendee(
          CalAddress(
            "mailto:janedoe@example.com",
            member = Member(
              List(
                CalAddress("mailto:projectA@example.com"),
                CalAddress("mailto:projectB@example.com")))))) should haveLines(
          """ATTENDEE;MEMBER="mailto:projectA@example.com","mailto:projectB@example.com"""",
          """ :mailto:janedoe@example.com""")
    }

    "3.2.20 Value Data Types" in {
      asIcal(
        Dtend(Date(LocalDate.parse("1998-07-04")))) should
        haveLines("DTEND;VALUE=DATE:19980704")
    }
  }
}
