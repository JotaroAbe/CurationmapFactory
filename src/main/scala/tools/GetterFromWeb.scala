package tools

import org.jsoup._
import org.jsoup.nodes.Document
import org.jsoup.nodes.Document.OutputSettings
import org.jsoup.safety.Whitelist

import scala.collection.mutable

case class GetterFromWeb(url : String){

  val sourceOpt: Option[nodes.Document] = try {
    Some(Jsoup.connect(url).get)
  }
  catch{
    case e:Exception =>
      println(s"${url}の文書データを取得できませんでした。")
      None
  }
  def getBodyTexts : List[String] ={
    sourceOpt match {
      case Some(doc: nodes.Document) =>
        val ret = mutable.MutableList.empty[String]
        try {
          cleanPreserveLineBreaks(doc.body().html).foreach {
            line =>
              ret += line.trim
          }
        }catch {
          case e:Exception =>
            println(s"${url}の文書データをパースできませんでした。")
            Nil
        }
       ret.toList
      case _ => Nil
    }
  }



  def cleanPreserveLineBreaks(bodyHtml: String): List[String] = { // get pretty printed html with preserved br and p tags
    val prettyPrintedBodyFragment = Jsoup.clean(bodyHtml, "", Whitelist.none.addTags("br", "p"), new OutputSettings().prettyPrint(true))

    val a: Document = Jsoup.parse(prettyPrintedBodyFragment)

    //a.select("ul").remove()
    //a.select("table").remove()
    // get plain text with preserved line breaks by disabled prettyPrint
    Jsoup.clean(a.html, "", Whitelist.none, new OutputSettings().prettyPrint(false)).split("\n").toList
  }

  def getTitle: String={
    sourceOpt match {
      case Some(value: nodes.Document) => value.title()
      case _ => ""
    }
  }


}
