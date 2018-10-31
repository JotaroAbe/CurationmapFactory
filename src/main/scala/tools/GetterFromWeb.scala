package tools

import org.jsoup._

case class GetterFromWeb(url : String){

  val source: nodes.Document = Jsoup.connect(url).get
  val (head, body) = (source.head, source.body)
  val line: String = body.text

  def getInput : String ={
    line
  }


}
