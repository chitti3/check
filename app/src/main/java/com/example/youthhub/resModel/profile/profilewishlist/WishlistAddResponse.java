package com.example.youthhub.resModel.profile.profilewishlist;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class WishlistAddResponse implements Parcelable {

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
			"WishlistAddResponse{" + 
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

	public WishlistAddResponse() {
	}

	protected WishlistAddResponse(Parcel in) {
		this.data = in.readParcelable(Data.class.getClassLoader());
		this.status = in.readInt();
	}

	public static final Parcelable.Creator<WishlistAddResponse> CREATOR = new Parcelable.Creator<WishlistAddResponse>() {
		@Override
		public WishlistAddResponse createFromParcel(Parcel source) {
			return new WishlistAddResponse(source);
		}

		@Override
		public WishlistAddResponse[] newArray(int size) {
			return new WishlistAddResponse[size];
		}
	};
}