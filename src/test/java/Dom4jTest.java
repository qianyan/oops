import org.dom4j.Document; 
import org.dom4j.DocumentException; 
import org.dom4j.io.SAXReader;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.XMLWriter;
import org.dom4j.io.OutputFormat;
import java.util.List;
import java.util.Iterator;
import java.io.FileWriter;

import org.junit.Test;
import org.junit.Before;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Dom4jTest {
    private final SAXReader reader = new SAXReader();
    private Document doc;
    private Element root;
    @Before
    public void setUp() throws Exception {
        doc = reader.read("src/test/resources/sample.xml");
        root = doc.getRootElement();
    } 

    @Test
    public void should_get_bar_nodes() throws Exception {
        List nodes = root.selectNodes("//foo/bar");

        assertThat(nodes.size() > 0, is(true));
    }

    @Test
    public void should_get_the_text_of_bar_element() throws Exception {
        Node node = root.selectSingleNode("//foo/bar[1]");
        String text = node.getText();

        assertThat(text.trim(), is("barbarbar...."));
    }

    @Test
    public void should_modify_a_node_text() throws Exception {
        Node node = root.selectSingleNode("//foo/bar[1]");
        node.setText("hahaha....");

        Node newNode = root.selectSingleNode("//foo/bar[1]");
        String text = newNode.getText();


        assertThat(text.trim(), is("hahaha...."));
    }

    @Test
    public void should_add_par_to_document() throws Exception {
        Element elem = (Element)root.selectSingleNode("//foo/bar[1]");
        elem.addElement("par")
            .addAttribute("k", "v")
            .addText("parparpar...");

        Node par = elem.selectSingleNode("//foo/bar/par");
        
        assertThat(par.getText().trim(), is("parparpar..."));
    }

    @Test
    public void should_delete_par_from_document() throws Exception {
        Node elem = root.selectSingleNode("//foo/bar[1]");
        elem.detach();

        List list = root.selectNodes("//foo/bar");

        assertThat(list.size(), is(1));
    }

    @Test
    public void should_be_able_to_write_to_file() throws Exception {
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

    @Test
    public void should_get_root_element_from_doc() throws Exception {
        assertThat(doc.getRootElement(), is(root));
    }

    @Test
    public void shoudld_get_node_by_name() throws Exception {
        Element elem = root.element("foo");
        String text = elem.getText();

        assertThat(text, is("foolish"));
    }

    @Test
    public void should_get_nodes_by_their_names() throws Exception {
        List list = root.elements("foo");

        assertThat(list.size(), is(3));
    }

    @Test
    public void should_travel_all_children_elements() throws Exception {
        Iterator it = root.elementIterator();
        Element e = (Element)it.next();

        assertThat(e.getText().trim(), is("foolish"));
    }

    @Test
    public void should_get_node_by_attribute() throws Exception{
        Node node = root.selectSingleNode("//foo[@k='v']");
        String text = node.getText();

        assertThat(text.trim(), is("foolish"));
    }

    @Test
    public  void should_get_the_first_node_under_foo() throws Exception {
        List list = root.selectNodes("//foo/*[1]");

        assertThat(list.size(), is(1));
    }

    // and [cond1][cond2]
    @Test
    public void should_get_attribute_by_mutiple_conditions() throws Exception {
        String text = root.valueOf("//bar[name(..)='foo']/@a");

        assertThat(text, is("b"));
    }

    @Test
    public void should_get_first_node_being_processed() throws Exception {
        Node firstNode =  root.selectSingleNode("//foo[position()=1]");
        String text = firstNode.getText();

        assertThat(text.trim(), is("foolish"));
    }

    // or [cond1] | [cond2]
    @Test
    public void should_get_these_nodes_matched() throws Exception {
        List list =  root.selectNodes("foo[bar] | oop[bar]");

        assertThat(list.size(), is(2));
    }

    @Test
    public void should_get_comment() throws Exception {
        Node node = root.selectSingleNode("//foo/comment()");

        String text = node.getText();

        assertThat(text.trim(), is("it's a comment"));
    }

    // count()
    @Test
    public void should_get_the_number_of_nodes() throws Exception {
        Object number = root.selectObject("count(//foo/node())");

        assertThat((Double)number, is(9.0));
    }

    // string function
    @Test
    public void should_concat_all_texts_of_foo_nodes() throws Exception {
        String concatString = root.valueOf("concat(//foo[1]/text(), //foo[2]/text())");

        assertThat(concatString, is("foolishfoolish"));
    }
}
