package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.AreaMapper;
import com.xiaoshu.dao.SchoolMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Area;
import com.xiaoshu.entity.School;
import com.xiaoshu.entity.SchoolExample;
import com.xiaoshu.entity.SchoolExample.Criteria;
import com.xiaoshu.entity.SchoolVo;


@Service
public class SchoolService {

	@Autowired
	UserMapper userMapper;

	/*// 查询所有
	public List<User> findUser(User t) throws Exception {
		return userMapper.select(t);
	};

	// 数量
	public int countUser(User t) throws Exception {
		return userMapper.selectCount(t);
	};

	// 通过ID查询
	public User findOneUser(Integer id) throws Exception {
		return userMapper.selectByPrimaryKey(id);
	};

	

	// 删除
	public void deleteUser(Integer id) throws Exception {
		userMapper.deleteByPrimaryKey(id);
	};

	// 登录
	public User loginUser(User user) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andPasswordEqualTo(user.getPassword()).andUsernameEqualTo(user.getUsername());
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	// 通过角色判断是否存在
	public User existUserWithRoleId(Integer roleId) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andRoleidEqualTo(roleId);
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	}*/
	
	@Autowired
	private SchoolMapper schoolMapper;
	
	@Autowired
	private AreaMapper areaMapper;
	
	

	public PageInfo<SchoolVo> findUserPage(SchoolVo schoolVo, int pageNum, int pageSize, String ordername, String order) {
		PageHelper.startPage(pageNum, pageSize);
		ordername = StringUtil.isNotEmpty(ordername)?ordername:"userid";
		order = StringUtil.isNotEmpty(order)?order:"desc";
		
		List<SchoolVo> userList = schoolMapper.findPage(schoolVo);
		PageInfo<SchoolVo> pageInfo = new PageInfo<SchoolVo>(userList);
		return pageInfo;
	}

	public List<Area> findAllArea() {
		return areaMapper.selectAll();
	}

	// 新增
		public void addUser(School t) throws Exception {
			schoolMapper.insert(t);
		};

		// 修改
		public void updateUser(School t) throws Exception {
			schoolMapper.updateByPrimaryKeySelective(t);
		};


		// 通过用户名判断是否存在，（新增时不能重名）
		public School existUserWithUserName(String userName) throws Exception {
			SchoolExample example = new SchoolExample();
			Criteria criteria = example.createCriteria();
			criteria.andSchoolnameEqualTo(userName);
			List<School> userList = schoolMapper.selectByExample(example);
			return userList.isEmpty()?null:userList.get(0);
		}

		public List<SchoolVo> findLog(SchoolVo schoolVo) {
			return schoolMapper.findPage(schoolVo);
		};

}
