package person.seven.designMode.ObserverPattern;/** * @description: 观察者模式 * 一个状态改变，通知所有订阅他的对象状态改变了，在创建订阅者的时候，构造方法加入发布者 * @author: liuqi create on 2018/8/13 **/public class Demo {    public static void main(String[] args) {        Subject subject = new Subject();        new AbcObserver(subject);        new BbcObserver(subject);        new CcbObserver(subject);        System.out.println("First state change: 10");        subject.setState(10);        System.out.println("First state change: 20");        subject.setState(20);    }}