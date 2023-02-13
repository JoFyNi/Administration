package components;

import java.util.ArrayList;
import java.util.List;

public interface connection {
    public static final String multicastIP = "192.168.173.89";
    public static final int multicastPORT = 3344;
    public static final String separator = "[>>>]";
    public static final int messageValue = 1024;
    public static final long breakTime = 3000;
    public static final String connectionOnline = "connectionOnline";
    public static final String connectionBreak = "connectionBreak";
    public static final String privat = "privat";

    public static final List<Request> requestsPending = new ArrayList<>();
    public static final List<Request> requestsApproved = new ArrayList<>();
    public static final List<Request> requestsRejected = new ArrayList<>();
}
