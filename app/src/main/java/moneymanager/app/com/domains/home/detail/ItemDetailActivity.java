package moneymanager.app.com.domains.home.detail;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import moneymanager.app.com.R;
import moneymanager.app.com.domains.BaseActivity;
import moneymanager.app.com.domains.home.add_item.AddItemActivity_;
import moneymanager.app.com.factory.MainApplication;
import moneymanager.app.com.models.ItemType;

import static moneymanager.app.com.util.Constants.DELETE_ITEM_RESULT;
import static moneymanager.app.com.util.Constants.EDIT_ITEM_REQUEST;
import static moneymanager.app.com.util.Constants.EDIT_ITEM_RESULT;
import static moneymanager.app.com.util.Constants.IS_EDIT_ITEM;
import static moneymanager.app.com.util.Constants.ITEM_CATEGORY;
import static moneymanager.app.com.util.Constants.ITEM_CREATED_TIME;
import static moneymanager.app.com.util.Constants.ITEM_DATE;
import static moneymanager.app.com.util.Constants.ITEM_DETAIL;
import static moneymanager.app.com.util.Constants.ITEM_ID;
import static moneymanager.app.com.util.Constants.ITEM_TYPE;
import static moneymanager.app.com.util.Constants.ITEM_VALUE;
import static moneymanager.app.com.util.Constants.SCREEN_TITLE;

@EActivity(R.layout.activity_item_detail)
public class ItemDetailActivity extends BaseActivity<ItemDetailView, ItemDetailPresenter>
        implements ItemDetailView {

    @App
    MainApplication application;

    @Inject
    ItemDetailPresenter presenter;

    @ViewById(R.id.activity_item_detail_tv_value)
    TextView tvValue;

    @ViewById(R.id.activity_item_detail_tv_category)
    TextView tvCategory;

    @ViewById(R.id.activity_item_detail_tv_detail)
    TextView tvDetail;

    @ViewById(R.id.activity_item_detail_tv_date)
    TextView tvDate;

    @ViewById(R.id.activity_item_detail_ll_bottom)
    LinearLayout llBottom;

    @ViewById(R.id.activity_item_detail_btn_edit)
    Button btnEdit;

    @ViewById(R.id.activity_item_detail_btn_delete)
    Button btnDelete;

    @ViewById(R.id.activity_item_detail_iv_category)
    ImageView ivCategory;

    @ViewById(R.id.activity_item_detail_iv_detail)
    ImageView ivDetail;

    @ViewById(R.id.activity_item_detail_iv_date)
    ImageView ivDate;

    @Extra(ITEM_TYPE)
    String itemType;

    @Extra(ITEM_CREATED_TIME)
    long itemCreatedTime;

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

    @AfterInject
    void afterInject() {
        DaggerItemDetailComponent.builder()
                .applicationComponent(application.getApplicationComponent())
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    public ItemDetailPresenter createPresenter() {
        return presenter;
    }

    @AfterViews
    void init() {
        setTitle(ItemType.INCOME.toString().equals(itemType)
                ? getString(R.string.income) : getString(R.string.payment));

        changeActionBarAndStatusBarColor(itemType);

        changeUiByItemType();

        setAllFields();
    }

    private void setAllFields() {
        tvValue.setText(itemValue);
        tvCategory.setText(itemCategory);
        tvDetail.setText(itemDetail);
        tvDate.setText(itemDate);
    }

    private void changeUiByItemType() {
        int mainColor = ContextCompat.getColor(getApplicationContext(),
                ItemType.INCOME.toString().equals(itemType) ? R.color.colorPrimary : R.color.orange);
        llBottom.setBackgroundColor(mainColor);

        ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(),
                ItemType.INCOME.toString().equals(itemType)
                        ? R.color.selector_white_green_color : R.color.selector_white_orange_color);
        btnEdit.setTextColor(colorStateList);
        btnDelete.setTextColor(colorStateList);

        tvValue.setTextColor(mainColor);
        tvCategory.setTextColor(mainColor);
        tvDetail.setTextColor(mainColor);
        tvDate.setTextColor(mainColor);

        ivCategory.setImageResource(ItemType.PAYMENT.toString().equals(itemType)
                ? R.drawable.ic_category_orange : R.drawable.ic_category);
        ivDetail.setImageResource(ItemType.PAYMENT.toString().equals(itemType)
                ? R.drawable.ic_detail_orange : R.drawable.ic_detail_green);
        ivDate.setImageResource(ItemType.PAYMENT.toString().equals(itemType)
                ? R.drawable.ic_date_orange : R.drawable.ic_date_green);
    }

    @Click(R.id.activity_item_detail_btn_edit)
    void clickEdit() {
        AddItemActivity_
                .intent(this)
                .extra(SCREEN_TITLE, getTitle().toString())
                .extra(ITEM_TYPE, itemType)
                .extra(IS_EDIT_ITEM, true)
                .extra(ITEM_ID, itemId)
                .extra(ITEM_CREATED_TIME, itemCreatedTime)
                .extra(ITEM_VALUE, itemValue)
                .extra(ITEM_CATEGORY, itemCategory)
                .extra(ITEM_DETAIL, itemDetail)
                .extra(ITEM_DATE, itemDate)
                .startForResult(EDIT_ITEM_REQUEST);
    }

    @Click(R.id.activity_item_detail_btn_delete)
    void clickDelete() {
        ConfirmDeleteDialog confirmDeleteDialog = new ConfirmDeleteDialog_();
        confirmDeleteDialog.setOnDeleteListener(() -> presenter.deleteItem(itemId));
        confirmDeleteDialog.show(getSupportFragmentManager(), ConfirmDeleteDialog.class.getSimpleName());
    }

    @OnActivityResult(EDIT_ITEM_REQUEST)
    void onEditResult(int resultCode, Intent data) {
        if (resultCode == EDIT_ITEM_RESULT) {
            if (data != null) {
                String value = data.getStringExtra(ITEM_VALUE);
                String category = data.getStringExtra(ITEM_CATEGORY);
                String detail = data.getStringExtra(ITEM_DETAIL);
                String date = data.getStringExtra(ITEM_DATE);
                if (!TextUtils.isEmpty(value)) {
                    tvValue.setText(value);
                }
                if (!TextUtils.isEmpty(category)) {
                    tvCategory.setText(category);
                }
                if (!TextUtils.isEmpty(detail)) {
                    tvDetail.setText(detail);
                }
                if (!TextUtils.isEmpty(date)) {
                    tvDate.setText(date);
                }
            }
            setResult(EDIT_ITEM_RESULT);
        }
    }

    @Override
    public void deleteItemSuccessful() {
        setResult(DELETE_ITEM_RESULT);
        finish();
    }

    @Override
    public void deleteItemFailed(Throwable throwable) {
        Toast.makeText(application, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
