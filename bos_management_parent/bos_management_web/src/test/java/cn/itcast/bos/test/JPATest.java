package cn.itcast.bos.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.bos.dao.base.StandardDao;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class JPATest {
	
	@Autowired
	private StandardDao dao;
	
	@Autowired
	private StandardService service;
	
	@Test
	public void testAdd(){
		Standard  standard = new Standard();
//		standard.setId(id);
		standard.setMaxLength(100);
		standard.setMaxWeight(100);
		standard.setMinLength(11);
		standard.setMinWeight(11);
		standard.setName("11-100");
		standard.setOperatingCompany("顺义分公司");
		standard.setOperatingTime(new Date());
		standard.setOperator("admin");
		dao.save(standard);
	}
	
	@Test
	public void testFindAll(){
		List<Standard> list = dao.findAll();
		System.out.println(list);
	}
	
	@Test
	public void testFindOne(){
		Standard standard = dao.findOne(1);
		System.out.println(standard);
	}
	
	@Test
	public void testFindByName(){
		Standard standard = dao.findByName("11-100");
		System.out.println(standard);
	}
	
	@Test
	public void testFindByNameLike(){
		List<Standard> list = dao.findByNameLike("%10%");
		System.out.println(list);
	}
	
	@Test
	public void testFindByNameAndOperator(){
		List<Standard> list = dao.findByNameAndOperator("1-10","admin");
		System.out.println(list);
	}
	
	@Test
	public void testFindByNameOrOperator(){
		List<Standard> list = dao.findByNameOrOperator("1-10","admin");
		System.out.println(list);
	}
	
	@Test
	public void testFindByNameLikeOrOperatorLike(){
		List<Standard> list = dao.findByNameLikeOrOperatorLike("1-10","admin");
		System.out.println(list);
	}
	
	@Test
	public void testFindBymingzi(){
		List<Standard> list = dao.findBymingzi("1-10");
		System.out.println(list);
	}
	
	@Test
	public void testUpdateOperator(){
		service.updateOperator("asasa");
	}

}
