package com.example.youthhub.profile.profileTabSubViews;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.youthhub.R;
import com.example.youthhub.resModel.profile.profileinfo.EndorsedByItem;
import com.example.youthhub.resModel.profile.profileinfo.ProfileInfoResp;
import com.example.youthhub.resModel.profile.profileinfo.SkillsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class skillprovideradapter extends RecyclerView.Adapter<skillprovideradapter.view> {
    Context context;
    Activity activity;
    List<EndorsedByItem> endorsedByItem;
    ProfileInfoResp profileInfoResp;
    List<SkillsItem> skillsItemList;


    public skillprovideradapter(Activity activity, List<EndorsedByItem> endorsedByItem) {
        this.activity = activity;
        this.endorsedByItem = endorsedByItem;
    }

    @NonNull
    @Override
    public view onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.skill_providername,viewGroup,false);
        return new view(v) ;
    }

    @Override
    public void onBindViewHolder(@NonNull view view, int i) {
        EndorsedByItem endorsedBym=endorsedByItem.get(i);
    view.skilltext1.setText(endorsedByItem.get(i).getCode());
         System.out.println(i+"  "+endorsedByItem.get(i).getCode()+"  "+"cdnjn");
    }

    @Override
    public int getItemCount() {
        return endorsedByItem.size();
    }

    public class view extends RecyclerView.ViewHolder {
        @BindView(R.id.skills_text1)
        TextView skilltext1;
        public view(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
