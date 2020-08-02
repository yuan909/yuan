package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.DeviceVo;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.Type;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.DeviceService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("device")
public class DeviceController extends LogController{
	static Logger logger = Logger.getLogger(DeviceController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private DeviceService deviceService;
	
	@RequestMapping("deviceIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Role> roleList = roleService.findRole(new Role());
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		List<Type> clist = deviceService.findAllType();
		request.setAttribute("clist", clist);
		request.setAttribute("operationList", operationList);
		request.setAttribute("roleList", roleList);
		return "device";
	}
	
	
	@RequestMapping(value="deviceList",method=RequestMethod.POST)
	public void userList(DeviceVo deviceVo,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
		
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
		
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<DeviceVo> userList= deviceService.findUserPage(deviceVo,pageNum,pageSize,ordername,order);
			
		
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",userList.getTotal() );
			jsonObj.put("rows", userList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveDevice")
	public void reserveUser(HttpServletRequest request,Device device,HttpServletResponse response){
		Integer deviceid = device.getDeviceid();
		JSONObject result=new JSONObject();
		try {
			if (deviceid != null) {   // userId不为空 说明是修改
				Device userName = deviceService.existUserWithUserName(device.getDevicename());
				if(userName == null){
					device.setDeviceid(deviceid);
					deviceService.updateUser(device);
					result.put("success", true);
				}else{
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
				
			}else {   // 添加.
				
				if(deviceService.existUserWithUserName(device.getDevicename())==null){  // 没有重复可以添加
					deviceService.addUser(device);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	@RequestMapping("deleteDevice")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String deviceid : ids) {
				deviceService.deleteUser(Integer.parseInt(deviceid));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	//导出
	@RequestMapping("exportDevice")
	public void exportDevice(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			//创建excel文档
			HSSFWorkbook wb = new HSSFWorkbook();
			//创建sheet页
			HSSFSheet sheet = wb.createSheet();
			//声明表头信息编号
			/*设备名称
			设备类型名称
			内存
			机身颜色
			价格
			设备状态
			创建日期*/
			String[] heder={"编号","设备名称","设备类型名称","内存","机身颜色","价格","设备状态","创建日期"}; 
			//创建行
			HSSFRow row = sheet.createRow(0);
			for (int i = 0; i < heder.length; i++) {
				//创建单元格
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(heder[i]);
			}
			//从数据库中讲所有数据读出
			List<DeviceVo> list = deviceService.findPage(new DeviceVo());
			//将数据依次循环遍历放入文档中
			for (int i = 0; i < list.size(); i++) {
				//创建数据行
				HSSFRow row2 = sheet.createRow(i+1);
				//获取list里面存在是数据集对象
				DeviceVo deviceVo = list.get(i);
				/*设备名称
				设备类型名称
				内存
				机身颜色
				价格
				设备状态
				创建日期*/
				row2.createCell(0).setCellValue(deviceVo.getDeviceid());
				row2.createCell(1).setCellValue(deviceVo.getDevicename());
				row2.createCell(2).setCellValue(deviceVo.getCname());
				row2.createCell(3).setCellValue(deviceVo.getDeviceram());
				row2.createCell(4).setCellValue(deviceVo.getColor());
				row2.createCell(5).setCellValue(deviceVo.getPrice());
				row2.createCell(6).setCellValue(deviceVo.getStatus());
				row2.createCell(7).setCellValue(TimeUtil.formatTime(deviceVo.getCreatetime(),"yyyy-MM-dd"));
				
			}
			//导出
			OutputStream os;
			File file = new File("d:/export/手机管理.xls");
			//设置导出路径
			if (!file.exists()) {//若目录不存在，创建
				file.createNewFile();
				logger.debug("创建文件夹路径为:"+file.getPath());
			}
			os = new FileOutputStream(file);
			wb.write(os);
			os.close();
			
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出信息错误",e);
			result.put("errorMsg", "对不起，导出失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("editPassword")
	public void editPassword(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("currentUser");
		if(currentUser.getPassword().equals(oldpassword)){
			User user = new User();
			user.setUserid(currentUser.getUserid());
			user.setPassword(newpassword);
			try {
				userService.updateUser(user);
				currentUser.setPassword(newpassword);
				session.removeAttribute("currentUser"); 
				session.setAttribute("currentUser", currentUser);
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("修改密码错误",e);
				result.put("errorMsg", "对不起，修改密码失败");
			}
		}else{
			logger.error(currentUser.getUsername()+"修改密码时原密码输入错误！");
			result.put("errorMsg", "对不起，原密码输入错误！");
		}
		WriterUtil.write(response, result.toString());
	}
}
