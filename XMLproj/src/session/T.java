package session;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class T
 */
@Stateless
@LocalBean
public class T implements TRemote, TLocal {

    /**
     * Default constructor. 
     */
    public T() {
        // TODO Auto-generated constructor stub
    }

}
