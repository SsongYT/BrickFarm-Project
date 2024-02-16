<div align = center><img src="https://capsule-render.vercel.app/api?type=waving&color=auto&height=180&section=header&text=BrickFarm&fontSize=90"/></div>
<div align = right>
<h5>📅 2023.10.4 ~ 2023.11.30</h5>
</div>
<br/>
<br/>
<h1>📌Languages/Framework</h1>
<div align = center>
<img src="https://img.shields.io/badge/Java-007396?style=flat&logo=Java&logoColor=white"/>
<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=HTML5&logoColor=white"/>
<img src="https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=CSS3&logoColor=white"/>
<img src="https://img.shields.io/badge/Javascript-F7DF1E?style=flat&logo=Javascript&logoColor=white"/>
<img src="https://img.shields.io/badge/jQuery-0769AD?style=flat&logo=jQuery&logoColor=white"/>
<img src="https://img.shields.io/badge/Ajax-0099E5?style=flat&logo=Ajax&logoColor=white"/>
<br/>
<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=Spring&logoColor=white"/>
<img src="https://img.shields.io/badge/JSP-512BD4?style=flat&logo=JSP&logoColor=white"/>
<img src="https://img.shields.io/badge/Oracle-F80000?style=flat&logo=Oracle&logoColor=white"/>
<img src="https://img.shields.io/badge/MySql-4479A1?style=flat&logo=MySql&logoColor=white"/>
<img src="https://img.shields.io/badge/MariaDB-003545?style=flat&logo=MariaDB&logoColor=white"/>
</div>
<br/>
<br/>
<h1>📌Software</h1>
<div align = center>
<img src="https://img.shields.io/badge/Notion-000000?style=flat&logo=Notion&logoColor=white"/>
<img src="https://img.shields.io/badge/Figma-F24E1E?style=flat&logo=Figma&logoColor=white"/>
<img src="https://img.shields.io/badge/GoogleSheets-34A853?style=flat&logo=GoogleSheets&logoColor=white"/>
<img src="https://img.shields.io/badge/Git-F05032?style=flat&logo=Git&logoColor=white"/>
<img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white"/>
<br/>
<img src="https://img.shields.io/badge/NaverAPI-03C75A?style=flat&logo=Naver&logoColor=white"/>
<img src="https://img.shields.io/badge/KakaoAPI-FFCD00?style=flat&logo=Kakao&logoColor=white"/>
<img src="https://img.shields.io/badge/Eclipse IDE-2c2255?style=flat&logo=EclipseIDE&logoColor=white"/>
<img src="https://img.shields.io/badge/Apache Tomcat-F8DC75?style=flat&logo=apachetomcat&logoColor=white"/>
</div>
<br/>
<br/>
<h1>📌개념적ERD & 공학적ERD</h1>
<p align="center">
<img width="100%" alt="개념적ERD" src="https://github.com/SsongYT/BrickFarm-Project/assets/136442036/08d7a97f-0174-49e3-97af-c2da6e60eab8"/>
</p>
<br/>
<br/>
<h1>📌공학적ERD</h1> 
<p align="center">
<img width="100%" alt="공학적ERD" src="https://github.com/SsongYT/BrickFarm-Project/assets/136442036/bb03cd58-2a29-49d9-810f-41869f2a2bb7"/>
</p>
<br/>
<br/>
<h1>📌사이트맵</h1>
<p align="center">
<img width="100%" alt="사이트맵" src="https://github.com/SsongYT/BrickFarm-Project/assets/136442036/46495df8-4652-4e92-b668-54b0115e0bda"/>
</p>
<br/>
<br/>
<h1>🏆담당 : 결제 및 관리자페이지(주문)</h1>

>꼼꼼한 검증을 통한 결제
>
>부분취소 기능 구현
>
>일괄처리(다중처리)로 편리한 관리
<br/>
<h2>📍주요 DB테이블 소개</h2>
<p align="center">
<img width="100%" alt="테이블" src="https://github.com/SsongYT/BrickFarm-Project/assets/136442036/02fbf6b4-a407-4d8b-86ed-ae6737df6379"/>
</p>
    
