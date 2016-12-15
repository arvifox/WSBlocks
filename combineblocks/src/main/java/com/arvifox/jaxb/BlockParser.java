package com.arvifox.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Andrey on 06.12.2016.
 */
public class BlockParser implements BlocksParserInt {
    @Override
    public Object getObject(File file, Class c) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(c);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object o = unmarshaller.unmarshal(file);
        return o;
    }

    @Override
    public void saveObject(File file, Object c) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(c.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(c, file);
    }
}
