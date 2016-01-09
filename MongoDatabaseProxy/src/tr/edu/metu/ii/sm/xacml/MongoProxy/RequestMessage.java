package tr.edu.metu.ii.sm.xacml.MongoProxy;

import com.google.common.primitives.Bytes;
import org.bson.BSONObject;
import org.bson.BasicBSONDecoder;
import org.bson.BasicBSONEncoder;

import java.util.Arrays;


public class RequestMessage {

    private MessageHeader header;
    private int flag;

    private String dbName;
    private String commandName;
    private String collectionName;
    private boolean isCollection;
    byte[] commandOrQueryBytes;

    private int numberToSkip;
    private int numberToReturn;

    private BSONObject document;
    private BSONObject fieldsSelectorDocument;

    public RequestMessage(byte[] allMessageBytes) throws Exception {
        System.out.println("Request message bytes length:" + allMessageBytes.length);

        this.header = new MessageHeader(allMessageBytes);

        int messageLength = this.header.getMessageLength();

        byte[] requestIdAsBytes = new byte[4];
        System.arraycopy(allMessageBytes, 4, requestIdAsBytes, 0, 4);

        byte[] afterHeaderBytes = new byte[messageLength - 16];
        System.arraycopy(allMessageBytes, 16, afterHeaderBytes, 0, messageLength - 16);
        //from_client.read(afterHeaderBytes, 0, messageLength - 16);

        this.flag = Utility.bytesToInt(afterHeaderBytes, 0);

        byte[] afterFlagBytes = new byte[afterHeaderBytes.length - 4];

        System.arraycopy(afterHeaderBytes, 4, afterFlagBytes, 0, afterHeaderBytes.length - 4);

        int index = Bytes.indexOf(afterFlagBytes, (byte) 0);

        commandOrQueryBytes = Arrays.copyOfRange(afterFlagBytes, 0, index + 1);
        String commandOrQuery = new String(commandOrQueryBytes, "UTF-8");
        this.setCommandOrCollectionText(commandOrQuery);

        byte[] afterFullCollNameBytes = new byte[afterFlagBytes.length - commandOrQueryBytes.length];
        System.arraycopy(afterFlagBytes, index + 1, afterFullCollNameBytes, 0, afterFlagBytes.length - commandOrQueryBytes.length - 1);

        this.numberToSkip = Utility.bytesToInt(afterFullCollNameBytes, 0);
        this.numberToReturn = Utility.bytesToInt(afterFullCollNameBytes, 4);

        int documentBytesLength = afterFullCollNameBytes.length - 8;
        byte[] documentBytes = new byte[documentBytesLength];
        System.arraycopy(afterFullCollNameBytes, 8, documentBytes, 0, documentBytesLength);

        BasicBSONDecoder decoder = new BasicBSONDecoder();

        //documentBytes may contain query document and returnFieldsSelector document. Documentation of Wire Protocol is not sufficient.
        int queryDocumentLength = Utility.bytesToInt(documentBytes, 0);
        byte[] queryDocumentBytes = new byte[queryDocumentLength];
        System.arraycopy(documentBytes, 0, queryDocumentBytes, 0, queryDocumentLength);
        this.document = decoder.readObject(queryDocumentBytes);

        if (queryDocumentLength < documentBytesLength) {
            int fieldSelectorDocumentLength = Utility.bytesToInt(documentBytes, queryDocumentLength);

            byte[] fieldSelectorDocumentBytes = new byte[fieldSelectorDocumentLength];
            System.arraycopy(documentBytes, queryDocumentLength, fieldSelectorDocumentBytes, 0, fieldSelectorDocumentLength);
            this.fieldsSelectorDocument = decoder.readObject(fieldSelectorDocumentBytes);
        } else {
            byte[] emptyFieldsSelectorDocumentBytes = new byte[5];
            emptyFieldsSelectorDocumentBytes[0] = 5;
            emptyFieldsSelectorDocumentBytes[1] = 0;
            emptyFieldsSelectorDocumentBytes[2] = 0;
            emptyFieldsSelectorDocumentBytes[3] = 0;
            emptyFieldsSelectorDocumentBytes[4] = 0;

            this.fieldsSelectorDocument = decoder.readObject(emptyFieldsSelectorDocumentBytes);
        }
    }

    public byte[] toByteArray() {
        BasicBSONEncoder encoder = new BasicBSONEncoder();
        byte[] documentBytes = encoder.encode(this.document);
        byte[] fieldSelectorDocumentBytes = encoder.encode(this.fieldsSelectorDocument);

        int headerLength = 16;
        int flagsLength = 4;
        int fullCollectionNameLenght = commandOrQueryBytes.length;
        int numberToSkipLength = 4;
        int numberToReturnLength = 4;
        int documentLength = documentBytes.length;
        int fieldSelectorDocumentLength = fieldSelectorDocumentBytes.length;

        int totalLength = headerLength + flagsLength + fullCollectionNameLenght + numberToSkipLength + numberToReturnLength + documentLength + fieldSelectorDocumentLength;

        byte[] requestMessageBytes = new byte[totalLength];

        this.header.setMessageLength(totalLength);

        byte[] headerBytes = this.header.toByteArray();

        int runningPosition = 0;
        System.arraycopy(headerBytes, 0, requestMessageBytes, runningPosition, headerLength);
        runningPosition += headerLength;
        System.arraycopy(Utility.intToBytes(this.flag), 0, requestMessageBytes, runningPosition, flagsLength);
        runningPosition += flagsLength;
        System.arraycopy(commandOrQueryBytes, 0, requestMessageBytes, runningPosition, fullCollectionNameLenght);
        runningPosition += fullCollectionNameLenght;
        System.arraycopy(Utility.intToBytes(this.numberToSkip), 0, requestMessageBytes, runningPosition, numberToSkipLength);
        runningPosition += numberToSkipLength;
        System.arraycopy(Utility.intToBytes(this.numberToReturn), 0, requestMessageBytes, runningPosition, numberToReturnLength);
        runningPosition += numberToReturnLength;
        System.arraycopy(documentBytes, 0, requestMessageBytes, runningPosition, documentLength);
        runningPosition += documentLength;
        System.arraycopy(fieldSelectorDocumentBytes, 0, requestMessageBytes, runningPosition, fieldSelectorDocumentLength);
        runningPosition += fieldSelectorDocumentLength;

        return requestMessageBytes;
    }

    public String getDbName() {
        return dbName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getCommandName() {
        return commandName;
    }

    public boolean isCommand() {
        return !isCollection;
    }

    public MessageHeader.MessageType getMessageType() {
        return this.header.getMessageType();
    }

    public int getRequestId() {
        return this.header.getRequestId();
    }

    public byte[] getRequestIdAsBytes() {
        return this.header.getRequestIdAsBytes();
    }

    public BSONObject getDocument() {
        return document;
    }

    public BSONObject getFieldsSelectorDocument() {
        return fieldsSelectorDocument;
    }

    private void setCommandOrCollectionText(String commandOrCollection) throws Exception {
        if (!commandOrCollection.contains(".")) {
            throw new Exception("Full collection name should contain a period.");
        }

        String[] parts = commandOrCollection.split("\\.");

        dbName = parts[0];
        if (parts[1].contains("$")) {
            commandName = parts[1].substring(1);
            collectionName = "";
            isCollection = false;
        } else {
            commandName = "";
            collectionName = parts[1];
            isCollection = true;
        }
    }

}
