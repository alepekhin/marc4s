package libheaven.core.marc

class AuthRecord (override val leader:Leader, override val controlFields:List[ControlField], override val dataFields:List[DataField]) 
extends Record (leader, controlFields, dataFields)  {
  
  def getName():String = {
    var result = ""
    dataFields foreach (x => {
      if (x.tag.equals("200")) {
        x.subfields foreach (y => {
          if (y.code.equals('a')) {
            result = result + y.data
          }
        })
      }
    })
    return result
  }

}