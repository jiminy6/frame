package cn.itcast.bos.web.action;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.FixedAreaService;
import cn.itcast.bos.web.action.comon.BaseAction;
import cn.itcast.crm.domain.Customer;
import cn.itcast.utils.Constants;
@Controller
public class FixedAreaAction extends BaseAction<FixedArea> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private FixedAreaService fixedAreaService;
	@Action(value="fixedArea_save",results={
			@Result(type=REDIRECT,location="pages/base/fixed_area.html")
	})
	public String save(){
		fixedAreaService.save(model);
		return SUCCESS;
	}
	@Action("fixed_page")
	public String page(){
		Pageable pageRequest = new PageRequest(page-1,rows);
		Specification<FixedArea> spec= new Specification<FixedArea>() {
			@Override
			public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate conjunction = cb.conjunction();//and 形式
				//单表
				if(StringUtils.isNotBlank(model.getId())){
					Predicate equal = cb.equal(root.get("id").as(String.class),model.getId());
			        conjunction.getExpressions().add(equal);
				}
				if(StringUtils.isNotBlank(model.getCompany())){
					Predicate like = cb.like(root.get("company").as(String.class),"%"+model.getCompany()+"%");
					conjunction.getExpressions().add(like);
				}
				//多表
				return conjunction ;
			}
		};
		Page<FixedArea> pageResponse=fixedAreaService.page(spec,pageRequest);
	    System.out.println(pageResponse);
		putDataToStack(pageResponse);
		return JSON;
	}
	
	@Action("fixedArea_listCustomerListByFixedAreaId")
	public String findByfixedAreaId(){
//	    Collection<? extends Customer> collection = WebClient.create("http://localhost:8002/crm_management/services/customerservice/customers"
	    		Collection<? extends Customer> collection = WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
		.path("/fixedaread")
		.path("/"+model.getId())
		.accept(MediaType.APPLICATION_JSON)
		.getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(collection);
		return JSON;
	}
	@Action("fixedArea_listCustomerListNoFixedAreaId")
	public String findnofixedArea(){
//		 Collection<? extends Customer> collection = WebClient.create("http://localhost:8002/crm_management/services/customerservice/customers")
				 Collection<? extends Customer> collection = WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
					.path("/nofixedaread")
					.accept(MediaType.APPLICATION_JSON)
					.getCollection(Customer.class);
					ActionContext.getContext().getValueStack().push(collection);
		return JSON;
	}
	private String ids;//用属性驱动获取页面上的customersIds
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Action(value="fixedArea_updatearea",results={@Result(type=REDIRECT,location="/pages/base/fixed_area.html")})
	public String updatefixedarea(){
		String customerIds=StringUtils.join(ids,",");
		System.out.println(customerIds);
//		WebClient.create("http://localhost:8002/crm_management/services/customerservice/customers")
		WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
		.path("/fixedaread")
		.path("/"+model.getId())
		.path("/"+customerIds)
		.accept(MediaType.APPLICATION_JSON)
		.put(null);
		return SUCCESS;
	}
}
