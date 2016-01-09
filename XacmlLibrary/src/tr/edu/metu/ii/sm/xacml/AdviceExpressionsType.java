
package tr.edu.metu.ii.sm.xacml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdviceExpressionsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdviceExpressionsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}AdviceExpression" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdviceExpressionsType", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17", propOrder = {
    "adviceExpression"
})
public class AdviceExpressionsType {

    @XmlElement(name = "AdviceExpression", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17", required = true)
    protected List<AdviceExpressionType> adviceExpression;

    /**
     * Gets the value of the adviceExpression property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adviceExpression property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdviceExpression().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdviceExpressionType }
     * 
     * 
     */
    public List<AdviceExpressionType> getAdviceExpression() {
        if (adviceExpression == null) {
            adviceExpression = new ArrayList<AdviceExpressionType>();
        }
        return this.adviceExpression;
    }

}
