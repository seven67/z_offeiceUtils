package person.seven.redis;


/**
 * @Description:
 * @Create by: seven
 * @Date: 2021/7/8 16:52
 */
public class Test {

    public static void main(String[] args) {
        RedisKeyObj redisKeyObj = new RedisKeyObj();
        redisKeyObj.setKey("first key");
        RedissonUtil.dealWithLock( () ->{
            System.out.println(" 业务 ");
        },redisKeyObj);
    }
}
