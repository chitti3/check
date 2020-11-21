package com.example.youthhub.dashBoard;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.youthhub.R;
import com.example.youthhub.profile.ProfileFragment;
import com.example.youthhub.resModel.CommonNewResponse;
import com.example.youthhub.resModel.CommonRes;
import com.example.youthhub.resModel.event.discussion.DiscussionAdd;
import com.example.youthhub.resModel.explore.exploreDiscussion.ExploreDiscussionAdd;
import com.example.youthhub.resModel.profile.BasicResponse;
import com.example.youthhub.resModel.profile.visualjourney.Delete.Journey_delete_model;
import com.example.youthhub.retrofit.ApiClient;
import com.example.youthhub.utils.Constants;
import com.example.youthhub.utils.FontTypeFace;
import com.example.youthhub.utils.Loader;
import com.example.youthhub.utils.MyToast;
import com.example.youthhub.utils.NetWorkUtil;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.youthhub.profile.ProfileInfoDialog.TAG;

public class DeleteMessageDialog extends Dialog {

    Activity activity;
    @BindView(R.id.dialog_title)
    TextView dialogTitle;
    @BindView(R.id.delete_txt)
    TextView deleteTxt;
    @BindView(R.id.view8)
    View view8;
    @BindView(R.id.yes_btn)
    TextView yesBtn;
    @BindView(R.id.view6)
    View view6;
    @BindView(R.id.cancel_btn)
    TextView cancelBtn;

    private OnDeleteListener onDeleteListener;
    private String code;
    private String feedId;
    private String topicId;
    private String from;
    private BasicResponse basicResponse;

