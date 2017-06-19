package moneymanager.app.com.domains.report;


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

@EFragment(R.layout.fragment_report)
public class ReportFragment extends MvpFragment<ReportView, ReportPresenter> implements ReportView {

    @NonNull
    @Override
    public ReportPresenter createPresenter() {
        return new ReportPresenter();
    }

    @AfterViews
    void init() {

    }
}
