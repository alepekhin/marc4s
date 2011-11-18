package libheaven.core.marc

class RusmarcRecord (override val leader:Leader, override val controlFields:List[ControlField], override val dataFields:List[DataField]) 
extends Record (leader, controlFields, dataFields) with MarcRecord {
  
  override
  def oneLevelDescription:String = {
    var result =  ""
    getDataField("200") foreach (x => {
      x.getSubfield('a') foreach (y => result = result + y.data)
    })
    result
  }

}