
package tr.edu.metu.ii.sm.xacml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FunctionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FunctionType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}ExpressionType">
 *       &lt;attribute name="FunctionId" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FunctionType", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17")
public class FunctionType
    extends ExpressionType
{

    @XmlAttribute(name = "FunctionId", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String functionId;

    /**
     * Gets the value of the functionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunctionId() {
        return functionId;
    }

    /**
     * Sets the value of the functionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunctionId(String value) {
        this.functionId = value;
    }

}
