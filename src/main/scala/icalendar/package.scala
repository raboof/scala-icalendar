package object icalendar {
  def nameFromClassName(obj: Any) = {
    val className = obj.getClass.getName
    className.substring(className.indexOf('$') + 1).replace("$", "")
  }
}
