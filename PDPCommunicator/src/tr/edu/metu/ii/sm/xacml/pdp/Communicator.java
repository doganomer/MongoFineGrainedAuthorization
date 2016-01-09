package tr.edu.metu.ii.sm.xacml.pdp;

import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.wso2.carbon.identity.entitlement.stub.EntitlementServiceException;
import org.wso2.carbon.identity.entitlement.stub.EntitlementServiceStub;
import tr.edu.metu.ii.sm.xacml.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.rmi.RemoteException;

/**
 * Created by omer.dogan on 17/05/2015.
 */
public class Communicator {

    EntitlementServiceStub entitlementServiceStub;
    String authCookie;

    public Communicator() throws Exception {
        // Loads setup configurations
        ClientUtils.loadConfigProperties();

        // Trust store path.  this must contain server's certificate.
        String trustStore = ClientUtils.getTrustStore();

        /**
         * Call to https://localhost:9443/services/   uses HTTPS protocol.
         * Therefore we to validate the server certificate. The server certificate is looked up in the
         * trust store. Following code sets what trust-store to look for and its JKs password.
         */
        System.setProperty("javax.net.ssl.trustStore", trustStore);
        System.setProperty("javax.net.ssl.trustStorePassword", ClientUtils.getTrustStorePassword());

        /**
         * Axis2 configuration context
         */
        ConfigurationContext configContext;

        try {
            /**
             * Create a configuration context. A configuration context contains information for
             * axis2 environment. This is needed to create an axis2 client
             */
            configContext = ConfigurationContextFactory.createConfigurationContextFromFileSystem(null, null);

            String serviceEndPoint = ClientUtils.getServerUrl() + "EntitlementService";

            entitlementServiceStub = new EntitlementServiceStub(configContext, serviceEndPoint);
            ServiceClient client = entitlementServiceStub._getServiceClient();
            Options option = client.getOptions();

            /**
             * Setting a authenticated cookie that is received from Carbon server.
             * If you have authenticated with Carbon server earlier, you can use that cookie, if
             * it has not been expired
             *
             * Here i am not using the cookie. If cookie verification fails, it would be looked for basic auth headers
             */
            option.setProperty(HTTPConstants.COOKIE_STRING, null);

            // Setting basic auth headers for authentication for user admin
            HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
            auth.setUsername(ClientUtils.getServerUsername());
            auth.setPassword(ClientUtils.getServerPassword());
            auth.setPreemptiveAuthentication(true);
            option.setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE, auth);
            option.setManageSession(true);

            // you can retrieve the cookie to use for sub sequent communications
            authCookie = (String) entitlementServiceStub._getServiceClient().getServiceContext().getProperty(HTTPConstants.COOKIE_STRING);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public ResponseType getDecisionByAttributes(String subjectId, String resourceId, String action, String[] environment) throws EntitlementServiceException, RemoteException, JAXBException {
        String decision = entitlementServiceStub.getDecisionByAttributes(subjectId, resourceId, action, null);
        return convertServiceResponseToResponseType(decision);
    }

    public ResponseType getDecisionByRequest(String requestXml) throws EntitlementServiceException, RemoteException, JAXBException {
        String decision = entitlementServiceStub.getDecision(requestXml);
        return convertServiceResponseToResponseType(decision);
    }

    private ResponseType convertServiceResponseToResponseType(String serviceResponse) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ResponseType.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        StreamSource streamSource = new StreamSource(new StringReader(serviceResponse));

        JAXBElement<ResponseType> responseObject = jaxbUnmarshaller.unmarshal(streamSource, ResponseType.class);
        ResponseType response = responseObject.getValue();

        return response;
    }
}
