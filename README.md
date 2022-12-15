# 💙 Event-Bus

해당 프로그램은 event-bus 패턴의 프로그램으로,  
event-bus를 바탕으로 여러 컴포넌트들이 이벤트를 발생시켜 통신하는 기법이다.
<br>
<br>

## 🔎 UML Diagram
![image](https://user-images.githubusercontent.com/90022940/207903730-d88e5c70-15f9-4b2d-9d7d-0e6a44d8b23c.png)
<br>
<br>

## 🔎 Sequence Diagram
<details>
<summary>수강 신청</summary>
<br>
<img src="https://user-images.githubusercontent.com/90022940/207903755-e94541a8-6ac5-4e40-a16b-5b5bd088b21e.png"/>
<br>
</details>
<br>

## 🙂 Advantage

#### 1. 높은 유지보수성 및 안전성
 input/output/student 등 컴포넌트를 관심사마다 나누어 보다 시스템을 분산해 개발하기 쉬우며,  
 각 로직을 완전히 분리시켜 높은 유지보수성 및 안전성을 가진다.

#### 2. 자유로운 이벤트 전달
특별한 조건 없이 이벤트 버스를 통해 모든 컴포넌트가 이벤트를 받을 수 있어 보다 자유로운 프로그래밍이 가능하다. 
<br><br>

## ☹ Disadvantage

#### 1. 어려운 디버깅
이벤트 전달 시간을 비롯해 이벤트 전달 과정에서 명확한 오류 발생 위치와 원인을 알기 어렵다.  
이에 이벤트를 받지 못하거나 데이터 누락이 발생할 위험이 있다.

#### 2. 부족한 직관성
모든 컴포넌트가 이벤트 버스를 통해 이벤트를 전달 및 응답하기 때문에  
실제 로직이 어떠한 로직으로 동작하는 지 한 눈에 알아보기 어렵다.

#### 3. 이벤트의 한계
이벤트의 공통된 메시지만으로 여러 데이터를 주고 받는 작업은 데이터의 병합과 분리 작업이 추가로 필요해 비효율적이다.

#### 4. 많은 CPU와 메모리 필요
여러 프로세스를 동시에 실행하기 때문에, 이를 실행시키는 과정이 복잡할 뿐만 아니라 많은 CPU와 메모리를 필요로 한다.

