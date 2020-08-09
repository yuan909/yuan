package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.SchoolMapper;
import com.xiaoshu.dao.SstudentMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.School;
import com.xiaoshu.entity.Sstudent;
import com.xiaoshu.entity.SstudentExample;
import com.xiaoshu.entity.SstudentExample.Criteria;
import com.xiaoshu.entity.SstudentVo;
import com.xiaoshu.entity.StudentVo;

@Service
public class SstudentService {

	@Autowired
	UserMapper userMapper;

/*	// 查询所有
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
	private SstudentMapper sstudentMapper;
	
	@Autowired
	private SchoolMapper schoolMapper;

	public PageInfo<SstudentVo> findUserPage(SstudentVo sstudentVo, int pageNum, int pageSize, String ordername, String order) {
		PageHelper.startPage(pageNum, pageSize);
		ordername = StringUtil.isNotEmpty(ordername)?ordername:"userid";
		order = StringUtil.isNotEmpty(order)?order:"desc";
		
		List<SstudentVo> userList = sstudentMapper.findPage(sstudentVo);
		PageInfo<SstudentVo> pageInfo = new PageInfo<SstudentVo>(userList);
		return pageInfo;
	}

	public List<School> findAllSchool() {
		return schoolMapper.selectAll();
	}

	// 新增
		public void addUser(Sstudent t) throws Exception {
			sstudentMapper.insert(t);
		};

		// 修改
		public void updateUser(Sstudent t) throws Exception {
			sstudentMapper.updateByPrimaryKeySelective(t);
		};
		
		// 通过用户名判断是否存在，（新增时不能重名）
		public Sstudent existUserWithUserName(String userName) throws Exception {
			SstudentExample example = new SstudentExample();
			Criteria criteria = example.createCriteria();
			criteria.andSnameEqualTo(userName);
			List<Sstudent> userList = sstudentMapper.selectByExample(example);
			return userList.isEmpty()?null:userList.get(0);
		};

		// 删除
		public void deleteUser(Integer id) throws Exception {
			sstudentMapper.deleteByPrimaryKey(id);
		}

		public List<SstudentVo> findLog(SstudentVo sstudentVo){
			List<SstudentVo> userList = sstudentMapper.findPage(sstudentVo);
			return userList;
					}

	

	

		
		
}
