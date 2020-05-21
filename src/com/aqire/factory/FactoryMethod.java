package com.aqire.factory;

/**
 * 工厂方法模式
 *
 * @author fubangfu2015@163.com
 * @date 2020/5/21 10:23
 * --------------------------------------------
 */

interface Button {
    void onClick();

    void render();
}

// 创建者类声明的工厂方法必须返回一个产品类的对象。创建者的子类通常会提供
// 该方法的实现。
abstract class Dialog {

    // 创建者还可提供一些工厂方法的默认实现。
    abstract Button createButton();

    // 请注意，创建者的主要职责并非是创建产品。其中通常会包含一些核心业务
    // 逻辑，这些逻辑依赖于由工厂方法返回的产品对象。子类可通过重写工厂方
    // 法并使其返回不同类型的产品来间接修改业务逻辑。
    public void render() {
        // 调用工厂方法创建一个产品对象。
        Button button = createButton();
        // 现在使用产品。
        button.onClick();
        button.render();
    }
}

// 具体产品需提供产品接口的各种实现。
class WindowButton implements Button {

    // 绑定本地操作系统点击事件。
    @Override
    public void onClick() {
        System.out.println("Window button is clicked.");
    }

    // 根据 Windows 样式渲染按钮。
    @Override
    public void render() {
        System.out.println("Window button is rendered.");
    }
}

class WebButton implements Button {

    @Override
    public void onClick() {
        System.out.println("Web button is clicked.");
    }

    @Override
    public void render() {
        System.out.println("Web button is rendered.");
    }
}


// 具体创建者将重写工厂方法以改变其所返回的产品类型。
class WindowDialog extends Dialog {
    @Override
    Button createButton() {
        return new WindowButton();
    }
}

class WebDialog extends Dialog {

    @Override
    Button createButton() {
        return new WebButton();
    }
}


class Application {
    private Dialog dialog;

    private Application init(String os) throws Exception {
        if ("window".equals(os)) {
            dialog = new WindowDialog();
        } else if ("web".equals(os)) {
            dialog = new WebDialog();
        } else {
            throw new Exception("错误！未知的操作系统。");
        }
        return this;
    }

    // 当前客户端代码会与具体创建者的实例进行交互，但是必须通过其基本接口
    // 进行。只要客户端通过基本接口与创建者进行交互，你就可将任何创建者子
    // 类传递给客户端。
    public void main(String os) {
        try {
            this.init(os).dialog.render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


public class FactoryMethod {
    public static void main(String[] args) {
        new Application().main(args.length == 0 ? "window" : "web");
    }
}
