package moneymanager.app.com.domains.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import moneymanager.app.com.R;
import moneymanager.app.com.domains.home.add_item.AddItemActivity_;
import moneymanager.app.com.factory.MainApplication;
import moneymanager.app.com.models.Item;
import moneymanager.app.com.models.ItemType;
import moneymanager.app.com.util.AppUtil;

import static moneymanager.app.com.util.Constants.ADD_NEW_ITEM_REQUEST;
import static moneymanager.app.com.util.Constants.ADD_NEW_ITEM_RESULT;
import static moneymanager.app.com.util.Constants.DELETE_ITEM_RESULT;
import static moneymanager.app.com.util.Constants.EDIT_ITEM_REQUEST;
import static moneymanager.app.com.util.Constants.EDIT_ITEM_RESULT;
import static moneymanager.app.com.util.Constants.ITEM_TYPE;
import static moneymanager.app.com.util.Constants.SCREEN_TITLE;

/**
 * -> Created by Think-Twice-Code-Once on 6/18/2017.
 */

@EFragment(R.layout.fragment_home)
public class HomeFragment extends MvpFragment<HomeView, HomePresenter> implements HomeView {

    @ViewById(R.id.fragment_home_rv_history)
    RecyclerView rvHistory;

    @ViewById(R.id.fragment_home_tv_history_prompt)
    TextView tvPrompt;

    @ViewById(R.id.fragment_home_tv_payment)
    TextView tvPayment;

    @ViewById(R.id.fragment_home_tv_income)
    TextView tvIncome;

    @ViewById(R.id.fragment_home_tv_balance)
    TextView tvBalance;

    @ViewById(R.id.fragment_home_s_filter)
    Spinner sFilter;

    @App
    MainApplication application;

    @Inject
    HomePresenter presenter;

    private ItemAdapter itemAdapter;

    @AfterInject
    void afterInject() {
        DaggerHomeComponent.builder()
                .applicationComponent(application.getApplicationComponent())
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    public HomePresenter createPresenter() {
        return presenter;
    }

    @AfterViews
    void init() {
        getHistoryList();

        presenter.initDefaultCategories();

        initFilter();
    }

    private void getHistoryList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        rvHistory.setLayoutManager(layoutManager);

        itemAdapter = new ItemAdapter(this);
        rvHistory.setAdapter(itemAdapter);

        presenter.getAllItems();
    }

    private void initFilter() {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.item_filters, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sFilter.setAdapter(spinnerAdapter);
    }

    @Click(R.id.fragment_home_fab_add_income)
    void clickAddIncome() {
        AddItemActivity_
                .intent(this)
                .extra(SCREEN_TITLE, getString(R.string.add_income))
                .extra(ITEM_TYPE, ItemType.INCOME.toString())
                .startForResult(ADD_NEW_ITEM_REQUEST);
    }

    @Click(R.id.fragment_home_fab_add_payment)
    void clickAddPayment() {
        AddItemActivity_
                .intent(this)
                .extra(SCREEN_TITLE, getString(R.string.add_payment))
                .extra(ITEM_TYPE, ItemType.PAYMENT.toString())
                .startForResult(ADD_NEW_ITEM_REQUEST);
    }

    @OnActivityResult(ADD_NEW_ITEM_REQUEST)
    void onResult(int resultCode) {
        if (resultCode == ADD_NEW_ITEM_RESULT) {
            presenter.getAllItems();
        }
    }

    @OnActivityResult(EDIT_ITEM_REQUEST)
    void onEditResult(int resultCode) {
        if (resultCode == EDIT_ITEM_RESULT || resultCode == DELETE_ITEM_RESULT) {
            presenter.getAllItems();
        }
    }

    @Override
    public void getAllItemsSuccessful(List<Item> items) {
         if (items.size() > 0) {
            tvPrompt.setVisibility(View.GONE);
        }
        itemAdapter.clear();
        itemAdapter.addAll(items);
        rvHistory.scrollToPosition(0);
        calculateBalance(items);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(items.get(2).getCreatedAt());
        String dateStr = calendar.getTime().toString();
        calendar.setTimeInMillis(items.get(4).getCreatedAt());
        dateStr = calendar.getTime().toString();
        int i = 0;
    }

    private void calculateBalance(List<Item> items) {
        float payment = 0;
        float income = 0;
        for (Item item : items) {
            if (ItemType.PAYMENT.toString().equals(item.getItemType())) {
                payment += item.getValue();
            } else {
                income += item.getValue();
            }
        }
        tvPayment.setText(getString(R.string.payment) + ": " + AppUtil.getPrettyNumber(String.valueOf(payment), true));
        tvIncome.setText(getString(R.string.income) + ": " + AppUtil.getPrettyNumber(String.valueOf(income), true));
        tvBalance.setText(AppUtil.getPrettyNumber(String.valueOf(income - payment), true));
    }

    @Override
    public void getAllItemsFailed(Throwable throwable) {
        Toast.makeText(application, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Click(R.id.fragment_home_fl_balance)
    void clickBalance() {
        PromptBalanceDialog promptBalanceDialog = new PromptBalanceDialog_();
        promptBalanceDialog.setBalance(tvBalance.getText().toString());
        promptBalanceDialog.show(getFragmentManager(), PromptBalanceDialog.class.getSimpleName());
    }
}
