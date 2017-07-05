package moneymanager.app.com.domains.home.add_item;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

import moneymanager.app.com.R;
import moneymanager.app.com.domains.BaseActivity;
import moneymanager.app.com.factory.MainApplication;
import moneymanager.app.com.models.Category;
import moneymanager.app.com.models.Item;
import moneymanager.app.com.models.ItemType;
import moneymanager.app.com.util.AppUtil;

import static java.lang.Float.parseFloat;
import static moneymanager.app.com.util.Constants.ADD_NEW_ITEM_RESULT;
import static moneymanager.app.com.util.Constants.EDIT_ITEM_RESULT;
import static moneymanager.app.com.util.Constants.IS_EDIT_ITEM;
import static moneymanager.app.com.util.Constants.ITEM_CATEGORY;
import static moneymanager.app.com.util.Constants.ITEM_DATE;
import static moneymanager.app.com.util.Constants.ITEM_DETAIL;
import static moneymanager.app.com.util.Constants.ITEM_ID;
import static moneymanager.app.com.util.Constants.ITEM_TYPE;
import static moneymanager.app.com.util.Constants.ITEM_VALUE;
import static moneymanager.app.com.util.Constants.SCREEN_TITLE;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/21/2017.
 */

@EActivity(R.layout.activity_add_item)
public class AddItemActivity extends BaseActivity<AddItemView, AddItemPresenter> implements AddItemView,
        Validator.ValidationListener {

    @Inject
    AddItemPresenter addItemPresenter;

    @App
    MainApplication application;

    @ViewById(R.id.activity_add_item_sv_container)
    ScrollView svContainer;

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

    @ViewById(R.id.activity_add_item_tv_date)
    TextView tvDate;

    @Extra(SCREEN_TITLE)
    String title;

    @Extra(ITEM_TYPE)
    String itemType;

    @Extra(IS_EDIT_ITEM)
    boolean isEditItem;

    @Extra(ITEM_ID)
    String itemId;

    @Extra(ITEM_VALUE)
    String itemValue;

    @Extra(ITEM_CATEGORY)
    String itemCategory;

    @Extra(ITEM_DETAIL)
    String itemDetail;

    @Extra(ITEM_DATE)
    String itemDate;

    private Validator validator;

    private boolean addMore;
    private ProgressDialog progressDialog;
    private TextWatcher textWatcher;
    private long createdTime = -1;

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

        handleTypingValue();

        handleTyingDate();

        presenter.getAllCategories(itemType);
    }

    private void initValidator() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    private void initBasicUi() {
        setTitle(title);

        changeActionBarAndStatusBarColor(itemType);

        changeUiByItemType();
    }

    private void changeUiByItemType() {
        llBottom.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
                ItemType.INCOME.toString().equals(itemType)
                        ? R.color.colorPrimary : R.color.orange));

        ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(),
                ItemType.INCOME.toString().equals(itemType)
                        ? R.color.selector_white_green_color : R.color.selector_white_orange_color);
        btnSave.setTextColor(colorStateList);
        btnSaveAndAddMore.setTextColor(colorStateList);
        btnCancel.setTextColor(colorStateList);

        int requiredIconRes = ItemType.INCOME.toString().equals(itemType)
                ? R.drawable.ic_required_green : R.drawable.ic_required_orange;
        ivRequiredValue.setImageResource(requiredIconRes);
        ivRequiredCategory.setImageResource(requiredIconRes);
        ivRequiredPrompt.setImageResource(requiredIconRes);

        //change UI when user edit item
        if (isEditItem) {
            btnSaveAndAddMore.setVisibility(View.GONE);
            btnSave.setText(getString(R.string.update));
            etValue.setText(itemValue);
            actvCategory.setText(itemCategory);
            etDetail.setText(itemDetail);
            tvDate.setText(itemDate);
        }
    }

    private void handleTypingValue() {
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

    private void handleTyingDate() {
        tvDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        hideKeyboard(tvDate);
        CustomDatePicker datePicker = new CustomDatePicker();
        datePicker.setItemType(itemType);
        datePicker.setOnSelectDateTimeListener(createdTime -> {
            tvDate.setText(AppUtil.getDateTimeStringFromMillisecond(createdTime));
            AddItemActivity.this.createdTime = createdTime;
        });
        datePicker.show(getSupportFragmentManager(), CustomDatePicker.class.getSimpleName());
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
            CategoryAdapter categoryAdapter = new CategoryAdapter(this, R.layout.item_category, categories);
            actvCategory.setAdapter(categoryAdapter);
            actvCategory.setText(itemCategory);
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

            actvCategory.setOnDismissListener(() ->
                    new Handler().postDelayed(() -> {
                        if (!actvCategory.isPopupShowing()) {
                            svContainer.scrollTo(0, 0);
                        }
                    }, 500));
        }
    }

    private void showCategoriesDropdown() {
        new Handler().postDelayed(() -> {
            if (actvCategory.hasFocus()) {
                actvCategory.showDropDown();
            }
        }, 100);
    }

    @Click(R.id.activity_add_item_ll_value)
    void clickValue() {
        etValue.requestFocus();
        showKeyboard(etValue);
    }

    @Click(R.id.activity_add_item_ll_category)
    void clickCategory() {
        actvCategory.requestFocus();
        showKeyboard(actvCategory);
        showCategoriesDropdown();
    }

    @Click(R.id.activity_add_item_actv_category)
    void clickActvCategory() {
        showCategoriesDropdown();
    }

    @Click(R.id.activity_add_item_ll_detail)
    void clickDetail() {
        etDetail.requestFocus();
        showKeyboard(etDetail);
    }

    @Click(R.id.activity_add_item_ll_date)
    void clickDate() {
        showDatePicker();
    }

    @Click(R.id.activity_add_item_tv_date)
    void clickTvDate() {
       showDatePicker();
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
        tvDate.setText("");
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
            String detail = etDetail.getText().toString().trim();
            if (createdTime == -1) {
                createdTime = System.currentTimeMillis();
            }

            Item item = new Item();
            if (TextUtils.isEmpty(itemId)) {
                item.setId(AppUtil.createUniqueId());
            } else {
                item.setId(itemId);
            }
            item.setValue(value);
            item.setDetail(detail);
            item.setItemType(itemType);
            item.setCreatedAt(createdTime);
            return item;
        } else {
            Toast.makeText(application, getString(R.string.invalid_value), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void saveItem(Item item, String categoryTag) {
        if (!addMore) {
            showLoading();
        }
        presenter.saveItem(item, categoryTag);
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
        if (!isEditItem) {
            setResult(ADD_NEW_ITEM_RESULT);
        } else {
            Intent editData = new Intent();
            if (!etValue.getText().toString().trim().equals(itemValue)) {
                editData.putExtra(ITEM_VALUE, etValue.getText().toString().trim());
            }
            if (!actvCategory.getText().toString().trim().equals(itemCategory)) {
                editData.putExtra(ITEM_CATEGORY, actvCategory.getText().toString().trim());
            }
            if (!etDetail.getText().toString().trim().equals(itemDetail)) {
                editData.putExtra(ITEM_DETAIL, etDetail.getText().toString().trim());
            }
            if (!tvDate.getText().toString().trim().equals(itemDate)) {
                editData.putExtra(ITEM_DATE, tvDate.getText().toString().trim());
            }
            setResult(EDIT_ITEM_RESULT, editData);
        }
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
            saveItem(getInputData(), actvCategory.getText().toString().trim());
            if (addMore) {
                resetFields();
                presenter.getAllCategories(itemType);
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
