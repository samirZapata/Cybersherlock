package com.usbbog.cbs_app.networking;

import androidx.compose.runtime.WeakReference;

import com.usbbog.cbs_app.view.OlvidarContrasena;

import java.io.Serializable;

public abstract class OnDatabaseQueryCallback implements Serializable {
    private final WeakReference<OlvidarContrasena> weakActivity;

    public OnDatabaseQueryCallback(OlvidarContrasena activity) {
        weakActivity = new WeakReference<>(activity);
    }

    public OlvidarContrasena getActivity() {
        return weakActivity.get();
    }
}
