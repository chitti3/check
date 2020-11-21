package com.example.youthhub.dashBoard.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.youthhub.R;
import com.example.youthhub.dashBoard.DashBoardActivity;
import com.example.youthhub.resModel.post.PostLikeResponse;
import com.example.youthhub.resModel.post.likepost.LikeResponse;
import com.example.youthhub.resModel.post.postList.GalleryList;
import com.example.youthhub.resModel.profilepostlist.PostDashboardListResponse;
import com.example.youthhub.resModel.profilepostlist.PostListItem;
import com.example.youthhub.retrofit.ApiClient;
import com.example.youthhub.utils.AppUtils;
import com.example.youthhub.utils.Constants;
import com.example.youthhub.utils.FontTypeFace;
import com.example.youthhub.utils.Loader;
import com.example.youthhub.utils.MyToast;
import com.example.youthhub.utils.NetWorkUtil;
import com.example.youthhub.utils.Preference;
import com.shuhart.bubblepagerindicator.BubblePageIndicator;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostImageViewActivity extends AppCompatActivity implements ProfileReportEditDialog.OnPassValueListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.back_constrain)
    ConstraintLayout backConstrain;
    @BindView(R.id.post_image)
    CircleImageView postImage;
    @BindView(R.id.post_code_txt)
    TextView postCodeTxt;
    @BindView(R.id.post_image_code_constrain)
    ConstraintLayout postImageCodeConstrain;
    @BindView(R.id.post_name)
    TextView postName;
    @BindView(R.id.posted_time)
    TextView postedTime;
    @BindView(R.id.three_dot)
    ImageView threeDot;
    @BindView(R.id.post_images_recycler)
    RecyclerView postImagesRecycler;
    @BindView(R.id.galarypost)
    ImageView galarypost;
    @BindView(R.id.like_count)
    TextView like_count;
    @BindView(R.id.like_post)
    ImageView like_post;
    static TextView cmt_count;
    @BindView(R.id.description)
    TextView descriptions;
    @BindView(R.id.cmt_post)
    ImageView cmt_post;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.view_more)
    TextView view_more;
    //    @BindView(R.id.scroll_description)
