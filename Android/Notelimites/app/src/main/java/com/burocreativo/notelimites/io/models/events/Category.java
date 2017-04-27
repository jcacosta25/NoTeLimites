package com.burocreativo.notelimites.io.models.events;

import com.google.gson.annotations.SerializedName;

public class Category {

	@SerializedName("categoryName")
	private String categoryName;

	@SerializedName("categoryID")
	private int categoryID;

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setCategoryID(int categoryID){
		this.categoryID = categoryID;
	}

	public int getCategoryID(){
		return categoryID;
	}

	@Override
 	public String toString(){
		return 
			"Categories{" + 
			"categoryName = '" + categoryName + '\'' + 
			",categoryID = '" + categoryID + '\'' + 
			"}";
		}
}