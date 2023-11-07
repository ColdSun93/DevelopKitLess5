import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class RoundTable extends Thread {
    private List<Thinker> thinkers;
    private CountDownLatch cdl;

    //private List<UsingForks> usingForks;
    private UsingForks fork;

    private int size = 5;


    public RoundTable(){
        cdl = new CountDownLatch(10);
        UsingForks fork = new UsingForks(new boolean[]{false,false,false,false,false});

        //usingForks = new ArrayList<>(size);
        thinkers = new ArrayList<>(size);
        //usingForks.add(new UsingForks());
        //System.out.println(usingForks.get(0).getForks());

        for (int i = 0; i < size; i++) {
             thinkers.add(new Thinker((i + 1),"Философ" + (i + 1),
                        cdl, fork));
                        //usingForks.get(i),
                        //usingForks.get(i + 1)));
            }
        }
//        thinkers.add(new Thinker("Философ1", cdl));
//        thinkers.add(new Thinker("Философ2", cdl));
//        thinkers.add(new Thinker("Философ3", cdl));
//        thinkers.add(new Thinker("Философ4", cdl));
//        thinkers.add(new Thinker("Философ5", cdl));



    @Override
    public void run() {
        try {
            takeTwoForks();
            cdl.await();
            goAll();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void takeTwoForks() {

        for (Thinker thinker: thinkers){
            thinker.start();
        }
    }

    private void goAll() throws InterruptedException {
        sleep(1000);
        for (Thinker thinker: thinkers){
            thinker.eat();
        }

    }
}
