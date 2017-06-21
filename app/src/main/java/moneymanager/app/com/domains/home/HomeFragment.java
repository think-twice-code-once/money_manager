package moneymanager.app.com.domains.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

import moneymanager.app.com.R;
import moneymanager.app.com.domains.home.add_item.AddItemActivity_;
import moneymanager.app.com.factory.MainApplication;
import moneymanager.app.com.models.Item;
import moneymanager.app.com.models.ItemType;

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
    }

    private void getHistoryList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        rvHistory.setLayoutManager(layoutManager);

        itemAdapter = new ItemAdapter();
        rvHistory.setAdapter(itemAdapter);

        presenter.getAllItems();
    }

    @Click(R.id.fragment_home_fab_add_income)
    void clickAddIncome() {
        AddItemActivity_
                .intent(this)
                .extra(SCREEN_TITLE, getString(R.string.add_income))
                .extra(ITEM_TYPE, ItemType.INCOME.toString())
                .start();
    }

    @Click(R.id.fragment_home_fab_add_payment)
    void clickAddPayment() {
        AddItemActivity_
                .intent(this)
                .extra(SCREEN_TITLE, getString(R.string.add_payment))
                .extra(ITEM_TYPE, ItemType.PAYMENT.toString())
                .start();
    }

    @Override
    public void getAllItemsSuccessful(List<Item> items) {
        if (items.size() > 0) {
            tvPrompt.setVisibility(View.GONE);
        }
        itemAdapter.addAll(items);
    }

    @Override
    public void getAllItemsFailed(Throwable throwable) {
        Toast.makeText(application, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }
}
