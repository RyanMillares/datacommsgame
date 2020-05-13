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


      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];



      sendData = sentence.getBytes();


      sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 8888);


      clientSocket.send(sendPacket);



      receivePacket = new DatagramPacket(receiveData, receiveData.length);

      clientSocket.receive(receivePacket);


      String modifiedSentence = new String(receivePacket.getData());

      System.out.println("FROM SERVER:" + modifiedSentence);


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
