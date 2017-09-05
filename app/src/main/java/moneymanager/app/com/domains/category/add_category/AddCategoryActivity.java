package moneymanager.app.com.domains.category.add_category;

import android.support.annotation.NonNull;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

import moneymanager.app.com.R;
import moneymanager.app.com.domains.BaseActivity;
import moneymanager.app.com.factory.MainApplication;

/**
 * -> Created by Think-Twice-Code-Once on 9/1/2017.
 */

@EActivity(R.layout.activity_add_category)
public class AddCategoryActivity extends BaseActivity<AddCategoryView, AddCategoryPresenter>
        implements AddCategoryView {

    @App
    MainApplication application;

    @NonNull
    @Override
    public AddCategoryPresenter createPresenter() {
        return presenter;
    }

    @Inject
    AddCategoryPresenter presenter;

    @AfterInject
    void afterInject() {
        DaggerAddCategoryComponent.builder()
                .applicationComponent(application.getApplicationComponent())
                .build()
                .inject(this);
    }

    @AfterViews
    void afterView() {

    }
}
