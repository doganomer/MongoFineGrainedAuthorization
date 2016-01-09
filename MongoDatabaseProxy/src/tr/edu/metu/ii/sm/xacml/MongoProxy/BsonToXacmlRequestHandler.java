package tr.edu.metu.ii.sm.xacml.MongoProxy;

import com.mongodb.BasicDBObject;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;
import tr.edu.metu.ii.sm.xacml.*;
import tr.edu.metu.ii.sm.xacml.pdp.Communicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by omer.dogan on 20/05/2015.
 */
public class BsonToXacmlRequestHandler {
    private RequestMessage bsonRequestMessage;

    private String action;
    private String subjectId;
    private HashMap<String, Object> documentFields;
    private ResponseType response;

    public BsonToXacmlRequestHandler(RequestMessage bsonRequestMessage, String subject) {
        this.bsonRequestMessage = bsonRequestMessage;
        this.subjectId = subject;
        this.documentFields = new HashMap<String, Object>();

        BSONObject requestDocument = bsonRequestMessage.getDocument();
        BSONObject fieldsSelectorDocument = bsonRequestMessage.getFieldsSelectorDocument();

        if (bsonRequestMessage.isCommand()) {
            if (requestDocument.containsField("insert")) {
                action = "create";
                BasicBSONList documents = (BasicBSONList) requestDocument.get("documents");
                BasicBSONObject insertedDocument = (BasicBSONObject) documents.get(0);

                for (String key : insertedDocument.keySet()) {
                    documentFields.put(key, insertedDocument.get(key));
                }
            } else if (requestDocument.containsField("update")) {
                action = "update";
            } else if (requestDocument.containsField("delete")) {
                action = "delete";
            }
        } else {
            action = "read";

            for (String key : requestDocument.keySet()) {
                documentFields.put(key, requestDocument.get(key));
            }
        }
    }

    public String getAction()
    {
        return this.action;
    }

    public ResponseType GetAccessDecisionResponse() throws Exception {
        Communicator pdpCommunicator = new Communicator();
        String request = GenerateRequest();
        this.response = pdpCommunicator.getDecisionByRequest(request);
        return this.response;
    }

    public void ProcessAdvices()
    {
        BSONObject requestDocument = this.bsonRequestMessage.getDocument();
        BSONObject fieldsSelectorDocument = this.bsonRequestMessage.getFieldsSelectorDocument();

        ResultType result = this.response.getResult().get(0);

        List<String> filterExpressions = new ArrayList<String>();

        AssociatedAdviceType associatedAdvice = result.getAssociatedAdvice();
        if (associatedAdvice != null) {
            List<AdviceType> advices = associatedAdvice.getAdvice();
            for (AdviceType advice : advices) {
                List<AttributeAssignmentType> attributeAssignments = advice.getAttributeAssignment();

                for (AttributeAssignmentType attributeAssignment : attributeAssignments) {
                    String attributeId = attributeAssignment.getAttributeId();
                    //sb.append(attributeId);

                    List<Object> contents = attributeAssignment.getContent();

                    for (Object content : contents) {
                        filterExpressions.add(content.toString());
                    }
                }
            }
        }

        for (String filter : filterExpressions) {
            String[] filterParts = filter.split(":");
            String operation = filterParts[0].trim();
            String fieldName = filterParts[1].trim();

            if (operation.equals("Filter")) {

                String value = filterParts[2].trim();
                String operator = filterParts[3].trim();

                BSONObject filterObject = new BasicDBObject();
                String dbOperator = "$eq";
                if (operator.equals("NotEqual")) {
                    dbOperator = "$ne";
                }
                filterObject.put(dbOperator, value);

                requestDocument.put(fieldName, filterObject);
            } else if (operation.equals("Exclude")) {
                if (fieldsSelectorDocument == null) {
                    fieldsSelectorDocument = new BasicBSONObject();
                }
                fieldsSelectorDocument.put(fieldName, 0);
            }
        }
    }

    private String GenerateRequest() {
        String code = "";
        String classification = null;
        String affiliation = null;

        if (this.documentFields.containsKey("Code")) {
            code = this.documentFields.get("Code").toString();
        }
        if (this.documentFields.containsKey("Affiliation")) {
            affiliation = this.documentFields.get("Affiliation").toString();
        }
        if (this.documentFields.containsKey("Classification")) {
            classification = this.documentFields.get("Classification").toString();
        }

        StringBuilder sb = new StringBuilder();

        String requestRootBegin = "<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n";

        sb.append(requestRootBegin);
        if (this.action != null) {
            String actionPart = "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\">\n" +
                    "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" IncludeInResult=\"false\">\n" +
                    "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + this.action + "</AttributeValue>\n" +
                    "</Attribute>\n";

            sb.append(actionPart);
        }

        if (this.subjectId != null) {
            String subjectPart = "</Attributes>\n" +
                    "<Attributes Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\">\n" +
                    "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject:subject-id\" IncludeInResult=\"false\">\n" +
                    "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + this.subjectId + "</AttributeValue>\n" +
                    "</Attribute>\n" +
                    "</Attributes>\n";
            sb.append(subjectPart);
        }
        if (code != null || classification != null || affiliation != null) {
            String resourcesBegin = "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:resource\">\n";
            sb.append(resourcesBegin);

            if (code != null) {
                String resourceIdPart = "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" IncludeInResult=\"false\">\n" +
                        "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + code + "</AttributeValue>\n" +
                        "</Attribute>\n";
                sb.append(resourceIdPart);
            }
            if (classification != null) {
                String classificationIdPart = "<Attribute AttributeId=\"http://sm.ii.metu.edu.tr/id/classification\" IncludeInResult=\"false\">\n" +
                        "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + classification + "</AttributeValue>\n" +
                        "</Attribute>\n";
                sb.append(classificationIdPart);
            }
            if (affiliation != null) {
                String affiliationPart = "<Attribute AttributeId=\"http://sm.ii.metu.edu.tr/id/affiliation\" IncludeInResult=\"false\">\n" +
                        "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + affiliation + "</AttributeValue>\n" +
                        "</Attribute>\n";
                sb.append(affiliationPart);
            }

            String resourcesEnd = "</Attributes>\n";
            sb.append(resourcesEnd);
        }

        String requestRootEnd = "</Request>";
        sb.append(requestRootEnd);

        return sb.toString();
    }
}
