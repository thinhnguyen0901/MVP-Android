package com.example.mvp.interfaces;

import com.example.mvp.model.User;

public interface LoginInterface {
    interface View {
        void loginSuccess();

        void loginError();

        void loginEmailError();

        void loginPasswordError();
    }

    interface Presenter {
        void handleSignIn(User user);
    }

}
