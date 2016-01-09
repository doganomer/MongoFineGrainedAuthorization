
package tr.edu.metu.ii.sm.xacml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}RequestDefaults" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}Attributes" maxOccurs="unbounded"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}MultiRequests" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ReturnPolicyIdList" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="CombinedDecision" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestType", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17", propOrder = {
    "requestDefaults",
    "attributes",
    "multiRequests"
})
public class RequestType {

    @XmlElement(name = "RequestDefaults", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17")
    protected RequestDefaultsType requestDefaults;
    @XmlElement(name = "Attributes", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17", required = true)
    protected List<AttributesType> attributes;
    @XmlElement(name = "MultiRequests", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17")
    protected MultiRequestsType multiRequests;
    @XmlAttribute(name = "ReturnPolicyIdList", required = true)
    protected boolean returnPolicyIdList;
    @XmlAttribute(name = "CombinedDecision", required = true)
    protected boolean combinedDecision;

    /**
     * Gets the value of the requestDefaults property.
     * 
     * @return
     *     possible object is
     *     {@link RequestDefaultsType }
     *     
     */
    public RequestDefaultsType getRequestDefaults() {
        return requestDefaults;
    }

    /**
     * Sets the value of the requestDefaults property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestDefaultsType }
     *     
     */
    public void setRequestDefaults(RequestDefaultsType value) {
        this.requestDefaults = value;
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
     * Gets the value of the multiRequests property.
     * 
     * @return
     *     possible object is
     *     {@link MultiRequestsType }
     *     
     */
    public MultiRequestsType getMultiRequests() {
        return multiRequests;
    }

    /**
     * Sets the value of the multiRequests property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiRequestsType }
     *     
     */
    public void setMultiRequests(MultiRequestsType value) {
        this.multiRequests = value;
    }

    /**
     * Gets the value of the returnPolicyIdList property.
     * 
     */
    public boolean isReturnPolicyIdList() {
        return returnPolicyIdList;
    }

    /**
     * Sets the value of the returnPolicyIdList property.
     * 
     */
    public void setReturnPolicyIdList(boolean value) {
        this.returnPolicyIdList = value;
    }

    /**
     * Gets the value of the combinedDecision property.
     * 
     */
    public boolean isCombinedDecision() {
        return combinedDecision;
    }

    /**
     * Sets the value of the combinedDecision property.
     * 
     */
    public void setCombinedDecision(boolean value) {
        this.combinedDecision = value;
    }

}
