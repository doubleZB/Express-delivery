package cn.itcast.bos.service.take_delivery;

import javax.jws.WebService;

import cn.itcast.bos.domain.take_delivery.Order;

@WebService
public interface OrderService {
	/**
	 * 保存订单
	 * @param order
	 */
	public void save(Order order);
}
