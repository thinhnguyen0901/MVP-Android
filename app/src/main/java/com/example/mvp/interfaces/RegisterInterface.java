package com.example.mvp.interfaces;

import android.provider.ContactsContract;

import com.example.mvp.model.User;

public interface RegisterInterface {
    interface View {
        void registerSuccess();

        void registerEmailError();

        void registerPasswordError();

        void registerEmailExit();

        void registerError();

        void registerRePasswordError();
    }

    interface Presenter {
        void handleRegister(User user, String rePassword);
    }
}
