package com.ks.trackingapp.client.activity.newapp;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.ks.trackingapp.client.RPCCall;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.allapp.AllAppPlace;
import com.ks.trackingapp.client.activity.appcomment.AppCommentPlace;
import com.ks.trackingapp.client.activity.basic.BasicActivity;
import com.ks.trackingapp.client.activity.home.HomePlace;
import com.ks.trackingapp.client.activity.homecomment.HomeCommentPlace;
import com.ks.trackingapp.client.activity.login.LoginPlace;
import com.ks.trackingapp.client.activity.pulltoload.PullToLoadPlace;
import com.ks.trackingapp.client.manager.TrackingManager;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.client.util.Toaster;
import com.ks.trackingapp.client.view.dialog.DialogOpenCommentApp;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.UserInfo;

public class NewAppActivity extends BasicActivity{

	private NewAppView view;
	private DialogOpenCommentApp dialogComment = new DialogOpenCommentApp();
	private ItemApp currentItemApp = null;//for go to appcomment app
	private ItemApp itemAppEdit = null;
	private boolean isFromAll;
	private boolean isEdit;
	private Long appId = -1L;
	public NewAppActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
		isFromAll = ((NewAppPlace)place).isFromAll();
		isEdit = ((NewAppPlace)place).isEdit();
		appId = ((NewAppPlace)place).getAppId();
	}
	
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getNewAppView();
		super.start(panel, eventBus,view);
		panel.setWidget(view);
	}
	
	@Override
	protected void handleEvent() {
		super.handleEvent();
		view.getCheckBoxAndroid().setValue(true);
		view.getCheckBoxIOS().setValue(true);
		view.getTextboxPakageName().setVisible(true);
		view.getTextboxAppleId().setVisible(true);
		view.getTextboxAppname().setText("");
		view.getTextboxPakageName().setText("");
		view.getTextboxAppleId().setText("");
		if(appId != -1L){
			updatePlatformItemApp(appId);
		}
		//handle checkbox android
		addHandlerRegistration(view.getCheckBoxAndroid().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				boolean isAndroid = view.getCheckBoxAndroid().getValue();
				if(isAndroid){
					view.getTextboxPakageName().setVisible(true);
				}else{
					view.getTextboxPakageName().setVisible(false);
				}
			}
		}));
		
		//handle checkbox android
		addHandlerRegistration(view.getCheckBoxIOS().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
					
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				boolean isIOS = view.getCheckBoxIOS().getValue();
					if(isIOS){
						view.getTextboxAppleId().setVisible(true);
					}else{
						view.getTextboxAppleId().setVisible(false);
					}
			}
		}));
		
		//handle button check
		addHandlerRegistration(view.getButtonCheck().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				addNewApp();
			}
		}));
		
		//handle dialog comment
		dialogComment.getBtNo().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				dialogComment.hide();
			}
		});
		
		dialogComment.getBtYes().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				ClientUtils.log("AppId:" + currentItemApp.getId());
				goTo(new AppCommentPlace(currentItemApp.getId()));
				dialogComment.hide();
			}
		});
		
		view.getButtonCancel().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
//				goTo(new HomeCommentPlace());
				goTo(new PullToLoadPlace());
			}
		});
		
		view.getButtonOk().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				addNewApp();
			}
		});
		view.getButtonDeleteComment().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				// TODO Auto-generated method stub
				TrackingApp.dataService.commentDeleteAll(new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Toaster.showToast("Delete failed");
					}

					@Override
					public void onSuccess(Void result) {
						Toaster.showToast("Delete all comment success");
					}
				});
			}
		});
		
		view.getButtonDeleteApp().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				// TODO Auto-generated method stub
				TrackingApp.dataService.appItemDeleteAll(new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Toaster.showToast("Delete failed");
					}

					@Override
					public void onSuccess(Void result) {
						Toaster.showToast("Delete all app success");
					}
				});
			}
		});
