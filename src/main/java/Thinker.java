import java.util.concurrent.CountDownLatch;

public class Thinker extends Thread {
    private int num;
    private String name;
    private boolean satiety = false; //сытость философа
    private CountDownLatch cdl;
    private UsingForks fork;

    public Thinker (int num, String name, CountDownLatch cdl, UsingForks fork){
        this.num = num;
        this.name = name;
        this.cdl = cdl;
        this.fork = fork;
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

        System.out.println(name + " размышляет");
        sleep(1000);

        while (!satiety) {
            if (num-1 < fork.getSizeForks()-1){
                if (!fork.getForks(num-1) && !fork.getForks(num)){
                    fork.setForks(num);
                    fork.setForks(num-1);
                    System.out.println(name + " ест");
                    sleep(1000);
                    cdl.countDown();
                    satiety = true;
                } else {
                    System.out.println(name + " размышляет");
                    sleep(1000);
                }
            } else {
                //if (!fork.getForks(num-1) && !fork.getForks(0)){
                fork.setForks(num-1);
                fork.setForks(0);
                System.out.println(name + " ест");
                sleep(1000);
                cdl.countDown();
                satiety = true;

//            } else {
//                System.out.println(name + " размышляет");
//                sleep(1000);
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void finishedEating() throws InterruptedException{
        if (num-1 < fork.getSizeForks()-1){
            fork.setForks(num);
            fork.setForks(num-1);
        } else {
            fork.setForks(num-1);
            fork.setForks(0);
        }

        System.out.println(name + " наелся и размышляет");
        sleep(1000);
    }

    public void eat(){
        synchronized (this){
            notify();
        }
    }

}
