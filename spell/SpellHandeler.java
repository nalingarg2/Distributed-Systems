//nalin garg[18665753/ng1181@nyu.edu]
package NalinSpell.src;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//import org.DistributedSystemNalin.SpellService.Iface;
import org.apache.thrift.TException;

//SpellHandeler checks whether the input is right or wrong from client
public class SpellHandeler implements SpellService.Iface {

    @Override
    public SpellResponse spellcheck(SpellRequest request) throws TException {
        
    	//Debug and check what the input is given
    	List<String> listRequest = request.to_check;
        System.out.println("listRequest > " + listRequest);


        SpellResponse response = new SpellResponse(new ArrayList<Boolean>());
        //HashMap<String, Boolean> responseList = new HashMap<String, Boolean>();
        
        //Place where dictionary is stored
        String filename = "/home/user/dictionary.txt";
        File file = new File(filename);

        try {
        	//read dictionary as each word and store in arraylist
            Scanner in = new Scanner(file);
            List<String> dictionaryWords = new ArrayList<String>();
            while (in.hasNextLine()) {
                dictionaryWords.add(in.nextLine());

            }
            in.close();
            
            //if false then loop breaks and writes true to addToIs_correct()method
            for (String l : listRequest) {
                boolean hasIt = false;
                for (String dw : dictionaryWords) {
                    hasIt = l.equalsIgnoreCase(dw);
                    if (hasIt) {
                        break;
                    }
                }
                response.addToIs_correct(hasIt);
            }
            

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //return response
        return response;
    }
}
