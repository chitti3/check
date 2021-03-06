package com.example.youthhub.resModel.profile.profileinfo;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ProfileInfoResp implements Parcelable {

	@SerializedName("data")
	private Data data;

	@SerializedName("status")
	private int status;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ProfileInfoResp{" +
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(this.data, flags);
		dest.writeInt(this.status);
	}

	public ProfileInfoResp() {
	}

	protected ProfileInfoResp(Parcel in) {
		this.data = in.readParcelable(Data.class.getClassLoader());
		this.status = in.readInt();
	}

	public static final Parcelable.Creator<ProfileInfoResp> CREATOR = new Parcelable.Creator<ProfileInfoResp>() {
		@Override
		public ProfileInfoResp createFromParcel(Parcel source) {
			return new ProfileInfoResp(source);
		}

		@Override
		public ProfileInfoResp[] newArray(int size) {
			return new ProfileInfoResp[size];
		}
	};
}