package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Standard;

//不用写注解也不用实现类
public interface StandardDao extends JpaRepository<Standard, Integer> {

	Standard findByName(String string);

	List<Standard> findByNameLike(String string);

	List<Standard> findByNameAndOperator(String string, String string2);

	List<Standard> findByNameOrOperator(String string, String string2);

	List<Standard> findByNameLikeOrOperatorLike(String string, String string2);

//	@Query("from Standard where name=?") //JPQL
	@Query(value="select * from T_STANDARD where C_NAME=?",nativeQuery=true)
	List<Standard> findBymingzi(String string);

	@Query("update Standard  set name=?") //JPQL
	@Modifying  //涉及到修改数据时
	void updateOperator(String string);

}
