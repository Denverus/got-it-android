package org.coursera.capstone.gotit.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.coursera.capstone.gotit.client.AppApplication;
import org.coursera.capstone.gotit.client.NavUtils;
import org.coursera.capstone.gotit.client.Utils;
import org.coursera.capstone.gotit.client.activities.MainActivity;
import org.coursera.capstone.gotit.client.adapter.base.BaseListAdapter;
import org.coursera.capstone.gotit.client.fragments.TeenProfileFragment;
import org.coursera.capstone.gotit.client.model.Person;
import org.coursera.capstone.gotit.client.provider.ServiceApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 10/27/2015.
 */
public class TeenListAdapter extends BaseListAdapter<Person, TeenListAdapter.TeenViewHolder> {

    public TeenListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onItemClick(Context context, Person person) {
        NavUtils.showTeenProfile(context, person);
    }

    @Override
    protected int getItemResourceId() {
        return org.coursera.capstone.gotit.client.R.layout.teen_item;
    }

    @Override
    protected TeenViewHolder createViewHolder(View v) {
        return new TeenViewHolder(v);
    }

    @Override
    protected List<Person> onRefresh(ServiceApi svc) {
        return svc.getTeens(AppApplication.getContext().getCurrentUserId());
    }

    @Override
    public void onBindViewHolder(TeenViewHolder holder, int position) {
        final Person person = getModel(position);
        holder.personName.setText(person.getName());
        holder.personAge.setText(Utils.ageToString(person.getDateBirth()));
    }

    public static class TeenViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        TeenViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(org.coursera.capstone.gotit.client.R.id.cv);
            personName = (TextView) itemView.findViewById(org.coursera.capstone.gotit.client.R.id.person_name);
            personAge = (TextView) itemView.findViewById(org.coursera.capstone.gotit.client.R.id.person_age);
            personPhoto = (ImageView) itemView.findViewById(org.coursera.capstone.gotit.client.R.id.person_photo);
        }
    }
}
