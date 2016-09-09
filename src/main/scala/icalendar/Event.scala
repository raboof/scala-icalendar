package icalendar

import Properties._

case class Event(
    dtstamp: Option[Dtstamp] = None,
    uid: Uid,
    dtstart: Option[Dtstart] = None,
    classification: Option[Classification] = None,
    description: Option[Description] = None,
    summary: Option[Summary] = None,
    categories: List[Categories] = Nil
) extends VObject {
  override def properties() =
    List(Some(dtstamp.getOrElse(Dtstamp.now())), Some(uid), dtstart, classification, description, summary).flatten ++ categories

  // TODO support for alarms
  def alarms = List()
}
