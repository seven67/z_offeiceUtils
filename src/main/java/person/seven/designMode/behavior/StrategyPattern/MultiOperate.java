package person.seven.designMode.behavior.StrategyPattern;/** * @description: * @author: liuqi create on 2018/8/16 **/public class MultiOperate implements Strategy {    @Override    public int doOperate(int number1, int number2) {        return number1*number2;    }}