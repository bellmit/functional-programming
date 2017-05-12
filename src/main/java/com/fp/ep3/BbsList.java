package com.fp.ep3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class Bbs {

    private int seq;

    private boolean isNew;

    private Date writeDate;
}

public class BbsList {

    Date newDate( int year, int month, int dayOfMonth ) {
        return Date.from( LocalDateTime.of( year, month, dayOfMonth, 1, 1 ).atZone( ZoneId.systemDefault() )
                                       .toInstant() );
    }

    Comparator<Bbs> bbsComparator() {
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

    @Test
    public void testFP0001()
        throws Exception {
        List<Bbs> sources = Arrays.asList( new Bbs( 1, false, newDate( 2017, 5, 9 ) ),
                                           new Bbs( 2, false, newDate( 2017, 5, 12 ) ),
                                           new Bbs( 3, false, newDate( 2017, 5, 10 ) ),
                                           new Bbs( 4, false, newDate( 2017, 5, 11 ) ) );
        System.out.println( "\n=========================================" );
        System.out.println( sources.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
        List<Bbs> result = sources.stream().sorted( bbsComparator() ).collect( Collectors.toList() );
        System.out.println( "\n=========================================" );
        System.out.println( result.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
    }

    @Test
    public void testFP0002()
        throws Exception {
        List<Bbs> sources = Arrays.asList( new Bbs( 1, false, newDate( 2017, 5, 9 ) ),
                                           new Bbs( 2, false, newDate( 2017, 5, 12 ) ),
                                           new Bbs( 3, false, newDate( 2017, 5, 10 ) ),
                                           new Bbs( 4, false, newDate( 2017, 5, 11 ) ) );
        System.out.println( "\n=========================================" );
        System.out.println( sources.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
        List<Bbs> result = sources.stream().sorted( Comparator.comparing( Bbs::getWriteDate ) )
                                  .collect( Collectors.toList() );
        System.out.println( "\n=========================================" );
        System.out.println( result.stream().map( String::valueOf ).collect( Collectors.joining( "\n" ) ) );
    }
}