>부분취소 기능을 구현하기위해 ordersheet에 각 세부상품이 있는 detailed_order 테이블로 구분하여 구현하고
>
>그 후 취소/교환/반품/구매확정을 detailed_order 테이블에서 구현되도록 설계하여 테이블을 구성하였다.
>
>적립금은 상품별로 사용하도록 만들면 편의성이 떨어지고 균등하게 분배하면 취소/반품시 문제가 있다 판단하여
>
>취소/반품시 코드적으로 이부분을 계산하여 결제금액을 반환하도록 하였다.
<br/>
<h2>📍결제 검증</h2>
<p align="center">
<img width="100%" alt="결제 검증" src="https://github.com/SsongYT/BrickFarm-Project/assets/136442036/4f483ac5-9017-4a5a-8c64-a3a3b2276004"/>
</p>

> 결제 API를 사용하여 결제 후에 데이터 위변조를 방지하기 위해 DB에서 검증 데이터를 가져와서
>
> controller와 service에서 추가적인 검증을 실시하고,
>
> 실패 시에는 errorCode를 반환하고 출력하여 구별이 가능하도록 구현하였습니다.
<br/>
<h3>💡코드 분석</h3>

<h4>💣Controller</h4>

```java
@PostMapping(value="orderCompletePage") 
public void inputCompleteOrder(@ModelAttribute UserOrdersheetDTO ordersheet, @ModelAttribute UserPaymentDTO payment,
@ModelAttribute UserCompleteDataDTO completeData, @RequestParam List<String> product_code,
@RequestParam List<Integer> quantity, HttpServletRequest request, Model model) {
  //세션 정보를 통해 멤버 정보 받아오기
  UserMemberVO sessionMember = (UserMemberVO)request.getSession().getAttribute("loginMemberInfo");
  List<UserPaymentListDTO> paymentList = new ArrayList<UserPaymentListDTO>();

  for (int i = 0; i < product_code.size(); i++) {
    UserPaymentListDTO paymentData = new UserPaymentListDTO(product_code.get(i), quantity.get(i));
    paymentList.add(paymentData);
  }
		
  try {
    UserMemberVO member = paymentService.getMember(sessionMember.getMember_no());
    Map<String, Object> result = paymentService.makeCompleteOrderByDataToUse(ordersheet, payment, completeData, paymentList, member);	
    String errorCode = (String)result.get("errorCode");
    if(errorCode == "000") {
      UserOrdersheetDTO ordersheetData = (UserOrdersheetDTO)result.get("ordersheet");
      UserPaymentDTO paymentData = (UserPaymentDTO)result.get("payment");
      List<UserOrderProductDTO> orderProductList = (List<UserOrderProductDTO>)result.get("orderProductList");
      int total_price = (int)result.get("total_price");
      int coupon_discounted_price = (int)result.get("coupon_discounted_price");
		
      model.addAttribute("ordersheet", ordersheetData);
      model.addAttribute("payment", paymentData);
      model.addAttribute("orderProductList", orderProductList);
      model.addAttribute("paymentType", completeData.getPaymentType());
      model.addAttribute("total_price", total_price);
      model.addAttribute("coupon_discounted_price", coupon_discounted_price);
    } 
    // 검증 실패에 대한 에러코드 (쿠폰,포인트,재고,결제검증)
    model.addAttribute("errorCode", errorCode);

  } catch (Exception e) { // 검증과 상관없이 DB정보 저장시 Exception에러코드
    e.printStackTrace();
    Map<String, Object> errorResponse = new HashMap<String, Object>();
    errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
    errorResponse.put("errorMessage", "시스템에러");	
    int errorCode = 999;
		
    model.addAttribute("errorCode", errorCode);
    model.addAttribute("errorResponse", errorResponse);
  }
}
```

<h4>💣Service</h4>

