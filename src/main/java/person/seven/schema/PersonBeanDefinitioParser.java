package person.seven.schema;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author liuqi
 * @create 2017-11-30
 **/
public class PersonBeanDefinitioParser extends AbstractSingleBeanDefinitionParser {

    @Nullable
    @Override
    protected Class<?> getBeanClass(Element element) {
        return Person.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String name = element.getAttribute("name");
        String age = element.getAttribute("age");

        builder.addPropertyValue("name", name);
        builder.addPropertyValue("age", Integer.valueOf(age));

    }
}
