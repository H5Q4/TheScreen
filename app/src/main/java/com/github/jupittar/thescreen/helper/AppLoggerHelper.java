package com.github.jupittar.thescreen.helper;

import com.github.jupittar.core.helper.LoggerHelper;
import com.orhanobut.logger.Logger;

@SuppressWarnings("unused")
public class AppLoggerHelper implements LoggerHelper {

    private int mMethodCount;

    @Override
    public void t(int methodCount) {
        mMethodCount = methodCount;
    }

    @Override
    public void i(String message, Object... args) {
        Logger.t(mMethodCount).i(message, args);
    }

    @Override
    public void v(String message, Object... args) {
        Logger.t(mMethodCount).v(message, args);
    }

    @Override
    public void d(String message, Object... args) {
        Logger.t(mMethodCount).d(message, args);
    }

    @Override
    public void e(String message, Object... args) {
        Logger.t(mMethodCount).e(message, args);
    }

    @Override
    public void e(Throwable throwable, String message, Object... args) {
        Logger.t(mMethodCount).e(throwable, message, args);
    }

    @Override
    public void json(String json) {
        Logger.t(mMethodCount).json(json);
    }

}
