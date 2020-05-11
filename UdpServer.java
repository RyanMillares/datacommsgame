import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UdpServer {
  public static void main(String[] args) throws Exception {
    System.out.println("im running");
    DatagramSocket serverSocket = null;

    try {
      serverSocket = new DatagramSocket(8888);
    } catch (Exception e) {
      System.out.println("Failed to open UDP socket");
      System.exit(0);
    }
    byte[] receiveData = new byte[1024];
    byte[] sendData  = new byte[1024];


    while (true) {
      System.out.println("1");
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      System.out.println("2");

      serverSocket.receive(receivePacket);
      System.out.println("3");

      String sentence = new String(receivePacket.getData());
      System.out.println("4");


      InetAddress ipAddress = receivePacket.getAddress();

      int port = receivePacket.getPort();


      String replyMsg;
      System.out.println(sentence.toLowerCase());
      if(sentence.toLowerCase().equals("hello there")){
        replyMsg = "testmsg1\n";
        System.out.println("test1");
      }
      else{
        replyMsg = "testMsg2\n";
        System.out.println("test2");
        System.out.println(sentence.length());
      }

      sendData = replyMsg.getBytes();

      DatagramPacket sendPacket =
          new DatagramPacket(sendData, sendData.length, ipAddress, port);

      serverSocket.send(sendPacket);
    }
  }
}
