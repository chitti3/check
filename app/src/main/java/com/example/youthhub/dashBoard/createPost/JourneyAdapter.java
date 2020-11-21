package com.example.youthhub.dashBoard.createPost;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.youthhub.R;
import com.example.youthhub.resModel.post.createPost.PrimeTag;
import com.example.youthhub.resModel.profile.visualjourney.PrimeTagsItem;
import com.example.youthhub.utils.Constants;
import com.example.youthhub.utils.FontTypeFace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JourneyAdapter extends RecyclerView.Adapter<JourneyAdapter.MyViewHolder> {

    private Activity activity;
    private int num = 2;

    private List<PrimeTag> primeTags = new ArrayList<>();
    private List<PrimeTag> selectedTags;
    private String primeTagsPath;
    private OnPassDataListener onPassDataListener;

    public void setOnPassDataListener(OnPassDataListener onPassDataListener) {
        this.onPassDataListener = onPassDataListener;
    }

    JourneyAdapter(Activity activity) {
        this.activity = activity;
    }

    public void addAll(List<PrimeTag> primeTags, String primeTagsPath, List<PrimeTag> selectedTags) {
        this.primeTags = primeTags;
        this.primeTagsPath = primeTagsPath;
        this.selectedTags = selectedTags;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.journey_adapter, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      /*  if (position == num) {
            holder.journeyView1.setVisibility(View.GONE);
            num = num + 3;
        } else {
            holder.journeyView1.setVisibility(View.VISIBLE);
        }*/


        PrimeTag primeTag = primeTags.get(position);


        holder.journeyConstrain1.setTag(R.id.holder_tag, holder);
        holder.journeyConstrain1.setTag(R.id.data_tag, primeTag);
        holder.journeyConstrain1.setTag(R.id.position, position);

        holder.journeyTxt.setTypeface(FontTypeFace.fontMedium(activity));
        holder.journeyTxt.setText(primeTag.getTgName());

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(activity)
                .load(Constants.getLoadGlide(activity, primeTagsPath + primeTag.getTgIcon()))
                .apply(options)
                .into(holder.journeyImg);

        for (int i = 0; i < selectedTags.size(); i++) {
            if (primeTag.getTgTagId().equals(selectedTags.get(i).getTgTagId())) {
                holder.journeyConstrain1.performClick();
            }
        }

    }

    @Override
    public int getItemCount() {
        return primeTags.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.select)
        ImageView select;
        @BindView(R.id.journey_img)
        ImageView journeyImg;
        @BindView(R.id.journey_txt)
        TextView journeyTxt;
        @BindView(R.id.journey_constrain1)
        ConstraintLayout journeyConstrain1;
        @BindView(R.id.demo)
        ConstraintLayout demo;
    /*    @BindView(R.id.journey_view1)
        View journeyView1;*/

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.journey_constrain1)
        public void onViewClicked(View view) {
            if (view.getId() == R.id.journey_constrain1) {
                MyViewHolder holder = (MyViewHolder) view.getTag(R.id.holder_tag);
                PrimeTag primeTag = (PrimeTag) view.getTag(R.id.data_tag);

                ShapeDrawable shape = new ShapeDrawable(new RectShape());
                shape.getPaint().setStyle(Paint.Style.STROKE);
                shape.getPaint().setStrokeWidth(5);

                if(holder.select.getVisibility() != View.VISIBLE) {
                    holder.select.setVisibility(View.VISIBLE);
                    holder.demo.setBackground(shape);
                    if (primeTag.getTgName().contains("Community")){
                        shape.getPaint().setColor(Color.parseColor("#212121"));
                        holder.journeyTxt.setTextColor(Color.parseColor("#212121"));
                        holder.demo.setBackground(shape);
                    }else  if (primeTag.getTgName().contains("Education")){
                        shape.getPaint().setColor(Color.parseColor("#66BB6A"));
                        holder.journeyTxt.setTextColor(Color.parseColor("#66BB6A"));
                        holder.demo.setBackground(shape);
                    }else  if (primeTag.getTgName().contains("Employment")){
                        shape.getPaint().setColor(Color.parseColor("#FFD54F"));
                        holder.journeyTxt.setTextColor(Color.parseColor("#FFD54F"));
                        holder.demo.setBackground(shape);
                    }else  if (primeTag.getTgName().contains("Events")){
                        shape.getPaint().setColor(Color.parseColor("#FF7043"));
                        holder.journeyTxt.setTextColor(Color.parseColor("#FF7043"));
                        holder.demo.setBackground(shape);
                    }else  if (primeTag.getTgName().contains("My Interests")){
                        shape.getPaint().setColor(Color.parseColor("#0D47A1"));
                        holder.journeyTxt.setTextColor(Color.parseColor("#0D47A1"));
                        holder.demo.setBackground(shape);
                    }else  if (primeTag.getTgName().contains("Projects")){
                        shape.getPaint().setColor(Color.parseColor("#7F8C8D"));
                        holder.journeyTxt.setTextColor(Color.parseColor("#7F8C8D"));
                        holder.demo.setBackground(shape);
                    }else  if (primeTag.getTgName().contains("Training")){
                        shape.getPaint().setColor(Color.parseColor("#3498DB"));
                        holder.journeyTxt.setTextColor(Color.parseColor("#3498DB"));
                        holder.demo.setBackground(shape);
                    }else  if (primeTag.getTgName().contains("Volunteering")){
                        shape.getPaint().setColor(Color.parseColor("#9B59B6"));
                        holder.journeyTxt.setTextColor(Color.parseColor("#9B59B6"));
                        holder.demo.setBackground(shape);
                    }

                } else {
                    holder.select.setVisibility(View.INVISIBLE);
                    shape.getPaint().setColor(Color.parseColor("#F5F5F5"));
                    holder.journeyTxt.setTextColor(Color.parseColor("#000000"));
                    holder.demo.setBackground(shape);
                }
                onPassDataListener.onPassData(primeTag);
            }

        }

    }

    public interface OnPassDataListener {
        void onPassData(PrimeTag primeTag);
    }

}

