package object.structure;

/**
 *
 * @author rafiul islam
 */
public class Square {
    private int x;
    private int y;
    private int width;
    private int height;
    
    public Square(){
        this.x = 0; this.y = 0; this.width = 0; this.height = 0;
    }
    
    public Square(int x, int y){
        this.x = x; this.y = y; this.height = 0; this.width = 0;
    }
    
    public Square(int x, int y, int width, int height){
        this.x = x; this.y = y; this.width = width; this.height = height;
    }

    public boolean isInside(int x, int y){
        if( (x>=this.x && x<=this.x + this.width) && 
            (y>=this.y && y<=this.y + this.height) )
            return true;
        
        return false;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public String toString(){
        return String.format("%s: x=%d, y=%d, width=%d, height=%d",
                "Square object",x,y,width,height);
    }
}
