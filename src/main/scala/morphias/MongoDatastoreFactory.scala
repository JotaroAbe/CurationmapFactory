package morphias

import com.mongodb.{MongoClient, MongoClientOptions, MongoCredential, ServerAddress}
import com.typesafe.config.ConfigFactory
import org.mongodb.morphia.{Datastore, Morphia}
import scala.collection.JavaConverters._

case class MongoDatastoreFactory() {
  def createDataStore : Datastore = {
    val morphia = new Morphia
    morphia.map(classOf[CurationMapMorphia])

    val config = ConfigFactory.load()

    def addPrefix(value: String): String = "mongodb.default.".concat(value)

    val host: String = config.getString(addPrefix("host"))
    val port: Int = config.getInt(addPrefix("port"))
    val dbName: String = config.getString(addPrefix("db"))
    val user: String = config.getString(addPrefix("user"))
    val password: String = config.getString(addPrefix("password"))

    val credential: MongoCredential = MongoCredential.createCredential(user, dbName, password.toCharArray)
    val client: MongoClient = new MongoClient(
      Seq[ServerAddress](new ServerAddress(host, port)).asJava,
      credential,
      MongoClientOptions.builder.build
    )
    val datastore: Datastore = morphia.createDatastore(client, dbName)
    datastore.ensureIndexes()
    datastore
  }

}
