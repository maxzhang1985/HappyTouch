package com.kuailedian.components.AutoUpdate;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.kuailedian.happytouch.R;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateChecker extends Fragment {

	//private static final String NOTIFICATION_ICON_RES_ID_KEY = "resId";
	private static final String NOTICE_TYPE_KEY = "type";
    private static final String APP_UPDATE_SERVER_URL = "app_update_server_url";
	//private static final String SUCCESSFUL_CHECKS_REQUIRED_KEY = "nChecks";
	private static final int NOTICE_NOTIFICATION = 2;
	private static final int NOTICE_DIALOG = 1;
	private static final String TAG = "UpdateChecker";

	private FragmentActivity mContext;
	private int mTypeOfNotice;

	/**
	 * Show a Dialog if an update is available for download. Callable in a
	 * FragmentActivity. Number of checks after the dialog will be shown:
	 * default, 5
	 * 
	 * @param fragmentActivity
	 *            Required.
	 */
	public static void checkForDialog(FragmentActivity fragmentActivity,String url) {
		FragmentTransaction content = fragmentActivity.getSupportFragmentManager().beginTransaction();
		UpdateChecker updateChecker = new UpdateChecker();
		Bundle args = new Bundle();
		args.putInt(NOTICE_TYPE_KEY, NOTICE_DIALOG);
        args.putString(APP_UPDATE_SERVER_URL,url);
		//args.putInt(SUCCESSFUL_CHECKS_REQUIRED_KEY, 5);
		updateChecker.setArguments(args);
		content.add(updateChecker, null).commit();
	}

	

	/**
	 * Show a Notification if an update is available for download. Callable in a
	 * FragmentActivity Specify the number of checks after the notification will
	 * be shown.
	 * 
	 * @param fragmentActivity
	 *            Required.
	 */
	public static void checkForNotification(FragmentActivity fragmentActivity,String url) {
		FragmentTransaction content = fragmentActivity.getSupportFragmentManager().beginTransaction();
		UpdateChecker updateChecker = new UpdateChecker();
		Bundle args = new Bundle();
		args.putInt(NOTICE_TYPE_KEY, NOTICE_NOTIFICATION);
        args.putString(APP_UPDATE_SERVER_URL,url);
		//args.putInt(NOTIFICATION_ICON_RES_ID_KEY, notificationIconResId);
		//args.putInt(SUCCESSFUL_CHECKS_REQUIRED_KEY, 5);
		updateChecker.setArguments(args);
		content.add(updateChecker, null).commit();
	}

	
	/**
	 * This class is a Fragment. Check for the method you have chosen.
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = (FragmentActivity) activity;
		Bundle args = getArguments();
		mTypeOfNotice = args.getInt(NOTICE_TYPE_KEY);
        String url = args.getString(APP_UPDATE_SERVER_URL);
		//mSuccessfulChecksRequired = args.getInt(SUCCESSFUL_CHECKS_REQUIRED_KEY);
		//mNotificationIconResId = args.getInt(NOTIFICATION_ICON_RES_ID_KEY);
		checkForUpdates(url);
	}

	/**
	 * Heart of the library. Check if an update is available for download
	 * parsing the desktop Play Store page of the app
	 */
	private void checkForUpdates(final String url) {

		HttpUtilsAsync.setTimeout(8000);
		HttpUtilsAsync.get(url, new RequestParams(), new TextHttpResponseHandler("UTF-8") {

			@Override
			public void onSuccess(int i, Header[] headers, String responseString) {
				Log.v("getdatalogonSuccess", responseString);

				parseJson(responseString);
			}

			@Override
			public void onFailure(int i, Header[] headers, String responseString, Throwable throwable) {
				Log.v("getdatalog", "failure");

			}


		});



	}


	private void parseJson(String json) {

		try {
			
			JSONObject obj = new JSONObject(json);
			String updateMessage = obj.getString(Constants.APK_UPDATE_CONTENT);
			String apkUrl = obj.getString(Constants.APK_DOWNLOAD_URL);
			int apkCode = obj.getInt(Constants.APK_VERSION_CODE);

			int versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
			Log.v("versioncode",String.valueOf(versionCode));
			if (apkCode > versionCode) {
				if (mTypeOfNotice == NOTICE_NOTIFICATION) {
					showNotification(updateMessage,apkUrl);
				} else if (mTypeOfNotice == NOTICE_DIALOG) {
					showDialog(updateMessage,apkUrl);
				}
			} else {
				//Toast.makeText(mContext, mContext.getString(R.string.app_no_new_update), Toast.LENGTH_SHORT).show();
			}

		} catch (PackageManager.NameNotFoundException ignored) {
		} catch (JSONException e) {
			Log.e(TAG, "parse json error", e);
		}
	}

	/**
	 * Show dialog
	 * 
	 */
	public void showDialog(String content,String apkUrl) {
        UpdateDialog d = new UpdateDialog();
		Bundle args = new Bundle();
		args.putString(Constants.APK_UPDATE_CONTENT, content);
		args.putString(Constants.APK_DOWNLOAD_URL, apkUrl);
		d.setArguments(args);
		d.show(mContext.getSupportFragmentManager(), null);
	}

	/**
	 * Show Notification
	 * 
	 */
	public void showNotification(String content,String apkUrl) {
		Notification noti;
		Intent myIntent = new Intent(mContext, DownloadService.class);
		myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		myIntent.putExtra(Constants.APK_DOWNLOAD_URL, apkUrl);
		PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		int smallIcon = mContext.getApplicationInfo().icon;
		noti = new NotificationCompat.Builder(mContext).setTicker(getString(R.string.newUpdateAvailable))
				.setContentTitle(getString(R.string.newUpdateAvailable)).setContentText(content).setSmallIcon(smallIcon)
				.setContentIntent(pendingIntent).build();

		noti.flags = Notification.FLAG_AUTO_CANCEL;
		NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, noti);
	}

	

	/**
	 * Check if a network available
	 */
	public static boolean isNetworkAvailable(Context context) {
		boolean connected = false;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if (ni != null) {
				connected = ni.isConnected();
			}
		}
		return connected;
	}

	
}
