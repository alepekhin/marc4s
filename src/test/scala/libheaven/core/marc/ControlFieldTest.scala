package libheaven.core.marc

import org.junit.Test

class TestControlField {

  @Test
  def test1 = {
    val f = new ControlField("001", "data");
  }
  
  @Test
  def test2 = {
    try {
    	val f = new ControlField("000", "data");
    } catch {
      case x:AssertionError =>  // expected
    }
  }
  
  @Test
  def test3 = {
    try {
    	val f = new ControlField("001", "");
    } catch {
      case x:AssertionError =>  // expected
    }
  }
  
  @Test
  def test4 = {
    try {
    	val f = new ControlField("001", "");
    } catch {
      case x:AssertionError =>  // expected
    }
  }
  
}