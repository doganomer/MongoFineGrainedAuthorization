package tr.edu.metu.ii.sm.xacml.MongoProxy;

/**
 * Created by omer.dogan on 16/05/2015.
 */
public class Utility {

    public static int bytesToInt(byte[] bytes, int offset) {
        int result = (int)bytes[offset]&0xff;
        result |= ((int)bytes[offset+1]&0xff) << 8;
        result |= ((int)bytes[offset+2]&0xff) << 16;
        result |= ((int)bytes[offset+3]&0xff) << 24;
        return result;
    }

    public static byte[] intToBytes(int i)
    {
        //return Ints.toByteArray(i);

        byte[] result = new byte[4];

		/*result[0] = (byte) (i >> 24);
		result[1] = (byte) (i >> 16);
		result[2] = (byte) (i >> 8);
		result[3] = (byte) (i >> 0);*/

        result[3] = (byte) (i >> 24);
        result[2] = (byte) (i >> 16);
        result[1] = (byte) (i >> 8);
        result[0] = (byte) (i >> 0);

        return result;
    }

    public static int AppendByteArray(byte[] source, byte[] destination, int destinationStart)
    {
        System.arraycopy(source, 0, destination, destinationStart, source.length);
        return destinationStart + source.length;
    }

}
