package moneymanager.app.com.domains.home.detail;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import moneymanager.app.com.models.Item;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/30/2017.
 */

public class ItemDetailPresenter extends MvpBasePresenter<ItemDetailView> {

    @Inject
    Realm realm;

    @Inject
    public ItemDetailPresenter() {
    }

    public void deleteItem(String itemId) {

        RealmResults<Item> itemToDelete = realm.where(Item.class).equalTo("id", itemId).findAll();
        realm.executeTransaction(realm -> {
            boolean isDeleted = false;
            try {
                isDeleted = itemToDelete.deleteFirstFromRealm();
            } catch (IllegalStateException ex) {
                if (getView() != null) {
                    getView().deleteItemFailed(ex.getCause());
                }
            }
            if (getView() != null && isDeleted) {
                getView().deleteItemSuccessful();
            }
        });
    }
}
