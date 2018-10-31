package morphias;

import jsons.CurationMapJson;
import jsons.DocumentJson;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.LinkedList;
import java.util.List;

@Entity("maps")
public class CurationMapMorphia {

    @Id @SuppressWarnings("unused")
    private ObjectId _id;

    public String query;
    public List<DocumentMorphia> documents;

    @SuppressWarnings("unused")
    private CurationMapMorphia() {}

    public CurationMapMorphia(String query, List<DocumentMorphia> documents){
        this.query = query;
        this.documents = documents;
    }

    public CurationMapJson toJson(){
        List<DocumentJson> jList = new LinkedList<>();

        if(documents != null) {
            for (DocumentMorphia dm : documents) {
                jList.add(dm.toJson());
            }
        }
        return new CurationMapJson(query,jList);
    }

}
