package libheaven.core.marc

import scala.collection.mutable.HashMap

class Record (val leader:Leader, val controlFields:List[ControlField], val dataFields:List[DataField]){

  var id:String = null
  var optlock:Int = _
  
  controlFields foreach (x => if (x.isIndentificator) id = x.data)
  assert(id != null, "Id can not be null")
  
  def getDataField(tag:String):List[DataField] = {
    var result = List[DataField]()
    dataFields foreach (x => {
      if (x.tag.equals(tag)) result = x :: result
    })
    result
  }
  
  override
  def toString():String  = {
    var result = "\nRecord #"+id+ leader.toString() 
	controlFields foreach (x => result = result + x.toString())
    dataFields foreach (x => result = result + x.toString())
    return result
  }
  
}