package libheaven.core.marc.rusmarc

import libheaven.core.marc._

class BibMarker extends Leader {
  
  def bibLevel = implDefined1.charAt(0) // implDefined1 = positions 07-08 
  def bibLevel_=(x:Char):Unit = {implDefined1.update(0,x)}
  def hierarchyCode = implDefined1.charAt(1)
  def hierarchyCode_=(x:Char):Unit = {implDefined1.update(1,x)}
  def codeLevel = implDefined2.charAt(0) // implDefined2 = positions 17-19
  def codeLevel_=(x:Char):Unit = {implDefined2.update(0,x)}
  
  def this(status:Char, recType:Char, implDefined1:Array[Char], implDefined2:Array[Char]) {
    this()
    this.status = status
    this.recType = recType
    this.implDefined1 = implDefined1
    this.implDefined2 = implDefined2

    assert(validStatus.contains(status))
    assert(validTypes.contains(recType))
    assert(validBibLevels.contains(bibLevel))
    assert(validHierarchyCodes.contains(hierarchyCode))
    assert(validCodeLevels.contains(codeLevel))

  }

  val validStatus = Array('n', 'd', 'c')
  val validTypes = Array('a', 'b', 'c', 'd', 'e', 'f', 'g', 'i', 'j', 'k', 'l', 'm', 'r')
  val validBibLevels = Array('a', 'i', 'm', 's', 'c')
  val validHierarchyCodes = Array(' ', '0', '1', '2')
  val validCodeLevels = Array(' ', '0', '1', '2', '3')


  override def toString(): String = {
    "\nstatus " + status + ", type " + recType + ", bibLevel " + bibLevel + ", codeLevel " + (if (codeLevel == ' ') '#' else codeLevel + ", hierarchyCode " + (if (hierarchyCode == ' ') '#' else hierarchyCode))
  }

} 
