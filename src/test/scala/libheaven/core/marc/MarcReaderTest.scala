package libheaven.core.marc

import org.junit.Test

class MarcReaderTest {

  @Test
  def test1 = {
    val reader = new MarcReader(getClass.getResourceAsStream("/summerland.mrc"), "UTF-8")
    while (reader.hasNext) {
      val record = reader.next;
    }
  }

  @Test
  def test2 = {
    val reader = new MarcReader(getClass.getResourceAsStream("/academy.iso"), "Cp866")
    var list = List[Record]()
    if (reader.hasNext) {
      val record = reader.next;
    }
  }

}