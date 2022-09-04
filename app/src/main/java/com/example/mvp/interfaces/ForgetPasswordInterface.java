package com.example.mvp.interfaces;

import com.example.mvp.model.User;

public interface ForgetPasswordInterface {
    interface View {
        void emailError();
        void sendSuccess();
    }

    interface Presenter {
        boolean handleForgetPassword(User user);
    }
}
