package com.jzson.gotit.client.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.NavUtils;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.databinding.FollowerListItemBinding;
import com.jzson.gotit.client.model.Person;
import com.jzson.gotit.client.provider.DataProvider;

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
        return R.layout.follower_list_item;
    }

    @Override
    protected FollowerListViewHolder createViewHolder(View v) {
        return new FollowerListViewHolder(v);
    }

    @Override
    protected List<Person> onRefresh() {
        return DataProvider.getInstance().getFollowerList(AppApplication.getContext().getUserId());
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
                DataProvider.getInstance().cancelSubscription(AppApplication.getContext().getUserId(), person.getId());
                NavUtils.showFollowerList(getContext());
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
            cv = (CardView)itemView.findViewById(R.id.cv);
            requestImage = (ImageView)itemView.findViewById(R.id.request_code_image);
            name = (TextView)itemView.findViewById(R.id.name);
            unsibscribeButton = (Button)itemView.findViewById(R.id.unsubscribe);
        }
    }
}
