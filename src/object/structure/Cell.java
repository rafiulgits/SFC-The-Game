package object.structure;
/**
 *
 * @author rafiul
 */
public class Cell extends Square{
    private boolean cancer;
    private boolean alive = true;
    private boolean countDown = false;
    //cell cell life count down
    private long counter;
    //constructors
    public Cell() {
        super();
    }
    public Cell(int x, int y){
        super(x, y);
    }
    public Cell(int x, int y, int width, int height){
        super(x, y, width, height);
    }
    public Cell(int x, int y, int width, int height,boolean cancer){
        super(x, y, width, height);
        this.cancer = cancer;
    }
    //methods
    public void setCancer(boolean cancer){
        this.cancer = cancer;
    }
    public boolean isCancer(){
        return cancer;
    }
    public void setAlive(boolean visible){
        this.alive = visible;
    }
    public boolean isAlive(){
        return alive;
    }
    public void setCountDown(int sec){
        countDown = true;
        counter = System.currentTimeMillis();
        new Thread(new Runnable(){
            public void run(){
                while(true){
                    //time for 2 second
                    if((System.currentTimeMillis()-counter)/1000 == sec){
                        countDown = false;
                        cancer = false;
                        break;
                    }
                }
            }
        }).start();
    }
    public boolean isInCounting(){
        return countDown;
    }
    public String toString(){
        return String.format("Cell object: x=%d, y=%d, width=%d, height=%d, "
                + "cencer="+cancer, getX(),getY(),getWidth(),getHeight(),cancer);
    }
}