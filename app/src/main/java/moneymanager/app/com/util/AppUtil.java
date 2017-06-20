package moneymanager.app.com.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/19/2017.
 */

public class AppUtil {

    public static String createUniqueId() {
        return UUID
                .randomUUID()
                .toString()
                .replace("=", "")
                .replace("/", "")
                .replace("+", "")
                .replace("-", "")
                .toLowerCase();
    }

    public static String getDateStringFromMillisecond(long createdAt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(createdAt);
        return simpleDateFormat.format(calendar.getTime());
    }
}
