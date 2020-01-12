package pipeline

import dataStructures.morphias.DocumentMorphia
import dev.morphia.Datastore
import dev.morphia.query.Query
import models.CurationMap
import tools.{BingSearcher, DataInputer, GoogleSearcher}


case class CMapFactory() {


  def createMap(query :String, ds : Datastore): Unit ={
    val res: Query[DocumentMorphia] = ds.createQuery(classOf[DocumentMorphia]).field("query").equal(query)
    //var cMapJsonOpt: Option[CurationMapJson] = Option.empty[CurationMapJson]

    if(res.count() == 0){

      val searcher = BingSearcher()
      searcher.search(query)

      val di = DataInputer()

      val cMap: CurationMap = di.inputWebData(query, searcher.getInput)
      cMap.genLinkAndInsertDb(ds)
      //cMap.genSplitLink()
      //cMap.mergeLink()
      //cMap.calcHits()
      //cMap.changeLinkDest()

      /*cMap.getMorphia.foreach{
        docm =>
          ds.save[DocumentMorphia](docm)
      }*/

      //cMapJsonOpt = Option(cMap.toJson)
    }else{
      println(s"「$query」はすでにDB内に存在します。")
    }
  }
}
