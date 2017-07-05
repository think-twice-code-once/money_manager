package moneymanager.app.com.util;

import java.text.DecimalFormat;
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

    public static String getDateTimeStringFromMillisecond(long createdAt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yy, HH:mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(createdAt);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getPrettyNumber(String valueStr, boolean isRemovedDotZero) {
        DecimalFormat decimalFormat = new DecimalFormat("########################.#####");
        valueStr = decimalFormat.format(Double.parseDouble(valueStr.replace(" ", "").replace(",", ".")));
        valueStr = valueStr.replace(",", ".");
        String prettyNumberStr = "";

        int count = 0;
        for (int i = valueStr.length() - 1; i >= 0; i--) {
            prettyNumberStr = String.valueOf(valueStr.charAt(i)) + prettyNumberStr;
            if (valueStr.charAt(i) != '.') {
                count++;
                if (count % 3 ==0) {
                    prettyNumberStr = " " + prettyNumberStr;
                }
            } else {
                count = 0;
                prettyNumberStr = prettyNumberStr.replace(" ", "");
            }
        }

        return isRemovedDotZero && prettyNumberStr.endsWith(".0")
                ? prettyNumberStr.trim().replace(".0", "") : prettyNumberStr.trim();
    }

    /*TODO: make category name capital words*/
    public static String getPrettyCategory(String inputCategory) {
        return inputCategory;
    }

    public static String getCategoryTag(String categoryName) {
        String[] words = categoryName.trim().toLowerCase().split(" +");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            stringBuilder.append(words[i]);
            if (i != (words.length -1)) {
                stringBuilder.append("_");
            }
        }
        return stringBuilder.toString();
    }
}
