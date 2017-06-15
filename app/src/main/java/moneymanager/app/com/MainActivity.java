package moneymanager.app.com;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import moneymanager.app.com.R;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements DrawerLayout.DrawerListener {

    @ViewById(R.id.activity_main_dl_root)
    DrawerLayout drawerLayout;

    @AfterViews
    void init() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        drawerLayout.addDrawerListener(this);

    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
