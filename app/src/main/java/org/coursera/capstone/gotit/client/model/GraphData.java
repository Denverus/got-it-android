package org.coursera.capstone.gotit.client.model;

import java.util.Date;

/**
 * Created by Denis on 11/13/2015.
 */

public class GraphData {

    private Long date;
    private int value;

    public GraphData(Long date, int value) {
        this.date = date;
        this.value = value;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "GraphData{" +
                "date=" + date +
                ", value=" + value +
                '}';
    }
}
