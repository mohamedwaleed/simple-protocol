package my.simple.protocol.client;

public class Message {
    public static String byeBye(String clientName, long duration) {
        return "BYE " + clientName + ", WE SPOKE FOR " + duration + " MS";
    }

    public static String sorry() {
        return "SORRY, I DID NOT UNDERSTAND THAT";
    }

    public static String hi(String sessionId) {
        return "HI, I AM " + sessionId;
    }

    public static String welcome(String clientName) {
        return "HI " + clientName;
    }

    public static String nodeAdded() {
        return "NODE ADDED";
    }

    public static String nodeExists() {
        return "ERROR: NODE ALREADY EXISTS";
    }

    public static String edgeAdded() {
        return "EDGE ADDED";
    }

    public static String nodeRemoved() {
        return "NODE REMOVED";
    }

    public static String nodeNotFound() {
        return "ERROR: NODE NOT FOUND";
    }

    public static String edgeRemoved() {
        return "EDGE REMOVED";
    }
}
