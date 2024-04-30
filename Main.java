public class Main {
    public static void main(String[] args) {
        // Create a Star network
        Star starNetwork = new Star();

        // Create a few client nodes
        ClientNode client1 = new ClientNode("Client1", starNetwork.centralNode);
        ClientNode client2 = new ClientNode("Client2", starNetwork.centralNode);
        ClientNode client3 = new ClientNode("Client3", starNetwork.centralNode);

        // Insert client nodes into the network
        starNetwork.insertNode(client1);
        starNetwork.insertNode(client2);
        starNetwork.insertNode(client3);

        // Send a message from client1
        client1.send("Hello from Client1!");

        // Delete a client node from the network
        starNetwork.deleteNode(client2);

        // Send a message from client2 (deleted node)
        client2.send("This message won't be received!");

        // Send a message from client3
        client3.send("Hello from Client3!");
    }
}
