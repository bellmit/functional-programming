package com.fp.ep1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;

public class MapFilterReduce {

    private List<Integer> sources() {
        return Arrays.asList( 1, 2, 3, 4, 5, 6, 7 );
    }

    private static final Integer max( Integer a, Integer b ) {
        if ( a > b ) {
            return a;
        }
        return b;
    }

    private static final Integer min( Integer a, Integer b ) {
        if ( a < b ) {
            return a;
        }
        return b;
    }

    private static final Integer square( Integer a ) {
        return a * a;
    }

    @Test
    public void ut1001_MapFilterReduce()
        throws Exception {
        /**
         * <pre>
         * 1. process map
         * 2. process filter
         * </pre>
         */
        List<Integer> results = sources().stream().map( x -> {
            return square( x );
        } ).filter( ( x ) -> {
            return ( x % 2 != 0 );
        } ).collect( Collectors.toList() );
        System.out.println( results.stream().map( x -> String.valueOf( x ) ).collect( Collectors.joining( "\n" ) ) );
        Optional<Integer> minVal = results.stream().reduce( ( a, b ) -> {
            return min( a, b );
        } );
        System.out.println( "min: " + minVal.get() );
        Optional<Integer> maxVal = results.stream().reduce( ( a, b ) -> {
            return max( a, b );
        } );
        System.out.println( "max: " + maxVal.get() );
    }

    @Test
    public void ut1002_MinMax()
        throws Exception {
        List<Integer> results = sources();
        Integer min = results.stream().min( new Comparator<Integer>() {

            @Override
            public int compare( Integer o1, Integer o2 ) {
                if ( o1 < o2 ) {
                    return -1;
                } else if ( o1 > o2 ) {
                    return 1;
                }
                return 0;
            }
        } ).get();
        System.out.println( "case 2 min: " + min );
        Integer max = results.stream().max( new Comparator<Integer>() {

            @Override
            public int compare( Integer o1, Integer o2 ) {
                if ( o1 < o2 ) {
                    return -1;
                } else if ( o1 > o2 ) {
                    return 1;
                }
                return 0;
            }
        } ).get();
        System.out.println( "case 2 max: " + max );
    }
}
