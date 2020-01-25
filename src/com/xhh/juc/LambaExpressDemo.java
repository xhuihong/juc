package com.xhh.juc;

import java.io.FileOutputStream;

@FunctionalInterface
interface Foo {
    //public void sayHello();
    public int add(int x, int y);

    public default int mul(int x, int y) {
        return x * y;
    }

    public static int div(int x, int y) {
        return x / y;
    }

}

/**
 * @author xhh
 * 函数式编程(只能有一个方法)
 * 拷贝小括号， 写死右箭头， 落地大括号
 *  @FunctionalInterface
 *  default
 *  static
 */
public class LambaExpressDemo {
    public static void main(String[] args) {
//        Foo foo = new Foo() {
//            @Override
//            public void sayHello() {
//                System.out.println("hello");
//            }
//
//            @Override
//            public void add(int x, int y) {
//
//            }
//        };


        Foo foo = (int x, int y) -> {
            System.out.println("hello lambda：");
            return x + y;
        };
        System.out.println(foo.add(1, 2));
        System.out.println(foo.mul(5, 5));
        System.out.println(Foo.div(5, 5));

    }
}
