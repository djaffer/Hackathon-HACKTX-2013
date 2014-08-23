package com.hacktx2013.utcafeteriamenu;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurant implements Parcelable{
	
	private int _id;
	private String _name;
	
	public Restaurant(int id, String name) {
		_id = id;
		_name = name;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public String getName()
	{
		return _name;
	}
	
	/**
	 * In order to pass objects between activities, the objects have to be Parcelable
	 * Parceling part
	 */
	public Restaurant(Parcel in) {
		this._id = in.readInt();
		this._name = in.readString();
	}
	
	@Override
	public int describeContents() {
		return this.hashCode();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(_id);
		dest.writeString(_name);
	}
	
	public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {

		@Override
		public Restaurant createFromParcel(Parcel source) {
			return new Restaurant(source);
		}

		@Override
		public Restaurant[] newArray(int size) {
			return new Restaurant[size];
		}
	};
}
