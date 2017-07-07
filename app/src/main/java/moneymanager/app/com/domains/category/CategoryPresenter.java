package moneymanager.app.com.domains.category;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.realm.Realm;
import moneymanager.app.com.models.Category;
import moneymanager.app.com.models.ItemType;
import rx.Observable;

/**
 * -> Created by Think-Twice-Code-Once on 6/18/2017.
 */

public class CategoryPresenter extends MvpBasePresenter<CategoryView> {

    @Inject
    Realm realm;

    @Inject
    public CategoryPresenter() {

    }

    public void getParentCategories(ItemType itemType) {
        realm.where(Category.class)
                .equalTo("type", itemType.toString())
                .equalTo("parentId", (String) null)
                .findAllAsync()
                .addChangeListener(realmResults ->
                        Observable.from(realmResults)
                                .toSortedList((category1, category2)
                                        -> category1.getContent().compareToIgnoreCase(category2.getContent()))
                                .subscribe(categories -> {
                                    if (getView() != null) {
                                        getView().getParentCategoriesSuccessful(categories);
                                    }
                                }, throwable -> {
                                    if (getView() != null) {
                                        getView().getParentCategoriesFailed(throwable);
                                    }
                                }));
    }

    public void getChildCategories(ItemType itemType, String parentId, String parentTag) {
        realm.where(Category.class)
                .equalTo("type", itemType.toString())
                .equalTo("parentId", parentId)
                .findAllAsync()
                .addChangeListener(realmResults ->
                        Observable.from(realmResults)
                                .toSortedList((category1, category2)
                                        -> category1.getContent().compareToIgnoreCase(category2.getContent()))
                                .subscribe(categories -> {
                                    if (getView() != null) {
                                        getView().getChildCategoriesSuccessful(parentTag, categories);
                                    }
                                }, throwable -> {
                                    if (getView() != null) {
                                        getView().getChildCategoriesFailed(throwable);
                                    }
                                }));
    }

}
