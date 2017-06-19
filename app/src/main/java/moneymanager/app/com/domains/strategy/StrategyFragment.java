package moneymanager.app.com.domains.strategy;


import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import moneymanager.app.com.R;

/**
 * -> Created by Think-Twice-Code-Once on 6/18/2017.
 */

@EFragment(R.layout.fragment_strategy)
public class StrategyFragment extends MvpFragment<StrategyView, StrategyPresenter>
        implements StrategyView {

    @NonNull
    @Override
    public StrategyPresenter createPresenter() {
        return new StrategyPresenter();
    }

    @AfterViews
    void init() {

    }

}
