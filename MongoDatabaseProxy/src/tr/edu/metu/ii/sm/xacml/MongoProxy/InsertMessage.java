package tr.edu.metu.ii.sm.xacml.MongoProxy;

import com.google.common.primitives.Bytes;
import org.bson.BSONObject;
import org.bson.BasicBSONDecoder;

import java.util.Arrays;

/**
 * Created by omer.dogan on 20/05/2015.
 */
public class InsertMessage {

    private MessageHeader header;
    private int flag;

    private String dbName;
    private String collectionName;

    private BSONObject document;

    public InsertMessage(byte[] allMessageBytes) throws Exception {
        System.out.println("Insert message bytes length:" + allMessageBytes.length);

        this.header = new MessageHeader(allMessageBytes);

        int messageLength = this.header.getMessageLength();

        byte[] afterHeaderBytes = new byte[messageLength - 16];
        System.arraycopy(allMessageBytes, 16, afterHeaderBytes, 0, messageLength - 16);

        this.flag = Utility.bytesToInt(afterHeaderBytes, 0);

        byte[] afterFlagBytes = new byte[afterHeaderBytes.length - 4];

        System.arraycopy(afterHeaderBytes, 4, afterFlagBytes, 0, afterHeaderBytes.length - 4);

        int index = Bytes.indexOf(afterFlagBytes, (byte) 0);

        byte[] fullCollectionBytes = Arrays.copyOfRange(afterFlagBytes, 0, index + 1);
        String fullCollectionName = new String(fullCollectionBytes, "UTF-8");
        this.setFullCollectionText(fullCollectionName);

        int documentBytesLength =afterFlagBytes.length - fullCollectionBytes.length;
        byte[] documentBytes = new byte[documentBytesLength];
        System.arraycopy(afterFlagBytes, index + 1, documentBytes, 0, documentBytesLength - 1);

        BasicBSONDecoder decoder = new BasicBSONDecoder();

        //documentBytes may contain query document and returnFieldsSelector document. Documentation of Wire Protocol is not sufficient.
        int queryDocumentLength = Utility.bytesToInt(documentBytes, 0);
        byte[] queryDocumentBytes = new byte[queryDocumentLength];
        System.arraycopy(documentBytes, 0, queryDocumentBytes, 0, queryDocumentLength);
        this.document = decoder.readObject(queryDocumentBytes);
    }

    private void setFullCollectionText(String fullCollectionText) throws Exception {
        if (!fullCollectionText.contains(".")) {
            throw new Exception("Full collection name should contain a period.");
        }

        String[] parts = fullCollectionText.split("\\.");

        dbName = parts[0];
        collectionName = parts[1];
    }
}
