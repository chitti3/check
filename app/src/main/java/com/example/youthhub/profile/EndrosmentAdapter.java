package com.example.youthhub.profile;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.youthhub.R;
import com.example.youthhub.resModel.profile.journey.Endorsedby;
import com.example.youthhub.resModel.profile.journey.ProfileJourneyListResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EndrosmentAdapter extends RecyclerView.Adapter<EndrosmentAdapter.vholder> {

      Activity activity;
    private ProfileJourneyListResponse profileJourneyListResponse;
    private List<Endorsedby> endorsedbyList=new ArrayList<>();
    String userType;

    public EndrosmentAdapter(Activity activity, List<Endorsedby> endorsedbyList, String userType) {
        this.activity = activity;
        this.endorsedbyList = endorsedbyList;
        this.userType = userType;
    }

    @NonNull
    @Override
    public vholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.endrosement_adapter,viewGroup,false);
        return new vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vholder vholder, int i) {
   vholder.end.setText(endorsedbyList.get(i).getJurnamecode());

    }

    @Override
    public int getItemCount() {
        return endorsedbyList.size();
    }

    public class vholder extends RecyclerView.ViewHolder {
        @BindView(R.id.end_user_code_profile)
        TextView end;
        public vholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
