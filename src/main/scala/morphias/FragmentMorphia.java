package morphias;

import jsons.FragmentJson;
import jsons.LinkJson;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.LinkedList;
import java.util.List;

@Entity("frags")
public class FragmentMorphia {
    @Id
    @SuppressWarnings("unused")
    private ObjectId _id;

    public String text;
    public List<LinkMorphia> links;
    public String uuid;

    @SuppressWarnings("unused")
    private FragmentMorphia() {}

    public FragmentMorphia(String text, List<LinkMorphia> links, String uuid){
        this.text = text;
        this.links = links;
        this.uuid = uuid;
    }
    public FragmentJson toJson(){
        List<LinkJson> jList = new LinkedList<>();

        if(links != null) {
            for (LinkMorphia fm : links) {
                jList.add(fm.toJson());
            }
        }

        return new FragmentJson(text, jList, uuid);
    }
}
