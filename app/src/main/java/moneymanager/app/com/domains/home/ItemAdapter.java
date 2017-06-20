package moneymanager.app.com.domains.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import moneymanager.app.com.R;
import moneymanager.app.com.models.Item;
import moneymanager.app.com.models.ItemType;
import moneymanager.app.com.util.AppUtil;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/20/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> items;

    public ItemAdapter() {
        items = new ArrayList<>();
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = items.get(position);
        Context context = holder.itemView.getContext();

        holder.tvTime.setText(AppUtil.getDateStringFromMillisecond(item.getCreatedAt()));
        holder.tvValue.setText(String.valueOf(item.getValue()));
        holder.tvCategory.setText(item.getCategory().getName());
        holder.cvRoot.setCardBackgroundColor(ItemType.PAY.toString().equals(item.getItemType())
                ? ContextCompat.getColor(context, R.color.orange)
                : ContextCompat.getColor(context, R.color.colorPrimary));
        holder.itemView.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addAll(List<Item> newItems) {
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime;
        TextView tvValue;
        TextView tvCategory;
        CardView cvRoot;

        public ItemViewHolder(View itemView) {
            super(itemView);

            tvTime = (TextView) itemView.findViewById(R.id.item_tv_time);
            tvValue = (TextView) itemView.findViewById(R.id.item_tv_value);
            tvCategory = (TextView) itemView.findViewById(R.id.item_tv_category);
            cvRoot = (CardView) itemView.findViewById(R.id.item_cv_root);

        }
    }
}
