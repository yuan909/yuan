package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.MajorMapper;
import com.xiaoshu.dao.StuMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Major;
import com.xiaoshu.entity.Stu;
import com.xiaoshu.entity.StuExample;
import com.xiaoshu.entity.StuExample.Criteria;
import com.xiaoshu.entity.StuVo;


@Service
public class StuService {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	private RedisTemplate redisTemplate;

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
	private StuMapper stuMapper;
	
	@Autowired
	private MajorMapper majorMapper;
	
	public PageInfo<StuVo> findUserPage(StuVo stuVo, int pageNum, int pageSize, String ordername, String order) {
		PageHelper.startPage(pageNum, pageSize);
		ordername = StringUtil.isNotEmpty(ordername)?ordername:"userid";
		order = StringUtil.isNotEmpty(order)?order:"desc";
	
		List<StuVo> userList = stuMapper.findPage(stuVo);
		PageInfo<StuVo> pageInfo = new PageInfo<StuVo>(userList);
		return pageInfo;
	}

	public List<Major> findAllMajor() {
		return majorMapper.selectAll();
	}

	// 新增
		public void addUser(Stu t) throws Exception {
			stuMapper.insert(t);
		};

		// 修改
		public void updateUser(Stu t) throws Exception {
			stuMapper.updateByPrimaryKeySelective(t);
		};
		
		// 通过用户名判断是否存在，（新增时不能重名）
		public Stu existUserWithUserName(String userName) throws Exception {
			StuExample example = new StuExample();
			Criteria criteria = example.createCriteria();
			criteria.andSdnameEqualTo(userName);
			List<Stu> userList = stuMapper.selectByExample(example);
			return userList.isEmpty()?null:userList.get(0);
		}

		public List<StuVo> findLog(StuVo stuVo) {
			return stuMapper.findPage(stuVo);
		};

		// 删除
		public void deleteUser(Integer sdId) throws Exception {
			stuMapper.deleteByPrimaryKey(sdId);
		}

		public List<StuVo> getgetEcharts() {
			return stuMapper.getgetEcharts();
		}

		public void addMajor(Major major) {
			majorMapper.insert(major);
			redisTemplate.boundHashOps(major).put(major, major.toString());
			
		};
		

}
