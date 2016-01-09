package tr.edu.metu.ii.sm.xacml.MongoProxy;

import com.mongodb.BasicDBObject;
import org.bson.BSONObject;

import java.io.*;
import java.net.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;
import tr.edu.metu.ii.sm.xacml.*;
import tr.edu.metu.ii.sm.xacml.pdp.Communicator;

/**
 * Created by omer.dogan on 16/05/2015.
 */
public class MongoDbProxy {
    /**
     * The main method parses arguments and passes them to runServer
     */
    public static void main(String[] args) throws IOException {
        try {

            args = new String[3];
            args[0] = "localhost";
            args[1] = "27777";
            args[2] = "27888";

            // Check the number of arguments
            if (args.length != 3)
                throw new IllegalArgumentException("Wrong number of arguments.");

            // Get the command-line arguments: the host and port we are proxy for
            // and the local port that we listen for connections on
            String host = args[0];
            int remoteport = Integer.parseInt(args[1]);
            int localport = Integer.parseInt(args[2]);
            // Print a start-up message
            System.out.println("Starting proxy for " + host + ":" + remoteport +
                    " on port " + localport);
            // And start running the server
            runServer(host, remoteport, localport);   // never returns
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * This method runs a single-threaded proxy server for
     * host:remoteport on the specified local port.  It never returns.
     *
     * @throws Exception
     **/
    public static void runServer(String host, int remoteport, int localport)
            throws Exception {
        // Create a ServerSocket to listen for connections with
        ServerSocket serverSocket = new ServerSocket(localport);

        System.out.println("Listening on port: " + localport);

        // Create buffers for client-to-server and server-to-client communication.
        // We make one final so it can be used in an anonymous class below.
        // Note the assumptions about the volume of traffic in each direction...
        final byte[] requestBytesBuffer = new byte[1024];
        byte[] reply = new byte[4096];

        // This is a server that never returns, so enter an infinite loop.
        while (true) {
            // Variables to hold the sockets to the client and to the server.
            Socket client = null, server = null;
            try {
                // Wait for a connection on the local port
                client = serverSocket.accept();

                // Get client streams. Make them final so they can
                // be used in the anonymous thread below.
                final InputStream fromClient = client.getInputStream();
                final OutputStream toClient = client.getOutputStream();

                // Make a connection to the real server
                // If we cannot connect to the server, send an error to the
                // client, disconnect, then continue waiting for another connection.
                try {
                    server = new Socket(host, remoteport);
                } catch (IOException e) {
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(toClient));
                    out.println("Proxy server cannot connect to " + host + ":" +
                            remoteport + ":\n" + e);
                    out.flush();
                    client.close();
                    continue;
                }

                // Get server streams.
                final InputStream from_server = server.getInputStream();
                final OutputStream to_server = server.getOutputStream();

                // Make a thread to read the client's requests and pass them to the
                // server.  We have to use a separate thread because requests and
                // responses may be asynchronous.
                Thread t = new Thread() {
                    public void run() {
                        int bytes_read;
                        try {
                            while ((bytes_read = fromClient.read(requestBytesBuffer)) != -1) {
                                Path filePath = FileSystems.getDefault().getPath("D:\\Users\\User.txt");
                                String subjectId = new String(java.nio.file.Files.readAllBytes(filePath));

                                RequestMessage requestMessage = new RequestMessage(requestBytesBuffer);

                                BsonToXacmlRequestHandler requestHandler = new BsonToXacmlRequestHandler(requestMessage, subjectId);
                                String action = requestHandler.getAction();

                                if (action == null) {
                                    byte[] newRequestMessage = requestMessage.toByteArray();
                                    int newRequestMessageLength = newRequestMessage.length;

                                    to_server.write(newRequestMessage, 0, newRequestMessageLength);
                                    to_server.flush();
                                } else {

                                    ResponseType response = requestHandler.GetAccessDecisionResponse();
                                    ResultType result = response.getResult().get(0);
                                    DecisionType decision = result.getDecision();

                                    if (decision == DecisionType.DENY) {
                                        String errorMessage = "Access denied for the user " + subjectId;

                                        ReplyErrorMessage errorReply = new ReplyErrorMessage(errorMessage);
                                        int originalRequestId = requestMessage.getRequestId();
                                        errorReply.setOriginalRequestId(originalRequestId);
                                        toClient.write(errorReply.toByteArray());
                                        toClient.flush();

                                        continue;
                                    } else if (decision == DecisionType.PERMIT) {
                                        requestHandler.ProcessAdvices();
                                    }

                                    byte[] newRequestMessage = requestMessage.toByteArray();
                                    int newRequestMessageLength = newRequestMessage.length;

                                    to_server.write(newRequestMessage, 0, newRequestMessageLength);
                                    to_server.flush();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // the client closed the connection to us, so  close our
                        // connection to the server.  This will also cause the
                        // server-to-client loop in the main thread exit.
                        try {
                            to_server.close();
                        } catch (IOException e) {
                        }
                    }
                };

                // Start the client-to-server request thread running
                t.start();

                // Meanwhile, in the main thread, read the server's responses
                // and pass them back to the client.  This will be done in
                // parallel with the client-to-server request thread above.
                int bytes_read;
                try {
                    while ((bytes_read = from_server.read(reply)) != -1) {
                        toClient.write(reply, 0, bytes_read);
                        toClient.flush();
                    }
                } catch (IOException e) {
                }

                // The server closed its connection to us, so close our
                // connection to our client.  This will make the other thread exit.
                toClient.close();
                System.out.println("toClient is closed");
            } catch (IOException e) {
                System.err.println(e);
            }
            // Close the sockets no matter what happens each time through the loop.
            finally {
                try {
                    if (server != null) server.close();
                    if (client != null) client.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
