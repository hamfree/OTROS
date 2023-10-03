package conociendo.struts.helloworld.action;

import com.opensymphony.xwork2.ActionSupport;

import conociendo.struts.helloworld.model.MessageStore;

public class HelloWorldAction extends ActionSupport {
    private static final long serialVersionUID = 6433658474909116664L;
    private MessageStore messageStore;
    private static int contadorSaludos = 0;
    private String userName;

    public int getContadorSaludos() {
        return contadorSaludos;
    }

    public String execute() {
        messageStore = new MessageStore();
        if (userName != null) {
            messageStore.setMessage(messageStore.getMessage() + " " + userName);
        }

        contadorSaludos++;
        return SUCCESS;
    }

    public MessageStore getMessageStore() {
        return messageStore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
