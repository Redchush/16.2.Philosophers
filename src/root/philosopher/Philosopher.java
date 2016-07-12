package root.philosopher;

import org.apache.log4j.Logger;
import root.fork.Fork;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Philosopher implements Runnable {

    private Fork left;
    private Fork right;
    private final int id;
    private final int thinkPercantage;
    private final static Logger logger = Logger.getRootLogger();

    public Philosopher(int id, int thinkPercantage, Fork right, Fork left) {
        this.id = id;
        this.thinkPercantage = thinkPercantage;
        this.right = right;
        this.left = left;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                logger.debug(this + " thinking ");
                thinking();
                logger.debug(this + " getting the right fork.");

                logger.debug(this + " getting the left fork.");
                logger.debug(this + " eating.");
            }
        } catch (InterruptedException e){
            logger.debug(this + " unexpectedly dying");
        }

    }
    private void thinking() throws InterruptedException {
        int sleepTime = generateSleepTime();
        TimeUnit.MILLISECONDS.sleep(sleepTime);
    }
    private int generateSleepTime(){
        Random random = new Random();
        return random.nextInt(thinkPercantage);
    }

    @Override
    public String toString() {
        return "Philosopher{" +
                "id=" + id +
                ", left=" + left +
                ", right=" + right +
                ", thinkPercantage=" + thinkPercantage +
                '}';
    }
}
