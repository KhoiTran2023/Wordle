import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;

public class WordleEx {
    private ArrayList<Character> deadLetters;
    private String deadLetter = "";
    private ArrayList<Character> goodLetters;
    private String goodLetter = "";
    private ArrayList<String> words;
    private File file;
    
    public WordleEx(File file1) {
        deadLetters = new ArrayList<Character>();
        goodLetters = new ArrayList<Character>();
        words = new ArrayList<String>();
 
    }

    public void addLetters() throws IOException
    {
        Scanner dict = new Scanner(new File("words.dat"));

        while(dict.hasNext())
        {
            words.add(dict.nextLine());
        }
    }

    public void addValid(String goodLetter)
    {
        for(int x = 0; x<goodLetter.length();x++)
        {
            goodLetters.add(goodLetter.charAt(x));
        }
    }

    public void addInvalid(String deadLetter)
    {
        for(int x = 0; x<deadLetter.length(); x++)
        {
            deadLetters.add(deadLetter.charAt(x));
        }
    }

    public String findWord()
    {
        String word = "";
        for(int i = 0; i<words.size();i++)
        {
            for(int x = 0; x<words.get(i).length();x++)
            {
                int count = 0;
                for(int o = 0;o<goodLetters.size();o++)
                {
                    if(words.get(i).charAt(x)==goodLetters.get(o))
                    {
                        count++;
                        continue;
                    }
                    while(count == goodLetters.size())
                    {
                        for(int y = 0; y<deadLetters.size();y++)
                        {
                            for(int z = 0; z<words.get(i).length();z++)
                            {
                                if(deadLetters.get(y)==words.get(i).charAt(z))
                                {
                                    count = 0;
                                }
                            }
                        }
                        word = words.get(i);
                        if(count==goodLetters.size())
                        {
                            return word;
                        }
                    }
                }
            }
        }
        return word;
    }
}