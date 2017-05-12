package com.fp;

import static com.fp.Stream.*;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

abstract class Stream<T> {

    static private final Empty EMPTY = new Empty();

    static public <T> Stream<T> cons( Supplier<T> head, Supplier<Stream<T>> tail ) {
        return new Cons<>( head, tail );
    }

    @SuppressWarnings("unchecked")
    static public <T> Stream<T> empty() {
        return (Stream<T>) EMPTY;
    }

    
    @SafeVarargs
    static public <T> Stream<T> stream( final T... ts ) {
        return stream_( 0, ts );
    }

    @SafeVarargs
    static public <T> Stream<T> stream_( int i, final T... ts ) {
        if ( ts.length == i ) {
            return empty();
        }
        return cons( () -> ts[i], () -> stream_( i + 1, ts ) );
    }

    static public <T> Stream<T> iterate( Function<T, T> f, T t ) {
        return cons( () -> t, () -> iterate( f, f.apply( t ) ) );
    }

    // concatMap :: Stream<A> -> (A -> Stream<B>) -> Stream<B>
    public <B> Stream<B> concatMap( Function<T, Stream<B>> f ) {
        if ( isEmpty() ) {
            return empty();
        }
        return f.apply( head() ).append( () -> tail().concatMap( f ) );
    }

    private Stream<T> append( Supplier<Stream<T>> that ) {
        if ( isEmpty() ) {
            return that.get();
        }
        return cons( () -> head(), () -> tail().append( that ) );
    }

    /**
     * [1,1,1,1,1,2,2,2.....] -> [ [1,1,1,1,1],.....]
     * group :: Stream<T> -> Stream<Stream<T>>
     */
    public Stream<Stream<T>> group() {
        if ( isEmpty() ) {
            return empty();
        }
        return cons( () -> this.takeWhile( x -> head().equals( x ) ),
                     () -> this.dropWhile( x -> head().equals( x ) ).group() );
    }

    public Stream<T> takeWhile( Predicate<T> f ) {
        if ( isEmpty() ) {
            return empty();
        }
        return f.test( head() ) ? cons( () -> head(), () -> tail().takeWhile( f ) ) : empty();
    }

    public Stream<T> dropWhile( Predicate<T> f ) {
        if ( isEmpty() ) {
            return empty();
        }
        Stream<T> cur = this;
        while ( !cur.isEmpty() && f.test( cur.head() ) ) {
            cur = cur.tail();
        }
        return cur;
    }

    abstract public T head();

    abstract public Stream<T> tail();

    abstract public boolean isEmpty();

    static class Cons<T>
        extends Stream<T> {

        private Supplier<T> head;

        private Supplier<Stream<T>> tail;

        private T head_;

        private Stream<T> tail_;

        /**
         * @param head
         * @param tail
         */
        public Cons( Supplier<T> head, Supplier<Stream<T>> tail ) {
            this.head = head;
            this.tail = tail;
        }

        /**
         * @see main.java.Stream#head()
         */
        @Override
        public T head() {
            if ( this.head != null ) {
                head_ = head.get();
                head = null;
            }
            return head_;
        }

        /**
         * @see main.java.Stream#tail()
         */
        @Override
        public Stream<T> tail() {
            if ( this.tail != null ) {
                tail_ = tail.get();
                tail = null;
            }
            return tail_;
        }

        /**
         * @see main.java.Stream#isEmpty()
         */
        @Override
        public boolean isEmpty() {
            return false;
        }
    }

    static class Empty
        extends Stream<Object> {

        /**
         * @see main.java.Stream#head()
         */
        @Override
        public Object head() {
            throw new NoSuchElementException();
        }

        /**
         * @see main.java.Stream#tail()
         */
        @Override
        public Stream<Object> tail() {
            throw new NoSuchElementException();
        }

        /**
         * @see main.java.Stream#isEmpty()
         */
        @Override
        public boolean isEmpty() {
            return true;
        }
    }

    public void log() {
        Stream<T> cur = this;
        int i = 0;
        while ( !cur.isEmpty() && i < 5 ) {
            System.out.println( cur.head() );
            cur = cur.tail();
            ++i;
        }
    }

    public int length() {
        Stream<T> cur = this;
        int size = 0;
        while ( !cur.isEmpty() ) {
            cur = cur.tail();
            ++size;
        }
        return size;
    }

    public T get( int i ) {
        return drop( i ).head();
    }

    private Stream<T> drop( int i ) {
        Stream<T> cur = this;
        while ( i-- > 0 ) {
            cur = cur.tail();
        }
        return cur;
    }
}

/**
 * @see https://www.youtube.com/watch?v=ARtLcQWBLKI
 */
public class AntNumber {

    static Stream<Stream<Integer>> ants() {
        Function<Stream<Integer>, Stream<Integer>> f = line -> line.group()
                                                                   .concatMap( g -> stream( g.head(), g.length() ) );
        return iterate( f, stream( 1 ) );
    }

    @Test
    public void testLog()
        throws Exception {
        Stream<Integer> natural = iterate( n -> n + 1, 0 );
        natural.concatMap( n -> stream( n, n ) ).log();
    }

    @Test
    public void testSize()
        throws Exception {
        Stream<Integer> stream = stream( 1, 2, 3, 4, 5 );
        System.out.println( stream.length() );
        assertEquals( 5, stream.length() );
    }

    @Test
    public void testGroup()
        throws Exception {
        Stream<Integer> stream = stream( 1, 1, 3, 1 );
        stream.group().head().log();
        //assertEquals( 5, stream.length() );
    }

    @Test
    public void testAnt()
        throws Exception {
        System.out.println( AntNumber.ants().get( 1000000 ).get( 1000000 ) );
    }

    /**
     * @param args
     */
    public static void main( String[] args ) {
        //        Stream<Integer> natural = iterate( n -> n + 1, 0 );
        //        natural.concatMap( n -> stream( n, n ) ).log();
        // natural.log();
        //        Stream<Integer> oneTwo = cons( () -> 1, () -> cons( () -> 2, () -> empty() ) );
        //        System.out.println( oneTwo.head() );
        //        System.out.println( oneTwo.tail().head() );
    }
}
