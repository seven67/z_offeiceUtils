package person.seven.designMode.behavior.ObserverPattern;/** * @description: * @author: liuqi create on 2018/8/13 **/public class CcbObserver extends Observer{    public CcbObserver(Subject subject) {        this.subject = subject;        subject.attach(this);    }    @Override    public void update() {        System.out.println("CCB电台收到通知，同步状态："+subject.getState());    }}