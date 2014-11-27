package com.nalin;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

class SpellThread implements Runnable{

	ArrayList<String> list = new ArrayList<String>();
	String[] ipList;
	
	public SpellThread(ArrayList<String> l, String[] il)
	{
		list = l;
		ipList = il;
	}
	
public void run() {

    
 

            
               
     
//    private void invoke(String[] ipList0, String[] ipList1) throws NoSuchAlgorithmException {

        //Takes the String as input
    	ArrayList<Integer> answer = null;

        

        

//check on all words for s0 and s1

        
        
        

        for (String eachWordCheck : list)
        {

            
            
            ArrayList<String> oneWord = new ArrayList<String>();

            oneWord.add(0,eachWordCheck);

            SpellRequest request = new SpellRequest(oneWord);

            TTransport transport = null;
            try {

        	   //int timeElapse = System.time();
                int i = 0;

                if (ipList.length - 1 >= (i)) {

                    long startTime = System.currentTimeMillis();
                    long elapsedTime = System.currentTimeMillis() - startTime;

                    //if(transport.isOpen() == false)
                    //elapsed time is to check if the result came back from open port in specified time(100)
                    while (elapsedTime != 100 && ipList.length - 1 >= i) {
                        System.out.println(ipList[i] + " : " + ipList[i + 1]);
                        //time to open the port (5000) ie time out
                        transport = new TSocket(ipList[i], Integer.parseInt(ipList[i + 1]), 5000);
                        System.out.println(transport.isOpen());
                        try {
                            transport.open();
                            System.out.println("It's open");
                            break;
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage()); 
                        }
                        i += 2;
                    }

                    while (transport.isOpen()) {

                        TProtocol protocol = new TBinaryProtocol(transport);
                        SpellService.Client client = new SpellService.Client(protocol);

                        SpellResponse response = client.spellcheck(request);

    					   //outputs answer.
                        // ArrayList <Boolean> flags = new ArrayList<Boolean>();
                        answer = new ArrayList<Integer>();

                        //Convert boolean to integer
                        for (Boolean b : response.is_correct) {
                            if (b == true) {
                                answer.add(1);
                            } else {
                                answer.add(0);
                            }
                        }
    				    	//boolean output(looks better)
                        // System.out.println("response > \n" + response.is_correct);

                        //actual integer answer
                        //System.out.println(eachWordCheck);
                        System.out.println("response > \n" + eachWordCheck + answer);
                        break;
                        //while      
                    }
                    i++;
                    //if
                } else {
                    System.out.println("all servers are exausted. Try Again!!!");
                }
                
                
                
            } 
            
            
            catch (TTransportException e) {
                e.printStackTrace();
            } catch (TException e) {
                e.printStackTrace();
            } finally {
            	
            	
                if (transport != null) {
                    transport.close();
                }

            }
     }
    }
}
//}
  //  String[] ipList;
public class SpellClient{
	
	static String bin;
    String[] ipList;
    static ArrayList <String> word0 = new ArrayList<String>();
    static ArrayList <String> word1 = new ArrayList<String>();
    
    public static char getMD5(String input) throws NoSuchAlgorithmException {
        
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            for (byte b : messageDigest)
            {
            	bin = Integer.toBinaryString(b);
            	
            }
            
          
            
  
		return bin.charAt(bin.length()-1);
		
    
    }
		
	
    public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException {

     //   SpellThread c = new SpellThread();

//List of S0 Servers        
        Scanner ini2 = new Scanner(new FileReader("/home/user/Downloads/ip_address0"));
        System.out.println("taking arg[0] file path for ip address");
        String s2 = null;
        ArrayList<String> list2 = new ArrayList<String>();
        while (ini2.hasNextLine()) {
            s2 = ini2.nextLine();

            StringTokenizer st2 = new StringTokenizer(s2);
            while (st2.hasMoreElements()) {
                String stoken2 = st2.nextToken();
                list2.add(stoken2);
            }
        }
        System.out.println("list2 >>> " + list2);
        ini2.close();
        String[] ipList = list2.toArray(new String[0]);
//		for (int i=0 ; i< ipList.length; i++)
//		System.out.println(ipList[i]);

//ipList for s1 servers    

    Scanner ini3 = new Scanner(new FileReader("/home/user/Downloads/ip_address1"));
        System.out.println("taking arg[0] file path for ip address");
        String s3 = null;
        ArrayList<String> list3 = new ArrayList<String>();
        while (ini3.hasNextLine()) {
            s3 = ini3.nextLine();

            StringTokenizer st3 = new StringTokenizer(s3);
            while (st3.hasMoreElements()) {
                String stoken3 = st3.nextToken();
                list3.add(stoken3);
            }
        }
        System.out.println("list3 >>> " + list3);
        ini3.close();
        String[] ipList1 = list3.toArray(new String[1]); 
        
 //----------------------------------------------------------------    
        
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
        
        for (String eachWordCheck : list)
        {

            
            if( getMD5(eachWordCheck) == '0')
            {

                word0.add(eachWordCheck);
            }

            else

                word1.add(eachWordCheck);    
        }
        
        Thread t1 = new Thread(new SpellThread(word0, ipList));
        Thread t2 = new Thread(new SpellThread(word1,ipList1));
        t1.start();
        t2.start();
   //-----------------------------------------------------------------------------------     	
   //     t1.invoke(ipList, ipList1);

    }
}