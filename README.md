# 设计模式

什么是设计模式吗？

> 维基百科是这样定义的，在软件工程中，设计模式是对软件设计中普遍存在（反复出现）的各种问题，所提出的解决方案。

即使刚学习编程时间不久，其实也是会见到过一些设计模式，因为这些设计模式在 JDK 、框架和各种类库中普遍存在，学习设计模式有助于更好的理解平时遇到的一些用法。

设计模式因软件开发领域的四位大佬（英语，Gang of Four，简称GoF）合作出版了《设计模式：可复用面向对象的基础》而出名，书中提到了 23 种设计模式，是最为经典的设计模式，分为三类：创建型、结构型和行为型。以下主要是介绍这 23 种设计模式。

先介绍一下面向对象 SOLID 原则，设计模式也是围绕着这些原则来解决特定的问题。

- S - 单一职责（Single Responsibility Principle），一个类负责一项职责
- O - 开闭原则（Open/Close Principle），对扩展开放，对修改关闭
- L - 里氏替换（Liskov Substitution Principle），子类对象可以替换父类对象，并且程序行为保持不变
- I - 接口隔离（Interface Segregation Principle），接口应该保持简单最小化，客户端不应该依赖它不需要的接口
- D - 依赖倒置（Dependence Inversion Principle），高层模块不依赖于低层模块，二者都依赖于抽象。抽象不依赖细节，细节依赖于抽象

还有一些其他的原则，比如组合优于继承、最少知道原则等。

## 创建型

创建型模式就是用来创建一个对象，我们定义一个类给出创建类实例的方法，方便客户端使用。

### 简单工厂模式

提供一个简单方法快速创建对象，客户端不用关注创建的细节。

``` java
public class Main {
    public static void main(String[] args) {
        Phone iphone = PhoneFactory.makePhone("苹果");
        iphone.call("乔布斯");
        Phone phone = PhoneFactory.makePhone("华为");
        phone.call("余承东");
    }
}

class PhoneFactory {
    public static Phone makePhone(String name) {
        if ("苹果".equals(name)) {
            return new ApplePhone();
        } else if ("华为".equals(name)) {
            return new HuaweiPhone();
        } else {
            return null;
        }
    }
}

interface Phone {
    void call(String name);
}

class ApplePhone implements Phone {
    @Override
    public void call(String name) {
        System.out.println("正在使用苹果手机打给" + name);
    }
}

class HuaweiPhone implements Phone {
    @Override
    public void call(String name) {
        System.out.println("正在使用华为手机打给" + name);
    }
}
```

工厂提供一个静态类，根据参数返回初始化的对象（共同父类或者实现同一接口）的实例。

### 工厂方法模式

引入工厂模式往往需要两个或以上的工厂，以便于根据不同工厂生成不同的产品。

``` java
public class Main {
    public static void main(String[] args) {
        AppleFactory appleFactory = new AppleFactory();
        Phone iphone16 = appleFactory.makePhone("16");
        iphone16.call("乔布斯");
        Phone iphone15 = appleFactory.makePhone("15");
        iphone15.call("乔布斯");

        Phone mateXT = new HuaweiFactory().makePhone("三折屏");
        mateXT.call("余承东");
    }
}

interface Factory {
    Phone makePhone(String name);
}

class AppleFactory implements Factory {

    @Override
    public Phone makePhone(String name) {
        if ("16".equals(name)) {
            return new Iphone16();
        } else if ("15".equals(name)) {
            return new Iphone15();
        }
        return null;
    }
}

class HuaweiFactory implements Factory {

    @Override
    public Phone makePhone(String name) {
        if ("三折屏".equals(name)) {
            return new MateXT();
        } else if ("60".equals(name)) {
            return new Mate60();
        }
        return null;
    }
}

interface Phone {
    void call(String name);
}

class Iphone16 implements Phone {

    @Override
    public void call(String name) {
        System.out.println("使用 iphone 16 打给" + name);
    }
}

class Iphone15 implements Phone {

    @Override
    public void call(String name) {
        System.out.println("使用 iphone 15 打给" + name);
    }
}

class MateXT implements Phone {

    @Override
    public void call(String name) {
        System.out.println("使用 Mate XT 三折屏打给" + name);
    }
}

class Mate60 implements Phone {

    @Override
    public void call(String name) {
        System.out.println("使用 Mate 60 打给" + name);
    }
}
```

不同的工厂生产的出来的产品不同，第一步先选定工厂，第二步基本和简单工厂相似。苹果的工厂只生产苹果的产品，华为的工厂生产华为的产品。

