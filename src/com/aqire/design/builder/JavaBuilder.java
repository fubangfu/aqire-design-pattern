package com.aqire.design.builder;

/**
 * 建造者模式
 *
 * @author fubangfu2015@163.com
 * @date 2020/5/21 14:57
 * --------------------------------------------
 */

// 生成器接口声明了创建产品对象不同部件的方法。
interface Builder {

    void setType(Type type);

    void setEngine(Engine engine);

    void setGPS(GPSNavigator gps);
}

// 产品特征 1
enum Type {
    CITY_CAR, SPORTS_CAR, SUV
}

// 产品特征 2
class Engine {
    private final double volume;
    private double mileage;
    private boolean started;

    public Engine(double volume, double mileage) {
        this.volume = volume;
        this.mileage = mileage;
    }

    public void on() {
        started = true;
    }

    public void off() {
        started = false;
    }

    public boolean isStarted() {
        return started;
    }

    public void go(double mileage) {
        if (started) {
            this.mileage += mileage;
        } else {
            System.err.println("Cannot go(), you must start engine first!");
        }
    }

    public double getVolume() {
        return volume;
    }

    public double getMileage() {
        return mileage;
    }
}

// 产品特征 3
class GPSNavigator {
    private final String route;

    public GPSNavigator() {
        this.route = "221b, Baker Street, London  to Scotland Yard, 8-10 Broadway, London";
    }

    public GPSNavigator(String manualRoute) {
        this.route = manualRoute;
    }

    public String getRoute() {
        return route;
    }
}

// 只有当产品较为复杂且需要详细配置时，使用生成器模式才有意义。下面的两个
// 产品尽管没有同样的接口，但却相互关联。
class Car {
    // 一辆汽车可能配备有 GPS 设备、行车电脑和几个座位。不同型号的汽车（
    // 运动型轿车、SUV 和敞篷车）可能会安装或启用不同的功能。
    private Type type;
    private GPSNavigator gps;
    private Engine engine;

    public Car(Type type, Engine engine, GPSNavigator gpsNavigator) {
        this.type = type;
        this.engine = engine;
        this.gps = gpsNavigator;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public GPSNavigator getGps() {
        return gps;
    }

    public void setGps(GPSNavigator gps) {
        this.gps = gps;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}

class Manual {
    // 用户使用手册应该根据汽车配置进行编制，并介绍汽车的所有功能。
    private final Type type;
    private final GPSNavigator gps;
    private final Engine engine;

    public Manual(Type type, GPSNavigator gps, Engine engine) {
        this.type = type;
        this.gps = gps;
        this.engine = engine;
    }

    public String print() {
        String info = "";
        info += "Type of car: " + type + "\n";
        info += "Engine: volume - " + engine.getVolume() + "; mileage - " + engine.getMileage() + "\n";
        if (this.gps != null) {
            info += "GPS Navigator: Functional" + "\n";
        } else {
            info += "GPS Navigator: N/A" + "\n";
        }
        return info;
    }
}

// 具体生成器类将遵循生成器接口并提供生成步骤的具体实现。你的程序中可能会
// 有多个以不同方式实现的生成器变体。
class CarBuilder implements Builder {
    private Type type;
    private GPSNavigator gps;
    private Engine engine;

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void setGPS(GPSNavigator gps) {
        this.gps = gps;
    }

    // 具体生成器需要自行提供获取结果的方法。这是因为不同类型的生成器可能
    // 会创建不遵循相同接口的、完全不同的产品。所以也就无法在生成器接口中
    // 声明这些方法（至少在静态类型的编程语言中是这样的）。
    public Car build() {
        return new Car(type, engine, gps);
    }
}

// 生成器与其他创建型模式的不同之处在于：它让你能创建不遵循相同接口的产品。
class CarManualBuilder implements Builder {
    private Type type;
    private GPSNavigator gps;
    private Engine engine;

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void setGPS(GPSNavigator gps) {
        this.gps = gps;
    }

    // 具体生成器需要自行提供获取结果的方法。这是因为不同类型的生成器可能
    // 会创建不遵循相同接口的、完全不同的产品。所以也就无法在生成器接口中
    // 声明这些方法（至少在静态类型的编程语言中是这样的）。
    public Manual build() {
        return new Manual(type, gps, engine);
    }
}


// 主管只负责按照特定顺序执行生成步骤。其在根据特定步骤或配置来生成产品时
// 会很有帮助。由于客户端可以直接控制生成器，所以严格意义上来说，主管类并
// 不是必需的。
class Director {

    public void constructSportsCar(Builder builder) {
        builder.setType(Type.SPORTS_CAR);
        builder.setEngine(new Engine(3.0, 0));
        builder.setGPS(new GPSNavigator());
    }

    public void constructCityCar(Builder builder) {
        builder.setType(Type.CITY_CAR);
        builder.setEngine(new Engine(1.2, 0));
    }

    public void constructSUV(Builder builder) {
        builder.setType(Type.SUV);
        builder.setEngine(new Engine(2.5, 0));
        builder.setGPS(new GPSNavigator());
    }
}

public class JavaBuilder {
    public static void main(String[] args) {
        Director director = new Director();
        //
        CarBuilder carBuilder = new CarBuilder();
        director.constructSportsCar(carBuilder);
        Car car = carBuilder.build();

        CarManualBuilder carManualBuilder = new CarManualBuilder();
        director.constructSportsCar(carManualBuilder);
        // 最终产品通常需要从生成器对象中获取，因为主管不知晓具体生成器和
        // 产品的存在，也不会对其产生依赖。
        Manual carManual = carManualBuilder.build();

        //
        System.out.println("Car built:\n" + car.getType());
        System.out.println("\nCar manual built:\n" + carManual.print());
    }
}
