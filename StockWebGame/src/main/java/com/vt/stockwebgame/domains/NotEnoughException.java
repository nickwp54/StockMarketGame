/*
 * Created by Nicholas Phillpott on 2016.04.24  * 
 * Copyright © 2016 Nicholas Phillpott. All rights reserved. * 
 */
package com.vt.stockwebgame.domains;

/**
 *
 * @author painter
 */
public class NotEnoughException extends Exception {

    /**
     * Creates a new instance of <code>NotEnoughExcpetion</code> without detail
     * message.
     */
    public NotEnoughException() {
    }

    /**
     * Constructs an instance of <code>NotEnoughExcpetion</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NotEnoughException(String msg) {
        super(msg);
    }
}
