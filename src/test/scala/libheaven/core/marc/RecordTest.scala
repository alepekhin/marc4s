package libheaven.core.marc

import org.junit.Test

class RecordTest {
  
  @Test
  def test1 = {
    val reader = new MarcReader(getClass.getResourceAsStream("/marc21-utf8-example.iso"), "UTF-8")
    reader.format = MarcFormat.MARC21
    while (reader.hasNext) {
      val record = reader.next.asInstanceOf[Marc21Record]
      println(record.oneLevelDescription)
    }
  }

  @Test
  def test2 = {
    val reader = new MarcReader(getClass.getResourceAsStream("/rusmarc-utf8-example.iso"), "UTF-8")
    reader.format = MarcFormat.RUSMARC
    while (reader.hasNext) {
      val record = reader.next.asInstanceOf[RusmarcRecord]
      println(record.oneLevelDescription)
    }
  }

  @Test
  def test3 = {
    val reader = new MarcReader(getClass.getResourceAsStream("/rusmarc-auth-utf8-example.iso"), "UTF-8")
    reader.format = MarcFormat.AUTH
    while (reader.hasNext) {
      val record = reader.next.asInstanceOf[AuthRecord]
      println(record.getName())
    }
  }

}