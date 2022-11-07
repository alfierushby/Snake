package com.game;

public class Main {
    public static void main(String[] args) {


        System.out.println("Hello world!" + GameUtil.class.getClassLoader().getResource("snake-head-right.png").toString());
    }
}