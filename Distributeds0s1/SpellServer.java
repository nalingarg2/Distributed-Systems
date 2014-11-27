package com.nalin;
//import org.apache.maven.settings.Server;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

// SpellServer creates Server Socket at 7919
public class SpellServer {
//	@SuppressWarnings("rawtypes")

    private static void start() {
        try {
        	//7919 port 
            TServerSocket serverTransport = new TServerSocket(8080);

            // Read data from the input, process the data through the Handler specified by the user 
            // and writes the data to the output.
//            @SuppressWarnings("unchecked")
            SpellService.Processor processor = new SpellService.Processor(new SpellHandeler());

            //worker thread. Exists seprately from Runnable and callable.
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).
                    processor(processor));
            
           
            System.out.println("Starting server on port 8080 ...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SpellServer srv = new SpellServer();
        srv.start();
    }

}
