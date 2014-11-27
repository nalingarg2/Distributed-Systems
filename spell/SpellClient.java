//nalin garg[18665753/ng1181@nyu.edu]
package NalinSpell.src;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

//SpellClient takes String as Input and pass it to Handeler
public class SpellClient {

    private void invoke() {
    	//Takes the String as input
    	Scanner ini = new Scanner(System.in);
		System.out.println("write sentence with word sepearted with white space");
		String s = ini.nextLine();
		ini.close();
		
		//list stores the string which was input above.
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(s);
		while (st.hasMoreElements()) {
			String stoken = st.nextToken();
			list.add(stoken);
		}

        SpellRequest request = new SpellRequest(list);

        TTransport transport = null;
        try {
            transport = new TSocket("localhost", 7919);
            transport.open();
            
            TProtocol protocol = new TBinaryProtocol(transport);
            SpellService.Client client = new SpellService.Client(protocol);
            SpellResponse response = client.spellcheck(request);
            
            //outputs answer.
           // ArrayList <Boolean> flags = new ArrayList<Boolean>();
            ArrayList <Integer> answer = new ArrayList <Integer>();
            
            //Convert boolean to integer
            for(Boolean b : response.is_correct)
            {
            	if(b == true)
            		answer.add(1);
            	else
            		answer.add(0);
            }
            //boolean output(looks better)
           // System.out.println("response > \n" + response.is_correct);
            
            //actual integer answer
            System.out.println("response > \n" + answer);
            

            
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                transport.close();
            }
            
        }
    }

    public static void main(String[] args) {
    	
        SpellClient c = new SpellClient();
        c.invoke();

    }

}
