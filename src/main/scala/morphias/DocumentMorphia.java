package morphias;

import jsons.DocumentJson;
import jsons.FragmentJson;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.LinkedList;
import java.util.List;

@Entity("docs")
public class DocumentMorphia {
    @Id
    @SuppressWarnings("unused")
    private ObjectId _id;

    public String url;
    public int docNum;
    public double hub;
    public double auth;
    public List<FragmentMorphia> fragments;
    public String uuid;

    @SuppressWarnings("unused")
    private DocumentMorphia() {}

    public DocumentMorphia(String url, int docNum, double hub, double auth, List<FragmentMorphia> fragments, String uuid){
        this.url = url;
        this.docNum = docNum;
        this.hub = hub;
        this.auth = auth;
        this.fragments = fragments;
        this.uuid = uuid;
    }
    public DocumentJson toJson(){
        List<FragmentJson> jList = new LinkedList<>();

        if(fragments != null) {

            for (FragmentMorphia lm : fragments) {
                jList.add(lm.toJson());
            }
        }
        return new DocumentJson(url, docNum, hub, auth, jList, uuid);
    }
}
