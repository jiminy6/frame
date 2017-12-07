package cn.itcast.bos.web.action.comon;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
@Result(name=BaseAction.JSON,type=BaseAction.JSON)
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
	protected int page;//页面传来的当前页码数
	protected int rows;//页面传来的每页显示数
	protected Map<String,Object> map=new HashMap<>();//封装total和rows的map集合
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	/**
	 * 泛形在接口中的使用(类型不明确)
	 */
	private static final long serialVersionUID = 1L;
	public static final String JSON ="json";// 定义字符串常量
	public static final String REDIRECT="redirect";
	protected T model;
	@Override
	public T getModel() {
		return model;
	}
	/**
	 * 默认构造方法中结合反射和泛型创建模型对象,通过获取到字节码对象创建对象(反射)，泛型(普适性)
	 * 先获取它的父对象    getGenericSuperclass
	 * 将父对象转成泛形对象 parameterizedType 
	 * 获取泛形类的具体类型 getActualTypeArguments,取第一个加上[0];
	 */
	
	public BaseAction(){
		
		Type superclass = this.getClass().getGenericSuperclass();
		ParameterizedType superclass2 =  (ParameterizedType) superclass;
		Class<T> clazz= (Class<T>) superclass2.getActualTypeArguments()[0];
		try {
			model = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	    /**
	     * 说明：将分页方法抽取到baseAction中
	     * @author luowenxin
	     * @time：2017年11月29日 上午9:25:45
	     */
	public void putDataToStack(Page<T>pageResponse){
		map.put("total", pageResponse.getTotalElements());
		map.put("rows",pageResponse.getContent());
		ActionContext.getContext().getValueStack().push(map);
	}
}
