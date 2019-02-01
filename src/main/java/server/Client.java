package server;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Client class which creates the appropriate senders and starts them.
 */
public class Client {

    public Queue<String> outgoingQueue;
    private Queue<String> incomingQueue;

    private Queue<String> positionQueue;
    private Queue<String> collisionQueue;
    private BlockingQueue<Integer> keypressQueue;

    private Thread outgoingPacketManager;
    private Thread incomingPacketManager;

    private PacketSender sender;
    private PacketReceiver receiver;

    public Client() throws IOException {
        outgoingQueue = new ConcurrentLinkedQueue<>();
        incomingQueue = new ConcurrentLinkedQueue<>();
        keypressQueue = new LinkedBlockingDeque<>();
        positionQueue = new LinkedBlockingDeque<>();
        collisionQueue = new LinkedBlockingDeque<>();
        initialisePacketManagers();

        outgoingQueue.add("POS");
        this.sender =
            new PacketSender(NetworkUtility.GROUP, NetworkUtility.SERVER_PORT, this.outgoingQueue);
        this.receiver =
            new PacketReceiver(NetworkUtility.GROUP, NetworkUtility.CLIENT_PORT,
                this.incomingQueue);
        this.incomingPacketManager.start();
        this.outgoingPacketManager.start();
        this.receiver.start();
        this.sender.start();
    }

    public static void main(String[] args) throws IOException {

        Client c = new Client();
        for (int i = 0; i < 100; i++) {
            c.outgoingQueue.add("Sent: " + i);
        }
    }

    /**
     * Initialises the incoming and outgoing packet managers
     */
    private void initialisePacketManagers() {
        // puts inputs from queues into the outgoing queue
        this.outgoingPacketManager =
            new Thread() {
                public void run() {
                    Integer key;
                    while (!isInterrupted()) {
                        try {
                            key = keypressQueue.take();
                            outgoingQueue.add(key.toString());
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            };

        // reads inputs from the incoming queue into the relevant queue - position queue or collision
        // queue
        this.incomingPacketManager =
            new Thread() {
                public void run() {
                    while (!isInterrupted()) {
                        try {
                            if (incomingQueue.isEmpty()) {
                                continue;
                            }
                            String data = incomingQueue.poll();

                            if (data.startsWith(NetworkUtility.POSITION_CODE)) {
                                positionQueue
                                    .add(data.substring(NetworkUtility.POSITION_CODE.length()));
                                System.out.println("Got position instruction from server");
                            } else if (data.startsWith(NetworkUtility.COLLISIONS_CODE)) {
                                collisionQueue
                                    .add(data.substring(NetworkUtility.COLLISIONS_CODE.length()));
                                System.out.println("Got collision instruction from server");
                            } else if (data.startsWith(NetworkUtility.STOP_CODE)) {
                                receiver.shutdown();
                                sender.shutdown();
                                outgoingPacketManager.interrupt();
                                incomingPacketManager.interrupt();
                                System.out.println("Recieved Stop Instruction");
                            } else {
                                throw new Exception();
                            }
                            Thread.sleep(50);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            System.out.println("Argument in incoming queue had invalid code");
                        }
                    }
                }
            };
    }
}
