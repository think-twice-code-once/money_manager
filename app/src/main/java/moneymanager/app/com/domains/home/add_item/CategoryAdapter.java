package moneymanager.app.com.domains.home.add_item;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import moneymanager.app.com.R;
import moneymanager.app.com.models.Category;
import moneymanager.app.com.models.ItemType;

import static android.view.View.GONE;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/28/2017.
 */

public class CategoryAdapter extends ArrayAdapter<Category> {

    private Context context;
    private int resItem;

    public CategoryAdapter(@NonNull Context context, @LayoutRes int resItem) {
        super(context, resItem);
        this.context = context;
        this.resItem = resItem;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CategoryViewHolder categoryViewHolder;
        if (convertView ==null) {
            categoryViewHolder = new CategoryViewHolder();
            convertView = layoutInflater.inflate(resItem, parent, false);

            categoryViewHolder.tvCategoryContent = (TextView) convertView
                    .findViewById(R.id.item_category_tv_category_content);
            categoryViewHolder.vMarginStart = convertView
                    .findViewById(R.id.item_category_v_margin_start);

            convertView.setTag(categoryViewHolder);
        } else {
            categoryViewHolder = (CategoryViewHolder) convertView.getTag();
        }

        Category category = getItem(position);
        if (category != null) {
            categoryViewHolder.tvCategoryContent.setText(category.getContent());
            categoryViewHolder.tvCategoryContent.setTextColor(ContextCompat.getColor(context,
                    ItemType.INCOME.toString().equals(category.getType()) ? R.color.colorPrimary : R.color.orange));
            categoryViewHolder.vMarginStart.setVisibility(
                    !TextUtils.isEmpty(category.getParentId()) ? View.VISIBLE : GONE);
        }

        return convertView;
    }

    private static class CategoryViewHolder {
        TextView tvCategoryContent;
        View vMarginStart;
    }
}
