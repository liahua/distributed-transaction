package com.chenahua.distributedtransaction;

import org.junit.jupiter.api.Test;

/**
 * todo 1.spring Bean创建的循环依赖 （属性注入，构造器注入的区别）
 * todo 2.spring bean 加载流程（FactoryBean BeanFactory）
 * todo 3.分库分表实验（动态路由）
 * todo 4.分布式事务
 * todo 5.
 *
 */
public class VolatileTest {
    volatile int race = 1;

    @Test
    void twoThreadTest() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increase();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increase();
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("race = " + race);
    }

    void increase() {
        race++;
    }

    int a = 0;
    boolean flag = false;

    public static class Singleton {
        private static Singleton instance;
        private int i = 0;

        public Singleton(int i) {
            this.i = i;
        }

        public static Singleton getInstance() {
            if (instance == null) {
                synchronized (Singleton.class) {
                    if (instance == null) {
                        instance = new Singleton(1);
                    }
                }
            }
            return instance;
        }

        public static void main(String[] args) throws InterruptedException {
            Thread t1 = new Thread(() -> {
                Singleton instance = Singleton.getInstance();
            });
            t1.start();
            Thread t2 = new Thread(() -> {
                Singleton instance = Singleton.getInstance();
            });
            t2.start();
            t1.join();
            t2.join();
            System.out.println("done" );
        }
    }
}

