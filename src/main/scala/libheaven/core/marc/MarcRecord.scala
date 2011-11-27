package libheaven.core.marc

trait MarcRecord {

  def multiLevelDescription(parentRecord: Record): String 
  def analyticalDescription(physicalUnit: Record, parentRecord: Record): String
  def oneLevelDescriptionUnderMainTitle(parentRecord: Record): String
  def oneLevelDescriptionUnderVolumeTitle(parentRecord: Record): String
  
  def removeResume:Record

}