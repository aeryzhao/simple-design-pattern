package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/3 13:29
 */
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

