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
        realm.where(Item.class)
                .findAllAsync()
                .addChangeListener(realmResultsItems ->
                        Observable.from(realmResultsItems)
                                .flatMap(items -> Observable.from(realmResultsItems)
                                        .toSortedList((item1, item2)
                                                -> (int) (item2.getCreatedAt() - item1.getCreatedAt())))
                                .subscribe(items -> {
                                    if (getView() != null) {
                                        getView().getAllItemsSuccessful(items);
                                    }
                                }, throwable -> {
                                    if (getView() != null) {
                                        getView().getAllItemsFailed(throwable);
                                    }
                                }));
    }

    public void initDefaultCategories() {
        if (realm.where(Category.class).findAll().size() < 5) {
            String[] cateContents = {"Food", "Fuel", "Book", "Clothes", "Stuff",
                    "Motorbike ticket", "Salary", "Selling Stuff"};
            String[] tags = {"food", "fuel", "book", "clothes", "stuff",
                    "motorbike_ticket", "salary", "selling_stuff"};
            String[] types = {ItemType.PAYMENT.toString(), ItemType.PAYMENT.toString(),
                    ItemType.PAYMENT.toString(), ItemType.PAYMENT.toString(), ItemType.PAYMENT.toString(),
                    ItemType.PAYMENT.toString(), ItemType.INCOME.toString(), ItemType.INCOME.toString()};

            for (int i = 0; i < cateContents.length; i++) {
                Category category = new Category();
                category.setId(AppUtil.createUniqueId());
                category.setContent(cateContents[i]);
                category.setTag(tags[i]);
                category.setType(types[i]);
                realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(category));
            }
            initDefaultChildCategories();
        }
    }

    private void initDefaultChildCategories() {
        Category foodCategory = realm.where(Category.class).equalTo("tag", "food").findFirst();
        String[] childCateContents = {"Breakfast", "Lunch", "Dinner", "Milk", "Snack", "KFC"};
        String[] childTags = {"breakfast", "lunch", "dinner", "milk", "snack", "kfc"};
        String[] childTypes = {ItemType.PAYMENT.toString(), ItemType.PAYMENT.toString(),
                ItemType.PAYMENT.toString(), ItemType.PAYMENT.toString(),
                ItemType.PAYMENT.toString(), ItemType.INCOME.toString()};

        for (int j = 0; j < childCateContents.length; j++) {
            Category c = new Category();
            c.setId(AppUtil.createUniqueId());
            c.setContent(childCateContents[j]);
            c.setTag(childTags[j]);
            c.setType(childTypes[j]);
            c.setParentId(foodCategory.getId());
            realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(c));
        }
    }

}
