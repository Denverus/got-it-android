package org.coursera.capstone.gotit.client.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Denis on 11/13/2015.
 */
public class Feedback extends BaseModel{

    private Person person;

    private List<GraphData> graphData;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<GraphData> getGraphData() {
        return graphData;
    }

    public void setGraphData(List<GraphData> graphData) {
        this.graphData = graphData;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "person=" + person +
                ", graphData=" + graphData +
                '}';
    }
}
