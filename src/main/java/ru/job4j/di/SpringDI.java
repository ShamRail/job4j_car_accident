package ru.job4j.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringDI {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru.job4j.di");
        context.refresh();
        System.out.println(context.getBean(ConsoleInput.class) == context.getBean(ConsoleInput.class));
        System.out.println(context.getBean(Store.class) == context.getBean(Store.class));
    }
}