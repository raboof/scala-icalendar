package icalendar

import java.time.{ ZonedDateTime, ZoneOffset }

import scala.language.implicitConversions

sealed abstract class Property[T <: ValueType] { self: Product =>
  lazy val name = nameFromClassName(this)

  val parameters = self.productIterator.collect {
    case Some(p: PropertyParameter[_]) => p
    case p: PropertyParameter[_] => p
  }.toList
  val value: T
}

object Properties {
  import ValueTypes._
  import PropertyParameters._

  case class Attach(value: EitherType[Uri, Binary]) extends Property[EitherType[Uri, Binary]]
  case class Categories(value: ListType[Text]) extends Property[ListType[Text]]
  case class Classification(value: ClassificationValue) extends Property[ClassificationValue] {
    override lazy val name = "Class"
  }

  case class Dtstamp(value: DateTime) extends Property[DateTime]
  object Dtstamp {
    def now(): Dtstamp = Dtstamp(ZonedDateTime.now(ZoneOffset.UTC))
    implicit def optionFromDateTime(dt: ZonedDateTime): Option[Dtstamp] = Some(Dtstamp(dt))
  }
  case class Dtstart(value: DateTime) extends Property[DateTime]
  object Dtstart {
    implicit def optionFromDateTime(dt: ZonedDateTime): Option[Dtstart] = Some(Dtstart(dt))
  }

  case class Uid(value: Text) extends Property[Text]
  case class Description(value: Text, altrep: Option[Altrep] = None) extends Property[Text]
  case class Organizer(value: CalAddress) extends Property[CalAddress]
  case class Attendee(value: CalAddress) extends Property[CalAddress]
  case class FreeBusy(value: ListType[Period], fbtype: Option[Fbtype] = None) extends Property[ListType[Period]]
  case class Summary(value: Text, language: Option[Language] = None) extends Property[Text]
  case class Location(value: Text, language: Option[Language]) extends Property[Text]
}
