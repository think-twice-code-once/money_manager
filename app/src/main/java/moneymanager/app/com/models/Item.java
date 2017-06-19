package moneymanager.app.com.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/19/2017.
 */

public class Item extends RealmObject {

    @Required
    @PrimaryKey
    private String id;

    private long value;
    private Category category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
