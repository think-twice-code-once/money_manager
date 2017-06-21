package moneymanager.app.com.domains.home.add_item;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;

import javax.inject.Inject;

import moneymanager.app.com.R;
import moneymanager.app.com.factory.MainApplication;
import moneymanager.app.com.models.ItemType;

import static moneymanager.app.com.util.Constants.ITEM_TYPE;
import static moneymanager.app.com.util.Constants.SCREEN_TITLE;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/21/2017.
 */

@EActivity(R.layout.activity_add_item)
public class AddItemActivity extends MvpActivity<AddItemView, AddItemPresenter> {

    @Inject
    AddItemPresenter addItemPresenter;

    @App
    MainApplication application;

    @Extra(SCREEN_TITLE)
    String title;

    @Extra(ITEM_TYPE)
    String itemType;

    @NonNull
    @Override
    public AddItemPresenter createPresenter() {
        return addItemPresenter;
    }

    @AfterInject

    void afterInject() {
        DaggerAddItemComponent
                .builder()
                .applicationComponent(application.getApplicationComponent())
                .build()
                .inject(this);
    }

    @AfterViews
    void init() {
        setTitle(title);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(
                    ContextCompat.getColor(getApplicationContext(), ItemType.INCOME.toString().equals(itemType)
                            ? R.color.colorPrimary : R.color.orange)));

            //just in case some devices not affected:
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow()
                        .setStatusBarColor(ContextCompat
                                .getColor(getApplicationContext(), ItemType.INCOME.toString().equals(itemType)
                                        ? R.color.colorPrimaryDark : R.color.orange_dark));
            }
        }
    }

    @OptionsItem(android.R.id.home)
    void clickBackNavigation() {
        onBackPressed();
    }
}
