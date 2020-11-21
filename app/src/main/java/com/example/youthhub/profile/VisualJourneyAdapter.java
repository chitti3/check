package com.example.youthhub.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.youthhub.R;
import com.example.youthhub.dashBoard.DeleteMessageDialog;
import com.example.youthhub.resModel.profile.journey.Media;
import com.example.youthhub.resModel.profile.journey.ProfileJourneyListResponse;
import com.example.youthhub.resModel.profile.visualjourney.Delete.Journey_delete_model;
import com.example.youthhub.retrofit.ApiClient;
import com.example.youthhub.utils.AppUtils;
import com.example.youthhub.utils.Constants;
import com.example.youthhub.utils.FontTypeFace;
import com.example.youthhub.utils.Loader;
import com.example.youthhub.utils.MyToast;
import com.example.youthhub.utils.NetWorkUtil;
import com.example.youthhub.utils.Preference;
import com.google.gson.Gson;
import com.shuhart.bubblepagerindicator.BubblePageIndicator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisualJourneyAdapter extends RecyclerView.Adapter<VisualJourneyAdapter.MyViewHolder> implements View.OnClickListener,DeleteMessageDialog.OnDeleteListener{

    public static final String TAG = VisualJourneyAdapter.class.getSimpleName();
    Activity activity;
   @BindView(R.id.visual_journey_img1)
    ImageView visualJourneyImg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sub_title)
    TextView subTitle;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.tags)
    TextView tags;
    @BindView(R.id.image_add)
    ImageView imageAdd;
    @BindView(R.id.add_image)
    TextView addImage;
    @BindView(R.id.event_view)
    View eventView;
    @BindView(R.id.deletejourney)
    ImageView deletejourney;
    Fragment fragment;
    @BindView(R.id.Edit_journey)
    ImageView edit_journey;
    String userType;
    List<Media> media;
    Visualslideadpater visualslideadpater;
    ProfileJourneyListResponse profileJourneyListResponse;
    int pos;


    public VisualJourneyAdapter(Activity activity, ProfileJourneyListResponse profileJourneyListResponse, String userType) {
        this.activity = activity;
        this.profileJourneyListResponse = profileJourneyListResponse;
        this.userType = userType;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.visual_journey_adapter, viewGroup, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        callTypeFace(holder);
        slide();
        media=profileJourneyListResponse.getData().getJourneylist().get(position).getMedia();
        if (profileJourneyListResponse.getData().getJourneylist().get(position).getMedia().size()==0)
        {
          holder.visualJourneyImg.setVisibility(View.VISIBLE);
            holder.pager.setVisibility(View.INVISIBLE);
             Glide.with(activity)
                    .load(Constants.getLoadGlide(activity, profileJourneyListResponse.getData().getDefaultImage()))
                    .apply(AppUtils.getRequestOption())
                    .listener(AppUtils.requestListener)
                    .into(holder.visualJourneyImg);
        }
        else {
            holder.visualJourneyImg.setVisibility(View.INVISIBLE);
            holder.pager.setVisibility(View.VISIBLE);
            visualslideadpater = new Visualslideadpater(activity, media, profileJourneyListResponse);
            holder.pager.setAdapter(visualslideadpater);
            holder.indicator.setViewPager(holder.pager);
            holder.indicator.setOnSurfaceCount(1);
            holder.indicator.setRisingCount(1);
            holder.indicator.setFillColor(ContextCompat.getColor(activity, R.color.colorAccent));
            holder.indicator.setPageColor(ContextCompat.getColor(activity, R.color.colorPrimary));
            holder.indicator.setRadius(activity.getResources().getDimensionPixelSize(R.dimen.default_bubble_indicator_radius));
            holder.indicator.setMarginBetweenCircles(activity.getResources().getDimensionPixelSize(
                    R.dimen.default_bubble_indicator_circles_margin));
            final float density = activity.getResources().getDisplayMetrics().density;
            holder.indicator.setRadius(4 * density);
            int NUM_PAGES = media.size();


            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    int currentPage = 0;
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0;
                    }
                    holder.pager.setCurrentItem(currentPage++, true);
                }
            };
        }

        holder.title.setText(profileJourneyListResponse.getData().getJourneylist().get(position).getJumTitle());
        holder.subTitle.setText(profileJourneyListResponse.getData().getJourneylist().get(position).getJumShortDescription());
        holder.desc.setText(profileJourneyListResponse.getData().getJourneylist().get(position).getJumFullDescription());
        Log.d(TAG, "onBindViewHolder: "+profileJourneyListResponse.getData().getJourneylist().get(position).getJucImage());
        Log.d(TAG, "onBindViewHolder: "+new Gson().toJson(profileJourneyListResponse.getData().getJourneylist().get(position)));
        if (profileJourneyListResponse.getData().getJourneylist().get(position).getJumTagsName()==null)
        {
            holder.tags.setVisibility(View.GONE);
        }else {
            holder.tags.setVisibility(View.VISIBLE);
            holder.tags.setText(profileJourneyListResponse.getData().getJourneylist().get(position).getJumTagsName());
        }
        if (profileJourneyListResponse.getData().getJourneylist().get(position).getJucImage() != null&&profileJourneyListResponse.getData().getJourneylist().get(position).getJucImage().length() != 0) {
          //  holder.visualJourneyImg.setVisibility(View.VISIBLE);
           /* Glide.with(activity)
                    .load(Constants.getLoadGlide(activity, profileJourneyListResponse.getData().getImagePathSource() + profileJourneyListResponse.getData().getJourneylist().get(position).getJucImage()))
                    .apply(AppUtils.getRequestOption())
                    .listener(AppUtils.requestListener)
                    .into(holder.visualJourneyImg);*/
            holder.addImage.setVisibility(View.GONE);
            holder.imageAdd.setVisibility(View.GONE);


        } else {
           /* Glide.with(activity)
                    .load(Constants.getLoadGlide(activity, profileJourneyListResponse.getData().getDefaultImage() + profileJourneyListResponse.getData().getJourneylist().get(position).getJucImage()))
                    .apply(AppUtils.getRequestOption())
                    .listener(AppUtils.requestListener)
                    .into(holder.visualJourneyImg);*/
            //  holder.addImage.setVisibility(View.VISIBLE);
            holder.addImage.setVisibility(View.GONE);
            holder.imageAdd.setVisibility(View.GONE);

        }

        if (userType.equals("1") || Preference.getInstance(activity).getStr(Constants.UserID).equals(profileJourneyListResponse.getData().getJourneylist().get(position).getJumUmUserId())) {
            holder.addImage.setVisibility(View.GONE);
            holder.imageAdd.setVisibility(View.GONE);
            holder.edit_journey.setVisibility(View.VISIBLE);
            holder.deletejourney.setVisibility(View.VISIBLE);
            holder.imageAdd.setVisibility(View.VISIBLE);
        } else {
            holder.addImage.setVisibility(View.GONE);
            holder.imageAdd.setVisibility(View.GONE);
            holder.edit_journey.setVisibility(View.INVISIBLE);
            holder.deletejourney.setVisibility(View.INVISIBLE);
            holder.imageAdd.setVisibility(View.GONE);
            holder.endorsementby.setVisibility(View.GONE);
            if (profileJourneyListResponse.getData().getJourneylist().get(position).getEndorsedBy().size()==0)
            {
                holder.endorsementby.setVisibility(View.GONE);
                holder.endby.setVisibility(View.GONE);

            }else {
                holder.endby.setVisibility(View.VISIBLE);
                holder.endorsementby.setVisibility(View.VISIBLE);
                holder.endorsementby.setText("ENDORSED BY");}
        }


        if (userType.equals("1")|| Preference.getInstance(activity).getStr(Constants.UserID).equals(profileJourneyListResponse.getData().getJourneylist().get(position).getJumUmUserId())){
            holder.addMilestone.setVisibility(View.VISIBLE);
            // holder.viewMilestone.setVisibility(View.VISIBLE);
        } else {
            holder.addMilestone.setVisibility(View.INVISIBLE);
            //  holder.viewMilestone.setVisibility(View.VISIBLE);
        }
        if (profileJourneyListResponse.getData().getJourneylist().get(position).getIsMilestone()==0){
            holder.addMilestone.setText("Add Milestone");
        }else{
            holder.addMilestone.setText("Milestone");
        }
        if (profileJourneyListResponse.getData().getJourneylist().get(position).getEndorsedBy().size()==0)
        {
            holder.endby.setVisibility(View.GONE);
        }else {
            holder.endby.setVisibility(View.VISIBLE);
            EndrosmentAdapter endrosmentAdapter = new EndrosmentAdapter(activity,profileJourneyListResponse.getData().getJourneylist().get(position).getEndorsedBy(),userType);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.endby.setLayoutManager(linearLayoutManager);
            holder.endby.setAdapter(endrosmentAdapter);
        }



        holder.addMilestone.setOnClickListener(v -> {
            if (profileJourneyListResponse.getData().getJourneylist().get(position).getIsMilestone()==0){
                Intent intent = new Intent(activity, AddMilestoneActivity.class);
                intent.putExtra(Constants.UserCode,profileJourneyListResponse.getData().getJourneylist().get(position).getJumCode());
                // intent.putExtra("visualjourneymaster",profileVisualJourneyAddMasterResponse);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.activity_slide_up, R.anim.stay);
            }else{
                Intent intent = new Intent(activity, MilestoneActivity.class);
                intent.putExtra(Constants.UserCode,profileJourneyListResponse.getData().getJourneylist().get(position).getJumCode());
                intent.putExtra("id","1");
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.activity_slide_up, R.anim.stay);
            }

        });

        holder.edit_journey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Journey_code;
                Journey_code = profileJourneyListResponse.getData().getJourneylist().get(position).getJumCode();
                Intent intent = new Intent(activity, AddStepActivity.class);
                intent.putExtra("j_code", Journey_code );
                intent.putExtra("boolen",true);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.activity_slide_up, R.anim.stay);

            }
        });
        holder.deletejourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DeleteMessageDialog deleteMessageDialog = new DeleteMessageDialog(activity,"Visual",profileJourneyListResponse.getData().getJourneylist().get(position).getJumCode(),null,null);
                deleteMessageDialog.setOnDeleteListener(VisualJourneyAdapter.this);
                deleteMessageDialog.show();
                pos=position;
