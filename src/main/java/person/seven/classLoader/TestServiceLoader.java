package person.seven.classLoader;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author liuqi
 * @create 2017-11-30
 **/
public class TestServiceLoader {

    public static void main(String[] args) {
        ServiceLoader<Company> serviceLoader = ServiceLoader.load(Company.class);
        Iterator<Company> animalIterator = serviceLoader.iterator();
        while(animalIterator.hasNext()){
            Company company = animalIterator.next();
            company.printName();
        }
    }
}
