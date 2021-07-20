package person.seven.skiplist;

/**
 * Description:
 *
 * @author : seven
 * @Date: 2021/7/20 17:49
 */
public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SkipList<String> list = new SkipList<String>();
        System.out.println(list);
        list.put(2, "yan");
        list.put(1, "co");
        list.put(3, "feng");
        //测试同一个key值
        list.put(1, "cao");
        list.put(4, "你");
        list.put(6, "丰");
        list.put(5, "好");
        System.out.println(list);
        System.out.println(list.size());
    }
}
