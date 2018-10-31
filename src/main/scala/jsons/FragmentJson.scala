package jsons

import play.api.libs.json.Json
import java.util.{List => JList}
import scala.collection.JavaConverters._

case class FragmentJson(text : String,
                        links :Seq[LinkJson],
                        uuid : String) {
  def this(text: String,
           links: JList[LinkJson],
           uuid: String) = {
    this(text, links.asScala, uuid)
  }
}
object FragmentJson{
  implicit val jsonWrites = Json.writes[FragmentJson]
  implicit val jsonreads = Json.reads[FragmentJson]
}
