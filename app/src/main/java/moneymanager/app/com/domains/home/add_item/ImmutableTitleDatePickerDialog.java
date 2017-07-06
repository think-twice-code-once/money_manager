package moneymanager.app.com.domains.home.add_item;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.widget.DatePicker;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 7/6/2017.
 */

public class ImmutableTitleDatePickerDialog extends DatePickerDialog {

    private String title;

    public void setImmutableTitle(String title) {
        this.title = title;
        setTitle(title);
    }

    public ImmutableTitleDatePickerDialog(@NonNull Context context, @StyleRes int themeResId,
                                          @Nullable OnDateSetListener listener, int year,
                                          int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onDateChanged(@NonNull DatePicker view, int year, int month, int dayOfMonth) {
        super.onDateChanged(view, year, month, dayOfMonth);
        setTitle(title);
    }
}
