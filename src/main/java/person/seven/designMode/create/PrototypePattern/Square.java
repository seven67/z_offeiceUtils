package person.seven.designMode.create.PrototypePattern;/** * @description: * @author: liuqi create on 2018/8/14 **/public class Square extends Shape {    public Square(String id) {        this.id = id;        type = "这是正方形！";    }    @Override    void draw() {    }}