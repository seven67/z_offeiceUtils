package test;import org.junit.Test;import org.springframework.beans.factory.annotation.Autowired;import person.seven.aop.log.UserController;/** * @description: * @author: liuqi create on 2018/7/4 **/public class LogTest extends TestSuper{    @Autowired    private UserController userController;    @Test    public void testAOP1(){        userController.testAOP("zhangsan", "123456");        userController.delete("zhangsan", "123456");    }}