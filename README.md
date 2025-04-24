# hulzzuk
![KakaoTalk_20250407_025553922](https://github.com/user-attachments/assets/20f7cffb-1c55-4464-906b-0242e9ed3559)
---
# 서비스 소개
> 트렌디한 여행지와 활동을 분석하고, 
여행 일정을 공유하며 함께 계획할 수 있는 
소셜 기반 여행 일정 계획 및 공유 플랫폼 서비스 입니다.
---
# 주요 기능
- 쉬운 일정 생성과 공유 및 다수의 사용자 간 실시간으로 협업하여 여행 계획 가능
- 여행 로그 시스템을 통한 해당 여행 장소에 대한 타 사용자의 경험 제공
- 사용자들이 어떤 여행지를 선호하고, 어떤 활동을 자주 선택하는지에 대한 정보 제공
- 타 플랫폼 사용 없이 여행 지역 및 장소 검색 가능
---
# 기술 스택
- DEVELOPER TOOL
  - Spring Tools Suite 4
  - IntellijIDEA
  - Maven
- 언어
  - JAVA
  - JSP
  - JAVASCRIPT
- 배포
  - AWSEC2
- CI/CD
  - GITHUB ACTION
- DB
  - ORACLE 21C XE
---
# ERD
![20250424_ERD](https://github.com/user-attachments/assets/0a3217a7-2ea6-4566-9f1f-12ac991ad62f)
---
# Git 전략
**GITUB FLOW 전략을 사용합니다.**
> 우리는 기본적으로 feature brnach를 생성하여 PR을 요청한 후 다같이 코드 리뷰를 거쳐\
> 최종적으로 develop branch에 merge하는 것으로 정책을 가져가고 있습니다.
- master : 실제 서비스 되는 branch로 주에 한번씩 최신 버전 적용
- develop : 개발 branch로 릴리즈에 있어서 절대적인 역할 ( 항상 최신 버전 유지!!)
- feature : develop branch 에서 뻗어나오는 branch로 기능별로 branch 생성\
            기능 구현 후 PR 요청 후 merge

