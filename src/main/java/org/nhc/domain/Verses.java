package org.nhc.domain;

import io.ebean.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Slf4j
@Table(name = "verses")
public class Verses extends Model {
    @Column(name = "book")
    private String osis;

    @Column(name = "verse")
    private String verse;

    @Column(name = "unformatted")
    private String content;

    public int chapter() {
        return Integer.valueOf(verse.split("\\.")[0]);
    }

    public int verse() {
        return Integer.valueOf(fill(verse.split("\\.")[1]));
    }

    private String fill(String str) {
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() < 3) {
            sb.append('0');
        }
        return sb.toString();
    }
}
