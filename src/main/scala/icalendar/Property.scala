package icalendar

import java.time.{LocalDate, ZonedDateTime, ZoneOffset}
import java.net.{URL, URI}

import scala.language.implicitConversions

sealed abstract class Property[T <: ValueType] { self: Product =>
  lazy val name = nameFromClassName(this)

  val parameters = self.productIterator.collect {
    case Some(p: PropertyParameter[_]) => p
    case p: PropertyParameter[_]       => p
  }.toList
  val value: T
}

object CalendarProperties {
  import ValueTypes._
  import PropertyParameters._

  case class Prodid(value: Text) extends Property[Text]
  case class Version(value: Text) extends Property[Text]
}

/** Component properties */
object Properties {
  import ValueTypes._
  import PropertyParameters._

  /** Descriptive */
  case class Attach(value: EitherType[Uri, Binary])
      extends Property[EitherType[Uri, Binary]]
  case class Categories(value: ListType[Text]) extends Property[ListType[Text]]
  case class Classification(value: ClassificationValue)
      extends Property[ClassificationValue] {
    override lazy val name = "Class"
  }
  case class Description(value: Text, altrep: Option[Altrep] = None)
      extends Property[Text]
  case class Location(value: Text, language: Option[Language])
      extends Property[Text]
  case class Summary(value: Text, language: Option[Language] = None)
      extends Property[Text]

  /** Date and Time */
  case class Dtstart(value: EitherType[DateTime, Date])
      extends Property[EitherType[DateTime, Date]]
  object Dtstart {
    implicit def optionFromDateTime(dt: ZonedDateTime): Option[Dtstart] =
      Some(Dtstart(EitherType(Left(dt))))
    implicit def optionFromLocalDate(ld: LocalDate): Option[Dtstart] =
      Some(Dtstart(EitherType(Right(ld))))
  }
  case class Dtend(value: EitherType[DateTime, Date])
      extends Property[EitherType[DateTime, Date]]
  case class FreeBusy(value: ListType[Period], fbtype: Option[Fbtype] = None)
      extends Property[ListType[Period]]

  /** Relationship */
  case class Attendee(value: CalAddress) extends Property[CalAddress]
  case class Organizer(value: CalAddress) extends Property[CalAddress]
  case class Url(value: Uri) extends Property[Uri]
  object Url {
    implicit def optionFromURL(url: URL): Option[Url] = Some(Url(url))
    implicit def optionFromURI(uri: URI): Option[Url] = Some(Url(uri))
  }
  case class Uid(value: Text) extends Property[Text]

  /** Change Management */
  case class Dtstamp(value: DateTime) extends Property[DateTime]
  object Dtstamp {
    def now(): Dtstamp = Dtstamp(ZonedDateTime.now(ZoneOffset.UTC))
    implicit def optionFromDateTime(dt: ZonedDateTime): Option[Dtstamp] =
      Some(Dtstamp(dt))
  }
}
