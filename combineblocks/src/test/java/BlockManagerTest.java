import com.arvifox.BlockManager.BlockDataType;
import com.arvifox.BlockManager.BlockManagerFactory;
import com.arvifox.BlockManager.IBlockManager;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import javax.xml.transform.Source;
import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by Andrey on 09.12.2016.
 */
public class BlockManagerTest {
    @Test
    @Ignore
    public void firsttest() {
        StringBuffer res = new StringBuffer();
        IBlockManager bm = BlockManagerFactory.getInstance().getManager();
        bm.setInput("<Blocks><Block id=\"1\" sizex=\"2\" sizey=\"2\" sizez=\"1\"><Hole x=\"1\" y=\"0\" z=\"0\"/></Block></Blocks>", BlockDataType.XML);
        bm.setOutput(res, BlockDataType.XML);
        bm.calcBlock();
        System.out.println(res.toString());
        Assert.assertTrue("firstmanager", true);
//        System.out.println(System.getProperty("user.dir"));
    }

    @Test
    public void test1() {
        IBlockManager bm = BlockManagerFactory.getInstance().getManager();
        bm.setInput(new File("./src/test/resources/inputdata.xml"), BlockDataType.XML);
        File tf = new File("./src/test/resources/outputdatat.xml");
        bm.setOutput(tf, BlockDataType.XML);
        bm.calcBlock();
        Source s = Input.fromFile(tf).build();
        Source sc = Input.fromFile(new File("./src/test/resources/outputdata.xml")).build();
        Diff d = DiffBuilder.compare(s).withTest(sc).ignoreComments().ignoreWhitespace().normalizeWhitespace().build();
        Assert.assertFalse(d.toString(), d.hasDifferences());
//        try {
//            Assert.assertTrue("testfile1", FileUtils.contentEquals(tf, new File("./src/test/resources/outputdata.xml")));
//        } catch (IOException ex) {
//
//        }
        tf.delete();
    }

    @Test
    public void test2() throws URISyntaxException {
        IBlockManager bm = BlockManagerFactory.getInstance().getManager();
        File ff = new File(this.getClass().getResource("inputdata2.xml").toURI());
        bm.setInput(ff, BlockDataType.XML);
        File tf = new File("res2.xml");
        bm.setOutput(tf, BlockDataType.XML);
        bm.calcBlock();
        Source s = Input.fromFile(tf).build();
        Source sc = Input.fromFile(new File(this.getClass().getResource("outputdata2.xml").toURI())).build();
        Diff d = DiffBuilder.compare(s).withTest(sc).ignoreComments().ignoreWhitespace().normalizeWhitespace().build();
        Assert.assertFalse(d.toString(), d.hasDifferences());
        tf.delete();
    }

    @Test
    public void test3() throws URISyntaxException {
        IBlockManager bm = BlockManagerFactory.getInstance().getManager();
        File ff = new File(this.getClass().getResource("inputdata3.xml").toURI());
        bm.setInput(ff, BlockDataType.XML);
        File tf = new File("res3.xml");
        bm.setOutput(tf, BlockDataType.XML);
        bm.calcBlock();
        Source s = Input.fromFile(tf).build();
        Source sc = Input.fromFile(new File(this.getClass().getResource("outputdata3.xml").toURI())).build();
        Diff d = DiffBuilder.compare(s).withTest(sc).ignoreComments().ignoreWhitespace().normalizeWhitespace().build();
        Assert.assertFalse(d.toString(), d.hasDifferences());
        tf.delete();
    }
}
