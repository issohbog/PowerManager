# 💻 소상공인 시간제 매장관리 시스템 (PowerManager MagicPOS)
<img width="859" height="482" alt="image" src="https://github.com/user-attachments/assets/32c324e7-0156-4c0f-8f0b-29b2b2b9db72" />

## 프로젝트 배경 
<img width="859" height="486" alt="image" src="https://github.com/user-attachments/assets/e2e3f0aa-115f-4692-a6a1-7f80b1dff2ec" />

---

## 📌 개요 (Overview)
- 이 프로젝트는 **좌석 관리, 요금제 관리, 상품 주문/결제 처리** 등을 통합적으로 관리할 수 있는 시스템입니다.  
- Spring Boot, MyBatis기반으로 개발하였으며, MySQL DB를 활용해 데이터 관리 및 트랜잭션을 처리합니다.  

---

## 👥 팀 구성 (Roles)
<img width="847" height="482" alt="image" src="https://github.com/user-attachments/assets/dd9f1e5f-4020-46cc-908c-9e6df9733589" />

---
## 개발 환경 
- 사용 언어
  html, javascript, css3, java
- 프레임 워크
  springBoot
- 사용 Tools
  vscode
- 사용 DB
  mysql
  
---

## ✨ 프로젝트 특징 (Highlights)
- ✅ **실시간 좌석 관리**: 사용자가 로그인하면 좌석 상태가 즉시 관리자 화면에 반영  
- ✅ **요금제 구매 및 잔여시간 관리**: 시간 단위 요금제 처리 및 자동 차감  
- ✅ **주문 시스템**: PC에서 사용자가 먹거리/음료 주문 → 관리자 화면에 알림 표시  
- ✅ **Spring Security 커스터마이징**: 좌석 로그인과 계정 로그인을 동시에 처리  
- ✅ **RESTful API 설계**: React와 Spring Boot 간 데이터 송수신 구조 확립  

---

## 🏗️ 시스템 설계 (Design)

<details>
  <summary>Flow Chart</summary>
  <img width="2500" height="2414" alt="화면이동흐름도" src="https://github.com/user-attachments/assets/b959dabb-f330-4263-8f14-eb7002a49111" />
</details>

<details>
  <summary>요구사항 정의서</summary>
  <img width="602" height="527" alt="image" src="https://github.com/user-attachments/assets/76e5e044-8b33-489d-8ccd-245429afd34e" />
</details>

<details>
  <summary>📊ERD</summary>
  <img width="2470" height="874" alt="소상공인 매장관리 시스템 (2)" src="https://github.com/user-attachments/assets/6483388e-3baa-43f3-9b4e-fe596861f1f1" />
</details>



[Figma 화면설계 바로가기](https://www.figma.com/design/6iwS9UY0lueFwxBOsccpAJ/POSRanger?node-id=0-1&t=gfZO85VLxMjnhKrK-1)




## 🚀 주요 기능 (Features)
### 사용자 (User)
- 로그인 후 좌석 자동 배정 및 요금제 차감
- 상품(음식/음료) 주문
- 남은 시간 확인 및 추가 충전

### 관리자 (Admin)
- 실시간 좌석 상태 모니터링
- 요금제/회원 관리
- 주문 처리 및 매출 통계 확인
- 상품 등록/수정/삭제

---

## 🙌후기(Retrospective)
- 프로젝트 진행 중 **Spring Security와 React 연동**에서 인증/인가 이슈를 겪었으나, Custom Filter를 구현하며 해결하였습니다.  
- ERD 설계 시 좌석/요금제/주문 관계를 정리하면서 데이터베이스 정규화에 대해 깊이 이해할 수 있었습니다.  
- 협업 과정에서 GitHub Flow 전략을 사용하여 **브랜치 관리와 충돌 해결** 경험을 쌓을 수 있었습니다.  
- 실제 현업에서의 POS/ERP 시스템 구조를 접목해보며, **서비스 운영을 고려한 백엔드 아키텍처**에 자신감이 생겼습니다.  

---

## 📌 추가 (Optional)
- 📷 실행 화면 스크린샷 (로그인 화면, 좌석 관리 화면, 주문 알림 등)  
- 🛠️ 설치/실행 방법 (clone → backend 실행 → frontend 실행)  
- 📄 라이선스 (MIT, Apache 등 선택 가능)  
