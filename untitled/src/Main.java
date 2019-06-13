import java.lang.management.ManagementFactory;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        test();
        System.out.println(ManagementFactory.getRuntimeMXBean().getName());
        Thread.sleep(60000);
        test();
    }

    public static void test() {
        System.out.println("Hello");
    }
}
