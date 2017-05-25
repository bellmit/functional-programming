package functional.style.programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

public class LambdaExample {

    @Test
    public void getNumbers() {
        final List<Integer> list = Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 8, 9 );
        final List<Integer> result = new ArrayList<>();
        // 2보다 큰 수만 받고 싶다.
        for ( Integer v : list ) {
            if ( v > 2 ) {
                result.add( v );
            }
        }
        final List<Integer> result2 = new ArrayList<>();
        // 7보다 작은 수를 받고 싶은 조건을 추가 하고 싶다. (추가 요구 기능이 발생된 경우)
        for ( Integer v : result ) {
            if ( v < 7 ) {
                result2.add( v );
            }
        }
        System.out.println( result2 );
    }

    private boolean filter( boolean... conditions ) {
        for ( boolean b : conditions ) {
            if ( !b ) {
                return true;
            }
        }
        return false;
    }

    /**
     * 재사용성을 높이는 OOP 방법은...
     */
    @Test
    public void getNumbersCase2() {
        final List<Integer> list = Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 8, 9 );
        final List<Integer> result = new ArrayList<>();
        for ( Integer v : list ) {
            if ( !filter( ( v > 2 ), ( v < 7 ) ) ) {
                result.add( v );
            }
        }
        System.out.println( result );
    }

    private <T> List<T> filter( List<T> list, Predicate<T> predicate ) {
        final List<T> result = new ArrayList<>();
        for ( T v : list ) {
            if ( predicate.test( v ) ) {
                result.add( v );
            }
        }
        return result;
    }

    /**
     * Predicate 를 통해 보완해 보자.
     */
    @Test
    public void getNumbersCase3() {
        final List<Integer> list = Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 8, 9 );
        final List<Integer> result = filter( list, new Predicate<Integer>() {

            public boolean test( Integer t ) {
                if ( t > 2 && t < 7 ) {
                    return true;
                }
                return false;
            }
        } );
        System.out.println( result );
    }

    /**
     * Predicate 를 Lambda 형식으로 보완해 보자.
     */
    @Test
    public void getNumbersCase4() {
        final List<Integer> list = Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 8, 9 );
        final List<Integer> result = filter( list, ( v ) -> {
            if ( v > 2 && v < 7 ) {
                return true;
            }
            return false;
        } );
        System.out.println( result );
    }

    /**
     * Predicate 를 Lambda 형식으로 좀 더  멋지게 보완해 보자.
     */
    @Test
    public void getNumbersCase5() {
        final List<Integer> list = Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 8, 9 );
        final List<Integer> result = filter( list, v -> v > 2 );
        final List<Integer> result2 = filter( result, v -> v < 7 );
        System.out.println( result2 );
    }

    /**
     * Function Composition
     * Predicate 함수를 이용 하는 Lambda 형식으로 훨씬 더  멋지게 보완해 보자.
     */
    @Test
    public void getNumbersCase6() {
        final Predicate<Integer> GE2 = n -> n > 2;
        final Predicate<Integer> LE7 = n -> n < 7;
        final List<Integer> list = Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 8, 9 );
        final List<Integer> result = filter( list, GE2.and( LE7 ) );
        System.out.println( result );
    }
}
