import java.util.ArrayList;
import java.util.List;

public class ServerNode {
    private List<ClientNode> clients;

    public ServerNode() {
        this.clients = new ArrayList<>();
    }

    public void brokerMessage(ClientNode sender, String message) {
        for (ClientNode client : clients) {
            client.receiveMessage(sender.getID(), message);
        }
    }

    public void addClient(ClientNode client) {
        clients.add(client);
    }

    public void removeClient(ClientNode client) {
        clients.remove(client);
    }
}
