package moneymanager.app.com.domains;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

import moneymanager.app.com.R;
import moneymanager.app.com.models.ItemType;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/30/2017.
 */

@EActivity
public abstract class BaseActivity<V extends MvpView, P extends MvpPresenter<V>> extends MvpActivity<V, P> {

    public void changeActionBarAndStatusBarColor(String itemType) {
        //config actionbar and status bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(
                    ContextCompat.getColor(getApplicationContext(),
                            ItemType.INCOME.toString().equals(itemType) ? R.color.colorPrimary : R.color.orange)));

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
    public void clickBackNavigation() {
        onBackPressed();
    }
}
