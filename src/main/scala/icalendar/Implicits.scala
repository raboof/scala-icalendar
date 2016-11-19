package icalendar

import scala.language.implicitConversions

object Implicits {
  implicit def liftOption[T](value: U)(implicit f: U => T): Option[T] = Some(value)
}
