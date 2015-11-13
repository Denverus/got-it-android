package org.coursera.capstone.gotit.client.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.coursera.capstone.gotit.client.AppApplication;
import org.coursera.capstone.gotit.client.NavUtils;
import org.coursera.capstone.gotit.client.R;
import org.coursera.capstone.gotit.client.Utils;
import org.coursera.capstone.gotit.client.adapter.base.BaseListAdapter;
import org.coursera.capstone.gotit.client.model.Feedback;
import org.coursera.capstone.gotit.client.model.GraphData;
import org.coursera.capstone.gotit.client.model.UserFeed;
import org.coursera.capstone.gotit.client.provider.ServiceApi;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FeedbackListAdapter extends BaseListAdapter<Feedback, FeedbackListAdapter.FeedbackViewHolder> {

    public FeedbackListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onItemClick(Context context, Feedback model) {
    }

    @Override
    protected int getItemResourceId() {
        return org.coursera.capstone.gotit.client.R.layout.feedback_item;
    }

    @Override
    protected FeedbackViewHolder createViewHolder(View v) {
        return new FeedbackViewHolder(v);
    }

    @Override
    protected List<Feedback> onRefresh(ServiceApi svc) {
        return svc.getFeedbackList(AppApplication.getContext().getCurrentUserId());
    }

    @Override
    public void onBindViewHolder(FeedbackViewHolder holder, int position) {
        final Feedback feedback = getModel(position);

        holder.personName.setText(feedback.getPerson().getName());

        GraphView graph = holder.graphView;

        DataPoint[] dataPoints = new DataPoint[feedback.getGraphData().size()];
        for (int i=0; i < feedback.getGraphData().size(); i++) {
            GraphData graphData = feedback.getGraphData().get(i);
            dataPoints[i] = new DataPoint(graphData.getDate(), graphData.getValue());
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

        graph.addSeries(series);
        graph.setTitle(getContext().getString(R.string.graph_title));

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getContext()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(feedback.getGraphData().get(0).getDate().getTime());
        graph.getViewport().setMaxX(feedback.getGraphData().get(feedback.getGraphData().size()-1).getDate().getTime());
        graph.getViewport().setXAxisBoundsManual(true);
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        GraphView graphView;

        FeedbackViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.cv);
            personName = (TextView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.person_name);
            graphView = (GraphView)itemView.findViewById(R.id.graph);
        }
    }
}
