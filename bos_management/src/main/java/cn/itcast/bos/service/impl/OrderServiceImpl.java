package cn.itcast.bos.service.impl;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.AreaRepository;
import cn.itcast.bos.dao.FixedAreaRepository;
import cn.itcast.bos.dao.OrderRepository;
import cn.itcast.bos.dao.WorkBillRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.take_delivery.Order;
import cn.itcast.bos.domain.take_delivery.WorkBill;
import cn.itcast.bos.service.OrderService;
import cn.itcast.crm.domain.Customer;
import cn.itcast.utils.Constants;
@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private AreaRepository areaRepository;
	@Autowired
	private FixedAreaRepository fixedAreaRepository ;
	@Autowired
	private WorkBillRepository workBillRepository ;
	@Override
	public void add(Order order) {
	    Area sendArea = areaRepository.findByProvinceAndCityAndDistrict(order.getSendArea().getProvince(),order.getSendArea().getCity(),order.getSendArea().getDistrict());
		Area recArea = areaRepository.findByProvinceAndCityAndDistrict(order.getRecArea().getProvince(),order.getRecArea().getCity(),order.getRecArea().getDistrict());
		order.setRecArea(recArea);
		order.setSendArea(sendArea);
		order.setStatus("待取件");
//		order.setRemark("快递哥哥，快来呀～");
		order.setOrderNum(UUID.randomUUID().toString());
		order.setSendMobileMsg("您的订单已经收到~");
		order.setOrderType("人工分单");
		order.setOrderTime(new Date());
		orderRepository.save(order);
		//==========================================
		String address=sendArea.getProvince()+sendArea.getCity()+sendArea.getDistrict()+order.getSendAddress();//拼接寄件人地址
		System.out.println(address);
//		WebClient.create("http://localhost:9002/crm_management/services/customerservice/customers/fixedareaid/address/北京市朝阳区xx小区");
		Customer customer = WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
		.path("/fixedareaid")
		.path("/address")
		.path("/"+address)
		.type(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.get(Customer.class);
		System.out.println(customer);
		//先走完全匹配
		if(customer!=null){
			String fixedAreaId = customer.getFixedAreaId();
			System.out.println("=========================================");
			System.out.println(customer.getFixedAreaId());
			if(StringUtils.isNotBlank(fixedAreaId)){
				FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
				Set<Courier> couriers = fixedArea.getCouriers();
				if(couriers!=null&&!couriers.isEmpty()){
					Courier courier = couriers.iterator().next();
					order.setCourier(courier);//关联快递员
					saveWorkBill(order, courier);
					return ;//当某个规则匹配的时,不在继续派单
				}
			}
		}
		//关键字匹配
		if(sendArea!=null){
			Set<SubArea> subareas = sendArea.getSubareas();//获取区域下的所有的分区信息
			for (SubArea sub : subareas) {
				//如果订单地址包含分区的关键字
				if(order.getSendAddress().contains(sub.getKeyWords())){
					FixedArea fixedArea = sub.getFixedArea();//通过分区找到定区
					if(fixedArea!=null){
						Set<Courier> couriers = fixedArea.getCouriers();
						if(couriers!=null&&!couriers.isEmpty()){
							Courier courier = couriers.iterator().next();
							order.setOrderType("自动分单");
							order.setCourier(courier);//关联快递员
							saveWorkBill(order, courier);
							return ;
						}
						
					}
					break;
				}
			}
			
		}
		
	}
	private void saveWorkBill(Order order, Courier courier) {
		WorkBill workBill = new WorkBill();
         //设置工单
		workBill.setRemark(order.getRemark());
		workBill.setCourier(courier);
		workBill.setBuildtime(new Date());
		workBill.setType("新");
		workBill.setOrder(order);
		workBill.setSmsNumber("2222");
		workBill.setAttachbilltimes(0);
		workBill.setPickstate("新单");
		workBillRepository.save(workBill);
	}

}
