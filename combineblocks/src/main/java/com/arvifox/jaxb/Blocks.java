package com.arvifox.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Andrey on 06.12.2016.
 */
@XmlRootElement(name = "blocks")
public class Blocks {
    private int id;
    private List<Block> blocks;

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    @XmlElement(name = "block")
    @XmlElementWrapper
    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }
}
