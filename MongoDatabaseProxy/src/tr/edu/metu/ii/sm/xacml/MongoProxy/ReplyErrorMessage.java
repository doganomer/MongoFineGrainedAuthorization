package tr.edu.metu.ii.sm.xacml.MongoProxy;

import org.bson.BasicBSONEncoder;
import org.bson.BasicBSONObject;
import com.google.common.primitives.Longs;

import java.util.Random;

/**
 * Created by omer.dogan on 16/05/2015.
 */
public class ReplyErrorMessage {

    private String errorMessage;

    public ReplyErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /*private MessageHeader header;
    private int responseFlags;

    private BSONObject document;*/

    /*public ReplyErrorMessage(byte[] allMessageBytes, byte[] originalrequestId) throws Exception
    {
        byte[] headerBytes = new byte[16];
        System.arraycopy(allMessageBytes, 0, headerBytes, 0, 16);


        int messageBytesLength = allMessageBytes.length;

        this.header = new MessageHeader(headerBytes);

        if (messageBytesLength>36)
        {
            if (originalrequestId != null)
            {
                //Set responseTo to the request ID
                allMessageBytes[8]=originalrequestId[0];
                allMessageBytes[9]=originalrequestId[1];
                allMessageBytes[10]=originalrequestId[2];
                allMessageBytes[11]=originalrequestId[3];
            }

            responseFlags = Utility.bytesToInt(allMessageBytes, 16);

            byte[] documentsBytesFromServer = new byte[allMessageBytes.length - 36];
            System.arraycopy(allMessageBytes, 36, documentsBytesFromServer, 0, allMessageBytes.length - 36);

            BasicBSONDecoder replyDecoder = new BasicBSONDecoder();
            document = replyDecoder.readObject(documentsBytesFromServer);
        }
    }*/

    /*public BSONObject getDocument()
    {
        return document;
    }*/

    private int originalRequestId;

    public void setOriginalRequestId(int requestId)
    {
        this.originalRequestId = requestId;
    }

    public byte[] toByteArray() {
        Random randomGenerator = new Random();
        int myRequestId = randomGenerator.nextInt(10000);

        System.out.println("My Request Id:" + myRequestId);

        MessageHeader errorHeader = new MessageHeader(0, myRequestId, this.originalRequestId, MessageHeader.MessageType.OP_REPLY);

        BasicBSONEncoder encoder = new BasicBSONEncoder();

        BasicBSONObject replyDocument = new BasicBSONObject();
        replyDocument.put("$err", this.errorMessage);

        byte[] replyDocumentBytes = encoder.encode(replyDocument);

        int flag = 2;
        long cursorId = 0;
        int startingFrom = 0;
        int numberReturned = 1;

        int headerLength = 16;
        int flagsLength = 4;
        int cursorIdLength = 8;
        int startingFromLength = 4;
        int numberReturnedLength = 4;
        int replyDocumentLength = replyDocumentBytes.length;

        int totalLength = headerLength + flagsLength + cursorIdLength + startingFromLength + numberReturnedLength + replyDocumentLength;

        byte[] replyMessageBytes = new byte[totalLength];

        errorHeader.setMessageLength(totalLength);

        byte[] headerBytes = errorHeader.toByteArray();



        int runningPosition = 0;
        System.arraycopy(headerBytes, 0, replyMessageBytes, runningPosition, headerLength);
        runningPosition += headerLength;
        System.arraycopy(Utility.intToBytes(flag), 0, replyMessageBytes, runningPosition, flagsLength);
        runningPosition += flagsLength;
        System.arraycopy(Longs.toByteArray(cursorId), 0, replyMessageBytes, runningPosition, cursorIdLength);
        runningPosition += cursorIdLength;
        System.arraycopy(Utility.intToBytes(startingFrom), 0, replyMessageBytes, runningPosition, startingFromLength);
        runningPosition += startingFromLength;
        System.arraycopy(Utility.intToBytes(numberReturned), 0, replyMessageBytes, runningPosition, numberReturnedLength);
        runningPosition += numberReturnedLength;
        System.arraycopy(replyDocumentBytes, 0, replyMessageBytes, runningPosition, replyDocumentLength);
        runningPosition += replyDocumentLength;

        return replyMessageBytes;
    }

}
