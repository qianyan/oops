import org.dom4j.VisitorSupport;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.Element;
import org.dom4j.Document;
public class NamespaceCleaner extends VisitorSupport {
    public void visit(Document document) {
        Element root = document.getRootElement();
        root.setQName(QName.get(root.getName(), Namespace.NO_NAMESPACE));
        root.additionalNamespaces().clear();
    }

    public void visit(Element node) {
        node.setQName(QName.get(node.getName(), Namespace.NO_NAMESPACE));
    }
}
