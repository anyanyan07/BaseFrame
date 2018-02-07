package anyan.com.baseframe.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import anyan.com.baseframe.util.ActivityManagerUtil;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
*Activity基类：一般需要继承此Activity
*@author anyanyan
*@date 2018-2-6
*/


public abstract class BaseActivity<T extends BasePresenterImpl> extends AppCompatActivity implements BaseContract.BaseView {

    T presenter;
    private Unbinder unbinder;

    //设置页面布局
    protected abstract int setLayoutRes(@LayoutRes int layoutId);
    //dagger2注入presenter
    protected abstract void injectPresenter();
    //初始化ui
    protected abstract void initView();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManagerUtil.getInstance().addActivity(this);
        unbinder = ButterKnife.bind(this);
        injectPresenter();
        //注入之后，presenter不为空
        if (presenter != null) {
            presenter.attachView(this);
        }
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerUtil.getInstance().removeActivity(this);
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (presenter != null) {
            presenter.detachView();
        }
    }

    //设置字体不随用户的设置改变而改变
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics());
        return res;
    }
}
