package com.jun.dao;

import com.jun.domain.PaymentInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface PaymentInfoDao {

	@Select("select * from payment_info where  id=#{id}")
	public PaymentInfo getPaymentInfo(@Param("id") Integer id);

	@Insert("insert into payment_info (userid,typeid,orderid,platformorderid,price,source,state,created,updated) value(#{userId},#{typeId},#{orderId},#{platformorderId},#{price},#{source},#{state},#{created},#{updated})")
	@Options(useGeneratedKeys = true, keyProperty = "id") // 添加该行，product中的id将被自动添加
	public Integer savePaymentType(PaymentInfo paymentInfo);

	@Select("select * from payment_info where  orderid=#{orderId}")
	public PaymentInfo getByOrderIdPayInfo(@Param("orderId") String orderId);

	@Update("update payment_info set state=#{state},paymessage=#{payMessage},platformorderid=#{platformorderId} where orderid=#{orderId} ")
	public Integer updatePayInfo(PaymentInfo paymentInfo);
}
