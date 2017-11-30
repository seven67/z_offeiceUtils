package person.seven.schema;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liuqi
 * @create 2017-11-30
 **/
public class SchemaTest {

    private static final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

    public static void main(String[] args) {
        Person user = (Person) context.getBean("isMe");
        System.out.println(user.getName());
        System.out.println(user.getAge());
    }


}
