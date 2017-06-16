package moneymanager.app.com;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * -> Created by Think-Twice-Code-Once on 6/16/2017.
 */

public class OptionAdapter extends ArrayAdapter<Option> {

    private Context context;
    private int itemRes;
    private List<Option> options;

    public OptionAdapter(@NonNull Context context, @LayoutRes int itemRes,
                         @NonNull List<Option> options) {
        super(context, itemRes, options);
        this.context = context;
        this.itemRes = itemRes;
        this.options = options;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        OptionViewHolder optionViewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_option, parent, false);

            optionViewHolder = new OptionViewHolder();
            optionViewHolder.ivOption = (ImageView) convertView
                    .findViewById(R.id.item_option_iv_option);
            optionViewHolder.tvTitle = (TextView) convertView
                    .findViewById(R.id.item_option_tv_title);
            optionViewHolder.tvDetail = (TextView) convertView.
                    findViewById(R.id.item_option_tv_detail);

            convertView.setTag(optionViewHolder);

        } else {
            optionViewHolder = (OptionViewHolder) convertView.getTag();
        }

        Option option = options.get(position);
        optionViewHolder.ivOption.setImageResource(option.getIconResId());
        optionViewHolder.tvTitle.setText(option.getTitle());
        optionViewHolder.tvDetail.setText(option.getDetail());

        return convertView;
    }

    private static class OptionViewHolder {
        ImageView ivOption;
        TextView tvTitle;
        TextView tvDetail;
    }
}
