
package tr.edu.metu.ii.sm.xacml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PolicySetCombinerParametersType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PolicySetCombinerParametersType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}CombinerParametersType">
 *       &lt;attribute name="PolicySetIdRef" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolicySetCombinerParametersType", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17")
public class PolicySetCombinerParametersType
    extends CombinerParametersType
{

    @XmlAttribute(name = "PolicySetIdRef", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String policySetIdRef;

    /**
     * Gets the value of the policySetIdRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicySetIdRef() {
        return policySetIdRef;
    }

    /**
     * Sets the value of the policySetIdRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicySetIdRef(String value) {
        this.policySetIdRef = value;
    }

}
