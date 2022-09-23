# Thread

## Thread ?

프로세스에서 실행되는 흐름의 단위를 일컫는 말이다.

- 프로세스의 실제 수행 경로이다.
- 모든 프로세스는 하나 이상의 스레드를 내포하고 있다.
  - 두 개 이상의 스레드를 가지는 프로세스를 *멀티 스레드 프로세스*라고 한다.
  - 프로세스의 주가 되는 스레드를 *메인 스레드*라고 한다.

---

> #### Process
>
> 메모리를 할당받아 실행중인 *프로그램의 인스턴스*이다.
>
> #### Thread
>
> 프로세스에서 실행되는 흐름의 단위이다.
>
> ---
>
> 스레드란, 프로세스가 할당받은 자원을 이용하는 *실제 실행의 단위*이다.
>
> ---

---

## Thread In OOP

객체지향 프로그래밍에서의 스레드 구현 방법은 크게 다음 둘로 나눌 수 있다.

>#### A. Thread 클래스를 상속하는 클래스 생성
>
>Thread 클래스를 상속(extend)하는 클래스를 생성하고 내부에 `run()` 메소드를 오버라이드, 기존 실행중인 스레드에서 `start()` 메소드를 사용하여 스레드를 실행한다.
>
>```java
>class SubThread extends Thread {
>  @Override
>	public void run() {
>		System.out.println("SubThread.run()");
>	}
>}
>
>SubThread subThread = new SubThread();
>
>subThread.start();
>```
>
>---
>
>#### B. Runnable 인터페이스 구현, Thread 클래스의 생성자에 인자로 전달
>
>Runnable 인터페이스를 구현(implement)하는 클래스를 생성하고 `run()` 메소드를 오	버라이드, 해당 클래스의 인스턴스를 Thread 클래스의 생성자에 인자로 전달한 후, 동일하게 `start()` 메소드를 사용하여 스레드를 실행한다.
>
>```java
>class SubRunnable implements Runnable {
>  @Override
>	public void run() {
>		System.out.println("SubRunnable.run()");
>	}
>}
>
>SubRunnable subRunnable = new SubRunnable();
>
>Thread thread = new Thread(subRunnable);
>
>thread.start();
>```
>
>---

전체적인 구성은 Runnable 인터페이스를 구현(implement)하는 것보다 Thread 클래스를 상속(extend) 하는 방법이 더 단순하다. 하지만 Java, Kotlin 등의 객체지향 프로그래밍에서는 클래스의 다중 상속을 지원하지 않는다. 이는 Thread 클래스를 상속하여 클래스를 구성하게 된다면 다른 클래스를 상속받을 수 없음을 의미한다. 반면 Runnable 인터페이스를 구현하는 방법을 사용한다면, 다른 클래스를 상속받음과 동시에 Runnable 인터페이스 외의 또 다른 인터페이스들도 구현할 수 있다. 따라서 Thread 클래스의 확장성이 중요하다면 Thread 클래스 상속 대신 Runnable 인터페이스를 구현하는 방법을 사용하는 것이 효율적일 것이다.

---

## Thread In Android

안드로이드에서 주가 되는 스레드는 `AndroidManifest.xml`에서 `Launcher`로 지정된 Activity를 메인 스레드로 삼는다.

> ```xml
> <intent-filter>
>     <action android:name="android.intent.action.MAIN" />
> 
>     <category android:name="android.intent.category.LAUNCHER" />
> </intent-filter>
> ```

---

UI를 구성하는 작업은 모두 메인 UI 스레드에서 진행되어야 한다. 하지만 UI 작업을 여러 곳에서 동시에 실행한다면 더 효율적인 UI 드로잉이 가능하지 않을까? 만약 메인 UI 스레드가 아닌 곳에서 UI 변경을 요청하게 되면 오류가 발생한다. 아래와 같이 UI를 구성하려고 하면 MainThread의 UI 작업이 실행된 뒤, SubThread의 UI 작업이 실행되어야 의도한 결과를 기대할 수 있다.

> ![정상](https://github.com/JunJaBoy/Android-Thread/blob/main/%EC%A0%95%EC%83%81.png?raw=true)

하지만 여러 작업을 비동기적으로 실행할 수 있도록 하는 Thread는 또한 어느 작업이 먼저 완료될 지(어느 스레드가 UI 작업을 먼저 완료할 지) 알 수 없다. 그래서 의도하지 않게 SubThread의 UI 작업이 먼저 완료되고, 그 후에 MainThread의 작업이 완료된다면 제일 마지막에 끝난 UI가 화면 최상단에 배치된다.

> ![비정상](https://github.com/JunJaBoy/Android-Thread/blob/main/%EB%B9%84%EC%A0%95%EC%83%81.png?raw=true)

---

<<<<<<< HEAD
=======

>>>>>>> main
이를 방지하기 위해 안드로이드 프레임워크에서는 UI 작업을 메인 스레드에서 한정한다. 그렇기에 위의 내용처럼 보조 스레드에서 UI를 그려야 하는 경우에는 메인 스레드에 '메시지'를 보내게 된다. 이렇게 전달된 메시지는 메인 스레드의 Handler에서 수신하고, 수신한 메시지를 기반으로 UI를 구성할 수 있다. 하지만, 메인 스레드에서 모든 작업을 처리하지 않고, 굳이 보조 스레드에서 작업을 처리하고 메인 스레드에서 UI를 처리하는 이유는 무엇일까?

---

앞서 언급했듯, 메인 스레드에서 UI를 구성한다. 만약 UI를 구성하는 과정에서 반환 시간이 긴 작업을 수행하게 된다면, 해당 작업이 완료될 때까지 화면이 멈춰(UI 구성을 중지)있게 된다. 따라서 UI를 그리는 작업을 담당하는 메인 스레드에서 반환 시간이 긴, 큰 작업은 보조 스레드로 위임하여 실행 후 비동기적으로 반환을 대기하는 방법을 채택하면 UI가 멈추는 등의 문제를 방지할 수 있다.

---

> 참고 자료
>
> > - [hmlwjd](https://gmlwjd9405.github.io/2018/09/14/process-vs-thread.html)
> > - [tcpschool](http://www.tcpschool.com/java/java_thread_concept)
> > - [recipes4dev](https://recipes4dev.tistory.com/143)
