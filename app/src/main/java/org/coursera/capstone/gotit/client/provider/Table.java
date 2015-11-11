package org.coursera.capstone.gotit.client.provider;

import org.coursera.capstone.gotit.client.model.BaseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Denis on 10/20/2015.
 */
public class Table<T extends BaseModel> {

    public interface AddTrigger<T> {
        void onAdd(T model);
    }

    public interface BooleanCriteria<T> {
        boolean getCriteriaValue(T value);
    }

    private int index = 0;

    private Map<Integer, T> map = new HashMap<>();

    private List<AddTrigger<T>> addTriggers = new ArrayList<>();

    public int add(T value) {
        value.setId(index);
        map.put(index, value);
        callAddTrigger(value);
        return index++;
    }

    public int addOrSave(T value) {
        if (value.getId() == BaseModel.INITIAL_ID) {
            return add(value);
        } else {
            map.put(index, value);
            return index;
        }
    }

    public void addOrSave(List<T> valueList) {
        for (T value : valueList) {
            addOrSave(value);
        }
    }

    public List<T> getList() {
        return new ArrayList<>(map.values());
    }

    public T getById(int index) {
        return map.get(index);
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

    public T delete(int id) {
        return map.remove(id);
    }

    public void deleteAll(List<T> list) {
        for (T value : list) {
            delete(value.getId());
        }
    }

    public void deleteByCriteria(BooleanCriteria<T> criteria) {
        List<T> result = new ArrayList<>();
        List<T> list = getList();
        for(T value : list) {
            if (criteria.getCriteriaValue(value)) {
                result.add(value);
            }
        }

        deleteAll(result);
    }

    public void addOnAddTrigger(AddTrigger<T> addTrigger) {
        addTriggers.add(addTrigger);
    }

    private void callAddTrigger(T model) {
        for (AddTrigger trigger : addTriggers) {
            trigger.onAdd(model);
        }
    }
}
