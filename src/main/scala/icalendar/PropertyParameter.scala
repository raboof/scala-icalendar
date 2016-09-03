package icalendar

import scala.language.implicitConversions

sealed abstract class PropertyParameter[T] {
  lazy val name = nameFromClassName(this)
  val value: T
}

trait Parameterized { self: Product =>
  def parameters =
    self.productIterator.collect {
      case Some(p: PropertyParameter[_]) => p
      case p: PropertyParameter[_] => p
    }.toList
}

case class Xname(value: String, vendorId: Option[String] = None)
trait XnameValue {
  val xname: Xname
  val asString = ???
}
case class IanaToken(token: String)
trait IanaTokenValue {
  val token: IanaToken
  val asString = ???
}

abstract class PropertyParameterValueType {
  val asString: String
}
trait Constant {
  val asString = nameFromClassName(this).toUpperCase
}

object PropertyParameters {
  import ValueTypes._

  case class Altrep(value: Uri) extends PropertyParameter[Uri]
  case class Cn(value: String) extends PropertyParameter[String]

  case class Cutype(value: CutypeValue) extends PropertyParameter[CutypeValue]
  object Cutype {
    implicit def fromValue(value: CutypeValue): Cutype = Cutype(value)
    implicit def optionFromValue(value: CutypeValue): Option[Cutype] = Some(Cutype(value))
  }
  sealed trait CutypeValue extends PropertyParameterValueType
  case object Individual extends CutypeValue with Constant
  case object Group extends CutypeValue with Constant
  case object Resource extends CutypeValue with Constant
  case object Room extends CutypeValue with Constant
  case object Unknown extends CutypeValue with Constant
  case class ExperimentalCuType(xname: Xname) extends CutypeValue with XnameValue
  case class IanaCuType(token: IanaToken) extends CutypeValue with IanaTokenValue
}
