
package tr.edu.metu.ii.sm.xacml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TargetType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TargetType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}AnyOf"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TargetType", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17", propOrder = {
    "anyOf"
})
public class TargetType {

    @XmlElement(name = "AnyOf", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17")
    protected List<AnyOfType> anyOf;

    /**
     * Gets the value of the anyOf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the anyOf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnyOf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnyOfType }
     * 
     * 
     */
    public List<AnyOfType> getAnyOf() {
        if (anyOf == null) {
            anyOf = new ArrayList<AnyOfType>();
        }
        return this.anyOf;
    }

}
