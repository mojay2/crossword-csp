
package crosswordv2;

import java.util.*;
public class CrosswordV2 {

    static Scanner cin = new Scanner(System.in);

    //Enclose domain values in double quotes
    public static ArrayList<String> convert(String dict){
        ArrayList<String> dictionary = new ArrayList<>();
        String[] dictArr = dict.split(",");
        for(int i = 0; i<dictArr.length; i++){
            dictArr[i] = "\""+dictArr[i].trim()+"\"";
            dictionary.add(dictArr[i]);
        }
        return dictionary;
    }

    //Retrieves all the nth letters of every word of the dictionary
    public static TreeSet<Character> retrieveKeys(ArrayList<String> dictionary , int pos){
        TreeSet<Character> keys = new TreeSet<>();
        Iterator it = dictionary.iterator();   
        while (it.hasNext()){
            char x = it.next().toString().charAt(pos);
            keys.add(x);
        }
        return keys;
    }

    //Checks for arc consistency 
    public static TreeSet<String> AC3(ArrayList<String> dictionary, TreeSet<Character> keys, int pos){
        TreeSet<String> result = new TreeSet<>();
        Iterator keyIT = keys.iterator();        

        while(keyIT.hasNext()){
            char key = (Character) keyIT.next();
            for(int i = 0; i < dictionary.size(); i++){
                String x = dictionary.get(i);
                if(x.charAt(pos) == key){
                    result.add(x);
                }    
            }
        }
        return result;
    }

    //Removes the double quotes of the arraylist
    public static TreeSet<String> revert(TreeSet<String> result){
        TreeSet<String> reverted = new TreeSet<>();
        for (String x : result) {
            String revert = x.substring(1, x.length()-1);
            reverted.add(revert);
        }     
        return reverted;
    }


    public static void main(String[] args) {
        while(true){
            //Enter domain values separated by commas, e.g: add, ado, lee, bee, art, arc
            System.out.println("\nEnter domain values of Var 1:");
            String StringX = cin.nextLine();
            ArrayList<String> X = convert(StringX);
            System.out.println("Enter domain values of Var 2:");
            String StringY = cin.nextLine();
            ArrayList<String> Y = convert(StringY);
            System.out.println("\n***CONSTRAINT: For every Xth position of Variable 1, MUST have SOME Variable 2 with the same Yth Letter ***");
            System.out.println("Enter X:");
            int posX = cin.nextInt();
            System.out.println("Enter Y:");
            int posY = cin.nextInt();

            TreeSet<Character> keys = retrieveKeys(X, posX);        
            TreeSet<String> result = AC3(Y, keys, posY );        
            TreeSet<String> resultFinal = revert(result);

            System.out.println("Keys of Variable 1:" + keys);        
            System.out.println("Reduced Domain of Variable 2: "+ resultFinal);        
            System.out.println("Size of new Domain: " + result.size());
            cin.nextLine();
        }
    }
    
}