import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class RoundTable extends Thread {
    private List<Thinker> thinkers;
    private CountDownLatch cdl;
    private UsingForks fork;
    private static Semaphore SEMAPHORE = null;

    private int size = 5;


    public RoundTable(){
        //cdl = new CountDownLatch(3);

        UsingForks fork = new UsingForks(new boolean[]{false,false,false,false,false});

        thinkers = new ArrayList<>(size);

        SEMAPHORE = new Semaphore(1); // количество потоков установил 1

        for (int i = 0; i < size; i++) {
             thinkers.add(new Thinker((i + 1),"Философ" + (i + 1),
                     SEMAPHORE, fork));

            }
        }

    @Override
    public void run() {
        takeTwoForks();
    }
//    @Override
//    public void run() {
//        try {
//            takeTwoForks();
//            cdl.await();
//            goAll();
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        }
//    }

    private void takeTwoForks() {

        for (Thinker thinker: thinkers){
            thinker.start();
        }
    }

//    private void goAll() throws InterruptedException {
//        sleep(1000);
//        for (Thinker thinker: thinkers){
//            thinker.eat();
//        }
//
//    }
}
