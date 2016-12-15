package com.arvifox.XmlUtil;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Andrey on 09.12.2016.
 */
public class XmlUtils {
    public static String XmlDocToString(Document xml) {
        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer tra = tf.newTransformer();
            tra.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter swr = new StringWriter();
            tra.transform(new DOMSource(xml), new StreamResult(swr));
            return swr.getBuffer().toString().replaceAll("\n|\r", "");
        } catch (TransformerConfigurationException ex) {
            return "";
        } catch (TransformerException ex) {
            return "";
        }
    }

    public static Document fromXMLString(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
    }

    public static String toStringLS(Document doc) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        DOMImplementationRegistry reg = DOMImplementationRegistry.newInstance();
        DOMImplementationLS impl = (DOMImplementationLS) reg.getDOMImplementation("LS");
        LSSerializer ser = impl.createLSSerializer();
        return ser.writeToString(doc);
    }

    public static String toXMLString(Document document) throws TransformerException, IOException {
        try {
            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
