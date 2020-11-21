package com.example.youthhub.dashBoard.dashboard;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youthhub.R;
import com.example.youthhub.resModel.post.EncouragersList;
import com.example.youthhub.resModel.post.PostLikeResponse;
import com.example.youthhub.utils.Constants;
import com.example.youthhub.utils.FontTypeFace;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LikeActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.like_icon)
    ImageView like_icon;
    @BindView(R.id.like_count)
    TextView commentTxt;
    @BindView(R.id.like_comment)
    TextView like_comment;
    @BindView(R.id.comment_like_recycler)
    RecyclerView commentLikeRecycler;
    LikeAdapter likeAdapter;
    PostLikeResponse postLikeResponse;

    List<EncouragersList> encouragersLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_like);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            postLikeResponse = bundle.getParcelable(Constants.PostLikeList);
            int encourage_status = bundle.getInt("encourage_status");
            if (postLikeResponse != null) {
                encouragersLists = postLikeResponse.getPostLikeData().getEncouragersList();
                String likes = String.valueOf(encouragersLists.size());

                if (likes.isEmpty()){
                    like_comment.setVisibility(View.GONE);
                    commentTxt.setVisibility(View.GONE);
                    like_icon.setVisibility(View.GONE);
                }
                else if (likes.equals("1")){
                    like_comment.setText("Like");
                }
                commentTxt.setText(likes);
                call_adapter(encouragersLists,postLikeResponse.getPostLikeData().getUserMediumPath(),postLikeResponse.getPostLikeData().getUserThumbnailPath());
            }
        }
        callTypeFace();
    }

    private void callTypeFace() {
        commentTxt.setTypeface(FontTypeFace.fontBold(this));
    }

    private void call_adapter(List<EncouragersList> encouragersLists, String userMediumPath, String userThumbnailPath) {
        commentLikeRecycler.setLayoutManager(new LinearLayoutManager(this));
        likeAdapter = new LikeAdapter(this);
        commentLikeRecycler.setAdapter(likeAdapter);
        likeAdapter.addAll(encouragersLists,userMediumPath,userThumbnailPath);

    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                overridePendingTransition(R.anim.stay, R.anim.activity_slide_down);
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