    public DeleteMessageDialog(Activity activity, String from, String code, String feedId, String topicId) {
        super(activity);
        this.activity = activity;
        this.from = from;
        this.code = code;
        this.feedId = feedId;
        this.topicId = topicId;
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.delete_message);
        ButterKnife.bind(this);
        callTypeFace();
        switch (from) {
            case "Explore":
                break;
            case "Event":
                break;
            case "Comment":
                deleteTxt.setText("Are you sure you would like to delete this comment?");
                break;
            case "Post":
                deleteTxt.setText("Are you sure you would like to delete this post?");
                break;
            case "wishlist":
                deleteTxt.setText("Are you sure to delete this wishlist item?");
                break;
            case "testimonial":
                deleteTxt.setText("Are you sure to delete this Testimonial?");
                break;
            case "Experience":
                deleteTxt.setText("Are you sure to delete this Experience?");
                break;
            case "Education":
                deleteTxt.setText("Are you sure to delete this Education?");
                break;
            case "Volunteering Experience":
                deleteTxt.setText("Are you sure to delete this Volunteering Experience?");
                break;
            case "Achievement and Awards":
                deleteTxt.setText("Are you sure to delete this Achievement and Awards?");
                break;
            case "Attach CV":
                deleteTxt.setText("Are you sure to delete this CV?");
                break;
            case "Technical Skills":
                deleteTxt.setText("Are you sure to delete this Technical Skill?");
                break;
            case "Interests":
                deleteTxt.setText("Are you sure to delete this Interest?");
                break;
            case "Languages":
                deleteTxt.setText("Are you sure to delete this Language?");
                break;
            case "Visual":
                deleteTxt.setText("Are you sure to delete this Visual Journey?");
                break;
        }
    }

    private void callTypeFace() {
        dialogTitle.setTypeface(FontTypeFace.fontBold(activity));
        yesBtn.setTypeface(FontTypeFace.fontBold(activity));
        cancelBtn.setTypeface(FontTypeFace.fontBold(activity));
    }

    @OnClick({R.id.yes_btn, R.id.cancel_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.yes_btn:
                if (from.equals("Explore")) {
                    explore_delete_msg_api();
                } else if (from.equals("Event")) {
                    event_delete_msg_api();
                } else if (from.equals("Comment")) {
                    comment_delete_api();
                } else if (from.equals("wishlist")) {
                    call_delete_wishlist_api(code);
                }else if (from.equals("testimonial")){
                    call_delete_testimonial_api(code);
                }else if(from.equals("Experience"))
                {
                    call_delete_experience_api(code);
                }else if (from.equals("Education"))
                {
                    call_delete_eduction_api(code);
                }else if (from.equals("Volunteering Experience"))
                {
                    call_delete_volunteer_api(code);
                }
                else if (from.equals("Achievement and Awards"))
                {
                    call_delete_achievement_api(code);
                }
                else if (from.equals("Achievement and Awards"))
                {
                    call_delete_achievement_api(code);
                }
                else if (from.equals("Attach CV"))
                {
                    call_delete_cv_api(code);
                }
                else if (from.equals("Technical Skills"))
                {
                    call_delete_technical_skills_api(code);
                }
                else if (from.equals("Interests"))
                {
                    call_delete_interests_api(code);
                }
                else if (from.equals("Languages"))
                {
                    call_delete_languages_api(code);
                }
                else if (from.equals("Visual"))
                {
                    call_delete_visual_api(code);
                }
                else {
                    post_delete_api();
                }
                break;
            case R.id.cancel_btn:
                dismiss();
                break;
        }
    }

    private void call_delete_visual_api(String code) {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<Journey_delete_model> call = ApiClient.getApiInterface().journeydelete(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                    Constants.getToken(activity), code);

            call.enqueue(new Callback<Journey_delete_model>() {
                @Override
                public void onResponse(@NonNull Call<Journey_delete_model> call, @NonNull Response<Journey_delete_model> response) {
                    if (response.isSuccessful()) {
                        //   Log.d(TAG, "onResponse:ProfileYouthInfoResponse 1 ");
                        if (response.body() != null) {
                            if (response.body().getStatus()==1) {
                                Journey_delete_model journey_delete_model = response.body();
                                onDeleteListener.OnDelete(true);
                                MyToast.normalMessage(response.body().getMessage(), activity);
                            } else {
                                //     Log.d(TAG, "onResponse:ProfileYouthInfoResponse else "+new Gson().toJson(profileInfoResp));
                                Log.d(TAG, "onResponse:failure " + new Gson().toJson(response.body()));
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }

                        }
                    } else {
                        Log.d(Constants.failureResponse, "Visual_Journey_delete" + response.toString());
                    }dismiss();
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<Journey_delete_model> call, @NonNull Throwable t) {
                    call_delete_visual_api(code);
                    Log.d(Constants.failureResponse, "Visual_Journey_delete" + t.toString());
                    Loader.showLoad(activity, false);
                }
            });
        }
    }


    private void call_delete_languages_api(String language_id) {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<BasicResponse> call = ApiClient.getApiInterface().deleteLanguages(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                    Constants.getToken(activity), language_id
            );

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        //   Log.d(TAG, "onResponse:ProfileYouthInfoResponse 1 ");
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                basicResponse = response.body();
                                onDeleteListener.OnDelete(true);
                                MyToast.normalMessage(response.body().getMessage(), activity);
                            } else {
                                //     Log.d(TAG, "onResponse:ProfileYouthInfoResponse else "+new Gson().toJson(profileInfoResp));
                                Log.d(ProfileFragment.TAG, "onResponse:failure " + new Gson().toJson(response.body()));
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }

                        }
                    } else {
                        Log.d(Constants.failureResponse, " profile_info_update" + response.toString());
                    }dismiss();
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                    call_delete_languages_api(language_id);
                    Log.d(Constants.failureResponse, " profile_info_update" + t.toString());
                    Loader.showLoad(activity, false);
                }
            });

        }
    }

    private void call_delete_interests_api(String interest_id) {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<BasicResponse> call = ApiClient.getApiInterface().deleteInterests(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                    Constants.getToken(activity), interest_id
            );

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        //   Log.d(TAG, "onResponse:ProfileYouthInfoResponse 1 ");
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                basicResponse = response.body();
                                onDeleteListener.OnDelete(true);
                                MyToast.normalMessage(response.body().getMessage(), activity);
                            } else {
                                //     Log.d(TAG, "onResponse:ProfileYouthInfoResponse else "+new Gson().toJson(profileInfoResp));
                                Log.d(ProfileFragment.TAG, "onResponse:failure " + new Gson().toJson(response.body()));
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }

                        }
                    } else {
                        Log.d(Constants.failureResponse, " profile_info_update" + response.toString());
                    }
                    dismiss();
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                    call_delete_interests_api(interest_id);
                    Log.d(Constants.failureResponse, " profile_info_update" + t.toString());
                    Loader.showLoad(activity, false);
                }
            });

        }
    }


    private void call_delete_technical_skills_api(String technical_skills_id) {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<BasicResponse> call = ApiClient.getApiInterface().deleteTechnicalSkills(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                    Constants.getToken(activity), technical_skills_id
            );

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        //   Log.d(TAG, "onResponse:ProfileYouthInfoResponse 1 ");
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                basicResponse = response.body();
                                onDeleteListener.OnDelete(true);
                                MyToast.normalMessage(response.body().getMessage(), activity);
                            } else {
                                //     Log.d(TAG, "onResponse:ProfileYouthInfoResponse else "+new Gson().toJson(profileInfoResp));
                                Log.d(ProfileFragment.TAG, "onResponse:failure " + new Gson().toJson(response.body()));
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }

                        }
                    } else {
                        Log.d(Constants.failureResponse, " profile_info_update" + response.toString());
                    }
                    dismiss();
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                    call_delete_technical_skills_api(technical_skills_id);
                    Log.d(Constants.failureResponse, " profile_info_update" + t.toString());
                    Loader.showLoad(activity, false);
                }
            });

        }
    }

    private void call_delete_cv_api(String cv_id) {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<BasicResponse> call = ApiClient.getApiInterface().deleteCV(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                    Constants.getToken(activity),cv_id
            );

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        //   Log.d(TAG, "onResponse:ProfileYouthInfoResponse 1 ");
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                basicResponse = response.body();
                                onDeleteListener.OnDelete(true);
                                MyToast.normalMessage(response.body().getMessage(), activity);
                            } else {
                                //     Log.d(TAG, "onResponse:ProfileYouthInfoResponse else "+new Gson().toJson(profileInfoResp));
                                Log.d(ProfileFragment.TAG, "onResponse:failure "+new Gson().toJson(response.body()));
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }

                        }
                    } else {
                        Log.d(Constants.failureResponse, " profile_info_update" + response.toString());
                    }
                    dismiss();
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                    call_delete_cv_api(cv_id);
                    Log.d(Constants.failureResponse, " profile_info_update" + t.toString());
                    Loader.showLoad(activity, false);
                }
            });

        }
    }

    private void call_delete_achievement_api(String achievement_id) {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<BasicResponse> call = ApiClient.getApiInterface().deleteAchievement(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                    Constants.getToken(activity), achievement_id
            );

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        //   Log.d(TAG, "onResponse:ProfileYouthInfoResponse 1 ");
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                basicResponse = response.body();
                                onDeleteListener.OnDelete(true);
                                MyToast.normalMessage(response.body().getMessage(), activity);
                            } else {
                                //     Log.d(TAG, "onResponse:ProfileYouthInfoResponse else "+new Gson().toJson(profileInfoResp));
                                Log.d(TAG, "onResponse:failure " + new Gson().toJson(response.body()));
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }

                        }
                    } else {
                        Log.d(Constants.failureResponse, " profile_info_update" + response.toString());
                    }
                    dismiss();
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                    call_delete_achievement_api(achievement_id);
                    Log.d(Constants.failureResponse, " profile_info_update" + t.toString());
                    Loader.showLoad(activity, false);
                }
            });

        }
    }



    private void call_delete_volunteer_api(String volunteer_id) {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<BasicResponse> call = ApiClient.getApiInterface().deleteVolunteering(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                    Constants.getToken(activity), volunteer_id
            );

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        //   Log.d(TAG, "onResponse:ProfileYouthInfoResponse 1 ");
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                basicResponse = response.body();
                               onDeleteListener.OnDelete(true);
                                MyToast.normalMessage(response.body().getMessage(), activity);
                            } else {
                                //     Log.d(TAG, "onResponse:ProfileYouthInfoResponse else "+new Gson().toJson(profileInfoResp));
                                Log.d(ProfileFragment.TAG, "onResponse:failure " + new Gson().toJson(response.body()));
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }

                        }
                    } else {
                        Log.d(Constants.failureResponse, " profile_info_update" + response.toString());
                    }
                    dismiss();
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                    call_delete_volunteer_api(volunteer_id);
                    Log.d(Constants.failureResponse, " profile_info_update" + t.toString());
                    Loader.showLoad(activity, false);
                }
            });

        }
    }

    private void call_delete_eduction_api(String education_id) {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<BasicResponse> call = ApiClient.getApiInterface().deleteEduction(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                    Constants.getToken(activity), education_id
            );

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        //   Log.d(TAG, "onResponse:ProfileYouthInfoResponse 1 ");
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                basicResponse = response.body();
                                 onDeleteListener.OnDelete(true);
                                MyToast.normalMessage(response.body().getMessage(), activity);
                            } else {
                                //     Log.d(TAG, "onResponse:ProfileYouthInfoResponse else "+new Gson().toJson(profileInfoResp));
                                Log.d(ProfileFragment.TAG, "onResponse:failure " + new Gson().toJson(response.body()));
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }

                        }
                    } else {
                        Log.d(Constants.failureResponse, " profile_info_update" + response.toString());
                    }
                    dismiss();
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                    call_delete_eduction_api(education_id);
                    Log.d(Constants.failureResponse, " profile_info_update" + t.toString());
                    Loader.showLoad(activity, false);
                }
            });

        }
    }

    private void call_delete_experience_api(String code) {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<BasicResponse> call = ApiClient.getApiInterface().deleteExperience(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                    Constants.getToken(activity), code
            );

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        //   Log.d(TAG, "onResponse:ProfileYouthInfoResponse 1 ");
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                basicResponse = response.body();
                                  onDeleteListener.OnDelete(true);
                            MyToast.normalMessage(response.body().getMessage(), activity);
                            } else {
                                //     Log.d(TAG, "onResponse:ProfileYouthInfoResponse else "+new Gson().toJson(profileInfoResp));
                                Log.d(ProfileFragment.TAG, "onResponse:failure " + new Gson().toJson(response.body()));
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }

                        }
                    } else {
                        Log.d(Constants.failureResponse, " profile_info_update" + response.toString());
                    }dismiss();
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                    call_delete_experience_api(code);
                    Log.d(Constants.failureResponse, " profile_info_update" + t.toString());
                    Loader.showLoad(activity, false);
                }
            });
        }
        }

    private void call_delete_testimonial_api(String code) {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<BasicResponse> call = ApiClient.getApiInterface().deleteTestimonial(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                    Constants.getToken(activity),code
            );

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        //   Log.d(TAG, "onResponse:ProfileYouthInfoResponse 1 ");
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                basicResponse = response.body();
                                onDeleteListener.OnDelete(true);
                                MyToast.normalMessage(response.body().getMessage(), activity);
                            } else {
                                //     Log.d(TAG, "onResponse:ProfileYouthInfoResponse else "+new Gson().toJson(profileInfoResp));
                                Log.d(ProfileFragment.TAG, "onResponse:failure "+new Gson().toJson(response.body()));
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }

                        }
                    } else {
                        Log.d(Constants.failureResponse, " profile_info_update" + response.toString());
                    }
                    dismiss();
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                    call_delete_wishlist_api(code);
                    Log.d(Constants.failureResponse, " profile_info_update" + t.toString());
                    Loader.showLoad(activity, false);
                }
            });

        }
    }

    private void call_delete_wishlist_api(String wishlish_id) {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<BasicResponse> call = ApiClient.getApiInterface().deleteWishlist(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                    Constants.getToken(activity), wishlish_id
            );

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicResponse> call, @NonNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        //   Log.d(TAG, "onResponse:ProfileYouthInfoResponse 1 ");
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                basicResponse = response.body();
                                onDeleteListener.OnDelete(true);
                                MyToast.normalMessage(response.body().getMessage(), activity);
                            } else {
                                //     Log.d(TAG, "onResponse:ProfileYouthInfoResponse else "+new Gson().toJson(profileInfoResp));
                                Log.d(TAG, "onResponse:failure " + new Gson().toJson(response.body()));
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }

                        }
                    } else {
                        Log.d(Constants.failureResponse, " profile_info_update" + response.toString());
                    }
                    dismiss();
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                    call_delete_wishlist_api(wishlish_id);
                    Log.d(Constants.failureResponse, " profile_info_update" + t.toString());
                    Loader.showLoad(activity, false);
                }
            });

        }
    }

    private void post_delete_api() {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<CommonRes> call = ApiClient.getApiInterface().getPostDelete(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                    Constants.getToken(activity),
                    code);

            call.enqueue(new Callback<CommonRes>() {
                @Override
                public void onResponse(@NonNull Call<CommonRes> call, @NonNull Response<CommonRes> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                onDeleteListener.OnDelete(true);
                            } else {
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }
                        }
                    } else {
                        Log.d(Constants.failureResponse + " PostDelete", response.toString());
                    }
                    dismiss();
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<CommonRes> call, @NonNull Throwable t) {
                    Log.d(Constants.failureResponse + " PostDelete", t.toString());
                    Loader.showLoad(activity, false);
                }
            });

        }
    }

    private void comment_delete_api() {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<CommonNewResponse> call = ApiClient.getApiInterface().getCommentDelete(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                    Constants.getToken(activity),
                    code,
                    feedId);

            call.enqueue(new Callback<CommonNewResponse>() {
                @Override
                public void onResponse(@NonNull Call<CommonNewResponse> call, @NonNull Response<CommonNewResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {

                                System.out.println("====-===="+ response.body().getStatus());
                                onDeleteListener.OnDelete(true);
                            } else {
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }
                        }
                    } else {
                        Log.d(Constants.failureResponse + " DeleteComment", response.toString());
                    }
                    dismiss();
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<CommonNewResponse> call, @NonNull Throwable t) {
                    Log.d(Constants.failureResponse + " DeleteComment", t.toString());
                    MyToast.errorMessage(activity.getResources().getString(R.string.error_msg), activity);
                    Loader.showLoad(activity, false);
                    dismiss();
                }
            });

        }
    }

    private void explore_delete_msg_api() {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<ExploreDiscussionAdd> call = ApiClient.getApiInterface().getExploreDiscussionDelete(Constants.getApiKey(activity), Constants.getAccessKey(activity), Constants.getToken(activity), code, topicId, feedId);
            call.enqueue(new Callback<ExploreDiscussionAdd>() {
                @Override
                public void onResponse(@NonNull Call<ExploreDiscussionAdd> call, @NonNull Response<ExploreDiscussionAdd> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                onDeleteListener.OnDelete(true);
                            } else {
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }
                        }
                    } else {
                        Log.d(Constants.failureResponse + " DeleteExplore", response.toString());
                    }
                    dismiss();
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<ExploreDiscussionAdd> call, @NonNull Throwable t) {
                    Log.d(Constants.failureResponse + " DeleteExplore", t.toString());
                    dismiss();
                    MyToast.errorMessage(activity.getResources().getString(R.string.error_msg), activity);
                    Loader.showLoad(activity, false);
                }
            });
        }
    }

    private void event_delete_msg_api() {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<DiscussionAdd> discussionAddCall = ApiClient.getApiInterface().getDeleteMsg(Constants.getApiKey(activity), Constants.getAccessKey(activity), Constants.getToken(activity), code, feedId);
            discussionAddCall.enqueue(new Callback<DiscussionAdd>() {
                @Override
                public void onResponse(@NonNull Call<DiscussionAdd> call, @NonNull Response<DiscussionAdd> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                onDeleteListener.OnDelete(true);
                            } else {
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }
                        }
                    } else {
                        Log.d(Constants.failureResponse + " DeleteEvent", response.toString());
                    }
                    dismiss();
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<DiscussionAdd> call, @NonNull Throwable t) {
                    Log.d(Constants.failureResponse + " DeleteEvent", t.toString());
                    dismiss();
                    MyToast.errorMessage(activity.getResources().getString(R.string.error_msg), activity);
                    Loader.showLoad(activity, false);
                }
            });
        }
    }

    public interface OnDeleteListener {
        void OnDelete(boolean deleted);
    }

}