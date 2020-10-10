package edu.aku.covid_followup_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.aku.covid_followup_app.R;
import edu.aku.covid_followup_app.contracts.MembersContract;
import edu.aku.covid_followup_app.databinding.ItemHhLayoutBinding;

public class HHListAdapter extends RecyclerView.Adapter<HHListAdapter.ViewHolder> {

    OnItemClicked itemClicked;
    boolean isMother;
    private Context mContext;
    private List<MembersContract> mList;
    private ItemHhLayoutBinding viewHolder;

    public HHListAdapter(Context mContext, List<MembersContract> mList, boolean isMother) {
        this.mContext = mContext;
        this.mList = mList;
        this.isMother = isMother;
    }

    public void setItemClicked(OnItemClicked itemClicked) {
        this.itemClicked = itemClicked;
    }

    public void setMList(List<MembersContract> members) {
        mList = members;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        ItemHhLayoutBinding bi = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_hh_layout, viewGroup, false);
        return new ViewHolder(bi);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        holder.bi.index.setText(mList.get(i).getHhid());
        holder.bi.address.setText(mList.get(i).getAddress());
        holder.bi.name.setText(mList.get(i).getHead());
        holder.bi.parentLayout.setOnClickListener(v -> itemClicked.onItemClick(mList.get(i), i, isMother));
        viewHolder = holder.bi;

        /*if (isMother) {
            if (InfoActivity.womenList.size() == 0) return;
            if (InfoActivity.Companion.checkingWomenExist(Integer.valueOf(mList.get(i).getSerialno()))) {
                holder.bi.parentLayout.setEnabled(false);
                holder.bi.parentLayout.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            }
        }*/

    }

    public ItemHhLayoutBinding getHolder() {
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClicked {
        void onItemClick(MembersContract item, int position, boolean flag);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemHhLayoutBinding bi;

        ViewHolder(@NonNull ItemHhLayoutBinding itemView) {
            super(itemView.getRoot());
            bi = itemView;
        }
    }
}
