package libheaven.core.marc;

import org.junit.Test
import org.junit.Assert

class AuthTest extends RecordTestBase {

	  @Test
	  def test_name = {
	    val example = authRecord(Array(
	        ("200", ' ', '0',Array(
	          ('a', "Тумина"), 
	          ('b', "Л.Е."), 
	          ('c', "старшая"), 
	          ('f', "1917-1987"),
	          ('g', "Лариса Евгеньевна")
	        ))
	    )) 
	    val expected = "Тумина Л.Е."
	    val actual = example.getName()
	    println(actual)
	    Assert.assertEquals(expected, actual)
	  }

}
