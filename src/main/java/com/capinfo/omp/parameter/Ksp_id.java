package com.capinfo.omp.parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.id.IdentityGenerator.GetGeneratedKeysDelegate;
import org.springframework.jdbc.core.JdbcTemplate;

import com.capinfo.framework.model.BaseEntity;
import com.capinfo.framework.service.GeneralService;
import com.capinfo.omp.model.ServiceProvider;

public class Ksp_id {

	private Long m1;
	private Long m2;
	private Long m3;
	private Long m4;
	private Long m5;
	private Long m6;
	private Long m7;
	private Long m8;
	private Long m9;
	private Long m10;
	private Long m11;
	private Long m12;
	private Long m13;
	private Long m14;
	private Long m15;
	private Long m16;

	public Long getM1() {
		return m1;
	}

	public void setM1(Long m1) {
		this.m1 = m1;
	}

	public Long getM2() {
		return m2;
	}

	public void setM2(Long m2) {
		this.m2 = m2;
	}

	public Long getM3() {
		return m3;
	}

	public void setM3(Long m3) {
		this.m3 = m3;
	}

	public Long getM4() {
		return m4;
	}

	public void setM4(Long m4) {
		this.m4 = m4;
	}

	public Long getM5() {
		return m5;
	}

	public void setM5(Long m5) {
		this.m5 = m5;
	}

	public Long getM6() {
		return m6;
	}

	public void setM6(Long m6) {
		this.m6 = m6;
	}

	public Long getM7() {
		return m7;
	}

	public void setM7(Long m7) {
		this.m7 = m7;
	}

	public Long getM8() {
		return m8;
	}

	public void setM8(Long m8) {
		this.m8 = m8;
	}

	public Long getM9() {
		return m9;
	}

	public void setM9(Long m9) {
		this.m9 = m9;
	}

	public Long getM10() {
		return m10;
	}

	public void setM10(Long m10) {
		this.m10 = m10;
	}

	public Long getM11() {
		return m11;
	}

	public void setM11(Long m11) {
		this.m11 = m11;
	}

	public Long getM12() {
		return m12;
	}

	public void setM12(Long m12) {
		this.m12 = m12;
	}

	public Long getM13() {
		return m13;
	}

	public void setM13(Long m13) {
		this.m13 = m13;
	}

	public Long getM14() {
		return m14;
	}

	public void setM14(Long m14) {
		this.m14 = m14;
	}

	public Long getM15() {
		return m15;
	}

	public void setM15(Long m15) {
		this.m15 = m15;
	}

	public Long getM16() {
		return m16;
	}

	public void setM16(Long m16) {
		this.m16 = m16;
	}

	@Override
	public String toString() {
		return "[{\"m1\":\""+m1+"\",\"m2\":\""+m2+"\",\"m3\":\""+m3+"\",\"m4\":\""+m4
				+"\",\"m5\":\""+m5+"\",\"m6\":\""+m6+"\",\"m7\":\""+m7+"\",\"m8\":\""+m8
				+"\",\"m9\":\""+m9+"\",\"m10\":\""+m10+"\",\"m11\":\""+m11+"\",\"m12\":\""
				+m12+"\",\"m13\":\""+m13+"\",\"m14\":\""+m14+"\",\"m15\":\""+m15
				+"\",\"m16\":\""+m16+"\"}]";
	}
	

	// 获取服务商
	public List<Map<String, Object>> getSp(GeneralService g) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		Map<String, Object> map4 = new HashMap<String, Object>();
		Map<String, Object> map5 = new HashMap<String, Object>();
		Map<String, Object> map6 = new HashMap<String, Object>();
		Map<String, Object> map7 = new HashMap<String, Object>();
		Map<String, Object> map8 = new HashMap<String, Object>();
		Map<String, Object> map9 = new HashMap<String, Object>();
		Map<String, Object> map10 = new HashMap<String, Object>();
		Map<String, Object> map11 = new HashMap<String, Object>();
		Map<String, Object> map12 = new HashMap<String, Object>();
		Map<String, Object> map13 = new HashMap<String, Object>();
		Map<String, Object> map14 = new HashMap<String, Object>();
		Map<String, Object> map15 = new HashMap<String, Object>();
		Map<String, Object> map16 = new HashMap<String, Object>();
		map1.put("key", "M1");
		map2.put("key", "M2");
		map3.put("key", "M3");
		map4.put("key", "M4");
		map5.put("key", "M5");
		map6.put("key", "M6");
		map7.put("key", "M7");
		map8.put("key", "M8");
		map9.put("key", "M9");
		map10.put("key", "M10");
		map11.put("key", "M11");
		map12.put("key", "M12");
		map13.put("key", "M13");
		map14.put("key", "M14");
		map15.put("key", "M15");
		map16.put("key", "M16");
		if (m1 != 0) {
			ServiceProvider sp1 = g.getObjectById(ServiceProvider.class, m1);
			map1.put("sp", sp1);
		}
		if (m2 != 0) {
			ServiceProvider sp2 = g.getObjectById(ServiceProvider.class, m2);

			map2.put("sp", sp2);
		}
		if (m3 != 0) {
			ServiceProvider sp3 = g.getObjectById(ServiceProvider.class, m3);

			map3.put("sp", sp3);
		}
		if (m4 != 0) {
			ServiceProvider sp4 = g.getObjectById(ServiceProvider.class, m4);

			map4.put("sp", sp4);
		}
		if (m5 != 0) {
			ServiceProvider sp5 = g.getObjectById(ServiceProvider.class, m5);

			map5.put("sp", sp5);
		}
		if (m6 != 0) {
			ServiceProvider sp6 = g.getObjectById(ServiceProvider.class, m6);

			map6.put("sp", sp6);
		}
		if (m7 != 0) {
			ServiceProvider sp7 = g.getObjectById(ServiceProvider.class, m7);

			map7.put("sp", sp7);
		}
		if (m8 != 0) {
			ServiceProvider sp8 = g.getObjectById(ServiceProvider.class, m8);

			map8.put("sp", sp8);
		}
		if (m9 != 0) {
			ServiceProvider sp9 = g.getObjectById(ServiceProvider.class, m9);

			map9.put("sp", sp9);
		}
		if (m10 != 0) {
			ServiceProvider sp10 = g.getObjectById(ServiceProvider.class, m10);

			map10.put("sp", sp10);
		}
		if (m11 != 0) {
			ServiceProvider sp11 = g.getObjectById(ServiceProvider.class, m11);

			map11.put("sp", sp11);
		}
		if (m12 != 0) {
			ServiceProvider sp12 = g.getObjectById(ServiceProvider.class, m12);

			map12.put("sp", sp12);
		}
		if (m13 != 0) {
			ServiceProvider sp13 = g.getObjectById(ServiceProvider.class, m13);

			map13.put("sp", sp13);
		}
		if (m14 != 0) {
			ServiceProvider sp14 = g.getObjectById(ServiceProvider.class, m14);

			map14.put("sp", sp14);
		}
		if (m15 != 0) {
			ServiceProvider sp15 = g.getObjectById(ServiceProvider.class, m15);

			map15.put("sp", sp15);
		}
		if (m16 != 0) {
			ServiceProvider sp16 = g.getObjectById(ServiceProvider.class, m16);

			map16.put("sp", sp16);
		}

		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
		list.add(map7);
		list.add(map8);
		list.add(map9);
		list.add(map10);
		list.add(map11);
		list.add(map12);
		list.add(map13);
		list.add(map14);
		list.add(map15);
		list.add(map16);

		return list;

	}

}
