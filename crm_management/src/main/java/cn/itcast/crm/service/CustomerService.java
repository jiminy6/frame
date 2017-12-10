package cn.itcast.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.itcast.crm.domain.Customer;
@Path("/customers")
@Consumes({MediaType.APPLICATION_ATOM_XML,MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})//生产者
public interface CustomerService {
	 
	    /**
	     * 说明：获取所有没有定区的customers
	     * @return
	     * @author luowenxin
	     * @time：2017年12月2日 下午9:17:08
	     */
	@Path("/nofixedaread")
	@GET
	public List<Customer> findCustomerListNoFixedAreaId();	
	 
	    /**
	     * 说明：根据指定的定区编号来查找客户列表
	     * @param areaid
	     * @return
	     * @author luowenxin
	     * @time：2017年12月2日 下午9:18:47
	     */
	@Path("/fixedaread/{fixedareaid}")
	@GET
	public List<Customer> findCustomerListByFixedAreaId(@PathParam("fixedareaid")String fixedareaid);
	/**
	 * 
	     * 说明：将客户关联到指定的定区中
	     * @author luowenxin
	     * @time：2017年12月2日 下午9:21:13
	 */
	@Path("/fixedaread/{fixedareaid}/{ids}")
	@PUT
	public void updateFixedAreaIdByCustomerIds(@PathParam("fixedareaid")String fixedareaid,@PathParam("ids") String ids);
	
	@POST
	public void saveCustomer(Customer customer);
	
	@Path("type/{telephone}/{type}")
	@PUT
	public void activeCustomer(@PathParam("telephone")String telephone,@PathParam("type")Integer type);
	
     
        /**
         * 说明：根据定区的地址，查询定区的编号
         * @return
         * @author luowenxin
         * @time：2017年12月8日 上午10:36:40
         */
	@Path("/fixedareaid/address/{address}")
	@GET
    public Customer  findFixedAreaByAddress(@PathParam("address")String address);
}
