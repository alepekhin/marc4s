package libheaven.core.marc

import scala.collection.mutable.HashMap

class Record (val leader:Leader, val controlFields:List[ControlField], val dataFields:List[DataField]){

  var id:String = null
  var optlock:Int = _
  
  controlFields foreach (x => if (x.isIndentificator) id = x.data)
  assert(id != null, "Id can not be null")
  
  override
  def toString():String  = {
    var result = "\nRecord #"+id+ leader.toString() 
	controlFields foreach (x => result = result + x.toString())
    dataFields foreach (x => result = result + x.toString())
    return result
  }
  
}