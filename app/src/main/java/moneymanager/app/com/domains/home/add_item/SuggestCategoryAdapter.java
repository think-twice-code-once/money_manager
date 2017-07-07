package moneymanager.app.com.domains.home.add_item;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import moneymanager.app.com.R;
import moneymanager.app.com.models.Category;
import moneymanager.app.com.models.ItemType;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/28/2017.
 */

public class SuggestCategoryAdapter extends ArrayAdapter<Category> {

    private Context context;
    private int resItem;
    private List<Category> categories;

    public SuggestCategoryAdapter(@NonNull Context context, @LayoutRes int resItem, List<Category> categories) {
        super(context, resItem);
        this.context = context;
        this.resItem = resItem;
        this.categories = categories;
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
                    .findViewById(R.id.item_suggest_category_tv_category_content);
            categoryViewHolder.ivCategoryParent = (ImageView) convertView
                    .findViewById(R.id.item_suggest_category_iv_cate_parent);
            categoryViewHolder.ivCategoryChild = (ImageView) convertView
                    .findViewById(R.id.item_suggest_category_iv_cate_child);

            convertView.setTag(categoryViewHolder);
        } else {
            categoryViewHolder = (CategoryViewHolder) convertView.getTag();
        }

        Category category = getItem(position);
        if (category != null) {
            categoryViewHolder.tvCategoryContent.setText(category.getContent());
            boolean isPaymentType = ItemType.PAYMENT.toString().equals(category.getType());
            categoryViewHolder.tvCategoryContent.setTextColor(ContextCompat.getColor(context,
                    isPaymentType ? R.color.orange : R.color.colorPrimary));
            if (category.getParentId() == null ) {
                categoryViewHolder.ivCategoryParent.setVisibility(View.VISIBLE);
                categoryViewHolder.ivCategoryChild.setVisibility(View.GONE);
               if (isPaymentType) {
                   categoryViewHolder.ivCategoryParent.setImageResource(R.drawable.ic_parent_cate_orange);
               } else {
                   categoryViewHolder.ivCategoryParent.setImageResource(R.drawable.ic_parent_cate_green);
               }
            } else {
                categoryViewHolder.ivCategoryChild.setVisibility(View.VISIBLE);
                categoryViewHolder.ivCategoryParent.setVisibility(View.GONE);
                if (isPaymentType) {
                    categoryViewHolder.ivCategoryChild.setImageResource(R.drawable.ic_child_cate_orange);
                } else {
                    categoryViewHolder.ivCategoryChild.setImageResource(R.drawable.ic_child_cate_green);
                }
            }
        }

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new CategoryFilter(this, categories);
    }

    private static class CategoryViewHolder {
        TextView tvCategoryContent;
        ImageView ivCategoryParent;
        ImageView ivCategoryChild;
    }
}
