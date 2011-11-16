package libheaven.core.marc

class Subfield(val code: Char, val data: String) {

  assert(data != null, "Data can not be null")

  var optlock:Int = _
  
  override def toString(): String = {
    return "\n    $" + code + ": " + data
  }

}
