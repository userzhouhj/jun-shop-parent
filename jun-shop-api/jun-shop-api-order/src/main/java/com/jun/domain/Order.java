package com.jun.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

	private Long id;
	/**
	 * 用户userid
	 */
	private Long userId;
	/**
	 * 订单编号
	 */
	private String orderNumber;
	/**
	 * 0 未支付 1已支付
	 */
	private Integer isPay;
	/**
	 * 支付id
	 */
	private Long payId;
	private Date created;
	private Date updated;
}
