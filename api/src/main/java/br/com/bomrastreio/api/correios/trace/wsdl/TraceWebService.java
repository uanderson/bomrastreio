package br.com.bomrastreio.api.correios.trace.wsdl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.6
 * 2017-02-09T19:17:11.380-02:00
 * Generated source version: 3.1.6
 * 
 */
@WebService(targetNamespace = "http://resource.webservice.correios.com.br/", name = "Service")
@XmlSeeAlso({ObjectFactory.class})
public interface TraceWebService {

    @WebMethod(operationName = "buscaEventosLista", action = "buscaEventosLista")
    @Action(input = "buscaEventosLista", output = "http://resource.webservice.correios.com.br/Service/buscaEventosListaResponse")
    @RequestWrapper(localName = "buscaEventosLista", targetNamespace = "http://resource.webservice.correios.com.br/", className = "br.com.bomrastreio.api.correios.trace.wsdl.MultipleTraceRequest")
    @ResponseWrapper(localName = "buscaEventosListaResponse", targetNamespace = "http://resource.webservice.correios.com.br/", className = "br.com.bomrastreio.api.correios.trace.wsdl.MultipleTraceResponseWrapper")
    @WebResult(name = "return", targetNamespace = "")
    public br.com.bomrastreio.api.correios.trace.wsdl.TraceResponse trace(
        @WebParam(name = "usuario", targetNamespace = "")
        java.lang.String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        java.lang.String senha,
        @WebParam(name = "tipo", targetNamespace = "")
        java.lang.String tipo,
        @WebParam(name = "resultado", targetNamespace = "")
        java.lang.String resultado,
        @WebParam(name = "lingua", targetNamespace = "")
        java.lang.String lingua,
        @WebParam(name = "objetos", targetNamespace = "")
        java.util.List<java.lang.String> objetos
    );

    @WebMethod(operationName = "buscaEventos", action = "buscaEventos")
    @Action(input = "buscaEventos", output = "http://resource.webservice.correios.com.br/Service/buscaEventosResponse")
    @RequestWrapper(localName = "buscaEventos", targetNamespace = "http://resource.webservice.correios.com.br/", className = "br.com.bomrastreio.api.correios.trace.wsdl.SingleTraceRequest")
    @ResponseWrapper(localName = "buscaEventosResponse", targetNamespace = "http://resource.webservice.correios.com.br/", className = "br.com.bomrastreio.api.correios.trace.wsdl.SingleTraceResponseWrapper")
    @WebResult(name = "return", targetNamespace = "")
    public br.com.bomrastreio.api.correios.trace.wsdl.TraceResponse traceOne(
        @WebParam(name = "usuario", targetNamespace = "")
        java.lang.String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        java.lang.String senha,
        @WebParam(name = "tipo", targetNamespace = "")
        java.lang.String tipo,
        @WebParam(name = "resultado", targetNamespace = "")
        java.lang.String resultado,
        @WebParam(name = "lingua", targetNamespace = "")
        java.lang.String lingua,
        @WebParam(name = "objetos", targetNamespace = "")
        java.lang.String objetos
    );
}
