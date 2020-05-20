import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;




class UdpClient {
  public static void main(String[] args) throws Exception {

    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

    DatagramSocket clientSocket = new DatagramSocket();
    DatagramPacket sendPacket = null;
    DatagramPacket receivePacket = null;
    String sentence = "";
    String username = "";
    InetAddress ipAddress = InetAddress.getByName("localhost");
    String hostIP = ipAddress.getHostAddress();
    String hostName = ipAddress.getHostName();
    String toServer;
    String message;
    int state = 0;
    boolean game = true;
    int player = 0;
    int priority = 0;
    byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];


    while (game) {
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
          clientSocket.send(sendPacket); //SENDS username to the server
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
          System.out.println(state);
        break;
        case 1: // GAMEPLAY


          //empties the arrays
          Arrays.fill(sendData, (byte) 0 );
          Arrays.fill(receiveData, (byte) 0 );
          receivePacket = new DatagramPacket(receiveData, receiveData.length);
          clientSocket.receive(receivePacket);
          sentence = new String(receivePacket.getData());
          System.out.println(sentence);
          if(sentence.substring(0,2).toUpperCase().equals("IT")){
            state = 2;
            break;
          } //all words have been guessed and instead of receiving next word
            //proceed to ending for scoring

          Arrays.fill(receiveData, (byte) 0 );
          receivePacket = new DatagramPacket(receiveData, receiveData.length);
          clientSocket.receive(receivePacket);
          sentence = new String(receivePacket.getData());
          message = sentence.substring(0,1);
          System.out.println(message);
          priority = Integer.parseInt(message);

          //the player loops for each word
          switch(priority){
            case 0: //you are the player guessing first
              while(true){
                Arrays.fill(sendData, (byte) 0 );
                Arrays.fill(receiveData, (byte) 0 );
                while(true){
                  System.out.print("Make your letter guess: ");
                  message = inFromUser.readLine();
                  if(message.length() > 1){
                    System.out.println("Must only be 1 character!");
                  }
                  else{
                    break;
                  }
                }
                sendData = message.getBytes();
                sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 8888);
                clientSocket.send(sendPacket);
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                sentence = new String(receivePacket.getData());
                System.out.println(sentence);
                if(sentence.substring(0,3).toUpperCase().equals("YEY")){
                  break;
                } //this means the word has been fully guessed and it is time to move to next

                //waiting for other player to send guess to server
                if(player == 1){
                  System.out.println("\nWaiting for Player 2 to guess...");

                }
                else{
                  System.out.println("\nWaiting for Player 1 to guess...");

                }
                //RECEIVE and DISPLAY other player's guess and result
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                sentence = new String(receivePacket.getData());
                System.out.println(sentence);
                if(sentence.substring(0,3).toUpperCase().equals("YEY")){
                  break;
                } //this means word has been fully guessed and it's time to move to next
              }



            break;
            case 1: //is the player going "second", so waits for player 1, then makes
                    //their guess
              while(true){
                if(player == 1){
                  System.out.println("\nWaiting for Player 2 to guess...");

                }
                else{
                  System.out.println("\nWaiting for Player 1 to guess...");

                }
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                sentence = new String(receivePacket.getData());
                System.out.println(sentence);
                if(sentence.substring(0,3).toUpperCase().equals("YEY")){
                  break;
                }

                while(true){
                  System.out.print("Make your letter guess: ");
                  message = inFromUser.readLine();
                  if(message.length() > 1){
                    System.out.println("Must only be 1 character!");
                  }
                  else{
                    break;
                  }
                }
                sendData = message.getBytes();
                sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 8888);
                clientSocket.send(sendPacket);
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                sentence = new String(receivePacket.getData());
                System.out.println(sentence);
                if(sentence.substring(0,3).toUpperCase().equals("YEY")){
                  break;
                }
              }

            break;
          }


        break;
        case 2: //all words have been finished and the game is OVER

          System.out.println("the end is here");
          /**
          Arrays.fill(receiveData, (byte) 0 );


          receivePacket = new DatagramPacket(receiveData, receiveData.length);
          clientSocket.receive(receivePacket);
          sentence = new String(receivePacket.getData());
          System.out.println(sentence);
          **/
          Arrays.fill(receiveData, (byte) 0 );

          receivePacket = new DatagramPacket(receiveData, receiveData.length);
          clientSocket.receive(receivePacket);
          sentence = new String(receivePacket.getData());
          int finale = Integer.parseInt(sentence.substring(0,1));
          if(finale == 0){
            System.out.println("Hmm, curious... It would seem you have both... tied.");

          } else if(finale == player){
            System.out.println("Congratulations, " +username+ ", you are the victor!");

          } else{
            System.out.println("Aww... It would appear you have been bested :\'c");

          }
          System.out.println("Play again? (yes/no)");
          message = inFromUser.readLine();
          sendData = message.getBytes();
          sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 8888);
          clientSocket.send(sendPacket);
          System.out.println("The other player is thinking...");
          receivePacket = new DatagramPacket(receiveData, receiveData.length);
          clientSocket.receive(receivePacket);
          sentence = new String(receivePacket.getData());
          if(!sentence.substring(0,3).toUpperCase().equals("YOU")){


            game = false;
          }
          System.out.println(sentence);
          state = 1;






        break;



      }



      //sentence = inFromUser.readLine();

    }
    System.out.println("CLOSING SOCKET...");
    clientSocket.close();
  }

}
