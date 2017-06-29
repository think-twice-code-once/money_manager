package moneymanager.app.com.domains.home.add_item;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import moneymanager.app.com.models.Category;
import moneymanager.app.com.models.Item;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/21/2017.
 */

public class AddItemPresenter extends MvpBasePresenter<AddItemView> {

    @Inject
    Realm realm;

    @Inject
    public AddItemPresenter() {

    }

    void saveItem(Item item) {
        realm.executeTransaction(
                realm -> realm.copyToRealmOrUpdate(item)
                        .asObservable()
                        .cast(Item.class)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(resultItem -> {
                            if (getView() != null) {
                                getView().saveItemSuccessful(resultItem);
                            }
                        }, throwable -> {
                            if (getView() != null) {
                                getView().saveItemFailed(throwable);
                            }
                        }));
    }


    void getAllCategories(String type) {
        RealmResults<Category> realmResultsCategories = realm.where(Category.class)
                .equalTo("type", type)
                .findAll();

        Observable.from(realmResultsCategories)
                .toSortedList((category1, category2)
                        -> category1.getContent().compareToIgnoreCase(category2.getContent()))
                .subscribe(categories -> {
                    if (getView() != null) {
                        getView().getAllCategoriesSuccessful(categories);
                    }
                }, throwable -> {
                    if (getView() != null) {
                        getView().getAllCategoriesFailed(throwable);
                    }
                });
    }
}
