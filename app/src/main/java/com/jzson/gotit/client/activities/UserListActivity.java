package com.jzson.gotit.client.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jzson.gotit.client.CallableTask;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.TaskCallback;
import com.jzson.gotit.client.VideoSvc;
import com.jzson.gotit.client.VideoSvcApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UserListActivity extends Activity {

    @InjectView(R.id.listView)
    protected ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ButterKnife.inject(this);

        Long videoId  = getIntent().getLongExtra("videoId", -1L);
        loadUserList(videoId);
    }

    private void loadUserList(final long videoId) {
        final VideoSvcApi svc = VideoSvc.getOrShowLogin(this);

        if (svc != null) {
            CallableTask.invoke(new Callable<Collection<String>>() {

                @Override
                public Collection<String> call() throws Exception {
                    return svc.getUsersWhoLikedVideo(videoId);
                }
            }, new TaskCallback<Collection<String>>() {

                @Override
                public void success(Collection<String> result) {
                    if (result.isEmpty()) {
                        Toast.makeText(
                                UserListActivity.this,
                                "Nobody like this video.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        userListView.setAdapter(new ArrayAdapter<String>(
                                UserListActivity.this,
                                android.R.layout.simple_list_item_1,
                                new ArrayList<>(result)));
                    }
                }

                @Override
                public void error(Exception e) {
                    Toast.makeText(
                            UserListActivity.this,
                            "Unable to get information for this video.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
