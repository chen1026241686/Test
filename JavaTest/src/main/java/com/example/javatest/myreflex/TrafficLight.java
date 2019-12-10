package com.example.javatest.myreflex;

/**
 * @author ChenYasheng
 * @date 2019/11/19
 * @Description
 */
public enum TrafficLight {

    RED("A") {
        public TrafficLight next() {
            return GREEN;
        }
    }, GREEN("B") {
        public TrafficLight next() {
            return YELLO;
        }
    }, YELLO("C") {
        public TrafficLight next() {
            return RED;
        }
    };

    abstract TrafficLight next();

    private String name;

    TrafficLight(String name) {
        this.name = name;
    }

}
