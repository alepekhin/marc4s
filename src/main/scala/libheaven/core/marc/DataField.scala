package libheaven.core.marc

import scala.collection.mutable.HashMap

class DataField(val tag: String, val ind1: Char, val ind2: Char, val subfields: List[Subfield]) {

  var optlock:Int = _ 

  val validIndicators: Array[Char] = Array(' ', '|', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

  assert(tag.length() == 3, "Wrong tag " + tag)
  assert(subfields != null, "Null not accepted")
  assert(validIndicators.contains(ind1), "Invalid indicator")
  assert(validIndicators.contains(ind2), "Invalid indicator")

  def getSubfield(code: Char): List[Subfield] = {
    val result = List[Subfield]()
    subfields foreach (x => if (x.code.equals(code)) x :: result)
    return result
  }

  override def toString(): String = {
    var result = "\n  " + tag + ": " + " " + (if (ind1 == ' ') '#' else ind1) + (if (ind2 == ' ') '#' else ind2)
    subfields foreach (x => result = result + x.toString())
    return result
  }

}