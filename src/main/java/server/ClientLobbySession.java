package server;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Queue;

import javafx.application.Platform;
import main.Client;
import utils.Input;

public class ClientLobbySession {

  private Queue<String> clientIn;
  private Queue<Input> keypressQueue;
  private InetAddress serverIP;
  private ClientGameplayHandler handler;
  private Client client;
  private String clientName;
  private String[] playerNames = new String[5];
  private volatile boolean gameStarted = false;

  private Socket soc;
  private Socket ss = new Socket();
  private ServerSocket serverSocket;
  private PrintWriter out;
  private BufferedReader in;

  Thread joiner =
          new Thread() {
            @Override
            public void run() {
              super.run();
              while (!Thread.currentThread().isInterrupted()) {
                try {
                  System.out.println("Getting the server address");
                  MulticastSocket socket = new MulticastSocket(NetworkUtility.CLIENT_M_PORT);
                  InetAddress group = NetworkUtility.GROUP;
                  Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                  while (interfaces.hasMoreElements()) {
                    NetworkInterface iface = interfaces.nextElement();
                    if (iface.isLoopback() || !iface.isUp()) {
                      continue;
                    }

                    Enumeration<InetAddress> addresses = iface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                      InetAddress addr = addresses.nextElement();
                      socket.setInterface(addr);
                      socket.joinGroup(group);
                    }
                  }

                  byte[] buf = new byte[256];
                  DatagramPacket packet = new DatagramPacket(buf, buf.length);
                  socket.receive(packet);
                  System.out.printf("Server Address: " + packet.getAddress());
                  serverIP = packet.getAddress();

                  soc = new Socket(serverIP, NetworkUtility.SERVER_DGRAM_PORT);
                  out = new PrintWriter(soc.getOutputStream());
                  in = new BufferedReader(new InputStreamReader(soc.getInputStream()));

                  String str = NetworkUtility.PREFIX + "CONNECT" + NetworkUtility.SUFFIX;
                  out.println(str);
                  System.out.println("SENT CONNECT TO SERVER");
                  out.println(clientName);
                  System.out.println("SENT CLIENT NAME: " + clientName);
                  out.flush();

                  String r = in.readLine();
                  int id = Integer.parseInt(r);
                  client.setId(id);

                  r = in.readLine();
                  int MIPID = Integer.parseInt(r);
                  client.setMIP(MIPID);
                  r = in.readLine();
                  if (r.equals("SUCCESS")) {
                    System.out.println("Server connection success");
                  }
                  gameStarter.start();
                  Thread.currentThread().interrupt();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            }
          };

  Thread gameStarter = new Thread(() -> {

    try {
      System.out.println("About to set up client game start channels");
      serverSocket = new ServerSocket(NetworkUtility.CLIENT_DGRAM_PORT);
      ss = serverSocket.accept();
      ss.setReuseAddress(true);
      BufferedReader gameIn = new BufferedReader(new InputStreamReader(ss.getInputStream()));
      System.out.println("Waiting for game start message");
      // get other player names
      String r = gameIn.readLine();
      System.out.println("Start game msg -> " + r);
      if (r.equals("START GAME")) {
        for (int i = 0; i < 5; i++) {
          playerNames[i] = gameIn.readLine();
          System.out.println("NAME: " + playerNames[i]);
        }
        gameStarted = true;
        handler = new ClientGameplayHandler(serverIP, keypressQueue, clientIn);
        client.setPlayerNames(playerNames);
        if (!client.isHost) {
          Platform.runLater(() -> client.startMultiplayerGame());
          shutdownTCP();
        }
      }

      gameIn.close();
      ss.close();
    } catch (IOException e) {
      if (ss != null && !ss.isClosed()) {
        try {
          ss.close();
        } catch (IOException err)
        {
          err.printStackTrace(System.err);
        }
      }
      e.printStackTrace();

      if (serverSocket != null && !serverSocket.isClosed()) {
        try {
          serverSocket.close();
        } catch (IOException err)
        {
          err.printStackTrace(System.err);
        }
      }
    }

  });

  public ClientLobbySession(
          Queue<String> clientIn, Queue<Input> keypressQueue, Client client, String clientName)
          throws IOException {

    this.clientIn = clientIn;
    this.keypressQueue = keypressQueue;
//    this.serverIP = NetworkUtility.getServerIP();
    this.client = client;
    this.clientName = clientName;
    joiner.start();
  }

  public void leaveLobby() {
    out.write(NetworkUtility.DISCONNECT);
    shutdownTCP();
    joiner.interrupt();
    gameStarter.interrupt();

  }

  private void shutdownTCP() {
    try {
      out.close();
      if (ss != null && !ss.isClosed()) {
        try {
          ss.close();
        } catch (IOException err)
        {
          err.printStackTrace(System.err);
        }
      }

      if (serverSocket != null && !serverSocket.isClosed()) {
        try {
          serverSocket.close();
        } catch (IOException err)
        {
          err.printStackTrace(System.err);
        }
      }
      in.close();
      soc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean isGameStarted() {
    return gameStarted;
  }
}
