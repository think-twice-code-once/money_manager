package moneymanager.app.com.domains.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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
import moneymanager.app.com.domains.home.add_item.CustomDatePicker;
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
    CustomSpinner sFilter;

    @ViewById(R.id.fragment_home_tv_range_from)
    TextView tvRangeFrom;

    @ViewById(R.id.fragment_home_tv_range_to)
    TextView tvRangeTo;

    @ViewById(R.id.fragment_home_ll_range)
    LinearLayout llRange;

    @App
    MainApplication application;

    @Inject
    HomePresenter presenter;

    private ItemAdapter itemAdapter;
    private String previousFilterType;
    private long fromCreatedTime = -1, toCreatedTime = -1;
    private CustomDatePicker customDatePicker;

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
        Context context = getContext();
        previousFilterType = FilterType.LIFE_TIME.getString(context);
        String[] itemFilters = {FilterType.TODAY.getString(context), FilterType.YESTERDAY.getString(context),
                FilterType.PICK_A_DAY.getString(context), FilterType.THIS_WEEK.getString(context),
                FilterType.THIS_MONTH.getString(context), FilterType.THIS_YEAR.getString(context),
                FilterType.RANGE.getString(context), FilterType.LIFE_TIME.getString(context)};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, itemFilters);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sFilter.setAdapter(spinnerAdapter);
        sFilter.setSelection(sFilter.getCount() - 1);
        sFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String valueOfSelectedItem = itemFilters[position];
                if (!previousFilterType.equals(valueOfSelectedItem)
                        || previousFilterType.equals(FilterType.PICK_A_DAY.getString(context))
                        || previousFilterType.equals(FilterType.RANGE.getString(context))) {
                    previousFilterType = valueOfSelectedItem;
                    if (!valueOfSelectedItem.equals(FilterType.RANGE.getString(context))) {
                        hideRange();
                    }
                    if (valueOfSelectedItem.equals(FilterType.TODAY.getString(context))) {
                        getFilteredItems(FilterType.TODAY);
                    } else if (valueOfSelectedItem.equals(FilterType.YESTERDAY.getString(context))) {
                        getFilteredItems(FilterType.YESTERDAY);
                    } else if (valueOfSelectedItem.equals(FilterType.PICK_A_DAY.getString(context))) {
                        getFilteredItems(FilterType.PICK_A_DAY);
                    } else if (valueOfSelectedItem.equals(FilterType.THIS_WEEK.getString(context))) {
                        getFilteredItems(FilterType.THIS_WEEK);
                    } else if (valueOfSelectedItem.equals(FilterType.THIS_MONTH.getString(context))) {
                        getFilteredItems(FilterType.THIS_MONTH);
                    } else if (valueOfSelectedItem.equals(FilterType.THIS_YEAR.getString(context))) {
                        getFilteredItems(FilterType.THIS_YEAR);
                    } else if (valueOfSelectedItem.equals(FilterType.RANGE.getString(context))) {
                        getFilteredItems(FilterType.RANGE);
                    } else { //LIFETIME
                        getFilteredItems(FilterType.LIFE_TIME);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getFilteredItems(FilterType filterType) {
        Calendar calendar = Calendar.getInstance();
        switch (filterType) {
            case TODAY:
                performFilterByDay(calendar);
                break;
            case YESTERDAY:
                calendar.add(Calendar.DATE, -1);
                performFilterByDay(calendar);
                break;
            case PICK_A_DAY:
                customDatePicker = new CustomDatePicker();
                customDatePicker.setItemType(ItemType.INCOME.toString());
                customDatePicker.setShouldPickTime(false);
                customDatePicker.setOnSelectDateTimeListener(createdTime -> {
                    calendar.setTimeInMillis(createdTime);
                    performFilterByDay(calendar);
                });
                customDatePicker.show(getFragmentManager(), CustomDatePicker.class.getSimpleName());
                break;
            case THIS_WEEK:
                setTimeEndOfDay(calendar);
                toCreatedTime = calendar.getTimeInMillis();
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                setTimeBeginOfDay(calendar);
                fromCreatedTime = calendar.getTimeInMillis();
                presenter.getItems(fromCreatedTime, toCreatedTime);
                break;
            case THIS_MONTH:
                setTimeEndOfDay(calendar);
                toCreatedTime = calendar.getTimeInMillis();
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                setTimeBeginOfDay(calendar);
                fromCreatedTime = calendar.getTimeInMillis();
                presenter.getItems(fromCreatedTime, toCreatedTime);
                break;
            case THIS_YEAR:
                setTimeEndOfDay(calendar);
                toCreatedTime = calendar.getTimeInMillis();
                calendar.set(Calendar.MONTH, Calendar.JANUARY);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                setTimeBeginOfDay(calendar);
                fromCreatedTime = calendar.getTimeInMillis();
                presenter.getItems(fromCreatedTime, toCreatedTime);
                break;
            case RANGE:
                customDatePicker = new CustomDatePicker();
                customDatePicker.setTitle(getString(R.string.select_start_day));
                customDatePicker.setItemType(ItemType.INCOME.toString());
                customDatePicker.setShouldPickTime(false);
                customDatePicker.setOnSelectDateTimeListener(fromTime -> {
                    calendar.setTimeInMillis(fromTime);
                    setTimeBeginOfDay(calendar);
                    fromCreatedTime = calendar.getTimeInMillis();

                    customDatePicker = new CustomDatePicker();
                    customDatePicker.setTitle(getString(R.string.select_end_day));
                    customDatePicker.setItemType(ItemType.INCOME.toString());
                    customDatePicker.setShouldPickTime(false);
                    customDatePicker.setOnSelectDateTimeListener(toTime -> {
                        calendar.setTimeInMillis(toTime);
                        setTimeEndOfDay(calendar);
                        toCreatedTime = calendar.getTimeInMillis();
                        if (toCreatedTime >= fromCreatedTime) {
                            presenter.getItems(fromCreatedTime, toCreatedTime);
                            showRange(fromCreatedTime, toCreatedTime);
                        } else {
                            Toast.makeText(application, getString(R.string.invalid_range), Toast.LENGTH_LONG).show();
                        }
                    });
                    customDatePicker.show(getFragmentManager(), CustomDatePicker.class.getSimpleName());
                });
                customDatePicker.show(getFragmentManager(), CustomDatePicker.class.getSimpleName());
                break;
            default:
                presenter.getAllItems();
                fromCreatedTime = -1;
                toCreatedTime = -1;
                break;
        }
    }

    private void showRange(long fromTime, long toTime) {
        showRange();
        tvRangeFrom.setText(AppUtil.getDateStringFromMillisecond(fromTime));
        tvRangeTo.setText(AppUtil.getDateStringFromMillisecond(toTime));
    }

    private void showRange() {
        llRange.setVisibility(View.VISIBLE);
    }

    private void hideRange() {
        llRange.setVisibility(View.GONE);
    }

    private void setTimeBeginOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    private void setTimeEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
    }

    private void performFilterByDay(Calendar calendar) {
        setTimeBeginOfDay(calendar);
        fromCreatedTime = calendar.getTimeInMillis();
        setTimeEndOfDay(calendar);
        toCreatedTime = calendar.getTimeInMillis();
        presenter.getItems(fromCreatedTime, toCreatedTime);
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
            if (fromCreatedTime != - 1 && toCreatedTime != -1) {
               presenter.getItems(fromCreatedTime, toCreatedTime);
            } else {
                presenter.getAllItems();
                sFilter.setSelection(sFilter.getCount() - 1);
            }
        }
    }

    @OnActivityResult(EDIT_ITEM_REQUEST)
    void onEditResult(int resultCode) {
        if (resultCode == EDIT_ITEM_RESULT || resultCode == DELETE_ITEM_RESULT) {
            if (fromCreatedTime != - 1 && toCreatedTime != -1) {
                presenter.getItems(fromCreatedTime, toCreatedTime);
            } else {
                presenter.getAllItems();
                sFilter.setSelection(sFilter.getCount() - 1);
            }
        }
    }

    @Override
    public void getItemsSuccessful(List<Item> items) {
        if (items.size() > 0) {
            tvPrompt.setVisibility(View.GONE);
        }
        itemAdapter.clear();
        itemAdapter.addAll(items);
        rvHistory.scrollToPosition(0);
        calculateBalance(items);
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
    public void getItemsFailed(Throwable throwable) {
        Toast.makeText(application, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Click(R.id.fragment_home_fl_balance)
    void clickBalance() {
        PromptBalanceDialog promptBalanceDialog = new PromptBalanceDialog_();
        promptBalanceDialog.setBalance(tvBalance.getText().toString());
        promptBalanceDialog.show(getFragmentManager(), PromptBalanceDialog.class.getSimpleName());
    }
}
