package com.wen;

public class Test {
    public static void main(String[] args) {
        try {
            System.out.println("try");
          /*  System.out.println(1 / 0);*/
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("catch");
            return;
        } finally {
            System.out.println("finally");
        }
        System.out.println("end");
        return;
    }
}
