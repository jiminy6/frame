package cn.itcast.bos.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.CourierService;
 /**
     * 说明：
     * @author luowenxin
     * @version 1.0
     * @date 2017年11月28日
     */
@Controller
@Scope("prototype")
public class CourierAction extends BaseAction<Courier> {
	@Autowired
	private CourierService courierService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Action(value="courier_save",results={@Result(type="redirect",location="/pages/base/courier.html")})
	public String save(){
		courierService.save(model);
		return SUCCESS;
	}
	 
	    /**
	     * 说明：分页(属性驱动)，业务(模型驱动)条件查询
	     * @return
	     * @author luowenxin
	     * @time：2017年11月28日 下午10:51:30
	     */
	@Action(value="courier_page")
	public String page(){
		Pageable pageRequest = new PageRequest(page-1,rows);//将页面上传来的page和rows参数封装成pageRequest对象
		Specification<Courier> spec = new Specification<Courier>() {

			/* (non-Javadoc)
			 * @see org.springframework.data.jpa.domain.Specification#toPredicate(javax.persistence.criteria.Root, javax.persistence.criteria.CriteriaQuery, javax.persistence.criteria.CriteriaBuilder)
			 * 参数一：主查询对象
			 * 参数二：简单查询对象，单表查询？
			 * 参数三：复杂查询对象(用这个)，多表查询？
			 */
			@Override
				public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new ArrayList<>();
				//简单对象- 单表
					//工号
					if(StringUtils.isNotBlank(model.getCourierNum())){
						Predicate equal = cb.equal(root.get("courierNum").as(String.class),model.getCourierNum());
						list.add(equal);
					}
					// 公司
					if(StringUtils.isNotBlank(model.getCompany())){
						Predicate like = cb.like(root.get("company").as(String.class),model.getCompany());
						list.add(like);
					}
					// 类型
					if(StringUtils.isNoneBlank(model.getType())){
						Predicate like = cb.like(root.get("type").as(String.class),model.getType());
						list.add(like);
					}
				//复杂兑现- 多表
					if(model.getStandard()!=null){
					 Join<Object, Object> standardJoin = root.join("standard");
					 if(StringUtils.isNotBlank(model.getStandard().getName())){
							Predicate e = cb.like(standardJoin.get("name").as(String.class),"%"+model.getStandard().getName()+"%");
							list.add(e);
						}
					}
					//将这个查询的列表转换成一个predicate数组,然后cb.add这个数组成为一个唯一的predicate
					Predicate and = cb.and(list.toArray(new Predicate[0]));
					return and;
				}
		};
		Page<Courier> pageResponse = courierService.page(spec,pageRequest);
		putDataToStack(pageResponse);
		return JSON;//这里必须写json，因为baseActin中绑定了name=json 时type=json
	}
	 /**
	  * 
	      * 说明：这是一个批量作废快递员的方法
	      * @return
	      * @author luowenxin
	      * @time：2017年11月29日 下午3:23:22
	  */
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	private Map<String,Object> resultMap=new HashMap<>();//用来存储异常信息
	@Action(value="courier_deleteBatch")
	public String deleteBatch(){
		try {
			courierService.deletBatch(ids);
			resultMap.put("result",true);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", false);
		}
		ActionContext.getContext().getValueStack().push(resultMap);
		return JSON;
	}
}
