package test;

public class TestRunner {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting server...");
        TestServer.startServer();

        System.out.println("Starting client...");
        TestClient.startClient();
    }
}
