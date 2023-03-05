
package crosswordv2;

import java.util.*;
public class CrosswordV2 {

    static Scanner cin = new Scanner(System.in);

    //Enclose domain values in double quotes    
    public static TreeSet<String> convert(String dict){
        TreeSet<String> dictionary = new TreeSet<>();
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
        Iterator<String> it = dictionary.iterator();   
        while (it.hasNext()){
            char x = it.next().toString().charAt(pos);
            keys.add(x);
        }
        return keys;
    }

    //Checks for arc consistency 
    public static TreeSet<String> AC3(ArrayList<String> dictionary, TreeSet<Character> keys, int pos){
        TreeSet<String> result = new TreeSet<>();
        Iterator<Character> keyIT = keys.iterator();        

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

    //Cycles through each pair of variables and checks each arc contingency
    public static HashMap<String, ArrayList<String>> cycle(HashMap<String, ArrayList<String>> compiled){
        System.out.println("Cycle start:");
            for(String key : compiled.keySet()){
            ArrayList<String> final_result = new ArrayList<>();
            if(key.startsWith("A")){
                for(int i = 1; i <= 3; i++) {
                    String Y = "";
                    if(i == 1)
                        Y = "D1";
                    if(i == 2)
                        Y = "D2";
                    if(i == 3)
                        Y = "D3";
                    
                    TreeSet<Character> V1_keys = retrieveKeys(compiled.get(key), i);
                    System.out.println(key + " keys at position "+i + " : "+ V1_keys);
                    for(String V2 : compiled.keySet()){
                        if(V2.startsWith(Y)){
                            int pos = Integer.valueOf(key.substring(key.length() - 1));
                            System.out.println("ARC " +key+ " and "+V2);
                            System.out.println("x: "+ i);
                            System.out.println("y: "+ pos);
                            TreeSet<String> result_tree = AC3(compiled.get(V2),V1_keys,pos);
                            final_result = new ArrayList<> (result_tree);
                            compiled.replace(V2, final_result);;
                            System.out.println("Reduced domain:" + final_result);
                            System.out.println("Reduced domain size: " + final_result.size()+ "\n");
                        }
                    }
                }
                compiled.replace("A1", compiled.get("D1"));                
                compiled.replace("A2", compiled.get("D2"));
                compiled.replace("A3", compiled.get("D3"));
                System.out.println("");
            }
        }
        return compiled;
    }
   

    public static void main(String[] args) {

        String dict_string = "add, ado, age, ago, aid, ail, aim, air, and, any, "
            + "ape, apt, arc, are, ark, arm, art, ash, ask, auk, awe, awl, aye, "
            + "bad, bag, ban, bat, bee, boa, ear, eel, eft, far, fat, fit, lee, "
            + "oaf, rat, tar, tie";

        TreeSet<String> dict_set = convert(dict_string);

        ArrayList<String> A1 = new ArrayList<String>( dict_set );
        ArrayList<String> A2 = A1;
        ArrayList<String> A3 = A1;
        ArrayList<String> D1 = A1;
        ArrayList<String> D2 = A1;
        ArrayList<String> D3 = A1;

        HashMap<String, ArrayList<String>> compiled = new HashMap<>();
        compiled.put("A1", A1);          
        compiled.put("A2", A2); 
        compiled.put("A3", A3);        
        compiled.put("D1", D1);        
        compiled.put("D2", D2);        
        compiled.put("D3", D3);        
       
        //Cycle through dictionary 2 times, 1 cycle is not enough, 3 becomes redundant
        compiled = cycle(compiled);
        compiled = cycle(compiled);

        System.out.println("");
        for(String key : compiled.keySet()){
            System.out.println(key + ":" + compiled.get(key));
        }
    }
    
}
