package org.coursera.capstone.gotit.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.coursera.capstone.gotit.client.AppApplication;
import org.coursera.capstone.gotit.client.CallableTask;
import org.coursera.capstone.gotit.client.NavUtils;
import org.coursera.capstone.gotit.client.R;
import org.coursera.capstone.gotit.client.TaskCallback;
import org.coursera.capstone.gotit.client.model.Person;
import org.coursera.capstone.gotit.client.provider.ProviderFactory;
import org.coursera.capstone.gotit.client.provider.ServiceApi;
import org.coursera.capstone.gotit.client.provider.ServiceCall;

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

	public static final String PREFS_NAME = "PrefsFile";

    //public static final String DEFAULT_SERVER = "https://10.2.4.52:8443";
    public static final String DEFAULT_SERVER = "https://10.0.2.2:8443";
    public static final String DEFAULT_USER = "user1";
    public static final String DEFAULT_PASSWORD = "pass";

	@InjectView(R.id.userName)
	protected EditText userName_;

	@InjectView(R.id.password)
	protected EditText password_;

	@InjectView(R.id.server)
	protected EditText server_;

	@InjectView(R.id.loginButton)
	protected Button loginButton;

    @InjectView(R.id.useLocalDataCheckBox)
    protected CheckBox useLocalDataCheckBox;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);

		ButterKnife.inject(this);

		useLocalDataCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                server_.setEnabled(!isChecked);
            }
        });

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean autoConnect = settings.getBoolean("autoConnect", false);
        boolean useLocalStorage = settings.getBoolean("useLocalStorage", false);
        String server = settings.getString("server", DEFAULT_SERVER);
        String user = settings.getString("user", DEFAULT_USER);
        String password = settings.getString("password", DEFAULT_PASSWORD);

        useLocalDataCheckBox.setChecked(useLocalStorage);
        server_.setText(server);
        userName_.setText(user);
        password_.setText(password);

        if (autoConnect) {
            login();
        }
	}

    @OnClick(R.id.registerTextView)
    public void register() {
        Intent registerIntent = new Intent(this, RegistrationActivity.class);
        startActivity(registerIntent);
    }

	@OnClick(R.id.loginButton)
	public void login() {
		loginButton.setEnabled(false);

        final String user = userName_.getText().toString();
		String pass = password_.getText().toString();
		String server = server_.getText().toString();

        // Prepare settings to save
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("autoConnect", true);
        editor.putBoolean("useLocalStorage", useLocalDataCheckBox.isChecked());
        editor.putString("server", server);
        editor.putString("user", user);
        editor.putString("password", pass);

        // Commit the edits!
        editor.commit();

        if (useLocalDataCheckBox.isChecked()) {
            ProviderFactory.init(ProviderFactory.INTERNAL_PROVIDER, server, user, pass);
        } else {
            ProviderFactory.init(ProviderFactory.SPRING_PROVIDER, server, user, pass);
        }

		CallableTask.invoke(this, new ServiceCall<Person>() {

			@Override
			public Person call(ServiceApi svc) throws Exception {
				return svc.getPersonByUsername(user);
			}
		}, new TaskCallback<Person>() {

			@Override
			public void success(Person result) {
				// OAuth 2.0 grant was successful and we
				// can talk to the server, open up the video listing
				AppApplication.getContext().setCurrentUser(result);
				NavUtils.showMainActivity(LoginScreenActivity.this);
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
