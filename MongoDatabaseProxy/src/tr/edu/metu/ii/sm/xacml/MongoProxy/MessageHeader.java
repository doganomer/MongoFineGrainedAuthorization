package tr.edu.metu.ii.sm.xacml.MongoProxy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by omer.dogan on 16/05/2015.
 */
public class MessageHeader {

    private int messageLength;
    private int requestId;
    private int responseTo;
    private int opCode;
    private byte[] requestIdAsBytes;

    public MessageHeader(byte[] headerBytes)
    {
        messageLength = Utility.bytesToInt(headerBytes, 0);
        requestId = Utility.bytesToInt(headerBytes, 4);
        responseTo = Utility.bytesToInt(headerBytes, 8);
        opCode = Utility.bytesToInt(headerBytes, 12);

        requestIdAsBytes = new byte[4];
        System.arraycopy(headerBytes, 4, requestIdAsBytes, 0, 4);
    }

    public MessageHeader(int messageLen, int requestIdentifier, int responseToIdentifier, MessageType messageType)
    {
        this.messageLength = messageLen;
        this.requestId = requestIdentifier;
        this.responseTo = responseToIdentifier;
        this.opCode = messageType.value;
    }

    public MessageType getMessageType()
    {
        MessageType type = intToTypeMap.get(Integer.valueOf(opCode));
        return type;
    }

    private static final Map<Integer, MessageType> intToTypeMap = new HashMap<Integer, MessageType>();
    static {
        for (MessageType type : MessageType.values()) {
            intToTypeMap.put(type.value, type);
        }
    }

    public int getRequestId()
    {
        return requestId;
    }

    public byte[] getRequestIdAsBytes()
    {
        return requestIdAsBytes;
    }

    public int getResponseTo()
    {
        return responseTo;
    }

    public int getMessageLength()
    {
        return messageLength;
    }

    public void setMessageLength(int newLenght)
    {
        this.messageLength = newLenght;
    }
    public void setRequestId(int requestId)
    {
        this.requestId = requestId;
    }
    public void setResponseTo(int responseTo)
    {
        this.responseTo = responseTo;
    }
    public void setOpCode(int opCode)
    {
        this.opCode = opCode;
    }

    public byte[] toByteArray()
    {
        byte[] headerBytes = new byte[16];
        System.arraycopy(Utility.intToBytes(this.messageLength), 0, headerBytes, 0, 4);
        System.arraycopy(Utility.intToBytes(this.requestId), 0, headerBytes, 4, 4);
        System.arraycopy(Utility.intToBytes(this.responseTo), 0, headerBytes, 8, 4);
        System.arraycopy(Utility.intToBytes(this.opCode), 0, headerBytes, 12, 4);

        return headerBytes;
    }

    public enum MessageType
    {
        OP_REPLY(1), //Database reply
        OP_UPDATE(2001), // Update document
        OP_INSERT(2002), // Insert new document
        OP_QUERY(2004), // Query a collection
        OP_GET_MORE(2005), // Get more data from a cursor
        OP_DELETE(2006), // Delete documents
        OP_KILL_CURSORS(2007); // Kill the cursor since client is done with it

        private int value;

        private MessageType(int value) {
            this.value = value;
        }
    }
}
