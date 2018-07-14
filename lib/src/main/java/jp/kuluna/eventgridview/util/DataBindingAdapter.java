package jp.kuluna.eventgridview.util;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by Qi Xingchen on 2018-7-14.
 */
public class DataBindingAdapter {
    /**
     * set view height using data binding
     */
    @BindingAdapter({"layout_height"})
    public static void setHeight(View view, int height) {
        view.getLayoutParams().height = Math.round(height * view.getContext().getResources().getDisplayMetrics().density + 0.5f);
    }
}
