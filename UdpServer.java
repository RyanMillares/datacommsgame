import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UdpServer {
  public static void main(String[] args) throws Exception {
    DatagramSocket serverSocket = null;

    try {
      serverSocket = new DatagramSocket(9876);
    } catch (Exception e) {
      System.out.println("Failed to open UDP socket");
      System.exit(0);
    }
    byte[] receiveData = new byte[1024];
    byte[] sendData  = new byte[1024];

    while (true) {
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      serverSocket.receive(receivePacket);
      String sentence = new String(receivePacket.getData());

      InetAddress ipAddress = receivePacket.getAddress();

      int port = receivePacket.getPort();

      //String capitalizedSentence = sentence.toUpperCase();
      String replyMsg;
      if(sentence.toLowerCase() == "hello there"){
        replyMsg = "General Kenobi!\n";
      }
      else{
        replyMsg = "Uninstalling myself...\n";
      }

      sendData = replyMsg.getBytes();

      DatagramPacket sendPacket =
          new DatagramPacket(sendData, sendData.length, ipAddress, port);

      serverSocket.send(sendPacket);
    }
  }
}
