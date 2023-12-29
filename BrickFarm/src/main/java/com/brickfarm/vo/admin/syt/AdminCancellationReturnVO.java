package com.brickfarm.vo.admin.syt;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AdminCancellationReturnVO {
	private int cancellation_return_no;
	private String cancel_money;
	private String depositYN;
	private String state;
	private String add_post_money;
	private Timestamp application_date;
	private Timestamp complete_date;
	private String reason;
	
	private String merchant_uid;
	private String member_id;
	private String member_name;
	private String product_name;
	private String product_main_image;
	private String discounted_price;
	private int quantity;
	private Timestamp order_day;
}
