package tools

import models.{CurationMap, Document, Fragment, Morpheme}
import org.apache.lucene.search.spell.LevensteinDistance

import scala.collection.mutable
import us.feliscat.text.analyzer.mor.mecab.IpadicMecab

import scala.util.control.Breaks
import us.feliscat.text.StringOption

case class DataInputer(){

  val docList: mutable.MutableList[Document] = mutable.MutableList.empty[Document]

  def inputWebData(query : String, sourceList : List[String]): CurationMap ={
    println("Web文書読み込み開始")
    var i :Int = 0
    sourceList.foreach {
      source =>
        if(source.nonEmpty) {
          i += 1
          println(s"$i / ${sourceList.length}")
          println(s"source URL:$source")
          val fragList = mutable.MutableList.empty[Fragment]
          val webData = GetterFromWeb(source)
          val docStrList: List[String] = webData.getBodyTexts
          val docTitle: String = webData.getTitle
          if(docStrList.nonEmpty) {
            val queue: mutable.MutableList[Morpheme] = mutable.MutableList.empty[Morpheme]
            println("形態素解析中...")
            docStrList.foreach {
              docStr =>
                val b = new Breaks
                b.breakable{
                  docStr.split("。").foreach {
                    str =>
                      if (str.nonEmpty && str.length < 10000 ) {
                        println(str + "\n")
                        IpadicMecab.analyze(StringOption(str + "。")).foreach {
                          mor =>
                            //println(mor)
                            val m = Morpheme(mor.split("\t").head, mor.split("\t").last)
                            if (m.morph != "EOS") {
                              queue += m
                              /*if (queue.last.getSubPartsOfSpeech == "句点" ) {
                                if(queue.size > 5){//短い＝意味ない
                                  fragList += Fragment(queue.toVector)
                                }
                                //println(doc.fragList.last.getText())
                                queue.clear()
                              }else */ if (queue.size > 255) { //長すぎもだめ
                                queue.clear()
                                b.break()
                              }
                            }
                        }
                        if(queue.size > 5){//短い＝意味ない
                          fragList += Fragment(queue.toVector)
                        }
                        queue.clear()
                      }
                  }
                }
            }
            val doc: Document = Document(source, docTitle, fragList.toVector, sourceList.indexOf(source))
            doc.setDocNumToFrag()

            if(!hasSimDoc(doc)){
              docList += doc
            }
          }
        }
    }
    CurationMap(query, docList.toVector, CurationMap.DEFAULT_ALPHA, CurationMap.DEFAULT_BETA)
  }

  private def hasSimDoc(document: Document):Boolean ={
    var ret = false
    val distance = new LevensteinDistance
    docList.foreach{
      otherDoc =>
        val score :Double = distance.getDistance(document.getText, otherDoc.getText)
        if(score > 0.5){
          ret = true
        }
    }
    ret
  }

}
