package person.seven.thread.future;/** * @description: * @author: liuqi create on 2018/4/20 **/public class FutureData implements Data{    private RealData realData = null;    private volatile boolean ready = false;    public synchronized void setResponse(RealData realData) {        if(ready) return;        this.realData = realData;        ready = true;        notifyAll();    }    @Override    public synchronized String getResponse() throws InterruptedException {        if(!ready){            wait();        }        return realData.getResponse();    }}