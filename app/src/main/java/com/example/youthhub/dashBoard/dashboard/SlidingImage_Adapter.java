package com.example.youthhub.dashBoard.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.example.youthhub.R;
import com.example.youthhub.dashBoard.exploreFragment.ImageViewActivity;
import com.example.youthhub.utils.MyToast;
import com.github.chrisbanes.photoview.PhotoView;
import com.zolad.zoominimageview.ZoomInImageView;

import java.util.ArrayList;

class SlidingImage_Adapter extends PagerAdapter {


    private String[] urls;
    private LayoutInflater inflater;
    // private Context context;
    Activity context;
    ArrayList<String> imagelist;
    ArrayList<String> videolist;
    String pathThumb;
    PostImageViewActivity postImageViewActivity;



    public SlidingImage_Adapter(PostImageViewActivity postImageViewActivity, ArrayList<String> imagelist, ArrayList<String> videolist ,  String pathThumb) {
        this.postImageViewActivity = postImageViewActivity;
        this.imagelist = imagelist;
        this.videolist = videolist;
        this.pathThumb = pathThumb;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imagelist.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        //  View imageLayout = inflater.inflate(R.layout.post_images_adapter, view, false);

        View views = LayoutInflater.from(view.getContext()).inflate(R.layout.post_images_adapter, view, false);

//        View views = layoutInflater.inflate(R.layout.post_images_adapter, view, false);

        assert views != null;
        final PhotoView imageView = (PhotoView) views.findViewById(R.id.post_images);
        VideoView videoView = (VideoView) views.findViewById(R.id.video);
        ImageView play_btn = (ImageView) views.findViewById(R.id.play_btn);
       // ImageView rotate_btn = (ImageView) views.findViewById(R.id.rotate_btn);
//        PhotoViewAttacher photoview = new PhotoViewAttacher(imageView);
//        photoview.update();

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);


        view.addView(views);



        if (imagelist.get(position).isEmpty()){
            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
            play_btn.setVisibility(View.VISIBLE);


            play_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    videoView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                    play_btn.setVisibility(View.GONE);
                    videoView.setHandleAudioFocus(true);
                    videoView.showControls();
                    videoView.setVideoURI(Uri.parse(videolist.get(position)));
                    videoView.setOnPreparedListener(() -> videoView.start());
                    videoView.setOnCompletionListener(new OnCompletionListener() {
                        @Override
                        public void onCompletion() {
                            videoView.start();
                        }
                    });

                    videoView.setOnErrorListener(e -> {
                        MyToast.errorMessage("Oops An Error Occur While Playing Video...!!!",postImageViewActivity); // display a toast when an error is occured while playing an video
                        return false;
                    });
                }
            });

        }else {

            imageView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            //  rotate_btn.setVisibility(View.VISIBLE);

//            Animation animation1 = AnimationUtils.loadAnimation(postImageViewActivity, R.anim.blink);
//            rotate_btn.startAnimation(animation1);

//            rotate_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    imageView.setRotation(imageView.getRotation() + 90);
//                }
//            });

            Glide.with(postImageViewActivity)
                    .load(pathThumb + imagelist.get(position))
                    .into(imageView);
        }



        imageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(postImageViewActivity , ImageViewActivity.class);
                intent.putExtra("pathThumb", pathThumb);
                //  intent.putExtra("imagelist", imagelist);
                intent.putExtra("viewimage", imagelist.get(position));
                postImageViewActivity.startActivity(intent);
                postImageViewActivity.overridePendingTransition(R.anim.activity_slide_up, R.anim.stay);
            }
        });


        //   view.addView(views, 0);




        return views;
    }

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
