package com.fun.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fun.domain.Customer;
import com.fun.service.CustomerService;
import com.fun.utils.UploadUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


/**
 * @author luowenxin
 *
 */
@Controller
@Scope("prototype")
@Namespace("/customer")
@ParentPackage("json-default")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, Object> pagination = new HashMap<String,Object>();//手动实例化
//	private List<Customer> pages;
	private Customer customer = new Customer();//手动实例化
	private String uploadFileName;
	private String uploadContentTpye;//上传的文件的类型
	private File upload;// 表示上传的文件
	@Autowired
	private CustomerService customerService;
	private Customer viewCustomer;// 记录选中的客户信息,保存到值栈中,提供get方法
	private List<Customer> customers;// 这个用来查看所有的customer信息,将查询到到的信息存储到值栈中,并提供get方法
	private int page;// 当前页码数
	private int rows;// 每个页码显示的数目
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public void setUploadContentTpye(String uploadContentTpye) {
		this.uploadContentTpye = uploadContentTpye;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	@Override
	public Customer getModel() {
		return customer;
	}
	

	/**
	 * @return
	 * 使用Struts的文件添加功能
	 *  upload
	 *  文件的类型
	 *  文件的原始名字uploadFileName
	 */
	@Action(value = "customer_save", results = { @Result(name = "success", location = "/jsp/customer/list.jsp"),
			@Result(name="input",location="/jsp/customer/add.jsp") },
			interceptorRefs={@InterceptorRef(value="defaultStack",
			params={"fileUpload.maximumSize","10485760"})})//检验类型,"fileUpload.allowedExtensions",".jpg,.mp3"
	// 引入interceptorRef属性,设置文件上传大小的参数
	public String save() { 
		if(upload!=null){
			String randonFileName = UploadUtils.generateRandonFileName(uploadFileName);
			System.out.println(randonFileName);
			String randomDir = UploadUtils.generateRandomDir(randonFileName);
			System.out.println(randomDir);
			try {
				FileUtils.copyFile(upload,new File("/develop/upload"+randomDir+"/"+randonFileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 这个是customer的原文件名
			customer.setCust_filename(uploadFileName);
			customer.setCust_image(randomDir+"/"+randonFileName);
		}
		
		customerService.save(customer);
		return SUCCESS;
	}
	/**
	 * @return
	 * 列表查询
	 */
	@Action(value = "customer_list", results = {@Result(name = "success", type = "json", params = { "root", "customers" }) })
	public String findAll() {
		System.out.println("action list");
		try {
			customers = customerService.findAll();
			for (Customer c : customers) {
				System.out.println(c);
			}
		} catch (Exception e) {
			System.out.println("出异常了");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public Customer getViewCustomer() {
		return viewCustomer;
	}

	public Map<String, Object> getPagination() {
		return pagination;
	}

	/**
	 * @return
	 * 分页查询
	 * 条件查询
	 * 点击下一页或者上一页的箭头时候,为什么页面不刷新?-->(Action中传递的数据没有问题)
	 */
	
	@Action(value = "customer_page", results = {@Result(name = "success", type = "json", params = { "root", "pagination" }) })
	// 这里param中的key必须为root是因为pagination默认存入的是对象栈
	public String page() {
		// 分页查询需要查询的首地址,每页显示的数目
		// 构造离线查询对象进行条件查询
		DetachedCriteria dc = DetachedCriteria.forClass(Customer.class);
		if(StringUtils.isNotBlank(customer.getCust_name())){
			dc.add(Restrictions.eq("cust_name", customer.getCust_name()));
		}
		//当点击右侧客户列表进入分页查询的页面时候,因为没有传递baseDictIndustry和baseDictSource,它们的值为空,执行后面的getDictId会报空指针异常.
		//解决方法:先进行判空操作,只有在不为空的情况下才执行后面的操作.
		if(customer.getBaseDictIndustry()!=null){
			if(StringUtils.isNotBlank(customer.getBaseDictIndustry().getDictId())){
				dc.add(Restrictions.eq("baseDictIndustry.dictId",customer.getBaseDictIndustry().getDictId()));
			}
		}
		if(customer.getBaseDictLevel()!=null){
			if(StringUtils.isNotBlank(customer.getBaseDictLevel().getDictId())){
				dc.add(Restrictions.eq("baseDictLevel.dictId",customer.getBaseDictLevel().getDictId()));
			}
		}
		if(customer.getBaseDictSource()!=null){
			if(StringUtils.isNotBlank(customer.getBaseDictSource().getDictId())){
				dc.add(Restrictions.eq("baseDictSource.dictId",customer.getBaseDictSource().getDictId()));
			}
		}
//		int start=(page-1)*rows;
//		System.out.println(start);
		int count = customerService.count(dc);
	    customers = customerService.page(dc,(page - 1) * rows, rows);
		// 查询customer的总数目,因为不是查询符合条件的customer的数量,所以需要加上条件
//		int count = customerService.count(dc); 
	    //放在这里每次都只显示第一页
		// 返回的数据中,数量总和必须交total,customer集合必须叫rows
		pagination.put("total", count);
		pagination.put("rows", customers);
		return SUCCESS;
	}
	/**
	 * @return
	 * 删除成功返回分页所有customers的页面
	 */
	@Action(value="customer_delete",results= {@Result(name="success" ,location="/jsp/customer/list.jsp")})
	public String delete(){
		customerService.delete(customer.getCust_id());
		return SUCCESS;
	}
	/**
	 * @return
	 * 根据客户的id查找,返回编辑客户的页面(数据的回显)
	 */
	@Action(value="customer_findById",results={@Result(name="success",location="/jsp/customer/edit.jsp")})
	public String findById(){
		viewCustomer=customerService.findById(customer.getCust_id());
		return SUCCESS;
	}
	/**
	 * @return
	 * 更新客户信息,需要配置和save一样的错误信息提示(input结果集)
	 */
	@Action(value="customer_update",results={@Result(name="success",location="/jsp/customer/list.jsp"),@Result(name="input",location="/jsp/customer/add.jsp") },
			interceptorRefs={@InterceptorRef(value="defaultStack",
			params={"fileUpload.maximumSize","10485760"})})
	public String update(){
		System.out.println("更新客户信息");
	    System.out.println(customer);//检验Action有没有获得页面的信息
		customerService.update(customer,upload,uploadFileName);
		return SUCCESS;
	}
}
