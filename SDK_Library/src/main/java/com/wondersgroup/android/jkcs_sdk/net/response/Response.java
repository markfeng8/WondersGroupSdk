/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.net.response;

/**
 * Created by x-sir on 2019/3/29 :)
 * Function:Base response model.
 */
public class Response<E> {

    public boolean success;
    public String message;
    public String token;
    public E body;

    public Response(Response r) {
        this.success = r.success;
        this.message = r.message;
    }
}
