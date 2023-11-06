import java.util.concurrent.CountDownLatch;

public class Thinker extends Thread {
    private String name;
    private boolean satiety = false;
    private CountDownLatch cdl;
    private volatile UsingForks fork1, fork2;

    public Thinker (String name, CountDownLatch cdl,  UsingForks fork1, UsingForks fork2){
        this.name = name;
        this.cdl = cdl;
        this.fork1 = fork1;
        this.fork2 = fork2;
    }

    @Override
    public void run() {
        try {
            takeTwoForks();
            synchronized (this){
                wait();
            }
            finishedEating();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    private void takeTwoForks() throws InterruptedException{
        while (!satiety) {
            if (!fork1.getForks() && !fork2.getForks()) {
                System.out.println(name + " ест");
                sleep(1000);
                fork1.setForks(true);
                fork2.setForks(true);
                cdl.countDown();
                satiety = true;
            } else{
                System.out.println(name + " размышляет");
                sleep(1000);

            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void finishedEating() throws InterruptedException{
        System.out.println(name + " наелся");
    }

    public void eat(){
        synchronized (this){
            notify();
        }
    }

}
