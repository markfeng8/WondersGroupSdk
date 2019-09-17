/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.net.callback;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by x-sir on 2019-06-20 :)
 * Function:抽象网络请求的回调
 */
public abstract class AbstractSubscriber<T> extends ResourceSubscriber<T> {

    @Override
    public void onComplete() {

    }
}
