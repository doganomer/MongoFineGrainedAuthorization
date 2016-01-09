package tr.edu.metu.ii.sm.xacml.MongoProxy;

import org.bson.BSONObject;
import org.bson.BasicBSONDecoder;

/**
 * Created by omer.dogan on 19/05/2015.
 */
public class ReplyMessage {

    private MessageHeader header;
    private int responseFlags;
    private long cursorId;
    private int startingFrom;
    private int numberReturned;

    private BSONObject document;

    public ReplyMessage(byte[] allMessageBytes, byte[] originalrequestId) throws Exception
    {
        this.header = new MessageHeader(allMessageBytes);
        int messageBytesLength = this.header.getMessageLength();

        /*
        MessageHeader-->16 bytes
        responseFlags-->4 bytes
        cursorId-->8 bytes
        startingFrom-->4 bytes
        numberReturned--> 4 bytes
        documents--> remaining bytes
         */

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

            byte[] cursorIdBytes = new byte[8];
            System.arraycopy(allMessageBytes, 20, cursorIdBytes, 0, 4);
            this.cursorId = com.google.common.primitives.Longs.fromByteArray(cursorIdBytes);

            this.startingFrom = Utility.bytesToInt(allMessageBytes, 28);
            this.numberReturned = Utility.bytesToInt(allMessageBytes, 32);


            int documentBytesLength = allMessageBytes.length - 36;
            byte[] documentsBytesFromServer = new byte[documentBytesLength];
            System.arraycopy(allMessageBytes, 36, documentsBytesFromServer, 0, documentBytesLength);

            BasicBSONDecoder replyDecoder = new BasicBSONDecoder();
            document = replyDecoder.readObject(documentsBytesFromServer);
        }
    }
}
