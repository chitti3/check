package com.example.youthhub.dashBoard.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.youthhub.R;
import com.example.youthhub.VideoViewActivity;
import com.example.youthhub.resModel.profilepostlist.GalleryListItem;
import com.example.youthhub.resModel.profilepostlist.PostListItem;
import com.example.youthhub.utils.AppUtils;
import com.example.youthhub.utils.Constants;
import com.example.youthhub.utils.FontTypeFace;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

class DashboardSlidingImage_Adapter extends PagerAdapter {


    private String[] urls;
    private LayoutInflater inflater;
    // private Context context;
    Activity activity;
    List<GalleryListItem> galleryLists;
    String pathThumb;
    PostListItem postList;

    String pathSource;
    String pathLarge;
    String pathMedium;
    String vidPath;
    String vidPosterPath;
    String profileMediumPath;
    String profileThumbnailPath;
    String eventLogoPath;



    RequestOptions requestOption;
    PostImageViewActivity postImageViewActivity;
    DashBoardPostListAdapter dashBoardPostListAdapter;

    public DashboardSlidingImage_Adapter(Activity activity, String pathThumb, List<GalleryListItem> galleryLists, PostListItem postList, String pathSource, String pathLarge, String pathMedium, String vidPath, String vidPosterPath, String profileMediumPath, String profileThumbnailPath, String eventLogoPath) {
        this.activity = activity;
        this.galleryLists = galleryLists;
        this.pathThumb = pathThumb;
        this.postList = postList;
        this.pathSource = pathSource;
        this.pathLarge = pathLarge;
        this.pathMedium = pathMedium;
        this.vidPath = vidPath;
        this.vidPosterPath = vidPosterPath;
        this.profileMediumPath = profileMediumPath;
        this.profileThumbnailPath = profileThumbnailPath;
        this.eventLogoPath = eventLogoPath;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return galleryLists.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View views = LayoutInflater.from(view.getContext()).inflate(R.layout.post_images_adapter, view, false);
        assert views != null;

        PhotoView imageView = (PhotoView) views.findViewById(R.id.dash_post_images);
        ImageView play_btn = (ImageView) views.findViewById(R.id.dash_play_btn);
        ImageView image = (ImageView) views.findViewById(R.id.image);

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        view.addView(views, 0);

        imageView.setVisibility(View.VISIBLE);



        if (galleryLists.get(0).getPgaType() != null) {


            if (galleryLists.get(position).getPgaType().equals("1")) {

                Glide.with(activity)
                        .load(pathThumb + galleryLists.get(position).getPgaImage())
                        .apply(AppUtils.getRequestOption())
                        .listener(AppUtils.requestListener)
                        .into(imageView);

            } else if (galleryLists.get(position).getPgaType().equals("3")) {
                play_btn.setVisibility(View.VISIBLE);
                Glide.with(activity)
                        .load(galleryLists.get(position).getPgaVideo())
                        .apply(AppUtils.getRequestOption())
                        .listener(AppUtils.requestListener)
                        .into(imageView);

            }
        } else  {

          //  imageView.setVisibility(View.GONE);
//            if (galleryLists.get(0).getExploreType1() == 2){
//            Glide.with(activity)
//                    .load(pathThumb + galleryLists.get(position).getPgaImage())
//                    .apply(AppUtils.getRequestOption())
//                    .listener(AppUtils.requestListener)
//                    .into(imageView);
        }
//        else if (galleryLists.get(position).getExploreType1() == 2){
//            imageView.setVisibility(View.GONE);
//            play_btn.setVisibility(View.GONE);
//        }

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity , VideoViewActivity.class);
                intent.putExtra(Constants.VideoPath , galleryLists.get(0).getPgaVideo());
                activity.startActivity(intent);
            }
        });


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    String value = postList.getUmProfilePicture();
                    bundle.putString(Constants.UserProfileImage, value);
                    bundle.putString("postlist", String.valueOf(postList));
                    bundle.putString("um_name", postList.getUmName());
                    bundle.putString("CreatedOn", postList.getPmCreatedOn());
                    bundle.putString("path_source", pathSource);
                    bundle.putString("path_large", pathLarge);
                    bundle.putString("path_medium", pathMedium);
                    bundle.putString("path_thumb", pathThumb);
                    bundle.putString("vid_path", vidPath);
                    bundle.putString("vid_poster_path", vidPosterPath);
                    bundle.putString("profile_medium_path", profileMediumPath);
                    bundle.putString("profile_thumbnail_path", profileThumbnailPath);
                    bundle.putString("event_logo_path", eventLogoPath);
                    bundle.putInt("totallike", Integer.parseInt(postList.getPmTotalLike()));
                    bundle.putInt("TotalComment", Integer.parseInt(postList.getPmTotalComment()));
                    bundle.putString("username_code",postList.getUsernameCode());
                    bundle.putString("description",postList.getPmDescription());
                    bundle.putInt("EncourageStatus",postList.getEncourageStatus());
                    bundle.putString("PmCode", postList.getPmCode());
                    bundle.putString("UmCode",postList.getUmCode());
                    bundle.putString("PmDescription", postList.getPmDescription());
                    bundle.putInt("ShareUserCount",postList.getShareUserCount());

                    Intent multi_img_count_txtIntent = new Intent(activity, PostImageViewActivity.class);
                    ArrayList<String>  images = new ArrayList<>();
                    ArrayList<String>  videos = new ArrayList<>();

                    for (int i= 0; i < postList.getGalleryList().size(); i++){
                        images.add(galleryLists.get(i).getPgaImage());
                        videos.add(galleryLists.get(i).getPgaVideo());
                        bundle.putString("galary", galleryLists.get(i).getPgaImage());
                    }
                    multi_img_count_txtIntent.putExtra("post_image",images);
                    multi_img_count_txtIntent.putExtra("post_video",videos);
                    multi_img_count_txtIntent.putExtras(bundle);
                    activity.startActivity(multi_img_count_txtIntent);
                    activity.overridePendingTransition(R.anim.activity_slide_up, R.anim.stay);
                }
            });



        return views;
    }

//    String loadImage(List<GalleryListItem> galleryLists, int i) {
//
//        String imagePath1 = null;
//        switch (galleryLists.get(0).getPgaType()) {
//            case "1":
//                imagePath1 = Constants.getLoadGlide(activity, pathThumb + galleryLists.get(i).getPgaImage());
//                break;
//            case "2":
//                imagePath1 = galleryLists.get(i).getPgaImage();
//                break;
//            case "3":
//                //imagePath1 = Constants.getLoadGlide(activity, galleryLists.get(i).getPgaVideoPoster());
//                imagePath1 = Constants.getLoadGlide(activity, galleryLists.get(i).getPgaVideo());
//                break;
//            case "4":
//                //imagePath1 = galleryLists.get(i).getPgaVideoPoster();
//                imagePath1 = galleryLists.get(i).getPgaVideo();
//                break;
//        }
//
//        return imagePath1;
//    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
