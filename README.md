# 아파트 단지 내 배달로봇 주문 어플리케이션

이 프로젝트는 아파트 단지 내 배달로봇 서비스를 목적으로 진행되었습니다. 프로젝트 개발 부분에서 어플 제작부분만 담았습니다. 사용자 친화적인 UI와 효율적인 데이터 관리를 통해 배달 프로세스를 간소화하고, 사용자 경험을 향상시키는 데 초점을 맞췄습니다.

## 프로젝트 개요

- **프로젝트명**: 아파트 단지 배달로봇 주문 어플리케이션
- **진행 기간**: 2023.01.21 ~ 2023.04.20
- **주요 목표**:
  - 배달 서비스 주문 및 관리 기능 구현
  - 배달 도착 확인 기능 제공
  - 무인 배달 로봇과의 통신을 통해 배달 프로세스 자동화

## 주요 기능

1. **회원 관리**
   - 사용자 회원가입 및 로그인
   - 사용자 정보 관리

   ![image](https://github.com/user-attachments/assets/38cf9aaf-892f-47c6-99a5-b500d78fe4a8)


2. **주문 기능**
   - 장바구니에 물품 추가
   - 장바구니 조회 및 수정


   ![image](https://github.com/user-attachments/assets/42ecd417-7f4b-4fb2-8b4b-26b3c89c6e23)


   ![image](https://github.com/user-attachments/assets/8cc38a2d-96dd-457a-8ef6-d4f316d3aaa6)



3. **결제 기능**
   - 주문정보 보여주기
   - 가상으로 카드번호 입력 후 결제


   ![image](https://github.com/user-attachments/assets/e33659bc-2c14-4233-b329-998f731b6f1f)


4. **도착 알림 기능**
   - 도착 시 백그라운드 알림
   - 물품 찾고 확인 버튼을 누르면 배달로봇 복귀
  

   ![image](https://github.com/user-attachments/assets/46a881fe-339f-411f-949e-f9c99df0773a)


5. **관리자 기능**
   - 매출액 관리

   ![image](https://github.com/user-attachments/assets/c04307a3-7df2-48d3-aab4-45771e361071)


## 기술 스택

- **프론트엔드**: [ Android Studio, JAVA ]
- **백엔드**: [ Firebase ]
- **데이터베이스**: [ Firestore ]

## 폴더 구조

```
dex_order_application/
└── dexorderapplication_firebase_cart/
    └── app/src/main/java/com/example/dexorderapplication/
        ├── Activity/      # MainActivity, LoginActivity, RegisterActivity, CartListActivity,
        │                  # PaymentActivity, ManageActivity(관리자), OrderInformation, ShowDetailActivity
        ├── Adapter/       # 음료/과자/라면/장바구니 RecyclerView 어댑터
        ├── Domain/        # BeverageDomain, ItemDomain (상품 데이터 모델)
        ├── Helper/        # ManagementCart(장바구니 상태), TinyDB(로컬 저장)
        ├── Interface/     # ChangeNumberItemsListener 등 콜백 인터페이스
        └── MyService.java, MyBroadcastReceiver.java, NoticeArrival.java  # 도착 알림(백그라운드)
```

## 실행 방법

1. `dexorderapplication_firebase_cart/`를 Android Studio로 엽니다.
2. Firebase 프로젝트를 연결합니다 (`app/google-services.json`은 이미 포함되어 있으니, 본인 Firebase 프로젝트로 교체하려면 Firebase 콘솔에서 새로 받아서 덮어씁니다).
3. 에뮬레이터 또는 실기기에서 실행합니다. 회원가입 → 상품 탭(음료/과자/라면)에서 장바구니 추가 → 결제(가상 카드번호 입력) → 도착 알림까지 흐름을 확인할 수 있습니다.

## 참고

- `MainActivity_previous.java` / `activity_main_previous.xml`은 이전 버전 UI로, 지금은 `Activity/MainActivity.java`가 실제로 쓰이는 메인 화면입니다. 혼동 방지용으로 남겨두신 것 같은데, 더 이상 필요 없으면 지워도 되는 파일입니다.
- 실제 배달로봇(하드웨어/주행 제어) 부분은 이 저장소 범위 밖이고, 이 레포는 주문 어플 쪽만 담당합니다 (README 상단에도 명시되어 있음).
