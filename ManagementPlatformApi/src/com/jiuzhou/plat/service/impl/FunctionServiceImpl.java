package com.jiuzhou.plat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuzhou.plat.bean.Function;
import com.jiuzhou.plat.mapper.FunctionMapper;
import com.jiuzhou.plat.service.FunctionService;

/**
* @author xingmh
* @version 2018年9月3日 下午11:10:52
* 类说明
*/
@Service("FunctionService")
public class FunctionServiceImpl extends ServiceBase implements FunctionService {

	@Autowired
	FunctionMapper functionMapper;
	
	@Override
	public Function findByMethod(String method) {
		Function function = getCache(CACHE_FUNCTION + method, Function.class);
		if (function == null) {
			function = functionMapper.findByMethod(method);
		}
		if (function != null) {
			setCache(CACHE_FUNCTION + method, function);
		}
		return function;
	}

}
