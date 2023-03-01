package com.yung.android.common.ui.wiget;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.customview.widget.ViewDragHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.WeakHashMap;

/**
 * Created by Leo on 2017/8/21.
 */

public class Logger extends FrameLayout implements Thread.UncaughtExceptionHandler, Application.ActivityLifecycleCallbacks {
    private static boolean debuggable = true; //正式环境(false)不打印日志，也不能唤起app的debug界面
    private static Logger mLogger;
    private static String tag;
    private final Context mContext;
    private long timestamp = 0;
    private View mSrcView;

    private int mFilterClick;
    private Context mCurrentActivity;
    private AlertDialog mFilterDialog;
    private String mFilterText;
    private int mFilterLevel;
    private static final int LOG_SOUT = 8;
    private static Thread.UncaughtExceptionHandler mDefaultHandler;
    private final LinearLayout mLogContainer;
    private List<String> mLogList = new ArrayList<>();
    private List<String> mFilterList = new ArrayList<>();
    private final ArrayAdapter<String> mLogAdapter;
    private final TextView mTvTitle;
    private final ListView mLvLog;
    private boolean mAutoScroll = true;

    public static void setTag(String tag) {
        Logger.tag = tag;
    }

    List<Integer> mList = Collections.synchronizedList(new ArrayList<Integer>());

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface IgnoreLoggerView {
        // 有些自定义view在解绑时会跟本工具冲突(onPause后view空白)
        // 可以在activity上打上此注解忽略本工具View
        // 当然忽略后不能在界面上唤起悬浮窗
    }

    private Logger(final Context context) {
        super(context);
        mContext = context;
        tag = context.getApplicationInfo().packageName; //可以自定义
        final float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 5, getResources().getDisplayMetrics());
        //日志容器
        mLogContainer = new LinearLayout(context);
        mLogContainer.setOrientation(LinearLayout.VERTICAL);
//        mLogContainer.setBackgroundColor(Color.argb(0x33, 0X00, 0x00, 0x00));
        mLogContainer.setBackgroundColor(Color.BLACK);

        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        LayoutParams layoutParams = new LayoutParams(widthPixels, heightPixels / 2, Gravity.BOTTOM);
        layoutParams.bottomMargin = 48;

        mLogContainer.setLayoutParams(layoutParams);

