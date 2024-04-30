public class ClientNode {
    private String ID; // Unique name/id
    private ServerNode server;

    public ClientNode(String ID, ServerNode server) {
        this.ID = ID;
        this.server = server;
    }

    public void send(String message) {
        server.brokerMessage(this, message);
    }

    public void receiveMessage(String senderID, String message) {
        System.out.println("Received message from " + senderID + ": " + message);
    }

    public String getID() {
        return ID;
    }
}
