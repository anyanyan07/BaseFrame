package anyan.com.baseframe.base;

/**
*MVP模式：BaseView,BasePresenter
*@author anyanyan
*@date 2018-2-6
*/


public interface BaseContract {
    interface BaseView{
    }

    interface BasePresenter<T extends BaseView>{
        void attachView(T baseView);
        void detachView();
    }
}
