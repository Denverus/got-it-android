package com.jzson.gotit.client.activities;

import java.util.Collection;
import java.util.concurrent.Callable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.jzson.gotit.client.CallableTask;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.TaskCallback;
import com.jzson.gotit.client.Video;
import com.jzson.gotit.client.VideoListAdapter;
import com.jzson.gotit.client.VideoSvc;
import com.jzson.gotit.client.VideoSvcApi;

public class VideoListActivity extends Activity {

	@InjectView(R.id.videoList)
	protected ListView videoList_;

	@InjectView(R.id.add_meta_data)
	protected FloatingActionButton fab;

	private VideoListAdapter adapter;

	private String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_list);

		username = getIntent().getStringExtra("username");

		ButterKnife.inject(this);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(VideoListActivity.this, AddMetaDataActivity.class);
				startActivityForResult(intent, AddMetaDataActivity.REQUEST_ADD);
			}
		});
		videoList_.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (adapter != null) {
					Video video = adapter.getItem(position);
					Intent intent = new Intent(VideoListActivity.this, UserListActivity.class);
					intent.putExtra("videoId", video.getId());
					startActivity(intent);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		refreshVideos();
	}

	public void refreshVideos() {
		final VideoSvcApi svc = VideoSvc.getOrShowLogin(this);

		if (svc != null) {
			CallableTask.invoke(new Callable<Collection<Video>>() {

				@Override
				public Collection<Video> call() throws Exception {
					return svc.getVideoList();
				}
			}, new TaskCallback<Collection<Video>>() {

				@Override
				public void success(Collection<Video> result) {
					Video[] values = result.toArray(new Video[result.size()]);
					adapter = new VideoListAdapter(VideoListActivity.this, values, username);
					videoList_.setAdapter(adapter);
				}

				@Override
				public void error(Exception e) {
					Toast.makeText(
							VideoListActivity.this,
							"Unable to fetch the video list, please login again.",
							Toast.LENGTH_SHORT).show();

					startActivity(new Intent(VideoListActivity.this,
							LoginScreenActivity.class));
				}
			});
		}
	}

	private void addVideoData(final Video video) {
		final VideoSvcApi svc = VideoSvc.getOrShowLogin(this);

		if (svc != null) {
			CallableTask.invoke(new Callable<Video>() {

				@Override
				public Video call() throws Exception {
					return svc.addVideo(video);
				}
			}, new TaskCallback<Video>() {

				@Override
				public void success(Video result) {
					Toast.makeText(
							VideoListActivity.this,
							"Video was successfully added.",
							Toast.LENGTH_SHORT).show();
					refreshVideos();
				}

				@Override
				public void error(Exception e) {
					Toast.makeText(
							VideoListActivity.this,
							"Unable to add video, please login again.",
							Toast.LENGTH_SHORT).show();

					startActivity(new Intent(VideoListActivity.this,
							LoginScreenActivity.class));
				}
			});
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AddMetaDataActivity.REQUEST_ADD) {
			if (resultCode == RESULT_OK) {
				String name = data.getStringExtra("name");
				String url =  data.getStringExtra("url");
				long duration = data.getLongExtra("duration", 0);
				Video video = new Video(name, url, duration, 0);
				addVideoData(video);
			}
		}
	}
}
