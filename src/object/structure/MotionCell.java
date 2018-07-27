package object.structure;

/**
 *
 * @author rafiul islam
 */
public class MotionCell extends Cell{
    
    private int endX, endY;
    private int centerX, centerY;
    private int speedX,speedY;
    private boolean up;
    //Constructors
    public MotionCell(){
        super();
    }
    public MotionCell(int x, int y){
        super(x, y);
    }
    public MotionCell(int x, int y, int width, int height){
        super(x, y, width, height);
    }
    public MotionCell(int x, int y, int width, int height,boolean cancer){
        super(x, y, width, height,cancer);
    }
    
    public void setMotion(int endX, int endY, int speedX, int speedY){
        this.endX = endX;
        this.endY = endY;
        this.speedX = speedX;
        this.speedY = speedY;
        setup();
    }
    private void setup(){
        centerX = getX(); centerY = getY();
        up = true;
    }
    public void move(){
        if(up){
            setX(getX()-speedX);
            setY(getY()-speedY);
        }
        else{
            setX(getX()+speedX);
            setY(getY()+speedY);
        }
        positionCheck();
    }
    private void positionCheck(){
        if(getX() < endX || getY() < endY){
            up = false;
        }
        else if(getX() > centerX || getY() > centerY){
            up = true;
        }
    }
}
