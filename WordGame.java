import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;


public class WordGame{
  public static void main(String[] args){
    Scanner input=new Scanner(System.in);

    int player1Score=0;
    int player2Score=0;
    //have many words to guess
    String correctWord1="hello";
    String correctWord2="food";
    String correctWord3="amazing";
    String correctWord4="why";
    String correctWord5="yummy";
    int arraySize1=5;
    int arraySize2=4;
    int arraySize3=7;
    int arraySize4=3;
    int arraySize5=5;
    boolean playagain=true;

//put correct letters in arraylist to see whether has got all letters correct in a word
//the arraylist size depends on the number of letters in a word
//arraylist for hello
while (playagain=true){
    ArrayList<String> LetterArray1 = new ArrayList<String>(arraySize1);
//arraylist for food
    ArrayList<String> LetterArray2 = new ArrayList<String>(arraySize2);
//arraylist for amazing
    ArrayList<String> LetterArray3 = new ArrayList<String>(arraySize3);
//arraylist for why
    ArrayList<String> LetterArray4 = new ArrayList<String>(arraySize4);
//arraylist for yummy
    ArrayList<String> LetterArray5 = new ArrayList<String>(arraySize5);

    int letterGuessed1=0;
    int letterGuessed2=0;
    int letterGuessed3=0;
    int letterGuessed4=0;
    int letterGuessed5=0;

    //word1 case
    //ask player1 to input letter to guess the word
    //users will keep guessing the same word until they get it right, then can start guessing the next word in game
    while (letterGuessed1<arraySize1){
    System.out.println("player1 enter letter to guess word1");
    String letter1=input.nextLine();
    if (correctWord1.contains(letter1))
    {
      player1Score=player1Score+1;
      LetterArray1.add(letter1);
      letterGuessed1=letterGuessed1+1;
      System.out.println(letterGuessed1);
    }//end if

    //ask player2 to input letter to guess the word
    //player one and player two take turns to guess the same word together
    System.out.println("player2 enter letter to guess word1");
    String letter2=input.nextLine();
    if(correctWord1.contains(letter2))
    {
      player2Score=player2Score+1;
      LetterArray1.add(letter2);
      letterGuessed1=letterGuessed1+1;
    }//end if
  }//end while

  System.out.println("proceed to the second word");
  //word2 case
  while (letterGuessed2<arraySize2){
  System.out.println("player1 enter letter to guess word2");
  String letter3=input.nextLine();
  if (correctWord2.contains(letter3))
  {
    player1Score=player1Score+1;
    LetterArray2.add(letter3);
    letterGuessed2=letterGuessed2+1;
  }//end if

  //ask player2 to input letter to guess the word
  //player one and player two take turns to guess the same word together
  System.out.println("player2 enter letter to guess word2");
  String letter4=input.nextLine();
  if(correctWord2.contains(letter4))
  {
    player2Score=player2Score+1;
    LetterArray2.add(letter4);
    letterGuessed2=letterGuessed2+1;
  }//end if
}//end while

  System.out.println("proceed to the third word");
  //word3 case
  while (letterGuessed3<arraySize3){
  System.out.println("player1 enter letter to guess word3");
  String letter5=input.nextLine();
  if (correctWord3.contains(letter5))
  {
    player1Score=player1Score+1;
    LetterArray3.add(letter5);
    letterGuessed3=letterGuessed3+1;
  }//end if

  //ask player2 to input letter to guess the word
  //player one and player two take turns to guess the same word together
  System.out.println("player2 enter letter to guess word3");
  String letter6=input.nextLine();
  if(correctWord3.contains(letter6))
  {
    player2Score=player2Score+1;
    LetterArray3.add(letter6);
    letterGuessed3=letterGuessed3+1;
  }//end if
}//end while

System.out.println("proceed to the fourth word");
//word4 case
while (letterGuessed4<arraySize4){
System.out.println("player1 enter letter to guess word4");
String letter7=input.nextLine();
if (correctWord4.contains(letter7))
{
  player1Score=player1Score+1;
  LetterArray4.add(letter7);
  letterGuessed4=letterGuessed4+1;
}//end if

//ask player2 to input letter to guess the word
//player one and player two take turns to guess the same word together
System.out.println("player2 enter letter to guess word4");
String letter8=input.nextLine();
if(correctWord4.contains(letter8))
{
  player2Score=player2Score+1;
  LetterArray4.add(letter8);
  letterGuessed4=letterGuessed4+1;
}//end if
}//end while

System.out.println("proceed to the fifth word");
//word5 case
while (letterGuessed5<arraySize5){
System.out.println("player1 enter letter to guess word5");
String letter9=input.nextLine();
if (correctWord5.contains(letter9))
{
  player1Score=player1Score+1;
  LetterArray5.add(letter9);
  letterGuessed5=letterGuessed5+1;
}//end if

//ask player2 to input letter to guess the word
//player one and player two take turns to guess the same word together
System.out.println("player2 enter letter to guess word5");
String letter10=input.nextLine();
if(correctWord5.contains(letter10))
{
  player2Score=player2Score+1;
  LetterArray5.add(letter10);
  letterGuessed5=letterGuessed5+1;
}//end if
}//end while

//compare scores of two players
if(player1Score>player2Score)
{
  System.out.println("player1 wins");
}
else if (player1Score<player2Score)
{
  System.out.println("player2 wins");
}
else{
  System.out.println("draw");
}

System.out.println("would you like to play again? enter 'yes' or 'no'");
String again=input.nextLine();
if(again=="yes")
{
  playagain=true;
}else{
  playagain=false;
}//end else
}//end boolean while


}//end static

}// end class
