# 등산 크루 모집 커뮤니티 ‘올라!’
스프링 프로젝트 ‘올라’ 입니다. 생활 커뮤니티 서비스로, 등산과 관련된 관심사를 해시태그로 만들어 다양하게 공유할 수 있는 방법을 구현했습니다.

<!-- [프로젝트 포트폴리오 상세 보기](http://jenyglee93.com/) -->
![리드미 대문 이미지](https://user-images.githubusercontent.com/86715916/222955463-95fad7f8-1959-46d1-b556-e550469bff0e.jpg)

## ✨ 주요기능
1. 관심사가 비슷한 친구를 랜덤으로 추천
2. 검색조건과 해시태그를 이용한 필터링 조회
3. 실시간 채팅을 통한 크루원 모집
4. 자동 등업 시스템
5. 커뮤니티 게시글/댓글/좋아요
6. 등산 검색 쇼핑(네이버 쇼핑)
7. Admin 기능(회원, 커뮤니티, 코스추천, 공지사항 관리)

## 📚 구현 기술 스택
- Redis 캐싱을 이용한 로그아웃
- QueryDSL
- PresignedURL 이미지 업로드
- JWT Access/Refresh Token
- Websocket, Socket js, Stomp 
- OAuth2 카카오 로그인
- RDS Database(MySQL)
- Github Action CI/CD


* * *

## 🪄프로젝트 시연!

|코스추천 페이지|코스추천 상세 페이지|
|------|---|
|![01_코스추천](https://user-images.githubusercontent.com/86715916/223703985-2de089a6-9f56-4e58-8939-d0030af101f8.gif)|![02_코스추천 상세](https://user-images.githubusercontent.com/86715916/223704287-301d4d3b-86d9-4ccf-af22-f7ca462da5ed.gif)|

|커뮤니티 페이지|커뮤니티 상세 페이지|
|------|---|
|![03_커뮤니티](https://user-images.githubusercontent.com/86715916/223704302-26ff7fee-d7ed-4d85-8b70-53013f6702a4.gif)|![04_커뮤니티 상세](https://user-images.githubusercontent.com/86715916/223704316-c736e7b5-1ca2-44cc-a14c-8252089dbfcc.gif)|

|쇼핑몰-공지사항|친구-알람|
|------|---|
|![05_쇼핑몰-공지사항](https://user-images.githubusercontent.com/86715916/223704336-a33b3be0-21b1-47d2-9cf3-e542861333f6.gif)|![06_친구-알람](https://user-images.githubusercontent.com/86715916/223704350-34147713-d5d1-4810-bfb4-7b26a66901cc.gif)|

|마이페이지|어드민 페이지|
|------|---|
|![07_마이페이지](https://user-images.githubusercontent.com/86715916/223704372-038ad154-816d-4213-8eb0-ea9f176013e5.gif)|![08_어드민](https://user-images.githubusercontent.com/86715916/223704382-95c9ad86-c34d-48b1-9286-10144351df58.gif)|


## 🎯 구현 이슈 정리

### 게시물
#### 한방쿼리로 연관관계 싹 지우기
> 커뮤니티 프로젝트를 하면서 게시글/댓글/댓글좋아요까지 연관되어있는 상태에서 게시글을 삭제했을 때 그 안에 있는 많은 댓글과 댓글 안에 있는 더 많은 좋아요들을 전부 삭제해야 하는 상황에서, 쿼리최적화를 할 수 있는 방법을 찾아보았습니다. Spring Data JPA에서 제공하는 deleteByXXX 메소드는 기본적으로 삭제 대상을 조회하는 쿼리 1번, 삭제하는 1건이 실행되어 N+1에서 벗어날 수 없었습니다. Bulk Delete 방식을 사용하면 많은 댓글에 있는 더많은 좋아요들을 한방 쿼리로 싹 지워줄 수 있었습니다.   
> <pre><code>@Query("delete from CommentLike cl where cl.comment.Id in :commentIds")</code></pre>
> 위의 쿼리문을 작성하여 SQL의 In절을 이용해 수많은 댓글들의 ID 배열 안에 댓글좋아요의 댓글 ID가 포함되는 것을 쿼리 한번으로 전부 지울 수 있었습니다.


#### 채팅방 구현하기

> 저희 조는 실시간 채팅 기능을 구현하기 위하여 websocket 통신 기술을 채택하였습니다.   
흔히 실시간 채팅기능은 WebSocket을 사용한다고 인지하고 있었기 때문에 기능 구현에 있어서 WebSocket만 사용하면 되는구나 라고 생각했었으나,
WebSoket만 사용하게 된다면 메세지가 어떤 요청인지, 어떤 포맷으로 오는지, 메세지 통신 과정을 어떻게 처리해야하는지 정해져 있지 않아
일일이 구현해야 한다는 사실을 알게되었습니다. 이러한 번거로움을 줄이기 위해 추가적으로 필요한 기술을 찾아보았고 
STOMP라는 서브 프로토콜을 사용함으로서 규격을 갖춘 메세지를 보낼 수 있다는 점과 더불어, 
초보 개발자에게도 Pub/Sub(발행 및 구독)의 비교적 이해하기 쉬운 개념을 통해 어렵지 않게 실시간 채팅 기능을 구현할 수 있다고 판단하여 해당 기술을 추가적으로 적용하여 기능 개발을 완료하였습니다.   
향후에는 STOMP 이외에도 Kafka, RabbitMQ, ActiceMQ등의 다양한 메세징 시스템 등을 이용하여 손쉬운 메세징 처리 기법들을 학습하고, 
프로젝트에 적용해보는 시간을 가져 봄으로서 각각의 기법들의 장점과 단점을 다양하게 파악해야겠다고 생각했습니다.

#### 쿼리문을 썼더니 게시글이 증식한다!?

> 게시글 테이블과 이미지 테이블을 join 했습니다. 게시글과 이미지는 1:N 관계를 가지고 있습니다. 이 연관관계에서 조회 쿼리문을 작성해보니 이미지의 개수만큼 게시물이 증식하는 문제를 발견했습니다. 이 상황에서 증식된 게시물은 이미지가 각각 분배돼어 각각 다른 이미지들을 갖고 었습니다. distinct를 사용했으나 이미지가 다르니 중복으로 볼 수 없었기 때문에 중복제거가 되지 않았습니다. 저는 이 상황을 쿼리문을 각각 분리하는 방향으로 수정했습니다. 첫번째로 게시글을 전체 조회했고, 조회된 게시물 리스트를 for문을 돌려 각각 게시글 ID를 이미지 테이블과 연관시켜서 추출해주었습니다. 결과적으로 각각의 게시글에 맞는 이미지 리스트가 들어가며 문제를 해결할 수 있었습니다.


### 친구
#### N:N 관계에서 매칭시스템 
> 하나의 유저가 가지고 있는 태그들 중에 나와 한개라도 같은 태그를 가지고 있는 유저들을 매칭해야하는 기능을 구현하던 중 N+1 문제를 마주하게 되었습니다.
처음에 N+1 문제를 해결하기 위해 Fetch Join또는 Batch Size를 설정 해줌으로서 문제해결을 하고자 했습니다. 그러나 fetch join을 사용하게 될 경우 paging 기법 함께 사용하지 못한다는 단점이 있었고
Batch size는 100번 일어날 n+1문제를 1번 이상으로 줄일 수는 있지만 N+1문제가 발생하지 않는 것은 아니기 때문에 해법이 아니라고 생각했습니다.   
오랜 고심 끝에 관점을 바꾸어 1이 되는 테이블이 기준이 되기 보다 N이 되는 테이블을 기준으로 삼아 조인한다면 N+1이 발생하는 문제도 사라지며 원하는 결과 값을 Paging 처리와 함께 풀어낼 수 있다는 생각을 하게 되었고 적용하여 문제를 해결할 수 있었습니다.   
N+1의 문제는 정말 다양한 경우의 수가 있기 때문에 향후에도 이러한 문제들을 많이 접해보고 다양한 문제해결을 적용해보는 연습을 해야겠다고 생각했습니다.


## 👥 팀 구성
백엔드 5명(이재원, 한세인, 김두영, 김주성, 김민선)

## 🗓 프로젝트 기간
- 2023년 02월 06일 ~ 2023년 03월 10일
- 배포일 : 2023년 03월 8일

## 💿 기술스택
<div style="display:flex;">
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 
  <img src="https://img.shields.io/badge/SPRINGDATAJPA-529327?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/QUERYDSL-0E70C7?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens">
  <img src="https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white">
  <img src="https://img.shields.io/badge/WEBSOCKET-FF6600?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
  <img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"> 
  <img src="https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white">
  <img src="https://img.shields.io/badge/CODEDEPLOY-82A450?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
  <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">
</div>


## 🛠 서비스 아키텍쳐
<img width="793" alt="아키텍쳐" src="https://user-images.githubusercontent.com/86715916/223677821-ce7c2d72-6b17-410f-b0fd-c5b8b76270ac.png">


## 🧩 ERD
<img width="793" alt="스크린샷 2023-03-07 오후 3 24 49" src="https://user-images.githubusercontent.com/86715916/223626267-a2f9762e-c314-43d4-9474-8b0f6336231c.png">

## ✏️ 피그마 시안
<img width="793" alt="스크린샷 2023-03-07 오후 1 43 06" src="https://user-images.githubusercontent.com/86715916/223626203-d7098c7c-846f-413e-bcd8-af908cee1f44.png">

