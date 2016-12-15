package com.arvifox.BlockManager;

import com.arvifox.Block.BlockData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Andrey on 09.12.2016.
 */
public class XmlDomReader implements IReader {

    private OnBlockReadListener onBlockReadListener;
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private Document docxml;

    public XmlDomReader() {
        dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {

        }
    }

    @Override
    public void startRead() {
        Element root = docxml.getDocumentElement();
        if (root.getTagName().equals("Blocks")) {
            NodeList nl = root.getChildNodes();
            for (int i = 0; i < nl.getLength(); i++) {
                Node nn= nl.item(i);
                if (nn.getNodeType() != Node.TEXT_NODE) {
                    Element el = (Element) nl.item(i);
                    if (el.getTagName().equals("Block")) {
                        BlockData bd = new BlockData();
                        if (el.hasAttribute("id"))
                            bd.setUid(Integer.parseInt(el.getAttribute("id")));
                        int x = 0, y = 0, z = 0;
                        if (el.hasAttribute("sizex"))
                            x = Integer.parseInt(el.getAttribute("sizex"));
                        if (el.hasAttribute("sizey"))
                            y = Integer.parseInt(el.getAttribute("sizey"));
                        if (el.hasAttribute("sizez"))
                            z = Integer.parseInt(el.getAttribute("sizez"));
                        bd.setSize(x, y, z);
                        NodeList bholes = el.getChildNodes();
                        for (int j = 0; j < bholes.getLength(); j++) {
                            Node nnb = bholes.item(j);
                            if (nnb.getNodeType() != Node.TEXT_NODE) {
                                Element bh = (Element) bholes.item(j);
                                if (bh.getTagName().equals("Hole")) {
                                    if (bh.hasAttribute("x"))
                                        x = Integer.parseInt(bh.getAttribute("x"));
                                    if (bh.hasAttribute("y"))
                                        y = Integer.parseInt(bh.getAttribute("y"));
                                    if (bh.hasAttribute("z"))
                                        z = Integer.parseInt(bh.getAttribute("z"));
                                    bd.addHole(x, y, z);
                                }
                            }
                        }
                        onBlockReadListener.onBlockRead(bd);
                    }
                }
            }
        }
    }

    @Override
    public void setOnBlockReadListener(OnBlockReadListener onBlockReadListener) {
        this.onBlockReadListener = onBlockReadListener;
    }

    @Override
    public void setString(String string) {
        try {
            docxml = db.parse(new InputSource(new StringReader(string)));
        } catch (SAXException ex) {

        } catch (IOException ex) {

        }
    }

    @Override
    public void setFile(File file) {
        try {
            docxml = db.parse(file);
        } catch (SAXException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
