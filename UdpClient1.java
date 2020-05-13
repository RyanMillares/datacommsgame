import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;




class UdpClient1 {
  public static void main(String[] args) throws Exception {

    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

    DatagramSocket clientSocket = new DatagramSocket();
    DatagramPacket sendPacket = null;
    DatagramPacket receivePacket = null;
    String sentence = "";
    String username;
    InetAddress ipAddress = InetAddress.getByName("localhost");
    String hostIP = ipAddress.getHostAddress();
    String hostName = ipAddress.getHostName();
    String toServer;
    String message;
    int state = 0;
    int player = 0;
    byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];


    while (true) {
      switch(state){
        // the START mode
        case 0:
          // CONNECT to UDP server
          System.out.println("Connecting to server...");
          toServer = "HELO";
          sendData = toServer.getBytes();
          sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 8888);
          clientSocket.send(sendPacket);
          //receive player number
          receivePacket = new DatagramPacket(receiveData, receiveData.length);
          clientSocket.receive(receivePacket);
          System.out.println("Connection established!");
          sentence = new String(receivePacket.getData());
          message = sentence.substring(0, 1);
          player = Integer.parseInt(message);
          System.out.println("Welcome to WORD COMMS, you are Player "+player);
          System.out.println("\nPlease provide an 8-character username.");
          while(true){
            System.out.print("Username: ");
            username = inFromUser.readLine();
            if(username.length() > 8){
              System.out.println("Too long!\n");
            }
            else{
              System.out.println("Welcome, " + username + "\n");
              break;
            }
          }
          Arrays.fill(sendData, (byte) 0 );
          Arrays.fill(receiveData, (byte) 0 );
          sendData = username.getBytes();
          sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 8888);
          clientSocket.send(sendPacket);
          if(player == 1){
            //WAIT until 2nd player connects
            System.out.println("Waiting for second player...");
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            sentence = new String(receivePacket.getData());
            message = sentence.substring(0, 8);
            System.out.println(message + " has connected! Let the games begin!");
            state = 1;




          }
          else{
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            sentence = new String(receivePacket.getData());
            message = sentence.substring(0, 8);
            System.out.println("Found game with: "+message+"! Let the games begin!");
            state = 1;

          }
          System.out.println(player);
        break;
        case 1: // GAMEPLAY
          switch(player){
            case 1:

            break;
            case 2:

            break;
          }

        break;



      }
      System.out.println("\nType a Sentence");
      sentence = inFromUser.readLine();

      sendData = sentence.getBytes();
      sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 8888);
      clientSocket.send(sendPacket);


      receivePacket = new DatagramPacket(receiveData, receiveData.length);
      int test = sendPacket.getPort();
      System.out.println(test);



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

}
