import Interfaces.NewInterface;

import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import java.lang.*;

public class Category extends Vehicle implements NewInterface {

    //String category;
    int categoryid;
    final int finalfield = 1;
    int x, y;


    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category, int category_id) {
        this.category = category_id + category;
    }

    

    public boolean getCategoryId() {
        try {
            String txtA = "Category";
            boolean flag = false;
            
            System.out.println("text");
            if (categoryid > 0)
                flag = true;
            return flag;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalStateException e) {
            throw e;
        }
    }

    final String stringValue = "Welcome";

    String get_string() {
        for (; true; )
            return stringValue;
    }

    public boolean diff(int x) {
        if (true)
            this.x = x + 1;
        else
            this.x = x - 1;

        System.out.println("X and Y are different");
        return false;
    }

    public boolean same(int y) {
        if (true)
            this.y = y + 1;
        System.out.println("X and Y are same");
        return true;
    }

    boolean bar(int x, int y) {
        return (x != y) ? diff(x) : same(y);
    }


    @Override
    public void remove(Object o) throws RemoveException, EJBException {

    }
}

