package moneymanager.app.com.domains.main;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import moneymanager.app.com.R;
import moneymanager.app.com.domains.BaseActivity;
import moneymanager.app.com.domains.about.AboutFragment_;
import moneymanager.app.com.domains.category.CategoryFragment_;
import moneymanager.app.com.domains.home.HomeFragment_;
import moneymanager.app.com.domains.report.ReportFragment_;
import moneymanager.app.com.domains.strategy.StrategyFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {

    @ViewById(R.id.activity_main_dl_root)
    DrawerLayout dlRoot;

    @ViewById(R.id.activity_main_rl_drawer_pane)
    RelativeLayout rlDrawerPane;

    @ViewById(R.id.activity_main_fl_main_content)
    FrameLayout flMainContent;

    @ViewById(R.id.activity_main_lv_options)
    ListView lvOptions;

    private List<String> titles;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    @AfterViews
    void init() {

        setUpActionBarDrawer();

        initData();

        initOptionsForSlidingMenu();

        replaceScreen(0);
    }

    private void setUpActionBarDrawer() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, dlRoot,
                R.string.sliding_menu_opened, R.string.sliding_menu_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }
        };

        dlRoot.addDrawerListener(actionBarDrawerToggle);
        dlRoot.setScrimColor(Color.TRANSPARENT);
    }

    private void initData() {
        titles = new ArrayList<>();
        titles.add(getString(R.string.home));
        titles.add(getString(R.string.category));
        titles.add(getString(R.string.report));
        titles.add(getString(R.string.strategy));
        titles.add(getString(R.string.about));
    }

    private void initOptionsForSlidingMenu() {
        List<Option> options = new ArrayList<>();
        options.add(new Option(R.drawable.ic_home, titles.get(0), getString(R.string.home_detail), true));
        options.add(new Option(R.drawable.ic_category, titles.get(1), getString(R.string.category_detail), false));
        options.add(new Option(R.drawable.ic_report, titles.get(2), getString(R.string.report_detail), false));
        options.add(new Option(R.drawable.ic_strategy, titles.get(3), getString(R.string.strategy_detail), true));
        options.add(new Option(R.drawable.ic_about, titles.get(4), getString(R.string.about_detail), false));

        OptionAdapter optionAdapter = new OptionAdapter(getApplicationContext(),
                R.layout.item_option, options);
        lvOptions.setAdapter(optionAdapter);

        lvOptions.setOnItemClickListener((parent, view, position, id) -> {
            dlRoot.closeDrawers();
            setTitle(titles.get(position));
            new Handler().postDelayed(() -> replaceScreen(position), 250);
            setTitle(titles.get(position));
            lvOptions.setItemChecked(position, true);
        });
    }

    private void replaceScreen(int position) {
        Fragment newFragment;
        switch (position) {
            case 1:
                newFragment = new CategoryFragment_();
                break;
            case 2:
                newFragment = new ReportFragment_();
                break;
            case 3:
                newFragment = new StrategyFragment_();
                break;
            case 4:
                newFragment = new AboutFragment_();
                break;
            default:
                newFragment = new HomeFragment_();
                break;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fl_main_content, newFragment)
                .commit();

        setTitle(titles.get(position));
        lvOptions.setItemChecked(position, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item)
                || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }
}
