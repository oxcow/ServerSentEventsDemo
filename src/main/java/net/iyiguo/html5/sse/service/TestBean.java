package net.iyiguo.html5.sse.service;

/**
 * @author leeyee
 * @date 2020/9/8
 */
@Deprecated
public class TestBean {

    public void close() {
        System.out.println("Invoke test bean close");
    }

    public void shutdown() {
        System.out.println("Invoke test bean shutdown");
    }
}
