package us.mattgreen;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



/**
 *     Ask the user what type of animal they want to create
 *     Accept the input(s) necessary to instantiate that type of object
 *     Create an object of that type and return it to that last
 */
public class CUI {

    private List<List<String>> newTalkablesInputCorrelations;//Keep alphabetical

    public CUI(List<List<String>> newTalkablesInputCorrelations){
        this.newTalkablesInputCorrelations = newTalkablesInputCorrelations;
    }
    public <T> T getNewTalkableFromUser(){
        T t = null;
        System.out.println("---~{Add An Element}~---"+
            "\nPlease enter the ID associated with the element you would like to add to the list and press enter."+
            "\nID: Option"
        );
        for(List<String> correlation: newTalkablesInputCorrelations){
            System.out.println(correlation.get(0)+": Add a new "+correlation.get(1));
        }
        Scanner talkableEntityInfoRetriever = new Scanner(System.in);
        String entityId;
        boolean dontReturn = true;
        while(dontReturn){
            System.out.print(" ID: ");
            entityId = talkableEntityInfoRetriever.next();
            for(List<String> correlation: newTalkablesInputCorrelations){
                if(correlation.get(0).equals(entityId)){
                    System.out.println("Creating new "+correlation.get(1)+"...");
                    try{
/*                        CODE FOR CREATING OBJECT FROM CLASS BASED ON
                          https://stackoverflow.com/questions/48668919/class-getconstructor-function-parameters and
                          https://stackoverflow.com/questions/1268817/create-new-object-from-a-string-in-java
*/
                        String className = correlation.get(2).substring(correlation.get(2).indexOf(' ')+1);

                        Class<?> classClass = Class.forName(className);
                        Constructor<?> constructorObject = classClass.getConstructors()[0];
                        String constructorRemaining = constructorObject.toString();
                        List<Object> parameters = new ArrayList<>();
                        constructorRemaining = constructorRemaining.substring(constructorRemaining.indexOf('(')+1,constructorRemaining.indexOf(')'))+",";
                        while(constructorRemaining.contains(",")){//May need to change if new classes added with different parameters
                            String parameterClassToWorkWith = constructorRemaining.substring(0, constructorRemaining.indexOf(','));
                            boolean notYetInput = true;
                            if(parameterClassToWorkWith.equals("boolean")){
                                String nextInput;
                                do{
                                    System.out.print(correlation.get(parameters.size()+3));
                                    nextInput = talkableEntityInfoRetriever.next();
                                    if(nextInput.equalsIgnoreCase("y")){
                                        parameters.add(true);
                                    }else{
                                        parameters.add(false);
                                    }
                                    notYetInput = false;
                                }while(notYetInput);
                            }else if(parameterClassToWorkWith.equals("int")){
                                String nextInput = "";
                                do{
                                    System.out.print(correlation.get(parameters.size()+3));
                                    nextInput = talkableEntityInfoRetriever.next();
                                    try{
                                        int input = Integer.valueOf(nextInput);
                                        notYetInput = false;
                                        parameters.add(input);
                                    }catch(NumberFormatException e){
                                        System.out.println("Sorry but that input could not be turned into a whole number, please try again.");
                                    }
                                }while(notYetInput);
                            }else if(parameterClassToWorkWith.equals("java.lang.String")){
                                System.out.print(correlation.get(parameters.size()+3));
                                parameters.add(talkableEntityInfoRetriever.next());
                                dontReturn = false;
                            }else{
                            }
                            constructorRemaining = constructorRemaining.substring(constructorRemaining.indexOf(',')+1);
                        }
                        return ((T) constructorObject.newInstance(parameters.toArray()));
                    }catch(Exception e){//Covers multiple exceptions, unfortunately unspecific
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("Sorry but that ID does relate to anything, please enter a new ID,");
        }
        talkableEntityInfoRetriever.close();
        return t;
    }

}
