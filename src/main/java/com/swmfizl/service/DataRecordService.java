package com.swmfizl.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swmfizl.dao.DataRecordDao;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Service
public class DataRecordService {

	@Autowired
	DataRecordDao dataRecordDao;

	/**
	 * 插入一条数据记录
	 * 
	 * @param map        数据记录实体
	 * @param collection 插入的集合
	 * @return Map
	 */
	public Map<String, Object> insertOne(Map<String, Object> map, String collection) {
		return dataRecordDao.insertOne(map, collection);
	}

	/**
	 * 匹配ID删除
	 * 
	 * @param id         记录ID
	 * @param collection 记录集合
	 * @return
	 */
	public DeleteResult deleteById(String id, String collection) {
		return dataRecordDao.deleteById(id, collection);
	}

	/**
	 * 匹配ID更新记录
	 * 
	 * @param map        更新信息
	 * @param collection 记录集合
	 * @return
	 */
	public UpdateResult updateKeyValue(Map<String, Object> map, String collection) {
		return dataRecordDao.updateKeyValue(map, collection);
	}

	/**
	 * 查询一条数据记录
	 * 
	 * @param id         数据记录ID
	 * @param collection 查询的集合
	 * @return Map
	 */
	public Map<String, Object> selectById(String id, String collection) {
		return dataRecordDao.selectById(id, collection);
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
		return dataRecordDao.selectByCondition(condition, collection);
	}
}

