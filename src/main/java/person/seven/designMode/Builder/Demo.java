package person.seven.designMode.Builder;/** * @description: 建造者模式 * 它提供了一种创建对象的最佳方式。不需要写多行set * @author: liuqi create on 2018/8/10 **/public class Demo {    public static void main( String[] args ){        Builder.Student a = new Builder.Student.StudentBuilder().setAge(13).setName("LiHua").build();        Builder.Student b = new Builder.Student.StudentBuilder().setSchool("sc").setSex("Male").setName("ZhangSan").build();        System.out.println(a);        System.out.println(b);    }}