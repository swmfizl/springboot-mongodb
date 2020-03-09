package com.swmfizl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swmfizl.service.DataRecordService;

@Controller
@ResponseBody
@CrossOrigin(value = "*", maxAge = 3600)
public class DataQueryController {

	@Autowired
	DataRecordService dataRecordService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/dataQuery", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public Map<String, Object> dataInsert(@RequestBody Map<String, Object> request) {
		// response响应
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> dataRecord = new ArrayList<Map<String, Object>>();
		
		List<String> ids = (List<String>) request.get("id");
		String collection = request.get("collection").toString();
		Map<String, Object> condition = (Map<String, Object>) request.get("condition");
		// 匹配ID查询
		if (ids != null) {
			for (int i = 0; i < ids.size(); i++) {
				String id = ids.get(i);
				Map<String, Object> dataOne = dataRecordService.selectById(id, collection);
				if (dataOne != null) {
					dataOne.put("id", dataOne.get("_id").toString());
					dataOne.remove("_id");
					dataRecord.add(dataOne);
				}
			}
			data.put("data", dataRecord);
		} else {
			// 匹配条件查询
			@SuppressWarnings("rawtypes")
			List<Map> dataAll = dataRecordService.selectByCondition(condition, collection);
			condition.remove("pageIndex");
			condition.remove("pageSize");
			@SuppressWarnings("rawtypes")
			List<Map> dataSum = dataRecordService.selectByCondition(condition, collection);
			for (Map<String, Object> dataOne : dataAll) {
				dataOne.put("id", dataOne.get("_id").toString());
				dataOne.remove("_id");
				dataRecord.add(dataOne);
			}
			data.put("sum", dataSum.size());
		}
		data.put("data", dataRecord);
		response.put("data", data);
		response.put("code", 0);
		response.put("msg", "success");
		return response;
	}
}
