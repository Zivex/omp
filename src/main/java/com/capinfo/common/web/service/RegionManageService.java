package com.capinfo.common.web.service;

import java.util.List;
import java.util.Map;

import com.capinfo.common.model.Resource;
import com.capinfo.common.model.SystemUser;
import com.capinfo.common.web.parameter.SystemUserParameter;
import com.capinfo.framework.web.service.CommonsDataOperationService;
import com.capinfo.omp.model.Composition;
import com.capinfo.omp.parameter.CompositionParameter;
import com.capinfo.omp.parameter.RegionManageParameter;
import com.capinfo.region.model.OmpRegion;

public interface RegionManageService extends CommonsDataOperationService<OmpRegion, RegionManageParameter> {

	/**
	 * 区域管理首页
	 * @param parameter
	 */
	Map<String, Object> getregionList(RegionManageParameter parameter);

	/**
	 * 添加省/直辖市
	 * @param parameter
	 */
	void addRegion(RegionManageParameter parameter);

	/**
	 * 修改区域名称
	 * @param parameter
	 */
	void updateRegion(RegionManageParameter parameter);
/**
 * 添加子区域
 * @param parameter
 */
	void addChildRegion(RegionManageParameter parameter);
/**
 * 删除
 * @param parameter
 */
	void deleteRegion(RegionManageParameter parameter);


}