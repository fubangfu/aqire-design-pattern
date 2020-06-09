package com.aqire.design.creation.single;

/**
 * 单例模式
 *
 * @author fubangfu2015@163.com
 * @date 2020/6/9 10:51
 * --------------------------------------------
 */

// 懒汉，线程安全，调用效率不高，但是能延时加载
class LazyMan {
    private static LazyMan instance;

    private LazyMan() {
    }

    public static synchronized LazyMan getInstance() {
        if (instance == null) {
            instance = new LazyMan();
        }
        return instance;
    }
}

// 双重校验锁，由于JVM底层模型原因，偶尔会出问题，不建议使用
class DoubleLock {
    private volatile static DoubleLock singleton;

    private DoubleLock() {
    }

    public static DoubleLock getInstance() {
        if (singleton == null) {
            synchronized (DoubleLock.class) {
                if (singleton == null) {
                    singleton = new DoubleLock();
                }
            }
        }
        return singleton;
    }
}

// 饿汉，线程安全，调用效率高，但是不能延时加载
class Hungry {
    private static final Hungry instance = new Hungry();

    private Hungry() {
    }

    public static Hungry getInstance() {
        return instance;
    }
}

// 静态内部类，线程安全，调用效率高，可以延时加载
class StaticInside {
    private static class SingletonHolder {
        private static final StaticInside INSTANCE = new StaticInside();
    }

    private StaticInside() {
    }

    public static StaticInside getInstance() {
        return SingletonHolder.INSTANCE;
    }
}


// 枚举，线程安全，调用效率高，不能延时加载，可以天然的防止反射和反序列化调用
class EnumSingle {
    //私有化构造函数
    private EnumSingle() {
    }

    //定义一个静态枚举类
    static enum SingletonEnum {
        //创建一个枚举对象，该对象天生为单例
        INSTANCE;
        private EnumSingle enumSingle;

        //私有化枚举的构造函数
        private SingletonEnum() {
            enumSingle = new EnumSingle();
        }

        public EnumSingle getInstance() {
            return enumSingle;
        }
    }

    //对外暴露一个获取User对象的静态方法
    public static EnumSingle getInstance() {
        return SingletonEnum.INSTANCE.getInstance();
    }
}

public class JavaSingle {
    public static void main(String[] args) {
        System.out.println(LazyMan.getInstance() == LazyMan.getInstance());
        System.out.println(DoubleLock.getInstance() == DoubleLock.getInstance());
        System.out.println(Hungry.getInstance() == Hungry.getInstance());
        System.out.println(StaticInside.getInstance() == StaticInside.getInstance());
        System.out.println(EnumSingle.getInstance() == EnumSingle.getInstance());
    }
}
