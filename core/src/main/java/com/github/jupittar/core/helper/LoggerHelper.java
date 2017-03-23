package com.github.jupittar.core.helper;

@SuppressWarnings("unused")
public interface LoggerHelper {

  void t(int methodCount);

  void i(String message, Object... args);

  void v(String message, Object... args);

  void d(String message, Object... args);

  void e(String message, Object... args);

  void e(Throwable throwable, String message, Object... args);

  void json(String json);

}
