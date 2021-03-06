package moneymanager.app.com.domains.home;

import android.content.Context;
import android.util.AttributeSet;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 7/6/2017.
 */

public class CustomSpinner extends android.support.v7.widget.AppCompatSpinner {

    public CustomSpinner(Context context) {
        super(context);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSelection(int position) {
        boolean sameSelected = (position == getSelectedItemPosition());
        super.setSelection(position);
        if (sameSelected && getOnItemSelectedListener() != null) {
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

    @Override
    public void setSelection(int position, boolean animate) {
        boolean sameSelected = (position == getSelectedItemPosition());
        super.setSelection(position, animate);
        if (sameSelected && getOnItemSelectedListener() != null) {
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }
}
