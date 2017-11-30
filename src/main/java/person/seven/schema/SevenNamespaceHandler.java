package person.seven.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author liuqi
 * @create 2017-11-30
 **/
public class SevenNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("person",new PersonBeanDefinitioParser());
    }
}
