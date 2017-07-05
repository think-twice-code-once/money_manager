package moneymanager.app.com.domains.home.add_item;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

import moneymanager.app.com.R;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 7/5/2017.
 */

public class CustomTimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private int theme;
    private Calendar defaultTime;
    private OnSelectDateTimeListener onSelectDateTimeListener;

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public void setDefaultTime(Calendar defaultTime) {
        this.defaultTime = defaultTime;
    }

    public void setOnSelectDateTimeListener(OnSelectDateTimeListener onSelectDateTimeListener) {
        this.onSelectDateTimeListener = onSelectDateTimeListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), theme, this,
                defaultTime.get(Calendar.HOUR_OF_DAY), defaultTime.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle(getString(R.string.select_time));
        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        defaultTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        defaultTime.set(Calendar.MINUTE, minute);
        if (onSelectDateTimeListener != null) {
            onSelectDateTimeListener.onSelectDate(defaultTime.getTimeInMillis());
        }
    }
}
