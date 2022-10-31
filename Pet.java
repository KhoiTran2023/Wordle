import java.util.ArrayList;
public class PetRunner {
    public static void main(String args[])
    {
        Pet cat = new Cat("Catty1");
        cat.speak();
    }
}

public class Pet
{
    private String myName;

    public Pet (String name)
    { myName = name; }

   public String getName()
   {  return myName;  }

   public String speak() { }
}
//The subclass Dog has the partial declaration shown below.

public class Dog extends Pet
{
    public Dog (String name)
    {
        super(name);
    }

    public String speak()
    {
        return "dog-sound";
    }
}

public class LoudDog extends Dog
{
    public LoudDog(String name)
    {
        super(name);
    }

    public String speak()
    {
        return "dog-sound" + " dog-sound";
    }
}


public class Cat extends Pet {
    public Cat(String name)
    {
        super(name);
        System.out.println(speak());

    }

    public String speak()
    {
        return "meow";
    }
}