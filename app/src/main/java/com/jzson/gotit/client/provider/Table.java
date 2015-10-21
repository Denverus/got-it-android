package com.jzson.gotit.client.provider;

import com.jzson.gotit.client.model.BaseModel;
import com.jzson.gotit.client.model.Feedback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Denis on 10/20/2015.
 */
public class Table<T extends BaseModel> {

    public interface Criteria<T> {
        int getCriteriaValue(T value);
    }

    public interface BooleanCriteria<T> {
        boolean getCriteriaValue(T value);
    }

    private int index = 0;

    private Map<Integer, T> map = new HashMap<>();

    public int add(T value) {
        value.setId(index);
        map.put(index, value);
        return index++;
    }

    public List<T> getList() {
        return new ArrayList<>(map.values());
    }

    public T getById(int index) {
        return map.get(index);
    }

    public List<T> getListByCriteria(int id, Criteria<T> criteria) {
        List<T> result = new ArrayList<>();
        List<T> list = getList();
        for(T value : list) {
            if (criteria.getCriteriaValue(value) == id) {
                result.add(value);
            }
        }
        return result;
    }

    public List<T> getListByCriteria(BooleanCriteria<T> criteria) {
        List<T> result = new ArrayList<>();
        List<T> list = getList();
        for(T value : list) {
            if (criteria.getCriteriaValue(value)) {
                result.add(value);
            }
        }
        return result;
    }
}
