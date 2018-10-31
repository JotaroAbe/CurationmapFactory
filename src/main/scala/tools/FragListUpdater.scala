package tools

import models.Fragment

import scala.collection.mutable

case class FragListUpdater(list : Vector[Fragment], oldPreFrag : Fragment, oldRearFrag : Fragment,newFrag : Fragment) {

  private val index: Int =  list.indexOf(oldPreFrag)
  private val newListbuff = mutable.MutableList.empty[Fragment]
  list.foreach{
    frag=>
      if(frag.uuid == oldPreFrag.uuid){
        newListbuff += newFrag

      }else if(frag.uuid == oldRearFrag.uuid){
        //Do Nothing
      }else{
        newListbuff += frag

      }

  }


  def getNewList: Vector[Fragment]={
    newListbuff.toVector
  }

}

