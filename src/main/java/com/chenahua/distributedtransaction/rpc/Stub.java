package com.chenahua.distributedtransaction.rpc;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Stub {
    @SuppressWarnings("unchecked")
    public static <T> T getStub(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
            Socket socket = getSocket();
            sendMessage(socket, args, method, clazz);
            return getData(socket, method);
        });
        return (T) o;
    }

    private static Object getData(Socket socket, Method method) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Object o = objectInputStream.readObject();
        return o;
    }

    /**
     * 传递:1.类名2.方法名,方法类型3.参数
     * @param socket
     * @param args
     * @param method
     * @param aClass
     * @throws IOException
     */
    private static void sendMessage(Socket socket, Object[] args, Method method, Class aClass) throws IOException {
        String clazzName=aClass.getName();
        String methodName=method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeUTF(clazzName);
        out.writeUTF(methodName);
        out.writeObject(parameterTypes);
        out.writeObject(args);
        out.flush();
    }

    private static Socket getSocket() throws IOException {
        return new Socket("127.0.0.1", 8888);
    }
}
