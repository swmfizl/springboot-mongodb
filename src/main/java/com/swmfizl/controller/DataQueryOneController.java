package com.swmfizl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swmfizl.service.DataRecordService;

@Controller
@ResponseBody
@CrossOrigin(value = "*", maxAge = 3600)
public class DataQueryOneController {

	@Autowired
	DataRecordService dataRecordService;

	@RequestMapping(value = "/dataQuery", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public Map<String, Object> dataInsert(@RequestParam("collection") String collectionValue, @RequestParam("id") String idValue) {
		// response响应
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> dataRecord = new ArrayList<Map<String, Object>>();
		// 请求参数
		String collection = collectionValue;
		String id = idValue;
		// 匹配ID查询
		Map<String, Object> dataOne = dataRecordService.selectById(id, collection);
		if (dataOne != null) {
			dataOne.put("id", dataOne.get("_id").toString());
			dataOne.remove("_id");
			dataRecord.add(dataOne);
		}
		data.put("data", dataRecord);
		response.put("data", data);
		response.put("code", 0);
		response.put("msg", "success");
		return response;
	}
}
