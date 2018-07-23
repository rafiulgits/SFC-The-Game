package test.main;

/**
 *
 * @author hp
 */
public class TimeCounter {
    public static void main(String[] args) {
        long prev = System.currentTimeMillis();
        while(true){
            long now = System.currentTimeMillis();
            if((now-prev)/1000 == 4){
                System.out.println("2 Second");
                break;
            }
        }
        
    }
}
