import java.util.concurrent.CountDownLatch;

public class UsingForks {
    private volatile boolean[] forks;

    public UsingForks(boolean[] forks){
        this.forks=forks;
    }

    public void setForks(int numFork) {
        forks[numFork] = !forks[numFork];
    }

    public boolean getForks(int numFork) {
        return forks[numFork];
    }

    public int getSizeForks() {
        return forks.length;
    }


}
