package person.seven.set;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 *  重写equals方法实现去重
 */
public class SetDistinctTest {

    /**
     * 1、hashcode和equals都是object的方法
     * 2、hashcode可以理解为比较轻量级实现，equals是重量级实现
     * 3、丢到set集合中，先调用hashcode方法，如果有重复再调用equals实现
     *    （hashCode()的默认行为是对堆上的对象产生独特值。如果没有重写hashCode()，则该class的两个对象无论如何都不会相等（即使这两个对象指向相同的数据））
     * 4、如果重写equals，不重写hashcode，那equals不会起效
     * @param args
     */

    public static void main(String[] args) {
        Set<Student> students = new HashSet<>();
        Student student1 = new Student("张三", "1");
        Student student2 = new Student("张三", "1");
        Student student3 = new Student("李四", "2");
        Student student4 = new Student("李四", "2");
        Student student5 = new Student("李四", "1");

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);

        System.out.println(students);

    }


    private static class Student {


        public Student() {
            super();
        }

        public Student(String name, String no) {
            super();
            this.name = name;
            this.no = no;
        }

        /**
         * 姓名
         */
        private String name;

        /**
         * 学号
         */
        private String no;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        @Override
        public int hashCode() {
            int result = 1;
            result = result + ((name == null) ? 0 : name.hashCode());
            result = result + ((no == null) ? 0 : no.hashCode());
            System.out.println(result);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Field[] objFields   = obj.getClass().getFields();
            Field[] fields = this.getClass().getFields();
            for (int i = 0; i < objFields.length; i++) {
                for (int j = 0; j < fields.length; j++) {
                    try {
                        if(fields[j].getName().equals(objFields[i].getName()) && !fields[j].get(this).equals(objFields[i].get(obj))){
                            return false;
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }

        @Override
        public String toString() {
            return "Student [name=" + name + ", no=" + no + "]";
        }

    }

}
