package libheaven.core.marc

class Marc21Record (override val leader:Leader, override val controlFields:List[ControlField], override val dataFields:List[DataField]) 
extends Record (leader, controlFields, dataFields) with MarcRecord {
  
  override def multiLevelDescription(parentRecord: Record): String = {
    throw new Error("Not implemented")
  }
  
  override def analyticalDescription(physicalUnit: Record, parentRecord: Record): String = {
    throw new Error("Not implemented")
  }
  
  override def oneLevelDescriptionUnderMainTitle(parentRecord: Record): String = {
    throw new Error("Not implemented")
  }

  override def oneLevelDescriptionUnderVolumeTitle(parentRecord: Record): String = {
    throw new Error("Not implemented")
  }
  
  override def removeResume:Record = {
    throw new Error("Not implemented")
  }

}