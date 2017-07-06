package moneymanager.app.com.domains.home.add_item;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;

import java.util.Calendar;

import moneymanager.app.com.R;
import moneymanager.app.com.models.ItemType;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 7/3/2017.
 */

public class CustomDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private String itemType;
    private OnSelectDateTimeListener onSelectDateTimeListener;
    private int theme;
    private boolean shouldPickTime = true;
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setOnSelectDateTimeListener(OnSelectDateTimeListener onSelectDateTimeListener) {
        this.onSelectDateTimeListener = onSelectDateTimeListener;
    }

    public void setShouldPickTime(boolean shouldPickTime) {
        this.shouldPickTime = shouldPickTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        theme = ItemType.INCOME.toString().equals(itemType)
                ? R.style.GreenDateTimePickerTheme : R.style.OrangeDateTimePickerTheme;
        DatePickerDialog datePickerDialog;
        if (!TextUtils.isEmpty(title)) {
            datePickerDialog = new ImmutableTitleDatePickerDialog(getActivity(), theme, this, year, month, day);
            ((ImmutableTitleDatePickerDialog)datePickerDialog).setImmutableTitle(title);
        } else {
            datePickerDialog = new DatePickerDialog(getActivity(), theme, this, year, month, day);
        }
        return datePickerDialog;
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        if (shouldPickTime) {
            CustomTimePicker customTimePicker = new CustomTimePicker();
            customTimePicker.setTheme(theme);
            customTimePicker.setDefaultTime(calendar);
            customTimePicker.setOnSelectDateTimeListener(onSelectDateTimeListener);
            customTimePicker.show(getFragmentManager(), CustomTimePicker.class.getSimpleName());
        } else {
            if (onSelectDateTimeListener != null) {
                onSelectDateTimeListener.onSelectDate(calendar.getTimeInMillis());
            }
        }
    }

}
