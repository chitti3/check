package com.example.youthhub.dashBoard.findConnectionFragment.multiSelectFilter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youthhub.R;
import com.example.youthhub.resModel.connection.conListMaster.Bizservice;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConnectionBizServiceAdapter extends RecyclerView.Adapter<ConnectionBizServiceAdapter.MyViewHolder> implements View.OnClickListener {

    private Activity activity;
    private List<Bizservice> lists;
    private OnViewClickListener onViewClickListener;
    private List<Bizservice> selectedLists;

    public void addAll(List<Bizservice> lists, List<Bizservice> selectedLists) {
        this.lists = lists;
        this.selectedLists = selectedLists;
        notifyDataSetChanged();
    }

    ConnectionBizServiceAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.multi_select_adapter, viewGroup, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bizservice list = lists.get(position);
        holder.tagsName.setText(list.getIcaName());

        holder.checkView.setOnClickListener(this);
        holder.checkView.setTag(R.id.holder_tag, holder);
        holder.checkView.setTag(R.id.data_tag, list);
        holder.checkView.setTag(R.id.position, position);
        for (int i=0;i<selectedLists.size();i++){
            if(list.getIcaCategoryId().equals(selectedLists.get(i).getIcaCategoryId())){
                //holder.checkImg.setVisibility(View.VISIBLE);
                holder.checkView.performClick();
            }
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.check_view) {
            MyViewHolder holder = (MyViewHolder) v.getTag(R.id.holder_tag);
            Bizservice singleList = (Bizservice) v.getTag(R.id.data_tag);
            if(holder.checkImg.getVisibility()!=View.VISIBLE){
                holder.checkImg.setVisibility(View.VISIBLE);
            }else {
                holder.checkImg.setVisibility(View.INVISIBLE);
            }
            onViewClickListener.OnViewItemClick(singleList);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    public interface OnViewClickListener {
        void OnViewItemClick(Bizservice singleList);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tags_name)
        TextView tagsName;
        @BindView(R.id.check_img)
        ImageView checkImg;
        @BindView(R.id.check_view)
        View checkView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}