package org.codance;

import java.util.Stack;

/**
 * @author zhaoxg
 * @date 2024/10/3 18:27
 */
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

interface Command {
    void execute();
}

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

class Light {
    public void on() {
        System.out.println("灯亮了");
    }

    public void off() {
        System.out.println("灯关了");
    }
}

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
