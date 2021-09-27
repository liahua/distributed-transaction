package com.chenahua.distributedtransaction;

import com.chenahua.distributedtransaction.dto.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import java.util.LinkedList;
import java.util.List;

/**
 * 前提: 对象的引用只被该Reference对象持有
 *
 */
public class ReferenceTest {
    /**
     * SoftReference <========> Redis lru
     * 创建对象时如果触发SoftReference(lru)时 将手动触发一次GC
     */
    @Test
    void softReferenceTest() {
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024 * 1024 * 10]);
        System.out.println(m.get());
        System.gc();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(m.get());


        byte[] b = new byte[1024 * 1024 * 15];
        System.out.println(m.get());
    }

    /**
     * 弱只要gc触发并且没有其他强引用就会被回收
     *
     * 由此可见ThreadLocal
     * 在Thread.threadLocals (也就是ThreadLocalMap)
     * 其Entry对象的构成为:
     * <pre>
     *     static class Entry extends WeakReference<ThreadLocal<?>> {
     *     //The value associated with this ThreadLocal
     *          Object value;
     *             Entry(ThreadLocal<?> k, Object v) {
     *                 super(k);
     *                 value = v;
     *             }
     *         }
     * </pre>
     *也就是说 ThreadLocal对象被指向了弱引用
     *等价于 当static ThreadLocal threalLocalExample = null 时
     * ThreadLocal对象会作为弱引用被gc自动回收
     * 这也就是为什么 ThreadLocal 需要在finally时调用remove方法
     * 因为此时 Thread.ThreadLocals中Entry的Key==null 但是value却无法被回收
     * 会导致内存泄漏
     *
     *
     *
     *但是,个人认为 在正常的web项目中,没人会傻逼到动不动就ThreadLocal =null这种操作吧
     *因此,个人认为在正常Web项目中,不会出现因为ThreadLocal导致的内存泄漏问题
     */
    @Test
    void weakReferenceTest() {

        User aaa = new User(1, "aaa");
        WeakReference m = new WeakReference<>(aaa);
        System.out.println(m.get()); //能拿到
        System.gc();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(m.get()); //能拿到
        aaa=null;
        System.out.println(m.get()); //能拿到
        System.gc();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(m.get()); //拿不到

    }

    public static final List<Object> LIST = new LinkedList<>();
    public static final ReferenceQueue<User> QUEUE = new ReferenceQueue<>();

    /**
     * 具体使用场景: DirectByteBuffer
     * 直接内存是os管理的,jvm无法管理直接内存的gc,因此,在jvm层面,通过ReferenceQueue来追踪被回收的对象,后续可以通过通知GC线程来进行堆外内存的释放
     */
    @Test
    void phantomReference() {
        PhantomReference<User> pR = new PhantomReference<>(new User(1, "aaa"), QUEUE);

        new Thread(() -> {
            while (true) {
                LIST.add(new byte[1024 * 1024]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(pR.get());
            }
        }).start();

        new Thread(() -> {
            while (true) {
                if (QUEUE.poll() != null) {
                    System.out.println("虚引用已被JVM回收");
                }
            }
        }).start();


        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 一个线程在终结时会自动执行this.notify
     * code详见jni  os_linux.cpp  os::pd_start_thread(Thread* thread))
     *
     * <pre>
     *     void os::pd_start_thread(Thread* thread) {
     *       OSThread * osthread = thread->osthread();
     *       assert(osthread->get_state() != INITIALIZED, "just checking");
     *       Monitor* sync_with_child = osthread->startThread_lock();
     *       MutexLockerEx ml(sync_with_child, Mutex::_no_safepoint_check_flag);
     *       sync_with_child->notify();
     *     }
     * </pre>
     *
     *
     *
     */
    @SneakyThrows
    @Test
    void weakReferenceThreadLocalTest() {
        System.out.println(USER_THREAD_LOCAL);
        User user = new User(3, "333");
//        User user1 = new User(5, "111");

        Thread t1 = new Thread(() -> {
            System.out.println("user = " + user);
            System.out.println("t1 start");
            USER_THREAD_LOCAL.set(new User(1, "aaa"));
            System.out.println("t1 final");
        });
        t1.start();
        Thread.sleep(1000);
        synchronized (t1) {
            System.out.println(333);
            System.out.println("t1是否存活"+t1.isAlive());
            System.out.println("t1是否存活"+t1.isAlive());
            System.out.println("t1是否存活"+t1.isAlive());
            System.out.println("t1是否存活"+t1.isAlive());
            Thread.State state = t1.getState();
             System.out.println("state = " + state);
            System.out.println("t1 before wait");
            t1.wait();
            System.out.println("after state = " + t1.getState());
            System.out.println("t1 after wait");
            System.out.println("t1是否存活"+t1.isAlive());
        }

        System.out.println("22222222222222222");
        Thread t2 = new Thread(() -> {
            System.out.println("t2 start");
            USER_THREAD_LOCAL.set(new User(2, "bbb"));
            System.out.println("t2 final");
        });
        t2.start();

        System.in.read();
    }

    /**
     * 猜想:
     * Thread对象在jni存在 canTerminal  default as true
     * 在Thread对象进入监视器队列时,canTerminal = false
     * 在Thread对象离开监视器队列/触发wait时,canTerminal =true
     *
     *
     * 线程结束时
     * Thread对象应是先getState=Terminaled
     * 然后触发thread.notify
     *
     *
     * @throws IOException
     */
    @Test
    void joinAndWaitMethodTest() throws IOException {
        Thread t1 = new Thread(() -> {
            System.out.println("t1 start");
            System.out.println("t1 final");
        });
        Thread tMain = new Thread(() -> {
            System.out.println("synchronized before");
            synchronized (t1){
                System.out.println("t1是否存活"+t1.isAlive());
                System.out.println("t1线程状态before Wait" + t1.getState());
                try {
                    t1.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1线程状态after Wait" + t1.getState());
                System.out.println("t1 after wait");
                System.out.println("t1是否存活"+t1.isAlive());
            }
            System.out.println("synchronized after");
        });
        Thread t2 = new Thread(() -> {
            synchronized (t1) {
                System.out.println("notify before");
                t1.notify();
                System.out.println("notify after");
            }
        });

        t1.start();
        tMain.start();
        t2.start();
        System.in.read();

    }
}
