import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Wordle 
{
    public static void main(String args[]) throws IOException
    {
        File file1 = new File("words.dat");
        WordleEx obj = new WordleEx(file1);
        obj.addLetters();
        String runAgain = "yes";
        Scanner keyboard = new Scanner(System.in);
        Scanner runAgainSc = new Scanner(System.in);


        System.out.println("Run BREAD, TOILS, and MUNCH");
        while(runAgain.equals("yes"))
        {
            String deadLetter = "";
            System.out.println("Enter your dead letters");
            deadLetter = keyboard.nextLine();
            obj.addInvalid(deadLetter);

            String goodLetter = "";
            System.out.println("Enter your valid letters");
            goodLetter = keyboard.nextLine();
            obj.addValid(goodLetter);

            System.out.println("Try " + obj.findWord());

            System.out.println("More words?");
            runAgain = runAgainSc.nextLine();
        }
    }
    

    
}