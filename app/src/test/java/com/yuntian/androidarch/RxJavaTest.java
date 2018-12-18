package com.yuntian.androidarch;

import org.junit.Test;

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


}
