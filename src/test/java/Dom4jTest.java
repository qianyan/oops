import org.dom4j.Document; 
import org.dom4j.DocumentException; 
import org.dom4j.io.SAXReader;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.XMLWriter;
import org.dom4j.io.OutputFormat;
import java.util.List;
import java.io.FileWriter;

import org.junit.Test;
import org.junit.Before;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Dom4jTest {
    private final SAXReader reader = new SAXReader();
    private Document doc;
    @Before
    public void setUp() throws Exception {
        doc = reader.read("src/test/resources/sample.xml");
    } 

    @Test
    public void should_get_bar_nodes() throws Exception {
        Element root = doc.getRootElement();
        List nodes = root.selectNodes("//foo/bar");

        assertThat(nodes.size() > 0, is(true));
    }

    @Test
    public void should_get_the_text_of_bar_element() throws Exception {
        Element root = doc.getRootElement();
        Node node = root.selectSingleNode("//foo/bar[1]");
        String text = node.getText();

        assertThat(text.trim(), is("barbarbar...."));
    }

    @Test
    public void should_add_par_to_document() throws Exception {
        Element root = doc.getRootElement();
        Element elem = (Element)root.selectSingleNode("//foo/bar[1]");
        elem.addElement("par")
            .addAttribute("k", "v")
            .addText("parparpar...");

        Node par = elem.selectSingleNode("//foo/bar/par");
        
        assertThat(par.getText().trim(), is("parparpar..."));
    }

    @Test
    public void should_delete_par_from_document() throws Exception {
        Element root = doc.getRootElement();
        Node elem = root.selectSingleNode("//foo/bar[1]");
        elem.detach();

        List list = root.selectNodes("//foo/bar");

        assertThat(list.size(), is(1));
    }

    @Test
    public void should_be_able_to_write_to_file() throws Exception {
        Element root = doc.getRootElement();
        root.addElement("par")
            .addAttribute("k", "v")
            .addText("parparpar....");

        XMLWriter writer = new XMLWriter(new FileWriter("src/test/resources/new.xml"));
        writer.write(root);
        writer.close();

        OutputFormat format = OutputFormat.createPrettyPrint();
        writer = new XMLWriter(System.out, format);
        writer.write( root );
        writer.close();

    }
}
