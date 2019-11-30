package pipeline

import dataStructures.jsons.CurationMapJson
import dataStructures.morphias.CurationMapMorphia
import models.CurationMap
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.query.Query
import tools.{BingSearcher, DataInputer, GoogleSearcher}


case class CMapFactory() {


  def createMap(query :String, ds : Datastore): Unit ={
    val res: Query[CurationMapMorphia] = ds.createQuery(classOf[CurationMapMorphia]).field("query").equal(query)
    //var cMapJsonOpt: Option[CurationMapJson] = Option.empty[CurationMapJson]

    if(res.count() == 0){

      val searcher = BingSearcher()
      searcher.search(query)

      val list: List[String] =searcher.getInput

      val di = DataInputer()

      val cMap: CurationMap = di.inputWebData(query, list)
      cMap.genLink()
      //cMap.genSplitLink()
      //cMap.mergeLink()
      //cMap.calcHits()
      //cMap.changeLinkDest()

      ds.save[CurationMapMorphia](cMap.getMorphia)

      //cMapJsonOpt = Option(cMap.toJson)
    }else{
      println(s"「$query」はすでにDB内に存在します。")
    }
  }
}
