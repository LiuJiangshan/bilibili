package ljs.bilibili.data;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import ljs.bilibili.R;
import ljs.bilibili.entity.BLive;

public class BPageAdapter extends PagedListAdapter<BLive, BViewHolder> {
    public BPageAdapter() {
        super(new DiffUtil.ItemCallback<BLive>() {
            @Override
            public boolean areItemsTheSame(BLive oldItem, BLive newItem) {
                return oldItem.getStream_id() == oldItem.getStream_id();
            }

            @Override
            public boolean areContentsTheSame(BLive oldItem, BLive newItem) {
                return oldItem == newItem;
            }
        });
    }

    @NonNull
    @Override
    public BViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BViewHolder holder, int position) {
        holder.bindLive(getItem(position));
    }
}
