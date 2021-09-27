package com.chenahua.distributedtransaction;

import org.junit.jupiter.api.Test;

//@SpringBootTest
class DistributedTransactionApplicationTests {

    /**
     * Redssion 源码理论解读
     * 分布式锁
     * 1.可重入
     * 2.超时不可死锁
     * expire实现
     * 通过redis设置过期时间达到超时不死锁的目的
     * 正常状况下 不设置超时时间默认 30s, 上锁后,会启动一个timeout 10s检查一次
     * 如果key(uuid+threadID)还在被保留(被保留在续订表内)说明任务依旧没有结束,则继续expire续时间,递归调用续订方法
     *
     * 如果设置了超时时间,则没有续订
     * 当key的超时时间结束后,key会从redis移除
     *
     * watchDog如何判断是否要续时间?
     * 默认情况为,无限递归,除非触发unlock 否则无限续
     * 待定.
     *
     *
     *
     *
     * 上锁逻辑
     * 1.检查是否存在key,不存在直接加锁
     * 2.检查是否是重入锁(uuid+threadid是否一致),存在直接count+1,更新expire
     * 3.自旋待考证) -----返回差多少过期时间
     *
     * 对key:liahua加锁
     * liahua{
     *     "uuid(redis集群得来):threadId":count
     * }
     */
    @Test
    void contextLoads() {
        long id = Thread.currentThread().getId();
        System.out.println("id = " + id);
    }

//    @Test
//    void redissonLock() {
//        RedissonClient redissonClient = Redisson.create();
//        RLock lock = redissonClient.getLock("11");
//        lock.lock();
//        lock.unlock();
//    }


    @Test
    void testStr() {
        String str="lllllllllllll";
        System.out.println(str.length());
    }
}