```java
// 재고,쿠폰보유,포인트보유 검증
@Override
public String verifyAll(int member_no, int point_pay_money, int coupon_held_no, List<UserPaymentListDTO> paymentList) 
throws Exception {
  String result = null;
  int intVerify = 9000;
  int verifyQuantity = 1;
  int verifyCoupon = 1;
  int verifyPoint = 1;
		
  // 재고 검증 (성공 0 , 실패 음수)
  if (stockDao.isVerifyStock(paymentList) != 0) { 
    verifyQuantity = -1;
    intVerify += 200;
  } 
		
  // 쿠폰 검증 (성공 1 , 실패 0)
  if (coupon_held_no != -1) {
    if(memberDao.verifyCoupon(member_no, coupon_held_no) != 1) {
      verifyCoupon = -1;	
      intVerify += 20;
    } 		
  } 
		
  // 포인트 검증 (성공1, 실패 -1)
  if (point_pay_money > 0) {
    if(memberDao.verifyPoint(member_no, point_pay_money) != 1) {
      verifyPoint = -1;
      intVerify += 2;
    }		
  } 
		
  // 완전 검증
  if (verifyQuantity == 1 && verifyCoupon == 1 && verifyPoint == 1) {
    result = "000";
  } else {
    String verify = Integer.toString(intVerify);
    result = verify.substring(verify.length()-3, verify.length());
  }
		
  return result;
}

//----------------------------------------------------------------------------------------------------------------
//결제금액DB에서 계산
@Override
public int getValidationPrice(List<UserPaymentListDTO> paymentList, int coupon_held_no, int post_money, int point_pay_money) throws Exception {		
  int totalPrice = detailedOrderDao.getValidationPrice(paymentList);
  float couponDiscountRate = 0;
  if (coupon_held_no != -1) {
    couponDiscountRate = couponHeldDao.getValidationRate(coupon_held_no);
  }

  int validationPrice = totalPrice - (Math.round(totalPrice * couponDiscountRate)) + post_money - point_pay_money;

  return validationPrice;
}

//----------------------------------------------------------------------------------------------------------------
//결제 검증
public IamportResponse<Payment> verifyPayment(int pay_money, String imp_uid) throws IamportResponseException, IOException {
  IamportResponse<Payment> verifyPayment = null;
  IamportResponse<Payment> responsePayment = paymentByImpUid(imp_uid);
  //DB데이터로 계산한 결제금액과 요청된 결제금액 비교
  if(responsePayment.getResponse().getAmount().intValue() == pay_money) {
    verifyPayment = responsePayment;
  }
  return verifyPayment;
}

//----------------------------------------------------------------------------------------------------------------
//survice 단에서 검증후 처리
@Override
public Map<String, Object> makeCompleteOrderByDataToUse(UserOrdersheetDTO ordersheet, UserPaymentDTO payment,
UserCompleteDataDTO completeData, List<UserPaymentListDTO> paymentList, UserMemberVO member) throws Exception {
  Map<String, Object> result = new HashMap<String, Object>();
  String errorCode = "000";
  // 재고,쿠폰,적립금 검증
  String verifyCode = verifyAll(member.getMember_no(), payment.getPoint_pay_money(), ordersheet.getCoupon_held_no(), paymentList);
  if (verifyCode.equals("000")) {	//검증성공
  // 검증을 위한 금액 계산 ( 밑에 100대신 validationPrice )
    int validationPrice = getValidationPrice(paymentList, ordersheet.getCoupon_held_no(), payment.getPost_money(), payment.getPoint_pay_money());
    // 결제액이 0원이 아닐때 결제API 검증을함.
    if(completeData.getPay_money() != 0) {
      // 검증함수 호출하여 리턴 값 받아오기
      IamportResponse<Payment> responsePayment = PaymentInfo.getInstance().verifyPayment(validationPrice, payment.getImp_uid());
      if (responsePayment != null) {

        ...결제 성공시 로직 수행...

      } else if(responsePayment == null) { // 결제 검증 실패
        //카드결제 취소
        if (completeData.getPaymentType().equals("card")) {
          BigDecimal cancel_request_amount = new BigDecimal(completeData.getPay_money());
          BigDecimal checksum = new BigDecimal(completeData.getPay_money());
          CancelDataDTO cancelData = new CancelDataDTO(payment.getImp_uid(), cancel_request_amount, "검증실패", checksum);
          PaymentInfo.getInstance().cancelPaymentByImpUid(cancelData);	
        }

        errorCode = "001";
        result.put("errorCode", errorCode);
      }
    } else if(completeData.getPay_money() == 0){

      ...결제 금액 0원일때 로직...

    }

  } else if(!verifyCode.equals("000") { // 유효성 3가지 중 1개라도 실패
    if (completeData.getPaymentType().equals("card")) {
      BigDecimal cancel_request_amount = new BigDecimal(completeData.getPay_money());
      BigDecimal checksum = new BigDecimal(completeData.getPay_money());
      CancelDataDTO cancelData = new CancelDataDTO(payment.getImp_uid(), cancel_request_amount, "검증실패", checksum);
      PaymentInfo.getInstance().cancelPaymentByImpUid(cancelData);
    }
    errorCode = verifyCode;
    result.put("errorCode", errorCode);

  }
  return result;
}
```
</br>
<h3>💥설명</h3>

