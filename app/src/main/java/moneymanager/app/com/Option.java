package moneymanager.app.com;

/**
 * -> Created by Think-Twice-Code-Once on 6/16/2017.
 */

public class Option {

    private int iconResId;
    private String title;
    private String detail;

    public Option(int iconResId, String title, String detail) {
        this.iconResId = iconResId;
        this.title = title;
        this.detail = detail;
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
}
