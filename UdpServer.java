import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UdpServer {
  public static void main(String[] args) throws Exception {
    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
    DatagramSocket serverSocket = null;
    InetAddress address1 = null;
    InetAddress address2 = null;
    String sentence;
    String name1, name2;
    int port1 = 0;
    int port2 = 0;
    int state = 0;

    String ind;




    try {
      serverSocket = new DatagramSocket(8888);
    } catch (Exception e) {
      System.out.println("Failed to open UDP socket");
      System.exit(0);
    }
    byte[] receiveData = new byte[1024];
    byte[] sendData  = new byte[1024];


    while (true) {
      switch(state){
        case 0:  //first client connects
          serverSocket.receive(receivePacket);
          sentence = new String(receivePacket.getData());
          port1 = receivePacket.getPort();
          address1 = receivePacket.getAddress();
          //get all the info
          if(sentence.substring(0,5).toUpperCase().equals("HELO")){
            ind = "1";

          }


      }

      serverSocket.receive(receivePacket);
      System.out.println("3");

      String
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
