package conociendo.struts.helloworld.model;

public class MessageStore {
    private String message;

    public MessageStore() {
        message = "Hola Usuario de Struts";
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return message + " (desde toString)";
    }

    public void setMessage(String theMessage) {
        message = theMessage;

    }

}
