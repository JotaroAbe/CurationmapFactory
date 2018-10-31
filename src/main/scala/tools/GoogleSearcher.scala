package tools

import com.typesafe.config.ConfigFactory
import org.jsoup.{Jsoup, nodes}

import scala.collection.mutable
import scala.util.matching.Regex



case class GoogleSearcher(query : String) {
  val config = ConfigFactory.load()


  val source: nodes.Document =
    Jsoup.connect(s"https://www.googleapis.com/customsearch/v1?q=$query&cx=${config.getString("csecx")}&key=${config.getString("csekey")}").ignoreContentType(true).get
  val (head, body) = (source.head, source.body)
  val line: String = body.text

  val r: Regex = "\"link\": .*?,".r
  val URLs: Regex.MatchIterator = r.findAllIn(line)



  def getInput : List[String] ={
    val m =  mutable.MutableList.empty[String]
    while(URLs.hasNext){
      val str =  URLs.next()
      m += str.substring(9 ,str.length - 2)
    }
    m.toList

  }


}
