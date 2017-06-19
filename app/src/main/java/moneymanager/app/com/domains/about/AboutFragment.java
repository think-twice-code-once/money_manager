package moneymanager.app.com.domains.about;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import moneymanager.app.com.R;

/**
 * -> Created by Think-Twice-Code-Once on 6/18/2017.
 */

@EFragment(R.layout.fragment_about)
public class AboutFragment extends MvpFragment<AboutView, AboutPresenter> {
    @NonNull
    @Override
    public AboutPresenter createPresenter() {
        return new AboutPresenter();
    }

    @AfterViews
    void init() {

    }
}
