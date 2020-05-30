package org.nhc;

import com.google.common.collect.Maps;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.nhc.domain.Book;
import org.nhc.domain.BookNames;
import org.nhc.domain.Verses;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class Bible {

    private final NavigableMap<BookNames, Book> bookMap;

    public Bible() {
        String version = "cuv";
        bookMap = loadVersion(version);
    }

    public Verses fetch(BookNames bookName, int chapter, int verse) {
        return bookMap.get(bookName).getVerses().get(chapter).get(verse);
    }

    public List<Verses> between(BookNames bookName, int chapter, int from, int to) {
        return new ArrayList<>(bookMap.get(bookName).getVerses().get(chapter).subMap(from, true, to, true).values());
    }

    private static NavigableMap<BookNames, Book> loadVersion(String version) {
        ServerConfig sc = new ServerConfig();
        sc.setDataSource(dataSource(version));
        sc.addPackage("org.nhc.domain");

        EbeanServer ebeanServer = EbeanServerFactory.create(sc);
        List<Verses> list = ebeanServer.find(Verses.class).findList();

        Map<String, Map<Integer, Map<Integer, Verses>>> verses = list.stream().collect(Collectors.groupingBy(Verses::getOsis, Collectors.groupingBy(Verses::chapter, Collectors.toMap(Verses::verse, Function.identity()))));

        NavigableMap<BookNames, Book> bookMap = new TreeMap<>(Comparator.comparing(BookNames::getNumber));
        verses.forEach((osis, map) -> {
            BookNames bookNames = BookNames.osisMap().get(osis);
            if (bookNames == null) {
                throw new RuntimeException(String.format("book not found: %s", osis));
            }


            NavigableMap<Integer, NavigableMap<Integer, Verses>> treeMap = Maps.newTreeMap();
            for (Map.Entry<Integer, Map<Integer, Verses>> e : map.entrySet()) {
                treeMap.put(e.getKey(), new TreeMap<>(e.getValue()));
            }

            bookMap.put(bookNames, new Book(version, bookNames, treeMap));
        });
        return bookMap;
    }

    private static DataSource dataSource(String version) {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(String.format("jdbc:sqlite::resource:versions/%s.sqlite3", version));

        return ds;
    }
}
