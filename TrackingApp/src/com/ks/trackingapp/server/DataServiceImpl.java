package com.ks.trackingapp.server;

import java.util.ArrayList;
import java.util.List;

import com.ks.trackingapp.client.DataService;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.ItemComment;
import com.ks.trackingapp.shared.model.UserInfo;

@SuppressWarnings("serial")
public class DataServiceImpl extends DAO implements
		DataService {




	@Override
	public UserInfo userRegister(UserInfo userInfo) {
		return super.registerUser(userInfo);
	}

	@Override
	public UserInfo userLogin(String userName, String password) {
		return super.userLogin(userName, password);
	}

	@Override
	public ItemApp appAddNew(ItemApp itemApp) {
		return super.appItemAddNew(itemApp);
	}

	@Override
	public ArrayList<ItemApp> appGetAllItem(Long userId) {
		return super.appGetAllItem(userId);
	}

	@Override
	public ArrayList<ItemComment> commentGetFromAppId(Long userId,Long appId) {
		return super.commentGetFromAppId(userId,appId);
	}

	@Override
	public ArrayList<ItemComment> commentFilterByTag(String language,Long userId,Long appId,String tag,String platform) {
		return super.commentFilterByTag(language,userId,appId,tag,platform);
	}

	@Override
	public void commentGetAppComment(Long userId,ItemApp itemApp) {
		super.getCommentApp(userId,itemApp);
	}

	@Override
	public ItemApp appGetFromId(Long appId) {
		return super.appGetFromAppId(appId);
	}

	@Override
	public ArrayList<ItemComment> getCommentAppWithTag(Long userId, Long appId,
			String language,String tag) {
		return super.getCommentAppByTag(userId, appId, language,tag);
	}

	@Override
	public ItemApp appUpdatePlatform(ItemApp itemApp) {
		return super.appItemUpdate(itemApp);
	}

	@Override
	public void appDeleteItem(Long appId) {
		super.appItemDelete(appId);
	}

	@Override
	public void commentDeleteAll() {
		super.commentDeleteAll();
	}

	@Override
	public void appItemDeleteAll() {
		super.appItemDeleteAll();
	}

	@Override
	public void commentDeleteByAppId(Long appId) {
		super.commentDeleteByAppId(appId);
	}

	@Override
	public List<ItemComment> getCommentByLanguage(String language) {
		return super.getCommentByLangauge(language);
	}

	@Override
	public List<ItemComment> getCommentAppNewsts(Long userId, ItemApp app) {
		return super.getCommentNewsts(userId, app);
	}

}
