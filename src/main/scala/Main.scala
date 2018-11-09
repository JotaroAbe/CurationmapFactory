import dataStructures.morphias.MongoDatastoreFactory
import org.mongodb.morphia.Datastore
import pipeline.CMapFactory


object Main {

  def main(args: Array[String]): Unit ={
    val ds: Datastore =  MongoDatastoreFactory().createDataStore

    args.foreach {
      q =>
        CMapFactory().createMap(q, ds)
    }

  }
}
