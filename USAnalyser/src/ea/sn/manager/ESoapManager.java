package ea.sn.manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import ea.sn.loggers.ELogger;

public class ESoapManager {

    static String endPoint;
    String action;
    static String param1Name;
    static String param1Value;
    static String param2Name;
    static String param2Value;
    static String param3Name;
    static String param3Value;
    static String xmlSoapEnvelope;
    static String soapProxyHost;
    static String soapProxyPort;
    static String soapUserName;
//	static String soapFilter ; 
    static char[] soapPassword;

    ELogger logger;
//    static String PROXY_HOST;
//    static Integer PROXY_PORT;

    public static String getSoapUserName() {
        return soapUserName;
    }

    public static void setSoapUserName(String soapUserName) {
        ESoapManager.soapUserName = soapUserName;
    }

    public static String getEndPoint() {
        return endPoint;
    }

    public static void setEndPoint(String e) {
        ESoapManager.endPoint = e;
    }

    public static char[] getSoapPassword() {
        return soapPassword;
    }

    public static void setSoapPassword(char[] sPassword) {
        ESoapManager.soapPassword = sPassword;
    }

    public ELogger getLogger() {
        return logger;
    }

    public void setLogger(ELogger logger) {
        this.logger = logger;
    }

    public static void setParam(int index, String name, String value) {
        if (index == 1) {
            param1Name = name;
            param1Value = value;
        } else if (index == 2) {
            param2Name = name;
            param2Value = value;
        } else if (index == 3) {
            param3Name = name;
            param3Value = value;
        }
    }

    public ESoapManager() {
        System.out.println("*** ESOAP ***");
    }

    public ESoapManager(String zEndPoint, String zAction, String zEnv) {
        System.out.println("*** ESOAP 2 ***");
        endPoint = zEndPoint;
        action = zAction;
        xmlSoapEnvelope = zEnv;
    }

    public static String getSoapProxyHost() {
        return soapProxyHost;
    }

    public static void setSoapProxyHost(String soapProxyHost) {
        ESoapManager.soapProxyHost = soapProxyHost;
    }

    public static String getSoapProxyPort() {
        return soapProxyPort;
    }

    public static void setSoapProxyPort(String soapProxyPort) {
        ESoapManager.soapProxyPort = soapProxyPort;
    }

    public String sendSoapRequest(int index) {
        String strResponse = null, strMessage = null;
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            String soapEndPoint = endPoint;
            SOAPMessage sm = null;

            if (index == 1) {
                sm = createSOAPRequestFromXML(xmlSoapEnvelope);
            } else {
                sm = createSOAPRequest(action);
            }

            strMessage = printSOAPMessage(sm);
            logger.log("SOAP Request: " + strMessage);

            //SOAPMessage soapResponse = soapConnection.call(sm, soapEndPoint);
            SOAPMessage soapResponse = soapCall(soapConnection, sm, soapEndPoint);

            strResponse = printSOAPMessage(soapResponse);
            logger.log("SOAP Response: " + strResponse);
            soapConnection.close();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder(e.toString());
            for (StackTraceElement ste : e.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            strResponse = sb.toString();
            logger.log(strResponse);
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
        //return "SOAP Message:\n" + strMessage + "\n\n\nSOAP Response:\n" + strResponse ;
        return "Response from end point " + endPoint + "\n" + strResponse;
    }

    private static void setCredentials(SOAPMessage soapMessage) {

        String loginPassword = soapUserName + ":" + String.valueOf(soapPassword);
        if (!loginPassword.contentEquals(":")) {
            String loginpasswordEnc = new String(Base64.getEncoder().encode(loginPassword.getBytes()));
            soapMessage.getMimeHeaders().addHeader("Authorization", "Basic " + loginpasswordEnc);
        }
    }

    private static SOAPMessage soapCall(SOAPConnection soapConnection, SOAPMessage soapMessage, String endPoint) {
        boolean isProxy = (soapProxyHost != null && !soapProxyHost.isEmpty());
        SOAPMessage soapResponse = null;
        try {
            if (!isProxy) {
                soapResponse = soapConnection.call(soapMessage, endPoint);
            } else {
                System.out.println("\nProxy in Use " + soapProxyHost + ":" + soapProxyPort);

                System.setProperty("https.proxyHost", soapProxyHost);
                System.setProperty("https.proxyPort", soapProxyPort.toString());

                soapResponse = soapConnection.call(soapMessage, endPoint);
            }
        } catch (SOAPException e) {
            e.printStackTrace();
        }

        return soapResponse;
    }

    private static SOAPMessage createSOAPRequestFromXML(String soapXML) throws Exception {
        String send = xmlSoapEnvelope;
        InputStream is = new ByteArrayInputStream(send.getBytes());
        SOAPMessage request = MessageFactory.newInstance().createMessage(null, is);
        setCredentials(request);

        request.writeTo(System.out);

        return request;
    }

    private static SOAPMessage createSOAPRequest(String myAction) throws Exception {

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        setCredentials(soapMessage);

        SOAPPart soapPart = soapMessage.getSOAPPart();

        int index = endPoint.indexOf('/', 8);
        String serverURI = endPoint.substring(0, index + 1);
        System.out.println("Server URI " + serverURI);

        SOAPEnvelope envelope = soapPart.getEnvelope();
        //envelope.addNamespaceDeclaration("m", "http://www.webserviceX.NET/"); //NEW

        SOAPBody soapBody = envelope.getBody();
        SOAPFactory soapFactory = SOAPFactory.newInstance();

        Name bodyName = soapFactory.createName(myAction, "", serverURI);

        SOAPBodyElement bodyElement = soapBody.addBodyElement(bodyName);

        if (param1Name != null && !param1Name.isEmpty() && !param1Value.isEmpty()) {
            Name name = soapFactory.createName(param1Name);
            SOAPElement symbol = bodyElement.addChildElement(name);
            symbol.addTextNode(param1Value);
        }
        if (param2Name != null && !param2Name.isEmpty()) {
            Name name2 = soapFactory.createName(param2Name);
            SOAPElement symbol2 = bodyElement.addChildElement(name2);
            symbol2.addTextNode(param2Value);
        }
        if (param3Name != null && !param3Name.isEmpty()) {
            Name name3 = soapFactory.createName(param3Name);
            SOAPElement symbol3 = bodyElement.addChildElement(name3);
            symbol3.addTextNode(param3Value);
        }

        //MimeHeaders headers = soapMessage.getMimeHeaders();
        //headers.addHeader("SOAPAction", serverURI  + "GetQuote");
        soapMessage.saveChanges();
        System.out.println();

        return soapMessage;
    }

    /**
     * Method used to print the SOAP Response
     */
    private static String printSOAPMessage(SOAPMessage soapResponse) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nSOAP Message = ");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(out);

        transformer.transform(sourceContent, result);
        System.out.print(out);
        return out.toString();

    }

}
