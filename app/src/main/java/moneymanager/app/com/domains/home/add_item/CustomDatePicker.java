package moneymanager.app.com.domains.home.add_item;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

import moneymanager.app.com.R;
import moneymanager.app.com.models.ItemType;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 7/3/2017.
 */

public class CustomDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private String itemType;
    private OnSelectDateListener onSelectDateListener;

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setOnSelectDateListener(OnSelectDateListener onSelectDateListener) {
        this.onSelectDateListener = onSelectDateListener;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int theme = ItemType.INCOME.toString().equals(itemType)
                ? R.style.GreenDatePickerTheme : R.style.OrangeDatePickerTheme;
        return new DatePickerDialog(getActivity(), theme, this, year, month, day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        if (onSelectDateListener  != null) {
            onSelectDateListener.onSelectDate(calendar.getTimeInMillis());
        }
    }

    public interface OnSelectDateListener {
       void onSelectDate(long createdTime);
    }
}
