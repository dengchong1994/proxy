package com.jzy.dc.test.service;

public class Dc implements Person {

    @Override
    public void findJob() {
        System.out.println("I need find job");
    }

    @Override
    public void findLove() {
        System.out.println("I need a beauty girl");
    }
}
