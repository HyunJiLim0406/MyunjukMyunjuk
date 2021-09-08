## 📕 소개

독서 기록 앱 [북적북적](https://play.google.com/store/apps/details?id=com.studiobustle.bookjuk&hl=ko&gl=US)의 기능을 Spring Boot로 구현하는 실습 프로젝트

도서 검색에는 네이버, 알라딘 도서 API를 사용

[기술 블로그](https://myunji.tistory.com/category/%F0%9F%92%BB%20%ED%98%84%EC%83%9D/%F0%9F%93%95%20%EB%A9%B4%EC%A0%81%EB%A9%B4%EC%A0%81%28%EC%8A%A4%ED%94%84%EB%A7%81%20%EC%8B%A4%EC%8A%B5%29)

## 📗 기술 스택

- Spring Boot
    - Spring Security
    - JPA
    - Spring Data JPA
    - QueryDSL
    - MapStruct
- MySQL
- AWS
    - S3

## 📙 ER-diagram

![myunjuk](https://user-images.githubusercontent.com/52443695/132505345-df13aa58-b56a-4ca3-b9f0-155f4f6b8cc0.png)
🔗 [링크](https://dbdiagram.io/d/6138a4b0825b5b0146f8a570)

## 📘 API 명세서

🔗 [링크](https://documenter.getpostman.com/view/14407018/U16htn4R)

## 📓 프로젝트 구조

```jsx
+---main
|   +---java
|   |   \---jpa
|   |       \---myunjuk
|   |           |   MyunjukApplication.java
|   |           |
|   |           +---infra
|   |           |   +---annotation
|   |           |   |       Enum.java
|   |           |   |       EnumValidator.java
|   |           |   |
|   |           |   +---config
|   |           |   |       OpenEntityManagerConfig.java
|   |           |   |       QueryDSLConfig.java
|   |           |   |       WebSecurityConfig.java
|   |           |   |
|   |           |   +---converter
|   |           |   |       BookStatusConverter.java
|   |           |   |       BooleanToYNConverter.java
|   |           |   |
|   |           |   +---exception
|   |           |   |       AccessDeniedException.java
|   |           |   |       CustomRuntimeException.java
|   |           |   |       DuplicateException.java
|   |           |   |       GlobalExceptionHandler.java
|   |           |   |       InvalidReqBodyException.java
|   |           |   |       InvalidReqParamException.java
|   |           |   |       NoSuchDataException.java
|   |           |   |       S3Exception.java
|   |           |   |
|   |           |   \---jwt
|   |           |           JwtAuthenticationFilter.java
|   |           |           JwtTokenProvider.java
|   |           |
|   |           \---module
|   |               +---controller
|   |               |       BookshelfController.java
|   |               |       BookshelfMemoController.java
|   |               |       CharactersController.java
|   |               |       HistoryController.java
|   |               |       HomeController.java
|   |               |       ProfileController.java
|   |               |       SearchController.java
|   |               |       UserController.java
|   |               |
|   |               +---mapper
|   |               |       BookshelfMapper.java
|   |               |       CharactersMapper.java
|   |               |       HomeMapper.java
|   |               |
|   |               +---model
|   |               |   +---domain
|   |               |   |       Book.java
|   |               |   |       BookStatus.java
|   |               |   |       Characters.java
|   |               |   |       Memo.java
|   |               |   |       User.java
|   |               |   |       UserCharacter.java
|   |               |   |
|   |               |   \---dto
|   |               |       |   CharacterDtos.java
|   |               |       |   HomeDto.java
|   |               |       |   JwtDtos.java
|   |               |       |   UserDtos.java
|   |               |       |
|   |               |       +---bookshelf
|   |               |       |       BookshelfBasic.java
|   |               |       |       BookshelfDetailDtos.java
|   |               |       |       BookshelfResDtos.java
|   |               |       |
|   |               |       +---history
|   |               |       |       ChartDto.java
|   |               |       |       MemoDto.java
|   |               |       |
|   |               |       \---search
|   |               |               AddSearchDetailResDto.java
|   |               |               SearchDetailDto.java
|   |               |               SearchDto.java
|   |               |               SearchReqDto.java
|   |               |               SearchResDto.java
|   |               |
|   |               +---repository
|   |               |   |   CharactersRepository.java
|   |               |   |   UserCharacterRepository.java
|   |               |   |   UserRepository.java
|   |               |   |
|   |               |   +---book
|   |               |   |       BookRepository.java
|   |               |   |       CustomizedBookRepository.java
|   |               |   |       CustomizedBookRepositoryImpl.java
|   |               |   |
|   |               |   \---memo
|   |               |           CustomizedMemoRepository.java
|   |               |           CustomizedMemoRepositoryImpl.java
|   |               |           MemoRepository.java
|   |               |
|   |               \---service
|   |                       BookshelfMemoService.java
|   |                       BookshelfService.java
|   |                       CharactersService.java
|   |                       CommonService.java
|   |                       CustomUserDetailService.java
|   |                       HistoryService.java
|   |                       HomeService.java
|   |                       ProfileService.java
|   |                       S3Service.java
|   |                       SearchService.java
|   |                       UserService.java
|   |
|   \---resources
|       |   application.yml
|       |
|       +---static
|       \---templates
\---test
    \---java
        \---jpa
            \---myunjuk
                |   MyunjukApplicationTests.java
                |
                \---module
                    +---controller
                    |       CharactersControllerTest.java
                    |       SearchControllerTest.java
                    |       UserControllerTest.java
                    |
                    +---model
                    |   \---dto
                    |       |   JwtDtosTest.java
                    |       |   UserDtosTest.java
                    |       |
                    |       \---search
                    |               SearchReqDtoTest.java
                    |
                    \---service
                            BookshelfMemoServiceTest.java
                            BookshelfServiceTest.java
                            CharactersServiceTest.java
                            HistoryServiceTest.java
                            HomeServiceTest.java
                            ProfileServiceTest.java
                            SearchServiceTest.java
                            UserServiceTest.java
```
