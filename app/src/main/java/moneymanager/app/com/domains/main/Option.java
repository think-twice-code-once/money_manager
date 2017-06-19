package moneymanager.app.com.domains.main;

/**
 * -> Created by Think-Twice-Code-Once on 6/16/2017.
 */

public class Option {

    private int iconResId;
    private String title;
    private String detail;
    private boolean hasSeparator;

    public Option(int iconResId, String title, String detail, boolean hasSeparator) {
        this.iconResId = iconResId;
        this.title = title;
        this.detail = detail;
        this.hasSeparator = hasSeparator;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setHasSeparator(boolean hasSeparator) {
        this.hasSeparator = hasSeparator;
    }

    public boolean hasSeparator() {
        return hasSeparator;
    }
}
