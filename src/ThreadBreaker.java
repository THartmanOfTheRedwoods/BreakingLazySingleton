import SingletonImplementations.Singleton;
import SingletonImplementations.SingletonType;

import java.lang.Runnable;
import java.lang.Thread;
import java.util.stream.IntStream;

public abstract class ThreadBreaker implements Runnable {
    private final String threadName;
    private final SingletonType type;

    public abstract Singleton createSingleton(SingletonType type);

    public ThreadBreaker(String threadName, SingletonType type) {
        this.threadName = threadName;
        this.type = type;
    }

    public static void main(String[] args) {
        //TODO: Consider making the SingletonType a command line argument or
        //      create an application loop to allow the user to select different
        //      types with Scanner.

        /* Functional way to create threads */
        IntStream.range(0, 10).forEach( (i) -> new Thread(
                new ThreadBreakerFactory(
                        "Thread"+i,
                        SingletonType.THREAD_SAFE)).start());

        /* Old Fashioned way to create a bunch of threads */
        /*
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < 12; i++) {
            threads.add(
                    new Thread(
                            new ThreadBreakerFactory("Thread"+i, SingletonType.THREAD_SAFE)));
        }
        for( Thread t : threads) {
            t.start();
        }
        */
    }

    @Override
    public void run() {
        Singleton instance = createSingleton(this.type);
        instance.setDataInSingleton("that sings I'm a singleton.");
        System.out.printf("%s has singleton type %s with id %s and data %s%n", this.getThreadName(), instance.getType(), instance.hashCode(), instance.getDataInSingleton());
    }

    public String getThreadName() {
        return threadName;
    }
}