/*                milestone_id = profileJourneyListResponse.getData().getJourneylist().get(position).getJumJourneyId();
                Journey_code = profileJourneyListResponse.getData().getJourneylist().get(position).getJumCode();
                holder.Deletejourneyy(Journey_code);
                removeAt(position);*/
            }
        });
        holder.imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EndrosementDialog endrosementDialog = new EndrosementDialog(activity,profileJourneyListResponse.getData().getJourneylist().get(position).getJumCode());
                endrosementDialog.show();

            }
        });

    }

    private void slide() {


    }


    public void removeAt(int position) {
        profileJourneyListResponse.getData().getJourneylist().remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,profileJourneyListResponse.getData().getJourneylist().size());

    }

    private void callTypeFace(MyViewHolder holder) {
        holder.title.setTypeface(FontTypeFace.fontBold(activity));
        holder.tags.setTypeface(FontTypeFace.fontBold(activity));
        holder.addMilestone.setTypeface(FontTypeFace.fontBold(activity));
    }

    @Override
    public int getItemCount() {
        return profileJourneyListResponse.getData().getJourneylist().size();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void OnDelete(boolean deleted) {
        if (deleted)
        {
            removeAt(pos);
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


       @BindView(R.id.visual_journey_img1)
        ImageView visualJourneyImg;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.sub_title)
        TextView subTitle;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.tags)
        TextView tags;
        @BindView(R.id.add_image)
        TextView addImage;
        @BindView(R.id.add_endrosement)
        ImageView imageAdd;
        @BindView(R.id.add_milestone)
        Button addMilestone;
        /*@BindView(R.id.view_milestone)
        Button viewMilestone;*/
        @BindView(R.id.end_by)
        RecyclerView endby;
        @BindView(R.id.deletejourney)
        ImageView deletejourney;
        Fragment fragment;
        @BindView(R.id.Edit_journey)
        ImageView edit_journey;
        @BindView(R.id.endorsement_by)
        TextView endorsementby;
        @BindView(R.id.pager)
        ViewPager pager;
        @BindView(R.id.indicator)
        BubblePageIndicator indicator;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void Deletejourneyy(String journey_code) {

            if (NetWorkUtil.isNetworkConnected(activity)) {
                Loader.showLoad(activity, true);
                Call<Journey_delete_model> call = ApiClient.getApiInterface().journeydelete(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                        Constants.getToken(activity), journey_code);

                call.enqueue(new Callback<Journey_delete_model>() {
                    @Override
                    public void onResponse(@NonNull Call<Journey_delete_model> call, @NonNull Response<Journey_delete_model> response) {
                        if (response.isSuccessful()) {
                            //   Log.d(TAG, "onResponse:ProfileYouthInfoResponse 1 ");
                            if (response.body() != null) {
                                if (response.body().getStatus()==1) {
                                    Journey_delete_model journey_delete_model = response.body();
                                    MyToast.normalMessage(response.body().getMessage(), activity);
                                } else {
                                    //     Log.d(TAG, "onResponse:ProfileYouthInfoResponse else "+new Gson().toJson(profileInfoResp));
                                    Log.d(TAG, "onResponse:failure " + new Gson().toJson(response.body()));
                                    MyToast.errorMessage(response.body().getMessage(), activity);
                                }

                            }
                        } else {
                            Log.d(Constants.failureResponse, "Visual_Journey_delete" + response.toString());
                        }
                        Loader.showLoad(activity, false);
                    }

                    @Override
                    public void onFailure(@NonNull Call<Journey_delete_model> call, @NonNull Throwable t) {
                        Deletejourneyy(journey_code);
                        Log.d(Constants.failureResponse, "Visual_Journey_delete" + t.toString());
                        Loader.showLoad(activity, false);
                    }
                });
            }
        }








    }
}
