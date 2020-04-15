import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;




class MyUdpClient {
  public static void main(String[] args) throws Exception {

    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

    DatagramSocket clientSocket = new DatagramSocket();
    String sentence = "";
    InetAddress ipAddress = InetAddress.getByName("icd.chapman.edu");
    while (true) {
      System.out.println("Type a Sentence");
      sentence = inFromUser.readLine();

      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];

      sendData = sentence.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 9876);

      clientSocket.send(sendPacket);

      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

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
}
