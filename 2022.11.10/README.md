# Deprecated API

## Deprecated API ?

> Deprecate : 비추천, 반대하다/비난하다
>
> Deprecated : 중요도가 떨어져 더이상 사용되고 있지 않고 앞으로 사라지게 될

### Deprecated in Programming

프로그래밍에서 Deprecated(@Deprecated 어노테이션으로 표현)은 더 이상 권장되지 않는 API의 명시를 의미합니다. Deprecated 어노테이션이 명시된 API 중 여전히 사용할 수 있는 API들이 있지만, 추후 대체되거나 제거될 가능성이 있기에 신규 코드를 작성할 때는 사용을 자제하는 편이 좋으며, 기존의 코드에서 Deprecated API를 사용중이라면 대체된 API 사용을 권장합니다.

---

## Deprecated API in Android

인터넷의 자료를 살펴보다 보면 많은 공식/비공식 문서에서 Deprecated API와 대체된 API가 두루 사용되는 것을 확인할 수 있습니다. 하지만 Deprecated API의 사용을 지양하는것이 좋습니다. 이는 대부분 해당 Deprecated된 API를 대체할 새로운 API가 등장했거나, 보안, 시스템 상의 오류를 야기하는 등의 이유 때문에 Deprecated된 것이기 때문입니다.

대표적인 Deprecated API인 `startActivityForResult()` 메소드와 이를 대체할 수 있는 `registerForActivityResuilt()`를 예로 들어 설명해 보겠습니다.

---

### `startActivityForResult()`

#### `startActivityForResult()` ?

`startActivityForResult()`는 새로운 Activity를 실행하여 해당 Activity의 종료 시 결과값(requestCode)을 반환 받으며, 반환받은 requestCode를 기반으로 `onActivityResult()`에서 Activity를 식별하여 콜백을 수행합니다.

```kotlin
startActivityForResult(Intent(context, T::class.java), requestCode)
```

```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
        this.requestCode ->
            if (resultCode == RESULT_OK) {
                // Callback Body
            }
    }
}
```

- `startActivityForResult()`는 새 Activity를 호출한 후 requestCode를 바탕으로 콜백을 수행할 수 있지만, `startActivity()`는 단지 새 Activity를 호출하는 기능만을 가지고 있습니다.

#### `startActivityForResult()` Deprecation

하지만  `startActivityForResult()`는 현재 Deprecated 되었습니다. 콜백 수행의 안정성 및 보일러플레이트 코드의 증가 등의 이슈 때문입니다.

##### Call Back Issue

`startActivityForResult()`를 호출한 Activity가 소멸되면 콜백을 수행하지 않습니다

- 카메라 등의 메모리 사용이 많은 작업을 수행하여 메모리가 부족해진 경우, 시스템은 카메라 앱을 호출한 Activity를 종료할 수 있으며, 다시 Activity로 돌아왔을 때 대기중인 콜백이 제거되어 콜백을 수행하지 않습니다

##### 보일러플레이트 코드 증가

- `startActivityForResult()` 메소드로 결과를 반환받을 Activity가 증가 할수록 결과 처리를 위한 CallBack 메소드인 `onActivityResult()`의 메소드의 크기가 팽창합니다registerForActivityResult()

---

### `registerForActivityResult()`

#### `registerForActivityResult()` ?

`registerForActivityResult()` 메소드는 ActivityResultContract와 ActivityResultCallback을 인자로 받아 ActivityResultLauncher을 반환 합니다.

##### ActivityResultContract

ActivityResultContract는 ActivityResultLauncher의 계약 타입을 설정하여, Result 결과로 어떤 형식의 계약을 호출할 것인지 설정합니다. API 호출 시 Request Code를 관리할 필요가 없도록 합니다.

- 종류
  - StartActivityForResult
  - RequestMultiplePermissions
  - RequestPermission
  - TakeVideo
  - PickContact
  - ...

##### ActivityResultLauncher

Activity Result 수신 시 수행할 Callback의 Body 부분이며, `startActivityForResult()`의 `onActivityResult()` 메소드에서 각 Result에 등록하는 Callback과 같은 역할을 합니다.

```kotlin
private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    ...
    activityResultLauncher.launch(Intent(this, SubSubActivity::class.java))
```

```kotlin
private fun onRegisterActivityResult() {
    activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
               // Callback Body
            }
        }
}
```

- `startActivityForResult()`가 Deprecated되기 이전부터 사용되던 API이며, `startActivityForResult()`를 대체할 목적으로 나온 만큼 `startActivityForResult()`가 Deprecated된 이후 본격적으로 사용되기 시작했습니다.

---

## Deprecated된 API의 사용

### Deprecated API의 사용 ?

Deprecated된 API는 사용을 지양해야 합니다. 하지만 Deprecated된 API 중 많은 경우 여전히 @SuppressLint 어노테이션으로 Lint 메시지를 무시하고 사용할 수 있습니다. 하지만 이는 권장되지 않는 방법입니다.

### Deprecated된 API의 사용을 지양하여야 하는 이유

Deprecated된 API를 계속해서 사용할 시, 지원 중단 등의 문제에 직면할 수 있습니다. 또한 대부분의 경우, 해당 Deprecated된 API를 대체 가능한 새로운 API를 지원하게 됩니다.

#### 지원 중단

여전히 사용 가능한 Deprecated API일지라도, 향후 버전에서 언제든지 제거될 수 있습니다. 보안, 호환 등의 문제로 언제든지 사용이 불가능해 질 수 있습니다.

#### 대체 API 사용

대부분의 경우, Deprecated된 API는 더 나은 개발 환경을 제공하거나, 새 플랫폼에 기능을 지원하기 위해 대체 가능한 API를 지원하고 있습니다. 추후 유지보수 및 코드 리팩토링의 원활함을 위해 Deprecated된 API보다 대체 가능한 API를 사용하는 것을 권장합니다.

## 결론

개발을 하며 Deprecated된 API는 사용을 자제하여야 하며, 대부분 대체 가능한 최신 API가 있으므로 추후 코드의 유지보수 및 최신화를 위해 Deprecated된 API를 대체할 수 있는 API 사용을 지향하는 것이 좋습니다.

---

> 참고 자료
>
> > - Deprecated API
> >   - [oracle](https://docs.oracle.com/javase/8/docs/technotes/guides/javadoc/deprecation/index.html)
> >   - [android developer](https://developer.android.com/reference/java/lang/Deprecated)
> >   - [Activity Result API](https://developer.android.com/training/basics/intents/result?hl=ko)
> >   - [kimhijun](https://velog.io/@kimjihun1001/Android-Deprecated-API-%EB%A0%88%EB%B2%A8)
> >   - [kang](https://kang6264.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%8A%A4%ED%8A%9C%EB%94%94%EC%98%A4%EC%97%90%EC%84%9C-deprecated%EB%90%9C-%EB%A9%94%EC%84%9C%EB%93%9C-%EC%B0%BE%EA%B8%B0)
> > - `startActivityForResult()`
> >   - [jhshjs](https://jhshjs.tistory.com/49)
> >   - [kimyunseok](https://kimyunseok.tistory.com/40)
> > - `registerForActivityResult()`
> >   - [ho-taek](https://velog.io/@ho-taek/Android-registerForActivityResult%EB%9E%80)

