package cn.itcast.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaDao;
import cn.itcast.bos.dao.base.FixedAreaDao;
import cn.itcast.bos.dao.base.OrderDao;
import cn.itcast.bos.dao.base.SubAreaDao;
import cn.itcast.bos.dao.take_delivery.WorkBillDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.take_delivery.Order;
import cn.itcast.bos.domain.take_delivery.WorkBill;
import cn.itcast.bos.service.take_delivery.OrderService;
import cn.itcast.bos.utils.SmsUtils;
import cn.itcast.crm.ws.impl.CustomerService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao dao;
	@Autowired
	private AreaDao adao;
	@Autowired
	private FixedAreaDao fdao;
	@Autowired
	private CustomerService crmProxy;
	@Autowired
	private WorkBillDao wdao;
	@Autowired
	private SubAreaDao sdao;
	
	@Override
	public void save(Order order) {
		System.out.println("**************************执行了bos后台的保存订单方法");
		try {
//			把order中的sendArea做成持久态
			Area sendArea = order.getSendArea(); //sendArea瞬时态
			sendArea = adao.findByProvinceAndCityAndDistrict(sendArea.getProvince(),sendArea.getCity(),sendArea.getDistrict());
			order.setSendArea(sendArea); //sendArea是持久态
			order = dao.save(order);
			//取件地址
			String sendAddress = order.getSendAddress();
//			找到快递员
//			基于CRM地址库完全匹配实现自动分单
			String fixedAreaId = crmProxy.findByAddress(sendAddress);
			if(fixedAreaId!=null){
				FixedArea fixedArea = fdao.findOne(fixedAreaId);
				Set<Courier> couriers = fixedArea.getCouriers();
//				1、上下班时间
//				2、接收能力
//				3、距离问题
//				模拟得到了一个快递员
				for (Courier courier : couriers) {
					WorkBill workBill = new WorkBill();
					workBill.setAttachbilltimes(0);
					workBill.setBuildtime(new Date());
					workBill.setCourier(courier);
					workBill.setOrder(order);
					workBill.setPickstate("已通知"); //给快递员发送过通知短信
					workBill.setRemark(order.getRemark());
					workBill.setSmsNumber("13236ytd");
					workBill.setType("新");
					wdao.save(workBill);
//					SmsUtils.sendSmsByWebService(courier.getTelephone(), "请到"+sendAddress+"取件");
					break;//跳出循环
				}
			}else{
//				基于分区关键字匹配实现自动分单
//				sendAddress去和每个分区的关键字和分区辅助关键字做匹配（包含）
//				sendAddress
				List<SubArea> findAll = sdao.findAll();
				for (SubArea subArea : findAll) {
					if(sendAddress.contains(subArea.getKeyWords())||sendAddress.contains(subArea.getAssistKeyWords())){
						FixedArea fixedArea2 = subArea.getFixedArea();
						Set<Courier> couriers = fixedArea2.getCouriers();
//						1、上下班时间
//						2、接收能力
//						3、距离问题
//						模拟得到了一个快递员
						for (Courier courier : couriers) {
							WorkBill workBill = new WorkBill();
							workBill.setAttachbilltimes(0);
							workBill.setBuildtime(new Date());
							workBill.setCourier(courier);
							workBill.setOrder(order);
							workBill.setPickstate("已通知"); //给快递员发送过通知短信
							workBill.setRemark(order.getRemark());
							workBill.setSmsNumber("13236ytd");
							workBill.setType("新");
							wdao.save(workBill);
//							SmsUtils.sendSmsByWebService(courier.getTelephone(), "请到"+sendAddress+"取件");
							break;//跳出循环
						}
					}
				}
				
				
			}
			
//			保存工单
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
