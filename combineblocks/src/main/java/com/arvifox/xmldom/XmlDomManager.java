package com.arvifox.xmldom;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by Andrey on 06.12.2016.
 */
public class XmlDomManager {
    private File inputfile;
    private File outpurfile;

    public File getInputfile() {
        return inputfile;
    }

    public void setInputfile(File inputfile) {
        this.inputfile = inputfile;
    }

    public File getOutpurfile() {
        return outpurfile;
    }

    public void setOutpurfile(File outpurfile) {
        this.outpurfile = outpurfile;
    }

    public XmlDomManager() {
    }

    public void doIt() {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(inputfile);
            Node root = document.getDocumentElement();

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
