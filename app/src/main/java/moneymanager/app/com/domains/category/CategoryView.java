package moneymanager.app.com.domains.category;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import moneymanager.app.com.models.Category;

/**
 * -> Created by Think-Twice-Code-Once on 6/18/2017.
 */

public interface CategoryView extends MvpView {

    void getParentCategoriesSuccessful(List<Category> categories);

    void getParentCategoriesFailed(Throwable throwable);

    void getChildCategoriesSuccessful(String parentTag, List<Category> categories);

    void getChildCategoriesFailed(Throwable throwable);
}
