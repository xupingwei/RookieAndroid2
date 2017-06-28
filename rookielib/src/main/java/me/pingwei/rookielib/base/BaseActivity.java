package me.pingwei.rookielib.base;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.TouchDelegate;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import me.pingwei.rookielib.R;
import me.pingwei.rookielib.app.ActivityTask;
import me.pingwei.rookielib.interfaces.IToaster;
import me.pingwei.rookielib.interfaces.IUserState;
import me.pingwei.rookielib.utils.DialogUtil;
import me.pingwei.rookielib.utils.DisplayHelper;
import me.pingwei.rookielib.utils.Toaster;


/**
 * Created by xupingwei on 2016/4/14.
 */
public abstract class BaseActivity extends AppCompatActivity implements IToaster, IUserState {

    private Toolbar toolbar;
    private TextView toolbarTitle;
    private TextView toolbarRightOne;
    private TextView toolbarRightTwo;

    /**
     * 设置要显示的Layout ID
     *
     * @return
     */
    protected int setLayoutId() {
        return 0;
    }


    /**
     * 设置顶部 Toolbar 的 title
     *
     * @param id
     */
    public void setToolbarTitle(int id) {
        if (null != toolbarTitle) {
            toolbarTitle.setText(getResources().getText(id));
        }
    }

    /**
     * 设置顶部 Toolbar 的 title
     *
     * @param title
     */
    public void setToolbarTitle(String title) {
        if (null != toolbarTitle) {
            toolbarTitle.setText(title);
        }
    }

    /**
     * 设置顶部 Toolbar 的 title
     *
     * @param title
     */
    public void setToolbarTitleSpannable(SpannableString title) {
        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
    }

    /**
     * 设置顶部 Toolbar 的 title
     *
     * @param id
     */
    public void setToolbarTitleBackground(int id) {
        if (toolbar != null) {
            toolbar.setBackgroundResource(id);
        }
    }

    /**
     * 设置toolbar背景(导航的Back,左边按钮)
     *
     * @param resId
     */
    public void setNavigationbarBackground(int resId) {
        if (null != toolbar) {
            toolbar.setNavigationIcon(resId);
        }
    }

    /**
     * 获取toolbar(导航的Back,左边按钮)
     *
     * @return
     */
    public Toolbar getToolbar() {
        return toolbar;
    }

    public TextView getToolbarRightOne() {
        return toolbarRightOne;
    }

    public TextView getToolbarRightTwo() {
        return toolbarRightTwo;
    }

    /**
     * 设置标题文字颜色
     *
     * @param colorId
     */
    public void setToolbarTitleTextColor(int colorId) {
        toolbarTitle.setTextColor(colorId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        initVariables();
        ActivityTask.addActivity(this);
        if (setLayoutId() != 0) {
            setContentView(setLayoutId());
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (null != toolbar) {
                toolbar.setTitle("");
                toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
                toolbarRightTwo = (TextView) toolbar.findViewById(R.id.toolbar_right_two);
                toolbarRightOne = (TextView) toolbar.findViewById(R.id.toolbar_right_one);
                setSupportActionBar(toolbar);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityTask.finishCurrentActivity();
                    }
                });
                setDelegateArea(toolbarRightOne);
                setDelegateArea(toolbarRightTwo);
            }
        }
        initViews();
        loadData();
    }

    private void setDelegateArea(final TextView delegate) {
        delegate.post(new Runnable() {
            @Override
            public void run() {
                // 构造一个 "矩型" 对象
                Rect delegateArea = new Rect();
                // Hit rectangle in parent's coordinates
                delegate.getHitRect(delegateArea);
                // 扩大触摸区域矩阵值
                delegateArea.left -= DisplayHelper.dip2px(getBaseContext(), 10);
                delegateArea.top -= DisplayHelper.dip2px(getBaseContext(), 10);
                delegateArea.right += DisplayHelper.dip2px(getBaseContext(), 10);
                delegateArea.bottom += DisplayHelper.dip2px(getBaseContext(), 10);
                /**
                 * 构造扩大后的触摸区域对象
                 * 第一个构造参数表示  矩形面积
                 * 第二个构造参数表示 被扩大的触摸视图对象
                 */
                TouchDelegate expandedArea = new TouchDelegate(delegateArea, delegate);

                if (View.class.isInstance(delegate.getParent())) {
                    // 设置视图扩大后的触摸区域
                    ((View) delegate.getParent()).setTouchDelegate(expandedArea);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        onResumeData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * finish当前Activity
     */
    public void finishCurrentActivity() {
        ActivityTask.finishCurrentActivity();
    }


    /**
     * finish所有Activity
     */
    public void finishAllActivity() {
        ActivityTask.finishAllActivity();
    }


    @Override
    public void showToast(String msg) {
        Toaster toaster = new Toaster(this.getApplicationContext(), msg);
        toaster.showLoading();
    }

    /**
     * toast为资源文件
     *
     * @param resId
     */
    public void showToast(int resId) {
        Toaster toaster = new Toaster(this, this.getString(resId));
        toaster.showLoading();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivity(Intent intent) {
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        super.startActivity(intent);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


    public abstract void initVariables();

    public abstract void initViews();

    public abstract void loadData();

    public abstract void onResumeData();

}
