# 키친포스

## 요구 사항
### 메뉴 그룹
* 메뉴 그룹을 생성할 수 있다.
* 메뉴 그룹을 조회할 수 있다.
### 메뉴
* 메뉴를 생성할 수 있다.
  * 메뉴는 등록된 메뉴 그룹에 속해야 한다.
  * 메뉴의 가격은 0원 이상이다.
  * 메뉴 상품은 모두 등록된 상품이어야 한다.
  * 메뉴의 가격은 메뉴 상품 가격의 합보다 작다.
* 메뉴를 조회할 수 있다.
### 주문
* 주문을 생성할 수 있다. 
  * 주문 테이블은 등록된 테이블이어야 한다.
  * 주문 항목 메뉴들은 등록된 메뉴여야 한다.
  * 주문 테이블은 빈 상태가 아니여야 한다.
* 주문의 상태를 수정할 수 있다.
  * 주문이 존재해야 한다.
  * 계산 완료된 주문이라면 수정할 수 없다.
* 주문을 조회할 수 있다.
### 상품
* 상품을 생성할 수 있다.
  * 상품의 가격은 0원 이상이다.
* 상품을 조회할 수 있다.
### 단체 지정
* 단체 지정을 생성할 수 있다.
  * 단체 지정된 테이블은 등록된 테이블이어야 한다.
  * 주문 테이블들이 2개 이상이어야 단체 지정이 가능하다.
  * 빈 주문 테이블만 단체 지정을 할 수 있다.
  * 주문 테이블이 다른 단체에 지정되지 않아야 한다.
* 단체 지정을 해제할 수 있다.
  * 주문 테이블들의 상태가 조리, 식사 상태라면 단체 지정을 해제할 수 있다. 
### 주문 테이블
* 주문 테이블을 생성할 수 있다.
* 주문 테이블을 조회할 수 있다.
* 주문 테이블은 빈 테이블로 변경할 수 있다.
  * 주문 테이블은 등록되어 있어야 한다.
  * 단체 지정된 주문 테이블은 변경할 수 없다.
  * 주문 테이블의 조리, 식사 중인 주문이 있으면 변경할 수 없다.
* 주문 테이블의 방문한 손님 수를 변경할 수 있다.
  * 변경하려는 손님 수는 0명 이상이어야 한다.
  * 존재하는 주문 테이블이어야 한다.
  * 빈 테이블은 변경할 수 없다.


## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |

----------------------------------------------------
## 1단계 테스트를 통한 코드 보호
### 요구 사항
* [ ] kitchenpos 패키지의 코드를 보고 키친포스의 요구 사항을 README.md에 작성
* [ ] 정리한 키친포스의 요구 사항을 토대로 테스트 코드를 작성
  * 모든 Business Object에 대한 테스트 코드를 작성
  * @SpringBootTest를 이용한 통합 테스트 코드 또는 @ExtendWith(MockitoExtension.class)를 이용한 단위 테스트 코드를 작성
* [ ] Lombok 없이 미션을 진행

### 힌트
* http 디렉토리의 .http 파일을 보고 어떤 요청을 받는지 참고
  * [IntelliJ의 .http를 사용해 Postman 대체하기](https://jojoldu.tistory.com/266)
```
###
POST {{host}}/api/menu-groups
Content-Type: application/json

{
  "name": "추천메뉴"
}

###
GET {{host}}/api/menus-groups

###
```
* src/main/resources/db/migration 디렉터리의 .sql 파일을 보고 어떤 관계로 이루어져 있는지 참고
```
id BIGINT(20) NOT NULL AUTO_INCREMENT,
order_table_id BIGINT(20) NOT NULL,
order_status VARCHAR(255) NOT NULL,
ordered_time DATETIME NOT NULL,
PRIMARY KEY (id)
```
* 키친포스 요구사항 작성 시 아래의 예제를 참고
```
### 상품

* 상품을 등록할 수 있다.
* 상품의 가격이 올바르지 않으면 등록할 수 없다.
    * 상품의 가격은 0 원 이상이어야 한다.
* 상품의 목록을 조회할 수 있다.
```
------------------------------------------------------
## 2단계 서비스 리팩터링
### 요구 사항
* [ ] 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드를 분리해 단위 테스트 가능한 코드에 대해 단위 테스트를 구현
  * spring Data JPA 사용 시 spring.jpa.hibernate.ddl-auto=validate 옵션을 필수로 설정
  * 데이터베이스 스키마 변경 및 마이그레이션이 필요하다면 아래 문서를 적극 활용
    * [DB도 형상관리를 해보자!](https://meetup.toast.com/posts/173)

### 프로그래밍 요구 사항
* [ ] Lombok 없이 미션을 진행
* [ ] 자바 코드 컨벤션을 지키면서 프로그래밍
  * 기본적으로 Google Java Style Guide을 원칙으로 사용
  * 들여쓰기는 '2 spaces'가 아닌 '4 spaces'
  * indent(인덴트, 들여쓰기) depth를 1까지만 허용
  * 3항 연산자, else, switch/case 사용금지
  * 모든 기능을 TDD로 구현해 단위 테스트가 존재 (UI 제외)
    * 핵심 로직을 구현하는 코드와 UI를 담당하는 로직을 구분
  * UI 로직을 InputView, ResultView와 같은 클래스를 추가해 분리
  * 함수(또는 메서드)의 길이가 10라인을 넘어가지 않도록 구현한다.
  * 함수(또는 메소드)가 한 가지 일만 하도록 최대한 작게 만들어라.
  * 배열 대신 컬렉션을 사용
  * 모든 원시 값과 문자열을 포장
  * 줄여 쓰지 않기(축약 금지)
  * 일급 컬렉션을 사용
  * 모든 엔티티를 작게 유지
  * 3개 이상의 인스턴스 변수를 가진 클래스를 사용하지 않기

### 힌트
* 테스트하기 쉬운 부분과 어려운 부분을 분리
* 한 번에 완벽한 설계를 하겠다는 욕심을 버리기
* 모델에 setter 메서드 넣지 않기
------------------------------------------------------
## 3단계 의존성 리팩토링
### 요구사항
* [ ] 의존성 관점에서 설계를 검토
  * 메뉴의 이름과 가격이 변경되면 주문 항목도 함께 변경되는데 메뉴 정보가 변경되더라도 주문 항목이 변경되지 않게 구현
  * 클래스 간의 방향도 중요하고 패키지 간의 방향도 중요한데 클래스 사이, 패키지 사이의 의존 관계는 단방향이 되도록 구현

### 프로그래밍 요구사항
* [ ] Lombok 없이 미션을 진행

### 힌트
* 함께 생성되고 함께 삭제되는 객체들을 함께 묶어라
* 불변식을 지켜야 하는 객체들을 함께 묶어라
* 가능하면 분리하라
* 연관 관계는 다양하게 구현 가능
  * 직접 참조 (객체 참조를 이용한 연관 관계)
  * 간접 참조 (리포지토리를 통한 탐색)