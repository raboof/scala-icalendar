package icalendar

import java.net.{URI, URL}
import java.time.{LocalDate, ZonedDateTime}
import scala.language.implicitConversions

sealed trait ValueType

object ValueTypes:
  import PropertyParameters._

  // TODO add 'parameterized' to add the parameter when not using 'Left'
  case class EitherType[T <: ValueType, U <: ValueType](value: Either[T, U])
      extends ValueType
  object EitherType:
    implicit def liftLeft[T <: ValueType, U <: ValueType](
        left: T
    ): EitherType[T, U] = EitherType(Left(left))
    implicit def liftRight[T <: ValueType, U <: ValueType](
        right: U
    ): EitherType[T, U] = EitherType(Right(right))

  case class ListType[T <: ValueType](values: T*) extends ValueType

  trait ConstantText:
    val text = nameFromClassName(this).toUpperCase

  sealed abstract class Text extends ValueType:
    val text: String

  object Text:
    given Conversion[String, Text] = string =>
      new Text with Constant:
        override val text = string

  case class Date(d: LocalDate) extends ValueType
  object Date:
    given Conversion[LocalDate, Date] = Date(_)

  case class DateTime(dt: ZonedDateTime) extends ValueType
  object DateTime:
    given Conversion[ZonedDateTime, DateTime] = DateTime(_)

  case class Binary(bytes: Array[Byte]) extends ValueType

  case class Uri(uri: URI) extends ValueType
  object Uri:
    given Conversion[String, Uri] = str => Uri(new URI(str))
    given Conversion[URI, Uri] = Uri(_)
    given Conversion[URL, Uri] = url => Uri(url.toURI)

  case class CalAddress(
      value: Uri,
      cn: Option[Cn] = None,
      cutype: Option[Cutype] = None,
      delegatedFrom: Option[DelegatedFrom] = None,
      delegatedTo: Option[DelegatedTo] = None,
      dir: Option[Dir] = None,
      member: Option[Member] = None
  ) extends ValueType
      with Parameterized

  case class Period(from: DateTime, to: DateTime) extends ValueType
  sealed trait ClassificationValue extends Text
  case object Private
      extends ClassificationValue
      with
        ConstantText
