package libheaven.core.marc

class Leader {

  var status:Char = _ 
  var recType:Char = _ 
  var implDefined1:Array[Char] = _ 
  var implDefined2:Array[Char] = _
  
  def this(status:Char, recType:Char, implDefined1:Array[Char], implDefined2:Array[Char]) {
    this()
    this.status = status
    this.recType = recType
    this.implDefined1 = implDefined1
    this.implDefined2 = implDefined2
    
    assert(implDefined1.size == 2, "Incorrect implDefined1 length")
    assert(implDefined2.size == 3, "Incorrect implDefined2 length")
  }


  override def toString(): String = {
    "\nstatus " + status + ", type " + recType + ", implDefined1 " + 
    		(if (implDefined1.charAt(0) == ' ') '#' else  implDefined1.charAt(0)) + 
    		(if (implDefined1.charAt(1) == ' ') '#' else  implDefined1.charAt(1)) + 
    		", implDefined2 " + 
    		(if (implDefined2.charAt(0) == ' ') '#' else  implDefined2.charAt(0)) + 
    		(if (implDefined2.charAt(1) == ' ') '#' else  implDefined2.charAt(1)) + 
    		(if (implDefined2.charAt(2) == ' ') '#' else  implDefined2.charAt(2))  
  }

}