package com.example.youthhub.dashBoard.createPost;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.youthhub.R;
import com.example.youthhub.UploadFileDialog;
import com.example.youthhub.dashBoard.InterfaceClass;
import com.example.youthhub.resModel.post.OwnTag;
import com.example.youthhub.resModel.post.createPost.JourneyList;
import com.example.youthhub.resModel.post.createPost.PostAddMaster;
import com.example.youthhub.resModel.post.createPost.PostGalleryUpload;
import com.example.youthhub.resModel.post.createPost.PostType;
import com.example.youthhub.resModel.post.createPost.PrimeTag;
import com.example.youthhub.resModel.post.createPost.Tag;
import com.example.youthhub.resModel.profile.visualjourney.TagsItem;
import com.example.youthhub.resModel.profilepostlist.PostDashboardListResponse;
import com.example.youthhub.retrofit.ApiClient;
import com.example.youthhub.utils.AppUtils;
import com.example.youthhub.utils.Constants;
import com.example.youthhub.utils.FontTypeFace;
import com.example.youthhub.utils.Loader;
import com.example.youthhub.utils.MyToast;
import com.example.youthhub.utils.NetWorkUtil;
import com.example.youthhub.utils.Preference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostActivity extends AppCompatActivity implements
        TagsJourneyDialog.PassListItems,
        ImagePostAdapter.OnPassDataListener,
        JourneyAdapter.OnPassDataListener {

    @BindView(R.id.create_post_txt)
    TextView createPostTxt;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_constrain)
    ConstraintLayout topConstrain;
    @BindView(R.id.profile_img)
    CircleImageView profileImg;
    @BindView(R.id.profiler_name)
    TextView profilerName;
    @BindView(R.id.post_type)
    AppCompatSpinner postType;
    @BindView(R.id.post_desc)
    EditText postDesc;
    @BindView(R.id.post_images_recycler)
    RecyclerView postImagesRecycler;
    @BindView(R.id.journey_txt)
    TextView journeyTxt;
    @BindView(R.id.journey_recycler)
    RecyclerView journeyRecycler;
    @BindView(R.id.select_tag)
    TextView selectTag;
    @BindView(R.id.select_visual_journey)
    TextView selectVisualJourney;
    @BindView(R.id.post_scroll)
    LinearLayout postScroll;
    @BindView(R.id.post_view2)
    View postView2;
    @BindView(R.id.camera)
    ImageView camera;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.location)
    ImageView location;
    @BindView(R.id.cmt_post_btn)
    ImageView cmtPostBtn;
    @BindView(R.id.bottom_constrain)
    ConstraintLayout bottomConstrain;
    @BindView(R.id.milestone_text)
    TextView milestonetext;
    @BindView(R.id.text_fullscreen)
    ImageView text_fullscreen;
    @BindView(R.id.text_fullscreen_exit)
    ImageView text_fullscreen_exit;
    ImagePostAdapter imagePostAdapter;
    JourneyAdapter journeyAdapter;
    PostAddMaster postAddMaster = null;

    Activity activity;

    UploadFileDialog uploadFileDialog;
    TagsJourneyDialog tagsJourneyDialog;
    @BindView(R.id.connection_user_code_profile)
    TextView connectionUserCodeProfile;
    @BindView(R.id.profile_img11)
    ConstraintLayout profileImg11;

    //restore previous filter
    private List<Tag> selectedTags = new ArrayList<>();
    private List<JourneyList> selectedJourneyLists = new ArrayList<>();

    List<PostImageModel> postImageModels = new ArrayList<>();


    int galleryCodeLength = 0;
    int primeTagLength = 0;
    List<PrimeTag> selectedPrimeTags = new ArrayList<>();

    //Api datas
    String post_title = "";
    String galleryCodes = "";
    String tag = "";
    String tagid="";
    String owntagid="";
    String prime_tags = "";
    String group_code = "";
    String postTypeId = "";
    String visualJourney = "";
    String tags = "";
    String owntagsname="";
    String owntagname="";
    PostDashboardListResponse postAddListResponse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        ButterKnife.bind(this);
        activity = this;
        callTypeFace();
        //   postImagesRecycler.setVisibility(View.GONE);

        init();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            postAddMaster = bundle.getParcelable(Constants.PostAddMaster);
            if (postAddMaster != null) {
                load_post_type_spinner(postAddMaster.getPostAddMasterData().getPostType());
                call_journey_adapter(postAddMaster.getPostAddMasterData().getPrimeTags(), postAddMaster.getPostAddMasterData().getPrimeTagsPath());
            }
        }
        uploadFileDialog = new UploadFileDialog(activity, "Post");
    }

    private void call_journey_adapter(List<PrimeTag> primeTags, String primeTagsPath) {

        List<PrimeTag>  primeTags2 = new ArrayList<>();

        primeTags2.add(primeTags.get(1));
        primeTags2.add(primeTags.get(6));
        primeTags2.add(primeTags.get(2));
        primeTags2.add(primeTags.get(4));
        primeTags2.add(primeTags.get(7));
        primeTags2.add(primeTags.get(5));
        primeTags2.add(primeTags.get(3));
        primeTags2.add(primeTags.get(0));

        journeyAdapter.addAll(primeTags2, primeTagsPath, selectedPrimeTags);
        //journeyAdapter.addAll(primeTags, primeTagsPath, selectedPrimeTags);
    }

    private void load_post_type_spinner(List<PostType> postTypeList) {
        ArrayAdapter<PostType> adapter = new ArrayAdapter<>(activity, R.layout.spinner_text, postTypeList);
        adapter.setDropDownViewResource(R.layout.spinner_text);
        postType.setAdapter(adapter);
        postType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PostType postType = (PostType) parent.getSelectedItem();
                postTypeId = String.valueOf(postType.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void callTypeFace() {
        createPostTxt.setTypeface(FontTypeFace.fontBold(activity));
        journeyTxt.setTypeface(FontTypeFace.fontMedium(activity));
        profilerName.setTypeface(FontTypeFace.fontSemiBold(activity));
        milestonetext.setTypeface(FontTypeFace.fontMedium(activity));
    }

    private void init() {

        postDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int lineCount = postDesc.getLineCount();
                if (lineCount >= 7) {
                    text_fullscreen.setVisibility(View.VISIBLE);
                } else {
                    text_fullscreen.setVisibility(View.GONE);
                    text_fullscreen_exit.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int lineCount = postDesc.getLineCount();
                if (lineCount >= 7) {
                    text_fullscreen.setVisibility(View.VISIBLE);
                } else {
                    text_fullscreen.setVisibility(View.GONE);
                    text_fullscreen_exit.setVisibility(View.GONE);
                }

            }
        });


        text_fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDesc.setMaxLines(Integer.MAX_VALUE);
                text_fullscreen.setVisibility(View.GONE);
                text_fullscreen_exit.setVisibility(View.VISIBLE);
            }
        });

        text_fullscreen_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_fullscreen.setVisibility(View.VISIBLE);
                text_fullscreen_exit.setVisibility(View.GONE);
                postDesc.setMaxLines(6);
            }
        });


        imagePostAdapter = new ImagePostAdapter(activity);
        imagePostAdapter.setOnPassDataListener(this);
        postImagesRecycler.setAdapter(imagePostAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        postImagesRecycler.setLayoutManager(linearLayoutManager);
        // postImagesRecycler.setVisibility(View.VISIBLE);


        journeyAdapter = new JourneyAdapter(activity);
        journeyAdapter.setOnPassDataListener(this);
        journeyRecycler.setAdapter(journeyAdapter);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        journeyRecycler.setLayoutManager(linearLayoutManager1);

        if (Preference.getInstance(this).getStr(Constants.UserProfileImage).length() != 0) {
            Glide.with(this)
                    .load(Preference.getInstance(this).getStr(Constants.UserImagePath) + Preference.getInstance(this).getStr(Constants.UserProfileImage) + "?Yh-Access-Key=" + Preference.getInstance(this).getStr(Constants.AccessKey))
                    .apply(AppUtils.getRequestOption())
                    .listener(AppUtils.requestListener)
                    .into(profileImg);


            profileImg.setVisibility(View.VISIBLE);
            connectionUserCodeProfile.setVisibility(View.GONE);

            Glide.with(this)
                    .load(Preference.getInstance(this).getStr(Constants.UserImagePath) + Preference.getInstance(this).getStr(Constants.UserProfileImage) + "?Yh-Access-Key=" + Preference.getInstance(this).getStr(Constants.AccessKey))
                    .apply(AppUtils.getRequestOption())
                    .listener(AppUtils.requestListener)
                    .into(profileImg);
        } else {

            profileImg.setVisibility(View.GONE);
            connectionUserCodeProfile.setVisibility(View.VISIBLE);

            connectionUserCodeProfile.setText(Preference.getInstance(this).getStr(Constants.UserNameCode));
        }

        profilerName.setText(Preference.getInstance(this).getStr(Constants.UserName));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.stay, R.anim.activity_slide_down);
    }

    @OnClick({R.id.back, R.id.select_tag, R.id.select_visual_journey, R.id.camera, R.id.cmt_post_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.select_tag:
                tagsJourneyDialog = new TagsJourneyDialog(activity);
                tagsJourneyDialog.setPassListItems(this);
                tagsJourneyDialog.newTagsInstance(postAddMaster.getPostAddMasterData().getTags(), selectedTags);
                tagsJourneyDialog.show();
                break;
            case R.id.select_visual_journey:
                tagsJourneyDialog = new TagsJourneyDialog(activity);
                tagsJourneyDialog.setPassListItems(this);
                tagsJourneyDialog.newJourneyListInstance(postAddMaster.getPostAddMasterData().getJourneyLists(), selectedJourneyLists);
                tagsJourneyDialog.show();
                //  getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                Window window = tagsJourneyDialog.getWindow();
//                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                break;
            case R.id.camera:
                uploadFileDialog.show();
                break;
            case R.id.cmt_post_btn:
                validate();
                break;

        }
    }

    private void validate() {
        if (postDesc.getText().toString().isEmpty()) {
            MyToast.normalMessage("Enter the Post Text", activity);
        } else {
            call_new_post_api();
        }
    }

    private void call_new_post_api() {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            if (owntagid.length()!=0&&tag.length()!=0)
            {
                tagid=owntagid+ ","+tag;
                owntagname=owntagsname+","+tags;
                selectTag.setText(owntagname);
            }else if (owntagid.length()==0&&tag.length()!=0)
            {
                tagid=tag;
                owntagname=tags;
                selectTag.setText(owntagname);
            }else if (owntagid.length()!=0&&tag.length()==0)
            {
                tagid=owntagid;
                owntagname=owntagsname;
                selectTag.setText(owntagname);
            }else {
                tagid="";

            }
            post_title = postDesc.getText().toString();
            Loader.showLoad(activity, true);
            Call<PostDashboardListResponse> call = ApiClient.getApiInterface().getAddPost(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                    Constants.getToken(activity),
                    post_title,
                    galleryCodes,
                    tagid,
                    prime_tags,
                    group_code,
                    postTypeId,
                    visualJourney);

            call.enqueue(new Callback<PostDashboardListResponse>() {
                @Override
                public void onResponse(@NonNull Call<PostDashboardListResponse> call, @NonNull Response<PostDashboardListResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                postAddListResponse = response.body();
                                MyToast.normalMessage("Posted Successfully", activity);
                                InterfaceClass.getInstance().changeState(postAddListResponse);
                                finish();
                            } else {
                                MyToast.normalMessage(response.body().getMessage(), activity);
                            }
                        }
                    } else {
                        Log.d(Constants.failureResponse + " AddPost", response.toString());
                    }
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<PostDashboardListResponse> call, @NonNull Throwable t) {
                    Log.d(Constants.failureResponse + " AddPost", t.toString());
                    Loader.showLoad(activity, false);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.CAMERA_REQUEST:
                    //for api
                    File file = new File(getFilesDir(), "newImage.jpg");
                    //for load recycler
                    Bitmap cameraPhoto = BitmapFactory.decodeFile(file.getPath());
                    Log.d("", "FileUpload : " + file.getName());
                    call_Api(file, cameraPhoto);
                    break;
                case Constants.GALLERY_REQUEST:

                    ClipData clipData = data.getClipData();

                    if (clipData != null) {
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri imageUri = clipData.getItemAt(i).getUri();
                            // your code for multiple image selection
                            Bitmap galleryPhoto = BitmapFactory.decodeFile(getPath(imageUri));
                            //for api
                            File file1 = new File(getPath(imageUri));
                            Log.d("", "FileUpload : " + file1.getName());
                            call_Api(file1, galleryPhoto);
                        }
                    } else {

                        Uri url = data.getData();
                        // selectedImagePath = getPath(url);
                        Bitmap galleryPhoto = BitmapFactory.decodeFile(getPath(url));
                        //for api
                        File file1 = new File(getPath(url));
                        Log.d("", "FileUpload : " + file1.getName());
                        call_Api(file1, galleryPhoto);
                        System.out.println("////////=="+ url);
                        //  Bitmap galleryPhoto = BitmapFactory.decodeFile(getPath(Uri.parse(selectedImagePath)));
                    }

                    break;
            }
        }
    }

    private void call_Api(File file, Bitmap imageBitMap) {

        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            final MultipartBody.Part body = MultipartBody.Part.createFormData("userfile", file.getName(), requestFile);

            Call<PostGalleryUpload> call = ApiClient.getApiInterface().getPostGalleryUpload(Constants.getApiKey(activity),
                    Constants.getAccessKey(activity),
                    Constants.getToken(activity),
                    body);

            call.enqueue(new Callback<PostGalleryUpload>() {
                @Override
                public void onResponse(@NonNull Call<PostGalleryUpload> call, @NonNull Response<PostGalleryUpload> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                uploadFileDialog.closeDialog();
                                PostImageModel postImageModel = new PostImageModel();
                                postImageModel.setGalleryCode(String.valueOf(response.body().getPostGalleryData().getGalleryCode()));
                                postImageModel.setBitmap(imageBitMap);
                                update_image_adapter(postImageModel);
                            } else {
                                MyToast.normalMessage(response.body().getMessage(), activity);
                            }
                        }
                    } else {
                        Log.d(Constants.failureResponse + " FileUpload", response.toString());
                    }
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<PostGalleryUpload> call, @NonNull Throwable t) {
                    Log.d(Constants.failureResponse + " FileUpload", t.toString());
                    Loader.showLoad(activity, false);
                    MyToast.errorMessage(t.toString(), activity);
                }
            });

        }

    }

    private void update_image_adapter(PostImageModel postImageModel) {
        if (postImageModel.equals(""))
        {
            postImagesRecycler.setVisibility(View.GONE);
        }else {
            //  postDesc.setLines(2);
            postImagesRecycler.setVisibility(View.VISIBLE);
        }
        postImageModels.add(postImageModel);
        imagePostAdapter.addAll(postImageModels);
        if (galleryCodes.isEmpty()) {
            galleryCodes = postImageModel.getGalleryCode();
        } else {
            galleryCodes = galleryCodes.concat("," + postImageModel.getGalleryCode());
        }
    }

    @Override
    public void onPassData(PostImageModel postImageModel) {
        if (!galleryCodes.isEmpty()) {
            galleryCodeLength = galleryCodes.length();
        }
        for (Iterator<PostImageModel> iterator = postImageModels.iterator(); iterator.hasNext(); ) {
            PostImageModel fruit = iterator.next();
            if (postImageModel.getGalleryCode().equals(fruit.getGalleryCode())) {
                iterator.remove();
                if (!galleryCodes.isEmpty()) {
                    if (galleryCodes.contains(",")) {
                        galleryCodes = galleryCodes.replace(postImageModel.getGalleryCode() + ",", "");
                        if (galleryCodes.length() == galleryCodeLength) {
                            galleryCodes = galleryCodes.replace("," + postImageModel.getGalleryCode(), "");
                        }
                    } else {
                        galleryCodes = galleryCodes.replace(postImageModel.getGalleryCode(), "");
                    }
                }
                if (galleryCodes.length() <= 0) {
                    galleryCodes = "";
                }
                break;
            }
        }
        imagePostAdapter.addAll(postImageModels);
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void TagsPassData(List<Tag> tagsList,String owntag) {

        if (owntag.length()!=0)
        {
            callowntag(owntag);
        }
        this.selectedTags = tagsList;

        for (int i = 0; i < tagsList.size(); i++) {
            if (tags.equals("")) {
                tags = tagsList.get(i).getTgName();
                tag = tagsList.get(i).getTgTagId();
            } else {
                tags = tags + "," + tagsList.get(i).getTgName();
                tag = tag + "," + tagsList.get(i).getTgTagId();
            }
        }
        //  selectTag.setText(tags);
        if (owntag.length()!=0&&tag.length()!=0)
        {
            owntagname="#"+owntag+","+tags;
            selectTag.setText(owntagname);
        }else if (owntag.length()==0&&tag.length()!=0)
        {
            owntagname=tags;
            selectTag.setText(owntagname);
        }else if (owntag.length()!=0&&tag.length()==0)
        {
            owntagname="#"+owntag;
            selectTag.setText(owntagname);
        }
    }


    private void callowntag(String owntag) {
        Loader.showLoad(activity,true);
        Call<OwnTag> call=ApiClient.getApiInterface().NewTag(Constants.getApiKey(activity),
                Constants.getAccessKey(activity),
                Constants.getToken(activity),owntag);
        call.enqueue(new Callback<OwnTag>() {
            @Override
            public void onResponse(Call<OwnTag> call, Response<OwnTag> response) {

                owntagid= String.valueOf(response.body().getData().getTags().getTgTagId());
                owntagsname= response.body().getData().getTags().getTgName();
                Log.e("jjsnv",response.body().getData().getTags().getTgName());
                selecttag();
                Loader.showLoad(activity,false);
            }



            @Override
            public void onFailure(Call<OwnTag> call, Throwable t) {
                MyToast.errorMessage("Something Went Wrong",activity);
            }
        });
    }

    private void selecttag() {
    }

    @Override
    public void TagsItemPassData(List<TagsItem> tags) {

    }

    @Override
    public void JourneyPassData(List<JourneyList> journeyLists) {
        this.selectedJourneyLists = journeyLists;
        String tags = "";
        for (int i = 0; i < journeyLists.size(); i++) {
            if (tags.equals("")) {
                tags = journeyLists.get(i).getJumTitle();
                visualJourney = journeyLists.get(i).getJumJourneyId();
            } else {
                tags = tags + "," + journeyLists.get(i).getJumTitle();
                visualJourney = visualJourney + "," + journeyLists.get(i).getJumJourneyId();
            }
        }
        selectVisualJourney.setText(tags);
    }

    @Override
    public void onPassData(PrimeTag primeTag) {
        int num;
        boolean itemTheir = false;
        if (selectedPrimeTags.size() == 0) {
            selectedPrimeTags.add(primeTag);
        } else {
            num = selectedPrimeTags.size();
            for (int i = 0; i < num; i++) {
                if (selectedPrimeTags.get(i).getTgTagId().equals(primeTag.getTgTagId())) {
                    selectedPrimeTags.remove(i);
                    itemTheir = false;
                    break;
                } else {
                    itemTheir = true;
                }
            }
            if (itemTheir) {
                selectedPrimeTags.add(primeTag);
            }
        }
        setPrimeTags(selectedPrimeTags);
    }

    private void setPrimeTags(List<PrimeTag> primeTags) {
        String tags = "";
        for (int i = 0; i < primeTags.size(); i++) {
            if (tags.equals("")) {
                tags = primeTags.get(i).getTgName();
                prime_tags = primeTags.get(i).getTgTagId();
            } else {
                tags = tags + "," + primeTags.get(i).getTgName();
                prime_tags = prime_tags + "," + primeTags.get(i).getTgTagId();
            }
        }
    }

}