> 에러 코드를 검증에 따라 백의 자리, 십의 자리, 일의 자리로 구별하고 복합적인 문제도 표현하고 싶었지만
> 
> 경우의 수가 많아져서 뷰 코드가 길어지는 문제가 발생했습니다.
> 
> 이를 깔끔하게 처리하고 싶었지만, 3가지의 유효성 검증을 1가지로 출력하게 되었습니다.
> 
> 이 문제를 해결하기 위해 Enum 클래스를 활용하여 체계적이고 전략적으로 에러 및 예외를 관리하고자 합니다.

***
<br/>
<br/>
<h2>📍결제 취소</h2>
<p align="center">
<img width="100%" alt="결제 취소" src="https://github.com/SsongYT/BrickFarm-Project/assets/136442036/39eda5d7-3c30-406b-9c0d-940b9da1a64a"/>
</p>

> 부분취소를 구현하였으며 이를 통해 다양한 문제를 처리했습니다.
>
> 배송비는 과실 여부를 고려하여 결정되며, 이후 무료배송 기준을 충족하는지를 확인하여
>
> 취소 요청 금액에서 해당 배송비를 제외하도록 구현했습니다.
>
> 또한, 실제 카드 또는 현금으로 결제된 금액과 사용된 적립금을 고려하여 우선적으로 실제 지불한 금액을 반환하고,
>
> 이를 모두 사용한 경우에는 적립금을 반환하는 형태의 로직을 구현했습니다.
<br/>
<h3>💡코드 분석</h3>

<h4>💣Service</h4>

