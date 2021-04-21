package icalendar
package ical

import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
  * Writing icalendar objects to the line-based RFC5545 ICalendar format
  */
object Writer {
  import ValueTypes._
  import PropertyParameters._

  val DQUOTE = "\""
  val CRLF = "\r\n"

  def additionalParameters(value: ValueType): List[PropertyParameter[_]] =
    value match {
      case EitherType(Right(payload)) =>
        List(Value(payload.getClass.getSimpleName.toUpperCase))
      case _ => Nil
    }

  def valueAsIcal(value: ValueType): String =
    value match {
      case t: Text =>
        t.text.flatMap {
          case '\\'  => "\\\\"
          case ';'   => "\\;"
          case ','   => "\\,"
          case '\n'  => "\\n"
          case other => other.toString
        }
      case date: Date => {
        date.d.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
      }
      case date: DateTime => {
        val utc = date.dt.withZoneSameInstant(ZoneOffset.UTC)
        utc.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"))
      }
      case value: CalAddress          => valueAsIcal(value.value)
      case Uri(uri)                   => uri.toString
      case EitherType(Left(payload))  => valueAsIcal(payload)
      case EitherType(Right(payload)) => valueAsIcal(payload)
      case Binary(bytes)              => ??? // TODO base64-encoded
      case list: ListType[_] =>
        list.values.toList.map(valueAsIcal).mkString(",")
      case Period(from, to) => valueAsIcal(from) + "/" + valueAsIcal(to)
    }

  def parameterValueAsIcal(value: Any): String =
    value match {
      case uri: Uri =>
        DQUOTE + uri.uri + DQUOTE
      case string: String =>
        if (
          string.contains(':') || string.contains(';') || string.contains(',')
        ) DQUOTE + string + DQUOTE
        else string
      case c: PropertyParameterValueType => c.asString
      case e: Either[_, _] =>
        e match {
          case Left(v)  => parameterValueAsIcal(v)
          case Right(v) => parameterValueAsIcal(v)
        }
      case l: List[_] =>
        l.map {
          case value: ValueType => DQUOTE + valueAsIcal(value) + DQUOTE
          case other            => DQUOTE + other.toString + DQUOTE
        }.mkString(",")
    }
  def parameterName(name: String): String =
    (name.head.toString ++ "[A-Z\\d]".r.replaceAllIn(
      name.tail,
      { m =>
        "-" + m.group(0)
      }
    )).toUpperCase

  def asIcal(parameters: List[PropertyParameter[_]]) =
    parameters
      .map((parameter: PropertyParameter[_]) =>
        ";" + parameterName(parameter.name) + "=" + parameterValueAsIcal(
          parameter.value
        )
      )
      .mkString("")

  def fold(contentline: String): String =
    if (contentline.length > 75)
      contentline.take(75) + CRLF + " " + fold(contentline.drop(75))
    else contentline
  def valueParameters(value: Any) =
    value match {
      case parameterized: Parameterized => parameterized.parameters
      case _                            => Nil
    }
  def asIcal(property: Property[_ <: ValueType]): String =
    fold(
      property.name.toUpperCase +
        asIcal(property.parameters) +
        asIcal(additionalParameters(property.value)) +
        asIcal(valueParameters(property.value)) +
        ":" + valueAsIcal(property.value)
    ) + CRLF

  def asIcal(vobject: VObject): String = {
    "BEGIN:" + vobject.name + CRLF +
      vobject.properties().map(asIcal).mkString +
      vobject.components().map(asIcal).mkString +
      "END:" + vobject.name + CRLF
  }
}
