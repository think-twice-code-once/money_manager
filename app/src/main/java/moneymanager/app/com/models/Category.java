package moneymanager.app.com.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/19/2017.
 */

public class Category extends RealmObject {

    @Required
    @PrimaryKey
    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
