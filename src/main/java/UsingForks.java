import java.util.concurrent.CountDownLatch;

public class UsingForks {
    private volatile boolean forks;

//    public UsingForks(){
//
//    }

    public void setForks(boolean forks) {
        this.forks = forks;
    }

    public boolean getForks() {
        return forks;
    }


}
