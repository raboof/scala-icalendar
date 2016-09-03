package icalendar

import java.net.URI
import scala.language.implicitConversions

sealed trait ValueType

object ValueTypes {
  import PropertyParameters._

  // TODO add 'parameterized' to add the parameter when not using 'Left'
  case class EitherType[T <: ValueType, U <: ValueType](value: Either[T, U]) extends ValueType

  case class Text(text: String) extends ValueType
  object Text {
    implicit def fromString(string: String): Text = Text(string)
  }

  case class DateTime(dt: Long) extends ValueType
  object DateTime {
    implicit def fromLong(dt: Long): DateTime = DateTime(dt)
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
                        delegatedFrom: Option[DelegatedFrom] = None)
      extends ValueType
      with Parameterized

}
