package libheaven.core.marc.marc21

import libheaven.core.marc._

class AuthMarker extends Leader {
    
  def codeLevel = implDefined2.charAt(0)
  def codeLevel_=(x:Char):Unit = {implDefined2.update(0,x)}

  def this(status:Char, recType:Char, implDefined1:Array[Char], implDefined2:Array[Char]) {
    this()
    this.status = status
    this.recType = recType
    this.implDefined1 = implDefined1
    this.implDefined2 = implDefined2
  }

}