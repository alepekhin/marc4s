package libheaven.core.marc.marc21

import libheaven.core.marc._

class Marc21Record (override val leader:Leader, override val controlFields:List[ControlField], override val dataFields:List[DataField]) 
extends Record (leader, controlFields, dataFields) {
  
  override
  def oneLevelDescription:String = {
    var result =  ""
    dataFields foreach (x => {
      if (x.tag.equals("245")) {
        x.subfields foreach (y => {
          if (y.code.equals('a')) {
        	  result = result + y.data
          }
        })
        x.subfields foreach (y => {
          if (y.code.equals('b')) {
        	  result = result + y.data
          }
        })
        x.subfields foreach (y => {
          if (y.code.equals('c')) {
        	  result = result + y.data
          }
        })
      }
    })
    result
  }

}