package libheaven.core.marc

class AuthRecord (override val leader:Leader, override val controlFields:List[ControlField], override val dataFields:List[DataField]) 
extends Record (leader, controlFields, dataFields)  {
  
  def getName():String = {
    var result = ""
    getDataField("200") foreach (x => {
      x.getSubfield('a') foreach (y => {
        result = result + y.data
      })
      x.getSubfield('b') foreach (y => {
        result = result + " " + y.data
      })
    })
    return result
  }

}