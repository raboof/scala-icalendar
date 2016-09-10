package icalendar

import Properties._

case class Event(
    dtstamp: Option[Dtstamp] = None,
    uid: Uid,
    dtstart: Option[Dtstart] = None,
    classification: Option[Classification] = None,
    description: Option[Description] = None,
    summary: Option[Summary] = None,
    url: Option[Url] = None,
    categories: List[Categories] = Nil
) extends VObject {
  override def properties() = {
    val constants = super.properties()
    if (constants.exists(_.isInstanceOf[Dtstamp])) constants
    else Dtstamp.now() :: constants
  }

  // TODO support for alarms
  def alarms = List()
}
