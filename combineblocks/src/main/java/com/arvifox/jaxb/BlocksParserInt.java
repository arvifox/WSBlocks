package com.arvifox.jaxb;

import javax.xml.bind.JAXBException;
import java.io.File;

/**
 * Created by Andrey on 06.12.2016.
 */
public interface BlocksParserInt {
    Object getObject(File file, Class c) throws JAXBException;
    void saveObject(File file, Object c) throws JAXBException;
}
