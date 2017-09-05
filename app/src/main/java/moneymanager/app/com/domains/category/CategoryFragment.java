package moneymanager.app.com.domains.category;


import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import moneymanager.app.com.R;
import moneymanager.app.com.domains.BaseActivity;
import moneymanager.app.com.domains.category.add_category.AddCategoryActivity_;
import moneymanager.app.com.factory.MainApplication;
import moneymanager.app.com.models.Category;
import moneymanager.app.com.models.ItemType;

/**
 * -> Created by Think-Twice-Code-Once on 6/18/2017.
 */

@EFragment(R.layout.fragment_category)
public class CategoryFragment extends MvpFragment<CategoryView, CategoryPresenter>
        implements CategoryView {

    @App
    MainApplication application;

    @Inject
    CategoryPresenter presenter;

    @ViewById(R.id.fragment_category_el_categories)
    ExpandableListView elvCategories;

    @ViewById(R.id.fragment_category_fab_add)
    FloatingActionButton fabAdd;

    @ViewById(R.id.fragment_category_rg_category_type)
    RadioGroup rgCategoryType;

    @ViewById(R.id.fragment_category_rb_payment)
    RadioButton rbPayment;

    @ViewById(R.id.fragment_category_rb_income)
    RadioButton rbIncome;

    private CategoryAdapter categoryAdapter;
    private List<Category> parentCategories;
    private Map<String, List<Category>> childCategories;
    private ItemType itemType;

    @NonNull
    @Override
    public CategoryPresenter createPresenter() {
        return presenter;
    }

    @AfterInject
    void afterInject() {
        DaggerCategoryComponent.builder()
                .applicationComponent(application.getApplicationComponent())
                .build()
                .inject(this);
    }

    @AfterViews
    void init() {
        initBasicUi();
    }

    private void initBasicUi() {
        rgCategoryType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == rbPayment.getId()) {
                ((BaseActivity) getActivity()).changeActionBarAndStatusBarColor(ItemType.PAYMENT.toString());
                changeButtonAddColor(R.color.orange);
                presenter.getParentCategories(ItemType.PAYMENT);
                itemType = ItemType.PAYMENT;
            } else if (checkedId == rbIncome.getId()) {
                ((BaseActivity) getActivity()).changeActionBarAndStatusBarColor(ItemType.INCOME.toString());
                changeButtonAddColor(R.color.colorPrimary);
                presenter.getParentCategories(ItemType.INCOME);
                itemType = ItemType.INCOME;
            }
        });
        rgCategoryType.check(rbPayment.getId());
    }

    private void changeButtonAddColor(int colorRes) {
        fabAdd.setRippleColor(ContextCompat.getColor(getContext(), colorRes));
        fabAdd.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), colorRes)));
    }

    @Click(R.id.fragment_category_fab_add)
    void clickAdd() {
        AddCategoryActivity_.intent(this).start();
    }

    @Override
    public void getParentCategoriesSuccessful(List<Category> categories) {
        parentCategories = new ArrayList<>(categories);
        childCategories = new HashMap<>();
        for (Category category: categories) {
            presenter.getChildCategories(itemType, category.getId(), category.getTag());
        }
    }

    @Override
    public void getParentCategoriesFailed(Throwable throwable) {
    }

    @Override
    public void getChildCategoriesSuccessful(String parentTag, List<Category> categories) {
        childCategories.put(parentTag, categories);
        if (categoryAdapter == null) {
            categoryAdapter = new CategoryAdapter(getContext(), parentCategories, childCategories);
            elvCategories.setAdapter(categoryAdapter);
        } else {
            categoryAdapter.addAllParentCategories(parentCategories);
            categoryAdapter.addAllChildCategories(childCategories);
        }
    }

    @Override
    public void getChildCategoriesFailed(Throwable throwable) {
    }
}