```java
// 결제 취소 관련 메소드(공용화)-일괄처리 가능하도록 List로 진행
@Transactional(rollbackFor = Exception.class)
private Map<String, Object> cancelPayment(List<Integer> pkNumberList) throws Exception {
	Map<String, Object> result = new HashMap<String, Object>();
	// 취소 성공 List
	List<Integer> successList = new ArrayList<Integer>();
	// 취소 실패 List
	List<AdminCancelFailDTO> failList = new ArrayList<AdminCancelFailDTO>();
	List<AdminCancelReturnDTO> cancelReturnList = new ArrayList<AdminCancelReturnDTO>();

	for (int i = 0; i < pkNumberList.size(); i++) {
		// 취소 데이터 수집
		AdminCancelDataDTO AdminCancelData = cancellationReturnDao.getCancelData(pkNumberList.get(i));
		// 카드+현금 가격
		int check_pay = AdminCancelData.getChecksum();
		// 취소,반품 취소신청 금액
		int cancel_pay = AdminCancelData.getCancel_request_amount();
		// 총결제금액 - 취소 요청금액 (계속 무료배송이 유지되는지 확인)
		int postYN = AdminCancelData.getTotal_pay_money() - AdminCancelData.getCancel_request_amount();
		// 과실 여부에 따라 배송비 측정
		if (AdminCancelData.getWhat().equals("반품") && AdminCancelData.getNegligence().equals("N")) {
			// 소비자 과실 (택배비 발생)
			int postMoney = 2500;
			// 총결제금액 - 취소요청금액을 했을시 배송비 무료기준에 부합하는지
			if(postYN >= 50000) {
				// 무료기준 부합 -> (편도) 배송비 적용
				AdminCancelData.setAdd_post_money(postMoney);
				cancel_pay = cancel_pay - postMoney;
			} else if(postYN < 50000) {
				if (AdminCancelData.getPost_money() == 0) {
				// 기존에 배송비 없이 배송 -> (왕복) 배송비 적용
					AdminCancelData.setAdd_post_money(2 * postMoney);
					cancel_pay = cancel_pay - (2 * postMoney);
				} else if (AdminCancelData.getPost_money() != 0) {
					// 기존에 배송비 결제
					// (편도) 배송비 적용
					AdminCancelData.setAdd_post_money(postMoney);
					cancel_pay = cancel_pay - postMoney;
				}
			}					
		}

		int point_amount = 0;
		int cancel_amount= 0;
		// 카드&현금 or 적립금 취소금액 계산
		if (cancel_pay > check_pay) { 
			// 적립금 취소 금액  
			point_amount = cancel_pay - check_pay;
			// 카드or현금 취소 금액
			cancel_amount = check_pay;
		} else if (cancel_pay <= check_pay) {
			// 카드or현금 취소 금액
			cancel_amount = cancel_pay;
		}
		// 카드or현금 금액 설정 
		AdminCancelData.setChange_cancel_amount(cancel_amount);
		// 변경된 적립금 금액 설정
		AdminCancelData.setChange_point_pay_money(point_amount);

		if (AdminCancelData.getPay_method().equals("카드")) {
			// 결제 취소 API
			// 결제 처리에 필요한 DTO 생성
			CancelDataDTO cancelData = new CancelDataDTO(AdminCancelData.getImp_uid(), new BigDecimal(AdminCancelData.getCancel_request_amount()),
			AdminCancelData.getReason(), new BigDecimal(AdminCancelData.getChecksum()));
			// 결제 API 호출하여 취소진행
			IamportResponse<Payment> response = PaymentInfo.getInstance().cancelPaymentByImpUid(cancelData);

			if (response.getCode() == 0) {
				// 취소 성공
				if (paymentDao.updatePaymentOnCancel(AdminCancelData) == 1) {
					// 적립금반환이 있다면 적립금 반환로그 작성 및 멤버 적립금 반환
					if(AdminCancelData.getChange_point_pay_money() != 0) {
						if(pointsUsageLogDao.insertCancelPoint(AdminCancelData) == 1) {
							if(memberDao.updateCancelPoint(AdminCancelData) != 1) {
								// 실패시 로직
								throw new Exception();
							}
						} else {
							throw new Exception();
						}
					}		
					// 성공 리스트에 담기
					successList.add(AdminCancelData.getPk_number());
					// payment 테이블 가격들 업데이트
					Timestamp now = new Timestamp(System.currentTimeMillis());
					AdminCancelReturnDTO cancelReturnData = new AdminCancelReturnDTO(AdminCancelData.getPk_number(),
					"완료", null, now, AdminCancelData.getAdd_post_money());
					cancelReturnList.add(cancelReturnData);
				} else {
					throw new Exception();
				}
			} else if (response.getCode() == 1) {
				// 취소 실패 리스트에 담기
				AdminCancelFailDTO fail = new AdminCancelFailDTO(AdminCancelData.getPk_number(), response.getMessage());
				failList.add(fail);
			}
		} else if (AdminCancelData.getPay_method().equals("현금")) {
			// 현금결제(무조건 성공)
			if (paymentDao.updatePaymentOnCancel(AdminCancelData) == 1) {
				// 적립금반환이 있다면 적립금 반환로그 작성 및 멤버 적립금 반환
				if(AdminCancelData.getChange_point_pay_money() != 0) {
					if(pointsUsageLogDao.insertCancelPoint(AdminCancelData) == 1) {
						if(memberDao.updateCancelPoint(AdminCancelData) != 1) {
							//실패시 로직
							throw new Exception();
						} 	
					} else {
						throw new Exception();
					}
				}
				// 성공 리스트에 담기
				successList.add(AdminCancelData.getPk_number());
				// payment 테이블 가격들 업데이트
				Timestamp now = new Timestamp(System.currentTimeMillis());
				AdminCancelReturnDTO cancelReturnData = new AdminCancelReturnDTO(AdminCancelData.getPk_number(),
				"완료", null, now, AdminCancelData.getAdd_post_money());
				cancelReturnList.add(cancelReturnData);
			} else {
				throw new Exception();
			}
		}
	}
	result.put("successList", successList);
	result.put("failList", failList);
	result.put("cancelReturnList", cancelReturnList);

	return result;
}

//--------------------------------------------------------------------------------------------------------------------------------
// 취소완료후 DB에 데이터 저장하는 로직구현
@Override
@Transactional(rollbackFor = Exception.class)
public Map<String, Object> changeStateReturnByComplete(List<Integer> returnNoList) throws Exception {
	Boolean verify = false;
	Map<String, Object> failListMap = new HashMap<String, Object>();

	// 취소 공용 메서드 갔다오기
	Map<String, Object> result = cancelPayment(returnNoList);

	List<Integer> successList = (List<Integer>) result.get("successList");
	List<AdminCancelFailDTO> failList = (List<AdminCancelFailDTO>) result.get("failList");
	List<AdminCancelReturnDTO> returnList = (List<AdminCancelReturnDTO>) result.get("cancelReturnList");

	// 취소/반품테이블(한번에) 업데이트 returnList= 성공한애들 상태변경에 맞게 객체화
	if (successList.size() > 0) {
		// 반출테이블 적용
		if(carryinOut(successList, "반품")) {
			// 상태 완료로 변경
			if (cancellationReturnDao.updateCancelReturnByStateComplete(returnList, "반품") == returnList.size()) {
				// 정보 검증하러 바로가기 
				List<String> compareMerchantUidList = ordersheetDao.compareCompleteStateCancelReturn(successList);
				if (compareMerchantUidList.size() > 0) {
					verify = verifyTotalState(compareMerchantUidList);
				} else {
					// DB 저장은 성공했지만, 검증에 통과한것이 0개이다.
					verify = true;
				}
			}
		}			
	} else {
		// API 취소 성공한것이 한개도 없다.
		verify = true;
	}
		
	if(verify) {
		failListMap.put("failList", failList);
	} else {
		throw new Exception();
	}

	return failListMap;
}
```
</br>
<h3>💥설명</h3>

