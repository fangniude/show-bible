package org.nhc.domain;

import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "number")
@ToString(of = {"number", "osis", "chineseName"}, includeFieldNames = false)
public class BookNames {
    private static List<BookNames> all = Lists.newArrayList();

    private int number;
    private String osis;
    private String chineseName;
    private String pinyin;

    static {
        all.add(new BookNames(1, "Gen", "创世记", "chuangshiji"));
        all.add(new BookNames(2, "Exod", "出埃及记", "chuaijiji"));
        all.add(new BookNames(3, "Lev", "利未记", "liweiji"));
        all.add(new BookNames(4, "Num", "民数记", "minshuji"));
        all.add(new BookNames(5, "Deut", "申命记", "shenmingji"));
        all.add(new BookNames(6, "Josh", "约书亚记", "yueshuyaji"));
        all.add(new BookNames(7, "Judg", "士师记", "shishiji"));
        all.add(new BookNames(8, "Ruth", "路得记", "ludeji"));
        all.add(new BookNames(9, "1Sam", "撒母耳记上", "samuerjishang"));
        all.add(new BookNames(10, "2Sam", "撒母耳记下", "samuerjixia"));
        all.add(new BookNames(11, "1Kgs", "列王纪上", "liewangjishang"));
        all.add(new BookNames(12, "2Kgs", "列王纪下", "liewangjixia"));
        all.add(new BookNames(13, "1Chr", "历代志上", "lidaizhishang"));
        all.add(new BookNames(14, "2Chr", "历代志下", "lidaizhixia"));
        all.add(new BookNames(15, "Ezra", "以斯拉记", "yisilaji"));
        all.add(new BookNames(16, "Neh", "尼希米记", "niximiji"));
        all.add(new BookNames(17, "Esth", "以斯帖记", "yisitieji"));
        all.add(new BookNames(18, "Job", "约伯记", "yueboji"));
        all.add(new BookNames(19, "Ps", "诗篇", "shipian"));
        all.add(new BookNames(20, "Prov", "箴言", "zhenyan"));
        all.add(new BookNames(21, "Eccl", "传道书", "chuandaoshu"));
        all.add(new BookNames(22, "Song", "雅歌", "yage"));
        all.add(new BookNames(23, "Isa", "以赛亚书", "yisaiyashu"));
        all.add(new BookNames(24, "Jer", "耶利米书", "yelimishu"));
        all.add(new BookNames(25, "Lam", "耶利米哀歌", "yelimiaige"));
        all.add(new BookNames(26, "Ezek", "以西结书", "yixijieshu"));
        all.add(new BookNames(27, "Dan", "但以理书", "danyilishu"));
        all.add(new BookNames(28, "Hos", "何西阿书", "hexiashu"));
        all.add(new BookNames(29, "Joel", "约珥书", "yueershu"));
        all.add(new BookNames(30, "Amos", "阿摩司书", "amosishu"));
        all.add(new BookNames(31, "Obad", "俄巴底亚书", "ebadiyashu"));
        all.add(new BookNames(32, "Jonah", "约拿书", "yuenashu"));
        all.add(new BookNames(33, "Mic", "弥迦书", "mijiashu"));
        all.add(new BookNames(34, "Nah", "那鸿书", "nahongshu"));
        all.add(new BookNames(35, "Hab", "哈巴谷书", "habagushu"));
        all.add(new BookNames(36, "Zeph", "西番雅书", "xifanyashu"));
        all.add(new BookNames(37, "Hag", "哈该书", "hagaishu"));
        all.add(new BookNames(38, "Zech", "撒迦利亚书", "sajialiyashu"));
        all.add(new BookNames(39, "Mal", "玛拉基书", "malajishu"));
        all.add(new BookNames(40, "Matt", "马太福音", "mataifuyin"));
        all.add(new BookNames(41, "Mark", "马可福音", "makefuyin"));
        all.add(new BookNames(42, "Luke", "路加福音", "lujiafuyin"));
        all.add(new BookNames(43, "John", "约翰福音", "yuehanfuyin"));
        all.add(new BookNames(44, "Acts", "使徒行传", "shituxingchuan"));
        all.add(new BookNames(45, "Rom", "罗马书", "luomashu"));
        all.add(new BookNames(46, "1Cor", "哥林多前书", "gelinduoqianshu"));
        all.add(new BookNames(47, "2Cor", "哥林多后书", "gelinduohoushu"));
        all.add(new BookNames(48, "Gal", "加拉太书", "jialataishu"));
        all.add(new BookNames(49, "Eph", "以弗所书", "yifusuoshu"));
        all.add(new BookNames(50, "Phil", "腓立比书", "feilibishu"));
        all.add(new BookNames(51, "Col", "歌罗西书", "geluoxishu"));
        all.add(new BookNames(52, "1Thess", "帖撒罗尼迦前书", "tiesaluonijiaqianshu"));
        all.add(new BookNames(53, "2Thess", "帖撒罗尼迦后书", "tiesaluonijiahoushu"));
        all.add(new BookNames(54, "1Tim", "提摩太前书", "timotaiqianshu"));
        all.add(new BookNames(55, "2Tim", "提摩太后书", "timotaihoushu"));
        all.add(new BookNames(56, "Titus", "提多书", "tiduoshu"));
        all.add(new BookNames(57, "Phlm", "腓利门书", "feilimenshu"));
        all.add(new BookNames(58, "Heb", "希伯来书", "xibolaishu"));
        all.add(new BookNames(59, "Jas", "雅各书", "yageshu"));
        all.add(new BookNames(60, "1Pet", "彼得前书", "bideqianshu"));
        all.add(new BookNames(61, "2Pet", "彼得后书", "bidehoushu"));
        all.add(new BookNames(62, "1John", "约翰一书", "yuehanyishu"));
        all.add(new BookNames(63, "2John", "约翰二书", "yuehanershu"));
        all.add(new BookNames(64, "3John", "约翰三书", "yuehansanshu"));
        all.add(new BookNames(65, "Jude", "犹大书", "youdashu"));
        all.add(new BookNames(66, "Rev", "启示录", "qishilu"));
    }

    public static List<BookNames> all() {
        return all;
    }

    public static Map<Integer, BookNames> numMap() {
        return all.stream().collect(Collectors.toMap(BookNames::getNumber, Function.identity()));
    }

    public static Map<String, BookNames> osisMap() {
        return all.stream().collect(Collectors.toMap(BookNames::getOsis, Function.identity()));
    }
}
