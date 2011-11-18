package libheaven.core.marc

class Marc21Record (override val leader:Leader, override val controlFields:List[ControlField], override val dataFields:List[DataField]) 
extends Record (leader, controlFields, dataFields) with MarcRecord {
  
  override
  def oneLevelDescription:String = {
    var result =  ""
    getDataField("245") foreach (x => {
      x.getSubfield('a') foreach (y => result = result + y.data)
      x.getSubfield('b') foreach (y => result = result + y.data)
      x.getSubfield('c') foreach (y => result = result + y.data)
    })
    result
  }

}