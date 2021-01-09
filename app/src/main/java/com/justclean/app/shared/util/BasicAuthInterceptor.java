/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class BasicAuthInterceptor implements Interceptor {
    private String credentials;


    public BasicAuthInterceptor(String user, String password) {
        this.credentials = Credentials.basic(user, password);

    }

    private String uisession = "";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).addHeader("Cookie", uisession).build();
        Response response = chain.proceed(authenticatedRequest);
        String temp = "";
        if (response.header("Set-Cookie") != null) {
            temp = response.header("Set-Cookie").split(";")[0];
        }
        if (temp.startsWith("UISESSION")) {
            uisession = temp;
        }
        return response;
    }
}