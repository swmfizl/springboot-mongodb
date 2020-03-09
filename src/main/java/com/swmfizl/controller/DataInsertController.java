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
public class DataInsertController {

	@Autowired
	DataRecordService dataRecordService;

	@RequestMapping(value = "/dataInsert", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public Map<String, Object> dataInsert(@RequestBody Map<String, Object> request) {
		// response响应
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> dataRecord = new ArrayList<Map<String, Object>>();
		List<Object> error = new ArrayList<Object>();
		
		String collection = request.get("collection").toString();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> datas = (List<Map<String, Object>>) request.get("data");
		
		// 写入记录
		for (int i = 0; i < datas.size(); i++) {
			Map<String, Object> dataOne = datas.get(i);
			Map<String, Object> newDataRecord = dataRecordService.insertOne(dataOne, collection);
			if (newDataRecord != null) {
				// 写入记录成功
				newDataRecord.put("id", newDataRecord.get("_id").toString());
				newDataRecord.remove("_id");
				dataRecord.add(newDataRecord);
			} else {
				// 写入记录失败
				error.add(dataOne);
			}
		}
		response.put("code", 0);
		response.put("msg", "success");

		data.put("data", dataRecord);
		if (error.size() > 0) {
			data.put("erroe", error);
		}
		response.put("data", data);
		return response;
	}
}
