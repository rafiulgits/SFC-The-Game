package test.main;

/**
 *
 * @author hp
 */
public class Counter {

    private int counter;
    private boolean done = false;
    
    public Counter() {
        counter = 0;
    }
    public void setCounter(){
        System.out.println("counter method called");
        Thread t = new Thread(new Runnable(){
            public void run(){
                System.out.println("Thread started");
                while(counter <= 50){
                    counter++;
                }
                show();
            }
        });
        t.start();
    }
    private void show(){
        System.out.println("Done\n\n");
        done = true;
    }
    public boolean isDone(){
        return done;
    }
}
