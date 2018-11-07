import dataStructures.morphias.MongoDatastoreFactory
import org.mongodb.morphia.Datastore
import pipeline.CMapGenerator

object Main {
  var dsOpt = Option.empty[Datastore]
  def main(args: Array[String]): Unit ={

    val ds : Datastore= dsOpt match {
      case Some(dataStore) => dataStore
      case _ => val newDataStore =  MongoDatastoreFactory().createDataStore
        dsOpt = Option(newDataStore)
        newDataStore
    }
    CMapGenerator("桜木町", ds)

  }
}
