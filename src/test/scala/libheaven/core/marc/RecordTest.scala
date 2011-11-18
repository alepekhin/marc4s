package libheaven.core.marc

import org.junit.Test
import libheaven.core.marc.rusmarc._
import libheaven.core.marc.marc21._

class RecordTest {
  
  @Test
  def test1 = {
    val reader = new MarcReader(getClass.getResourceAsStream("/marc21-utf8-example.iso"), "UTF-8")
    reader.format = MarcFormat.MARC21
    while (reader.hasNext) {
      val record = reader.next
      println(record.oneLevelDescription)
    }
  }

  @Test
  def test2 = {
    val reader = new MarcReader(getClass.getResourceAsStream("/rusmarc-utf8-example.iso"), "UTF-8")
    reader.format = MarcFormat.RUSMARC
    while (reader.hasNext) {
      val record = reader.next
      println(record.oneLevelDescription)
    }
  }

}