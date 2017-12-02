package cn.itcast.bos.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.bos.domain.base.Standard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class StandardRepositoryTest {
	@Autowired
	private StandardRepository standardRepository;
	@Test
	public void test1(){
		Standard standard = new Standard();
		standard.setName("大炮");
		standard.setId(3);
		standard.setOperator("兔子");
		standardRepository.save(standard);
	}
	@Test
	public void test2(){
//		Standard findOne = standardRepository.findOne(1);
//		System.out.println(findOne);
	    Standard one= standardRepository.findByName("大炮");
		System.out.println(one);
	}
	@Test
	public void test3(){
		String nameById = standardRepository.findNameById(3);
		System.out.println(nameById);
	}
	@Test
	public void test4(){
		Standard standard = standardRepository.findByNameAndId(3,"大炮");
		System.out.println(standard);
	}
	
}
