package models

import java.util.UUID

import scala.collection.immutable.List
import scala.collection.mutable

case class Fragment (morphList: Vector[Morpheme]){

  var docNum : Int = Document.docNumNone
  val uuid :UUID = UUID.randomUUID
  var links: mutable.MutableList[InclusiveLink] = mutable.MutableList.empty[InclusiveLink]

  def getText: String ={
    var ret :String = ""
    morphList.foreach{
      morph =>
        ret += morph.word
    }
    ret
  }

  def getNounList: List[String]={
    val nounList = mutable.MutableList.empty[String]

    morphList.foreach{
      morphme=>
        if(morphme.getPartsOfSpeech == "名詞"){
          nounList += morphme.word
        }
    }

    nounList.toList
  }


  def hasLink(docNum : Int): Boolean ={
    var ret : Boolean = false
    links.foreach{
      link =>
        if(link.getDestDocNum == docNum){
          ret = true
        }
    }
    ret
  }

  def genLink(destDoc : Document): Unit ={
    var link : InclusiveLink = LinkNone.apply(Document.docNumNone)
    if (docNum != destDoc.docNum) {
      val inclusiveScore: Double = calcInclusive(destDoc)
      //println(s"$inclusiveScore")
      if (inclusiveScore >= CurationMap.ALPHA) {
        println(s"Generate Link Doc${this.docNum} -> Doc${destDoc.docNum}")
        link = InclusiveLink(destDoc.getText, destDoc.uuid ,destDoc.docNum)
      } else {
        //frag.links += NoneLink(doc.docNum)
      }
    }else{
      //frag.links += NoneLink(doc.docNum)
    }
    if(!link.isLinkNone){
      links += link
    }

  }


  def +(rearFrag : Fragment) :Fragment ={
    val mergedFrag = Fragment(Vector.concat(morphList, rearFrag.morphList))

    mergedFrag.docNum = docNum
    this.links.foreach{
      preLink =>
        rearFrag.links.foreach{
          rearLink=>
          if(preLink.destDocNum == rearLink.destDocNum){
            mergedFrag.links += InclusiveLink(preLink.destText, preLink.destUuid ,preLink.destDocNum)
          }
        }
    }
    mergedFrag
  }

  def calcInclusive(destDoc :Document) : Double={
    val nounNum :Int= getNounList.length
    var inclusiveNum : Int= 0

    getNounList.foreach{
      initNoun=>
        if(destDoc.getNounList.contains(initNoun)){
          inclusiveNum += 1
        }
    }

    if(nounNum != 0 && inclusiveNum != 0){
      inclusiveNum.toFloat / nounNum
    }else{
      0.0
    }


  }
  def calcInclusive(destFrag :Fragment) : Double={
    val nounNum :Int= getNounList.length
    var inclusiveNum : Int= 0

    getNounList.foreach{
      initNoun=>
        if(destFrag.getNounList.contains(initNoun)){
          inclusiveNum += 1
        }
    }

    if(nounNum != 0 && inclusiveNum != 0){
      inclusiveNum.toFloat / nounNum
    }else{
      0.0
    }


  }

  def isFragNone :Boolean={
    false
  }

}
object FragNone extends Fragment(Vector.empty) {
  override def isFragNone :Boolean={
    true
  }
}
