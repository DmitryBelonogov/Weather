package com.nougust3.weather.Data.Source.Remote.Interceptors;

import com.nougust3.weather.Data.Source.Remote.Exceptions.NoNetworkException;
import com.nougust3.weather.Data.Source.Remote.NetworkChecker;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkCheckInterceptor implements Interceptor {

    private NetworkChecker networkChecker;

    public NetworkCheckInterceptor(NetworkChecker networkChecker) {
        this.networkChecker = networkChecker;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        if (!networkChecker.isConnected()) {
            throw new NoNetworkException("No network connection");
        }

        try {
            return chain.proceed(requestBuilder.build());
        } catch (SocketTimeoutException | UnknownHostException e) {
            throw new NoNetworkException();
        }

    }

}