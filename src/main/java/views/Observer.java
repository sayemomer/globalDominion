package views;

import models.Observable;


/**
 * Observer interface to update the observable state
 */

public interface Observer {

    /**
     * Method to update the observable state
     * @param p_observable_state observable state
     */

    public void update(Observable p_observable_state);

}
