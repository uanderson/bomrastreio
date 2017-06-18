
package br.com.bomrastreio.api.correios.trace.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sroxml complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="sroxml"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="versao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="qtd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TipoPesquisa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TipoResultado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://resource.webservice.correios.com.br/}objeto" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sroxml", propOrder = {
    "version",
    "count",
    "searchType",
    "searchResult",
    "objects"
})
public class TraceResponse {

    @XmlElement(name = "versao")
    protected String version;
    @XmlElement(name = "qtd")
    protected String count;
    @XmlElement(name = "TipoPesquisa")
    protected String searchType;
    @XmlElement(name = "TipoResultado")
    protected String searchResult;
    @XmlElement(name = "objeto")
    protected List<TraceObject> objects;

    /**
     * Gets the value of the version property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the count property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCount(String value) {
        this.count = value;
    }

    /**
     * Gets the value of the searchType property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSearchType() {
        return searchType;
    }

    /**
     * Sets the value of the searchType property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSearchType(String value) {
        this.searchType = value;
    }

    /**
     * Gets the value of the searchResult property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSearchResult() {
        return searchResult;
    }

    /**
     * Sets the value of the searchResult property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSearchResult(String value) {
        this.searchResult = value;
    }

    /**
     * Gets the value of the objects property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the objects property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjects().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TraceObject }
     *
     *
     */
    public List<TraceObject> getObjects() {
        if (objects == null) {
            objects = new ArrayList<TraceObject>();
        }
        return this.objects;
    }

}
