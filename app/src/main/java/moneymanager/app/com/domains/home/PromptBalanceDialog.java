package moneymanager.app.com.domains.home;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
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

    @ViewById(R.id.dialog_balance_prompt_tv_balance_prompt)
    TextView tvBalancePrompt;

    private String balance;

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
    }

    @SuppressWarnings("deprecation")
    @AfterViews
    void init () {
        tvBalance.setText(balance);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvBalancePrompt.setText(Html.fromHtml(getString(R.string.balance_prompt),
                    Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvBalancePrompt.setText(Html.fromHtml(getString(R.string.balance_prompt)));
        }
    }

    @Click(R.id.dialog_balance_prompt_bnt_close)
    void clickClose() {
        dismiss();
    }
}
