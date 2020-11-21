package com.example.youthhub.dashBoard.exploreFragment;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.youthhub.R;
import com.example.youthhub.utils.Constants;
import com.example.youthhub.utils.FontTypeFace;
import com.example.youthhub.utils.MyToast;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageViewActivity extends AppCompatActivity {
    @BindView(R.id.image)
    PhotoView image;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu_dot)
    ImageView menu_dot;
    @BindView(R.id.rorate_menu1)
    TextView rorate_menu1;
    String paththumb;
    String viewimage;
    int rotate = 0;
    ArrayList<String> imagelist;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            paththumb = bundle.getString("pathThumb");
            viewimage = bundle.getString("viewimage");

            title.setTypeface(FontTypeFace.fontBold(this));
            rorate_menu1.setTypeface(FontTypeFace.fontBold(this));
            //  imagelist = bundle.getStringArrayList("imagelist");

//            PhotoViewAttacher photoview = new PhotoViewAttacher(image);
//            photoview.update();


            Glide.with(this)
                    .load(paththumb + viewimage)
                    .into(image);

//            Animation animation1 =
//                    AnimationUtils.loadAnimation(getApplicationContext(),
//                            R.anim.blink);
//            rotate_image.startAnimation(animation1);
//
//            rotate_image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    image.setRotation(image.getRotation() + 90);
//                }
//            });

        }

    }


    @OnClick({R.id.back, R.id.back_constrain, R.id.menu_dot, R.id.rorate_menu1, R.id.image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
            case R.id.back_constrain:
                onBackPressed();
                break;
            case R.id.menu_dot:
                rotate++;
                if ((rotate % 2) == 0){
                    rorate_menu1.setVisibility(View.GONE);
                } else {
                    rorate_menu1.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rorate_menu1:
                rotate = 0;
                rorate_menu1.setVisibility(View.GONE);
                image.setRotation(image.getRotation() + 90);
                break;
            case R.id.image:
                rotate = 0;
                rorate_menu1.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.stay, R.anim.activity_slide_down);
    }


}