### 抽象工厂模式

当涉及到**产品族**的时候，就需要引入抽象工厂模式了。

一个经典的例子是造一台电脑。我们先不引入抽象工厂模式，看看怎么实现。

因为电脑是由许多的构件组成的，我们将 CPU 和主板进行抽象，然后 CPU 由 CPUFactory 生产，主板由 MainBoardFactory 生产，然后，我们再将 CPU 和主板搭配起来组合在一起。

这个时候的客户端调用是这样的：

```java
// 得到 Intel 的 CPU
CPUFactory cpuFactory = new IntelCPUFactory();
CPU cpu = intelCPUFactory.makeCPU();

// 得到 AMD 的主板
MainBoardFactory mainBoardFactory = new AmdMainBoardFactory();
MainBoard mainBoard = mainBoardFactory.make();

// 组装 CPU 和主板
Computer computer = new Computer(cpu, mainBoard);
```

单独看 CPU 工厂和主板工厂，它们分别是前面我们说的**工厂模式**。这种方式也容易扩展，因为要给电脑加硬盘的话，只需要加一个 HardDiskFactory 和相应的实现即可，不需要修改现有的工厂。

但是，这种方式有一个问题，那就是如果 **Intel 家产的 CPU 和 AMD 产的主板不能兼容使用**，那么这代码就容易出错，因为客户端并不知道它们不兼容，也就会错误地出现随意组合。

当涉及到这种产品族的问题的时候，就需要抽象工厂模式来支持了。我们不再定义 CPU 工厂、主板工厂、硬盘工厂、显示屏工厂等等，我们直接定义电脑工厂，每个电脑工厂负责生产所有的设备，这样能保证肯定不存在兼容问题。

这个时候，对于客户端来说，不再需要单独挑选 CPU厂商、主板厂商、硬盘厂商等，直接选择一家品牌工厂，品牌工厂会负责生产所有的东西，而且能保证肯定是兼容可用的。

``` java
public class Main {
    public static void main(String[] args) {
        InterFactory interFactory = new InterFactory();
        Computer computer = new Computer(interFactory);
    }
}

class Computer {
    CPU cpu;
    MainBroad mainBroad;
    HardDisk hardDisk;
    public Computer(Factory factory) {
        this.cpu = factory.makeCPU();
        this.mainBroad = factory.makeMainBroad();
        this.hardDisk = factory.makeHardDisk();
    }

    public void info() {
        System.out.println(cpu.getClass().getName() + "\n" + mainBroad.getClass().getName() + "\n" + hardDisk.getClass().getName());
    }
}


interface Factory {
    CPU makeCPU();
    MainBroad makeMainBroad();
    HardDisk makeHardDisk();
}

class InterFactory implements Factory {

    @Override
    public CPU makeCPU() {
        return new InterCPU();
    }

    @Override
    public MainBroad makeMainBroad() {
        return new InterMainBroad();
    }

    @Override
    public HardDisk makeHardDisk() {
        return new InterHardDisk();
    }
}

class AmdFactory implements Factory {

    @Override
    public CPU makeCPU() {
        return new AmdCPU();
    }

    @Override
    public MainBroad makeMainBroad() {
        return new AmdMainBroad();
    }

    @Override
    public HardDisk makeHardDisk() {
        return new AmdHardDist();
    }
}

interface CPU {}
class InterCPU implements CPU {}
class AmdCPU implements CPU {}

interface MainBroad {}
class InterMainBroad implements MainBroad {}
class AmdMainBroad implements MainBroad {}

interface HardDisk {}
class InterHardDisk implements HardDisk {}
class AmdHardDist implements HardDisk {}
```

抽象工厂的问题也是显而易见的，比如我们要加个显示器，就需要修改所有的工厂，给所有的工厂都加上制造显示器的方法。这有点违反了**对修改关闭，对扩展开放**这个设计原则。

### 单例模式

#### 饿汉单例

``` java
public class Singleton {
    // 静态变量，存储实例
    private static Singleton instance;

    // 私有化构造函数，避免外部实例化
    private Singleton() {}

    // 提供全局访问点，使用时才创建实例
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

#### 懒汉单例

双重检测才能保证多线程安全，注意变量 volatile 关键字修饰保证可见性。

``` java
public class Singleton {
    // volatile 关键字确保 instance 变量的可见性
    private static volatile Singleton instance;

    // 私有化构造函数，避免外部实例化
    private Singleton() {}

