package moneymanager.app.com.domains.home;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.realm.Realm;
import moneymanager.app.com.models.Category;
import moneymanager.app.com.models.Item;
import moneymanager.app.com.models.ItemType;
import moneymanager.app.com.util.AppUtil;
import rx.Observable;

/**
 * -> Created by Think-Twice-Code-Once on 6/18/2017.
 */

public class HomePresenter extends MvpBasePresenter<HomeView> {

    @Inject
    Realm realm;

    @Inject
    public HomePresenter() {

    }

    public void getAllItems() {
        realm.executeTransaction(realm -> {
            Item item = new Item();
            item.setId(AppUtil.createUniqueId());
            item.setValue(14325);
            Category category = new Category();
            category.setId(AppUtil.createUniqueId());
            category.setName("Clothes");
            item.setCategory(category);
            item.setCreatedAt(System.currentTimeMillis());
            item.setDetail("I'm hungry 1");
            item.setItemType(System.currentTimeMillis() % 2 == 0 ? ItemType.EARN.toString() : ItemType.PAY.toString());

            realm.copyToRealmOrUpdate(item);
        });

        realm.where(Item.class)
                .findAll()
                .asObservable()
                .flatMap(items -> Observable.from(items).toList())
                .subscribe(items -> {
                    if (getView() != null) {
                        getView().getAllItemsSuccessful(items);
                    }
                }, throwable -> {
                    if (getView() != null) {
                        getView().getAllItemsFailed(throwable);
                    }
                });
    }

}
