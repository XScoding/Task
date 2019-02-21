package com.xs.task;

import com.xs.task.api.Sequence;
import com.xs.task.api.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xs on 2019/1/24.
 */

public class TaskSession implements Session {

    private List<String> keys = new ArrayList<>();

    private Sequence sequence;

    public TaskSession(Sequence sequence) {
        this.sequence = sequence;
        if (sequence != null) {
            this.keys.addAll(Arrays.asList(sequence.getRunTasks()));
        }
    }

    @Override
    public boolean add(String task) {
        return keys.add(task);
    }

    @Override
    public void add(String task, int index) {
        if (index > keys.size() || index < 0)
            throw new IndexOutOfBoundsException(error(index));
        keys.add(index,task);
    }

    @Override
    public void change(String task, int index) {
        if (index > keys.size() || index < 0)
            throw new IndexOutOfBoundsException(error(index));
        keys.remove(index);
        keys.add(index,task);
    }

    @Override
    public void change(String newTask, String oldTask) {
        if (keys.indexOf(oldTask) >= 0) {
            change(newTask, keys.indexOf(oldTask));
        }
    }

    @Override
    public boolean replace(String[] tasks) {
        if (tasks != null) {
            keys.clear();
            keys.addAll(Arrays.asList(tasks));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String delete(int index) {
        if (index > keys.size() || index < 0)
            throw new IndexOutOfBoundsException(error(index));
        return keys.remove(index);
    }

    @Override
    public boolean delete(String task) {
        return keys.remove(task);
    }

    @Override
    public String get(int index) {
        return keys.get(index);
    }

    @Override
    public int indexOf(String task) {
        return keys.indexOf(task);
    }

    @Override
    public int lastIndexOf(String task) {
        return keys.lastIndexOf(task);
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public boolean commit() {
        String[] tasks = new String[keys.size()];
        keys.toArray(tasks);
        return sequence.submit(tasks);
    }

    private String error(int index) {
        return "Index: "+index+", Size: "+ keys.size();
    }
}
