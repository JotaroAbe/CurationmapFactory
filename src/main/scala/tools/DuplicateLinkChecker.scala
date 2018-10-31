package tools

import models.{Document, Fragment, InclusiveLink}

import scala.collection.mutable

case class DuplicateLinkChecker(frag1 : Fragment, frag2 : Fragment) {

  var isDuplicate : Boolean = false
  private val dupDocNumSet = mutable.Set.empty[Int]

  getDestLinkDocNums(frag1).foreach{
    destDocNum=>
      if(getDestLinkDocNums(frag2).contains(destDocNum)){//重複
        dupDocNumSet += destDocNum
        isDuplicate = true
      }
  }


  private def getDestLinkDocNums(frag :Fragment) : Set[Int]={
    val ret = mutable.Set.empty[Int]
    frag.links.foreach{
      link=>
        if(link.getDestDocNum != Document.docNumNone){
          ret += link.getDestDocNum
        }
    }
    ret.toSet
  }

  def getDupDocNumSet: Set[Int]={
    dupDocNumSet.toSet
  }

}
