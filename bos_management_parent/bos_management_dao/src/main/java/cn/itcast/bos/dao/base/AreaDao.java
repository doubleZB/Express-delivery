package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.base.Area;

public interface AreaDao extends JpaRepository<Area, String> {

	List<Area> findByCitycodeLikeOrShortcodeLike(String string, String string2);

	Area findByProvinceAndCityAndDistrict(String province, String city, String district);

}
