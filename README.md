# 💻 소상공인 시간제 매장관리 시스템 (PowerManager MagicPOS)

---

## 📑 목차 (Table of Contents)
1. [개요 (Overview)](#-개요-overview)
2. [역할 분담 (Roles)](#-역할-분담-roles)
3. [프로젝트 특징 (Highlights)](#-프로젝트-특징-highlights)
4. [시스템 설계 (Design)](#-시스템-설계-design)
5. [주요 기능 (Features)](#-주요-기능-features)
6. [후기 (Retrospective)](#-후기-retrospective)


---

## 📌 개요 (Overview)
- 이 프로젝트는 **좌석 관리, 요금제 관리, 상품 주문/결제 처리** 등을 통합적으로 관리할 수 있는 시스템입니다.  
- Spring Boot, MyBatis기반으로 개발하였으며, MySQL DB를 활용해 데이터 관리 및 트랜잭션을 처리합니다.  



---

## 👥 역할 분담 (Roles)
- **장예지**: 
- **박도현**: 


---

## ✨ 프로젝트 특징 (Highlights)
- ✅ **실시간 좌석 관리**: 사용자가 로그인하면 좌석 상태가 즉시 관리자 화면에 반영  
- ✅ **요금제 구매 및 잔여시간 관리**: 시간 단위 요금제 처리 및 자동 차감  
- ✅ **주문 시스템**: PC에서 사용자가 먹거리/음료 주문 → 관리자 화면에 알림 표시  
- ✅ **Spring Security 커스터마이징**: 좌석 로그인과 계정 로그인을 동시에 처리  
- ✅ **RESTful API 설계**: React와 Spring Boot 간 데이터 송수신 구조 확립  

---

## 🏗️ 시스템 설계 (Design)
### 📂 아키텍처
- **Frontend**: React (Vite 기반), TailwindCSS  
- **Backend**: Spring Boot, MyBatis, Lombok  
- **Database**: MySQL 8.0  
- **Infra**: AWS EC2, Nginx  

### 📊 ERD
(여기에 ERD 이미지 첨부)

---

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
