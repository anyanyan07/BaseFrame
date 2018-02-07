package anyan.com.baseframe.util;

import android.app.Activity;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

/**
*管理Activity
*@author anyanyan
*@date 2018-2-6
*/


public class ActivityManagerUtil {
    private  List<Activity> activityStack; //存放activity的集合
    private static ActivityManagerUtil instance;//AppManagerUtils实例

    private ActivityManagerUtil() {
    }

    /**
     * 单一实例
     */
    public static ActivityManagerUtil getInstance() {
        synchronized (ActivityManagerUtil.class){
            if (instance == null) {
                instance = new ActivityManagerUtil();
            }
            return instance;
        }
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new LinkedList<>();
        }
        activityStack.add(activity);
    }


    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activityStack.remove(activity);
            activity.finish();
            System.gc();
        }
    }

    /**
     * 移除指定的activity
     */
    public void removeActivity(Activity activity) {
        if (activityStack == null) {
            return;
        }
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 获取指定的Activity
     *
     * @author kymjs
     */
    public  Activity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {

        try {
            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
