package moneymanager.app.com.domains.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import moneymanager.app.com.R;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/29/2017.
 */

@EFragment(R.layout.dialog_balance_prompt)
public class PromptBalanceDialog extends DialogFragment {

    @ViewById(R.id.dialog_balance_prompt_tv_balance)
    TextView tvBalance;

    private String balance;

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
    }

    @AfterViews
    void init () {
        tvBalance.setText(balance);
    }

    @Click(R.id.dialog_balance_prompt_bnt_close)
    void clickClose() {
        dismiss();
    }
}
