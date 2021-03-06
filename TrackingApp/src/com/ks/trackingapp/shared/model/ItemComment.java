package com.ks.trackingapp.shared.model;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.ks.trackingapp.shared.interfacemodel.IBasic;

@SuppressWarnings("serial")
@Entity
public class ItemComment implements IBasic {

	private @Id Long id;
	private String title;
	@Index
	private String comment;
	@Index
	private String platform;
	@Index
	private String appname;
	private @Index Long appId;
	private @Index Long userId;
	private @Index Date date;
	private @Index int rating;
	private @Index String idComment;
	private @Index String language;
	@Index
	private String userName = null;
	private String avatar = "";
	
	

	public ItemComment() {
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setIdComment(String idComment) {
		this.idComment = idComment;
	}

	public String getIdComment() {
		return idComment;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public Long getId() {
		return id;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getAppId() {
		return appId;
	}

	public Date getDate() {
		return date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

}
