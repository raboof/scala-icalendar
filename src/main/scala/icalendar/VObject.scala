package icalendar

abstract class VObject { self: Product =>
  lazy val name = "V" + nameFromClassName(this).toUpperCase

  def properties() =
    self.productIterator.collect {
      case Some(p: Property[_]) => p
      case p: Property[_] => p
    }.toList

  def components() =
    self.productIterator.collect {
      case Some(o: VObject) => List(o)
      case o: VObject => List(o)
      case objects: List[_] => objects.collect { case o: VObject => o }
    }.flatten
}
