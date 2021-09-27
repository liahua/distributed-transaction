package com.chenahua.distributedtransaction;

/**
 * 对象在堆中的存储结构
 * 类比书在图书馆中的存储方式
 * 书的标签(对象头)+书的实体(对象实例数据){
 * 对象头:{
 * 书的版本  (gc标志)
 * 书的更新时间 (gc age)
 * 书的借记者 (同步锁)
 * 书的编码 (hashcode)
 * 书的目录 (对象类型指针)
 * }
 * 对象实例数据
 * <p>
 * 同时根据内存读取的特性, 对象需满足8字节对齐(不足则要补充空白)
 * }
 */
public class ObjectInHeap {
}
