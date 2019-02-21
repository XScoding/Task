package com.xs.task;

import android.content.Context;
import android.content.res.XmlResourceParser;

import android.support.annotation.XmlRes;
import android.text.TextUtils;
import android.util.Log;

import com.xs.task.api.Task;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * task manager
 *
 * Created by xs on 2019/1/10.
 */

public class TaskManager {

    private static final String TAG = "TaskManager";

    private static TaskManager instance;

    private int id;

    private TaskManager() {
    }

    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public void init(@XmlRes int id) {
        this.id = id;
    }

    /**
     * 外部添加的task(add 方式注册)
     */
    private Map<String, TaskInfo> outTasks = new HashMap<>();
    /**
     * 内部注册的task(xml 方式注册)
     */
    private Map<String, TaskInfo> classTasks;

    /**
     * get
     * @param context
     * @param key
     * @return
     */
    public Task getTask(Context context, String key) {
        initClassTask(id, context);
        Task task = initTask(key);
        return task;
    }

    /**
     * 所有注册的task
     *
     * @return
     */
    public Map<String, TaskInfo> getAllTask(Context context) {
        initClassTask(id, context);
        Map<String, TaskInfo> map = new HashMap<>();
        if (outTasks.size() > 0) {
            map.putAll(outTasks);
        }
        if (classTasks != null && classTasks.size() > 0) {
            map.putAll(classTasks);
        }
        return map;
    }

    /**
     * 外部添加task
     * @param key
     * @param task
     * @param <T>
     */
    public <T extends Task> boolean addTask(Context context,String key, Class<T> task) {
        initClassTask(id, context);
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setKey(key);
        taskInfo.setClz(task.getName());
        outTasks.put(key,taskInfo);
        return true;
    }

    /**
     * init task
     * @param key
     * @return
     */
    private Task initTask(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        if (outTasks.containsKey(key)) {
            String clz = outTasks.get(key).getClz();
            return createTask(clz);
        } else if (classTasks.containsKey(key)) {
            String clz = classTasks.get(key).getClz();
            return createTask(clz);
        }
        return null;
    }


    /**
     * create task
     * @param taskClass
     * @return
     */
    private Task createTask(Class<Task> taskClass) {
        Task task = null;
        try {
            if (taskClass != null & Task.class.isAssignableFrom(taskClass)) {
                task = taskClass.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding Task " + taskClass.getSimpleName() + "");
        }
        return task;
    }

    /**
     * create task
     * @param className
     * @return
     */
    private Task createTask(String className) {
        Task task = null;
        try {
            Class<?> c = null;
            if ((className != null) && !("".equals(className))) {
                c = Class.forName(className);
            }
            if (c != null & Task.class.isAssignableFrom(c)) {
                task = (Task) c.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }

    /**
     * 获取接口泛型类名
     *
     * @param obj
     * @param i
     * @return
     */
    public String getT(Object obj, int i) {
        try {
            Type[] types = obj.getClass().getGenericInterfaces();
            ParameterizedType genericSuperclass = (ParameterizedType) types[0];
            Class aClass = (Class) (genericSuperclass.getActualTypeArguments()[i]);
            String name = aClass.getName();
            return name;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 校验task执行顺序的合法性
     *
     * @param newTaskInstances
     * @param head
     * @param tail
     * @return
     */
    public boolean checkTask(List<TaskInstance> newTaskInstances, String head, String tail) {
        for (int i = 0; i < newTaskInstances.size(); i++) {
            if (i == 0) {
                if (!TextUtils.equals(head, newTaskInstances.get(i).getInput())) {
                    return false;
                }
                if (newTaskInstances.size() == 1) {
                    if (!TextUtils.equals(newTaskInstances.get(i).getOutput(), tail)) {
                        return false;
                    }
                } else {
                    if (!TextUtils.equals(newTaskInstances.get(i).getOutput(), newTaskInstances.get(i + 1).getInput())) {
                        return false;
                    }
                }
            } else if (i == newTaskInstances.size() - 1) {
                if (!TextUtils.equals(newTaskInstances.get(i).getOutput(), tail)) {
                    return false;
                }
            } else {
                if (!TextUtils.equals(newTaskInstances.get(i).getOutput(), newTaskInstances.get(i + 1).getInput())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 初始化task配置
     *
     * @param id
     * @param context
     */
    private void initClassTask(@XmlRes int id, Context context) {
        if (context == null) {
            return;
        }
        if (classTasks == null || classTasks.size() == 0) {
            XmlResourceParser xml = context.getResources().getXml(id);
            parse(xml);
        }
    }

    /**
     * 解析task配置文件
     *
     * @param xmlParser
     */
    private void parse(XmlResourceParser xmlParser) {
        try {
            int event = xmlParser.getEventType();   //先获取当前解析器光标在哪
            while (event != XmlPullParser.END_DOCUMENT) {    //如果还没到文档的结束标志，那么就继续往下处理
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:   //一般都是获取标签的属性值，所以在这里数据你需要的数据
                        if (xmlParser.getName().equals("task")) {
                            TaskInfo taskInfo = new TaskInfo();
                            for (int i = 0; i < xmlParser.getAttributeCount(); i++) {
                                if (TextUtils.equals("key", xmlParser.getAttributeName(i))) {
                                    taskInfo.setKey(xmlParser.getAttributeValue(i));
                                } else if (TextUtils.equals("class", xmlParser.getAttributeName(i))) {
                                    taskInfo.setClz(xmlParser.getAttributeValue(i));
                                }
                            }
                            if (!TextUtils.isEmpty(taskInfo.getKey()) && !TextUtils.isEmpty(taskInfo.getClz())) {
                                classTasks.put(taskInfo.getKey(), taskInfo);
                            }
                        } else if ((xmlParser.getName().equals("tasks"))) {
                            classTasks = new HashMap<>();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                event = xmlParser.next();   //将当前解析器光标往下一步移
            }
        } catch (XmlPullParserException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

}
