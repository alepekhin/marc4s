package libheaven.core.marc

class ControlField(val tag: String, var data: String) {

  val ID_TAG = "001"
  var optlock:Int = _ 

  assert(tag.length() == 3 , "Wrong tag " + tag)
  assert(data != null && data.trim().size > 0, "Empty data not accepted")

  override
  def toString():String  = {
    "\n  " + tag + ": " + data
  }
  
  def isIndentificator = tag.equals(ID_TAG)
  
}