package moneymanager.app.com.domains.suggestion;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import moneymanager.app.com.R;

/**
 * -> Created by Think-Twice-Code-Once on 6/18/2017.
 */

@EFragment(R.layout.fragment_suggestion)
public class SuggestionFragment extends MvpFragment<SuggestionView, SuggestionPresenter>
        implements SuggestionView {

    @NonNull
    @Override
    public SuggestionPresenter createPresenter() {
        return new SuggestionPresenter();
    }

    @AfterViews
    void init() {

    }

}