> 부분 취소를 구현하는 과정에서 설계가 충분히 깔끔하지 못해 불필요한 컬럼이 추가되었고,
>
> 실제 필요한 컬럼이 부족하여 쿼리를 조합할 때 어려움이 있었습니다.
>
> 또한 API 서버와 데이터베이스 간 불일치에 대한 처리도 제대로 이루어지지 않았습니다.
>
> 부분 취소를 구현하면서 예상치 못한 문제가 발생했고, 이를 통해 로직의 복잡성을 체감하며
>
> 중간에 수정이 필요할 경우 코드 수정이 번거로워지는 점을 깨달았습니다.
>
> 이러한 경험을 통해 설계의 중요성을 다시 한 번 깨달았습니다.
***
<br/>
<br/>
<h2>📍상태 확정</h2>
<p align="center">
<img width="100%" alt="상태 확정" src="https://github.com/SsongYT/BrickFarm-Project/assets/136442036/eb8432a8-8856-45f7-8c43-a833325ab220"/>
</p>

> detailed_order들의 상태가 모두 완료가 되면 ordersheet의 최종상태가 완료로 변경되고
>
> 적립금 지급을 하도록 설계를 하게 되었다.
>
> 1개의 detailed_order의 상태가 완료상태(취소완료,교환완료,반품완료,구매확정)가 되었을때
>
> 같은 ordersheet에 묶여있는 모든 detailed_order의 상태를 확인하고
>
> 그 모든 상태가 완료가 되었을시 적립금 지급 및 최종상태 변경을 하도록 구현하였다.
<br/>
<h3>💡코드 분석</h3>

<h4>💣Mapper</h4>

> DB에서 데이터를 가져온 후 Survice단에서 비교를 하는 작업은 비효율적이라 생각하여
>
> mybatis의 동적쿼리를 이용하여 쿼리에서 비교 후 조건에 맞는 merchant_uid를
>
> 반환하도록 쿼리를 구현하여 해결하였다.

