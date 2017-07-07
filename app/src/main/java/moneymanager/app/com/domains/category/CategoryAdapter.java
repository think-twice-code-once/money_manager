package moneymanager.app.com.domains.category;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moneymanager.app.com.R;
import moneymanager.app.com.models.Category;
import moneymanager.app.com.models.ItemType;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 7/6/2017.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Category> parentCategories;
    private Map<String, List<Category>> childCategories;

    public CategoryAdapter(Context context, List<Category> parentCategories,
                           Map<String, List<Category>> childCategories) {
        this.context = context;
        this.parentCategories = new ArrayList<>(parentCategories);
        this.childCategories = new HashMap<>(childCategories);
    }

    @Override
    public int getGroupCount() {
        return parentCategories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childCategories.get(parentCategories.get(groupPosition).getTag()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentCategories.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childCategories.get(parentCategories.get(groupPosition).getTag()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View parentCategoryView =  layoutInflater.inflate(R.layout.item_parent_category, parent, false);
        ImageView ivIndicator = (ImageView) parentCategoryView.findViewById(R.id.item_parent_category_iv_indicator);
        TextView tvCategory = (TextView) parentCategoryView.findViewById(R.id.item_parent_category_tv_category_content);
        ImageView ivCategoryType = (ImageView) parentCategoryView.findViewById(R.id.item_parent_category_iv_cate_type);

        Category categoryParent = parentCategories.get(groupPosition);
        parentCategoryView.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_opacity));
        tvCategory.setText(categoryParent.getContent());
        tvCategory.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        if (ItemType.PAYMENT.toString().equals(categoryParent.getType())) {
            tvCategory.setTextColor(ContextCompat.getColor(context, R.color.orange));
            ivIndicator.setImageResource(R.drawable.ic_down_arrow_orange);
            ivCategoryType.setImageResource(R.drawable.ic_parent_cate_orange);
        } else {
            tvCategory.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            ivIndicator.setImageResource(R.drawable.ic_down_arrow_green);
            ivCategoryType.setImageResource(R.drawable.ic_parent_cate_green);
        }

        if (getChildrenCount(groupPosition) == 0) {
            ivIndicator.setVisibility(View.INVISIBLE);
        } else {
            ivIndicator.setVisibility(View.VISIBLE);
        }

        return parentCategoryView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View childCategoryView =  layoutInflater.inflate(R.layout.item_child_category, parent, false);
        TextView tvCategory = (TextView) childCategoryView.findViewById(R.id.item_child_category_tv_category_content);
        ImageView ivCategoryType = (ImageView) childCategoryView.findViewById(R.id.item_child_category_iv_cate_type);

        Category categoryChild = childCategories.get(parentCategories.get(groupPosition).getTag()).get(childPosition);
        tvCategory.setText(categoryChild.getContent());
        if (ItemType.PAYMENT.toString().equals(categoryChild.getType())) {
            tvCategory.setTextColor(ContextCompat.getColor(context, R.color.orange));
            ivCategoryType.setImageResource(R.drawable.ic_child_cate_orange);
        } else {
            tvCategory.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            ivCategoryType.setImageResource(R.drawable.ic_child_cate_green);
        }

        return childCategoryView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void addAllParentCategories(List<Category> categories) {
        parentCategories.clear();
        parentCategories.addAll(categories);
    }

    public void addAllChildCategories(Map<String, List<Category>> categories) {
        childCategories.clear();
        childCategories = new HashMap<>(categories);
        notifyDataSetChanged();
    }
}
