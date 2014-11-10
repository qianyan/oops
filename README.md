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



