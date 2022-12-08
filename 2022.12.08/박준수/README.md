# Reusable Tags in XML Layout

## Reusable Tag ?

안드로이드의 XML 레이아웃은 태그 계층 구조로 구성되어 있습니다. 한 레이아웃 내에서 사용되는 XML 태그들은 다른 레이아웃의 XML 태그와 중복되거나 유사한 모습으로 존재할 수 있습니다. 안드로이드의 XML 레이아웃 태그도 일반적인 객체지향적인 개발과 같이 유사한 부분을 단위로 묶어 클래스를 만들고, 인스턴스를 생성하여 해당하는 부분을 재사용하는 것처럼 사용할 수 있습니다. 여기서 것처럼이란 말은, 실제 XML 레이아웃 태그의 인스턴스를 생성하지는 않으나, 기본이 되는 XML 레이아웃 태그의 틀을 선언해 두고 `style` 속성 또는 `<include>`, `<merge>`와 같은 태그로 중복되거나 유사한 부분을 재사용할 수 있습니다.

## `style`

### `style` ?

`style`은 View의 속성으로 지정할 수 있는 '속성의 모음' 이며, View의 배경, 글꼴, 크기, 색상 등의 모양과 활성화 상태 등을 외부에 이름과 함께 선언한 후, 해당 View에 적용합니다. key-value 형식으로 속성과 값을 지정할 수 있습니다.

### `style` 특징

- XML 레이아웃에서 View 속성의 재활용이 가능합니다

- 외부에 속성을 선언한 `style`을 사용하는 View에서 해당 속성을 오버라이드하여 사용할 수 있습니다
- View가 사용하는 `style` 내부에 이미 선언한 속성은 사용하는 View에서 생략할 수 있습니다
  - `android:layout_width`, `android:layout_height` 등의 필수적인(required) 속성들 또한 해당 View가 사용하는 `style`에 이미 적용되어 있다면 View에서 속성 선언을 생략할 수 있습니다

### `style` 선언

- 주로 `/res/values/` 경로에 `style.xml` 이름의 파일을 생성하여 `styles` 속성을 선언합니다

- `<resources>` 상위 태그에 `<styles>` 태그를 사용하여 여러 종류의 View의 속성을 선언할 수 있습니다

  -  `<item>` 태그를 하위 태그로 선언하여 `android`, `app` 네임스페이스의 속성을 View에 적용합니다
    - `android` 네임스페이스의 속성을 사용하기 위해서 `android:{속성 이름}`을 선언합니다
    - `app` 네임스페이스의 속성을 사용하기 위해서 `{속성 이름}`을 바로 선언합니다
  - `<style>` 태그의 `parent` 속성으로 `parent`로 선언된 View를 상속한 속성을 정의할 수 있습니다
  - `parent` View의 에 점 표기법을 사용하여 해당 `parent` View를 상속할 수 있습니다

  ```xml
  <resources>
    
      <style name="PrimaryWidget">
      		<item name="android:layout_width">wrap_content</item>
          <item name="android:layout_height">48dp</item>
        	<item name="layout_constraintTop_ToTopOf">parent</item> // app TAG
      </style>
    
    	<style name="PrimaryButton" parent="Widget.AppCompat.Button">
    			<item name="android:layout_width">wrap_content</item>
        	<item name="android:layout_height">wrap_content</item>
        	<item name="android:text">Android is The Best OS in the world</item> // Fact
    	</style>
    
    	<style name="Widget.AppCompat.Button.SecondaryButton">
          <item name="android:layout_width">wrap_content</item>
          <item name="android:layout_height">wrap_content</item>
          <item name="android:text">Kotlin is The Most Beautiful Language in the world</item>
      </style>
  </resources>
  ```

### `style` 사용

- XML 태그에 `styles.xml`에 지정된 속성을 선언하여 사용합니다

  ```xml
  <!-- style.xml -->
  <style name="PrimaryButton" parent="Widget.AppCompat.Button">
  		<item name="android:layout_width">wrap_content</item>
  	  <item name="android:layout_height">48dp</item>
  		<item name="android:text">첫 번째 버튼</item>
  		<item name="android:textSize">24sp</item>
  </style>
  ```

  ```xml
  <!-- layout -->
  <androidx.appcompat.widget.AppCompatButton
  		style="@style/PrimaryButton" />
  ```

