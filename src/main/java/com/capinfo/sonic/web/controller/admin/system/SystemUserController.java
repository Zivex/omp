package com.capinfo.sonic.web.controller.admin.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.capinfo.common.model.Role;
import com.capinfo.common.model.SystemUser;
import com.capinfo.common.security.authentication.AuthenticationSuccessHandlerImpl;
import com.capinfo.common.security.cache.SecurityCache;
import com.capinfo.common.web.parameter.SystemUserParameter;
import com.capinfo.common.web.service.RoleService;
import com.capinfo.common.web.service.SystemUserService;
import com.capinfo.framework.model.BaseEntity;
import com.capinfo.framework.model.Messages;
import com.capinfo.framework.model.Messages.MessageType;
import com.capinfo.framework.service.GeneralService;
import com.capinfo.framework.web.view.model.DataListViewModel;
import com.capinfo.omp.model.Composition;
import com.capinfo.omp.parameter.CompositionParameter;
import com.capinfo.region.model.OmpRegion;

@Controller
@RequestMapping("/admin/sys/user")
@SessionAttributes("eccomm_admin")
public class SystemUserController extends AuthenticationSuccessHandlerImpl {

	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SecurityCache securityCache;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private GeneralService generalService;

	/**
	 * 用户管理首页
	 * 
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/initial.shtml")
	public ModelAndView index(SystemUserParameter parameter,
			@ModelAttribute("eccomm_admin") SystemUser user) {
		ModelAndView mv = new ModelAndView("/admin/sys/user/initial");
		List<SystemUser> entities = new ArrayList<SystemUser>();
		if (user.getLeave() == 1
				&& ("b".equals(user.getAccount_type()) || "m".equals(user
						.getAccount_type()))) {
			String account_type = user.getAccount_type();
			parameter.setEncode(account_type);
			parameter.getEntity().setEncoding(user.getEncoding());
		}
		int totalCount = this.systemUserService.getTotalCount(parameter);
		if (totalCount > 0) {
			entities = systemUserService.getList(parameter, totalCount,
					parameter.getCurrentPieceNum());
		}
		mv.addObject("roleList", roleService.getAllRoles(user));
		mv.addAllObjects(new DataListViewModel<SystemUser>("dataList",
				entities, totalCount, this.systemUserService, parameter)
				.buildViewModel());
		return mv;
	}

	/**
	 * 用户管理列表
	 * 
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/list.shtml")
	public ModelAndView userList(SystemUserParameter parameter,
			@ModelAttribute("eccomm_admin") SystemUser user) {
		ModelAndView mv = new ModelAndView("/admin/sys/user/list");
		List<SystemUser> entities = new ArrayList<SystemUser>();
		if (user.getLeave() == 1
				&& ("b".equals(user.getAccount_type()) || "m".equals(user
						.getAccount_type()))) {
			String account_type = user.getAccount_type();
			parameter.setEncode(account_type);
			parameter.getEntity().setEncoding(user.getEncoding());
		}
		int totalCount = this.systemUserService.getTotalCount(parameter);
		if (totalCount > 0) {
			entities = systemUserService.getList(parameter, totalCount,
					parameter.getCurrentPieceNum());
		}
		mv.addObject("roleList", roleService.getAllRoles(user));
		mv.addAllObjects(new DataListViewModel<SystemUser>("dataList",
				entities, totalCount, this.systemUserService, parameter)
				.buildViewModel());
		return mv;
	}

	/**
	 * 用户详情
	 * 
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/detail.shtml")
	public ModelAndView detail(SystemUserParameter parameter) {
		ModelAndView mv = new ModelAndView("/admin/sys/user/detail");
		if (parameter.getEntity().getId() != null) {
			SystemUser user = systemUserService.getUserById(parameter
					.getEntity().getId());
			parameter.setEntity(user);
		}
		mv.addObject("user", parameter.getEntity());
		return mv;
	}

	/**
	 * 新建用户信息页面
	 * 
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "/form.shtml", method = RequestMethod.GET)
	public ModelAndView saveUserInto(SystemUserParameter parameter,
			@ModelAttribute("eccomm_admin") SystemUser user) {
		ModelAndView mv = new ModelAndView("/admin/sys/user/form");
		List<Role> allRoles = roleService.getAllRoles(user);
		List<Role>  nList = new ArrayList<Role>();
		if(user.getLeave()!=0){
			for (Role role : allRoles) {
				if(role.getId()!=1 && role.getId()!=2){
					nList.add(role);
				}
			}
		}
		mv.addObject("command", parameter);
		mv.addObject("roleList", nList);
		return mv;
	}

	/**
	 * 保存用户
	 * 
	 * @param request
	 * @param response
	 * @param parameter
	 *            使用<code>@ModelAttribute</code>才能使Valid生效
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/save.shtml", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject saveUser(
			@Valid @ModelAttribute("command") SystemUserParameter parameter,
			BindingResult result,
			@ModelAttribute("eccomm_admin") SystemUser user) {
		Messages messages = new Messages();
		SystemUser entity = parameter.getEntity();
		// 基本验证
		if (result.hasErrors()) {
			messages.setSuccess(false);
			messages.setMessage("操作失败,验证未通过！");
		} else {
			// 用户名验证
			boolean exist = systemUserService.isExistUser(parameter.getEntity()
					.getLogonName());
			if (exist) {
				messages.setSuccess(false);
				messages.setMessage("登录名已存在,添加用户失败！");
			} else {
				String pass = parameter.getEntity().getPassword();
				// parameter.getEntity().setPassword(passwordEncoder.encode(pass));
				parameter.getEntity().setPassword(pass);
				// 判断用户类型
				String account_type = parameter.getEntity().getAccount_type();
				// 政府
				if ("g".equals(account_type)) {
					// 判断用户等级
					String community = parameter.getCommunity();
					int leave = 1;
					// 区域
					String r = "";
					if (community != null && !"".equals(community)) {
						leave = 5;
						r = community;

					} else {
						String street = parameter.getStreet();
						if (street != null && !"".equals(street)) {
							leave = 4;
							r = street;
						} else {
							String county = parameter.getCounty();
							if (county != null && !"".equals(county)) {
								leave = 3;
								r = county;
							} else {
								String shi = parameter.getShi();
								leave = 2;
								r = shi;
							}

						}
					}
					// 账户类型
					// account_type = account_type+standardNo;
					// parameter.getEntity().setAccount_type(account_type);
					// 账户等级
					OmpRegion region = systemUserService.getbiRegoinid(Long
							.parseLong(r));
					// String standardNo = region.getStandardNo();
					entity.setLeave(leave);
					Long id = region.getId();
					Long parentid = region.getParentid();
					// 设置账户从属关系
					entity.setRid(id);
					entity.setParentid(parentid);
					entity.setRegionName(region.getName());
				} else {
					// 判断用户等级
					Integer siji = parameter.getSiji();
					int leave = 1;
					// 区域
				//	Integer r = 0;
					if (siji != null && siji != 0) {
						leave = 5;
						entity.setSiji(siji);

					} else {
						Integer sjji = parameter.getSjji();
						if (sjji != null && sjji != 0) {
							leave = 4;
							entity.setSjji(sjji);
						} else {
							Integer erji = parameter.getErji();
							if (erji != null && erji != 0) {
								leave = 3;
								entity.setErji(erji);
							} else {
								Integer yiji = parameter.getYiji();
								if (yiji != null && yiji != 0) {
									leave = 2;
									entity.setYiji(yiji);
								}
							}

						}
					}
					entity.setLeave(leave);
				}

				// 设置用户所属机构
				entity.setType_id(user.getType_id());
				boolean suc = systemUserService.saveUser(entity);
				String info = suc == true ? "添加用户成功" : "添加用户失败";
				messages.setSuccess(suc);
				messages.setMessage(info);
			}

		}
		JSONObject json = JSONObject.fromObject(messages);
		return json;
	}

	/**
	 * 编辑用户页面
	 * 
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "/edit.shtml", method = RequestMethod.GET)
	public ModelAndView updateUserInto(SystemUserParameter parameter) {
		ModelAndView mv = new ModelAndView("/admin/sys/user/edit");
		if (parameter.getEntity().getId() != null) {
			SystemUser user = systemUserService.getUserById(parameter
					.getEntity().getId());
			parameter.setEntity(user);
		}
		mv.addObject("command", parameter);
		mv.addObject("roleList", roleService.getAllRoles(parameter.getEntity()));

		return mv;
	}

	/**
	 * 修改用户信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param parameter
	 *            使用<code>@ModelAttribute</code>才能使Valid生效
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/update.shtml", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateUser(Model model,
			@Valid @ModelAttribute("command") SystemUserParameter parameter,
			BindingResult result) {
		Messages messages = new Messages();
		// 基本验证
		if (parameter.getEntity().getId() == null) {
			messages.setSuccess(false);
			messages.setMessage("操作失败,验证未通过！");
		} else {
			boolean suc = systemUserService.updateUserInfo(parameter);
			String info = suc == true ? "修改用户信息成功" : "修改用户信息失败";
			messages.setSuccess(suc);
			messages.setMessage(info);
			if (suc) {
				refreshUserCache(parameter.getEntity().getId());
			}
		}
		JSONObject json = JSONObject.fromObject(messages);
		return json;
	}

	/**
	 * 删除用户
	 * 
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "/delete.shtml", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteUser(SystemUserParameter parameter) {
		Messages messages = new Messages();
		if (null != parameter.getEntity().getId()) {
			Long sysuserid = parameter.getEntity().getId();
			boolean suc = systemUserService.deleteUser(parameter);
			String info = suc == true ? "删除用户信息成功" : "删除用户信息失败";
			messages.setSuccess(suc);
			messages.setMessage(info);
			//
			if (suc) {
				removeUserFromCache(parameter.getEntity().getId());
			}
		} else {
			String info = "参数不全，操作失败！";
			messages.setSuccess(false);
			messages.setMessage(info);
		}
		JSONObject json = JSONObject.fromObject(messages);
		return json;
	}

	/**
	 * 重置密码
	 * 
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "/reset_pass.shtml", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject resetPass(SystemUserParameter parameter) {
		Messages messages = new Messages();
		if (null != parameter.getEntity().getId()) {
			boolean suc = systemUserService.resetPassword(parameter);
			String info = suc == true ? "重置密码成功，新密码：123456" : "重置密码失败";
			messages.setSuccess(suc);
			messages.setMessage(info);
		} else {
			String info = "参数不全，操作失败！";
			messages.setSuccess(false);
			messages.setMessage(info);
		}
		JSONObject json = JSONObject.fromObject(messages);
		return json;
	}

	/**
	 * 更改密码
	 * 
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "/change_password_form.shtml", method = RequestMethod.GET)
	public ModelAndView changePasswordForm(SystemUserParameter parameter,
			@ModelAttribute("eccomm_admin") SystemUser user) {
		ModelAndView mv = new ModelAndView("/admin/sys/user/password");
		parameter.setEntity(user);
		if (null == user.getId()) {
			String info = "参数不全，操作失败！";
			MessageType level = Messages.MessageType.warning;
			Messages messages = new Messages(false, info, level);
			mv.addObject("messages", messages);
			mv.addObject("posted", true);
		}
		mv.addObject("posted", false);
		mv.addObject("command", parameter);
		return mv;
	}

	@RequestMapping(value = "/change_password.shtml", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject changePassword(SystemUserParameter parameter,
			@ModelAttribute("eccomm_admin") SystemUser user) {
		Messages messages = new Messages();
		if (null != parameter.getEntity().getId()
				&& StringUtils.isNotBlank(parameter.getEntity().getPassword())
				&& StringUtils.isNotBlank(parameter.getOldPassword())) {
			boolean isUserPassword = systemUserService
					.isUserPassword(parameter);
			if (isUserPassword) {
				boolean suc = systemUserService.changePassword(parameter);
				String info = suc == true ? "修改密码成功！" : "修改密码失败！";
				messages.setSuccess(suc);
				messages.setMessage(info);
			} else {
				messages.setSuccess(false);
				messages.setMessage("原密码错误，请确认！");
			}
		} else {
			String info = "参数不全，操作失败！";
			messages.setSuccess(false);
			messages.setMessage(info);
		}
		JSONObject json = JSONObject.fromObject(messages);
		return json;
	}

	/**
	 * 更改用户信息
	 * 
	 * @param parameter
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/change_userinfo_form.shtml", method = RequestMethod.GET)
	public ModelAndView changeFingerprintForm(SystemUserParameter parameter,
			@ModelAttribute("eccomm_admin") SystemUser user) {
		ModelAndView mv = new ModelAndView("/admin/sys/user/changeUserinfo");
		SystemUser sys = generalService.getObjectById(SystemUser.class,
				user.getId());
		parameter.setEntity(sys);
		if (null == user.getId()) {
			String info = "参数不全，操作失败！";
			MessageType level = Messages.MessageType.warning;
			Messages messages = new Messages(false, info, level);
			mv.addObject("messages", messages);
			mv.addObject("posted", true);
		}
		mv.addObject("posted", false);
		mv.addObject("command", parameter);
		return mv;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Set.class, "entity.roles",
				new CustomCollectionEditor(Set.class) {
					@Override
					protected Object convertElement(Object element) {
						Long id = null;
						if (element instanceof String
								&& !((String) element).equals("")) {
							try {
								id = Long.parseLong((String) element);
							} catch (NumberFormatException e) {
								System.out.println("Element was "
										+ ((String) element));
								id = null;
							}
						} else if (element instanceof Long) {
							// From the database 'element' will be a Long
							id = (Long) element;
						}
						return id != null ? new Role(id) : null;
					}
				});
	}

	private void refreshUserCache(Long id) {
		SystemUser user = systemUserService.getUserById(id);
		if (null != user) {
			securityCache.modifyUserInCache(user, user.getLogonName());
		}

	}

	private void removeUserFromCache(Long id) {
		SystemUser user = systemUserService.getUserById(id);
		if (null != user) {
			securityCache.removeUserFromCache(user.getLogonName());
		}

	}

	@RequestMapping(value = "recharge.shtml", method = RequestMethod.POST)
	@ResponseBody
	public String recharge(Long money, Long id) {
		systemUserService.recharge(money, id);

		return "1";
	}

	/**
	 * 跳转到建立银行商户组织
	 * 
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "/toComposition.shtml")
	public ModelAndView toComposition(CompositionParameter parameter) {
		ModelAndView mv = new ModelAndView("/admin/sys/user/composition");
		mv.addObject("command", parameter);
		return mv;
	}

	/**
	 * 保存
	 * 
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "/saveComposition.shtml")
	public ModelAndView saveComposition(
			@ModelAttribute("eccomm_admin") SystemUser user,
			CompositionParameter parameter) {
		ModelAndView mv = new ModelAndView("/admin/sys/user/composition");
		Composition entity = parameter.getEntity();
		Composition entityC = generalService.getObjectById(Composition.class,
				entity.getId());
		if (entity.getPrient_id() != 0) {
			Composition entityP = generalService.getObjectById(
					Composition.class, entity.getPrient_id());
			if (entityP.getLevelid() != null) {
				entityC.setLevelid(entityP.getLevelid() + 1);
				entityC.setPrient_id(entityP.getId());
			}
		} else {
			entityC.setLevelid(1L);
		}
		entityC.setUpdatetime(new Date());
		generalService.saveOrUpdate(entityC);
		mv.addObject("command", parameter);
		return mv;
	}

	/**
	 * 添加机构
	 * 
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "addComposition.shtml")
	@ResponseBody
	public String addComposition(
			@ModelAttribute("eccomm_admin") SystemUser user,
			CompositionParameter parameter, String name) {
		parameter.getEntity().setName(name);
		systemUserService.addMechanism(parameter.getEntity(), user);
		return "添加成功";
	}

	/**
	 * ajax下拉追加机构
	 * 
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "selectComposition.shtml")
	@ResponseBody
	public List<Composition> selectComposition(
			@ModelAttribute("eccomm_admin") SystemUser user) {
		List<Composition> compositionList = systemUserService
				.getCompositionList(user);
		return compositionList;
	}
}
