package com.capinfo.order.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.capinfo.common.model.SystemUser;
import com.capinfo.omp.model.Omp_old_order;
import com.capinfo.omp.parameter.OrderParameter;
import com.capinfo.omp.service.OldService;
import com.capinfo.omp.service.OrderService;
import com.capinfo.omp.ws.client.ClientGetDataService;
import com.capinfo.omp.ws.model.ImKey;

@Controller
@RequestMapping("/order")
@SessionAttributes("eccomm_admin")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired 
	private OldService oldService;

	@RequestMapping("/orderManage/initial.shtml")
	public ModelAndView initial(OrderParameter parameter,@ModelAttribute("eccomm_admin") SystemUser user) {
		ModelAndView mv = new ModelAndView("/omp/order/initial");

		getList(mv, parameter,user);
		return mv;
	}

	@RequestMapping("/orderManage/list.shtml")
	@ResponseBody
	public ModelAndView listt(OrderParameter parameter,@ModelAttribute("eccomm_admin") SystemUser user) {
		ModelAndView mv = new ModelAndView("/omp/order/list");
		
		getList(mv, parameter,user);
		return mv;
	}

	public void getList(ModelAndView mv,OrderParameter parameter ,SystemUser user) {
		
		
		
		//int count = orderService.getOrderCount(parameter,user);
		
		Map<String, Object> map = orderService.getOrderList( parameter,user);
		
		mv.addObject("dataList", map.get("orderList"));
		mv.addObject("count", map.get("count"));
		mv.addObject("command", parameter);
	}

	/**
	 * 发送指令
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/orderManage/sendOrder.shtml")
	@ResponseBody
	public String sendOrder(String ids,@ModelAttribute("eccomm_admin") SystemUser user) throws Exception {
		if(!orderService.queryCount(ids)){
			return "您发送的指令条数已超出，请充值后再行发送，充值电话：褚-84933228";
		}
		int i = 0;
		ClientGetDataService c = new ClientGetDataService();
		if (!StringUtils.isEmpty(ids)) {
			String[] split = ids.split(",");
			for (String id : split) {
				//语音发送次数-1
				String orderSata = orderService.numRest(id);
				String json = orderService.sendOrder(id);
				ImKey imKey = c.sendOrder(json);
				if ("1".equals(imKey.getStatusCode())) {
//				ImKey imKey = new ImKey();
//					if (true) {
					orderService.resultOrder(imKey, id,user);
					i++;
				}else{
					//失败回滚
					orderService.rollback(id,user,orderSata);
				}
				if (i != 0) {
					// 全部成功后修改指令状态
						orderService.toupdete(id,imKey);
						
						orderService.upMessg(imKey, id);
					
				}
			}
			if (i !=0) {
				return "发送成功";
			}
			return "发送失败";

		}

		return null;
	}

	@RequestMapping("/orderManage/delect.shtml")
	public String appointmentsend(String uid) {
			orderService.delect(uid);
			return "发送成功";
		
	}

	@RequestMapping("/orderManage/batchSendInstructions.shtml")
	public void batchSendInstructions() throws Exception {
		System.out.println("batchSendInstructions:定时器自动执行发送指令程序，间隔时间5分钟");
		if (orderService.getcount()) {
			String id = orderService.checkDeBatchSendInstructions();
			sendOrder(id, null);
		}
	}

	@RequestMapping("/orderManage/ordercommunity.shtml")
	public ModelAndView ordercommunity() {
		ModelAndView mv = new ModelAndView("/omp/order/listNew");
		return mv;
	}

	@RequestMapping("/orderManage/odercom.shtml")
	public ModelAndView orderCom(String ids) throws Exception {
		boolean result = true;
		ModelAndView mv = new ModelAndView("/omp/order/initial");
		if (!StringUtils.isEmpty(ids)) {
			String[] split = ids.split(",");
			for (String id : split) {
				System.out.println(id);
				result = orderService.upsendOrder(id);
				System.out.println(result);
			}
		}
		return mv;
	}

	@RequestMapping("/orderManage/oInfo.shtml")
	@ResponseBody
	public ModelAndView oInfo(String id,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/omp/old/oldInfoNew");
 
    	List<Map<String,Object>> oList = orderService.getOList(id);
    	if(oList!=null&&oList.size()>0){
    		Map<String, Object> map = oList.get(0);
            mv.addObject("detaMap",map);
    	}
		return mv;
	}
	
	
	
	
	
	
	
}
