package tr.edu.metu.ii.sm.xacml.pip;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created by omer.dogan on 18/05/2015.
 */
public class DatabaseManager {

    DBCollection dbCollection;

    public DatabaseManager(int mongoDbPort, String host) throws UnknownHostException {
        Mongo mongo = new Mongo(host, mongoDbPort);
        DB database = mongo.getDB("MilitaryForces");

        dbCollection = database.getCollection("MilitaryForce");
    }

    public String getForceAttributeValue(String attributeName, String resourceID) {
        String attributeValue = null;

        if (dbCollection == null) {
            return attributeValue;
        }
        if (resourceID == null || resourceID.equals("")) {
            return attributeValue;
        }

        BasicDBObject query = new BasicDBObject();
        query.put("Code", resourceID);

        DBObject forceObject = dbCollection.findOne(query);

        if (forceObject != null)
        {
            attributeValue = forceObject.get(attributeName).toString();
        }

        return attributeValue;
    }

}