## `<include>`

### `<include>` ?

`<include>`란 재사용이 가능한 레이아웃을 지정할 수 있는 '레이아웃 모음' 입니다. 대표적으로 확인/취소 버튼이 모여 있는 재사용이 가능한 ViewGroup, 툴바, 정보를 표시하는 텍스트 등이 존재합니다.

### `<include>` 특징

- 레이아웃의 재활용이 가능합니다
- 외부에 선언한 레이아웃을 현재 레이아웃에 포함시켜 사용할 수 있습니다

### `<include>` 레이아웃 선언

- 일반적인 레이아웃 선언과 동일하게 `res/layout` 디렉토리 내에 선언합니다

  - `<include>` 레이아웃을 사용하는 레이아웃에서 `<layout>` 태그로 DataBinding을 사용하는 경우 `<include>` 레이아웃 또한 `<layout>` 태그로 DataBinding을 사용해야 합니다

    ```xml
    <layout
    		...>
    
        <data>
    
            <variable
            		... />
        </data>
      
      	<LinearLayout
        		... >
          
          	...
          	<TextView
    						... >
          	
          	...
          	</TextView>
      	</LinearLayout>
    </layout>
    ```

	### `<merge>`

#### `<merge>` ?

`<include>` 레이아웃 최상단의 ViewGroup태그를 `<merge>` 태그로 대체하면, 해당 `<include>` 레이아웃이 선언된 상위 레이아웃의 ViewGroup과 병합됩니다. `<include>` 레이아웃이 선언된 상위 레이아웃의 ViewGroup 태그와의 중복을 제거하여 탐색 속도를 개선하기 위해 사용하거나, 상위 레이아웃과의 ViewGroup을 통일하기 위해 사용합니다.

#### `<merge>` 특징

- `<include>` 레이아웃을 선언하는 상위 레이아웃 ViewGroup 태그의 속성을 따릅니다

  ```xml
  선언
  <merge
     	...>
    
    	<TextView
  				... />
  </merge>
  ```

  ```xml
  <LinearLayout
  		...>
  
    	// merge 적용 X
    	<LinearLayout
  				...>
    
      		<TextView ... />
    	</LinearLayout>
    
    	// merge 적용
  		<TextView ... />
  </LinearLayout>
  ```

#### `<merge>` 사용

- `<merge>` 속성을 적용하고 싶은 `<include>` 레이아웃의 최상위 레이아웃을 `<merge>` 태그로 변경합니다
- `tools:parentTag` 속성을 사용하여 미리보기 레이아웃을 지정할 수 있습니다

### `<include>` 사용

- 선언한 레이아웃을 적용할 곳에 `<include>` 태그와 `layout` 속성을 함께 사용하여 적용합니다

- 기존에 선언된 속성을 오버라이드하여 재정의할 수 있습니다

  ```xml
  // 외부
  <layout
  		...>
  
      <data>
  
          <variable
          		... />
      </data>
    
    	<LinearLayout
      		... >
        
        	...
        	<TextView
  						... >
        	
        	...
        	</TextView>
    	</LinearLayout>
  </layout>
  ```

  ```xml
  // 적용할 곳
  <layout
  		...>
  
  		<LinearLayout
  				...>
    
  				<include layout="{레이아웃 리소스}" />
        	<include layout="{레이아웃 리소스}" 
                   android:layout_width="wrap_content" />
    	</LinearLayout>
  </layout>
  ```

> 참고 자료
>
> > style
> >
> > - [android developer](https://developer.android.com/guide/topics/ui/look-and-feel/themes?hl=ko)
> >
> > include
> >
> > - [holika](https://holika.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%ED%8C%81-include-%ED%83%9C%EA%B7%B8-%EB%B0%98%EB%B3%B5%EB%90%98%EB%8A%94-%EB%A0%88%EC%9D%B4%EC%95%84%EC%9B%83%EC%9D%84-%EC%9E%AC%EC%82%AC%EC%9A%A9%ED%95%98%EC%9E%90)
> > - [android developer](https://developer.android.com/develop/ui/views/layout/improving-layouts/reusing-layouts?hl=ko)
> > - [mparchive](https://mparchive.tistory.com/154)