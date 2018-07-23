package motion;

import object.structure.Cell;

/**
 *
 * @author hp
 */
public class Motion extends Cell{

    public Motion() {
        super();
    }
    public Motion(int x, int y){
        super(x, y);
    }
    public Motion(int x, int y, int width, int heigh){
        super(x, y, width, heigh);
    }
    public Motion(int x, int y, int width, int height, boolean cancer){
        super(x, y, width, height, cancer); 
    }
    /** 
     * Vertical 
     */
    
    private int X, Y, scale;
    public void setWinger(int X, int Y, int scale){
        this.X = X; this.Y = Y; this.scale = scale;
    }
    private int dx, dy;
    private void setup(){
        dx = (X-getX()>0 ? X-getX():getX()-X);
    }
}
