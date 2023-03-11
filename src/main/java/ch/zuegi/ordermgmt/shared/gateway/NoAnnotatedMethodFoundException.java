package ch.zuegi.ordermgmt.shared.gateway;

public class NoAnnotatedMethodFoundException extends RuntimeException {
    public NoAnnotatedMethodFoundException(String s) {
        super(s);
    }
}
