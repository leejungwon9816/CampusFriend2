# CampusFriend01
campusFriend_

<h1>대학 동아리 앱</h1>

만드는 중...

추가작업
* 채팅
* 동아리 홍보 게시판
* 로그인 및 세부 권한 부여

-현재 서버로 Firebase를 이용 중인 AndroidStuio app.

<h4>문제 해결</h4>

1. 게시판의 작성한 글에 대해서 작성자만이 수정 및 삭제 권한을 가질 수 있게끔 만들어 주어야 했다.
-> 처음에는 특정 함수를 사용해서 이러한 권한 문제를 해결할 수 있을 것이라고 생각하였다. 그런데 인터넷 검색 결과 문제 해결 방법이 생각보다 간단하다는 것을 알게 되었다. 바로 visibility를 사용하는 것이다. layout 상에서는 설정 메뉴를 투명화 시켜놓은 후, kotlin에서 if 문법을 사용하여 글을 작성한 작성자의 uid와 현재 본인의 uid가 동일하면 설정 메뉴가 보이도록 설정해주었다. (본인이 작성한 글에 색이 들어 오는 것도 동일한 원리가 적용되었다.)  


-ㅁ-
