<div align = center><img src="https://capsule-render.vercel.app/api?type=waving&color=auto&height=180&section=header&text=BrickFarm&fontSize=90"/></div>
<div align = right>
<h5>📅 2023.10.4 ~ 2023.11.30&emsp;&emsp;&emsp;&emsp;&ensp;</h5>
</div>📌📍📋&#128161;&#128163;&#128165;
<br/>
<br/>
<h2>📌Languages/Framework</h2>
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
<h2>📌Software</h2>
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
<h2>📌개념적ERD</h2>
<img width="900" alt="개념적ERD" src="https://github.com/SsongYT/BrickFarm-Project/assets/136442036/08d7a97f-0174-49e3-97af-c2da6e60eab8"/>

 <br/>
 <br/>
<h2>📌공학적ERD</h2> 
<img width="900" alt="공학적ERD" src="https://github.com/SsongYT/BrickFarm-Project/assets/136442036/bb03cd58-2a29-49d9-810f-41869f2a2bb7"/>

<br/>
<br/>
<h2>📌사이트맵</h2>
<img width="900" alt="사이트맵" src="https://github.com/SsongYT/BrickFarm-Project/assets/136442036/46495df8-4652-4e92-b668-54b0115e0bda"/>

<br/>
<br/>
<h2>🏆담당 : 결제 및 관리자-주문</h2>

>꼼꼼한 결제처리
>
>부분취소 기능 구현
>
>편리한 관리기능

<br/>
<br/>
<h3>📍주요 DB테이블 소개</h3>
<img width="900" alt="테이블" src="https://github.com/SsongYT/BrickFarm-Project/assets/136442036/02fbf6b4-a407-4d8b-86ed-ae6737df6379"/>
    
>전체상품의 부분취소 기능을 구현하기위해 ordersheet에 각 세부상품이 있는 detailed_order 테이블로 구분하여 구현하고
>
>그 후 취소/교환/반품/구매확정을 detailed_order 테이블에서 이루어지도록 설계하여 테이블을 구성하였다.
>
>적립금은 상품별로 사용하도록 만들면 고객 편의성이 떨어지게 되고 균등하게 분배하면 취소/반품시 문제가 있다고 판단하여
>
>취소/반품시 코드적으로 이부분을 계산하여 결제금액을 반환하도록 하였다.

<br/>
<br/>
<h3>📍결제 검증</h3>
<img width="900" alt="결제 검증" src="https://github.com/SsongYT/BrickFarm-Project/assets/136442036/4f483ac5-9017-4a5a-8c64-a3a3b2276004"/>

> javascript 에서 결제API를 이용한 결제 이후 script의 조작으로 데이터 위변조를 방지하기 위해
>
> DB에서 검증데이터를 가지고와 controller와 survice 에서 추후 검증을 실시하였고
>
> 실패시 errorCode를 반환하여 출력하도록 조치하였다.

<br/>
<br/>
<h4>&#128161;코드 분석</h4>

<h4>&#128163; Controller</h4>

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

<h4>&#128163; Survice</h4>

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
<h4>&#128165;평가</h4>

> errorCode를 재고문제시 백의자리, 쿠폰문제시 십의자리, 적립금문제시 일의자리로 표현하여 
>
> 복합적 문제도 표현하고 싶었는데 경우의수 다양성으로 뷰단에 코드가 길어지는 문제가 발생하였다.
>
> 이부분을 깔끔하게 처리하고 싶었지만 시간상의 문제로 구현하지 못하고
>
> 그냥 3가지 유효성문제를 유효성검증실패 1가지로 출력하게되었는데 
>
> 다음번에는 이부분을 HandlerExceptionResolver 인터페이스나 체계적ErrorResponse 파일을 만들어
>
> 보다 전략적으로 error 및 Exception 관리를 시도해보고 싶다.

***

<h3>📍결제 취소</h3>
<img width="900" alt="결제 취소" src="https://github.com/SsongYT/BrickFarm-Project/assets/136442036/39eda5d7-3c30-406b-9c0d-940b9da1a64a"/>
<h3>📍상태 확정</h3>
<img width="900" alt="상태 확정" src="https://github.com/SsongYT/BrickFarm-Project/assets/136442036/eb8432a8-8856-45f7-8c43-a833325ab220"/>
