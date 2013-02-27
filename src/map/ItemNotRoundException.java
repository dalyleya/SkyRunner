package map;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 27.02.13
 * Time: 22:45
 * To change this template use File | Settings | File Templates.
 */
public class ItemNotRoundException extends Exception {
    public ItemNotRoundException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ItemNotRoundException(String detailMessage) {
        super(detailMessage);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getMessage() {
        return super.getMessage();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
