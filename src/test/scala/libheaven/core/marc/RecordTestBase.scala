package libheaven.core.marc

class RecordTestBase {

  private def sList(s: Array[Tuple2[Char, String]]): List[Subfield] = {
    var result = List[Subfield]()
    s foreach (x => result = new Subfield(x._1, x._2) :: result)
    result
  }
  
  protected def rusmarcRecord(datafields: Array[Tuple4[String, Char, Char, Array[Tuple2[Char, String]]]]): RusmarcRecord = {
      var controlFields = List[ControlField]()
      var dataFields = List[DataField]()
      datafields foreach (x => dataFields = new DataField(x._1, x._2, x._3, sList(x._4) ) :: dataFields )
      new RusmarcRecord(
        new Leader('n', 'm', Array(' ', ' '), Array(' ', ' ', ' ')),
        new ControlField("001", "ID") :: controlFields,
        dataFields)
    }
  
  protected def authRecord(datafields: Array[Tuple4[String, Char, Char, Array[Tuple2[Char, String]]]]): AuthRecord = {
      var controlFields = List[ControlField]()
      var dataFields = List[DataField]()
      datafields foreach (x => dataFields = new DataField(x._1, x._2, x._3, sList(x._4) ) :: dataFields )
      new AuthRecord(
        new Leader('n', 'm', Array(' ', ' '), Array(' ', ' ', ' ')),
        new ControlField("001", "ID") :: controlFields,
        dataFields)
    }
  
}