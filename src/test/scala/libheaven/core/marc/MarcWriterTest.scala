package libheaven.core.marc

import org.junit.Test
import java.io.FileInputStream
import java.io.FileOutputStream
import org.marc4j.MarcStreamWriter

class MarcWriterTest {

  @Test
  def test1 = {
    val reader = new MarcReader(getClass.getResourceAsStream("/rusmarc-utf8-example.iso"), "UTF-8")
    if (reader.hasNext) {
      val record = reader.next;
      val output = new FileOutputStream("target/rusmarc-utf8-example-output.iso")
      val writer = new MarcWriter(new MarcStreamWriter(output, "UTF-8"))
      writer.write(record)
      output.close()
      val reader2 = new MarcReader(new FileInputStream("target/rusmarc-utf8-example-output.iso"), "UTF-8")
      if (reader2.hasNext) {
        val record2 = reader2.next
      }
    }
  }

}