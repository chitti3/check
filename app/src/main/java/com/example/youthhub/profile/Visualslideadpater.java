package com.example.youthhub.profile;

import android.app.Activity;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.youthhub.R;
import com.example.youthhub.resModel.profile.journey.Media;
import com.example.youthhub.resModel.profile.journey.ProfileJourneyListResponse;
import com.example.youthhub.utils.AppUtils;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

public class Visualslideadpater extends PagerAdapter {
Activity activity;
ProfileJourneyListResponse profileJourneyListResponse;
String userType;
List<Media> media;


    public Visualslideadpater(Activity activity, List<Media> media,ProfileJourneyListResponse profileJourneyListResponse) {
        this.activity = activity;
        this.media = media;
        this.profileJourneyListResponse=profileJourneyListResponse;
    }

    @Override
    public int getCount() {
        return media.size();
    }


   @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       ((ViewPager) container).removeView((View) object);
    }




    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slide_visual, container, false);
        assert view != null;
        PhotoView imageView = (PhotoView) view.findViewById(R.id.post_images);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(view, 0);

            Glide.with(activity).load(profileJourneyListResponse.getData().getImagePathMedium()+media.get(position).getJucImage())
                    .apply(AppUtils.getRequestOption())
                    .listener(AppUtils.requestListener)
                    .into(imageView);

        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }
    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
