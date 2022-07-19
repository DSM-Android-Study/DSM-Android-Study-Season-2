## SharedPreferences
- 안드로이드 앱 개발을 하다 보면, 앱의 데이터들을 저장해 관리해야 할 상황이 존재함
- 간단한 데이터를 DB나 서버에 저장하기 부담스러울 때 안드로이드에서 기본적으로 제공되는 SharedPreferences를 사용함
  ex) 초기 설정값이나 자동 로그인 여부 등 간단한 값
- key-value 방식 (간단한 키-값 메소드를 사용하여 데이터를 저장하는 비관계형 DB유형)
- 데이터를 앱 폴더 내에 파일로 저장, **앱 삭제 시 데이터 파일도 삭제됨**


#### 사용법
- `MainActivity`의 `onCreate`에서 쓸 수는 있지만 매번 Activity마다 SharedPreferences를 초기화하는 것은 번거로움
  -> 앱의 어디서든 전역적으로 사용하려면 **싱글톤 패턴**을 사용해서 어디서든 접근 가능하게 만드는 것이 좋다.

1. Application()을 상속받는 App Class를 생성
```Kotlin
class App : Application() {

    companion object {
        lateinit var prefs: PreferenceUtil
    }
// prefs라는 이름의 MySharedPreferences 하나만 생성할 수 있도록 설정.

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
    
}
```

2. manifest에 App Class의 이름 등록
```Kotlin
<application
    android:name="com.example.test.App"
    ...
</application>
```

3. SharedPreferences를 변수로 가지고 있는 Class 생성
```Kotlin
class PreferenceUtil(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }
    
}
```

