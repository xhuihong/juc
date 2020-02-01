package com.xhh.juc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
class User{
    String username;
    int age;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}

public class AtomicReferenceDemo  {
    public static void main(String[] args) {
        AtomicReference<User> userAtomicReference=new AtomicReference<>();
        User z3=new User("z3",18);
        User li4=new User("li4",22);
        userAtomicReference.set(z3);
        System.out.println(userAtomicReference.compareAndSet(z3,li4)+"\t"+userAtomicReference.get().toString());
        System.out.println(userAtomicReference.compareAndSet(z3,li4)+"\t"+userAtomicReference.get().toString());
    }
}
