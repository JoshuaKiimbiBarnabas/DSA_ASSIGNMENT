public class Star {
    // Change access level to package-private or protected
    ServerNode centralNode;

    public Star() {
        this.centralNode = new ServerNode();
    }

    public void insertNode(ClientNode client) {
        centralNode.addClient(client);
    }

    public void deleteNode(ClientNode client) {
        centralNode.removeClient(client);
    }
}
