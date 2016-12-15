package com.arvifox.BlockManager;

import com.arvifox.SingleBlock.SingleBlockResult;
import com.arvifox.XmlUtil.XmlUtils;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringWriter;

/**
 * Created by Andrey on 09.12.2016.
 */
public class XmlDomWriter implements IWriter {
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private Document docxml;
    private Element root;

    private File file;
    private StringBuffer rstr;

    public XmlDomWriter() {
        dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
            docxml = db.newDocument();
        } catch (ParserConfigurationException ex) {

        }
    }

    @Override
    public void setString(StringBuffer s) {
        rstr = s;
    }

    @Override
    public void setFile(File f) {
        file = f;
    }

    public void addCDATA() {
        CDATASection cdata = docxml.createCDATASection("this cdata");
        root.appendChild(cdata);
    }

    @Override
    public void doWrite(SingleBlockResult sbr) {
        Element bl = docxml.createElement("Block");
        bl.setAttribute("id", Integer.toString(sbr.uid));
        root.appendChild(bl);
        for (SingleBlockResult.BlockItem bi : sbr.resultBlocks) {
            Element cbl = docxml.createElement("Cblock");
            bl.appendChild(cbl);
            cbl.setAttribute("xstart", Integer.toString(bi.start.getX()));
            cbl.setAttribute("ystart", Integer.toString(bi.start.getY()));
            cbl.setAttribute("zstart", Integer.toString(bi.start.getZ()));
            cbl.setAttribute("xlength", Integer.toString(bi.length.getX()));
            cbl.setAttribute("ylength", Integer.toString(bi.length.getY()));
            cbl.setAttribute("zlength", Integer.toString(bi.length.getZ()));
        }
    }

    @Override
    public void prepareToWrite() {
        root = docxml.createElement("Blocks");
        docxml.appendChild(root);
    }

    @Override
    public void finishWrite() {
        if (file != null) {
            try {
                Transformer t = TransformerFactory.newInstance().newTransformer();
                t.setOutputProperty(OutputKeys.METHOD, "xml");
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.transform(new DOMSource(docxml), new StreamResult(new FileOutputStream(file)));
            } catch (TransformerConfigurationException ex) {

            } catch (FileNotFoundException ex) {

            } catch (TransformerException ex) {

            }
        }
        if (rstr != null) {
            try {
                rstr.append(XmlUtils.toStringLS(docxml));
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
    }
}