//    ScrollView scroll_description;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.view_less)
    TextView view_less;


    private String pathSource;
    private String pathLarge;
    private String pathMedium;
    private String pathThumb;
    private String vidPath;
    private String vidPosterPath;
    private String profileMediumPath;
    private String profileThumbnailPath;
    private String eventLogoPath;
    private String username_code;
    private String description;
    private String postlist;
    private String title;
    private String Cretedon;
    private String profileThumbnailPaths;
    private String profile_picture;
    private int EncourageStatus;
    private String PmCode;
    private int totallike;
    private int TotalComment;
    private String UmCode;
    private String PmDescription;
    private int ShareUserCount;
    PostListItem postList;



    List<PostListItem> postLists = new ArrayList<>();
    PostListItem listItem;

    PostImagesAdapter postImagesAdapter;
    List<GalleryList> galleryLists = new ArrayList<>();
    private Activity activity;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;


    OnPassValueListener onPassValueListener;

    public void setOnPassValueListener(OnPassValueListener onPassValueListener){
        this.onPassValueListener = onPassValueListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_image_view);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();


        descriptions = findViewById(R.id.description);
        cmt_count = findViewById(R.id.cmt_count);


        ArrayList<String> imagelist = (ArrayList<String>) getIntent().getSerializableExtra("post_image");
        ArrayList<String> videolist = (ArrayList<String>) getIntent().getSerializableExtra("post_video");


        title = bundle.getString("um_name");
        Cretedon = bundle.getString("CreatedOn");
        profileThumbnailPaths = bundle.getString("profile_thumbnail_path");
        totallike = bundle.getInt("totallike");
        EncourageStatus = bundle.getInt("EncourageStatus");
        TotalComment = bundle.getInt("TotalComment");
        username_code = bundle.getString("username_code");
        description = bundle.getString("description");
        postList = bundle.getParcelable("postlist");
        PmCode = bundle.getString("PmCode");
        bundle.getParcelable("parcel");
        UmCode = bundle.getString("UmCode");
        PmDescription = bundle.getString("PmDescription");
        ShareUserCount = bundle.getInt("ShareUserCount");
        pathThumb = bundle.getString("path_thumb");
        profile_picture = bundle.getString(Constants.UserProfileImage);


        postName.setText(title);
        postName.setSelected(true);
        postedTime.setText(Cretedon);

        postName.setTypeface(FontTypeFace.fontSemiBold(this));
        postedTime.setTypeface(FontTypeFace.fontRegular(this));
        descriptions.setTypeface(FontTypeFace.fontRegular(this));
        like_count.setTypeface(FontTypeFace.fontBold(this));
        cmt_count.setTypeface(FontTypeFace.fontBold(this));
        view_more.setTypeface(FontTypeFace.fontSemiBold(this));
        view_less.setTypeface(FontTypeFace.fontSemiBold(this));


        if (totallike == 0) {
            like_count.setText(totallike + " Like");
        } else if (totallike == 1) {
            like_count.setText(totallike + " Like");
        } else if (totallike > 1) {
            like_count.setText(totallike + " Likes");
        }


        if (TotalComment == 0) {
            cmt_count.setText(TotalComment + " Comment");
        } else if (TotalComment == 1) {
            cmt_count.setText(TotalComment + " Comment");
        } else if (TotalComment > 1) {
            cmt_count.setText(TotalComment + " Comments");
        }

        descriptions.setText(description);
        descriptions.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = descriptions.getLineCount();

                System.out.println("==LineCount===="+"  " +lineCount);

                if (lineCount >= 4){
                    view_more.setVisibility(View.VISIBLE);
                    view_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            view_more.setVisibility(View.GONE);
                            view_less.setVisibility(View.VISIBLE);
                            descriptions.setMaxLines(Integer.MAX_VALUE);
                        }
                    });
                }
                view_less.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view_less.setVisibility(View.GONE);
                        view_more.setVisibility(View.VISIBLE);
                        descriptions.setMaxLines(3);
                    }
                });
            }
        });




        switch (EncourageStatus) {
            case 0:
                like_post.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_unlike_heart));
                break;
            case 1:
                like_post.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_like_heart));
                break;
        }




        if (profile_picture.isEmpty()){
            postCodeTxt.setText(username_code);
        } else {
            Glide.with(this)
                    .load(profileThumbnailPaths + profile_picture)
                    .into(postImage);
        }



        postImagesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        postImagesAdapter = new PostImagesAdapter(imagelist, pathThumb, this);
        postImagesRecycler.setAdapter(postImagesAdapter);



        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(this,imagelist,videolist , pathThumb));

        BubblePageIndicator indicator = (BubblePageIndicator)
                findViewById(R.id.indicator);

        indicator.setOnSurfaceCount(1);
        indicator.setRisingCount(2);
        indicator.setFillColor(ContextCompat.getColor(this, R.color.colorAccent));
        indicator.setPageColor(ContextCompat.getColor(this, R.color.colorPrimary));
        indicator.setRadius(getResources().getDimensionPixelSize(R.dimen.default_bubble_indicator_radius));
        indicator.setMarginBetweenCircles(getResources().getDimensionPixelSize(
                R.dimen.default_bubble_indicator_circles_margin));
        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        NUM_PAGES = imagelist.size();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };



        if (bundle != null) {
            postList = bundle.getParcelable(Constants.PostImageList);
            pathSource = bundle.getString("path_source");
            pathLarge = bundle.getString("path_large");
            pathMedium = bundle.getString("path_medium");
            pathThumb = bundle.getString("path_thumb");
            vidPath = bundle.getString("vid_path");
            vidPosterPath = bundle.getString("vid_poster_path");
            profileMediumPath = bundle.getString("profile_medium_path");
            profileThumbnailPath = bundle.getString("profile_thumbnail_path");
            eventLogoPath = bundle.getString("event_logo_path");

        }

    }


    @OnClick({R.id.back_constrain, R.id.back, R.id.three_dot, R.id.like_post, R.id.like_count, R.id.cmt_count , R.id.cmt_post, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_constrain:
            case R.id.back:
                Validate();
                break;
            case R.id.three_dot:
                String PostPmDescription =descriptions.getText().toString().trim();

                if (UmCode.equals(Constants.getUserCode(this)) && ShareUserCount <= 0) {
                    ProfileReportEditDialog editDeleteDialog = new ProfileReportEditDialog(this, PmCode, PostPmDescription, "EditDelete");
                    //  editDeleteDialog.setOnPassValueListener((ProfileReportEditDialog.OnPassValueListener) activity);
                    editDeleteDialog.setOnPassValueListener(this);
                    editDeleteDialog.show();

                } else {
                    ProfileReportEditDialog reportDialog = new ProfileReportEditDialog(this, PmCode, PostPmDescription, "Report");
                    //    reportDialog.setOnPassValueListener((ProfileReportEditDialog.OnPassValueListener) activity);
                    reportDialog.setOnPassValueListener(this);
                    reportDialog.show();
                }
                break;
            case R.id.like_post:
                call_post_list_api(PmCode);
                //   call_post_encourage_api(EncourageStatus , PmCode);
                break;
            case R.id.cmt_post:
            case R.id.cmt_count:
                Bundle comment_bundle = new Bundle();
                comment_bundle.putString(Constants.PostCode, PmCode);
                comment_bundle.putString(Constants.CommentCount, String.valueOf(TotalComment));
                comment_bundle.putInt(Constants.EncourageStatus, EncourageStatus);
                Intent commentIntent = new Intent(this, CommentActivity.class);
                commentIntent.putExtras(comment_bundle);
                startActivity(commentIntent);
                overridePendingTransition(R.anim.activity_slide_up, R.anim.stay);
                break;
            case R.id.like_count:
                call_post_encourage_list_api(PmCode);
                break;
            case R.id.share:
                PostShareDialog postShareDialog = new PostShareDialog(this, PmCode);
                postShareDialog.show();
                break;
        }

    }



    private void call_post_list_api( String PmCode) {
        if (NetWorkUtil.isNetworkConnected(this)) {
            // int EncourageStatus = "";

            Call<PostDashboardListResponse> call = ApiClient
                    .getApiInterface()
                    .getPostList(Constants.getApiKey(this),
                            Constants.getAccessKey(this),
                            Constants.getToken(this),
                            "",
                            "",
                            PmCode,
                            "");

            call.enqueue(new Callback<PostDashboardListResponse>() {
                @Override
                public void onResponse(@NonNull Call<PostDashboardListResponse> call, @NonNull Response<PostDashboardListResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {

                                // update_adapter(response.body());
                                postLists = response.body().getData().getPostList();

                                for (int i =0; i< postLists.size(); i++){
                                    EncourageStatus = postLists.get(i).getEncourageStatus();
                                    totallike = Integer.parseInt(postLists.get(i).getPmTotalLike());
                                    PmDescription = postLists.get(i).getPmDescription();
                                }
                                call_post_encourage_api(EncourageStatus, PmCode, totallike);

                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PostDashboardListResponse> call, @NonNull Throwable t) {
                    Log.d(Constants.failureResponse + " PostList", t.toString());
                }
            });

        }
    }

    private void call_post_encourage_list_api( String PmCode) {
        if (NetWorkUtil.isNetworkConnected(this)) {
            Loader.showLoad(this, true);
            Call<PostLikeResponse> call = ApiClient.getApiInterface().getPostEncourageList(Constants.getApiKey(this), Constants.getAccessKey(this),
                    Constants.getToken(this),
                    PmCode);

            call.enqueue(new Callback<PostLikeResponse>() {
                @Override
                public void onResponse(@NonNull Call<PostLikeResponse> call, @NonNull Response<PostLikeResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(Constants.PostLikeList, response.body());
                                Intent likeActivityIntent = new Intent(PostImageViewActivity.this, LikeActivity.class);
                                likeActivityIntent.putExtras(bundle);
                                startActivity(likeActivityIntent);
                                overridePendingTransition(R.anim.activity_slide_up, R.anim.stay);
                            } else {
                                MyToast.errorMessage(response.body().getMessage(), PostImageViewActivity.this);
                            }
                        }
                    } else {
                        Log.d(Constants.failureResponse + " PostLikeList", response.toString());
                    }
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<PostLikeResponse> call, @NonNull Throwable t) {
                    Log.d(Constants.failureResponse + " PostLikeList", t.toString());
                    Loader.showLoad(PostImageViewActivity.this, false);
                }
            });

        }
    }


    private void call_post_encourage_api(int encourageStatus, String pmCode, int totallike) {
        if (NetWorkUtil.isNetworkConnected(this)) {
            String status;
            if (encourageStatus == 1) {
                status = "0";
            } else {
                status = "1";
            }
            //  Loader.showLoad(this, true);

            Call<LikeResponse> call = ApiClient.getApiInterface().getPostEncourage(Constants.getApiKey(this), Constants.getAccessKey(this),
                    Constants.getToken(this),
                    pmCode,
                    status);

            String finalStatus = status;
            call.enqueue(new Callback<LikeResponse>() {
                @Override
                public void onResponse(@NonNull Call<LikeResponse> call, @NonNull Response<LikeResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {

                                if (response.body().getMessage().contains("Post has been Encourage successfully")){
                                    like_post.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_like_heart));
                                    like_count.setText(totallike + 1 + " Likes");

                                } else if (response.body().getMessage().contains("Post has been UnEncourage successfully")){
                                    like_post.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_unlike_heart));
                                    like_count.setText(totallike -1 + " Likes");
                                }
                                //  Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                            }
                        }
                    } else {
                        Log.d(Constants.failureResponse + " PostLike", response.toString());
                    }
                    // Loader.showLoad(this, false);
                }

                @Override
                public void onFailure(@NonNull Call<LikeResponse> call, @NonNull Throwable t) {
                    Log.d(Constants.failureResponse + " PostLike", t.toString());
                    //  Loader.showLoad(this, false);

                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Validate();
    }

    private void Validate() {
        String getdescription = descriptions.getText().toString();
        String getlikes = like_count.getText().toString();
        String namess = String.valueOf(totallike);
        if (description.equals(getdescription) && getlikes.contains(namess)){
            finish();
        }else {
            finish();
            Intent intent = new Intent(PostImageViewActivity.this , DashBoardActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.stay, R.anim.activity_slide_down);
        }
    }

    @Override
    public void onPassDescData(String description) {
        if (onPassValueListener == null){
            descriptions.setText(description);

        } else {
            onPassValueListener.onPassDescData(description);
        }
    }

    @Override
    public void onPassDeleteData(boolean deleted) {
        if (onPassValueListener == null){
            Intent intent = new Intent(PostImageViewActivity.this, DashBoardActivity.class);
            startActivity(intent);
        } else {
            onPassValueListener.onPassDeleteData(deleted);

        }
    }

    @Override
    public void onPassReportData(boolean deleted) {

    }
    public interface OnPassValueListener{
        void onPassDescData(String description);
        void onPassDeleteData(boolean deleted);
        void onPassReportData(boolean deleted);
    }
}
