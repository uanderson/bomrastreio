
package br.com.bomrastreio.api.correios.trace.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eventos complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="eventos"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hora" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descricao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="detalhe" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="recebedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="documento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="comentario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="local" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cidade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="uf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="amazoncode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="amazontimezone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://resource.webservice.correios.com.br/}destino" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://resource.webservice.correios.com.br/}endereco" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eventos", propOrder = {
    "type",
    "status",
    "date",
    "time",
    "description",
    "detail",
    "receiver",
    "document",
    "comment",
    "local",
    "code",
    "city",
    "country",
    "sto",
    "amazonCode",
    "amazonTimeZone",
    "destinations",
    "address"
})
public class TraceEvent {

    @XmlElement(name = "tipo")
    protected String type;
    protected String status;
    @XmlElement(name = "data")
    protected String date;
    @XmlElement(name = "hora")
    protected String time;
    @XmlElement(name = "descricao")
    protected String description;
    @XmlElement(name = "detalhe")
    protected String detail;
    @XmlElement(name = "recebedor")
    protected String receiver;
    @XmlElement(name = "documento")
    protected String document;
    @XmlElement(name = "comentario")
    protected String comment;
    protected String local;
    @XmlElement(name = "codigo")
    protected String code;
    @XmlElement(name = "cidade")
    protected String city;
    @XmlElement(name = "uf")
    protected String country;
    protected String sto;
    @XmlElement(name = "amazoncode")
    protected String amazonCode;
    @XmlElement(name = "amazontimezone")
    protected String amazonTimeZone;
    @XmlElement(name = "destino")
    protected List<TraceDestination> destinations;
    @XmlElement(name = "endereco")
    protected TraceAddress address;

    /**
     * Gets the value of the type property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the date property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Gets the value of the time property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTime(String value) {
        this.time = value;
    }

    /**
     * Gets the value of the description property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the detail property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Sets the value of the detail property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDetail(String value) {
        this.detail = value;
    }

    /**
     * Gets the value of the receiver property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Sets the value of the receiver property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setReceiver(String value) {
        this.receiver = value;
    }

    /**
     * Gets the value of the document property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDocument() {
        return document;
    }

    /**
     * Sets the value of the document property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDocument(String value) {
        this.document = value;
    }

    /**
     * Gets the value of the comment property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the local property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLocal() {
        return local;
    }

    /**
     * Sets the value of the local property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLocal(String value) {
        this.local = value;
    }

    /**
     * Gets the value of the code property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the city property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the country property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the sto property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSto() {
        return sto;
    }

    /**
     * Sets the value of the sto property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSto(String value) {
        this.sto = value;
    }

    /**
     * Gets the value of the amazonCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAmazonCode() {
        return amazonCode;
    }

    /**
     * Sets the value of the amazonCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAmazonCode(String value) {
        this.amazonCode = value;
    }

    /**
     * Gets the value of the amazonTimeZone property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAmazonTimeZone() {
        return amazonTimeZone;
    }

    /**
     * Sets the value of the amazonTimeZone property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAmazonTimeZone(String value) {
        this.amazonTimeZone = value;
    }

    /**
     * Gets the value of the destinations property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the destinations property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDestinations().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TraceDestination }
     *
     *
     */
    public List<TraceDestination> getDestinations() {
        if (destinations == null) {
            destinations = new ArrayList<TraceDestination>();
        }
        return this.destinations;
    }

    /**
     * Gets the value of the address property.
     *
     * @return
     *     possible object is
     *     {@link TraceAddress }
     *
     */
    public TraceAddress getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     *
     * @param value
     *     allowed object is
     *     {@link TraceAddress }
     *
     */
    public void setAddress(TraceAddress value) {
        this.address = value;
    }

}
