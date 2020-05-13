import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;


class UdpServer {
  public static void main(String[] args) throws Exception {
    byte[] receiveData = new byte[1024];
    byte[] sendData  = new byte[1024];

    String display = "";
    String scores = "";
    String currentWord = "";
    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    DatagramPacket sendPacket = null;
    DatagramSocket serverSocket = null;
    InetAddress address1 = null;
    InetAddress address2 = null;
    String sentence;
    String name1="";
    String name2="";
    int score1;
    int score2;
    int port1 = 0;
    int port2 = 0;
    int state = 0;
    int num = 1;

    String ind;
    String useless = "general kenobi";
    //prepare the words
    String[] words = {"hello", "food", "amazing", "yummy", "rainbow", "magic"};
    String[] letters;
    boolean[][] checker = new boolean[words.length][];
    String[][] letterList = new String[words.length][];
    int counter = 0;
    for(String word : words){
      letters = word.split("(?!^)");
      letterList[counter] = letters;
      boolean[] checkList = new boolean[letters.length];
      checker[counter] = checkList;
      Arrays.fill(checkList, Boolean.FALSE);
      counter++;

    }
    counter = 0;







    try {
      serverSocket = new DatagramSocket(8888);
    } catch (Exception e) {
      System.out.println("Failed to open UDP socket");
      System.exit(0);
    }



    while (true) {
      switch(state){
        case 0:  //clients are connecting
          if(num == 1){
            System.out.println("Waiting for Player 1...");
          }

          serverSocket.receive(receivePacket);
          sentence = new String(receivePacket.getData());
          if(num == 1){
            port1 = receivePacket.getPort();
            address1 = receivePacket.getAddress();
          }
          else{
            port2 = receivePacket.getPort();
            address2 = receivePacket.getAddress();
          }

          System.out.println("Player "+num+" connected.");
          //get all the info
          if(sentence.substring(0,4).toUpperCase().equals("HELO")){
            //SEND player number
            if(num == 1){
              ind = "1";
            }
            else{
              ind = "2";
            }

            sendData = ind.getBytes();
            if(num == 1){
              sendPacket = new DatagramPacket(sendData, sendData.length, address1, port1);

            }
            else{
              sendPacket = new DatagramPacket(sendData, sendData.length, address2, port2);

            }
            serverSocket.send(sendPacket);
            //CLEAR byte array
            Arrays.fill(receiveData, (byte) 0 );
            //receive Player USERNAME
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            sentence = new String(receivePacket.getData());
            if(num == 1){
              name1 = sentence.substring(0, 8);
              System.out.println("Player "+num+" username: "+name1);

            }
            else{
              name2 = sentence.substring(0, 8);
              System.out.println("Player "+num+" username: "+name2);
              sendData = name2.getBytes();
              sendPacket = new DatagramPacket(sendData, sendData.length, address1, port1);
              serverSocket.send(sendPacket);
              sendData = name1.getBytes();

              sendPacket = new DatagramPacket(sendData, sendData.length, address2, port2);

              serverSocket.send(sendPacket);

            }
            if(num == 1){
              System.out.println("1 player connected, waiting for 2nd...");
              num++;
            }
            else{
              System.out.println("Both players connected. Beginning game.");
              state = 1;
            }

          }
        break;
        case 1: //the game
          score1 = 0;
          score2 = 0;


          for(int i = 0; i < letterList.length; ++i){
            Arrays.fill(sendData, (byte) 0 );
            Arrays.fill(receiveData, (byte) 0 );

            scores = "\n\n" + name1 + ": " + score1 + "\n" + name2 + ": " + score2 + "\n";
            for(int j = 0; j < letterList[i].length; ++j){
              if(checker[i][j]){
                currentWord = currentWord + letterList[i][j];
              }
              else{
                currentWord = currentWord + "_";
              }
              currentWord = currentWord + " ";
            }
            display = scores + "\nCurrent Word: " + currentWord;
            System.out.println("The word is: " + words[i]);
            sendData = display.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, address1, port1);
            serverSocket.send(sendPacket);
            sendPacket = new DatagramPacket(sendData, sendData.length, address2, port2);
            serverSocket.send(sendPacket);
            if(i%2 == 0){
              useless = "0";
              sendData = display.getBytes();
              sendPacket = new DatagramPacket(sendData, sendData.length, address1, port1);
              serverSocket.send(sendPacket);
              useless = "1";
              sendData = display.getBytes();
              sendPacket = new DatagramPacket(sendData, sendData.length, address1, port2);
              serverSocket.send(sendPacket);
            }
            else{
              useless = "1";
              sendData = display.getBytes();
              sendPacket = new DatagramPacket(sendData, sendData.length, address1, port1);
              serverSocket.send(sendPacket);
              useless = "0";
              sendData = display.getBytes();
              sendPacket = new DatagramPacket(sendData, sendData.length, address1, port2);
              serverSocket.send(sendPacket);
            }




            while(true){
              int addScore = 0;
              String guess;
              display = "";
              scores = "";
              currentWord = "";
              receivePacket = new DatagramPacket(receiveData, receiveData.length);
              serverSocket.receive(receivePacket);
              sentence = new String(receivePacket.getData());
              guess = sentence.substring(0,1);
              for(int j = 0; j < letterList[i].length; ++j){
                if(guess.equals(letterList[i][j])){
                  addScore += 10;
                  checker[i][j] = true;
                }
              }
              if(receivePacket.getPort() == port1){
                score1 += addScore;
              }
              else{
                score2 += addScore;
              }
              scores = "\n\n" + name1 + ": " + score1 + "\n" + name2 + ": " + score2 + "\n";
              for(int j = 0; j < letterList[i].length; ++j){
                if(checker[i][j]){
                  currentWord = currentWord + letterList[i][j];
                }
                else{
                  currentWord = currentWord + "_";
                }
                currentWord = currentWord + " ";
              }
              display = scores + "\nCurrent Word: " + currentWord;




            }
          }
        break;


      }



    }
  }
  public static void PrepGame(){

  }
}
