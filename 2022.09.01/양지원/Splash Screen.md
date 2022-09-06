## Splash Screen 이란?

- 앱의 본격적인 화면이 나오기 전에 1~2초 간 잠시 나타난다
- 일반적으로 단색 배경에 어플리케이션의 로고가 중앙에 표시되는 경우가 많다

##### 왜 사용하나
- 디자인 적인 요소, 브랜드나 앱의 이미지각인을 위해서
- 어플리케이션을 열었을 때 0.x몇초 동안의 공백화면을 채우기 위해서
	- 공백 화면이 뜨는 이유
	- Application을 실행하면, 첫 Activity의 onCreate()가 실행되기 전에 setTheme()이 먼저 실행된다
		``setTheme() : 액티비티에 테마가 지정 되어있을 때 테마를 표시하는 메서드``
	- setTheme() -> onCreate() 순으로 호출되기 때문에 테마가 먼저 나타난 후, 화면 layout이 불러와지는 것

![[Pasted image 20220901202542.png]]




1. Splash 화면의 배경으로 활용되는 배경 Xml을 생성한다
- layout이 아닌 **drawable에 background_splash.xml파일**을 생성
![[Pasted image 20220901211224.png]]


2. values/themes.xml에서 SplashTheme을 설정한다
![[Pasted image 20220901211256.png]]

3. Androidmanifest에서 MainActivity속성에 theme을 설정한다.
![[Pasted image 20220901211346.png]]