Functional Programming 에서 Function 은, 
- 입력값과 출력값이 같다. Pure Function
- Side-Effect 가 없다.
- Immutability -> 값의 상태가 변하는 변수가 없으며, 계산된 값은 항상 새로운 값을 생성 한다.
  : 값에 대한 레퍼런스를 가지지 않는다.
  : 복잡한 동시성(병렬 처리) 프로그래밍 처리가 필요하지 않다.
  : CPU 가 일정한 사용율만 차지 한다.
  : Memory Allocation, GC 에 대한 부담이 최소화 된다.
- 함수형에서 1 ~ 10 까지 더한 값 55 를 구한다고 했을 때, 최종 결과값 55만 가지고 있으며,
  1 부터 9 까지의 연산에 대한 상태 변경 등등에 대해선 가지고 있지 않는다.
- First Class Function
  : int someFunction(function)
  : Function someFunction(int i)
  : list = [function, function2, function3, ...]
  : 과일주스를 만든다면 껍질을 벗기는 과일과 도구를 통해 껍질을 깎아야 하는 과일이 있을 때 , 벗기는 행위 자체를 파라미터로 넘긴다.
       심지어, 리턴 되는 대상이 행위 자체를 받을 수 있다.
    function 을 파리미터로 넘길수 있고, function 을 결과로 받을 수 있다. 심지어 function 을 자료구조에 담을 수 있다.
  : Anonymous Function
    new Comparator<Product>() {
        public int compare(Product p1, Product p2) {
            return p1.getPrice().compareTo( p2.getPrice() );
        }
    }
    Java 에서 주로 사용 되던 Anonymous Class 에서, 꼭 필요한 부분만 취하면 아래와 같이 간단해 진다.
    (Product p1, Product p2) {
            return p1.getPrice().compareTo( p2.getPrice() );
        }
    Lambda 의 화살표 표기법으로 전환 하면
    (Product p1, Product p2) -> return p1.getPrice().compareTo( p2.getPrice() )
    Functional 에선 언제나 return 값을 취하고, 
          비교 대상이 되는 값에 대해 다른 값이 올수 없으므로, Product 라는 자료형역시 필요하지 않는다.
    Functional 스타일대로 최적화 하면,
    (p1, p2) -> p1.getPrice().compareTo( p2.getPrice() )
    
- Lambda Expression
  : (Product p1, Product p2) -> p1.getPrice().compareTo( p2.getPrice() )
  : (p1, p2) -> p1.getPrice().compareTo( p2.getPrice() )
  : (User user) -> user.getName()
  : (user) -> user.getName()
      parameter가 하나인 경우, 괄호를 노이즈를 없앨 수 잇다.
  : user -> user.getName()
      parameter가 없는 경우는 아래와 같다.      
  : () -> "Unknown"
      return 값이 없는 형식은 아래와 같이 할 수 있다.
  : user -> System.out.println( "hello " + user)
     
- FunctinalInterface ---> Java 에서의 Lambda Expression 지원 하기 위한 새로운 타입으로 아래의 룰을 지켜야 한다.
  : Single Abstract Method (오직 하나의 메서드만 정의)
    @FunctinalInterface public interface Function<T, R> { R apply(T t); }
  





