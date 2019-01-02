package com.yuntian.androidarch;

import com.yuntian.androidarch.annotation.CheckPermission;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import io.reactivex.Flowable;

/**
 * @link {https://github.com/ReactiveX/RxJava}
 */
public class RxJavaTest {

    @Test
    public void  test1(){
        Flowable.just("Hello world").subscribe(System.out::println);
    }


    @Test
    public void  test2(){

        Stack<String> stack=new Stack<>();
        stack.push("A");
        stack.push("B");
        stack.push("C");
        stack.push("D");

        for (String temp: stack ) {
            System.out.println(temp+"");
        }

        System.out.println(stack.peek()); //peek方法是返回栈顶的元素但不移除它。
        while (!stack.empty()) {
            System.out.println(stack.pop());
        }
    }

    @Test
    public void  test3() {
        List<String> list=new ArrayList<>();

        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        list.subList(1,3).clear();

        for (String temp: list ) {
            System.out.println(temp+"");
        }
    }

    @Test
    public void  test4() {
        List<String> list=new ArrayList<>();

        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");


        List<String>  tem=new ArrayList<>();

        tem.add(list.get(0));
        tem.add(list.get(3));
        list.removeAll(tem);

        for (String temp: list ) {
            System.out.println(temp+"");
        }
    }

    @Test
    public void  test5() {
        TestT test=new TestT();
        try {
            Method method= test.getClass().getMethod("wiriteToSd");
            CheckPermission annotation=method.getAnnotation(CheckPermission.class);
            System.out.println(annotation.value());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
