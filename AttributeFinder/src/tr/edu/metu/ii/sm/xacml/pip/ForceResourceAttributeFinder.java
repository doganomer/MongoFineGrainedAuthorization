package tr.edu.metu.ii.sm.xacml.pip;

import org.wso2.carbon.identity.entitlement.pip.AbstractPIPAttributeFinder;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Created by omer.dogan on 14/05/2015.
 */
public class ForceResourceAttributeFinder extends AbstractPIPAttributeFinder {

    private static final String AFFILIATION_ID = "http://sm.ii.metu.edu.tr/id/affiliation";
    private static final String CLASSIFICATION_ID = "http://sm.ii.metu.edu.tr/id/classification";

    private Set<String> supportedAttributes = new HashSet<String>();
    private int port = 27777;
    private String host = "127.0.0.1";

    @Override
    public Set<String> getAttributeValues(String subjectId, String resourceId, String actionId, String environmentId, String attributeId, String issuer) throws Exception {
        Set<String> values = new HashSet<String>();

        String attributeName = null;

        if (AFFILIATION_ID.equals(attributeId)) {
            attributeName = "Affiliation";
        } else if (CLASSIFICATION_ID.equals(attributeId)) {
            attributeName = "Classification";
        } else {
            throw new Exception("Attribute ID is not valid: " + attributeId);
        }

        DatabaseManager dbManager = new DatabaseManager(port, host);
        String attributeValue = dbManager.getForceAttributeValue(attributeName, resourceId);

        if (attributeValue == null)
        {
            if (AFFILIATION_ID.equals(attributeId)) {
                attributeValue = "Friend";
            } else if (CLASSIFICATION_ID.equals(attributeId)) {
                attributeValue = "UNCLASSIFIED";
            }
        }
        values.add(attributeValue);

        return values;
    }

    @Override
    public void init(Properties properties) throws Exception {
        String portProperty = properties.getProperty("DatabasePort");
        String hostProperty = properties.getProperty("DatabaseHost");
        if (portProperty != null && portProperty != "") {
            port = Integer.parseInt(portProperty);
        }
        if (hostProperty != null && hostProperty != "") {
            host = hostProperty;
        }

        supportedAttributes.add(AFFILIATION_ID);
        supportedAttributes.add(CLASSIFICATION_ID);
    }

    @Override
    public String getModuleName() {
        return "ForceResourceAttributeFinder";
    }

    @Override
    public Set<String> getSupportedAttributes() {
        return supportedAttributes;
    }
}
