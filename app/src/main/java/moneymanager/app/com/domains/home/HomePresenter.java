package moneymanager.app.com.domains.home;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.realm.Realm;
import moneymanager.app.com.models.Item;
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
