package person.seven.designMode.behavior.VisitorPattern;/** * @description: * @author: liuqi create on 2018/8/16 **/public interface ComputerPartVisitor {    public void visit(Monitor monitor);    public void visit(Mouse mouse);    public void visit(Keyboard keyboard);    public void visit(Computer computer);}