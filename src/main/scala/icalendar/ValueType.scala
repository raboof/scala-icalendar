package icalendar

import java.net.URI
import java.time.ZonedDateTime
import scala.language.implicitConversions

sealed trait ValueType

object ValueTypes {
  import PropertyParameters._

  // TODO add 'parameterized' to add the parameter when not using 'Left'
  case class EitherType[T <: ValueType, U <: ValueType](value: Either[T, U]) extends ValueType
  case class ListType[T <: ValueType](values: T*) extends ValueType

  trait ConstantText {
    val text = nameFromClassName(this).toUpperCase
  }
  sealed abstract class Text extends ValueType {
    val text: String
  }
  object Text {
    implicit def fromString(string: String): Text = new Text with Constant {
      override val text = string
    }
  }

  case class DateTime(dt: ZonedDateTime) extends ValueType
  object DateTime {
    // TODO require or convert to UTC here?
    implicit def fromZonedDateTime(dt: ZonedDateTime): DateTime = DateTime(dt)
  }

  case class Binary(bytes: Array[Byte]) extends ValueType

  case class Uri(uri: URI) extends ValueType
  object Uri {
    implicit def fromString(string: String): Uri = Uri(new URI(string))
    implicit def fromUri(uri: URI): Uri = Uri(uri)
  }

  case class CalAddress(value: Uri,
                        cn: Option[Cn] = None,
                        cutype: Option[Cutype] = None,
                        delegatedFrom: Option[DelegatedFrom] = None,
                        delegatedTo: Option[DelegatedTo] = None,
                        dir: Option[Dir] = None,
                        member: Option[Member] = None)
      extends ValueType
      with Parameterized

  case class Period(from: DateTime, to: DateTime) extends ValueType
  sealed trait ClassificationValue extends Text
  case object Private extends ClassificationValue with ConstantText
}
