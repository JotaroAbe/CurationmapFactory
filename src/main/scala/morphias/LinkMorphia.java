package morphias;

import jsons.LinkJson;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("links")
public class LinkMorphia {
    @Id
    @SuppressWarnings("unused")
    private ObjectId _id;

//    public String destText;
    public int destDocNum;
    public String uuid;

    @SuppressWarnings("unused")
    private LinkMorphia() {}

    public LinkMorphia(int destDocNum, String uuid){
//        this.destText = destText;
        this.destDocNum = destDocNum;
        this.uuid = uuid;
    }

    public LinkJson toJson(){
        return new LinkJson(destDocNum, uuid);
    }
}
