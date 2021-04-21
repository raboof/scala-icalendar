package icalendar

abstract class VObject { self: Product =>
  lazy val name = "V" + nameFromClassName(this).toUpperCase

  def properties(): List[Property[_]] =
    self.productIterator.collect {
      case Some(p: Property[_]) => List(p)
      case p: Property[_] => List(p)
      case list: List[_] => list.collect { case p: Property[_] => p }
    }.flatten.toList

  def components() =
    self.productIterator.collect {
      case Some(o: VObject) => List(o)
      case o: VObject => List(o)
      case list: List[_] => list.collect { case o: VObject => o }
    }.flatten
}
