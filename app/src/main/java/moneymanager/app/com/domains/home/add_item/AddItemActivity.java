package moneymanager.app.com.domains.home.add_item;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import moneymanager.app.com.domains.home.HomeFragment;
import moneymanager.app.com.factory.MainApplication;
import moneymanager.app.com.models.Category;
import moneymanager.app.com.models.Item;
import moneymanager.app.com.models.ItemType;
import moneymanager.app.com.util.AppUtil;

import static moneymanager.app.com.util.Constants.ITEM_TYPE;
import static moneymanager.app.com.util.Constants.SCREEN_TITLE;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/21/2017.
 */

@EActivity(R.layout.activity_add_item)
public class AddItemActivity extends MvpActivity<AddItemView, AddItemPresenter> implements AddItemView {

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

    private boolean addMore;
    ProgressDialog progressDialog;

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

        if (getSupportActionBar() != null) {
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

    @Click(R.id.activity_add_item_btn_cancel)
    void clickCancel() {
        finish();
    }

    @Click(R.id.activity_add_item_btn_save_and_add_more)
    void clickSaveAndAddMore() {
        addMore = true;
        saveItem(getInputData());
        resetFields();
    }

    @Click(R.id.activity_add_item_btn_save)
    void clickSAve() {
        addMore = false;
        saveItem(getInputData());
    }

    private void resetFields() {
        etValue.setText("");
        etCategory.setText("");
        etDetail.setText("");
        etDate.setText("");
        etValue.requestFocus();
    }

    private Item getInputData() {
        float value = Float.parseFloat(etValue.getText().toString().trim());
        String categoryName = etCategory.getText().toString().trim();
        String detail = etDetail.getText().toString().trim();
        long createdTime = System.currentTimeMillis();

        Category cate = new Category();
        cate.setId(AppUtil.createUniqueId());
        cate.setName(categoryName);

        Item item = new Item();
        item.setId(AppUtil.createUniqueId());
        item.setValue(value);
        item.setCategory(cate);
        item.setDetail(detail);
        item.setItemType(itemType);
        item.setCreatedAt(createdTime);
        return item;
    }

    private void saveItem(Item item) {
        showLoading();
        presenter.saveItem(item);
    }

    private void showSoftInput(View currentView) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(currentView, 0);
    }

    @Override
    public void showLoading() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.saving));
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    @Override
    public void saveItemSuccessful(Item item) {
        setResult(HomeFragment.ADD_NEW_ITEM_RESULT);
        callHideLoadingWithDelay();
    }

    private void callHideLoadingWithDelay() {
        new Handler().postDelayed(() -> {
            hideLoading();
            if (!addMore) {
                finish();
            }
        }, 500);
    }

    @Override
    public void saveItemFailed(Throwable throwable) {
        hideLoading();
        Toast.makeText(application, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
