package com.jzson.gotit.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.Utils;
import com.jzson.gotit.client.activities.MainActivity;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.fragments.TeenProfileFragment;
import com.jzson.gotit.client.model.Person;
import com.jzson.gotit.client.provider.DataProvider;

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
        Intent intent = new Intent(context, MainActivity.class);
        AppApplication.getContext().setPerson(person);
        AppApplication.getContext().setFragment(new TeenProfileFragment());
        context.startActivity(intent);
    }

    @Override
    protected int getItemResourceId() {
        return R.layout.teen_item;
    }

    @Override
    protected TeenViewHolder createViewHolder(View v) {
        return new TeenViewHolder(v);
    }

    @Override
    protected List<Person> onRefresh() {
        return DataProvider.getInstance().getTeens(AppApplication.getContext().getUserId());
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
            cv = (CardView) itemView.findViewById(R.id.cv);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            personAge = (TextView) itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
        }
    }
}
