package person.seven.redis;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Create by: seven
 * @Date: 2021/7/8 17:01
 */
@FunctionalInterface
public interface RedissonUtil {


    public void business();

    public static void dealWithLock(RedissonUtil redissonUtil, RedisKeyObj redisKeyObj) {
        // 构造redisson实现分布式锁必要的Config
        RLock disLock = getLock(redisKeyObj);
        System.out.println("获取锁对象" + redisKeyObj);
        boolean isLock;
        try {
            //尝试获取分布式锁
            isLock = disLock.tryLock(500, 5000, TimeUnit.MILLISECONDS);
            if (isLock) {
                System.out.println("获取锁成功，执行业务");
                redissonUtil.business();
            }
        } catch (Exception e) {

        } finally {
            System.out.println("释放锁");
            // 无论如何, 最后都要解锁
            disLock.unlock();
        }
    }

    public static RLock getLock(RedisKeyObj redisKeyObj) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://172.201.1.180:5379").setPassword("a123456").setDatabase(0);
        // 构造RedissonClient
        RedissonClient redissonClient = Redisson.create(config);
        // 设置锁定资源名称

        RLock disLock = redissonClient.getLock(redisKeyObj.getKey());
        return disLock;
    }

    public static RLock getRedLock(RedisKeyObj redisKeyObj) {
        Config config1 = new Config();
        config1.useSingleServer().setAddress("redis://100.100.0.1:5378")
                .setPassword("root").setDatabase(0);
        RedissonClient redissonClient1 = Redisson.create(config1);

        Config config2 = new Config();
        config2.useSingleServer().setAddress("redis://100.100.0.1:5379")
                .setPassword("root").setDatabase(0);
        RedissonClient redissonClient2 = Redisson.create(config2);

        Config config3 = new Config();
        config3.useSingleServer().setAddress("redis://100.100.0.1:5380")
                .setPassword("root").setDatabase(0);
        RedissonClient redissonClient3 = Redisson.create(config3);

        String resourceName = "REDLOCK";

        RLock lock1 = redissonClient1.getLock(resourceName);
        RLock lock2 = redissonClient2.getLock(resourceName);
        RLock lock3 = redissonClient3.getLock(resourceName);
        RedissonRedLock redLock = new RedissonRedLock(lock1, lock2, lock3);
        return redLock;
    }


}
