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
    boolean game = true;
    int port1 = 0;
    int port2 = 0;
    int state = 0;
    int num = 1;
    String reply1 = "";
    String reply2 = "";


    String ind;
    String useless = "general kenobi";
    //prepare the words
  //  String[] words = {"hello", "food", "amazing", "yummy", "rainbow", "magic"};
    String[] words = {"hi", "no"};
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



    while (game) {
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
            display = "";
            scores = "";
            useless = "";
            currentWord = "";
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
              sendData = useless.getBytes();
              sendPacket = new DatagramPacket(sendData, sendData.length, address1, port1);
              serverSocket.send(sendPacket);
              useless = "1";
              sendData = useless.getBytes();
              sendPacket = new DatagramPacket(sendData, sendData.length, address1, port2);
              serverSocket.send(sendPacket);
            }
            else{
              useless = "1";
              sendData = useless.getBytes();
              sendPacket = new DatagramPacket(sendData, sendData.length, address1, port1);
              serverSocket.send(sendPacket);
              useless = "0";
              sendData = useless.getBytes();
              sendPacket = new DatagramPacket(sendData, sendData.length, address1, port2);
              serverSocket.send(sendPacket);
            }




            while(true){
              String good = "";
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
                  if(checker[i][j]){
                    addScore = -1;
                    break;
                  }
                  addScore += 10;
                  checker[i][j] = true;

                }
              }
              if(receivePacket.getPort() == port1){
                if(addScore >= 0){
                  score1 += addScore;

                }
                good = "     "+name1 + " guessed: " +guess+ "...";
              }
              else{
                if(addScore >= 0){
                  score2 += addScore;

                }
                good = "     "+name2 + " guessed: " +guess+ "...";

              }
              if(addScore > 0){
                good += "\nCORRECT!\n";
              }
              else if(addScore == 0){
                good += "\nSadly, "+guess+" is not in the word...\n";
              }
              else{
                good += guess + " has already been guessed!\n";
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
              display = scores + "\nCurrent Word: " + currentWord + good;
              if(!isDone(checker[i])){
                sendData = display.getBytes();
                sendPacket = new DatagramPacket(sendData, sendData.length, address1, port1);
                serverSocket.send(sendPacket);
                sendPacket = new DatagramPacket(sendData, sendData.length, address2, port2);
                serverSocket.send(sendPacket);
              }
              else{
                if(receivePacket.getPort() == port1){
                  display = "Yey!  " + name1 + "finished the word! It was: " + words[i];

                }
                else{
                  display = "Yey!  " + name2 + "finished the word! It was: " + words[i];


                }
                System.out.println("Next word!");
                display += "\nNext word!";
                sendData = display.getBytes();
                sendPacket = new DatagramPacket(sendData, sendData.length, address1, port1);
                serverSocket.send(sendPacket);
                sendPacket = new DatagramPacket(sendData, sendData.length, address2, port2);
                serverSocket.send(sendPacket);
                break;

              }






            }
          }
          String finale = "It would seem you have reached the end...\n";
          finale += "Your scores are as follows...\n";
          finale += name1 + ": " + score1 + "\n";
          finale += name2 + ": " + score2 + "\n";
          sendData = finale.getBytes();
          sendPacket = new DatagramPacket(sendData, sendData.length, address1, port1);
          serverSocket.send(sendPacket);
          sendPacket = new DatagramPacket(sendData, sendData.length, address2, port2);
          serverSocket.send(sendPacket);
          Arrays.fill(sendData, (byte) 0 );
          Arrays.fill(receiveData, (byte) 0 );
          if(score1 > score2){
            useless = "1";
          }
          else if(score1 < score2){
            useless = "2";
          }
          else{
            useless = "0";
          }
          System.out.println("hi");
          sendData = useless.getBytes();
          sendPacket = new DatagramPacket(sendData, sendData.length, address1, port1);
          serverSocket.send(sendPacket);
          System.out.println("hi");

          sendPacket = new DatagramPacket(sendData, sendData.length, address2, port2);
          serverSocket.send(sendPacket);
          System.out.println("hi");

          receivePacket = new DatagramPacket(receiveData, receiveData.length);
          serverSocket.receive(receivePacket);
          System.out.println("hi");

          sentence = new String(receivePacket.getData());
          boolean p1 = (sentence.substring(0,3).toUpperCase().equals("YES"));
          receivePacket = new DatagramPacket(receiveData, receiveData.length);
          serverSocket.receive(receivePacket);
          sentence = new String(receivePacket.getData());
          boolean p2 = (sentence.substring(0,3).toUpperCase().equals("YES"));
          if(p1 && p2){
            useless = "You both want to play? Then BACK you go!";
            sendData = useless.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, address1, port1);
            serverSocket.send(sendPacket);
            sendPacket = new DatagramPacket(sendData, sendData.length, address2, port2);
            serverSocket.send(sendPacket);

          }
          else{
            useless = "One or both of you do not wish to play again... Thanks for playing!";
            sendData = useless.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, address1, port1);
            serverSocket.send(sendPacket);
            sendPacket = new DatagramPacket(sendData, sendData.length, address2, port2);
            serverSocket.send(sendPacket);
            game = false;
          }



          /**
          String finale = "It would seem you have reached the end...\n";
          finale += "Your scores are as follows...\n";
          finale += name1 + ": " + score1;
          finale += name2 + ": " + score2;
          if(score1 > score2){
            finale += "Congratulations, "+ name1 + ", it would appear you are victorious.";

          }
          else if(score1 < score2){
            finale += "Congratulations, "+ name2 + ", it would appear you are victorious.";

          }
          else{
            finale += "Curious... it would appear the two of you have tied..."
          }**/


        break;
        case 2:
        break;


      }



    }
    System.out.println("CLOSING SOCKET...");
    serverSocket.close();
  }
  public static boolean isDone(boolean[] checks){
    for(int i = 0; i < checks.length; ++i){
      if(!checks[i]){
        return false;
      }
    }
    return true;
  }
}
