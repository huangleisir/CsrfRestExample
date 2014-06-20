package sk.bsmk.csrf;

import org.junit.Test;
import org.redisson.Redisson;
import redis.embedded.RedisServer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmbeddedRedisServerTest {

    @Test
    public void thatEmbeddedRedisServerWorks() throws Exception {
        RedisServer redisServer = new RedisServer(6379);
        assertFalse(redisServer.isActive());
        redisServer.start();
        assertTrue(redisServer.isActive());
        redisServer.stop();
        assertFalse(redisServer.isActive());
    }

    @Test
    public void thatRedissonCanRetrieveMapFromEmbeddedServer() throws Exception {
        RedisServer redisServer = new RedisServer(6379);
        redisServer.start();
        Redisson redisson = Redisson.create();
        assertThat(redisson.getMap("test"), is(notNullValue()));
        redisson.shutdown();
        redisServer.stop();
    }

}
