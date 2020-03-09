package com.swmfizl.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Component
public class DataRecordDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 插入一条数据记录
	 * 
	 * @param map        数据记录实体
	 * @param collection 插入的集合
	 * @return Map
	 */
	public Map<String, Object> insertOne(Map<String, Object> map, String collection) {
		return mongoTemplate.insert(map, collection);
	}

	/**
	 * 匹配ID删除
	 * 
	 * @param id         记录ID
	 * @param collection 记录集合
	 * @return
	 */
	public DeleteResult deleteById(String id, String collection) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		return mongoTemplate.remove(query, collection);
	}

	/**
	 * 匹配ID更新记录
	 * 
	 * @param map        更新信息
	 * @param collection 记录集合
	 * @return
	 */
	public UpdateResult updateKeyValue(Map<String, Object> map, String collection) {
		Query query = new Query();
		Update update = new Update();
		query.addCriteria(Criteria.where("_id").is(map.get("id").toString()));
		for (String key : map.keySet()) {
			if (!key.equals("id")) {
				update.set(key, map.get(key));
			}
		}
		return mongoTemplate.upsert(query, update, Map.class, collection);
	}

	/**
	 * 查询一条数据记录
	 * 
	 * @param id         数据记录ID
	 * @param collection 查询的集合
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectById(String id, String collection) {
		return mongoTemplate.findById(id, Map.class, collection);
	}

	/**
	 * 匹配条件查询
	 * 
	 * @param condition  条件
	 * @param collection 查询的集合
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> selectByCondition(Map<String, Object> condition, String collection) {
		Query query = new Query();
		int pageIndex = condition.get("pageIndex") == null ? 0
				: Integer.parseInt(condition.get("pageIndex").toString());
		int pageSize = condition.get("pageSize") == null ? 0 : Integer.parseInt(condition.get("pageSize").toString());
		for (String key : condition.keySet()) {
			if (!key.equals("pageIndex") && !key.equals("pageSize")) {
				query.addCriteria(Criteria.where(key).is(condition.get(key)));
			}
		}
		query.with(Sort.by(Sort.Order.desc("_id")));
		query.skip((pageIndex - 1) * pageSize);
		query.limit((pageIndex - 1) * pageSize + pageSize);
		return mongoTemplate.find(query, Map.class, collection);
	}
}

