package anyan.com.baseframe.base;

/**
 * Created by Administrator on 2018-2-6.
 */

public class BasePresenterImpl implements BaseContract.BasePresenter {
    BaseContract.BaseView baseView;
    @Override
    public void attachView(BaseContract.BaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    public void detachView() {
        this.baseView = null;
    }
}
