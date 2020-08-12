package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.dao.AreaMapper;
import com.xiaoshu.entity.Area;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.School;
import com.xiaoshu.entity.SchoolVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.SchoolService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("school")
public class SchoolController extends LogController{
	static Logger logger = Logger.getLogger(SchoolController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private SchoolService schoolService;
	
	@RequestMapping("schoolIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Role> roleList = roleService.findRole(new Role());
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		List<Area> clist = schoolService.findAllArea();
		request.setAttribute("clist", clist);
		request.setAttribute("operationList", operationList);
		request.setAttribute("roleList", roleList);
		return "school";
	}
	
	
	@RequestMapping(value="schoolList",method=RequestMethod.POST)
	public void userList(SchoolVo schoolVo,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
		
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<SchoolVo> userList= schoolService.findUserPage(schoolVo,pageNum,pageSize,ordername,order);
		
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
	
	
	// 新增或修改reserveSchool
	@RequestMapping("reserveSchool")
	public void reserveUser(HttpServletRequest request,School school,HttpServletResponse response){
		Integer id = school.getId();
		
		JSONObject result=new JSONObject();
		try {
			if (id != null) {   // userId不为空 说明是修改
				School userName = schoolService.existUserWithUserName(school.getSchoolname());
				if(userName == null){
					school.setId(id);
					schoolService.updateUser(school);
					result.put("success", true);
				}else{
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
				
			}else {   // 添加
				if (school.getPhone().length()!=11) {
					result.put("success", false);
					result.put("errorMsg", "手机号必须为十一位数字");
				} else {
					result.put("success", true);
					
				if(schoolService.existUserWithUserName(school.getSchoolname())==null){  // 没有重复可以添加
					school.setCreatetime(new Date());
					schoolService.addUser(school);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
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
	
	//导出
	@RequestMapping("export")
	public void backup(HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		try {
			String time = TimeUtil.formatTime(new Date(), "yyyyMMddHHmmss");
		    String excelName = "手动备份"+time;
			SchoolVo schoolVo = new SchoolVo();
			List<SchoolVo> list = schoolService.findLog(schoolVo);
			String[] handers = {"编号","分校名称","所在城市","联系方式","详细地址","分校状态","创建时间"};
			// 1导入硬盘
			ExportExcelToDisk(request,handers,list,excelName);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("", "对不起，备份失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	
	// 导出到硬盘
	@SuppressWarnings("resource")
	private void ExportExcelToDisk(HttpServletRequest request,
			String[] handers, List<SchoolVo> list, String excleName) throws Exception {
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook();//创建工作簿
			HSSFSheet sheet = wb.createSheet("操作记录备份");//第一个sheet
			HSSFRow rowFirst = sheet.createRow(0);//第一个sheet第一行为标题
			rowFirst.setHeight((short) 500);
			for (int i = 0; i < handers.length; i++) {
				sheet.setColumnWidth((short) i, (short) 4000);// 设置列宽
			}
			//写标题了
			for (int i = 0; i < handers.length; i++) {
			    //获取第一行的每一个单元格
			    HSSFCell cell = rowFirst.createCell(i);
			    //往单元格里面写入值
			    cell.setCellValue(handers[i]);
			}
			for (int i = 0;i < list.size(); i++) {
			    //获取list里面存在是数据集对象
				SchoolVo schoolVo = list.get(i);
			    //创建数据行
			    HSSFRow row = sheet.createRow(i+1);
			    //设置对应单元格的值
			    row.setHeight((short)400);   // 设置每行的高度
			    //""编号","分校名称","所在城市","联系方式","详细地址","分校状态","创建时间
			    row.createCell(0).setCellValue(i+1);
			    row.createCell(1).setCellValue(schoolVo.getSchoolname());
			    row.createCell(2).setCellValue(schoolVo.getCname());
			    row.createCell(3).setCellValue(schoolVo.getPhone());
			    row.createCell(4).setCellValue(schoolVo.getAddress());
			    row.createCell(5).setCellValue(schoolVo.getStatus());
			    row.createCell(6).setCellValue(TimeUtil.formatTime(schoolVo.getCreatetime(), "yyyy-MM-dd"));
			}
			//写出文件（path为文件路径含文件名）
				OutputStream os;
				File file = new File("d:/export/"+"学校管理"+".xls");
				
				if (!file.exists()){//若此目录不存在，则创建之  
					file.createNewFile();  
					logger.debug("创建文件夹路径为："+ file.getPath());  
	            } 
				os = new FileOutputStream(file);
				wb.write(os);
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
	}
	
	
	@RequestMapping("deleteUser")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				userService.deleteUser(Integer.parseInt(id));
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
	
	//导入
	@RequestMapping("importSchool")
	public void importSchool(MultipartFile excelFile,HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			//获取前台传递excel
			HSSFWorkbook wb = new HSSFWorkbook(excelFile.getInputStream());
			//获取sheet页
			HSSFSheet sheetAt = wb.getSheetAt(0);
			//获取最后一行的行数
			int rowNum = sheetAt.getLastRowNum();
			//循环最后一行行数获取对象
			for (int i = 1; i <= rowNum; i++) {
				//获取单元格
				HSSFRow row = sheetAt.getRow(i);
				//获取参数""编号","分校名称","所在城市","联系方式","详细地址","分校状态","创建时间
				String schoolname = row.getCell(0).toString();
				String cname = row.getCell(1).toString();
				String phone = row.getCell(2).toString();
				String address = row.getCell(3).toString();
				String status = row.getCell(4).toString();
				Date createtime = row.getCell(5).getDateCellValue();
				//根据部门名称查询部门id
				int areaid = findRidByName(cname);
				//封装参数
				School school = new School();
				school.setSchoolname(schoolname);
				school.setAreaid(areaid);
				school.setPhone(phone);
				school.setAddress(address);
				school.setStatus(status);
				school.setCreatetime(createtime);
				//保存到数据库中
				schoolService.addUser(school);
				
			}
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	@Autowired
	private AreaMapper areaMapper;
	public int findRidByName(String cname) {
		Area area = new Area();
		area.setAreaname(cname);
		Area one = areaMapper.selectOne(area);
		return one.getId();
		
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
