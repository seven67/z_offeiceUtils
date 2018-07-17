package person.seven.annotation;import java.lang.annotation.*;import java.lang.reflect.Field;import java.lang.reflect.Method;import java.text.ParseException;import java.text.SimpleDateFormat;import java.util.Date;import java.util.TimeZone;import static java.lang.annotation.ElementType.FIELD;import static java.lang.annotation.RetentionPolicy.RUNTIME;/** * @description: 注解 * @author: liuqi create on 2018/7/17 **/public class MethodAnnotations {    public static void main(String[] args) throws Exception {        // 获取方法参数的注解        paramsAnnotation();        // 通过注解格式化bean        formatBeanByAnnotation();    }    private static void formatBeanByAnnotation() throws ParseException {        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");        Student zhangsan = new Student("张三", sdf.parse("1990-12-12"), 80.9d);        System.out.println(format(zhangsan));    }    @Retention(RUNTIME)    @Target(FIELD)    static @interface Label {        String value() default "";    }    @Retention(RUNTIME)    @Target(FIELD)    static @interface Format {        String pattern() default "yyyy-MM-dd HH:mm:ss";        String timezone() default "GMT+8";    }    static class Student {        @Label("姓名")        String name;        @Label("出生日期")        @Format(pattern="yyyy/MM/dd")        Date born;        @Label("分数")        double score;        public Student() {        }        public Student(String name, Date born, Double score) {            super();            this.name = name;            this.born = born;            this.score = score;        }        @Override        public String toString() {            return "Student [name=" + name + ", born=" + born + ", score=" + score + "]";        }    }    public static String format(Object obj) {        try {            Class<?> cls = obj.getClass();            StringBuilder sb = new StringBuilder();            for (Field f : cls.getDeclaredFields()) {                if (!f.isAccessible()) {                    f.setAccessible(true);                }                Label label = f.getAnnotation(Label.class);                String name = label != null ? label.value() : f.getName();                Object value = f.get(obj);                if (value != null && f.getType() == Date.class) {                    value = formatDate(f, value);                }                sb.append(name + "：" + value + "\n");            }            return sb.toString();        } catch (IllegalAccessException e) {            throw new RuntimeException(e);        }    }    private static Object formatDate(Field f, Object value) {        Format format = f.getAnnotation(Format.class);        if (format != null) {            SimpleDateFormat sdf = new SimpleDateFormat(format.pattern());            sdf.setTimeZone(TimeZone.getTimeZone(format.timezone()));            return sdf.format(value);        }        return value;    }    @Target(ElementType.PARAMETER)    @Retention(RUNTIME)    static @interface QueryParam {        String value();    }    @Target(ElementType.PARAMETER)    @Retention(RUNTIME)    static @interface DefaultValue {        String value() default "";    }    public void hello(@QueryParam("action") String action,                      @QueryParam("sort") @DefaultValue("asc") String sort){        // ...    }    /**     * 获取方法参数的注解     * @throws NoSuchMethodException     */    private static void paramsAnnotation() throws NoSuchMethodException {        Class<?> cls = MethodAnnotations.class;        Method method = cls.getMethod("hello", new Class[]{String.class, String.class});        Annotation[][] annts = method.getParameterAnnotations();        for(int i=0; i<annts.length; i++){            System.out.println("annotations for paramter " + (i+1));            Annotation[] anntArr = annts[i];            for(Annotation annt : anntArr){                if(annt instanceof QueryParam){                    QueryParam qp = (QueryParam)annt;                    System.out.println(qp.annotationType().getSimpleName()+":"+ qp.value());                }else if(annt instanceof DefaultValue){                    DefaultValue dv = (DefaultValue)annt;                    System.out.println(dv.annotationType().getSimpleName()+":"+ dv.value());                }            }        }    }}