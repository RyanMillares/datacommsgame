import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;




class UdpClient {
  public static void main(String[] args) throws Exception {

    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

    DatagramSocket clientSocket = new DatagramSocket();
    DatagramPacket sendPacket = null;
    DatagramPacket receivePacket = null;
    String sentence = "";
    InetAddress ipAddress = InetAddress.getByName("localhost");
    String hostIP = ipAddress.getHostAddress();
    String hostName = ipAddress.getHostName();
    while (true) {
      System.out.println(hostIP + " " + hostName);
      System.out.println("\nType a Sentence");
      sentence = inFromUser.readLine();
      System.out.println(sentence);
      System.out.println(ReturnMsg(sentence));
      System.out.println("1");


      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];
      System.out.println("2");


      sendData = sentence.getBytes();
      System.out.println("3");

      sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 8888);
      System.out.println("4");


      clientSocket.send(sendPacket);
      System.out.println("5");


      receivePacket = new DatagramPacket(receiveData, receiveData.length);
      int test = sendPacket.getPort();
      System.out.println(test);
      System.out.println("6");


      clientSocket.receive(receivePacket);
      System.out.println("7");


      String modifiedSentence = new String(receivePacket.getData());
      System.out.println("8");


      System.out.println("FROM SERVER:" + modifiedSentence);
      System.out.println("9");

      if (sentence.toUpperCase().equals("GOODBYE")) {
        break;
      }
      //sentence = inFromUser.readLine();

    }

    clientSocket.close();
  }
  public static String ReturnMsg(String sentence){
    String replyMsg;
    if(sentence.toLowerCase().equals("hello there")){
      return "testmsg1\n";
      //System.out.println("test1");
    }
    else{
      return "testMsg2\n";
      //System.out.println("test2");
    }

  }
}
