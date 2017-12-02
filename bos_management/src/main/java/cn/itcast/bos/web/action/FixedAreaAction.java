package cn.itcast.bos.web.action;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.FixedAreaService;
@Controller
public class FixedAreaAction extends BaseAction<FixedArea> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private FixedAreaService fixedAreaService;
	@Action(value="fixedArea_save",results={
			@Result(type="redirect",location="pages/base/fixed_area.html")
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
}
