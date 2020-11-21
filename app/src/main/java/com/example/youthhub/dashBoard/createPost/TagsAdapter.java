package com.example.youthhub.dashBoard.createPost;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.youthhub.R;
import com.example.youthhub.resModel.post.createPost.Tag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.MyViewHolder> implements View.OnClickListener {

    private Activity activity;
    private OnViewClickListener onViewClickListener;

    private List<Tag> lists = new ArrayList<>();
    private List<Tag> eamplelist = new ArrayList<>();
    private List<Tag> selectedLists = new ArrayList<>();

    public void addAll(List<Tag> lists, List<Tag> selectedLists) {
        this.lists = lists;
        this.selectedLists = selectedLists;
        eamplelist= new ArrayList<>(lists);
        notifyDataSetChanged();
    }


    TagsAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.multi_select_adapter, viewGroup, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Tag list = lists.get(position);
        holder.tagsName.setText(list.getTgName());

        holder.checkView.setOnClickListener(this);
        holder.checkView.setTag(R.id.holder_tag, holder);
        holder.checkView.setTag(R.id.data_tag, list);
        holder.checkView.setTag(R.id.position, position);
        for (int i=0;i<selectedLists.size();i++){
            if(list.getTgTagId().equals(selectedLists.get(i).getTgTagId())){
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
            Tag singleList = (Tag) v.getTag(R.id.data_tag);
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
        void OnViewItemClick(Tag singleList);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tags_name)
        TextView tagsName;
        @BindView(R.id.check_img)
        public
        ImageView checkImg;
        @BindView(R.id.check_view)
        RelativeLayout checkView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =getLayoutPosition();
                    //Your logic

                }
            });
        }
    }


    public Filter getFilter() {

        return filter;

    }


    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Tag> filteredList = new ArrayList<>();


            if(charSequence == null | charSequence.length() == 0){
                filteredList.addAll(eamplelist);

            }else{
                String searchChr = charSequence.toString().toLowerCase().trim();

                for(Tag list: eamplelist){
                    if(list.getTgName().toLowerCase().contains(searchChr)){
                        filteredList.add(list);
                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            lists.clear();
            lists.addAll((List) filterResults.values);
            notifyDataSetChanged();


        }
    };










}