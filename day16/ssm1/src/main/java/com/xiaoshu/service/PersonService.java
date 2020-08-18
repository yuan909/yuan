package com.xiaoshu.service;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.BankMapper;
import com.xiaoshu.dao.PersonMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Bank;
import com.xiaoshu.entity.Person;
import com.xiaoshu.entity.PersonExample;
import com.xiaoshu.entity.PersonExample.Criteria;
import com.xiaoshu.entity.PersonVo;


@Service
public class PersonService {

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
	}
*/
	@Autowired
	private PersonMapper personMapper;
	
	@Autowired
	private BankMapper bankMapper;
	
	public PageInfo<PersonVo> findUserPage(PersonVo personVo, int pageNum, int pageSize, String ordername, String order) {
		PageHelper.startPage(pageNum, pageSize);
		ordername = StringUtil.isNotEmpty(ordername)?ordername:"userid";
		order = StringUtil.isNotEmpty(order)?order:"desc";
		
		List<PersonVo> userList = personMapper.findPage(personVo);
		PageInfo<PersonVo> pageInfo = new PageInfo<PersonVo>(userList);
		return pageInfo;
	}

	public List<Bank> findAllBank() {
		return bankMapper.selectAll();
	}
	
	// 新增
		public void addUser(Person t) throws Exception {
			personMapper.insert(t);
		};

		// 修改
		public void updateUser(Person t) throws Exception {
			personMapper.updateByPrimaryKeySelective(t);
		};

		// 通过用户名判断是否存在，（新增时不能重名）
		public Person existUserWithUserName(String userName) throws Exception {
			PersonExample example = new PersonExample();
			Criteria criteria = example.createCriteria();
			criteria.andPNameEqualTo(userName);
			List<Person> userList = personMapper.selectByExample(example);
			return userList.isEmpty()?null:userList.get(0);
		};

		// 删除
		public void deleteUser(Integer pId) throws Exception {
			personMapper.deleteByPrimaryKey(pId);
		}

		public List<PersonVo> getEcharts() {
			return personMapper.getEcharts();
		}

		@Autowired
		private JmsTemplate jmsTemplate;
		
		@Autowired
		private Destination queueTextDestination;
		public void findPage(final Bank bank) {
			bankMapper.insert(bank);
			jmsTemplate.send(queueTextDestination, new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					String jsom = JSONObject.toJSONString(bank);
					return session.createTextMessage(jsom);
				}
			});
		};
		

}
