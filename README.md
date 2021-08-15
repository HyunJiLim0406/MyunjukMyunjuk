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
|   |               |       |       ChartAmountDto.java
|   |               |       |       ChartPageDto.java
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
                            SearchServiceTest.java
                            UserServiceTest.java
```

---
