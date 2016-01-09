
package tr.edu.metu.ii.sm.xacml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExpressionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExpressionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExpressionType", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17")
@XmlSeeAlso({
    ApplyType.class,
    AttributeSelectorType.class,
    FunctionType.class,
    AttributeDesignatorType.class,
    VariableReferenceType.class
})
public abstract class ExpressionType {


}
