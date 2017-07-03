package moneymanager.app.com.domains.home.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import moneymanager.app.com.R;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 7/3/2017.
 */

@EFragment(R.layout.dialog_confirm_delete)
public class ConfirmDeleteDialog extends DialogFragment {

    private OnDeleteListener onDeleteListener;

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
    }

    @AfterViews
    void init() {

    }

    @Click(R.id.dialog_confirm_delete_btn_delete)
    void clickDelete() {
        if (onDeleteListener != null) {
            onDeleteListener.onDelete();
        }
        dismiss();
    }

    @Click(R.id.dialog_confirm_delete_btn_cancel)
    void clickCancel() {
        dismiss();
    }

    public interface OnDeleteListener {
        void onDelete();
    }
}
