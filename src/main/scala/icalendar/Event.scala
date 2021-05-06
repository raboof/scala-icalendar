package icalendar

import Properties._

case class Event(
    dtstamp: Option[Dtstamp] = None,
    uid: Uid,
    dtstart: Option[Dtstart] = None,
    classification: Option[Classification] = None,
    description: Option[Description] = None,
    // geo
    // last-mod
    location: Option[Location] = None,
    organizer: Option[Organizer] = None,
    // priority
    // seq
    // status
    summary: Option[Summary] = None,
    // transp
    url: Option[Url] = None,
    // recurid
    // rrule
    // dtend/duration
    // ...
    categories: List[Categories] = Nil
) extends VObject:
  override def properties() =
    val constants = super.properties()
    if (constants.exists(_.isInstanceOf[Dtstamp])) constants
    else Dtstamp.now() :: constants

  // TODO support for alarms
  def alarms = List()
