package com.example.mvp.interfaces;

import android.provider.ContactsContract;

import com.example.mvp.model.User;

public interface RegisterInterface {
    interface View {
        void registerSuccess();

        void registerError();

        void registerEmailExit();

        void registerRePasswordError();
    }

    interface Presenter {
        void handleRegister(User user, String rePassword);
    }
}