//		view.getFormPanel().addSubmitHandler(new FormPanel.SubmitHandler() {
//			public void onSubmit(SubmitEvent event) {
//				if (!"".equalsIgnoreCase(view.getFileUpload().getFilename())) {
//					TrackingApp.getClientFactory().getLoadingDialog().show();
//					Toaster.showToast("Upload" + "tracking-dot-qt3men.appspot.com/" +"uploadfile");
//					view.getFormPanel().setAction("http://127.0.0.1:8888/" +"uploadfile?userId="+TrackingManager.newInstance().getCurrentUser().getId());
//				} else {
//					Toaster.showToast("Please choose file upload");
//				}
//			}
//		});
//		view.getFormPanel().addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
//			public void onSubmitComplete(SubmitCompleteEvent event) {
//				TrackingApp.getClientFactory().getLoadingDialog().hide();
//				goTo(new HomeCommentPlace());
//			}
//		});
		hideView();
	}
	
	private void hideView(){
		UserInfo currentUser = TrackingManager.newInstance().getCurrentUser();
		String userName = currentUser.getUserName();
		Toaster.showToast("CurrentUser:" + userName);
		if(currentUser.getUserName().equals("admin")){
			view.getFlowPanelUpload().setVisible(true);
			view.getFlowPanelDelete().setVisible(true);
		}else{
			view.getFlowPanelUpload().setVisible(false);
			view.getFlowPanelDelete().setVisible(false);
		}
	}
	
	private void updatePlatformItemApp(final Long appId){
		new RPCCall<ItemApp>() {

			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Get appitem failed");
			}

			@Override
			public void onSuccess(ItemApp itemApp) {
				if(itemApp != null){
					itemAppEdit = itemApp;
					view.getTextboxAppname().setText(itemApp.getAppName());
					if(itemApp.isAndroid()){
						view.getCheckBoxAndroid().setValue(true);
						view.getTextboxPakageName().setText(itemApp.getPackageName());
						view.getTextboxPakageName().setReadOnly(true);
					}
					if(itemApp.isIOS()){
						view.getCheckBoxIOS().setValue(true);
						view.getTextboxAppleId().setText(itemApp.getAppleId());
						view.getTextboxAppleId().setReadOnly(true);
					}
					if(isEdit){
						view.getTextboxPakageName().setReadOnly(false);
						view.getTextboxAppleId().setReadOnly(false);
					}
				}
			}

			@Override
			protected void callService(AsyncCallback<ItemApp> cb) {
				TrackingApp.dataService.appGetFromId(appId, cb);
			}
		}.retry(0);;
	}
	private void addNewApp(){
		String appName = view.getTextboxAppname().getText().toString().trim();
		String packageName = view.getTextboxPakageName().getText().toString().trim();
		String appleId = view.getTextboxAppleId().getText().toString().trim();
		boolean isAndroid = view.getCheckBoxAndroid().getValue();
		boolean isIOS = view.getCheckBoxIOS().getValue();
		if(!ClientUtils.validate(appName)) {
			Toaster.showToast("Please type your appname.");
			return;
		}
		if(isAndroid && !ClientUtils.validate(packageName)){
			Toaster.showToast("Please type package name");
			return;
		}
		if(isIOS && !ClientUtils.validate(appleId)) {
			Toaster.showToast("Please type apple id");
			return;
		}
		if(!isAndroid && !isIOS){
			Toaster.showToast("Please choose either Android or IOS.");
			return;
		}
		UserInfo currentUser = TrackingManager.newInstance().getCurrentUser();
		if(currentUser == null){
			Toaster.showToast("Please login to continue use app");
			goTo(new LoginPlace());
			return;
		}
		final ItemApp itemApp = new ItemApp(appName, currentUser.getId());
		if(isAndroid){
			itemApp.setAndroid(true);
			itemApp.setPackageName(packageName);
		}
		if(isIOS) {
			itemApp.setIOS(true);
			itemApp.setAppleId(appleId);
		}
		//handle for user want edit appitem
		if(itemAppEdit != null){
			if(itemAppEdit.isAndroid() == false && isAndroid == false){
				Toaster.showToast("Please check Android");
				return;
			}else{
				itemAppEdit.setAndroid(true);
				itemAppEdit.setPackageName(view.getTextboxPakageName().getText().toString());
			}
			if(itemAppEdit.isIOS() == false && isIOS == false){
				Toaster.showToast("Please check IOS");
				return;
			}else{
				itemAppEdit.setIOS(true);
				itemAppEdit.setAppleId(view.getTextboxAppleId().getText().toString());
			}
			updateItemApp(itemAppEdit);
			return;
		}
		TrackingApp.getClientFactory().getLoadingDialog().show();
		//add new appItem
		TrackingApp.dataService.appAddNew(itemApp,new AsyncCallback<ItemApp>() {

			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Add app failed,please check your network?");
			}

			@Override
			public void onSuccess(ItemApp itemResult) {
				if(itemResult.isSuccess()){
					Toaster.showToast("Add app success,get comment this app.");
					getCommentForApp(itemResult);
				}else{
					if(itemResult.getAddFailedForReason() == Config.APPITEM_APPNAME_EXITS){
						Toaster.showToast("Failed because your App name exits");
						view.getTextboxAppname().setText("");
					}else if(itemResult.getAddFailedForReason() == Config.APPITEM_PACKAGENAME_EXITS){
						Toaster.showToast("Failed because packagename exits");
						view.getTextboxPakageName().setText("");
					}else if(itemResult.getAddFailedForReason() == Config.APPITEM_APPLEID_EXITS){
						Toaster.showToast("Failed because appleid exits");
						view.getTextboxAppleId().setText("");
					}
				}
			}
		});
		
	}
	
	private void updateItemApp(ItemApp itemApp){
		TrackingApp.dataService.appUpdatePlatform(itemApp, new AsyncCallback<ItemApp>() {

			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Update ItemApp failed.");
			}

			@Override
			public void onSuccess(ItemApp result) {
				if(result.isSuccess()){
					getCommentForApp(itemAppEdit);
				}
			}
		});
	}
	private void getCommentForApp(final ItemApp itemApp){
		new RPCCall<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Get comment failed");
			}

			@Override
			public void onSuccess(Void result) {
				currentItemApp = itemApp;
				dialogComment.show();
			}

			@Override
			protected void callService(AsyncCallback<Void> cb) {
				TrackingApp.dataService.commentGetAppComment(TrackingManager.newInstance().getCurrentUser().getId(),itemApp,cb);
			}
		}.retry(0);
	}
	@Override
	protected void onBackPress() {
		super.onBackPress();
		if(isFromAll){
			goTo(new AllAppPlace());
			return;
		}
		if(isEdit) {
			goTo(new AllAppPlace());
			return;
		}
		goTo(new HomePlace());
	}

}
