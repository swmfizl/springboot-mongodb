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
import com.mongodb.client.result.UpdateResult;

@Controller
@ResponseBody
@CrossOrigin(value = "*", maxAge = 3600)
public class DataUpdateController {

	@Autowired
	DataRecordService dataRecordService;

	@RequestMapping(value = "/dataUpdate", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
	public Map<String, Object> dataInsert(@RequestBody Map<String, Object> request) {
		// response响应
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> dataRecord = new ArrayList<Map<String, Object>>();
		List<String> error = new ArrayList<String>();
		// 请求参数
		String collection = request.get("collection").toString();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> datas = (List<Map<String, Object>>) request.get("data");
		// 更新记录
		for (int i = 0; i < datas.size(); i++) {
			Map<String, Object> dataOne = datas.get(i);
			// 更新记录
			UpdateResult updateResult = dataRecordService.updateKeyValue(dataOne, collection);
			if (updateResult.getModifiedCount() == 1) {
				Map<String, Object> updateData = dataRecordService.selectById(dataOne.get("id").toString(), collection);
				updateData.put("id", updateData.get("_id").toString());
				updateData.remove("_id");
				dataRecord.add(updateData);
			} else {
				// 更新记录失败
				error.add("更新失败: " + dataOne.get("id").toString());
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
