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
  def asString: String
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

  case class DelegatedFrom(value: List[CalAddress]) extends PropertyParameter[List[CalAddress]]
  case class DelegatedTo(value: List[CalAddress]) extends PropertyParameter[List[CalAddress]]
  case class Dir(value: Uri) extends PropertyParameter[Uri]
  case class Fbtype(value: FbtypeValue) extends PropertyParameter[FbtypeValue]
  object Fbtype {
    implicit def fromValue(value: FbtypeValue): Fbtype = Fbtype(value)
    implicit def optionFromValue(value: FbtypeValue): Option[Fbtype] = Some(Fbtype(value))
  }
  sealed trait FbtypeValue extends PropertyParameterValueType
  case object Free extends FbtypeValue with Constant
  case object Busy extends FbtypeValue with Constant
  case object BusyUnavailable extends FbtypeValue with Constant
  case object BusyTentative extends FbtypeValue with Constant
  case class ExperimentalFbtype(xname: Xname) extends FbtypeValue with XnameValue
  case class IanaFbtype(token: IanaToken) extends FbtypeValue with IanaTokenValue

  case class Language(value: LanguageTag) extends PropertyParameter[LanguageTag]
  object Language {
    def apply(tag: String): Language = Language(LanguageTag(List(tag)))
    def apply(tag1: String, tag2: String): Language = Language(LanguageTag(List(tag1, tag2)))
  }
  // RFC5646
  case class LanguageTag(subtags: List[String]) extends PropertyParameterValueType {
    lazy val asString = subtags.mkString("-")
  }

  case class Member(value: List[CalAddress]) extends PropertyParameter[List[CalAddress]]

  case class Value(value: String) extends PropertyParameter[String]
}
