package moneymanager.app.com.domains.home.detail;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/30/2017.
 */

public class ItemDetailPresenter extends MvpBasePresenter<ItemDetailView> {

    @Inject
    public ItemDetailPresenter() {

    }

    @Inject
    Realm realm;
}
