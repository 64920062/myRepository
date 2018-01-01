/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.db;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeesite.common.callback.MethodCallback;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.tests.BaseInitDataTests;
import com.jeesite.modules.sys.dao.RoleMenuDao;
import com.jeesite.modules.sys.entity.Area;
import com.jeesite.modules.sys.entity.Company;
import com.jeesite.modules.sys.entity.Config;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.entity.DictType;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.Log;
import com.jeesite.modules.sys.entity.Menu;
import com.jeesite.modules.sys.entity.Module;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.entity.Post;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.RoleMenu;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.AreaService;
import com.jeesite.modules.sys.service.CompanyService;
import com.jeesite.modules.sys.service.ConfigService;
import com.jeesite.modules.sys.service.DictDataService;
import com.jeesite.modules.sys.service.DictTypeService;
import com.jeesite.modules.sys.service.EmpUserService;
import com.jeesite.modules.sys.service.MenuService;
import com.jeesite.modules.sys.service.ModuleService;
import com.jeesite.modules.sys.service.OfficeService;
import com.jeesite.modules.sys.service.PostService;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.service.UserService;

/**
 * 初始化核心表数据
 * @author ThinkGem
 * @version 2017-10-22
 */
//@Commit
public class InitCoreData extends BaseInitDataTests {
	
	@Override
	public void begin() {
		super.begin();
		dataFile = new File(InitCoreData.class.getResource(
				InitCoreData.class.getSimpleName() + ".xlsx").getFile());
	}
	
	public void initLog() throws Exception{
		clearTable(new Log());
	}
	
	@Autowired
	private ConfigService configService;
	public void initConfig() throws Exception{
		try{
			clearTable(new Config());
			initExcelData(Config.class, new MethodCallback() {
				@Override
				public Object execute(Object... params) {
					String action = (String)params[0];
					if("save".equals(action)){
						Config entity = (Config)params[1];
						entity.setId(IdGen.nextId());
						entity.setIsNewRecord(true);
						configService.save(entity);
						return null;
					}
					return null;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Autowired
	private ModuleService moduleService;
	public void initModule() throws Exception{
		try{
			clearTable(new Module());
			initExcelData(Module.class, new MethodCallback() {
				@Override
				public Object execute(Object... params) {
					String action = (String)params[0];
					if("save".equals(action)){
						Module entity = (Module)params[1];
						entity.setIsNewRecord(true);
						moduleService.save(entity);
						return null;
					}
					return null;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Autowired
	private DictTypeService dictTypeService;
	@Autowired
	private DictDataService dictDataService;
	public void initDict() throws Exception{
		try{
			clearTable(new DictType());
			initExcelData(DictType.class, new MethodCallback() {
				@Override
				public Object execute(Object... params) {
					String action = (String)params[0];
					if("save".equals(action)){
						DictType entity = (DictType)params[1];
						entity.setId(IdGen.nextId());
						entity.setIsNewRecord(true);
						dictTypeService.save(entity);
						return null;
					}
					return null;
				}
			});

			clearTable(new DictData());
			initExcelData(DictData.class, new MethodCallback() {
				@Override
				public Object execute(Object... params) {
					String action = (String)params[0];
					if("save".equals(action)){
						DictData entity = (DictData)params[1];
						entity.setIsNewRecord(true);
						dictDataService.save(entity);
						return null;
					}
					return null;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Autowired
	private RoleService roleService;
	public void initRole() throws Exception{
		try{
			clearTable(new Role());
			initExcelData(Role.class, new MethodCallback() {
				@Override
				public Object execute(Object... params) {
					String action = (String)params[0];
					if("save".equals(action)){
						Role entity = (Role)params[1];
						entity.setIsNewRecord(true);
						roleService.save(entity);
						return null;
					}
					return null;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleMenuDao roleMenuDao;
	public void initMenu() throws Exception{
		try{
			clearTable(new Menu());
			clearTable(new RoleMenu());
			initExcelData(Menu.class, new MethodCallback() {
				@Override
				public Object execute(Object... params) {
					String action = (String)params[0];
					if("save".equals(action)){
						Menu entity = (Menu)params[1];
						entity.setIsNewRecord(true);
						menuService.save(entity);
						RoleMenu rm = new RoleMenu();
						rm.setMenuCode(entity.getMenuCode());
						rm.setRoleCode(Role.CORP_ADMIN_ROLE_CODE);
						roleMenuDao.insert(rm);
						return null;
					}
					return null;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Autowired
	private UserService userService;
	public void initUser() throws Exception{
		try{
			clearTable(new User());
			initExcelData(User.class, new MethodCallback() {
				@Override
				public Object execute(Object... params) {
					String action = (String)params[0];
					if("save".equals(action)){
						User entity = (User)params[1];
						entity.setIsNewRecord(true);
						entity.setPassword(UserService.encryptPassword(entity.getPassword()));
						userService.save(entity);
						return null;
					}
					return null;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Autowired
	private AreaService areaService;
	public void initArea() throws Exception{
		try{
			clearTable(new Area());
			initExcelData(Area.class, new MethodCallback() {
				@Override
				public Object execute(Object... params) {
					String action = (String)params[0];
					if("save".equals(action)){
						Area entity = (Area)params[1];
						entity.setIsNewRecord(true);
						areaService.save(entity);
						return null;
					}
					return null;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Autowired
	private OfficeService officeService;
	public void initOffice() throws Exception{
		try{
			clearTable(new Office());
			initExcelData(Office.class, new MethodCallback() {
				@Override
				public Object execute(Object... params) {
					String action = (String)params[0];
					if("save".equals(action)){
						Office entity = (Office)params[1];
						entity.setIsNewRecord(true);
						officeService.save(entity);
						return null;
					}
					return null;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Autowired
	private CompanyService companyService;
	public void initCompany() throws Exception{
		try{
			clearTable(new Company());
			initExcelData(Company.class, new MethodCallback() {
				@Override
				public Object execute(Object... params) {
					String action = (String)params[0];
					if("save".equals(action)){
						Company entity = (Company)params[1];
						entity.setIsNewRecord(true);
						companyService.save(entity);
						return null;
					}
					return null;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Autowired
	private PostService postService;
	public void initPost() throws Exception{
		try{
			clearTable(new Post());
			initExcelData(Post.class, new MethodCallback() {
				@Override
				public Object execute(Object... params) {
					String action = (String)params[0];
					if("save".equals(action)){
						Post entity = (Post)params[1];
						entity.setIsNewRecord(true);
						postService.save(entity);
						return null;
					}
					return null;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Autowired
	private EmpUserService empUserService;
	public void initEmpUser() throws Exception{
		try{
			clearTable(new Employee());
			initExcelData(EmpUser.class, new MethodCallback() {
				@Override
				public Object execute(Object... params) {
					String action = (String)params[0];
					if("set".equals(action)){
						EmpUser entity = (EmpUser)params[1];
						String header = (String)params[2];
						String val = (String)params[3];
						if ("employee.employeePosts".equals(header)){
							entity.getEmployee().setEmployeePosts(new String[]{val});
							return true;
						}
					}
					else if("save".equals(action)){
						EmpUser entity = (EmpUser)params[1];
						entity.setIsNewRecord(true);
						entity.setPassword(UserService.encryptPassword(entity.getPassword()));
						empUserService.save(entity);
						userService.saveAuth(entity);
						return null;
					}
					return null;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}
