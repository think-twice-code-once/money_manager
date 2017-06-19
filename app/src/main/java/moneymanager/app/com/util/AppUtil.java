package moneymanager.app.com.util;

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
}
