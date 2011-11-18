package libheaven.core.marc.rusmarc

import libheaven.core.marc._

class RusmarcRecord (override val leader:Leader, override val controlFields:List[ControlField], override val dataFields:List[DataField]) 
extends Record (leader, controlFields, dataFields) {
  
  override
  def oneLevelDescription:String = {
    var result =  ""
    dataFields foreach (x => {
      if (x.tag.equals("200")) {
        x.subfields foreach (y => {
          if (y.code.equals('a')) {
            result = result + y.data
          }
        })
      }
    })
    result
  }

}