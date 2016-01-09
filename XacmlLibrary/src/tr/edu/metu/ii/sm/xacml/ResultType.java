
package tr.edu.metu.ii.sm.xacml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}Decision"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}Status" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}Obligations" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}AssociatedAdvice" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}Attributes" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}PolicyIdentifierList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultType", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17", propOrder = {
    "decision",
    "status",
    "obligations",
    "associatedAdvice",
    "attributes",
    "policyIdentifierList"
})
public class ResultType {

    @XmlElement(name = "Decision", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17", required = true)
    @XmlSchemaType(name = "string")
    protected DecisionType decision;
    @XmlElement(name = "Status", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17")
    protected StatusType status;
    @XmlElement(name = "Obligations", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17")
    protected ObligationsType obligations;
    @XmlElement(name = "AssociatedAdvice", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17")
    protected AssociatedAdviceType associatedAdvice;
    @XmlElement(name = "Attributes", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17")
    protected List<AttributesType> attributes;
    @XmlElement(name = "PolicyIdentifierList", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17")
    protected PolicyIdentifierListType policyIdentifierList;

    /**
     * Gets the value of the decision property.
     * 
     * @return
     *     possible object is
     *     {@link DecisionType }
     *     
     */
    public DecisionType getDecision() {
        return decision;
    }

    /**
     * Sets the value of the decision property.
     * 
     * @param value
     *     allowed object is
     *     {@link DecisionType }
     *     
     */
    public void setDecision(DecisionType value) {
        this.decision = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the obligations property.
     * 
     * @return
     *     possible object is
     *     {@link ObligationsType }
     *     
     */
    public ObligationsType getObligations() {
        return obligations;
    }

    /**
     * Sets the value of the obligations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObligationsType }
     *     
     */
    public void setObligations(ObligationsType value) {
        this.obligations = value;
    }

    /**
     * Gets the value of the associatedAdvice property.
     * 
     * @return
     *     possible object is
     *     {@link AssociatedAdviceType }
     *     
     */
    public AssociatedAdviceType getAssociatedAdvice() {
        return associatedAdvice;
    }

    /**
     * Sets the value of the associatedAdvice property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssociatedAdviceType }
     *     
     */
    public void setAssociatedAdvice(AssociatedAdviceType value) {
        this.associatedAdvice = value;
    }

    /**
     * Gets the value of the attributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributesType }
     * 
     * 
     */
    public List<AttributesType> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<AttributesType>();
        }
        return this.attributes;
    }

    /**
     * Gets the value of the policyIdentifierList property.
     * 
     * @return
     *     possible object is
     *     {@link PolicyIdentifierListType }
     *     
     */
    public PolicyIdentifierListType getPolicyIdentifierList() {
        return policyIdentifierList;
    }

    /**
     * Sets the value of the policyIdentifierList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolicyIdentifierListType }
     *     
     */
    public void setPolicyIdentifierList(PolicyIdentifierListType value) {
        this.policyIdentifierList = value;
    }

}
