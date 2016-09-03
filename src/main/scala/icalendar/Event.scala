package icalendar

import Properties._

case class Event(
    dtstamp: Option[Dtstamp],
    uid: Uid
  ) {
    def properties() = List(
     dtstamp.getOrElse(Dtstamp.now()),
     uid)

    // TODO support for alarms
    def alarms = List()
  }
