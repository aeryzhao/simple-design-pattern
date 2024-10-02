package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/2 18:46
 */
public class Main {
    public static void main(String[] args) {
        InterFactory interFactory = new InterFactory();
        Computer computer = new Computer(interFactory);
        computer.info();
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