        mLogContainer.setVisibility(GONE);
        //小窗口标题
        mTvTitle = new TextView(context);
        mTvTitle.setTextSize(v * 1.5f);
        mTvTitle.setText("Logger");
        mTvTitle.setTextColor(Color.WHITE);
        mTvTitle.setBackgroundColor(Color.argb(0x55, 0X00, 0x00, 0x00));
        mTvTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog(); //点击日志窗口标题栏打开过滤器
            }
        });
        mTvTitle.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                loggerSwitch();//长按日志窗口标题栏关闭日志窗口
                return true;
            }
        });
        mLogContainer.addView(mTvTitle);
        //日志列表
        mLvLog = new ListView(context) {
            @Override
            public boolean onTouchEvent(MotionEvent ev) {
                getParent().requestDisallowInterceptTouchEvent(true);
                return super.onTouchEvent(ev);
            }
        };
        mLvLog.setFastScrollEnabled(true);
        mLvLog.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mLogContainer.addView(mLvLog);
        mLogAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, mFilterList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = new TextView(parent.getContext());
                }
                TextView textView = (TextView) convertView;
                textView.setTextSize(v);
                textView.setText(Html.fromHtml(mFilterList.get(position)));
                textView.setShadowLayer(1, 1, 1, Color.BLACK);
                return textView;
            }
        };
        mLvLog.setAdapter(mLogAdapter);
        mLvLog.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mAutoScroll = firstVisibleItem + visibleItemCount == totalItemCount;
            }
        });
        mLvLog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCurrentActivity);
                String message = mFilterList.get(position);
                message = message.replace("FFFFFF", "000000");
                builder.setMessage(Html.fromHtml(message));
                builder.setPositiveButton("确定", null);
                builder.setNegativeButton("清空日志", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mLogList.clear();
                        refreshList();
                    }
                });
                builder.show();
            }
        });

        mActivityCheck = new ActivityCheck();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(mRunnable, 10000);
    }

    //activity检测
    private ActivityCheck mActivityCheck;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            handler.removeCallbacks(this);
            handler.postDelayed(this, 30000); //30秒检测一次
            String s = null;
            try {
                s = mActivityCheck.check();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(s)) return;
            //Toast.makeText(mContext, "发生内存泄漏:" + s, Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 在application 的 onCreate() 方法初始化
     *
     * @param application
     */
    public static void init(Application application) {
        if (debuggable && mLogger == null) {
            synchronized (Logger.class) {
                if (mLogger == null) {
                    mLogger = new Logger(application.getApplicationContext());
                    application.registerActivityLifecycleCallbacks(mLogger);
                    //获取系统默认异常处理器
                    mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
                    //线程空闲时设置异常处理，兼容其他框架异常处理能力
                    Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                        @Override
                        public boolean queueIdle() {
                            Thread.setDefaultUncaughtExceptionHandler(mLogger);//线程异常处理设置为自己
                            return false;
                        }
                    });
                }
            }
        }
    }

    public static void v(String msg) {
        v(tag, msg);
    }

    public static void d(String msg) {
        d(tag, msg);
    }

    public static void i(String msg) {
        i(tag, msg);
    }

    public static void w(String msg) {
        w(tag, msg);
    }

    public static void e(String msg) {
        e(tag, msg);
    }

    public static void s(String msg) {
        s(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (mLogger != null) mLogger.print(Log.VERBOSE, tag, msg);
    }

    public static void d(String tag, String msg) {
        if (mLogger != null) mLogger.print(Log.DEBUG, tag, msg);
    }

    public static void i(String tag, String msg) {
        if (mLogger != null) mLogger.print(Log.INFO, tag, msg);
    }

    public static void w(String tag, String msg) {
        if (mLogger != null) mLogger.print(Log.WARN, tag, msg);
    }

    public static void e(String tag, String msg) {
        if (mLogger != null) mLogger.print(Log.ERROR, tag, msg);
    }

    public static void s(String tag, String msg) {
        if (mLogger != null) mLogger.print(LOG_SOUT, tag, msg);
    }

    private void print(int type, String tag, String msg) {
        if (!debuggable || mLogger == null || type < mFilterLevel + 2) return;
//        String str = "[" + getTime() + "]" + getLevel(type) + "/" + tag + ":" + msg;
        String str = "[" + getTime() + "]" + getLevel(type) + "/" + ":" + msg;
        if (!TextUtils.isEmpty(mFilterText) && !str.contains(mFilterText)) return;
        handler.obtainMessage(type, str).sendToTarget();
        int start = 0;
        int end = 0;
        while (end < msg.length()) {
            end = start + 3000 > msg.length() ? msg.length() : start + 3000;
            String subMsg = msg.substring(start, end);
            start = end;
            switch (type) {
                case Log.VERBOSE:
                    Log.v(tag, subMsg);
                    break;
                case Log.DEBUG:
                    Log.d(tag, subMsg);
                    break;
                case Log.INFO:
                    Log.i(tag, subMsg);
                    break;
                case Log.WARN:
                    Log.w(tag, subMsg);
                    break;
                case Log.ERROR:
                    Log.e(tag, subMsg);
                    break;
                case LOG_SOUT:
                    System.out.println(tag + ":" + subMsg);
                    break;
            }
        }
    }

    private String getLevel(int type) {
        String[] level = new String[]{"S", "", "V", "D", "I", "W", "E"};
        return level[type];
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm:ss.SSS",
                Locale.getDefault()).format(new Date());
    }

    private void addText(int type, String text) {
        String[] level = new String[]{"#FFFFFF", "", "#FFFFFF", "#2FB1FE", "#00ff00", "#EFC429", "#FF0000"};
        String str = String.format("<font color=\"" + level[type] + "\">%s</font>", text);
        mLogList.add(str);
        while (mLogList.size() > 100) mLogList.remove(0);
        refreshList();
    }

    /*刷新日志列表*/
    private void refreshList() {
        mFilterList.clear();//清空过滤列表
        for (int i = 0; i < mLogList.size(); i++) {
            String s = mLogList.get(i);
            int l = 2;
            for (int j = 2; j < 7; j++) {
                String level1 = getLevel(j);
                if (s.contains("]" + level1 + "/")) {
                    l = j;
                    break;
                }
            }
            if (l >= mFilterLevel + 2 && (mFilterText == null || s.contains(mFilterText))) {
                mFilterList.add(s);
            }
        }
        mLogAdapter.notifyDataSetChanged();
        if (mAutoScroll)
            mLvLog.smoothScrollToPosition(mLogList.size());
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mActivityCheck.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        mCurrentActivity = activity;
        if (debuggable) {
            if (checkIgnore(activity)) return;
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            mSrcView = decorView.getChildAt(0);
            decorView.removeView(mSrcView);
            mLogger.addView(mSrcView, 0);
            mLogger.addView(mLogContainer, 1);
            decorView.addView(mLogger);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        mCurrentActivity = null;
        if (checkIgnore(activity)) return;
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        mLogger.removeView(mSrcView);
        mLogger.removeView(mLogContainer);
        decorView.removeView(mLogger);
        if (mSrcView != null) {
            decorView.addView(mSrcView, 0);
        }
    }

    private boolean checkIgnore(Activity activity) {
        Class<? extends Activity> a = activity.getClass();
        return a.isAnnotationPresent(IgnoreLoggerView.class);
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mActivityCheck.remove(activity);
    }


    private void resetParams(int x, int y) {
        MarginLayoutParams margin = new MarginLayoutParams(mLogContainer.getLayoutParams());
        margin.setMargins(x, y, x + margin.width, y + margin.height);
        LayoutParams layoutParams = new LayoutParams(margin);
        mLogContainer.setLayoutParams(layoutParams);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            long l = SystemClock.uptimeMillis();
            long dis = l - timestamp;

            checkFilter(dis, ev.getY());
            timestamp = SystemClock.uptimeMillis();
        }
        return super.dispatchTouchEvent(ev);
    }


    //日志开关切换
    public void loggerSwitch() {
        if (mLogContainer.getVisibility() == GONE) {
            mLogContainer.setVisibility(VISIBLE);
        } else {
            mLogContainer.setVisibility(GONE);
        }
    }

    public void hideLogger() {
        mLogContainer.setVisibility(GONE);
    }

    public static Logger getInstance() {
        return mLogger;
    }

    private void checkFilter(long dis, float y) {
        if (mLogContainer.getVisibility() == GONE) return;
        if (dis < 300 && y < 200) {
            mFilterClick++;
            if (mFilterClick > 3) {
                showFilterDialog();
                mFilterClick = 0;
            }
        } else {
            mFilterClick = 0;
        }
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            String text = (String) msg.obj;
            addText(msg.what, text);
        }
    };

    private void showFilterDialog() {
        if (mCurrentActivity == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(mCurrentActivity);
        builder.setTitle("日志过滤器");
        builder.setView(initDialogView());
        builder.setCancelable(false);
        if (mFilterDialog != null) {
            mFilterDialog.dismiss();
        }
        mFilterDialog = builder.show();
    }

    @NonNull
    private View initDialogView() {
        //容器
        LinearLayout linearLayout = new LinearLayout(mCurrentActivity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        //下拉框
        Spinner spinner = new Spinner(mCurrentActivity, Spinner.MODE_DROPDOWN);
        spinner.setAdapter(new ArrayAdapter<String>(mCurrentActivity,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Verbose", "Debug", "Info", "Warn", "Error"}));
        spinner.setSelection(mFilterLevel);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mFilterLevel = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //文本编辑框
        final EditText editText = new EditText(mCurrentActivity);
        editText.setHint("筛选关键字");
        if (mFilterText != null) {
            editText.setText(mFilterText);
            editText.setSelection(mFilterText.length());
        }
        //按钮
        Button button = new Button(mCurrentActivity);
        button.setText("确定");
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterText = editText.getText().toString();
                mFilterDialog.dismiss();
                refreshList();
            }
        });
        //添加到容器
        linearLayout.addView(spinner);
        linearLayout.addView(editText);
        linearLayout.addView(button);
        return linearLayout;
    }

    /**
     * 捕获崩溃信息
     *
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        // 打印异常信息
        e.printStackTrace();
        // 我们没有处理异常 并且默认异常处理不为空 则交给系统处理
        if (mDefaultHandler != null) {
            // 系统处理  
            mDefaultHandler.uncaughtException(t, e);
        }
    }


    private void showInWeb(CharSequence msg, final Thread t, final Throwable ex) {
        try {
            ServerSocket socket = new ServerSocket(45678);
            StringBuilder sb = new StringBuilder("HTTP/1.1 200 OK\n")
                    .append("\n")
                    .append("<head>")
                    .append("<meta name='viewport' content='width=240, target-densityDpi=device-dpi'>")
                    .append("</head>")
                    .append("<html>")
                    .append("<h1>APP Crash</h1>")
                    .append(msg)
                    .append("<br/>")
                    .append("</html>");
            byte[] bytes = sb.toString().getBytes();
            for (; ; ) {
                Socket accept = socket.accept();
                OutputStream os = accept.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
                accept.close();
                mDefaultHandler.uncaughtException(t, ex);
                //Process.killProcess(Process.myPid());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ActivityCheck {
        List<Integer> mList = Collections.synchronizedList(new ArrayList<Integer>());
        WeakHashMap<Activity, Integer> mMap = new WeakHashMap<>();
        ReferenceQueue mQueue = new ReferenceQueue();
        WeakReference mPhantomReference = new WeakReference(new Object(), mQueue);

        void add(Activity activity) {
            int code = activity.hashCode();
            mList.add(code);
            mMap.put(activity, code);
        }

        void remove(Activity activity) {
            mList.remove(Integer.valueOf(activity.hashCode()));
        }

        String check() throws InterruptedException {
            if (!mPhantomReference.isEnqueued()) return null;
//            e("理论存活activity数：" + mList.size());
            StringBuilder stringBuilder = new StringBuilder();
            for (Activity activity : mMap.keySet()) {
                int s = activity.hashCode();
                String name = activity.getClass().getName();
                if (!mList.contains(s)) {
                    stringBuilder.append(name).append(";");
                }
            }
            mQueue.remove();
            mPhantomReference = new WeakReference(new Object(), mQueue);
            return stringBuilder.toString();
        }
    }
}
