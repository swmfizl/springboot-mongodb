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
import com.mongodb.client.result.DeleteResult;

@Controller
@ResponseBody
@CrossOrigin(value = "*", maxAge = 3600)
public class DataDeleteController {

	@Autowired
	DataRecordService dataRecordService;

	@RequestMapping(value = "/dataDelete", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
	public Map<String, Object> dataDelete(@RequestBody Map<String, Object> request) {
		// response响应
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<String> dataRecord = new ArrayList<String>();
		List<String> error = new ArrayList<String>();
		// 请求参数
		String collection = request.get("collection").toString();
		@SuppressWarnings("unchecked")
		List<String> ids = (List<String>) request.get("id");
		// 删除记录
		for (int i = 0; i < ids.size(); i++) {
			String id = ids.get(i);
			DeleteResult deleteResult = dataRecordService.deleteById(id, collection);
			if (deleteResult.getDeletedCount() == 1) {
				dataRecord.add(id);
			} else {
				// 删除记录失败
				error.add("删除记录失败: " + id);
			}
		}
		response.put("code", 0);
		response.put("msg", "success");

		data.put("id", dataRecord);
		if (error.size() > 0) {
			data.put("erroe", error);
		}
		response.put("data", data);
		return response;
	}
}
