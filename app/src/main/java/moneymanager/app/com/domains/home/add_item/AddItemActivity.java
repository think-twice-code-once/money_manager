package moneymanager.app.com.domains.home.add_item;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

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

    @ViewById(R.id.activity_add_item_ll_bottom)
    LinearLayout llBottom;

    @ViewById(R.id.activity_add_item_btn_save)
    Button btnSave;

    @ViewById(R.id.activity_add_item_btn_save_and_add_more)
    Button btnSaveAndAddMore;

    @ViewById(R.id.activity_add_item_btn_cancel)
    Button btnCancel;

    @ViewById(R.id.activity_add_item_iv_required_value)
    ImageView ivRequiredValue;

    @ViewById(R.id.activity_add_item_iv_required_category)
    ImageView ivRequiredCategory;

    @ViewById(R.id.activity_add_item_iv_required_prompt)
    ImageView ivRequiredPrompt;

    @ViewById(R.id.activity_add_item_et_value)
    EditText etValue;

    @ViewById(R.id.activity_add_item_et_category)
    EditText etCategory;

    @ViewById(R.id.activity_add_item_et_detail)
    EditText etDetail;

    @ViewById(R.id.activity_add_item_et_date)
    EditText etDate;

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

            int mainColor = ContextCompat.getColor(getApplicationContext(), ItemType.INCOME.toString().equals(itemType)
                    ? R.color.colorPrimary : R.color.orange);
            llBottom.setBackgroundColor(mainColor);

            ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(),
                    ItemType.INCOME.toString().equals(itemType)
                            ? R.color.selector_white_green_color : R.color.selector_white_orange_color);
            btnSave.setTextColor(colorStateList);
            btnSaveAndAddMore.setTextColor(colorStateList);
            btnCancel.setTextColor(colorStateList);

            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(mainColor));

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

        int requiredIconRes = ItemType.INCOME.toString().equals(itemType)
                ? R.drawable.ic_required_green : R.drawable.ic_required_orange;
        ivRequiredValue.setImageResource(requiredIconRes);
        ivRequiredCategory.setImageResource(requiredIconRes);
        ivRequiredPrompt.setImageResource(requiredIconRes);
    }

    @OptionsItem(android.R.id.home)
    void clickBackNavigation() {
        onBackPressed();
    }

    @Click(R.id.activity_add_item_ll_value)
    void clickValue() {
        etValue.requestFocus();
        showSoftInput(etValue);
    }

    @Click(R.id.activity_add_item_ll_category)
    void clickCategory() {
        etCategory.requestFocus();
        showSoftInput(etCategory);
    }


    @Click(R.id.activity_add_item_ll_detail)
    void clickDetail() {
        etDetail.requestFocus();
        showSoftInput(etDetail);
    }

    @Click(R.id.activity_add_item_ll_date)
    void clickDate() {
        etDate.requestFocus();
        showSoftInput(etDate);
    }

    private void showSoftInput(View currentView) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(currentView, 0);
    }
}
