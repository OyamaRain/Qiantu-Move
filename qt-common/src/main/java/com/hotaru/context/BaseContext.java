package com.hotaru.context;

// ThreadLocal --- 用于保存当前登录用户的id
public class BaseContext {

    private static final ThreadLocal<Long> currentId = new ThreadLocal<>();
    private static final ThreadLocal<String> currentName = new ThreadLocal<>();

    private BaseContext() {
        // 防止被 new
    }

    public static void setCurrentId(Long id) {
        currentId.set(id);
    }

    public static Long getCurrentId() {
        return currentId.get();
    }

    public static void setCurrentName(String name) {
        currentName.set(name);
    }

    public static String getCurrentName() {
        return currentName.get();
    }


     //统一清理 ThreadLocal
    public static void clear() {
        currentId.remove();
        currentName.remove();
    }

}
