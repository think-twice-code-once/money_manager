package moneymanager.app.com.domains.home;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import moneymanager.app.com.models.Item;

/**
 * -> Created by Think-Twice-Code-Once on 6/18/2017.
 */

public interface HomeView extends MvpView {

    void getAllItemsSuccessful(List<Item> items);

    void getAllItemsFailed(Throwable throwable);
}
