package com.project.jarjamediaapp.Networking;

public interface CallbackInterface<T> {
    void onSuccess(T response);
    void onError(Throwable throwable);
}
