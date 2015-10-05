package com.jzson.gotit.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * Created by dtrotckii on 9/1/2015.
 */
public class VideoListAdapter extends ArrayAdapter<Video> {
    private final Context context;
    private final Video[] values;
    private final String username;
    private boolean[] likes;

    public VideoListAdapter(Context context, Video[] values, String username) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.username = username;
        likes = new boolean[values.length];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.video_list_item, parent, false);
        TextView videoNameView = (TextView) rowView.findViewById(R.id.nameTextView);
        TextView videoUrlView = (TextView) rowView.findViewById(R.id.urlTextView);
        TextView likeCountView = (TextView) rowView.findViewById(R.id.likeCount);
        TextView durationTextView = (TextView) rowView.findViewById(R.id.durationTextView);
        ImageView likeView = (ImageView) rowView.findViewById(R.id.likeIcon);

        Video video = values[position];

        setLikeClickHandler(likeView, likeCountView, video, position);

        videoNameView.setText(video.getName());
        videoUrlView.setText("URL: "+video.getUrl());
        likeCountView.setText(video.getLikes()+"");
        durationTextView.setText("Duration: "+video.getDuration());

        updateLikeIcon(likeView, likeCountView, video, position);

        return rowView;
    }

    private void updateLikeIcon(final ImageView likeView, final TextView likeCountTextView, final Video video, final int position) {
        final VideoSvcApi svc = VideoSvc.getOrShowLogin(context);

        if (svc != null) {
            CallableTask.invoke(new Callable<Collection<String>>() {

                @Override
                public Collection<String> call() throws Exception {
                    return svc.getUsersWhoLikedVideo(video.getId());
                }
            }, new TaskCallback<Collection<String>>() {

                @Override
                public void success(Collection<String> result) {
                    updateLikeView(likeView, likeCountTextView, video.getId(), result.contains(username), position);
                }

                @Override
                public void error(Exception e) {
                }
            });
        }
    }

    private void updateLikeView(final ImageView likeView, TextView likeCountTextView, long videoId, boolean liked, final int position) {
        likes[position] = liked;
        if (liked) {
            likeView.setImageResource(R.drawable.full_heart);
        } else {
            likeView.setImageResource(R.drawable.empty_heart);
        }
        updateLikesCount(likeCountTextView, videoId);
    }

    private void setLikeClickHandler(final ImageView likeView, final TextView likeCountTextView, final Video video, final int position) {
        likeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!likes[position])
                    likeVideo(likeView, likeCountTextView, video.getId(), position);
                else
                    unlikeVideo(likeView, likeCountTextView, video.getId(), position);
            }
        });
    }

    private void likeVideo(final ImageView likeView, final TextView likeCountTextView, final long videoId, final int position) {
        final VideoSvcApi svc = VideoSvc.getOrShowLogin(context);

        if (svc != null) {
            CallableTask.invoke(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    return svc.likeVideo(videoId);
                }
            }, new TaskCallback<Void>() {

                @Override
                public void success(Void result) {
                    Toast.makeText(
                            context,
                            "Video liked.",
                            Toast.LENGTH_SHORT).show();
                    //((VideoListActivity)context).refreshVideos();
                    updateLikeView(likeView, likeCountTextView, videoId, true, position);
                }

                @Override
                public void error(Exception e) {
                    Toast.makeText(
                            context,
                            "Unable to like video",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void unlikeVideo(final ImageView likeView, final TextView likeCountTextView, final long videoId, final int position) {
        final VideoSvcApi svc = VideoSvc.getOrShowLogin(context);

        if (svc != null) {
            CallableTask.invoke(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    return svc.unlikeVideo(videoId);
                }
            }, new TaskCallback<Void>() {

                @Override
                public void success(Void result) {
                    Toast.makeText(
                            context,
                            "Video unliked.",
                            Toast.LENGTH_SHORT).show();
                    //((VideoListActivity)context).refreshVideos();
                    updateLikeView(likeView, likeCountTextView, videoId, false, position);
                }

                @Override
                public void error(Exception e) {
                    Toast.makeText(
                            context,
                            "Unable to unlike video",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateLikesCount(final TextView textView, final long videoId) {
        final VideoSvcApi svc = VideoSvc.getOrShowLogin(context);

        if (svc != null) {
            CallableTask.invoke(new Callable<Video>() {

                @Override
                public Video call() throws Exception {
                    return svc.getVideoById(videoId);
                }
            }, new TaskCallback<Video>() {

                @Override
                public void success(Video result) {
                    textView.setText(result.getLikes()+"");
                }

                @Override
                public void error(Exception e) {
                    Toast.makeText(
                            context,
                            "Unable to like video",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
