package com.hacktx2013.utcafeteriamenu;

import android.os.Parcel;
import android.os.Parcelable;

public class RestaurantDetail implements Parcelable{
	
	private int _id;
	private String _name;
	private String _building;
	private String _hours;
	private String _menu;
	
	public RestaurantDetail(int id, String name, String building, String hours, String menu) {
		_id = id;
		_name = name;
		_building = building;
		_hours = hours;
		_menu = menu;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String getBuilding()
	{
		return _building.toUpperCase();
	}
	
	public String getHours()
	{
		return _hours;
	}
	
	public String getMenu()
	{
		return _menu;
	}
	
	/**
	 * In order to pass objects between activities, the objects have to be Parcelable
	 * Parceling part
	 */
	public RestaurantDetail(Parcel in) {
		this._id = in.readInt();
		this._name = in.readString();
		this._building = in.readString();
		this._hours = in.readString();
		this._menu = in.readString();
	}
	
	@Override
	public int describeContents() {
		return this.hashCode();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(_id);
		dest.writeString(_name);
		dest.writeString(_building);
		dest.writeString(_hours);
		dest.writeString(_menu);
	}
	
	public static final Parcelable.Creator<RestaurantDetail> CREATOR = new Parcelable.Creator<RestaurantDetail>() {

		@Override
		public RestaurantDetail createFromParcel(Parcel source) {
			return new RestaurantDetail(source);
		}

		@Override
		public RestaurantDetail[] newArray(int size) {
			return new RestaurantDetail[size];
		}
	};
}
