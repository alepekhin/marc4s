package libheaven.core.marc

class RusmarcRecord(override val leader: Leader, override val controlFields: List[ControlField], override val dataFields: List[DataField])
  extends Record(leader, controlFields, dataFields) with MarcRecord {

  val REGION_SEPARATOR = ". – "

  override def multiLevelDescription(record461: Record): String = {
    val rec461 = if (record461 != null) record461.asInstanceOf[RusmarcRecord] else this.rec461
    var result = if (rec461 == null) description
    else rec461.description + "\n" + nextLevelDescription
    if (result.endsWith(".")) result
    else result + "."
  }

  override def analyticalDescription(record463: Record, record461: Record): String = {
    val rec463 = if (record463 != null) record463.asInstanceOf[RusmarcRecord] else this.rec463
    val rec461 = if (record461 != null) record461.asInstanceOf[RusmarcRecord] else this.rec461
    val result = description + " // " + (
      if (rec461 == null) {
        new Region(rec463.field200a) concat
          new Region(rec463.field210ad) concat
          new Region(
            if (rec463.volumeNumber.length() > 0) {
              rec463.volumeNumber
            } else {
              if (!parseInlineField("463").isEmpty) parseInlineField("463")(0).volumeNumber else ""
            })
      } else {
        new Region(rec461.field200af) concat
          new Region({
            var s210a = if (rec463.field210a.length() > 0) rec463.field210a else rec461.field210a // место издания
            var s210c = "" //if (rec463.field210c.length() > 0) rec463.field210c else rec461.field210c // издательство
            var s210d = if (rec463.field210d.length() > 0) rec463.field210d else rec461.field210d // год
            val str = s210a +
              (if (s210a.length() > 0 && s210c.length() > 0) " : " + s210c else s210c) +
              (if ((s210a + s210c).length() > 0 && s210d.length() > 0) ", " + s210d else s210d)
            str
          }) concat
          new Region({
            var t = ""
            if (rec461.volumeNumber.length() > 0)
              t = rec461.volumeNumber
            else {
              if (!rec463.parseInlineField("461").isEmpty) t = rec463.parseInlineField("461")(0).volumeNumber
            }
            if (rec463.field200a.length() > 0) {
              if (t.length() > 0) t = t + " : " + rec463.field200a
              else t = rec463.field200a
            }
            t
          }) concat
          new Region(
            if (rec463.volumeNumber.length() > 0) {
              rec463.volumeNumber
            } else {
              if (!parseInlineField("463").isEmpty) parseInlineField("463")(0).volumeNumber else ""
            })
      }).text

    if (result.endsWith(".")) result
    else result + "."
  }

  override def oneLevelDescriptionUnderMainTitle(record461: Record): String = {
    val rec461 = if (record461 != null) record461.asInstanceOf[RusmarcRecord] else this.rec461
    var result = rec461.oneLevelMainDescription + " " + descriptionTom(rec461)
    if (result.endsWith(".")) result
    else result + "."
  }

  override def oneLevelDescriptionUnderVolumeTitle(record461: Record): String = {
    var parent = record461 
    if (record461 == null) {
      if (parseInlineField("461").isEmpty) throw new Error("Field 461 is required")
      parent = parseInlineField("461")(0) 
    }
    val seria = "("+parent.asInstanceOf[RusmarcRecord].field200af+" ; "+rec461.volumeNumber+")"
    var result = new Region(beforeSeria) concat
    new Region(seria) concat
    new Region(afterSeria) text
    
    if (result.endsWith(".")) result
    else result + "."
  }
  
  override def removeResume:Record = {
    var datafields = List[DataField]()
    dataFields foreach (x => {
      if (!x.tag.equals("330")) datafields = x :: datafields 
    })
    new RusmarcRecord(leader, controlFields, datafields.reverse)
  }

  private def description: String = {
    //     Номер тома из встроенного поля
    var volume =
      if (rec461 != null) rec461.volumeNumber
      else if (rec462 != null) rec462.volumeNumber
      else ""
    if (field200a.length() > 0 && volume.length() > 0) volume = volume + " : "
    // Область заглавия и сведений об ответственности
    var result = new Region(volume + field200) concat
      // Область издания
      new Region(field205) concat
      // Область специфических сведений
      new Region(field206) concat
      new Region(field207) concat
      new Region(field208) concat
      new Region(field229) concat
      new Region(field230) concat
      // Область выходных данных
      new Region(field210) concat
      // Область физической характеристики
      new Region(field215) concat
      // Область серии
      new Region(seria) concat
      // Область примечания
      new Region(field300) concat
      new Region(field301) concat
      new Region(field302) concat
      new Region(field305) concat
      new Region(field309) concat
      new Region(field311) concat
      new Region(field313) concat
      new Region(field316) concat
      new Region(field320) concat
      new Region(field321) concat
      new Region(field326) concat
      new Region(field327) concat
      new Region(field333) concat
      new Region(field336) concat
      new Region(field337) concat
      // Область стандартного номера
      new Region(field10s9) concat // тираж
      new Region(field10) concat
      new Region(field71) text
      
      result + field330
  }

  private def descriptionTom(parent: RusmarcRecord): String = {
    //     Номер тома из встроенного поля
    var volume =
      if (rec461 != null) rec461.volumeNumber
      else if (rec462 != null) rec462.volumeNumber
      else ""
    if (volume.length() > 0 && !volume.endsWith(".")) volume = volume + "."
    // Область заглавия и сведений об ответственности
    if (field200a.length() > 0) volume = volume + " "
    var result = new Region(volume + field200(parent)) concat
      // Область издания
      new Region(field205) concat
      // Область специфических сведений
      new Region(field206) concat
      new Region(field207) concat
      new Region(field208) concat
      new Region(field229) concat
      new Region(field230) concat
      // Область выходных данных
      new Region(field210(parent)) concat
      // Область физической характеристики
      new Region(field215) concat
      // Область серии
      new Region(seria) concat
      // Область примечания
      new Region(field300) concat
      new Region(field301) concat
      new Region(field302) concat
      new Region(field305) concat
      new Region(field309) concat
      new Region(field311) concat
      new Region(field313) concat
      new Region(field316) concat
      new Region(field320) concat
      new Region(field321) concat
      new Region(field326) concat
      new Region(field327) concat
      new Region(field333) concat
      new Region(field336) concat
      new Region(field337) concat
      // Область стандартного номера
      new Region(field10s9) concat // тираж
      new Region(field10) concat
      new Region(field71) text
      
      result + field330
  }

  private def nextLevelDescription: String = {
    //     Номер тома из встроенного поля
    var volume =
      if (rec461 != null) rec461.volumeNumber
      else if (rec462 != null) rec462.volumeNumber
      else if (rec463 != null) rec463.volumeNumber
      else ""
    if (field200a.length() > 0 && volume.length() > 0) volume = volume + " : "
    // Область заглавия и сведений об ответственности

    var result = new Region(volume + field200) concat
      //    new Region(field200) concat
      // Область издания
      new Region(field205) concat
      // Область специфических сведений
      new Region(field206) concat
      new Region(field207) concat
      new Region(field208) concat
      new Region(field229) concat
      new Region(field230) concat
      // Область выходных данных
      new Region(field210d) concat
      // Область физической характеристики
      new Region(field215short) concat
      // Область серии
      new Region(seria) concat
      // Область примечания
      new Region(field300) concat
      new Region(field301) concat
      new Region(field302) concat
      new Region(field305) concat
      new Region(field309) concat
      new Region(field311) concat
      new Region(field313) concat
      new Region(field316) concat
      new Region(field320) concat
      new Region(field321) concat
      new Region(field326) concat
      new Region(field327) concat
      new Region(field333) concat
      new Region(field336) concat
      new Region(field337) concat
      // Область стандартного номера
      new Region(field10s9) concat // тираж
      new Region(field10) concat
      new Region(field71) text
      
      result + field330
  }

  private def beforeSeria: String = {
    // Область заглавия и сведений об ответственности
    new Region(field200) concat
      //    new Region(field200) concat
      // Область издания
      new Region(field205) concat
      // Область специфических сведений
      new Region(field206) concat
      new Region(field207) concat
      new Region(field208) concat
      new Region(field229) concat
      new Region(field230) concat
      // Область выходных данных
      new Region(field210) concat
      // Область физической характеристики
      new Region(field215) text
  }

  private def afterSeria: String = {
      // Область примечания
      var result = new Region(field300) concat
      new Region(field301) concat
      new Region(field302) concat
      new Region(field305) concat
      new Region(field309) concat
      new Region(field311) concat
      new Region(field313) concat
      new Region(field316) concat
      new Region(field320) concat
      new Region(field321) concat
      new Region(field326) concat
      new Region(field327) concat
      new Region(field333) concat
      new Region(field336) concat
      new Region(field337) concat
      // Область стандартного номера
      new Region(field10s9) concat // тираж
      new Region(field10) concat
      new Region(field71) text
      
      result + field330
  }

  private def oneLevelMainDescription: String = {
    field200ade
  }

  private def rec461: RusmarcRecord = {
    if (!parseInlineField("461").isEmpty) parseInlineField("461")(0)
    else {
      if (rec463 != null) rec463.rec461
      else null
    }
  }

  private def rec462: RusmarcRecord = {
    if (!parseInlineField("462").isEmpty) parseInlineField("462")(0)
    else null
  }

  private def rec463: RusmarcRecord = {
    if (!parseInlineField("463").isEmpty) parseInlineField("463")(0)
    else null
  }

  // Область заглавия и сведений об ответственности
  private def field200: String = {
    var result = ""
    getDataField("200") foreach (x => {
      if (x.ind1.equals('1')) result = result + out(x.getSubfield('a'), " ; ", "")
      if (!x.getSubfield('b').isEmpty) {
        result = result + " [" + out(x.getSubfield('b'), ", ", "") + "]"
      }
      result = result + out(x.getSubfield('h'), ". ")
      if (x.getSubfield('h').isEmpty) {
        result = result + out(x.getSubfield('i'), ", ", ". ")
      } else {
        result = result + out(x.getSubfield('i'), ", ")
      }
      result = result +
        out(x.getSubfield('d'), " = ") +
        out(x.getSubfield('e'), " : ") +
        out(x.getSubfield('f'), " / ") +
        out(x.getSubfield('g'), " ; ")
    })
    parseInlineField("423") foreach (record => {
      result = result +
        { if (result.endsWith(".")) " " else ". " } +
        record.field200
    })
    result
  }

  private def field200(rec461: RusmarcRecord): String = {
    var result = ""
    getDataField("200") foreach (x => {
      if (x.ind1.equals('1')) result = result + out(x.getSubfield('a'), " ; ", "")
      if (!x.getSubfield('b').isEmpty) {
        result = result + " [" + out(x.getSubfield('b'), ", ", "") + "]"
      }
      result = result + out(x.getSubfield('h'), ". ")
      if (x.getSubfield('h').isEmpty) {
        result = result + out(x.getSubfield('i'), ", ", ". ")
      } else {
        result = result + out(x.getSubfield('i'), ", ")
      }
      result = result +
        out(x.getSubfield('d'), " = ") +
        out(x.getSubfield('e'), " : ")
      var fList = List[Subfield]()
      x.getSubfield('f') foreach (z => {
        fList = new Subfield('f', z.data) :: fList
      })
      if (rec461 != null) {
        rec461.getDataField("200") foreach (y => {
          y.getSubfield('f') foreach (z => {
            fList = new Subfield('f', z.data) :: fList
          })
        })
      }
      var resp = out(fList, " ; ")
      if (resp.length() > 3) resp = " / " + resp.substring(3)
      result = result + resp +
        out(x.getSubfield('g'), " ; ")
    })
    parseInlineField("423") foreach (record => {
      result = result +
        { if (result.endsWith(".")) " " else ". " } +
        record.field200
    })
    result
  }

  private def field200ade: String = {
    var result = ""
    getDataField("200") foreach (x => {
      if (x.ind1.equals('1')) result = result + out(x.getSubfield('a'), " ; ", "")
      if (!x.getSubfield('b').isEmpty) {
        result = result + " [" + out(x.getSubfield('b'), ", ", "") + "]"
      }
      var eList = List[Subfield]()
      x.getSubfield('e') foreach (x => {
        eList = new Subfield('e', x.data.charAt(0).toUpperCase + x.data.substring(1)) :: eList
      })
      result = result +
        out(x.getSubfield('d'), " = ") +
        out(eList.reverse, ". ")
    })
    if (result.endsWith(".")) result
    else result + "."
  }

  private def field200ab: String = {
    var result = ""
    getDataField("200") foreach (x => {
      if (x.ind1.equals('1')) result = result + out(x.getSubfield('a'), " ; ", "")
      if (!x.getSubfield('b').isEmpty) {
        result = result + " [" + out(x.getSubfield('b'), ", ", "") + "]"
      }
    })
    result
  }

  private def field200af: String = {
    var result = ""
    getDataField("200") foreach (x => {
      if (x.ind1.equals('1')) result = result + out(x.getSubfield('a'), " ; ", "")
      result = result + responsibility
      parseInlineField("461") foreach (x => {
        // add responsibilty from high level
        result = result + x.responsibility
      })
    })
    parseInlineField("423") foreach (record => {
      result = result +
        { if (result.endsWith(".")) " " else ". " } +
        record.field200
    })
    result
  }
  private def field200a: String = {
    var result = ""
    getDataField("200") foreach (x => {
      if (x.ind1.equals('1')) result = result + out(x.getSubfield('a'), " ; ", "")
    })
    result
  }

  private def responsibility: String = {
    var result = ""
    getDataField("200") foreach (x => {
      result = result + out(x.getSubfield('f'), " / ")
    })
    result
  }

  // Область издания
  private def field205: String = {
    var result = ""
    getDataField("205") foreach (x => {
      result = result + out(x.getSubfield('a'), " ; ", "")
      result = result + out(x.getSubfield('d'), " = ")
      result = result + out(x.getSubfield('f'), " / ")
      result = result + out(x.getSubfield('g'), " ; ")
      result = result + out(x.getSubfield('b'), ", ")
    })
    result
  }
  // Область специфических сведений
  private def field206: String = {
    var result = ""
    getDataField("206") foreach (x => {
      result = result + out(x.getSubfield('a'), ", ", "")
      result = result + out(x.getSubfield('b'), " ; ")
    })
    result
  }
  private def field207: String = {
    var result = ""
    getDataField("207") foreach (x => {
      result = result + out(x.getSubfield('a'), ", ", "")
    })
    result
  }
  private def field208: String = {
    var result = ""
    getDataField("208") foreach (x => {
      result = result + out(x.getSubfield('a'), ", ", "")
      result = result + out(x.getSubfield('d'), " ; ")
    })
    result
  }
  private def field229: String = {
    var result = ""
    getDataField("229") foreach (x => {
      result = result + out(x.getSubfield('a'), ", ", "")
    })
    result
  }
  private def field230: String = {
    var result = ""
    getDataField("230") foreach (x => {
      result = result + out(x.getSubfield('a'), ", ", "")
    })
    result
  }
  // Область выходных данных
  private def field210: String = {
    var result = ""
    getDataField("210") foreach (x => {
      result = result + out(x.getSubfield('a'), " ; ", "")
      result = result + out(x.getSubfield('c'), " : ")
      result = result + out(x.getSubfield('d'), ", ",
        if (x.getSubfield('a').isEmpty && x.getSubfield('c').isEmpty) "" else ", ")
      result = result + out(x.getSubfield('e'), " ; ")
      result = result + out(x.getSubfield('g'), " : ")
      result = result + out(x.getSubfield('h'), ", ")
    })
    result
  }

  private def field210(rec461: RusmarcRecord): String = {
    var result = ""
    var aList = List[Subfield]()
    var cList = List[Subfield]()
    var dList = List[Subfield]()
    var eList = List[Subfield]()
    var gList = List[Subfield]()
    var hList = List[Subfield]()
    getDataField("210") foreach (x => {
        x.getSubfield('a') foreach (y => aList = new Subfield('a', y.data) :: aList)
        x.getSubfield('c') foreach (y => cList = new Subfield('c', y.data) :: cList)
        x.getSubfield('d') foreach (y => dList = new Subfield('d', y.data) :: dList)
        x.getSubfield('e') foreach (y => eList = new Subfield('e', y.data) :: eList)
        x.getSubfield('g') foreach (y => gList = new Subfield('f', y.data) :: gList)
        x.getSubfield('h') foreach (y => hList = new Subfield('h', y.data) :: hList)
      })
    if (rec461 != null) {
      rec461.getDataField("210") foreach (x => {
        if (aList.isEmpty) x.getSubfield('a') foreach (y => aList = new Subfield('a', y.data) :: aList)
        if (cList.isEmpty) x.getSubfield('c') foreach (y => cList = new Subfield('c', y.data) :: cList)
        if (dList.isEmpty) x.getSubfield('d') foreach (y => dList = new Subfield('d', y.data) :: dList)
        if (eList.isEmpty) x.getSubfield('e') foreach (y => eList = new Subfield('e', y.data) :: eList)
        if (gList.isEmpty) x.getSubfield('g') foreach (y => gList = new Subfield('f', y.data) :: gList)
        if (hList.isEmpty) x.getSubfield('h') foreach (y => hList = new Subfield('h', y.data) :: hList)
      })
    }
      result = result + out(aList, " ; ", "")
      result = result + out(cList, " : ")
      result = result + out(dList, ", ",
        if (aList.isEmpty && cList.isEmpty) "" else ", ")
      result = result + out(eList, " ; ")
      result = result + out(gList, " : ")
      result = result + out(hList, ", ")
    result
  }

  private def field210ad: String = {
    var result = ""
    getDataField("210") foreach (x => {
      result = result + out(x.getSubfield('a'), " ; ", "")
      result = result + out(x.getSubfield('d'), ", ",
        if (x.getSubfield('a').isEmpty && x.getSubfield('c').isEmpty) "" else ", ")
    })
    result
  }

  private def field210a: String = {
    var result = ""
    getDataField("210") foreach (x => {
      result = result + out(x.getSubfield('a'), " ; ", "")
    })
    result
  }

  private def field210c: String = {
    var result = ""
    getDataField("210") foreach (x => {
      result = result + out(x.getSubfield('c'), " : ", "")
    })
    result
  }
  private def field210d: String = {
    var result = ""
    getDataField("210") foreach (x => {
      result = result + out(x.getSubfield('d'), ", ", "")
    })
    result
  }
  // Область физической характеристики
  private def field215: String = {
    var result = ""
    getDataField("215") foreach (x => {
      result = result + out(x.getSubfield('a'), " ; ", "")
      result = result + out(x.getSubfield('c'), " : ")
      result = result + out(x.getSubfield('d'), " ; ")
      result = result + out(x.getSubfield('e'), " + ")
    })
    result
  }

  private def field215short: String = {
    var result = ""
    getDataField("215") foreach (x => {
      result = result + out(x.getSubfield('a'), " ; ", "")
      result = result + out(x.getSubfield('c'), " : ")
      result = result + out(x.getSubfield('e'), " + ")
    })
    result
  }

  // Область примечания
  private def field300: String = {
    var result = ""
    getDataField("300") foreach (x => { result = (if (result.endsWith(".")) result.substring(0, result.length()-1) else result) + out(x.getSubfield('a'), REGION_SEPARATOR) })
    if (result.length() > 3) result.substring(4) else result
  }
  private def field301: String = {
    var result = ""
    getDataField("301") foreach (x => { result = result + out(x.getSubfield('a'), REGION_SEPARATOR) })
    if (result.length() > 3) result.substring(4) else result
  }
  def field302: String = {
    var result = ""
    getDataField("302") foreach (x => { result = result + out(x.getSubfield('a'), REGION_SEPARATOR) })
    if (result.length() > 3) result.substring(4) else result
  }
  private def field305: String = {
    var result = ""
    getDataField("305") foreach (x => { result = result + out(x.getSubfield('a'), REGION_SEPARATOR) })
    if (result.length() > 3) result.substring(4) else result
  }
  private def field309: String = {
    var result = ""
    getDataField("309") foreach (x => { result = result + out(x.getSubfield('a'), REGION_SEPARATOR) })
    if (result.length() > 3) result.substring(4) else result
  }
  private def field311: String = {
    var result = ""
    getDataField("311") foreach (x => { result = result + out(x.getSubfield('a'), REGION_SEPARATOR) })
    if (result.length() > 3) result.substring(4) else result
  }
  private def field313: String = {
    var result = ""
    getDataField("313") foreach (x => { result = result + out(x.getSubfield('a'), REGION_SEPARATOR) })
    if (result.length() > 3) result.substring(4) else result
  }
  private def field316: String = {
    var result = ""
    getDataField("316") foreach (x => { result = result + out(x.getSubfield('a'), REGION_SEPARATOR) })
    if (result.length() > 3) result.substring(4) else result
  }
  private def field320: String = {
    var result = ""
    getDataField("320") foreach (x => { result = result + out(x.getSubfield('a'), REGION_SEPARATOR) })
    if (result.length() > 3) result.substring(4) else result
  }
  private def field321: String = {
    var result = ""
    getDataField("321") foreach (x => { result = result + out(x.getSubfield('a'), REGION_SEPARATOR) })
    if (result.length() > 3) result.substring(4) else result
  }
  def field326: String = {
    var result = ""
    getDataField("326") foreach (x => { result = result + out(x.getSubfield('a'), REGION_SEPARATOR) })
    if (result.length() > 3) result.substring(4) else result
  }
  private def field327: String = {
    var result = ""
    getDataField("327") foreach (x => { result = result + out(x.getSubfield('a'), REGION_SEPARATOR) })
    if (result.length() > 3) result.substring(4) else result
  }
  private def field330: String = {
    var result = ""
    getDataField("330") foreach (x => { result = result + out(x.getSubfield('a'), "\n") })
    result
  }
  private def field333: String = {
    var result = ""
    getDataField("333") foreach (x => { result = result + out(x.getSubfield('a'), REGION_SEPARATOR) })
    if (result.length() > 3) result.substring(4) else result
  }
  def field336: String = {
    var result = ""
    getDataField("336") foreach (x => { result = result + out(x.getSubfield('a'), REGION_SEPARATOR) })
    if (result.length() > 3) result.substring(4) else result
  }
  private def field337: String = {
    var result = ""
    getDataField("337") foreach (x => { result = result + out(x.getSubfield('a'), REGION_SEPARATOR) })
    if (result.length() > 3) result.substring(4) else result
  }
  // Область стандартного номера
  private def field10s9: String = {
    var result = ""
    getDataField("010") foreach (x => { result = result + out(x.getSubfield('9'), ", ", "") })
    getDataField("011") foreach (x => { result = result + out(x.getSubfield('9'), ", ", "") })
    result
  }
  private def field10: String = {
    var result = ""
    getDataField("010") foreach (x => {
      if (!x.getSubfield('a').isEmpty)
        result = result + out(x.getSubfield('a'), ", ", "ISBN ")
    })
    getDataField("011") foreach (x => {
      if (!x.getSubfield('a').isEmpty)
        result = result + out(x.getSubfield('a'), ", ", "ISSN ")
    })
    getDataField("010") foreach (x => {
      if (!x.getSubfield('b').isEmpty) {
        result = result + out(x.getSubfield('b'), ", ", "(")
        result = result + ")"
      }
    })
    getDataField("011") foreach (x => {
      if (!x.getSubfield('b').isEmpty) {
        result = result + out(x.getSubfield('b'), ", ", "(")
        result = result + ")"
      }
    })
    getDataField("010") foreach (x => {
      if (!x.getSubfield('d').isEmpty) {
        result = result + out(x.getSubfield('d'), " : ")
      }
    })
    getDataField("011") foreach (x => {
      if (!x.getSubfield('d').isEmpty) {
        result = result + out(x.getSubfield('d'), " : ")
      }
    })
    result
  }

  private def field71: String = {
    var result = ""
    getDataField("071") foreach (x => {
      var descr = ""
   	  if (x.ind1.equals('0')) descr = "Н.е. " 
   	  if (x.ind1.equals('1')) descr = "Н.м. " 
   	  if (x.ind1.equals('2')) descr = "Н.д. " 
   	  if (x.ind1.equals('5')) descr = "Др. "
   	  x.getSubfield('a') foreach (y =>{
   	    result = result + descr + y.data
   	  })
    })
    result
  }
  
  private def out(subfields: List[Subfield], delimeter: String): String = {
    var result = new Element("", delimeter)
    subfields foreach (x => result = result concat new Element(x.data, delimeter))
    result.text
  }

  private def out(subfields: List[Subfield], delimeter: String, firstDelimeter: String): String = {
    var result = ""
    var i = 1
    subfields foreach (y => {
      if (y.data.length() > 0) {
        if (i == 1) {
          if (firstDelimeter.length() > 0) {
            if (result.endsWith(firstDelimeter.substring(0, 1))) result = result + firstDelimeter.substring(1)
            else result = result + firstDelimeter
          } else {
            result = result + firstDelimeter
          }
        } else {
          if (delimeter.length() > 0) {
            if (result.endsWith(delimeter.substring(0, 1))) result = result + delimeter.substring(1)
            else result = result + delimeter
          } else {
            result = result + delimeter
          }
        }
        result = result + y.data
        i = i + 1
      }
    })
    result
  }

  private def seria: String = {
    var region = ""
    var i = 0
    getDataField("225") foreach (x => {
      var result = ""
      x.getSubfield('a') foreach (y => {
        result = result + y.data
      })
      x.getSubfield('d') foreach (y => {
        result = result + " = " + y.data
      })
      x.getSubfield('e') foreach (y => {
        result = result + " : " + y.data
      })
      x.getSubfield('f') foreach (y => {
        result = result + " / " + y.data
      })
      var hasH = false
      x.getSubfield('h') foreach (y => {
        if (result.endsWith(".")) result = result + " " + y.data
        else result = result + ". " + y.data
        hasH = true
      })
      x.getSubfield('i') foreach (y => {
        if (hasH) {
          if (result.endsWith(",")) result = result + " " + y.data
          else result = result + ", " + y.data
        } else {
          if (result.endsWith(".")) result = result + " " + y.data
          else result = result + ". " + y.data
        }
      })
      x.getSubfield('x') foreach (y => {
        if (result.endsWith(",")) result = result + " " + y.data
        else result = result + ", " + y.data
      })
      x.getSubfield('v') foreach (y => {
        result = result + " ; " + y.data
      })
      if (result.length() > 0) result = { if (i == 0) "(" else " (" } + result + ")"
      region = region + result
      i = i + 1
    })
    region;
  }

  private def volumeNumber: String = {
    var result = ""
    getDataField("200") foreach (x => {
      x.getSubfield('v') foreach (y => {
        result = y.data
      })
    })
    result
  }

  private def parseInlineField(tag: String): List[RusmarcRecord] = {
    var result = List[RusmarcRecord]()
    getDataField(tag) foreach (x => {
      val leader = new Leader('n', 'm', Array(' ', ' '), Array(' ', ' ', ' '))
      var controlfields = List[ControlField]()
      var datafields = List[DataField]()
      x.subfields foreach (y => {
        val terms = y.data.split("\\$")
        assert(terms.size >= 1, "Invalid inline data " + y.data + " size=" + terms.size + " 0:" + terms(0))
        assert(terms(0).length() >= 3, "Invalid inline tag " + y.data)
        val tag = terms(0).substring(0, 3)
        if (tag.startsWith("00")) {
          controlfields = new ControlField(tag, terms(0).substring(3)) :: controlfields
        } else {
          assert(terms(0).length() == 5, "Invalid inline tag " + y.data)
          var ind1 = terms(0).charAt(3)
          var ind2 = terms(0).charAt(4)
          if (ind1.equals('#')) ind1 = ' '
          if (ind2.equals('#')) ind2 = ' '
          var subfields = List[Subfield]()
          var i = 1;
          while (i < terms.length) {
            subfields = new Subfield(terms(i).charAt(0), terms(i).substring(1)) :: subfields
            i = i + 1
          }
          datafields = new DataField(tag, ind1, ind2, subfields) :: datafields
        }
      })
      if (controlfields.isEmpty) controlfields = new ControlField("001", "Inline") :: controlfields
      result = new RusmarcRecord(leader, controlfields, datafields) :: result
    })
    result
  }

  private class Element(val text: String, val separator: String) {
    def concat(other: Element): Element = {
      if (other.text.length() > 0) {
        if (text.length() == 0) return new Element(separator + other.text, separator)
        assert(separator.length() > 0, "Empty separator")
        if (text.endsWith(separator.substring(0, 1))) {
          new Element(text + separator.substring(1) + other.text, separator)
        } else {
          new Element(text + separator + other.text, separator)
        }
      } else new Element(separator + this, separator)
    }
  }

  private class Region(val text: String) {
    def concat(other: Region): Region = {
      if (other.text.length() > 0) {
        if (this.text.length() == 0) return other;
        if (text.endsWith(".")) {
          new Region(text + " – " + other.text)
        } else {
          new Region(text + REGION_SEPARATOR + other.text)
        }
      } else this
    }
  }

}