```java
<select id="compareCompleteStateByUid" resultType="String">
SELECT B.merchant_uid 
FROM 
	(SELECT COUNT(*) AS complete, D.merchant_uid 
	FROM detailed_order D 
		LEFT OUTER JOIN exchange E 
			ON D.detailed_order_no = E.detailed_order_no 
		LEFT OUTER JOIN cancellation_return CR 
			ON D.detailed_order_no = CR.detailed_order_no 
	WHERE D.merchant_uid 
		IN 
		<foreach collection="merchantUidList" item="item" open="(" close=")" separator=",">
		#{item}
		</foreach>
		AND ((D.payment_state IN ("구매확정")) 
			OR (D.payment_state IN ("취소","반품") AND CR.state = "완료") 
			OR (D.payment_state IN ("교환") AND E.state = "완료")) 
	GROUP BY D.merchant_uid) A 
	INNER JOIN 
	(SELECT COUNT(*) AS total, D.merchant_uid 
	FROM detailed_order D
	WHERE D.merchant_uid
		IN 
		<foreach collection="merchantUidList" item="item" open="(" close=")" separator=",">
		#{item}
		</foreach>
	GROUP BY D.merchant_uid) B 
		ON A.merchant_uid = B.merchant_uid 
		AND A.complete = B.total 
GROUP BY B.merchant_uid
</select>
```
<h4>💣Service</h4>

```java
// 최종 완료 상태로 변경해야하는 merchant_uid 변경하는 메서드
@Transactional(rollbackFor = Exception.class)
private boolean verifyTotalState(List<String> compareMerchantUidList) throws Exception {
	boolean result = false;
	Timestamp now = new Timestamp(System.currentTimeMillis());
	// 주문서의 최종상태 변경
	if (ordersheetDao.updateOrderSheetOnState(compareMerchantUidList, now) == compareMerchantUidList.size()) {
		// 결제액 0원이 아닌 List
		List<String> haveToMerchantUidList = paymentDao.selectPaymentMoney(compareMerchantUidList);
		// 포인트 적립로그에 적립
		if(haveToMerchantUidList.size() > 0) {
			if (pointsAccrualLogDao.insertPointAccrualLogByCompletion(haveToMerchantUidList) == haveToMerchantUidList.size()) {
				int count = 0;
				for(String haveToMerchantUid : haveToMerchantUidList) {
					count += memberDao.updateMemberOnAccumulate(haveToMerchantUid);
				}
				if(count == haveToMerchantUidList.size()) {
					result = true;
				}
			} 
		} else {
			result = true;
		}
	}
	return result;
}

//--------------------------------------------------------------------------------------------------------------------------------------
// 취소완료 상태 변경
@Override
@Transactional(rollbackFor = Exception.class)
public boolean changeStateExchangeByComplete(List<AdminExchangeDTO> exchangeList, List<Integer> exchangeNoList) throws Exception {
	boolean result = false;
	// 반출테이블 적용
	if(carryinOut(exchangeNoList, "교환")) {
		// 교환 상태 변경
		if (exchangeDao.updateExchangeByStateComplete(exchangeList) == exchangeList.size()) {
			// 모든 검증하러 바로가기
			List<String> compareMerchantUidList = ordersheetDao.compareCompleteStateExchange(exchangeNoList);
			if (compareMerchantUidList.size() > 0) {
				result = verifyTotalState(compareMerchantUidList);
			} else {
				result = true;
			}	
		} 
	}
	if(result == false) {
		throw new Exception();
	}
	return result;
}
```
</br>
<h3>💥설명</h3>

> 첫 테이블 설계 이후 다른 담당자들의 기능 구현에 어려움을 겪으면서 테이블 수정을 하다 보니
>
> 최종 상태라는 컬럼이 생기게 되었고 이에 따라 적립금 지급도 각각의 detailed_order에서
>
> 진행하던것을 최종 상태를 기준으로 하게 되었다.
>
> 처음 설계 단계부터 만들어진게 아니다 갑자기 변경되어서 코드가 지저분하고 쿼리도 복잡해 진것 같다.
>
> 설계 단계에서 부터 고려하여 설계를 하였다면 불필요한 컬럼(total_state 등)도 줄어들고
>
> 또한 쿼리 및 Service단도 깔끔해 졌을것 같다.
***
</br>
</br>
<h2>📍관리자 일괄처리</h2>
<p align="center">
<img width="100%" alt="상태 확정" src="https://github.com/SsongYT/BrickFarm-Project/assets/136442036/dca08449-9382-428c-9f9d-16bef00dc127"/>
</p>

