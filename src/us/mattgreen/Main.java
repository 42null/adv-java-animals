package us.mattgreen;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private final static FileOutput outFile = new FileOutput("animals.txt");
    private final static FileInput inFile = new FileInput("animals.txt");


    public static void main(String[] args) {
        ArrayList<Talkable> zoo = new ArrayList<>();

        // Lines to Replace Begin Here
//        zoo.add(new Dog(true, "Bean"));
//        zoo.add(new Cat(9, "Charlie"));
//        zoo.add(new Teacher(44, "Stacy Read"));

        List<List<String>> zooAddableTypes1 = new ArrayList<List<String>>(); //List to allow diffrent lengths in the future, name is not asked as it is for teachables which all have a name
        zooAddableTypes1.add(Arrays.asList("1", "Dog", Dog.class.toString(), "Enter Y or y if the dog is friendly, anything else for not friendly: ","Please enter the Dogs name: "));
        zooAddableTypes1.add(Arrays.asList("2", "Cat", Cat.class.toString(), "Enter the amount of mice the cat has caught: ","Please enter the Cats name: "));
        zooAddableTypes1.add(Arrays.asList("3", "Teacher", Teacher.class.toString(), "Enter the age of the teacher: ","Please enter the Teachers name: "));

        CUI userInterface = new CUI(zooAddableTypes1);
        zoo.add(userInterface.getNewTalkableFromUser());

//        Instantiate an object of your user input-gathering class
//        Use that object to add an object to the list

        // End Lines to Replace
        System.out.println(zoo.size());
        for (Talkable thing : zoo) {
            printOut(thing);
        }
        outFile.fileClose();
        inFile.fileRead();
        inFile.fileClose();

        FileInput indata = new FileInput("animals.txt");
        String line;
        while ((line = indata.fileReadLine()) != null) {
            System.out.println(line);
        }
    }

    public static void printOut(Talkable p) {
        System.out.println(p.getName() + " says=" + p.talk());
        outFile.fileWrite(p.getName() + " | " + p.talk());
    }
}
