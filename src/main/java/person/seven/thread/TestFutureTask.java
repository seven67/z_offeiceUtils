package person.seven.thread;import java.util.concurrent.Callable;import java.util.concurrent.ExecutionException;import java.util.concurrent.Executors;import java.util.concurrent.FutureTask;/** * @description: 异步处理线程 * @author: liuqi create on 2018/4/18 **/public class TestFutureTask {    static class WaterType {        private String name;        public WaterType(String name) {            this.name = name;        }        public String getName() {            return name;        }        public void setName(String name) {            this.name = name;        }    }    static class BoilWater implements Callable<WaterType>{        @Override        public WaterType call() {            System.out.println("");            try {                Thread.sleep(3000);            } catch (InterruptedException e) {                e.printStackTrace();            }            return new WaterType("主人，主人：龙井水烧好了！");        }    }    public static void main(String[] args) throws InterruptedException, ExecutionException {        System.out.println("周末在家打刀塔，针哈皮！");        Thread.sleep(1000);        System.out.println("好像有点口渴了啊，去烧点水");        FutureTask futureTask = new FutureTask(new BoilWater());        Executors.newFixedThreadPool(1,Executors.defaultThreadFactory()).submit(futureTask);        System.out.println("水烧着了，赶紧肥来上高地");        Thread.sleep(2000);        System.out.println("擦，团灭一波，水烧好了没");        while (!futureTask.isDone()){            System.out.println("水還沒燒好啊，再烧会");            Thread.sleep(400);        }        System.out.println("滴滴滴 ");        WaterType waterType = (WaterType) futureTask.get();        System.out.println(waterType.getName());    }}