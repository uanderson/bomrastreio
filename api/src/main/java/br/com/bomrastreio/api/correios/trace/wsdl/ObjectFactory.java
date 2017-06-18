
package br.com.bomrastreio.api.correios.trace.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.com.bomrastreio.api.correios.trace.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BuscaEventos_QNAME = new QName("http://resource.webservice.correios.com.br/", "buscaEventos");
    private final static QName _BuscaEventosLista_QNAME = new QName("http://resource.webservice.correios.com.br/", "buscaEventosLista");
    private final static QName _BuscaEventosListaResponse_QNAME = new QName("http://resource.webservice.correios.com.br/", "buscaEventosListaResponse");
    private final static QName _BuscaEventosResponse_QNAME = new QName("http://resource.webservice.correios.com.br/", "buscaEventosResponse");
    private final static QName _Destino_QNAME = new QName("http://resource.webservice.correios.com.br/", "destino");
    private final static QName _Endereco_QNAME = new QName("http://resource.webservice.correios.com.br/", "endereco");
    private final static QName _Evento_QNAME = new QName("http://resource.webservice.correios.com.br/", "evento");
    private final static QName _Objeto_QNAME = new QName("http://resource.webservice.correios.com.br/", "objeto");
    private final static QName _Rastro_QNAME = new QName("http://resource.webservice.correios.com.br/", "rastro");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.com.bomrastreio.api.correios.trace.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SingleTraceRequest }
     * 
     */
    public SingleTraceRequest createSingleTraceRequest() {
        return new SingleTraceRequest();
    }

    /**
     * Create an instance of {@link MultipleTraceRequest }
     * 
     */
    public MultipleTraceRequest createMultipleTraceRequest() {
        return new MultipleTraceRequest();
    }

    /**
     * Create an instance of {@link MultipleTraceResponseWrapper }
     * 
     */
    public MultipleTraceResponseWrapper createMultipleTraceResponseWrapper() {
        return new MultipleTraceResponseWrapper();
    }

    /**
     * Create an instance of {@link SingleTraceResponseWrapper }
     * 
     */
    public SingleTraceResponseWrapper createSingleTraceResponseWrapper() {
        return new SingleTraceResponseWrapper();
    }

    /**
     * Create an instance of {@link TraceDestination }
     * 
     */
    public TraceDestination createTraceDestination() {
        return new TraceDestination();
    }

    /**
     * Create an instance of {@link TraceAddress }
     * 
     */
    public TraceAddress createTraceAddress() {
        return new TraceAddress();
    }

    /**
     * Create an instance of {@link TraceEvent }
     * 
     */
    public TraceEvent createTraceEvent() {
        return new TraceEvent();
    }

    /**
     * Create an instance of {@link TraceObject }
     * 
     */
    public TraceObject createTraceObject() {
        return new TraceObject();
    }

    /**
     * Create an instance of {@link TraceResponse }
     * 
     */
    public TraceResponse createTraceResponse() {
        return new TraceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SingleTraceRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://resource.webservice.correios.com.br/", name = "buscaEventos")
    public JAXBElement<SingleTraceRequest> createBuscaEventos(SingleTraceRequest value) {
        return new JAXBElement<SingleTraceRequest>(_BuscaEventos_QNAME, SingleTraceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultipleTraceRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://resource.webservice.correios.com.br/", name = "buscaEventosLista")
    public JAXBElement<MultipleTraceRequest> createBuscaEventosLista(MultipleTraceRequest value) {
        return new JAXBElement<MultipleTraceRequest>(_BuscaEventosLista_QNAME, MultipleTraceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultipleTraceResponseWrapper }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://resource.webservice.correios.com.br/", name = "buscaEventosListaResponse")
    public JAXBElement<MultipleTraceResponseWrapper> createBuscaEventosListaResponse(MultipleTraceResponseWrapper value) {
        return new JAXBElement<MultipleTraceResponseWrapper>(_BuscaEventosListaResponse_QNAME, MultipleTraceResponseWrapper.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SingleTraceResponseWrapper }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://resource.webservice.correios.com.br/", name = "buscaEventosResponse")
    public JAXBElement<SingleTraceResponseWrapper> createBuscaEventosResponse(SingleTraceResponseWrapper value) {
        return new JAXBElement<SingleTraceResponseWrapper>(_BuscaEventosResponse_QNAME, SingleTraceResponseWrapper.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TraceDestination }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://resource.webservice.correios.com.br/", name = "destino")
    public JAXBElement<TraceDestination> createDestino(TraceDestination value) {
        return new JAXBElement<TraceDestination>(_Destino_QNAME, TraceDestination.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TraceAddress }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://resource.webservice.correios.com.br/", name = "endereco")
    public JAXBElement<TraceAddress> createEndereco(TraceAddress value) {
        return new JAXBElement<TraceAddress>(_Endereco_QNAME, TraceAddress.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TraceEvent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://resource.webservice.correios.com.br/", name = "evento")
    public JAXBElement<TraceEvent> createEvento(TraceEvent value) {
        return new JAXBElement<TraceEvent>(_Evento_QNAME, TraceEvent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TraceObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://resource.webservice.correios.com.br/", name = "objeto")
    public JAXBElement<TraceObject> createObjeto(TraceObject value) {
        return new JAXBElement<TraceObject>(_Objeto_QNAME, TraceObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TraceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://resource.webservice.correios.com.br/", name = "rastro")
    public JAXBElement<TraceResponse> createRastro(TraceResponse value) {
        return new JAXBElement<TraceResponse>(_Rastro_QNAME, TraceResponse.class, null, value);
    }

}