    // 提供全局访问点
    public static Singleton getInstance() {
        if (instance == null) {  // 第一次检查
            synchronized (Singleton.class) {
                if (instance == null) {  // 第二次检查
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

#### 静态内部类

利用 Java 类加载机制的特性，可以通过静态内部类来实现单例模式，既实现了延迟加载，又保证了线程安全，推荐使用。

``` java
public class Singleton {
    // 私有化构造函数
    private Singleton() {}

    // 静态内部类
    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    // 提供全局访问点
    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
```

### 建造者模式

参数比较多时，采用链式调用的方式创建对象比较优雅。一般使用 `Lombok` 的 `@Builder`注解生成，不要要注意这个注解会把对象构造方法变成私有，如果还需要 `new` 创建对象记得添加 `@NoArgsConstructor`

``` java
public class Main {
    public static void main(String[] args) {
        User user = User.builder().name("jack").age(12).email("xxx@emial.com").address("shanghai").build();
        System.out.println(user);
    }
}

class User {
    private String name;
    private Integer age;
    private String email;
    private String address;

    private User(String name, Integer age, String email, String address) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private Integer age;
        private String email;
        private String address;

        private Builder() {}

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(name, age, email, address);
        }
    }

    @Override
    public String toString() {
        return "name = " + name + ", age = " + age + ", email = " + email + ", address = " + address;
    }

}
```

### 原型模式

原型基于实例克隆一个相同的对象，Java Object 类提供了 clone() 方法，不过需要类实现 Cloneable 接口，这是一个空接口。注意，clone 方法是浅拷贝，深拷贝需要自己重写此方法。

### 创建型总结

创建型总体比较简单，它的目的就是创建对象，面向对象语言第一步就要先创建对象实例。

简单工厂通过传入值简单获得创建对象，工厂方法在简单工厂的基础上增加了产品的维度，抽象工厂提供一簇产品，保证了一簇产品的兼容性。单例节约成本全局唯一实例，复用对象。建造者模式适合创建多个参数的对象。原型主要用于克隆对象。

## 结构型

### 代理模式

代理模式通常用增强一个类的功能，保持相同的方法，Spring AOP 就是通过动态代理实现切面功能。

``` java
public class Main {
    public static void main(String[] args) {
        PhoneProxy phoneProxy = new PhoneProxy();
        phoneProxy.makePhone();
    }
}

interface PhoneService {
    void makePhone();
}

class PhoneServiceImpl implements PhoneService {

    @Override
    public void makePhone() {
        System.out.println("制作手机");
    }
}

class PhoneProxy implements PhoneService {
    // 被代理的真实对象，也可以通过构造方法传入
    PhoneService phoneService = new PhoneServiceImpl();

    @Override
    public void makePhone() {
        // 代理前处理
        System.out.println("以旧换新");
        phoneService.makePhone();
        // 代理后处理
        System.out.println("给手机赠送个贴膜");
    }
}
```

### 适配器模式

适配器就是有个接口要实现，现在不满足，需要增加一层适配器进行适配。

#### 对象适配器

有两个不同的类型猫和狗，它们两个的叫声不同，将猫适配成狗，这样猫也能够当成狗用，不过仍然还是猫的叫声。

先定义猫和狗的接口以及实现类

``` java
interface Cat {
    void meow();
}

interface Dog {
    void bark();
}

class HelloKitty implements Cat {

    @Override
    public void meow() {
        System.out.println("喵喵喵");
    }
}

class Husky implements Dog {

    @Override
    public void bark() {
        System.out.println("汪汪汪");
    }
}

```

使用猫来适配狗的方法，让猫扮演狗

``` java
class CatAdapter implements Dog {
    Cat cat;

    public CatAdapter(Cat cat) {
        this.cat = cat;
    }

    @Override
    public void bark() {
        cat.meow();
    }
}
```

客户端调用

``` java
public class InterfaceAdapter {
    public static void main(String[] args) {
        CatAdapter catAdapter = new CatAdapter(new HelloKitty());
        catAdapter.bark();
    }
}
```

#### 类适配器

类适配器基于继承一个已经有的类，复用此类的方法来适配目标

``` java
public class ClassAdapter {
    public static void main(String[] args) {
        ServiceAdapter adapter = new ServiceAdapter();
        adapter.methodA();
        adapter.methodB();
    }
}

class Service {
    public void methodA() {
        System.out.println("method A");
    }
}

interface Target {
    void methodA();
    void methodB();
}

class ServiceAdapter extends Service implements Target {

    @Override
    public void methodB() {
        System.out.println("实现方法 B");
    }
}
```

### 装饰模式

装饰模式有点类似于套娃，目的是起到装饰的作用。在 Java IO 中有广泛的应用，以 `FilterInputStream`、`FilterOutputStream ` 为基础的各种实现类 `BufferedInputStream`、`DataInputStream` 等。

定义一个数据写入和读取的接口

```java
interface DataSource {
    void write(String content);
    String read();
}
```

实现该类

``` java
class FileDataSource implements DataSource {
    private String content;

    @Override
    public void write(String content) {
        this.content = content;
    }

    @Override
    public String read() {
        return content;
    }
}
```

编写装饰类基类及其子类，需要装饰的类通过继承基类来进行装饰

``` java
class DataSourceDecorator implements DataSource {
    private DataSource dataSource;

    public DataSourceDecorator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void write(String content) {
        dataSource.write(content);
    }

    @Override
    public String read() {
        return dataSource.read();
    }
}

class CompressDataSourceDecorator extends DataSourceDecorator {

    public CompressDataSourceDecorator(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void write(String content) {
        super.write(compress(content));
    }

    @Override
    public String read() {
        return deCompress(super.read());
    }

    private String compress(String content) {
        return content + "compressed";
    }

    private String deCompress(String content) {
        return content.replace("compressed", "");
    }
}
```

客户端调用

```java
public class Main {
    public static void main(String[] args) {
        CompressDataSourceDecorator compressDataSourceDecorator = new CompressDataSourceDecorator(new DataSourceDecorator(new FileDataSource()));
        compressDataSourceDecorator.write("hhhh");
        System.out.println(compressDataSourceDecorator.read());
    }
}
```

这里的 `DataSourceDecorator` 就相当于 `FilterInputStream` 的作用，在基础装饰类通过 `extends` 继承可以扩展无数装饰类。

### 桥接模式

桥接模式就是抽象的分离，共同依赖接口。

首先定义一个接口

``` java
interface Draw {
    void draw(int radius, int x, int y);
}
```

接口的实现类

``` java
class RedPen implements Draw {

    @Override
    public void draw(int radius, int x, int y) {
        System.out.println("使用红色画笔" + " radius: " + radius + ", x: " + ", y: " + y);
    }
}

class GreenPen implements Draw {

    @Override
    public void draw(int radius, int x, int y) {
        System.out.println("使用绿色画笔" + " radius: " + radius + ", x: " + ", y: " + y);
    }
}

class BluePen implements Draw {

    @Override
    public void draw(int radius, int x, int y) {
        System.out.println("使用蓝色画笔" + " radius: " + radius + ", x: " + ", y: " + y);
    }
}
```

定义一个抽象类，使用桥接的接口 Draw

``` java
abstract class Shape {
    protected Draw draw;

    public Shape(Draw draw) {
        this.draw = draw;
    }

    public abstract void draw();
}
```

抽象类的实现类

```java
class Circle extends Shape {
    private int radius;

    public Circle(Draw draw, int radius) {
        super(draw);
        this.radius = radius;
    }

    @Override
    public void draw() {
        draw.draw(radius, 0, 0);
    }
}

class Ractangle extends Shape {
    private int x, y;

    public Ractangle(Draw draw, int x, int y) {
        super(draw);
    }

    @Override
    public void draw() {
        draw.draw(0, x, y);
    }
}
```

这样就通过接口和抽象达到了解耦，依赖抽象。

### 组合模式

组合模式用于表示有层次结构的数据，比如部门、文件夹等，使得对单个对象和多个组合具有一致的访问性。

先定义一个描述文件的接口，包含文件和文件夹的一些方法

``` java
interface FileComposite {
    default void add(FileComposite fileComposite) { throw new UnsupportedOperationException();}
    default void remove(FileComposite fileComposite) { throw new UnsupportedOperationException();}
    default FileComposite getChildren(int i) { throw new UnsupportedOperationException();}
    String getName();
    void display();
}
```

分别实现文件和文件夹的功能

``` java
class File implements FileComposite {
    private String fileName;

    public File(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public void display() {
        System.out.println("File: " + fileName);
    }
}

class Folder implements FileComposite {
    private List<FileComposite> children;
    private String fileName;

    public Folder(String fileName) {
        this.fileName = fileName;
        children = new ArrayList<>();
    }

    @Override
    public void add(FileComposite fileComposite) {
        children.add(fileComposite);
    }

    @Override
    public void remove(FileComposite fileComposite) {
        children.remove(fileComposite);
    }

    @Override
    public FileComposite getChildren(int i) {
        return children.get(i);
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public void display() {
        System.out.println("Folder: " + fileName);
        if (!children.isEmpty()) {
            children.forEach(FileComposite::display);
        }
    }
}
```

客户端的调用

``` java
public class Main {
    public static void main(String[] args) {
        File fileA = new File("fileA");
        File fileB = new File("fileB");
        File fileC = new File("fileC");
        File fileD = new File("fileD");
        Folder folder1 = new Folder("folder1");
        Folder folder2 = new Folder("folder2");
        folder1.add(fileA);
        folder1.add(fileB);
        folder2.add(fileC);
        folder2.add(fileD);
        folder1.add(folder2);
        folder1.display();
    }
}
```

将对象组合成树来处理部分和整体的关系，使客户端能够整体统一的对待单个和组合对象的调用。

### 享元模式

享元 Flywight 的含义就轻量化，核心就是共享对象，比如线程池等各种池化对象，避免了对象的频繁创建，共享对象，提升效率。

``` java
public class Main {
    public static void main(String[] args) {
        String[] strings = new String[]{"Red", "Green", "Black"};
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Pen pen = PenFactory.getPen(strings[Math.abs(random.nextInt()) % strings.length]);
            System.out.println(pen.hashCode());
            pen.draw();
        }
    }
}

class Pen {
    private String color;

    public Pen(String color) {
        this.color = color;
    }

    public void draw() {
        System.out.println("使用" + color);
    }
}

class PenFactory {
    private static final Map<String, Pen> pens = new HashMap<>();
    private PenFactory() {}

    public static Pen getPen(String color) {
        if (pens.containsKey(color)) {
            return pens.get(color);
        }
        Pen p = new Pen(color);
        pens.put(color, p);
        return p;
    }
}
```

### 门面模式

门面模式非常简单，主要是用来简化外部系统的调用，隐藏系统的复杂度，提供简单易用的外部接口。一个简单场景就是要看视频，通过播放器开机按键直接打开显示屏和音响开始播放，不用在分别打开显示屏、打开音响。

``` java
public class Main {
    public static void main(String[] args) {
        Player player = new Player();
        player.on();
        player.off();
    }
}

class Display {
    public void on() {
        System.out.println("显示器已打开");
    }

    public void play() {
        System.out.println("视频播放中");
    }

    public void off() {
        System.out.println("显示器已关闭");
    }
}

class Sound {
    public void on() {
        System.out.println("音响已打开");
    }

    public void off() {
        System.out.println("音响已关闭");
    }
}

class Player {
    private Display display;
    private Sound sound;

    public Player () {
        display = new Display();
        sound = new Sound();
    }

    public void on() {
        display.on();
        sound.on();
        display.play();
    }

    public void off() {
        display.off();
        sound.off();
    }
}
```

## 行为型

### 策略模式

根据不同的策略执行不同的事情，策略就是指抽象部分，具体交给实现类

先定义一个策略接口

``` java
interface Strategy {
    void draw();
}
```

然后定义几个策略的实现

```java
class RedPen implements Strategy {

    @Override
    public void draw() {
        System.out.println("使用红色笔绘画");
    }
}

class GreenPen implements Strategy {

    @Override
    public void draw() {
        System.out.println("使用绿色笔绘画");
    }
}
```

持有策略的上下文 `Context` 类

``` java
class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void executeDraw() {
        strategy.draw();
    }
}
```

客户端调用

``` java
public class Main {
    public static void main(String[] args) {
        Context context = new Context(new RedPen());
        context.executeDraw();
    }
}
```

似曾相似吧，像简化版的桥接模式，其实桥接模式增加了一层抽象，将 Context 类变成抽象类，降低了耦合但也更加复杂。

### 观察者模式

类似于公众号订阅关注，观察者订阅主题，主题有数据变动通知所有观察者。

先定义主题类，有一个集合用于存储订阅者的信息

``` java
class Subject {
    private List<Observer> observers = new ArrayList<>();
    private int state;

    public void setState(int state) {
        this.state = state;
        noticeAll();
    }

    public void register(Observer observer) {
        observers.add(observer);
    }

    private void noticeAll() {
        observers.forEach(Observer::update);
    }
}
```

定义观察者接口，主要是用来主题变更是获取通知回调，然后在观察者实现类中保存有主题变量，在构造方法中初始化订阅主题。可以有多个不同的观察者实现类，用来实现不同的功能，比如文章发布、物流变化等。

``` java
interface Observer {
    void update();
}

class ConcreteObserver implements Observer{
    private Subject subject;

    public ConcreteObserver(Subject subject) {
        this.subject = subject;
        subject.register(this);
    }

    @Override
    public void update() {
        System.out.println("收到主题更新了，开始行动！");
    }
}
```

客户端的调用

``` java
public class Main {
    public static void main(String[] args) {
        Subject subject = new Subject();
        new ConcreteObserver(subject);
        new ConcreteObserver(subject);
        subject.setState(1);
    }
}
```

### 责任链模式

责任链通常需要先建立一个单向链表，调用方只需要调用头部节点，后面的节点就是自动流转。就像很审批单，只需要发起申请，经过审批人流转完成审批。在 Spring 中过滤器权限管理就是用的责任链模式。
用一个模拟审批的例子来演示。

首先，定义一个审批抽象类

```java
abstract class Approver {
    protected Approver nextApprover;

    public void setNextApprover(Approver approver) {
       this.nextApprover = approver;
    }

    public abstract void request(int amount);
}
```

根据审批人的能力大小范围实现抽象类，假设经理只能审批 1000 元以下的金额，超过的只能由 CEO 审批。

``` java
class Manager extends Approver {

    @Override
    public void request(int amount) {
        if (amount <= 1000) {
            System.out.println("经理批准了 " + amount + " 元");
        } else if (nextApprover != null) {
            nextApprover.request(amount);
        }
    }
}

class CEO extends Approver {

    @Override
    public void request(int amount) {
        if (amount > 1000) {
            System.out.println("CEO批准了 " + amount + " 元");
        }
    }
}
```

模拟审批过程

``` java
public class Main {
    public static void main(String[] args) {
        Manager approver = new Manager();
        CEO ceo = new CEO();
        // 1.建立责任链
        approver.setNextApprover(ceo);
        // 2.进行审批处理
        approver.request(1000);
        approver.request(5000);
    }
}
```

通过链条传递请求，每个处理这决定是否处理请求，不能处理交给下一个处理者，只需要从链条的开头调用，程序会自动的流转。

### 模版方法模式

设置一个抽象类，规定第一步、第二步、第三步如何执行，空余步骤留给子类去实现，保证按照顺序执行。

首先，定义一个抽象模版类

``` java
abstract class AbstractTemplate {
    public void templateMethod() {
        init();
        apply();
        end();
    }

    public void init() {
        System.out.println("上班打卡");
    }

    public abstract void apply();

    public void end() {
        System.out.println("下班回家");
    }
}
```

继承抽象模板类实现其中的抽象方法

``` java
class Template extends AbstractTemplate{

    @Override
    public void apply() {
        System.out.println("工作");
        System.out.println("吃午饭");
        System.out.println("工作");
        System.out.println("健身");
    }
}
```

客户端使用模板方法

``` java
public class Main {
    public static void main(String[] args) {
        Template template = new Template();
        template.templateMethod();
    }
}
```

模板方法看起就是把大象装进冰箱只需要三步，抽象类只负责开冰箱门和关门，中间怎么办不管，哈哈哈。

### 访问者模式

访问者模式用于将操作和数据结构分离。通过访问者，可以在不修改数据结构的前提下，增加对其元素的操作，避免因扩展操作而修改已有的类。

首先，定义访问者接口

``` java
interface Visitor {
    void visitorElement(Element element);
}
```

具体的访问者类

``` java
class ConcreteVisitor implements Visitor {

    @Override
    public void visitorElement(Element element) {
      	// 调用访问元素内部的类
        element.operation();
    }
}
```

定义元素接受访问者访问接口和具体的实现类

``` java
interface Element {
    void accept(Visitor visitor);
    void operation();
}

class ElementA implements Element {

    @Override
    public void accept(Visitor visitor) {
        visitor.visitorElement(this);
    }

    @Override
    public void operation() {
        System.out.println("ElementA 的特殊行为");
    }
}
```

最后定义一个数据结构，让数据结构中的元素接受外部访问

``` java
class ObjectStructure {
    private List<Element> elements = new ArrayList<>();

    public void addElement(Element element) {
        elements.add(element);
    }

    public void applyVisitor(Visitor visitor) {
        elements.forEach(e -> e.accept(visitor));
    }
}
```

客户端调用

``` java
public class Main {
    public static void main(String[] args) {
        ObjectStructure list = new ObjectStructure();
        list.addElement(new ElementA());
        list.applyVisitor(new ConcreteVisitor());
    }
}
```

访问者模式的主要思想是为对象结构（树或者列表）中的每个元素定义一个新的操作，避免扩展操作修改已有的类。

### 状态模式

状态模式是将行为和状态分离，具体状态封装了当前状态的相关行为。当对象状态方法变化时，切换到具体的状态类，进而改变对象的行为，而无需修改上下文类的代码。

``` java
public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        context.processOrder();
        context.processOrder();
        context.processOrder();
    }
}

interface OrderState {
    void handleOrder(Context context);
}

class NewOrderState implements OrderState {

    @Override
    public void handleOrder(Context context) {
        System.out.println("新增订单");
        context.setState(new PaidOrderState());
    }
}

class PaidOrderState implements OrderState {

    @Override
    public void handleOrder(Context context) {
        System.out.println("支付完成");
        context.setState(new ShipedOrderState());
    }
}

class ShipedOrderState implements OrderState {

    @Override
    public void handleOrder(Context context) {
        System.out.println("订单完成购买，没有其他需要执行");
    }
}

class Context {
    private OrderState state;

    public Context() {
        this.state = new NewOrderState();
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void processOrder() {
        state.handleOrder(this);
    }
}
```

很像是责任链模式是不是，不过责任链需要先构建单向链只能设置的方向调用，状态模式则是每个状态都可以在执行后设置上下文状态，更加灵活。

### 命令模式

命令模式将请求封装成对象，解耦了请求发出者和执行者。命令的调用者一般支持命令的撤销和重做操作。

首先，定义命令接口

``` java
interface Command {
    void execute();
}
```

具体命令

```java
class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }
}

class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }
}
```

命令的接收者，实际的执行人

``` java
class Light {
    public void on() {
        System.out.println("灯亮了");
    }

    public void off() {
        System.out.println("灯关了");
    }
}
```

命令的调用者，用于控制命令的调用

```java
class RemoteController {
    private Command command;
    private Stack<Command> history = new Stack<>();

   public void setCommand(Command command) {
       this.command = command;
       history.push(command);
   }

   public void pressButton() {
       history.pop().execute();
   }
}
```

客户端代码

```java
public class Main {
    public static void main(String[] args) {
        RemoteController controller = new RemoteController();
        Light light = new Light();
        controller.setCommand(new LightOnCommand(light));
        controller.pressButton();
        controller.setCommand(new LightOffCommand(light));
        controller.pressButton();
    }
}
```

**典型应用**

1.**GUI 按钮操作**：在图形用户界面中，每个按钮点击操作可以抽象为命令对象。

2.**事务管理**：如数据库操作中，将每个操作封装成命令对象，支持事务的提交与回滚。

3.**日志记录与恢复**：命令模式可以用于日志记录，并在系统出错时恢复某个操作。

### 中介模式

不同对象之间不直接沟通，而是通过共同向中介进行沟通。模拟一个聊天室，用户发完消息需要有中介者服务器推送给其他人。

首先，定义中介接口

``` java
interface ChatMediator {
    void sendMessage(String message, User user);
    void addUser(User user);
}
```

中介实现类

``` java
class ConcreteChatMediator implements ChatMediator {
    private List<User> users = new ArrayList<>();

    @Override
    public void sendMessage(String message, User user) {
        // 通过中介发送消息，中介再把消息转发给其他人
        users.forEach(e -> {
            if (e != user) {
                e.receive(message);
            }
        });
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }
}
```

聊天室的用户，只管发送消息和接受消息

``` java
class User {
    protected ChatMediator chatMediator;
    private String name;

    public User(ChatMediator chatMediator, String name) {
        this.chatMediator = chatMediator;
        this.name = name;
    }

    public void send(String message) {
        System.out.println(this.name + "发送了消息：" + message);
        chatMediator.sendMessage(message, this);
    }
    public void receive(String message) {
        System.out.println(name + "收到消息：" + message);
    }
}
```

客户端模式程序

``` java
public class Main {
    public static void main(String[] args) {
        ConcreteChatMediator mediator = new ConcreteChatMediator();
        User jack = new User(mediator, "Jack");
        mediator.addUser(jack);
        mediator.addUser(new User(mediator, "Lucy"));
        mediator.addUser(new User(mediator, "Tom"));
        jack.send("Cp 滴滴");
    }
}
```

### 迭代器模式

迭代器很常见，Java 的集合类中提供了 `iterator()` 方法用于遍历集合。

首先，定义迭代器

```java
interface Iterator {
    boolean hasNext();
    Object next();
}
```

迭代器的具体实现类

``` java
class ConcreteIterator implements Iterator {
    private List<Book> books;
    private int index;

    public ConcreteIterator(List<Book> list) {
        this.books = list;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < books.size();
    }

    @Override
    public Book next() {
        return books.get(index++);
    }
}
```

定义聚合类

``` java
interface BookCollection {
    Iterator iterator();
}
```

聚合类的具体实现类

``` java
class Library implements BookCollection {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public Iterator iterator() {
        return new ConcreteIterator(books);
    }
}
```

书籍实体类

``` java
class Book {
    private String name;

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

客户端模拟操作

``` java
public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook(new Book("《设计模式》"));
        library.addBook(new Book("《数据结构》"));
        library.addBook(new Book("《重构：改善既有代码》"));
        Iterator iterator = library.iterator();
        while (iterator.hasNext()) {
            Book book = (Book) iterator.next();
            System.out.println(book.getName());
        }
    }
}
```

### 备忘录模式

用于保存对象的某个状态，以便于恢复这个状态，而不破坏封装性。

假设有一个文本编辑器 Editor 类

``` java
class Editor {
    private String content;

    public void saveContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Memento save() {
        return new Memento(content);
    }

    public void restore(Memento memento) {
        this.content = memento.getContent();
    }
}
```

定义备忘录对象，用于恢复数据

``` java
class Memento {
    private String content;

    public Memento(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
```

管理备忘录的类

```java
class Caretaker {
    private List<Memento> mementoList = new ArrayList<>();

    public void saveMemento(Memento memento) {
        mementoList.add(memento);
    }

    public Memento getMemento(int index) {
        return mementoList.get(index);
    }
}
```

客户端调用

``` java
public class Main {
    public static void main(String[] args) {
        Editor editor = new Editor();
        Caretaker caretaker = new Caretaker();
        editor.saveContent("版本一");
        caretaker.saveMemento(editor.save());
        System.out.println(editor.getContent());
        editor.saveContent("版本二");
        caretaker.saveMemento(editor.save());
        System.out.println(editor.getContent());
        editor.saveContent("版本三");
        caretaker.saveMemento(editor.save());
        System.out.println(editor.getContent());
        editor.restore(caretaker.getMemento(0));
        System.out.println(editor.getContent());
    }
}
```

### 解释器模式

很少有人介绍解释器模式，发现这里大有学问，一般应用在编译器、正则表达式，主要是定义语法规则，并通过解释器去解释和执行这些规则。然后我还发现有人写了一本书，英文名叫《Writing An Interpreter In Go》，有对应的中文版中信出版社《用 Go 语言自制编译器》，书中作者从零实现了一个叫做 Monkey 的语言，被很多人用其他语言实现，如果你想创建一门编程语言这本书将会是很好的参考书，具体可以在这个网站了解：https://monkeylang.org

以下是一个简单解释器的例子，一个加法表达式和一个减法表达式以及一个数字表达式，实现计算 3 + 5 - 2。

``` java
public class Main {
    public static void main(String[] args) {
        Number number = new Number(3);
        Number number1 = new Number(5);
        Number number2 = new Number(2);
        Add add = new Add(number, number1);
        Subtract subtract = new Subtract(add, number2);
        System.out.println(subtract.interpret());
    }
}

// 抽象表达式类
interface Expression {
    int interpret();
}

// 终结符号表达式类
class Number implements Expression {
    private int number;

    public Number(int number) {
        this.number = number;
    }

    @Override
    public int interpret() {
        return number;
    }
}

// 非终结符号表达式类，加法表达式
class Add implements Expression {
    private Expression left;
    private Expression right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret() {
        return left.interpret() + right.interpret();
    }
}

// 非终结符号表达式类，减法表达式
class Subtract implements Expression {
    private Expression left;
    private Expression right;

    public Subtract(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret() {
        return left.interpret() - right.interpret();
    }
}

```

## 总结

文中的例子都是最简单的举例，基本上都很浅些易懂，但都体现了每个设计模式的显著特点，目的就是一眼看出每种模式的精妙之处。学习设计模式对于理解面向对象思想有些启发，其实每个模式单独来看都很简单，比较容易理解，困难的是将这些设计模式组合应用到实际工作中去。先通过简单熟悉每种模式，等到实际工作中再遇到框架提供的类库，可以仔细深入看一看它们是怎么做的。另外，最重要的还是多思考尝试再以后的编码过程中用一用某些设计模式写代码。

想要学习更多的设计模式，可以去看这个库介绍了丰富的设计模式：https://github.com/iluwatar/java-design-patterns



参考文章：https://javadoop.com/post/design-pattern