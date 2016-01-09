
package tr.edu.metu.ii.sm.xacml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConditionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConditionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}Expression"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConditionType", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17", propOrder = {
    "expression"
})
public class ConditionType {

    @XmlElementRef(name = "Expression", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17", type = JAXBElement.class)
    protected JAXBElement<?> expression;

    /**
     * Gets the value of the expression property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ApplyType }{@code >}
     *     {@link JAXBElement }{@code <}{@link AttributeDesignatorType }{@code >}
     *     {@link JAXBElement }{@code <}{@link AttributeValueType }{@code >}
     *     {@link JAXBElement }{@code <}{@link VariableReferenceType }{@code >}
     *     {@link JAXBElement }{@code <}{@link ExpressionType }{@code >}
     *     {@link JAXBElement }{@code <}{@link FunctionType }{@code >}
     *     {@link JAXBElement }{@code <}{@link AttributeSelectorType }{@code >}
     *     
     */
    public JAXBElement<?> getExpression() {
        return expression;
    }

    /**
     * Sets the value of the expression property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ApplyType }{@code >}
     *     {@link JAXBElement }{@code <}{@link AttributeDesignatorType }{@code >}
     *     {@link JAXBElement }{@code <}{@link AttributeValueType }{@code >}
     *     {@link JAXBElement }{@code <}{@link VariableReferenceType }{@code >}
     *     {@link JAXBElement }{@code <}{@link ExpressionType }{@code >}
     *     {@link JAXBElement }{@code <}{@link FunctionType }{@code >}
     *     {@link JAXBElement }{@code <}{@link AttributeSelectorType }{@code >}
     *     
     */
    public void setExpression(JAXBElement<?> value) {
        this.expression = value;
    }

}
