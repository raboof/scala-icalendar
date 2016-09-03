package icalendar

abstract class PropertyParameter[T] {
  lazy val name = nameFromClassName(this)
  val value: T
}

trait Parameterized { self: Product =>
  def parameters = self.productIterator.collect {
    case Some(p: PropertyParameter[_]) => p
    case p: PropertyParameter[_] => p
  }.toList
}

object PropertyParameters {
  import ValueTypes._

  type IanaToken = String

  sealed trait ConstantOrExtension
  case class Constant(value: Any) extends ConstantOrExtension
  case class Experimental(name: Xname) extends ConstantOrExtension
  case class Iana(token: IanaToken) extends ConstantOrExtension

  case class Altrep(value: Uri) extends PropertyParameter[Uri]
  case class Cn(value: String) extends PropertyParameter[String]

  // Meh... I guess we want a CutypeValue type or something after all... 
  sealed abstract class Cutype extends PropertyParameter[ConstantOrExtension] {
    override lazy val name = "CUTYPE"
    override val value: ConstantOrExtension = Constant(this)
  }
  case object Individual extends Cutype
  case object Group extends Cutype
  case object Resource extends Cutype
  case object Room extends Cutype
  case object Unknown extends Cutype
  case class ExperimentalCuType(xname: Xname) extends Cutype {
    override val value = Experimental(xname)
  }
  case class IanaCuType(token: IanaToken) extends Cutype {
    override val value = Iana(token)
  }
}
