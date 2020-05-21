package com.aqire.abs;

/**
 * 抽象工厂模式
 *
 * @author fubangfu2015@163.com
 * @date 2020/5/21 13:24
 * --------------------------------------------
 */

// 系列产品中的特定产品必须有一个基础接口。所有产品变体都必须实现这个接口。
interface Button {
    void paint();
}

// 具体产品由相应的具体工厂创建。
class WinButton implements Button {
    // 根据 Windows 样式渲染按钮。
    @Override
    public void paint() {
        System.out.println("Window Button is rendered.");
    }
}

class MacButton implements Button {
    // 根据 macOS 样式渲染按钮
    @Override
    public void paint() {
        System.out.println("Mac Button is rendered.");
    }
}


// 这是另一个产品的基础接口。所有产品都可以互动，但是只有相同具体变体的产
// 品之间才能够正确地进行交互。
interface Checkbox {
    void paint();
}


class WinCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Window Checkbox is rendered.");
    }
}

class MacCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Mac Checkbox is rendered.");
    }
}

// 抽象工厂接口声明了一组能返回不同抽象产品的方法。这些产品属于同一个系列
// 且在高层主题或概念上具有相关性。同系列的产品通常能相互搭配使用。系列产
// 品可有多个变体，但不同变体的产品不能搭配使用。
interface GUIFactory {
    Button createButton();

    Checkbox createCheckbox();
}

// 具体工厂可生成属于同一变体的系列产品。工厂会确保其创建的产品能相互搭配
// 使用。具体工厂方法签名会返回一个抽象产品，但在方法内部则会对具体产品进
// 行实例化。
class WinFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new WinButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WinCheckbox();
    }
}


// 每个具体工厂中都会包含一个相应的产品变体。
class MacFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}

// 客户端代码仅通过抽象类型（GUIFactory、Button 和 Checkbox）使用工厂
// 和产品。这让你无需修改任何工厂或产品子类就能将其传递给客户端代码。
class Application {
    private GUIFactory factory;
    private Button button;

    public Application(GUIFactory factory) {
        this.factory = factory;
    }

    public Application createUI() {
        this.button = this.factory.createButton();
        return this;
    }

    public void paint() {
        this.button.paint();
    }
}

// 程序会根据当前配置或环境设定选择工厂类型，并在运行时创建工厂（通常在初
// 始化阶段）。
class ApplicationConfigurator {
    public void main(String os) throws Exception {
        GUIFactory factory;
        if ("window".equals(os)) {
            factory = new WinFactory();
        } else if ("mac".equals(os)) {
            factory = new MacFactory();
        } else {
            throw new Exception("错误！未知的操作系统。");
        }
        new Application(factory).createUI().paint();
    }
}


public class AbstractFactory {
    public static void main(String[] args) {
        try {
            new ApplicationConfigurator().main("window");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
