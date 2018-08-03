package com.aengussong.movies.network.callback;

import com.aengussong.movies.network.errorResponse.NetworkError;
import com.aengussong.movies.network.response.BaseResponse;

public interface NetworkCallback<T> {

    void onSuccess(T response);

    void onError(NetworkError error);
}
