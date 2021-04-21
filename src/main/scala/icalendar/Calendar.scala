package icalendar

import CalendarProperties._

case class Calendar(
    prodid: Prodid,
    version: Version = Version("2.0"),
    // TODO calscale
    // TODO method
    // TODO x-prop / iana-prop
    events: List[Event] = Nil
// TODO other components
) extends VObject
