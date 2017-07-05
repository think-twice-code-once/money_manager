package moneymanager.app.com.domains.home;

import android.content.Context;

import moneymanager.app.com.R;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 7/5/2017.
 */

public enum FilterType {

    TODAY(R.string.today), YESTERDAY(R.string.yesterday), PICK_A_DAY(R.string.pick_a_day),
    THIS_WEEK(R.string.this_week), THIS_MONTH(R.string.this_month), THIS_YEAR(R.string.this_year),
    RANGE(R.string.range), LIFE_TIME(R.string.lifetime);

    private int resString;

    FilterType(int resString) {
        this.resString = resString;
    }

    public String getString(Context context) {
        return context.getString(resString);
    }
}
