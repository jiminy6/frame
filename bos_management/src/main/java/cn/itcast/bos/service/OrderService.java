package cn.itcast.bos.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.itcast.bos.domain.take_delivery.Order;
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_ATOM_XML})
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_ATOM_XML})
public interface OrderService {
	@Path("/orders")
	@POST
	void add(Order order);
	
}
