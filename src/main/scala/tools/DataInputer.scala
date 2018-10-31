package tools

import models.{CurationMap, Document, Fragment, Morpheme}

import scala.collection.mutable

import us.feliscat.text.analyzer.mor.mecab.IpadicMecab



import us.feliscat.text.StringOption

case class DataInputer(query : String, sourceList : List[String]){

  val docList = mutable.MutableList.empty[Document]
  println("形態素解析中...")

  var i :Int = 0
  sourceList.foreach {
    source =>
      i += 1
      println(s"$i / ${sourceList.length}")

      val fragList = mutable.MutableList.empty[Fragment]
      val docStr: String = GetterFromWeb(source).getInput
      val queue: mutable.MutableList[Morpheme] = mutable.MutableList.empty[Morpheme]
      docStr.split("。").foreach {
        str =>
          if(!str.isEmpty) {
            IpadicMecab.analyze(StringOption(str + "。")).foreach {
              mor =>
                //println(mor)
                val m = Morpheme(mor.split("\t").head, mor.split("\t").last)
                if (m.morph != "EOS") {
                  queue += m
                  if (queue.last.getSubPartsOfSpeech == "句点") {
                    fragList += Fragment(queue.toVector)
                    //println(doc.fragList.last.getText())
                    queue.clear()
                  }
                }
            }
          }
      }
      val doc: Document = Document(source, fragList.toVector, sourceList.indexOf(source))
      doc.setDocNumToFrag()

      docList += doc
  }



  def gethasntLinkCurationMap : CurationMap={
    CurationMap(query, docList.toVector)
  }
}
