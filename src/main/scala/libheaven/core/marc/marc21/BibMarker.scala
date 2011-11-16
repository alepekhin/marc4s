package libheaven.core.marc.marc21

import libheaven.core.marc._

class BibMarker extends Leader {
  
  def bibLevel = implDefined1.charAt(0) // implDefined1 = positions 07-08 
  def bibLevel_=(x:Char):Unit = {implDefined1.update(0,x)}
  def encodingLevel = implDefined2.charAt(0) // implDefined2 = positions 17-19
  def encodingLevel_=(x:Char):Unit = {implDefined2.update(0,x)}
  
  def this(status:Char, recType:Char, implDefined1:Array[Char], implDefined2:Array[Char]) {
    this()
    this.status = status
    this.recType = recType
    this.implDefined1 = implDefined1
    this.implDefined2 = implDefined2

    assert(validStatus.contains(status))
    assert(validTypes.contains(recType))
    assert(validBibLevels.contains(bibLevel))
    assert(validEncodingLevels.contains(encodingLevel))

  }

  val validStatus = Array('a', 'n', 'd', 'c', 'p')
  val validTypes = Array('a', 'c', 'd', 'e', 'f', 'g', 'i', 'j', 'k', 'm', 'o', 'p', 'r', 't')
  val validBibLevels = Array('a', 'i', 'm', 's', 'c', 'd')
  val validEncodingLevels = Array(' ', '1', '2', '3', '5', '7', '8', 'u', 'z')


  override def toString(): String = {
    "\nstatus " + status + ", type " + recType + ", bibLevel " + bibLevel + ", codeLevel " + (if (encodingLevel == ' ') '#' else encodingLevel)
  }

} 
