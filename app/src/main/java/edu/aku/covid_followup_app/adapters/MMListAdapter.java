package edu.aku.covid_followup_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.aku.covid_followup_app.R;
import edu.aku.covid_followup_app.activities.list.ListViewModel;
import edu.aku.covid_followup_app.contracts.MembersContract;
import edu.aku.covid_followup_app.databinding.ItemLayoutBinding;

public class MMListAdapter extends RecyclerView.Adapter<MMListAdapter.ViewHolder> {

    OnItemClicked itemClicked;
    private Context mContext;
    private List<MembersContract> mList;
    private ListViewModel vModel;

    public MMListAdapter(Context mContext, List<MembersContract> mList, ListViewModel vModel) {
        this.mContext = mContext;
        this.mList = mList;
        this.vModel = vModel;
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
        ItemLayoutBinding bi = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_layout, viewGroup, false);
        return new ViewHolder(bi);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        holder.bi.index.setText(mList.get(i).getMemberid());
        holder.bi.address.setText(mList.get(i).getAddress());
        holder.bi.name.setText(mList.get(i).getMembername());
        holder.bi.parentLayout.setOnClickListener(v -> itemClicked.onItemClick(mList.get(i), i));
        int iconRes = R.drawable.ic_lock;

        if (vModel.getCheckedItemValues(Integer.parseInt(mList.get(i).getMemberid()))) {
            holder.bi.checkIcon.setVisibility(View.VISIBLE);
            holder.bi.parentLayout.setEnabled(false);
        }

        Glide.with(mContext)
                .asBitmap()
                .load(iconRes)
                .into(holder.bi.statusImg);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClicked {
        void onItemClick(MembersContract item, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemLayoutBinding bi;

        ViewHolder(@NonNull ItemLayoutBinding itemView) {
            super(itemView.getRoot());
            bi = itemView;
        }
    }
}
