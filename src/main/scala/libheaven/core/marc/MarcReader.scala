package libheaven.core.marc

import org.marc4j.MarcStreamReader
import java.io.InputStream
import java.io.FileInputStream
import java.io.File

class MarcReader(val input: _root_.java.io.InputStream, val encoding: String) {

  assert(input != null, "Input can not be null")

  var format = MarcFormat.RUSMARC

  val reader = new MarcStreamReader(input, if (encoding == null) "UTF-8" else encoding)

  def hasNext = reader.hasNext()

  def next: Record = {
    val r = reader.next();
    val leader = new Leader(
      r.getLeader().getRecordStatus(),
      r.getLeader().getTypeOfRecord(),
      r.getLeader().getImplDefined1(),
      r.getLeader().getImplDefined2())
    var controlFields = List[ControlField]()
    val iterator = r.getControlFields().iterator()
    for (i <- 0 to r.getControlFields().size() - 1) {
      val c = iterator.next().asInstanceOf[_root_.org.marc4j.marc.ControlField]
      val controlField = new ControlField(c.getTag(), c.getData())
      controlFields = controlField :: controlFields
    }
    var dataFields = List[DataField]()
    val iterator2 = r.getDataFields().iterator()
    for (i <- 0 to r.getDataFields().size() - 1) {
      val c = iterator2.next().asInstanceOf[_root_.org.marc4j.marc.DataField]
      val iterator3 = c.getSubfields().iterator()
      var subFields = List[Subfield]()
      for (j <- 0 to c.getSubfields().size() - 1) {
        val s = iterator3.next().asInstanceOf[_root_.org.marc4j.marc.Subfield]
        subFields = new Subfield(s.getCode(), s.getData()) :: subFields
      }
      dataFields = new DataField(c.getTag(), c.getIndicator1(), c.getIndicator2(), subFields) :: dataFields
    }
    if (MarcFormat.MARC21.equals(format)) {
      return new Marc21Record(leader, controlFields, dataFields)
    }
    if (MarcFormat.RUSMARC.equals(format)) {
      return new RusmarcRecord(leader, controlFields, dataFields)
    }
    if (MarcFormat.AUTH.equals(format)) {
      return new AuthRecord(leader, controlFields, dataFields)
    }
    throw new Error("Format "+format+" not accepted")
  }

}