package com.xiaoshu.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.alibaba.fastjson.JSONObject;
import com.xiaoshu.entity.Bank;

public class MyMessageListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			String text = textMessage.getText();
			Bank bank = JSONObject.parseObject(text, Bank.class);
			System.out.println("接受到的数据内容为："+bank);
		} catch (JMSException e) {
			// TODO: handle exception
		}
	}
	
}
