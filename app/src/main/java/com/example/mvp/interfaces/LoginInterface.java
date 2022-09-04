package com.example.mvp.interfaces;

import com.example.mvp.model.User;

public interface LoginInterface {
    interface View {
        void loginSuccess();

        void loginPasswordError();

        void loginEmailError();

        void loginNotEmailExit();

        void loginError();

        void loginPasswordWrong();
    }

    interface Presenter {
        void handleSignIn(User user);
    }

}
