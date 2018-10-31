package pipeline

import dataStructures.jsons.CurationMapJson
import dataStructures.morphias.CurationMapMorphia
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.query.Query
import tools.{DataInputer, GoogleSearcher}


case class CMapGenerator(query :String, ds : Datastore) {

  val res: Query[CurationMapMorphia] = ds.createQuery(classOf[CurationMapMorphia]).field("query").equal(query)
  var cMapJsonOpt: Option[CurationMapJson] = Option.empty[CurationMapJson]

  if(res.count() == 0){

    val list: List[String] = GoogleSearcher(query).getInput
    val cMap = DataInputer(query,list).gethasntLinkCurationMap
    cMap.genLink()
    cMap.genSplitLink()
    cMap.mergeLink()
    cMap.calcHits()
    cMap.changeLinkDest()

    ds.save[CurationMapMorphia](cMap.getMorphia)

    cMapJsonOpt = Option(cMap.toJson)
  }
}
