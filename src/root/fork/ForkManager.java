package root.fork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ForkManager {

    private static List<Fork> forks;
    private int forksSize;

    public ForkManager(int forksSize) {
        this.forksSize = forksSize;
        forks = new ArrayList<>(forksSize);
        for (int i = 0; i < forksSize ; i++) {
            Fork fork = new Fork(i);
            forks.add(fork);
        }
    }

    public void takeFork(int id) throws InterruptedException {
        Fork fork = forks.get(id);
        Lock lock = fork.getLock();
        Condition condition =  fork.getCondition();
        try{
            lock.lock();
            while (fork.isTaken()){
                condition.await();
            }
            fork.setTaken(true);
          } finally {
            lock.unlock();
        }

    }
    public synchronized void dropFork(int id){
        Fork fork = forks.get(id);
        fork.setTaken(false);
        fork.getCondition().signalAll();
    }

}
