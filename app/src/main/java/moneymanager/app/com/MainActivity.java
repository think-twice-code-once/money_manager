package moneymanager.app.com;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    private ActionBarDrawerToggle actionBarDrawerToggle;

    @AfterViews
    void init() {

        setUpActionBarDrawer();

        initOptionsForSlidingMenu();
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

    private void initOptionsForSlidingMenu() {
        List<Option> options = new ArrayList<>();
        options.add(new Option(R.mipmap.ic_launcher, "Home", "Detail about home"));
        options.add(new Option(R.mipmap.ic_launcher, "Report", "Detail about report"));
        options.add(new Option(R.mipmap.ic_launcher, "Contact", "Detail about contact"));
        OptionAdapter optionAdapter = new OptionAdapter(getApplicationContext(),
                R.layout.item_option, options);
        lvOptions.setAdapter(optionAdapter);
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
