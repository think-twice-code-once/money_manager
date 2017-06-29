package moneymanager.app.com.domains.home.add_item;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import moneymanager.app.com.models.Category;
import moneymanager.app.com.models.Item;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/21/2017.
 */

public interface AddItemView extends MvpView {

    void showLoading();

    void hideLoading();

    void saveItemSuccessful(Item item);

    void saveItemFailed(Throwable throwable);

    void getAllCategoriesSuccessful(List<Category> categories);

    void getAllCategoriesFailed(Throwable throwable);
}
