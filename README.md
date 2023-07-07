# Interview_Feed

[관련 내용 노션에서 보러 가기](https://www.notion.so/erickim-portfolio/S-A-6eab7701b23546a791e76b94da6fe18d#ba36910a569247109ee31935c6dbd25b)


### 취업 준비생들을 대상으로 한 면접 / 채용 정보 공유 플랫폼

- 채용은 정보 싸움!
- 벨로그 등에 있는 **********해시태그 기능**********을 유용하게 사용해보고 싶다!
- 이 정보들을 해시태그 기능을 이용해 상세히 카테고리로 나누어 (직무별, 회사별, 전형별) 정보를 공유할 수 있는 플랫폼을 구상.
---
## 프로젝트 요구 사항
### **필수 구현 기능**

- [x]  **사용자 인증 기능**
    - 회원가입 기능
        - 새로운 사용자가 ID와 비밀번호의 형태로 서비스에 가입할 수 있어야 합니다.
            - 이 때, 비밀번호는 안전하게 암호화되어 저장되어야 합니다!
    - 로그인 및 로그아웃 기능
        - 사용자는 자신의 계정으로 서비스에 로그인하고 로그아웃할 수 있어야 합니다.
- [x]  **프로필 관리**
    - 프로필 수정 기능
        - 이름, 한 줄 소개와 같은 기본적인 정보를 볼 수 있어야 하며 수정할 수 있어야 합니다.
        - 비밀번호 수정 시에는 비밀번호를 한 번 더 입력받는 과정이 필요합니다.
- [x]  **게시물 CRUD 기능**
    - 게시물 작성, 조회, 수정, 삭제 기능
        - 게시물 조회를 제외한 나머지 기능들은 전부 인가(Authorization) 개념이 적용되어야 하며 이는 JWT와 같은 토큰으로 검증이 되어야 할 것입니다.
        - 예컨대, 내가 작성한 글을 남이 삭제할 수는 없어야 하고 오로지 본인만 삭제할 수 있어야겠죠?
    - 게시물 작성, 수정, 삭제 시 새로고침 기능
        - 프론트엔드에서 게시물 작성, 수정 및 삭제를 할 때마다 조회 API를 다시 호출하여 자연스럽게 최신의 게시물 내용을 화면에 보여줄 수 있도록 해야 합니다!
- [x]  **뉴스 피드 기능**
    - 뉴스 피드 페이지
        - 사용자가 다른 사용자의 게시물을 한 눈에 볼 수 있는 뉴스 피드 페이지가 있어야 합니다.

### **[⭐](https://emojipedia.org/star/)** 추가 구현 기능 **[⭐](https://emojipedia.org/star/)** - 이것들까지 구현하면 너무 좋아요!

- [x]  **댓글 CRUD 기능**
    - 댓글 작성, 조회, 수정, 삭제 기능
        - 사용자는 게시물에 댓글을 작성할 수 있고 본인의 댓글은 수정 및 삭제를 할 수 있어야 합니다.
        - 또한, 게시물과 마찬가지로 댓글 조회를 제외한 나머지 기능들은 인가(Authorization)개념이 적용되어야 합니다.
    - 댓글 작성, 수정, 삭제 시 새로고침 기능
        - 프론트엔드에서 댓글 작성, 수정 및 삭제를 할 때마다 조회 API를 다시 호출하여 자연스럽게 최신의 댓글 목록을 화면에 보여줄 수 있도록 해야 합니다!
    
- [ ]  **좋아요 기능**
    - 게시물 및 댓글 좋아요/좋아요 취소 기능
        - 사용자가 게시물이나 댓글에 좋아요를 남기거나 취소할 수 있어야 합니다.
        - 이 때, 본인이 작성한 게시물과 댓글에 좋아요는 남길 수 없도록 해봅니다!

- [x]  **프론트엔드 만들어보기**
    - 백엔드에서 제공하는 API를 통해 서버와 통신하는 프론트엔드를 구현합니다.
    - 와이어프레임에 나온 명세를 최대한 구현해보면 금상첨화겠죠?
    - 웹개발 종합반에서 배웠던 부트스트랩을 활용해봐도 좋아요~
     
- [x]  **이메일 가입 및 인증 기능**
    - 이메일 가입 시 이메일 인증 기능을 포함하는 것이 좋습니다.

### 이외에 추가 구현 기능
- **해시태그 작성 기능**
  - 게시글 하나 당 여러 개의 해시태그 작성 가능, 해시태그 별 게시글 조화 가능

---

## 프로젝트 명세

### 🎯 ERD

![interview_feed](https://github.com/ayboori/Interview_Feed/assets/105356296/5d38840e-c5fd-4cdc-bb02-6ace0df393c2)


### 🎯 [API 명세 보러 가기](https://www.notion.so/erickim-portfolio/S-A-6eab7701b23546a791e76b94da6fe18d?pvs=4#ba36910a569247109ee31935c6dbd25b)

### 🎯 [WireFrame 보러 가기](https://www.figma.com/file/blpC3rMqs1YaE8AFNTuHJz/Untitled?type=design&node-id=0-1&mode=design&t=v4uNvaY4DX1X9CwE-0)
