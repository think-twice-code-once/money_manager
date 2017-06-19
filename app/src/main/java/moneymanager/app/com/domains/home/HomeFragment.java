package moneymanager.app.com.domains.home;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import moneymanager.app.com.R;
import moneymanager.app.com.factory.MainApplication;
import moneymanager.app.com.models.Category;
import moneymanager.app.com.models.Item;
import moneymanager.app.com.util.AppUtil;

/**
 * -> Created by Think-Twice-Code-Once on 6/18/2017.
 */

@EFragment(R.layout.fragment_home)
public class HomeFragment extends MvpFragment<HomeView, HomePresenter> implements HomeView {

    @ViewById(R.id.fragment_home_fab_add)
    FloatingActionButton fabAdd;

    @ViewById(R.id.fragment_home_fab_subtract)
    FloatingActionButton fabSubtract;

    @App
    MainApplication application;

    @Inject
    HomePresenter presenter;

    @Inject
    Realm realm;

    @AfterInject
    void afterInject() {
        DaggerHomeComponent.builder()
                .applicationComponent(application.getApplicationComponent())
                .build()
                .inject(this);
    }

    @AfterViews
    void init() {
        List<Item> items = realm.copyFromRealm(realm.where(Item.class).findAll());

        fabAdd.setOnClickListener(v -> {
            Item item = new Item();
            item.setId(AppUtil.createUniqueId());
            item.setValue(10);

            Category category = new Category();
            category.setId(AppUtil.createUniqueId());
            category.setName("Food");

            item.setCategory(category);

            realm.executeTransactionAsync(realm -> {
                realm.copyToRealmOrUpdate(item);
            });
        });

        fabSubtract.setOnClickListener(v -> {

        });
    }

    @NonNull
    @Override
    public HomePresenter createPresenter() {
        return presenter;
    }

}
