/**
  * The scala-icalendar unit tests follow the structure of RFC5545
  */
package icalendar

import scala.language.implicitConversions

import org.scalatest._
import org.scalatest.matchers.should._

package object ical {
  implicit def liftOption[T](value: T): Option[T] = Some(value)
  implicit def liftOptionWithConversion[T, U](obj: T)(implicit conv: Conversion[T, U]): Option[U] = Some(conv(obj))

  def haveLines(lines: String*) =
    Matchers.equal(lines.mkString("\r\n") + "\r\n")
}
