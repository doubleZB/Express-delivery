package cn.itcast.bos.service.take_delivery.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.dao.take_delivery.WorkBillDao;
import cn.itcast.bos.domain.take_delivery.WorkBill;
import cn.itcast.bos.utils.MailUtils;

public class JobUtils {

	@Autowired
	private WorkBillDao dao;
//	发送当前月的工单信息
	public void sendWorkBillMail(){
//		查询工单信息
		List<WorkBill> list = dao.findByMonth();
		String content ="";
		for (WorkBill workBill : list) {
			content+=workBill.toString()+"<br/>";
		}
		String subject = "工单信息"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date());
		MailUtils.sendMail(subject, content, "itcast_server@sina.com");
		System.out.println("邮件发送成功");
	}
}
