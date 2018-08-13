package person.seven.designMode.DecoratorPattern;/** * @description: 装饰器模式 * @author: liuqi create on 2018/8/13 **/public class Demo {    public static void main(String[] args) {        Shape circle = new Circle();        Shape redCircle = new RedShapeDecorator(new Circle());        Shape redRectangle = new RedShapeDecorator(new Rectangle());        System.out.println("Circle with normal border");        circle.draw();        System.out.println("\nCircle of red border");        redCircle.draw();        System.out.println("\nRectangle of red border");        redRectangle.draw();    }}