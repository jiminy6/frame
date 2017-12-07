package cn.itcast.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.AreaService;
import cn.itcast.bos.web.action.comon.BaseAction;
@Controller
@Scope("prototype")
public class AreaAction extends BaseAction<Area>{
	@Autowired
	private AreaService areaService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File  upload;
	private String uploadFileName;
	private String uploadContentType;
	private Map <String,Object> resultMap=new HashMap<>();//这个是用来存储结果的集合
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	 
	    /**
	     * 说明：导入地址文件
	     * @return
	     * @author luowenxin
	     * @time：2017年11月29日 下午10:05:58
	     */
	@Action("area_importData")
	public String importData(){
		System.out.println(upload.getName());
		System.out.println(uploadFileName);
		ArrayList<Area> list = new ArrayList<>();
		try {
			HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(upload));//打开工作簿
			HSSFSheet sheet = workbook.getSheetAt(0);//读取工作表
			for (Row row : sheet) {
				if(row.getRowNum()==0){
					continue;//剔除第一行的数据(第一行一般都是一些标题)
				}
			 String id = row.getCell(0).getStringCellValue();
			 String province = row.getCell(1).getStringCellValue();
			 String city = row.getCell(2).getStringCellValue();
			 String district = row.getCell(3).getStringCellValue();
			 String postcode = row.getCell(4).getStringCellValue();
			 Area area = new Area();
			 area.setId(id);
			 area.setProvince(province);
			 area.setDistrict(district);
			 area.setPostcode(postcode);
			 area.setCity(city);
			 String proviceStr = StringUtils.substring(province,0,-1);//shen
			 String cityStr = StringUtils.substring(city,0,-1);//shi
			 String districtStr = StringUtils.substring(district,0,-1);//qu
			 String shortcode = PinyinHelper.getShortPinyin(proviceStr+cityStr+districtStr).toUpperCase();//区域简码
			 area.setShortcode(shortcode);
			 String citycode = PinyinHelper.convertToPinyinString(cityStr,"",PinyinFormat.WITHOUT_TONE);
			 area.setCitycode(citycode);//城市编码
			 list.add(area);
			}
			areaService.save(list);
			resultMap.put("result",true);
		} catch (Exception e) {
			resultMap.put("result",false);
			e.printStackTrace();
		} 
		ActionContext.getContext().getValueStack().push(resultMap);
		return JSON;
	}
	 
	    /**
	     * 说明：业务条件分页查询
	     * @return
	     * @author luowenxin
	     * @time：2017年11月30日 上午10:02:24
	     */
	@Action("area_page")
	public String page(){
		Pageable pageRequest = new PageRequest(page-1,rows);
		Page<Area> pageResponse=areaService.page(pageRequest);
		putDataToStack(pageResponse);
		return JSON;
	}
}
