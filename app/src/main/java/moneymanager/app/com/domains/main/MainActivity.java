package moneymanager.app.com.domains.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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
import moneymanager.app.com.domains.about.AboutFragment_;
import moneymanager.app.com.domains.category.CategoryFragment_;
import moneymanager.app.com.domains.home.HomeFragment_;
import moneymanager.app.com.domains.report.ReportFragment_;
import moneymanager.app.com.domains.suggestion.SuggestionFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.activity_main_dl_root)
    DrawerLayout dlRoot;

    @ViewById(R.id.activity_main_rl_drawer_pane)
    RelativeLayout rlDrawerPane;

    @ViewById(R.id.activity_main_fl_main_content)
    FrameLayout flMainContent;

    @ViewById(R.id.activity_main_lv_options)
    ListView lvOptions;

    private List<Fragment> screens;
    private List<String> titles;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    @AfterViews
    void init() {

        setUpActionBarDrawer();

        initData();

        initOptionsForSlidingMenu();

        addDefaultScreen();
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
        screens = new ArrayList<>();
        titles = new ArrayList<>();

        screens.add(new HomeFragment_());
        titles.add(getString(R.string.home));
        screens.add(new CategoryFragment_());
        titles.add(getString(R.string.category));
        screens.add(new ReportFragment_());
        titles.add(getString(R.string.report));
        screens.add(new SuggestionFragment_());
        titles.add(getString(R.string.suggestion));
        screens.add(new AboutFragment_());
        titles.add(getString(R.string.about));
    }

    private void initOptionsForSlidingMenu() {
        List<Option> options = new ArrayList<>();
        options.add(new Option(R.mipmap.ic_launcher, titles.get(0), getString(R.string.home_detail)));
        options.add(new Option(R.mipmap.ic_launcher, titles.get(1), getString(R.string.category_detail)));
        options.add(new Option(R.mipmap.ic_launcher, titles.get(2), getString(R.string.report_detail)));
        options.add(new Option(R.mipmap.ic_launcher, titles.get(3), getString(R.string.suggestion_detail)));
        options.add(new Option(R.mipmap.ic_launcher, titles.get(4), getString(R.string.about_detail)));

        OptionAdapter optionAdapter = new OptionAdapter(getApplicationContext(),
                R.layout.item_option, options);
        lvOptions.setAdapter(optionAdapter);

        lvOptions.setOnItemClickListener((parent, view, position, id) -> {
            dlRoot.closeDrawers();
            setTitle(titles.get(position));

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main_fl_main_content, screens.get(position))
                    .commit();
            setTitle(titles.get(position));

            lvOptions.setItemChecked(position, true);
        });
    }

    private void addDefaultScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fl_main_content, screens.get(0))
                .commit();
        setTitle(titles.get(0));
        lvOptions.setItemChecked(0, true);
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
}
