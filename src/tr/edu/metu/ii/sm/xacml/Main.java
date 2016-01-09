package tr.edu.metu.ii.sm.xacml;

//import com.mongodb.*;

import com.mongodb.*;
import org.wso2.carbon.identity.entitlement.stub.EntitlementServiceException;
import tr.edu.metu.ii.sm.xacml.pdp.Communicator;

import java.io.StringReader;
import java.net.UnknownHostException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.*;

public class Main {

    public static void main(String[] args) throws Exception {

        //SERIALIZATION
       /* RequestType request = new RequestType();
        request.setCombinedDecision(false);
        request.setReturnPolicyIdList(false);

        List<Object> readActionAttributeContent = new ArrayList<Object>();
        readActionAttributeContent.add("create");

        List<AttributeValueType> readActionAttributeValues = new ArrayList<AttributeValueType>();
        AttributeValueType readActionAttributeValue = new AttributeValueType();
        readActionAttributeValue.setDataType("http://www.w3.org/2001/XMLSchema#string");
        readActionAttributeValue.content = readActionAttributeContent;
        readActionAttributeValues.add(readActionAttributeValue);

        List<AttributeType> readActionAttributes = new ArrayList<AttributeType>();
        AttributeType readActionAttribute = new AttributeType();
        readActionAttribute.setAttributeId("urn:oasis:names:tc:xacml:1.0:action:action-id");
        readActionAttribute.setIncludeInResult(false);
        readActionAttribute.attributeValue = readActionAttributeValues;
        readActionAttributes.add(readActionAttribute);

        AttributesType actionAttributes = new AttributesType();
        actionAttributes.setCategory("urn:oasis:names:tc:xacml:3.0:attribute-category:action");
        actionAttributes.attribute = readActionAttributes;

        request.attributes = new ArrayList<AttributesType>();
        request.attributes.add(actionAttributes);

        JAXBContext jaxbContext = JAXBContext.newInstance(RequestType.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(request, System.out);*/

        //MONGO
       /*Mongo mongo = new Mongo("127.0.0.1", 27888);

        DB database = mongo.getDB("MilitaryForces");

        DBCollection forcesCollection = database.getCollection("MilitaryForce");

        BasicDBObject query = new BasicDBObject();

        BasicDBObject notEqual = new BasicDBObject();
        notEqual.put("$ne","EFSFAF");
        //query.put("Code",notEqual);

        BasicDBObject projection = new BasicDBObject();
        //projection.put("Capability",0);

        DBCursor cursor = forcesCollection.find(query, projection);

        while (cursor.hasNext())
        {
            DBObject forceObject = cursor.next();
            System.out.println(forceObject);
        }*/

        //PDP Communication
        /*Communicator pdpCommunicator = new Communicator();

        ArrayList<String> subjects = new ArrayList<String>();
        subjects.add("alice");
        subjects.add("bob");
        subjects.add("chuck");
        subjects.add("charlie");
        subjects.add("dave");
        subjects.add("eve");

        ArrayList<String> resources = new ArrayList<String>();
        resources.add("FFUMDB");
        resources.add("EFUMOC");
        resources.add("FFSBMD");
        resources.add("EFSFAF");

        ArrayList<String> actions = new ArrayList<String>();
        actions.add("create");
        actions.add("read");
        actions.add("update");
        actions.add("delete");

        for(String subject:subjects)
        {
            for(String resource:resources)
            {
                for(String action:actions)
                {
                    PrintResults(pdpCommunicator, subject, resource, action);
                }
            }
        }*/

        //XACML Request
        /*String subject="charlie";
        String classification = "SECRET";
        String affiliation = "Enemy";

        String requestRootBegin = "<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n";
        String actionPart = "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\">\n" +
                "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" IncludeInResult=\"false\">\n" +
                "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">create</AttributeValue>\n" +
                "</Attribute>\n" +
                "</Attributes>\n";

String request =
        "<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n" +
                "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\">\n" +
                "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" IncludeInResult=\"false\">\n" +
                "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">create</AttributeValue>\n" +
                "</Attribute>\n" +
                "</Attributes>\n" +
                "<Attributes Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\">\n" +
                "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject:subject-id\" IncludeInResult=\"false\">\n" +
                "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + subject +"</AttributeValue>\n" +
                "</Attribute>\n" +
                "</Attributes>\n" +
                "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:resource\">\n" +
                "<Attribute AttributeId=\"http://sm.ii.metu.edu.tr/id/classification\" IncludeInResult=\"false\">\n" +
                "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + classification + "</AttributeValue>\n" +
                "</Attribute>\n" +
                "<Attribute AttributeId=\"http://sm.ii.metu.edu.tr/id/affiliation\" IncludeInResult=\"false\">\n" +
                "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + affiliation + "</AttributeValue>\n" +
                "</Attribute>\n" +
                "</Attributes>\n" +
                "</Request>";

        System.out.println(request);*/

        //Getting subjectId from a File

        Path filePath = FileSystems.getDefault().getPath("D:\\Users\\User.txt");
        String username = new String(java.nio.file.Files.readAllBytes(filePath));

        System.out.println(username);
    }

    private static void PrintResults(Communicator pdpCommunicator, String subjectId, String resourceId, String action) throws JAXBException, RemoteException, EntitlementServiceException {
        ResponseType response = pdpCommunicator.getDecisionByAttributes(subjectId, resourceId, action, null);
        ResultType result = response.getResult().get(0);
        String actualDecision = result.getDecision().value();

        StringBuilder sb = new StringBuilder();
        AssociatedAdviceType associatedAdvice = result.getAssociatedAdvice();
        if (associatedAdvice != null) {
            List<AdviceType> advices = associatedAdvice.getAdvice();
            for (AdviceType advice : advices) {
                List<AttributeAssignmentType> attributeAssignments = advice.getAttributeAssignment();

                for (AttributeAssignmentType attributeAssignment : attributeAssignments) {
                    String attributeId = attributeAssignment.getAttributeId();
                    //sb.append(attributeId);

                    List<Object> contents = attributeAssignment.getContent();

                    contents.forEach(sb::append);
                }
            }
        }

        String actualAdvice = sb.toString();

        String separator = ",";
        System.out.println(subjectId + separator + resourceId + separator + action + separator + actualDecision + separator + actualAdvice);

        StatusType status = result.getStatus();
        String statusMessage = status.getStatusMessage();
        System.out.println(statusMessage);
    }

    private static void printResponse(ResponseType response) {
        System.out.println("Decision: ");
        System.out.println(response.getResult().get(0).getDecision().value());
        System.out.println("Advice: ");

        StringBuilder sb = new StringBuilder();
        AssociatedAdviceType associatedAdvice = response.getResult().get(0).getAssociatedAdvice();
        if (associatedAdvice != null) {
            List<AdviceType> advices = associatedAdvice.getAdvice();
            for (AdviceType advice : advices) {
                List<AttributeAssignmentType> attributeAssignments = advice.getAttributeAssignment();

                for (AttributeAssignmentType attributeAssignment : attributeAssignments) {
                    String attributeId = attributeAssignment.getAttributeId();
                    sb.append(attributeId);

                    List<Object> contents = attributeAssignment.getContent();

                    contents.forEach(sb::append);
                }
            }
        }

        System.out.println(sb.toString());
    }
}
