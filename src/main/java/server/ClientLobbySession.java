package server;

import main.Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class ClientLobbySession {
    
    private Queue<String> positionQueue;
    private Queue<String> collisionQueue;
    private BlockingQueue<Integer> keypressQueue;
    private InetAddress serverIP;
    private ClientGameplayHandler handler;
    private Client client;
    
    
    public ClientLobbySession(Queue<String> positionQueue, Queue<String> collisionQueue,
                              BlockingQueue<Integer> keypressQueue, Client client) throws IOException {
        this.positionQueue = positionQueue;
        this.collisionQueue = collisionQueue;
        this.keypressQueue = keypressQueue;
        this.serverIP = NetworkUtility.getServerIP();
        this.client = client;
        
    }
    
    public void join() {
        
        try {
            DatagramSocket ds = new DatagramSocket(3001);
            
            String str = NetworkUtility.PREFIX + "CONNECT" + NetworkUtility.SUFFIX;
            DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), this.serverIP, NetworkUtility.SERVER_DGRAM_PORT);
            ds.send(dp);
            
            byte[] buf = new byte[1024];
            dp = new DatagramPacket(buf, 1024);
            ds.receive(dp);
            String r = new String(dp.getData(), 0, dp.getLength());
            r = r.replaceAll("\u0000.*", "");
            int id = Integer.parseInt(r);
            this.client.setId(id);
    
    
            buf = new byte[1024];
            dp = new DatagramPacket(buf, 1024);
            ds.receive(dp);
            r = new String(dp.getData(), 0, dp.getLength());
            r = r.replaceAll("\u0000.*", "");
            
            if (r.equals("SUCCESS")) {
                System.out.println("Server connection success");
            }
            
            buf = new byte[1024];
            dp = new DatagramPacket(buf, 1024);
            ds.receive(dp);
            r = new String(dp.getData(), 0, dp.getLength());
            
            if (r.equals("STARTGAME")) {
                handler = new ClientGameplayHandler();
            }
            
            System.out.println(r);
            ds.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) throws IOException {
//        ClientLobbySession c = new ClientLobbySession(new LinkedBlockingQueue<String>(),
//                new LinkedBlockingQueue<String>(), new LinkedBlockingQueue<Integer>());
//        c.join();
    }
}
