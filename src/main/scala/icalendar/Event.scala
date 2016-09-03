package icalendar

import Properties._

case class Event(
    dtstamp: Option[Dtstamp],
    uid: Uid
  ) {
    def properties() = List(
     dtstamp.getOrElse(now()),
     uid)

    // TODO support for alarms
    def alarms = List()
  }
