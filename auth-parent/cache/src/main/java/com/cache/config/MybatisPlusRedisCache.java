package com.cache.config;


import com.cache.util.SpringUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
@NoArgsConstructor
public class MybatisPlusRedisCache implements Cache {
    // 读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    //这里使用了redis缓存，使用springboot自动注入
    private RedisTemplate<String, Object> redisTemplate;

    private String id;

    public MybatisPlusRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache实例需要一个id");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        if (redisTemplate == null) {
            //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
        }
        if (value != null) {
            redisTemplate.opsForValue().set(key.toString(), value);
        }
    }

    @Override
    public Object getObject(Object key) {
        if (redisTemplate == null) {
            //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
        }
        try {
            if (key != null) {
                return redisTemplate.opsForValue().get(key.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("缓存出错 ");
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        if (redisTemplate == null) {
            //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
        }
        if (key != null) {
            redisTemplate.delete(key.toString());
        }
        return null;
    }

    @Override
    public void clear() {
        log.debug("清空缓存");
        if (redisTemplate == null) {
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
        }
        try {
            Set<String> keys = scan(this.id);
            if (!CollectionUtils.isEmpty(keys)) {
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
            log.error("清空缓存", e);
        }
    }

    public Set<String> scan(String matchKey) {
        if (redisTemplate == null) {
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
        }
        Set<String> keys = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keysTmp = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match("*" + matchKey + "*").count(1000).build());
            while (cursor.hasNext()) {
                keysTmp.add(new String(cursor.next()));
            }
            return keysTmp;
        });

        return keys;
    }

    @Override
    public int getSize() {
        if (redisTemplate == null) {
            //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
        }
        Long size = redisTemplate.execute((RedisCallback<Long>) RedisServerCommands::dbSize);
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
