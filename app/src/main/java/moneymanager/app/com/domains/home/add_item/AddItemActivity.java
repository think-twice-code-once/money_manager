package moneymanager.app.com.domains.home.add_item;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

import moneymanager.app.com.R;
import moneymanager.app.com.domains.home.HomeFragment;
import moneymanager.app.com.factory.MainApplication;
import moneymanager.app.com.models.Category;
import moneymanager.app.com.models.Item;
import moneymanager.app.com.models.ItemType;
import moneymanager.app.com.util.AppUtil;

import static java.lang.Float.parseFloat;
import static moneymanager.app.com.util.Constants.ITEM_TYPE;
import static moneymanager.app.com.util.Constants.SCREEN_TITLE;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/21/2017.
 */

@EActivity(R.layout.activity_add_item)
public class AddItemActivity extends MvpActivity<AddItemView, AddItemPresenter> implements AddItemView,
        Validator.ValidationListener {

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

    @NotEmpty
    @ViewById(R.id.activity_add_item_et_value)
    EditText etValue;

    @NotEmpty
    @ViewById(R.id.activity_add_item_actv_category)
    AutoCompleteTextView actvCategory;

    @ViewById(R.id.activity_add_item_et_detail)
    EditText etDetail;

    @ViewById(R.id.activity_add_item_et_date)
    EditText etDate;

    @Extra(SCREEN_TITLE)
    String title;

    @Extra(ITEM_TYPE)
    String itemType;

    private Validator validator;

    private boolean addMore;
    private ProgressDialog progressDialog;
    private TextWatcher textWatcher;
    private CategoryAdapter categoryAdapter;

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

        initValidator();

        initBasicUi();

        handleTypeValue();

        presenter.getAllCategories(itemType);
    }

    private void initValidator() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    private void initBasicUi() {
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

    private void handleTypeValue() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence content, int start, int before, int count) {
                if (!content.toString().endsWith(".") && content.length() > 3) {
                    etValue.removeTextChangedListener(textWatcher);
                    etValue.setText(AppUtil.getPrettyNumber(content.toString().replace(" ", ""), false));
                    etValue.setSelection(etValue.length());
                    etValue.addTextChangedListener(textWatcher);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        etValue.addTextChangedListener(textWatcher);
    }

    @Override
    public void getAllCategoriesSuccessful(List<Category> categories) {
        handleShowingCategories(categories);
    }

    @Override
    public void getAllCategoriesFailed(Throwable throwable) {

    }

    private void handleShowingCategories(List<Category> categories) {
        if (categories != null) {
            categoryAdapter = new CategoryAdapter(this, R.layout.item_category, categories);
            actvCategory.setAdapter(categoryAdapter);
            actvCategory.setText("");
            actvCategory.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence content, int start, int before, int count) {
                    if (TextUtils.isEmpty(content.toString().trim())) {
                        showCategoriesDropdown();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            actvCategory.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    showCategoriesDropdown();
                }
            });
        }

        /*TODO: Auto save newly created categories and show it just in the next time when user type category*/
    }

    private void showCategoriesDropdown() {
        new Handler().postDelayed(() -> {
            if (actvCategory.hasFocus()) {
                actvCategory.showDropDown();
            }
        }, 100);
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
        actvCategory.requestFocus();
        showSoftInput(actvCategory);
        showCategoriesDropdown();
    }

    @Click(R.id.activity_add_item_actv_category)
    void clickActvCategory() {
        showCategoriesDropdown();
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
        validator.validate();
    }

    @Click(R.id.activity_add_item_btn_save)
    void clickSAve() {
        addMore = false;
        validator.validate();
    }

    private void resetFields() {
        etValue.setText("");
        actvCategory.setText("");
        etDetail.setText("");
        etDate.setText("");
        etValue.requestFocus();
    }

    @Nullable
    private Item getInputData() {
        float value = -1;
        try {
            value = parseFloat(etValue.getText().toString().trim().replace(" ", ""));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        if (value > 0) {
            String categoryName = actvCategory.getText().toString().trim();
            String detail = etDetail.getText().toString().trim();
            long createdTime = System.currentTimeMillis();

            Category cate = new Category();
            cate.setId(AppUtil.createUniqueId());
            cate.setContent(categoryName);

            Item item = new Item();
            item.setId(AppUtil.createUniqueId());
            item.setValue(value);
            item.setCategory(cate);
            item.setDetail(detail);
            item.setItemType(itemType);
            item.setCreatedAt(createdTime);
            return item;
        } else {
            Toast.makeText(application, getString(R.string.invalid_value), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void saveItem(Item item) {
        if (!addMore) {
            showLoading();
        }
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

    @Override
    public void onValidationSucceeded() {
        if (getInputData() != null) {
            saveItem(getInputData());
            if (addMore) {
                resetFields();
            }
        }
    }

    @SuppressWarnings("Convert2streamapi")
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            if (error.getView() instanceof EditText) {
                ((EditText) error.getView()).setError(error.getCollatedErrorMessage(this));
                error.getView().requestFocus();
            }
        }
    }
}
