package moneymanager.app.com.domains.home.detail;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/30/2017.
 */

public interface ItemDetailView extends MvpView {

    void deleteItemSuccessful();

    void deleteItemFailed(Throwable throwable);

}
