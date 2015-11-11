package org.coursera.capstone.gotit.client.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.coursera.capstone.gotit.client.AppApplication;
import org.coursera.capstone.gotit.client.CallableTask;
import org.coursera.capstone.gotit.client.NavUtils;
import org.coursera.capstone.gotit.client.TaskCallback;
import org.coursera.capstone.gotit.client.adapter.base.BaseListAdapter;
import org.coursera.capstone.gotit.client.databinding.FollowerListItemBinding;
import org.coursera.capstone.gotit.client.model.Person;
import org.coursera.capstone.gotit.client.provider.ServiceApi;
import org.coursera.capstone.gotit.client.provider.ServiceCall;

import java.util.List;

/**
 * Created by Denis on 10/23/2015.
 */

public class FollowerListAdapter extends BaseListAdapter<Person, FollowerListAdapter.FollowerListViewHolder> {

    public FollowerListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onItemClick(Context context, Person model) {

    }

    @Override
    protected int getItemResourceId() {
        return org.coursera.capstone.gotit.client.R.layout.follower_list_item;
    }

    @Override
    protected FollowerListViewHolder createViewHolder(View v) {
        return new FollowerListViewHolder(v);
    }

    @Override
    protected List<Person> onRefresh(ServiceApi svc) {
        return svc.getFollowerList(AppApplication.getContext().getCurrentUserId());
    }

    @Override
    public void onBindViewHolder(FollowerListViewHolder holder, int position) {
        final FollowerListItemBinding bind = DataBindingUtil.bind(holder.itemView);
        final Person person = getModel(position);
        bind.setPerson(person);
        bind.unsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind.unsubscribe.setEnabled(false);
                cancelSubscription(AppApplication.getContext().getCurrentUserId(), person.getId());
            }
        });
    }

    public static class FollowerListViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView requestImage;
        TextView name;
        Button unsibscribeButton;

        FollowerListViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.cv);
            requestImage = (ImageView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.request_code_image);
            name = (TextView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.name);
            unsibscribeButton = (Button)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.unsubscribe);
        }
    }

    private void cancelSubscription(final int userId, final int followerId) {
        CallableTask.invoke(getContext(), new ServiceCall<Void>() {
            @Override
            public Void call(ServiceApi srv) throws Exception {
                srv.cancelSubscription(userId, followerId);
                return null;
            }
        }, new TaskCallback<Void>() {
            @Override
            public void success(Void result) {
                NavUtils.showFollowerList(getContext());
                Toast.makeText(getContext(), "Subscription canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void error(Exception e) {
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
