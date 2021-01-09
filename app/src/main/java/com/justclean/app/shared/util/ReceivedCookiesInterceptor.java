/*
 * Created by  Muhammad Sayed  on 1/9/21 11:11 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashSet;

public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                System.out.println(header);
                cookies.add(header);
            }
        }

        return originalResponse;
    }
}