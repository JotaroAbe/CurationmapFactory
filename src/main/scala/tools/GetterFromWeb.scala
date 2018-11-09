package tools

import org.jsoup._
import org.jsoup.nodes.Element

case class GetterFromWeb(url : String){

  val (headOpt: Option[Element], bodyOpt: Option[Element]) = try {
    val source: nodes.Document = Jsoup.connect(url).get
    (Some(source.head), Some(source.body))
  }
  catch{
    case e:Exception =>
      println(s"${url}の文書データを取得できませんでした。")
      (None, None)
  }
  def getInput : String ={
    bodyOpt match {
      case Some(value:Element) => value.text
      case _ => ""
    }
  }


}
