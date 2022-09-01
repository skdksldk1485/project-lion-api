# LION SHOP

LION SHOP는 E-commerce형태의 웹 서비스를 구현한 쇼핑몰 프로젝트입니다.


## 🌏 사이트

<http://54.180.155.13:8080/> (2022.09.01부터 운영 중지 상태)


## ⏰ 개발 기간

- 2022.05 ~ 2022.05(3주)

## ⚙️ 기술 스택

- Front

  - Thymeleaf

- Back

  - Spring boot
  - Spring Sequrity
  - Spring Data Jpa
  - Java 11
  - MariaDB
  - Docker
  - AWS

## 🤓 기획 및 설계

![structure](https://user-images.githubusercontent.com/29578054/173249530-6ca84413-485a-44bc-bc04-3e300e14f460.png)

## 👩‍ DB ERD

![캡처11](https://user-images.githubusercontent.com/29578054/187077616-71991d9b-3bdd-4531-b267-ea9d27171cf4.PNG)

## ✨ 기능 구현 

### 1. 회원 가입 기능 구현 
 - 레이아웃 공통화 적용
 - 회원 정보 입력 후 회원 가입 성공 시 로그인 페이지로 이동
![img_7](https://user-images.githubusercontent.com/29578054/187985345-1ac03d21-0ec9-4a9e-ac82-9586f89e566b.png)
 - 동일한 이메일로 가입 시도 시 에러 메세지 화면 출력
 - 필수 입력 값 및 validation 조건을 통과 못할 경우 에러 메세지 화면 출력

### 2. 로그인 기능 구현
 - 이메일과 비밀번호 입력 후 로그인 성공 시 메인 페이지로 이동
 - 회원 가입 버튼 클릭 시 회원 가입 페이지로 이동
![img_3](https://user-images.githubusercontent.com/29578054/187985456-a3bbd327-9872-471f-8ce4-242bbb8378dd.png)

### 3. 상품 등록
- 상품 등록 시 상품 정보, 상품 이미지 정보 등록
- 상품 등록 후 상품 수정 페이지로 이동
![img_2](https://user-images.githubusercontent.com/29578054/187985640-e3242f44-bab0-49f2-a334-ef8972d477cc.png)


- 상품 정보 필수 입력 체크
- 첫번째 상품 이미지는 대표 이미지로 사용하기 위해서 필수 체크
<img width="948" alt="img_3 (1)" src="https://user-images.githubusercontent.com/29578054/187985734-73354d84-7400-447f-8cfb-9f4ed13336bf.png">


### 4. 상품 조회 (상품 수정 페이지)
![img_4](https://user-images.githubusercontent.com/29578054/187985823-4d025438-1476-48d4-b16d-ad03e45e3bf0.png)


### 5. 메인 페이지 상품 조회 구현
- 메인페이지에 한 페이지당 상품이 6개씩 노출되도록 구현
- 품절인 상품은 미노출 처리
![img_1](https://user-images.githubusercontent.com/29578054/187985999-60c74f2e-002e-428c-8b21-cc95818566b7.png)

- 오른쪽 상단 검색창에서 상품명/키워드로 검색 시 해당 키워드를 포함하고 있는 상품 리스트만 조회.
  - (상품상세 or 상품명 조회)
![img_2 (1)](https://user-images.githubusercontent.com/29578054/187986083-95fecf62-a6f2-4417-a859-4a824b2ff647.png)


### 6. 상품 상세 페이지 조회 구현
- 대표 이미지 (첫번째 이미지) 왼쪽 상단 노출
- 상품 판매 상태(판매중/품절) 조회
- 상품명 / 상품 가격 / 배송비 / 상품 상세 설명 조회
- 상품 상세 설명 하단에는 해당 상품의 이미지 나열
![img_3 (2)](https://user-images.githubusercontent.com/29578054/187986197-bd888272-1e06-437c-a346-563bc3ff3981.png)


- 품절 상태일 경우 주문하기 버튼 미노출
![img_4 (1)](https://user-images.githubusercontent.com/29578054/187986272-edd8578a-7ca2-47e2-bab1-30beaf08963e.png)


### 7. 주문 하기 구현
- 상품 상세 페이지에서 주문하기 버튼 클릭 시 주문 로직 수행 (ajax를 사용한 비동기 처리)
- 주문 성공 시 상품 주문 개수만큼 상품의 재고 차감
- 주문 성공 후 주문 이력 페이지 이동
- 주문 상태(OrderStatus)는 ORDER로 저장
- 주문 수량이 상품의 재고보다 많을 경우 주문 실패 처리
![img_5](https://user-images.githubusercontent.com/29578054/187986438-3fb81631-dc0c-4aec-9a32-08cac018519e.png)


### 8. 주문 이력 조회
- 현재 회원의 주문 이력 조회
- 한 페이지당 6개씩 조회하도록 구현
![img_6](https://user-images.githubusercontent.com/29578054/187986516-9ed15cd7-bec8-4bff-9e38-78cedb3f505f.png)


### 9. 주문 취소
- 주문 이력 페이지에서 주문 취소버튼 클릭 시 주문 취소 로직 수행
- 주문 취소 시 주문 개수만큼 상품의 재고를 증가
- 주문 상태(OrderStatus)는 CANCEL로 저장
![img_7 (1)](https://user-images.githubusercontent.com/29578054/187986592-6f0be96c-dfa9-4fe0-8611-0399497a63cd.png)

