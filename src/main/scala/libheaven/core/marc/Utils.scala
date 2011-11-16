package libheaven.core.marc

object Utils {

  def asCSV(s:String):String = {
    if (s == null) return ""
    var result = s.replaceAll("\n", " ").replaceAll("\r", " ")
    if (s.contains("\"")) result = result.replaceAll("\"","\"\"")
    if (s.contains(",")) result = "\""+result+"\""
    result
  }
  
}