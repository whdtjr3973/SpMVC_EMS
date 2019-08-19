package com.biz.ems.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.UpdateProvider;

import com.biz.ems.model.EmailVO;

public interface EmailDao {
	@Select("SELECT * FROM tbl_ems")
	public List<EmailVO> selectAll();
	@Select("SELECT * FROM tbl_ems")
	public int selectList();
	@Select(" SELECT * FROM tbl_ems WHERE ems_seq = #{ems_seq} ")
	public EmailVO findBySeq(long ems_seq);
	@Select(" SELECT * FROM tbl_ems WHERE ems_from_email = #{ems_from_email} ")
	public List<EmailVO> findByFrom(String ems_from_email);
	@Select(" SELECT * FROM tbl_ems WHERE ems_to_email = #{ems_to_email} ")
	public List<EmailVO> findByTo(String ems_to_email);
	
	/*
	 * 매개변수가 2개 이상일 경우에는
	 * 반드시 @Param으로 변수 이름을 명시해주어야 한다.
	 */
	public List<EmailVO> findByFormAndTo(
								@Param("ems_from_email")String ems_from_email,
								@Param("ems_to_email")String ems_to_email); 
		@InsertProvider(value=EmailSQL.class, method="Ems_insert_sql")
		@SelectKey(
				keyProperty = "ems_seq",
				statement=" SELECT SEQ_EMS.NEXTVAL FROM DUAL ",
				resultType = Long.class,
				before = true)
	public int insert(EmailVO emailVO);
	@UpdateProvider(value=EmailSQL.class, method="Ems_update_sql")
	public int update(EmailVO emailVO);
	@Delete(" DELETE FROM TBL_EMS ")
	public int delete(long ems_seq);
	
}