> 관리자 페이지에서 상태변화를 일괄처리가 가능하도록 구현하여 사용자의 이용 편의성을 향상시켰으며,
>
> 상태 변경을 되돌리는 기능도 가능하도록 구현했습니다.
>
> 다만, 결제 API와 관련된 사항은 불가능하기 때문에 이러한 상황이 발생하지 않도록 뷰에서 유효성 검사를 진행했습니다.

<h3>💡코드 분석</h3>

<h4>💣JS</h4>

```java
//디테일 Toastr
function detailErrorToastr(state) {
  let errorMsg = "";
  let errorTitle = "";
  if (state == "stateCompleteVerify") {
    errorMsg = "완료상태가 아닌 정보로 </br>시도해주세요.";
    errorTitle = "완료상태 변경불가";
  } else if (state == "cancel") {
    errorMsg = "배송된 상품은 취소를 할수없습니다.";
    errorTitle = "취소 신청불가";
  } else if (state == "exchangeReturn") {
    errorMsg = "배송되지않은 상품은 교환/반품을 할수없습니다.";
    errorTitle = "교환/반품 신청불가";
  } else if (state == "completeActive") {
    errorMsg = "배송되지않은 상품은 구매확정을 할수없습니다.";
    errorTitle = "구매확정불가";
  } else if (state == "inputVerify") {
    errorMsg = "사유를 입력해주세요.";
    errorTitle = "사유정보 미입력";
  } else if (state == "inputVerifyBefore") {
    errorMsg = "입금자명을 입력해주세요.";
    errorTitle = "입금자명 미입력";
  } else if (state == "postNoVerify") {
    errorMsg = "운송장번호를 입력해주세요.";
    errorTitle = "운송장 미입력";
  } else if (state == "stateProcessVerify") {
    errorMsg = "먼저 진행중으로 변경해주세요.";
    errorTitle = "완료 상태 변경불가";
  }
  

  toastr.error(errorMsg, errorTitle, {
    positionClass: "toast-top-full-width",
    timeOut: 5e3,
    closeButton: !0,
    debug: !1,
    newestOnTop: !0,
    progressBar: !0,
    preventDuplicates: !0,
    onclick: null,
    showDuration: "300",
    hideDuration: "1000",
    extendedTimeOut: "1000",
    showEasing: "swing",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut",
    tapToDismiss: !1,
  });
}
```

***
</br>
</br>
<h2>📋총평</h2>

> 결제 부분에 대한 설계가 미흡하여 테이블 및 컬럼 구성에 문제가 있었고, 이로 인해 기능 구현이 어려웠습니다.
>
> 예를 들어, 결제수단을 구분하는 컬럼이 없어서 쿼리에서 AS절을 사용하여 추가해야 했으며,
>
> 결제 상태를 표시하는 컬럼도 취소 요청 시 결제 상태가 "취소"로 변경되어 결제 후 취소인지 결제 전 취소인지
>
> 구별할 수 없게 되었습니다. 이 문제를 해결하기 위해 결제 시간과 취소 신청 시간을 비교하여 구현하는 방법을 사용했습니다.
>
> 이러한 설계 단계의 미흡함을 발견했을 시엔 프로젝트 진행이 너무 많이 되어있어 확인해야할 코드의 양이 많아서
>
> 테이블 구조변경을 하지 않고 문제를 해결해야했기 때문에 코드가 복잡해진것 같습니다.
>
> 기능을 구현하기 위해 추가된 테이블의 정보가 공유되지 않아 수정할 시간이 촉박해지고,
>
> 테이블 구조에 대한 오해로 인해 불필요한 작업을 하게 되었습니다.
>
> 이러한 경험을 통해 설계의 중요성과 작은 일이라도 빠르게 소통하는 것이 업무 효율을 높일 수 있음을 깨달았습니다.
