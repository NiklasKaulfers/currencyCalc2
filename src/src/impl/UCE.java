package src.impl;


/**
 * an error for unknown Currencies
 */
public class UCE extends Throwable{
    /**
     * error handling for unknown currencies
     * @param msg the msg of what went wrong, has to be handled in the code
     */
    public UCE(String msg){
        super(msg);
    }
}
