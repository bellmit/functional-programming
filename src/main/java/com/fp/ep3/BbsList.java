package com.fp.ep3;

import static com.fp.ep3.Utils.accepting;
import static com.fp.ep3.Utils.bbsComparator;
import static com.fp.ep3.Utils.currentWeek;
import static com.fp.ep3.Utils.doIfTrue;
import static com.fp.ep3.Utils.isWithinOneWeek;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.Test;

final class Utils {

    public static Date currentWeek( int day ) {
        Calendar cal = Calendar.getInstance();
        cal.setTime( Date.from( LocalDateTime.now().atZone( ZoneId.systemDefault() ).toInstant() ) );
        cal.add( Calendar.DATE, ( day * -1 ) );
        return cal.getTime();
    }

    public static Comparator<Bbs> bbsComparator() {
        return new Comparator<Bbs>() {

            @Override
            public int compare( Bbs b, Bbs b2 ) {
                if ( b.getWriteDate().getTime() > b2.getWriteDate().getTime() ) {
                    return 1;
                }
                return -1;
            }
        };
    }

    public static boolean isWithinOneWeek( Date date ) {
        Calendar weekAgo = Calendar.getInstance();
        weekAgo.add( Calendar.DATE, -7 );
        Calendar dcal = Calendar.getInstance();
        dcal.setTime( date );
        return weekAgo.before( dcal );
    }

    public static boolean isWithinOneWeek( Bbs bbs ) {
        final Date date = bbs.getWriteDate();
        Calendar weekAgo = Calendar.getInstance();
        weekAgo.add( Calendar.DATE, -7 );
        Calendar dcal = Calendar.getInstance();
        dcal.setTime( date );
        return weekAgo.before( dcal );
    }

    public static <O, T> Consumer<O> accepting( final BiConsumer<? super O, ? super T> function, final T param ) {
        Objects.requireNonNull( function, "The function cannot be null." );
        return object -> function.accept( object, param );
    }

    public static <T> void doIfTrue( boolean isTrue, Runnable runnable ) {
        if ( isTrue ) {
            runnable.run();
        }
    }
}

class Bbs {

    public Bbs( int i, boolean b, Date newDate ) {
        this.seq = i;
        this.isNew = b;
        this.writeDate = newDate;
    }

    private int seq;

    private boolean isNew;

    private Date writeDate;

    public int getSeq() {
        return seq;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew( boolean isNew ) {
        this.isNew = isNew;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate( Date writeDate ) {
        this.writeDate = writeDate;
    }

    public Bbs makeNewFlag( boolean isNew ) {
        if ( isNew ) {
            this.isNew = true;
        }
        return this;
    }

    public Bbs isNewPredicate( Predicate<Bbs> pre ) {
        if ( pre.test( this ) ) {
            this.isNew = true;
        }
        return this;
    }

    public Bbs setFieldIfTrue( Predicate<Bbs> pre, Consumer<Bbs> setter ) {
        if ( pre.test( this ) ) {
            setter.accept( this );
        }
        return this;
    }

    @Override
    public String toString() {
        return "Bbs [seq=" + seq + ", isNew=" + isNew + ", writeDate=" + writeDate + "]";
    }
}

public class BbsList {

    @Test
    public void testWeek()
        throws Exception {
        System.out.println( isWithinOneWeek( currentWeek( 2 ) ) );
    }

    List<Bbs> sources() {
        return Arrays.asList( new Bbs( 1, false, currentWeek( 0 ) ), new Bbs( 2, false, currentWeek( 12 ) ),
                              new Bbs( 3, false, currentWeek( 2 ) ), new Bbs( 4, false, currentWeek( 11 ) ) );
    }

    @Test
    public void testFP0001_sorting()
        throws Exception {
        List<Bbs> sources = sources();
        System.out.println( "\n=========================================" );
        System.out.println( sources.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
        List<Bbs> result = sources.stream().sorted( bbsComparator() ).collect( Collectors.toList() );
        System.out.println( "\n=========================================" );
        System.out.println( result.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
    }

    @Test
    public void testFP0002_sorting2()
        throws Exception {
        List<Bbs> sources = sources();
        System.out.println( "\n=========================================" );
        System.out.println( sources.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
        List<Bbs> result = sources.stream().sorted( Comparator.comparing( Bbs::getWriteDate ) )
                                  .collect( Collectors.toList() );
        System.out.println( "\n=========================================" );
        System.out.println( result.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
    }

    @Test
    public void testFP0003_setNew_byOOP()
        throws Exception {
        final List<Bbs> sources = sources();
        System.out.println( "\n=========================================" );
        System.out.println( sources.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
        List<Bbs> result = sources.stream().map( bbs -> bbs.makeNewFlag( isWithinOneWeek( bbs.getWriteDate() ) ) )
                                  .collect( Collectors.toList() );
        System.out.println( "\n=========================================" );
        System.out.println( result.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
    }

    @Test
    public void testFP0004_setNew_isNewPredicate()
        throws Exception {
        final List<Bbs> sources = sources();
        System.out.println( "\n=========================================" );
        System.out.println( sources.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
        List<Bbs> result = sources.stream().map( bbs -> bbs.isNewPredicate( b -> isWithinOneWeek( b.getWriteDate() ) ) )
                                  .collect( Collectors.toList() );
        System.out.println( "\n=========================================" );
        System.out.println( result.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
    }

    @Test
    public void testFP0005_setNew_setFieldIfTrue()
        throws Exception {
        final List<Bbs> sources = sources();
        System.out.println( "\n=========================================" );
        System.out.println( sources.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
        List<Bbs> result = sources.stream()
                                  .map( bbs -> bbs.setFieldIfTrue( b -> isWithinOneWeek( b.getWriteDate() ),
                                                                   b -> b.setNew( true ) ) )
                                  .collect( Collectors.toList() );
        System.out.println( "\n=========================================" );
        System.out.println( result.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
    }

    @Test
    public void testFP0006_setNew_setFieldIfTrue_byConsumer()
        throws Exception {
        final List<Bbs> sources = sources();
        System.out.println( "\n=========================================" );
        System.out.println( sources.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
        List<Bbs> result = sources.stream()
                                  .map( bbs -> bbs.setFieldIfTrue( Utils::isWithinOneWeek,
                                                                   b -> b.setWriteDate( new Date() ) ) )
                                  .collect( Collectors.toList() );
        System.out.println( "\n=========================================" );
        System.out.println( result.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
    }

    @Test
    public void testFP0007_setNew_setFieldIfTrue_byAccepting()
        throws Exception {
        final List<Bbs> sources = sources();
        System.out.println( "\n=========================================" );
        System.out.println( sources.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
        List<Bbs> result = sources.stream()
                                  .map( bbs -> bbs.setFieldIfTrue( Utils::isWithinOneWeek,
                                                                   accepting( Bbs::setNew, true ) ) )
                                  .collect( Collectors.toList() );
        System.out.println( "\n=========================================" );
        System.out.println( result.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
    }

    /**
     * peek 는 반환값을 가지지 않는 경우 사용
     * @throws Exception
     */
    @Test
    public void testFP0008_setNew_doIfTrue_byRunner()
        throws Exception {
        final List<Bbs> sources = sources();
        System.out.println( "\n=========================================" );
        System.out.println( sources.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
        List<Bbs> result = sources.stream().peek( bbs -> doIfTrue( isWithinOneWeek( bbs ), () -> bbs.setNew( true ) ) )
                                  .collect( Collectors.toList() );
        System.out.println( "\n=========================================" );
        System.out.println( result.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
    }
}
