package com.fp.ep2;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CurryingAndCompositionFunction {

    @Test
    public void ut1001_Currying() {
        // Create a function that adds 2 integers
        BiFunction<Integer, Integer, Integer> adder = ( a, b ) -> a + b;
        // And a function that takes an integer and returns a function
        Function<Integer, Function<Integer, Integer>> currier = a -> b -> adder.apply( a, b );
        // Call apply 4 to currier (to get a function back)
        Function<Integer, Integer> curried = currier.apply( 4 );
        // Results
        System.out.printf( "Simple Curry Example: %d\n", curried.apply( 3 ) ); // ( 4 + 3 )
    }

    @Test
    public void ut1002_Composition()
        throws Exception {
        // A function that adds 3
        Function<Integer, Integer> add3 = ( a ) -> a + 3;
        // And a function that multiplies by 2
        Function<Integer, Integer> times2 = ( a ) -> a * 2;
        // Compose add with times
        Function<Integer, Integer> composedA = add3.compose( times2 );
        // And compose times with add
        Function<Integer, Integer> composedB = times2.compose( add3 );
        // Results
        System.out.printf( "Times then add: %d\n", composedA.apply( 6 ) );  // ( 6 * 2 ) + 3
        System.out.printf( "Add then times: %d\n", composedB.apply( 6 ) );  // ( 6 + 3 ) * 2
    }

    static public <A, B, C> Function<A, Function<B, C>> curry( final BiFunction<A, B, C> f ) {
        return ( A a ) -> ( B b ) -> f.apply( a, b );
    }

    static public <A, B, C> BiFunction<A, B, C> uncurry( Function<A, Function<B, C>> f ) {
        return ( A a, B b ) -> f.apply( a ).apply( b );
    }

    @Test
    public void ut1003_Currying_Add()
        throws Exception {
        BiFunction<Integer, Integer, Integer> add = ( a, b ) -> a + b;
        Function<Integer, Function<Integer, Integer>> currier = curry( add );
        System.out.println( "Curried add: " + currier.apply( 3 ).apply( 5 ) );
        // uncurry 는, 개별 함수로 currying 된 함수를 합성 함술 전환 하고, 결과가 같은지 검증 하는데 사용될 수 있다.
        BiFunction<Integer, Integer, Integer> addComp = uncurry( currier );
        System.out.println( "Uncurried add: " + addComp.apply( 3, 5 ) );
    }

    @Test
    public void ut1004_Currying_Mul()
        throws Exception {
        BiFunction<Integer, Integer, Integer> mul = ( a, b ) -> a * b;
        Function<Integer, Function<Integer, Integer>> currier = curry( mul );
        System.out.println( "Curried mul: " + currier.apply( 3 ).apply( 5 ) );
        // uncurry 는, 개별 함수로 currying 된 함수를 합성 함술 전환 하고, 결과가 같은지 검증 하는데 사용될 수 있다.
        BiFunction<Integer, Integer, Integer> addComp = uncurry( currier );
        System.out.println( "Uncurried mul: " + addComp.apply( 3, 5 ) );
    }
}
