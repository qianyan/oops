Dom4j Study
==========
* dom4j1.6.1
* jaxen1.1.6 -> xpath engine 
* an aixs
* node test
* function
* zero or more predicates

### Aixs

`/root/foo`

### node test

`/root/foo/comment()`

`/root/foo/text()`

### function

`//foo[position()=1]`

`count(//foo/node())`

### predicates

* Every predicates must be in [].

`/root/foo//bar[name(..)='foo']/@a`

`/root/foo[bar] | /root/oop[bar]`

### namespace

* namespace definition

`<B:bar xmlns:B="some schema url"></B:bar>`

* how to use xpath under namespace

1.`/*local-name()='bar'`

2. set namespace alias

```
DocumentHelper.createXPath("//B:bar");
map.put("B", "some schema url");
xpath.setNamespaceURL(map);
```
3. set no namespace

```
doc = DocumentHelper.parseText("<B:root xmlns:B="url">something</B:root>");
doc.accept(new NamespaceCleaner());
xpath.selectSingleNode(doc.getRootElement());
```
