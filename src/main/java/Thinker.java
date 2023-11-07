import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Thinker extends Thread {
    private int num;
    private String name;
    private boolean satiety = false; //сытость философа
    //private CountDownLatch cdl;
    private UsingForks fork;
    private Random random;
    private Semaphore SEMAPHORE;

//    public Thinker (int num, String name, CountDownLatch cdl, UsingForks fork){
//        this.num = num;
//        this.name = name;
//        this.cdl = cdl;
//        this.fork = fork;
//        random = new Random();
//    }
public Thinker (int num, String name, Semaphore SEMAPHORE, UsingForks fork){
    this.num = num;
    this.name = name;
    this.SEMAPHORE = SEMAPHORE;
    this.fork = fork;
    random = new Random();
}

    @Override
    public void run() {
        System.out.println(name + " размышляет");
        try {
            SEMAPHORE.acquire();
            System.out.println(name + " ищет вилки");

            synchronized (fork) {
                if (num - 1 < fork.getSizeForks() - 1) {

                    if (!fork.getForks(num - 1) && !fork.getForks(num)) {
                        fork.setForks(num);
                        fork.setForks(num - 1);
                        System.out.println(name + " ест");
                        satiety = true;
                    } else {
                        System.out.println(name + " размышляет");
                    }
                } else {
                    //if (!fork.getForks(num-1) && !fork.getForks(0)){
                    fork.setForks(num - 1);
                    fork.setForks(0);
                    System.out.println(name + " ест");

                    satiety = true;
                }
            }
            sleep(random.nextInt(2, 5) * 1000L);

            synchronized (fork) {
                if (num - 1 < fork.getSizeForks() - 1) {
                        fork.setForks(num);
                        fork.setForks(num - 1);
                } else {
                    fork.setForks(num - 1);
                    fork.setForks(0);
                }

            }
            SEMAPHORE.release();
            System.out.println(name + " размышляет");


            sleep(random.nextInt(2, 5) * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


//    } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        //    @Override
//    public void run() {
//        try {
//            takeTwoForks();
//            synchronized (this){
//                wait();
//            }
//            finishedEating();
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        }
//
//    }

//    private void takeTwoForks() throws InterruptedException{
//
//        System.out.println(name + " размышляет");
//        sleep(random.nextInt(2, 5) * 1000L);
//
//        while (!satiety) {
//            if (num-1 < fork.getSizeForks()-1){
//                if (!fork.getForks(num-1) && !fork.getForks(num)){
//                    fork.setForks(num);
//                    fork.setForks(num-1);
//                    System.out.println(name + " ест");
//                    sleep(random.nextInt(2, 5) * 1000L);
//                    cdl.countDown();
//                    satiety = true;
//                } else {
//                    System.out.println(name + " размышляет");
//                    sleep(random.nextInt(2, 5) * 1000L);
//                }
//            } else {
//                //if (!fork.getForks(num-1) && !fork.getForks(0)){
//                fork.setForks(num-1);
//                fork.setForks(0);
//                System.out.println(name + " ест");
//                sleep(random.nextInt(2, 5) * 1000L);
//                cdl.countDown();
//                satiety = true;
//
////            } else {
////                System.out.println(name + " размышляет");
////                sleep(1000);
//            }
//            try {
//                sleep(random.nextInt(2, 5) * 1000L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    private void finishedEating() throws InterruptedException{
        if (num-1 < fork.getSizeForks()-1){
            fork.setForks(num);
            fork.setForks(num-1);
        } else {
            fork.setForks(num-1);
            fork.setForks(0);
        }

        System.out.println(name + " наелся и размышляет");
        sleep(random.nextInt(2, 5) * 1000L);
    }

//    public void eat(){
//        synchronized (this){
//            notify();
//        }
//    }

}
