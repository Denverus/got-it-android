package com.jzson.gotit.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.Utils;
import com.jzson.gotit.client.activities.MainActivity;
import com.jzson.gotit.client.fragments.TeenProfileFragment;
import com.jzson.gotit.client.model.Person;

import java.util.List;

/**
 * Created by Denis on 10/11/2015.
 */
public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.PersonViewHolder> implements View.OnClickListener {

    private List<Person> persons;
    private RecyclerView mRecyclerView;

    public PersonListAdapter(List<Person> persons){
        this.persons = persons;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teen_item, viewGroup, false);
        v.setOnClickListener(this);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText(persons.get(i).getName());
        personViewHolder.personAge.setText(Utils.ageToString(persons.get(i).getDateBirth()));
        //personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    @Override
    public void onClick(View view) {
        int itemPosition = mRecyclerView.getChildAdapterPosition(view);
        Person person = persons.get(itemPosition);
        Context context = mRecyclerView.getContext();

        Intent intent = new Intent(context, MainActivity.class);
        AppApplication.getContext().setPerson(person);
        AppApplication.getContext().setFragment(new TeenProfileFragment());
        context.startActivity(intent);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

}