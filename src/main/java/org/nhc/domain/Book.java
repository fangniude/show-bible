package org.nhc.domain;

import lombok.*;

import java.util.NavigableMap;

/**
 * @author lewis
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"version", "bookNames"}, includeFieldNames = false)
public class Book {
    private String version;
    private BookNames bookNames;
    private NavigableMap<Integer, NavigableMap<Integer, Verses>> verses;
}
