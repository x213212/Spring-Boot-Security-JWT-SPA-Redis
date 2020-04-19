/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.inlighting.security.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

/**
 * Caches {@link UserDetails} instances in a Spring defined {@link Cache}.
 *
 * @author Marten Deinum
 * @since 3.2
 */
public class SessionRedisUserCache implements UserCache {

	// ~ Static fields/initializers
	// =====================================================================================

	public static final Log logger = LogFactory.getLog(SessionRedisUserCache.class);

	// ~ Instance fields
	// ================================================================================================
	public long sessionTimeout = 100;
	public final Cache cache;
    public final RedisTemplate<String, CustomUserDetails> redisTemplate;
	// ~ Constructors
	// ===================================================================================================

	public SessionRedisUserCache(Cache cache, RedisTemplate<String, CustomUserDetails> redisTemplate) throws Exception {
		Assert.notNull(cache, "cache mandatory");
		Assert.notNull(redisTemplate, "redisTemplate mandatory");
		this.redisTemplate = redisTemplate;
		this.cache = cache;
	}

	// ~ Methods
	// ========================================================================================================

	public UserDetails getUserFromCache(String username) {
		//Cache.ValueWrapper element = username != null ? cache.get(username) : null;
		CustomUserDetails result = username != null ? (CustomUserDetails) redisTemplate.opsForValue().get(username): null; 
		if (logger.isDebugEnabled()) {
			logger.debug("Cache hit:" + (result != null) + "; username: " + username);
			
		}
	      

		if (result == null) {
			System.out.println("Cache NO hit:"+username);
			return null;
		}
		else {
		System.out.println("Cache hit:" + (result != null) + "; username: " + username);

		return (UserDetails) result;
		}
	}


	public void putUserInCache(UserDetails user) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + user.getUsername());
	
		}

		//重寫userdetails
		CustomUserDetails customUserDetails = new CustomUserDetails (user.getUsername(),user.getPassword(),user.getAuthorities());
	     System.out.println(customUserDetails.getUsername());
	     System.out.println(customUserDetails.getAuthorities());

	     redisTemplate.opsForValue().set(customUserDetails.getUsername(),customUserDetails,sessionTimeout);
	     //原本opsForValue()是只能操作字符串的.现在就可以操作对象了
	    //customUserDetails result = (customUserDetails) template.opsForValue().get(customUserDetails.getUsername()+"");
		//System.out.println(result.toString());
		//ache.put(user.getUsername(), user);
	}

	public void removeUserFromCache(UserDetails user) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + user.getUsername());
		}

		this.removeUserFromCache(user.getUsername());
	}

	public void removeUserFromCache(String username) {
		redisTemplate.delete(username);
	

//		cache.evict(username);
	}
}
