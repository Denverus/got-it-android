package com.jzson.gotit.client.activities;

import java.util.Collection;
import java.util.concurrent.Callable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jzson.gotit.client.CallableTask;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.TaskCallback;
import com.jzson.gotit.client.Video;
import com.jzson.gotit.client.VideoSvc;
import com.jzson.gotit.client.VideoSvcApi;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 
 * This application uses ButterKnife. AndroidStudio has better support for
 * ButterKnife than Eclipse, but Eclipse was used for consistency with the other
 * courses in the series. If you have trouble getting the login button to work,
 * please follow these directions to enable annotation processing for this
 * Eclipse project:
 * 
 * http://jakewharton.github.io/butterknife/ide-eclipse.html
 * 
 */
public class LoginScreenActivity extends Activity {

	@InjectView(R.id.userName)
	protected EditText userName_;

	@InjectView(R.id.password)
	protected EditText password_;

	@InjectView(R.id.server)
	protected EditText server_;

	@InjectView(R.id.loginButton)
	protected Button loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);

		ButterKnife.inject(this);
	}

	@OnClick(R.id.loginButton)
	public void login() {
		loginButton.setEnabled(false);

		final String user = userName_.getText().toString();
		String pass = password_.getText().toString();
		String server = server_.getText().toString();

		final VideoSvcApi svc = VideoSvc.init(server, user, pass);

		CallableTask.invoke(new Callable<Collection<Video>>() {

			@Override
			public Collection<Video> call() throws Exception {
				return svc.getVideoList();
			}
		}, new TaskCallback<Collection<Video>>() {

			@Override
			public void success(Collection<Video> result) {
				// OAuth 2.0 grant was successful and we
				// can talk to the server, open up the video listing
				Intent intent = new Intent(
						LoginScreenActivity.this,
						VideoListActivity.class);
				intent.putExtra("username", user);

				startActivity(intent);
				loginButton.setEnabled(true);
			}

			@Override
			public void error(Exception e) {
				Log.e(LoginScreenActivity.class.getName(), "Error logging in via OAuth.", e);

				Toast.makeText(
						LoginScreenActivity.this,
						"Login failed, check your Internet connection and credentials.",
						Toast.LENGTH_SHORT).show();
				loginButton.setEnabled(true);
			}
		});
	}

}
