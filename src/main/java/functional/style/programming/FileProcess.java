package functional.style.programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class FileProcess {

    private void prepare()
        throws IOException {
        File file = new File( "./test.txt" );
        FileWriter writer = new FileWriter( file );
        writer.write( "apple banana melon pineapple " );
        writer.write( "mango cherry jamon kiwi" );
        writer.close();
    }

    private void removeFile() {
        File file = new File( "./test.txt" );
        file.delete();
    }

    @Test
    public void ut1001_oopStyle() {
        try {
            prepare();
            File file = new File( "./test.txt" );
            List<String> list = new ArrayList<>();
            FileReader reader = new FileReader( file );
            BufferedReader br = new BufferedReader( reader );
            String line = br.readLine();
            while ( line != null ) {
                if ( line != null ) {
                    String[] words = line.split( "[\\s]+" );
                    for ( String word : words ) {
                        if ( !list.contains( word ) ) {
                            list.add( word );
                        }
                    }
                }
                line = br.readLine();
            }
            br.close();
            Collections.sort( list );
            System.out.println( list );
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            removeFile();
        }
    }

    @Test
    public void ut1002_functionalStyle() {
        try {
            prepare();
            final Stream<String> lines = Files.lines( Paths.get( "./test.txt" ) );
            System.out.println( lines.map( line -> line.split( "[\\s]+" ) ).flatMap( Arrays::stream ).distinct()
                                     .sorted().collect( Collectors.toList() ) );
            lines.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            removeFile();
        }
    }
}
