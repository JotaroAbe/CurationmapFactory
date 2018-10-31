package models

import java.util.UUID

case class InclusiveLink(var destText : String, var destUuid: UUID, destDocNum : Int){


  override def toString: String = {
    s"-> Doc$destDocNum"
  }


  def getDestDocNum : Int ={
    destDocNum
  }

  def getDestText : String={
    destText
  }

  def +(rearLink : InclusiveLink) :InclusiveLink={
    InclusiveLink(destText, destUuid ,destDocNum)
  }
  def isLinkNone :Boolean={
    false
  }
}

class LinkNone(initDocNum : Int) extends InclusiveLink("", null ,Document.docNumNone){


  override def toString: String = {
    s"Doc${initDocNum}Frag None Link"
  }


  override def getDestDocNum: Int = Document.docNumNone

  override def isLinkNone: Boolean = true


}
object LinkNone extends InclusiveLink("", null, Document.docNumNone){
  def apply(initDocNum: Int): LinkNone = new LinkNone(initDocNum)
}


