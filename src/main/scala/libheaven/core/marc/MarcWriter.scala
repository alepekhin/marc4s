package libheaven.core.marc

import org.marc4j.MarcStreamWriter

class MarcWriter(val writer: MarcStreamWriter) {

  def write(record: Record): Unit = {
    val mFactory = new _root_.org.marc4j.marc.impl.MarcFactoryImpl();
    val mrecord = mFactory.newRecord();
    val mleader = mrecord.getLeader()
    mleader.setRecordStatus(record.leader.status)
    mleader.setTypeOfRecord(record.leader.recType)
    mleader.setImplDefined1(record.leader.implDefined1)
    mleader.setImplDefined2(record.leader.implDefined2)

    val cList = mrecord.getControlFields().asInstanceOf[_root_.java.util.List[_root_.org.marc4j.marc.ControlField]]
    record.controlFields foreach (x => {
      cList.add(mFactory.newControlField(x.tag, x.data))
    })

    val dList = mrecord.getDataFields().asInstanceOf[_root_.java.util.List[_root_.org.marc4j.marc.DataField]]
    record.dataFields foreach (x => {
      val df = mFactory.newDataField(x.tag, x.ind1, x.ind2)
      x.subfields foreach (y => {
        df.addSubfield(mFactory.newSubfield(y.code, y.data))
      })
      dList.add(df)
    })

    writer.write(mrecord)

  }
}