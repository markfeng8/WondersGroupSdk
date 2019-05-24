/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.utils;

import android.support.annotation.NonNull;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by x-sir on 2019-05-24 :)
 * Function:RxJava2.x View 点击事件封装
 */
public class RxUtils {

    public static Observable<String> clickView(@NonNull View view) {
        checkNoNull(view);
        return Observable.create(new ViewClickOnSubscribe(view));
    }

    private static <T> void checkNoNull(T value) {
        if (value == null) {
            throw new NullPointerException("object is null!");
        }
    }

    private static class ViewClickOnSubscribe implements ObservableOnSubscribe<String> {

        private View view;

        ViewClickOnSubscribe(View view) {
            this.view = view;
        }

        @Override
        public void subscribe(ObservableEmitter<String> emitter) {
            View.OnClickListener onClickListener = v -> {
                if (!emitter.isDisposed()) {
                    emitter.onNext("OK");
                }
            };
            view.setOnClickListener(onClickListener);
        }
    }
}
