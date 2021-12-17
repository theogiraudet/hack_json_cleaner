package fr.istic;

import com.fasterxml.jackson.annotation.*;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "officialLanguage",
        "labelCode",
        "homeCountries",
        "homeCities",
        "deezerUrl",
        "spotifyUrl",
        "visits"
})
public class ArtistData {

    private final static String SPOTIFY_LINK = "https://open.spotify.com/";
    private final static String DEEZER_LINK = "https://www.deezer.com/fr/album/";

    private final String name;
    private List<String> countries;
    private List<String> cities;
    private final String spotify;
    private final String deezer;
    private final String lang;
    private final String labelCode;
    @JsonIgnore
    private final String edition;


    private final List<VisitedEdition> visits;


    public ArtistData(
            @JsonProperty(value = "artistes", required = true) String artistString,

            @JsonProperty(value = "origine_pays1") String homeCountry1,
            @JsonProperty(value = "origine_ville1") String homeCity1,
            @JsonProperty(value = "origine_pays2") String homeCountry2,
            @JsonProperty(value = "origine_ville2") String homeCity2,
            @JsonProperty(value = "origine_pays3") String homeCountry3,
            @JsonProperty(value = "origine_ville3") String homeCity3,
            @JsonProperty(value = "origine_pays4") String homeCountry4,
            @JsonProperty(value = "origine_ville4") String homeCity4,

            @JsonProperty(value = "1er_projet_atm") String project1,
            @JsonProperty(value = "1ere_date_timestamp") String date1,
            @JsonProperty(value = "1ere_salle") String theater1,
            @JsonProperty(value = "1ere_ville") String city1,

            @JsonProperty(value = "2eme_projet") String project2,
            @JsonProperty(value = "2eme_date_timestamp") String date2,
            @JsonProperty(value = "2eme_salle") String theater2,
            @JsonProperty(value = "2eme_ville") String city2,

            @JsonProperty(value = "3eme_projet") String project3,
            @JsonProperty(value = "3eme_date_timestamp") String date3,
            @JsonProperty(value = "3eme_salle") String theater3,
            @JsonProperty(value = "3eme_ville") String city3,

            @JsonProperty(value = "4eme_projet") String project4,
            @JsonProperty(value = "4eme_date_timestamp") String date4,
            @JsonProperty(value = "4eme_salle") String theater4,
            @JsonProperty(value = "4eme_ville") String city4,

            @JsonProperty(value = "5eme_projet") String project5,
            @JsonProperty(value = "5eme_date_timestamp") String date5,
            @JsonProperty(value = "5eme_salle") String theater5,
            @JsonProperty(value = "5eme_ville") String city5,

            @JsonProperty(value = "6eme_projet") String project6,
            @JsonProperty(value = "6eme_date_timestamp") String date6,
            @JsonProperty(value = "6eme_salle") String theater6,
            @JsonProperty(value = "6eme_ville") String city6,

            @JsonProperty(value = "spotify") String spotify,
            @JsonProperty(value = "deezer") String deezer,
            @JsonProperty(value = "cou_official_lang_code") String lang,
            @JsonProperty(value = "cou_iso3_code") String labelCode,
            @JsonProperty(value = "edition") String edition
    ) {
        super();
        this.name = artistString;
        this.lang = lang;
        this.labelCode = labelCode;
        this.edition = edition;

        this.countries = new LinkedList<>();
        this.cities = new LinkedList<>();
        if(homeCountry1 != null)
            countries.addAll(Arrays.asList(homeCountry1.split(" / ")));
        if(homeCountry2 != null)
            countries.addAll(Arrays.asList(homeCountry2.split(" / ")));
        if(homeCountry3 != null)
            countries.addAll(Arrays.asList(homeCountry3.split(" / ")));
        if(homeCountry4 != null)
            countries.addAll(Arrays.asList(homeCountry4.split(" / ")));
        if(countries.isEmpty())
            countries = null;

        if(homeCity1 != null)
            cities.addAll(Arrays.asList(homeCity1.split(" / ")));
        if(homeCity2 != null)
            cities.addAll(Arrays.asList(homeCity2.split(" / ")));
        if(homeCity3 != null)
            cities.addAll(Arrays.asList(homeCity3.split(" / ")));
        if(homeCity4 != null)
            cities.addAll(Arrays.asList(homeCity4.split(" / ")));

        if(cities.isEmpty())
            cities = null;

        this.spotify = spotify != null ? SPOTIFY_LINK + spotify.replace("spotify:", "").replace(":", "/") : null;
        this.deezer = deezer != null ? DEEZER_LINK + deezer : null;

        visits = new ArrayList<>(6);

        final var shows = new LinkedList<Show>();
        shows.add(addToList(project1, city1, theater1, date1));
        shows.add(addToList(project2, city2, theater2, date2));
        shows.add(addToList(project3, city3, theater3, date3));
        shows.add(addToList(project4, city4, theater4, date4));
        shows.add(addToList(project5, city5, theater5, date5));
        shows.add(addToList(project6, city6, theater6, date6));

        final var list = shows.stream()
                .filter(Objects::nonNull)
                .map(s -> Tuple.of(Utils.timestamp2year(Long.parseLong(s.getDate())), s))
                .collect(Collectors.groupingBy(t -> t._1))
                .entrySet()
                .stream()
                .map(e -> new VisitedEdition(e.getKey(), e.getValue()
                        .stream()
                        .map(Tuple::get_2)
                        .collect(Collectors.toList()))
                ).collect(Collectors.toList());
        visits.addAll(list);
    }

    private Show addToList(String project, String city, String theater, String date) {
        if (city != null || theater != null || date != null)
            return new Show(project, theater, city, date);
        return null;
    }

    @JsonIgnore
    public String getEdition() {
        return edition;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("homeCountries")
    public List<String> getCountries() {
        return countries;
    }

    @JsonProperty("homeCities")
    public List<String> getCities() {
        return cities;
    }

    @JsonProperty("officialLanguage")
    public String getLang() {
        return lang;
    }

    @JsonProperty("spotifyUrl")
    public String getSpotify() {
        return spotify;
    }

    @JsonProperty("deezerUrl")
    public String getDeezer() {
        return deezer;
    }

    @JsonProperty("visits")
    public List<VisitedEdition> getVisits() {
        return Collections.unmodifiableList(visits);
    }

    @JsonProperty("labelCode")
    public String getLabelCode() {
        return labelCode;
    }

    @Override
    public String toString() {
        return "ArtistData{" +
                "name='" + name + '\'' +
                ", countries=" + countries +
                ", cities=" + cities +
                ", spotify='" + spotify + '\'' +
                ", deezer='" + deezer + '\'' +
                ", lang='" + lang + '\'' +
                ", labelCode='" + labelCode + '\'' +
                ", visits=" + visits +
                '}';
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "number",
            "year",
            "shows"
    })
    public static class VisitedEdition {
        final int year;
        final int number;
        final List<Show> shows;

        public VisitedEdition(int year, List<Show> shows) {
            this.year = year;
            this.number = Utils.date2edition(year);
            this.shows = new LinkedList<>(shows);
        }

        @JsonProperty("year")
        public int getYear() {
            return year;
        }

        @JsonProperty("number")
        public int getNumber() {
            return number;
        }

        public void addShow(Show show) {
            shows.add(show);
        }

        @JsonProperty("shows")
        public List<Show> getShows() {
            return shows;
        }

        @Override
        public String toString() {
            return "VisitedEdition{" +
                    "year=" + year +
                    ", number=" + number +
                    ", shows=" + shows +
                    '}';
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "project",
            "date",
            "city",
            "theater"
    })
    public static class Show {

        final String theater;
        final String project;
        final String city;
        final String date;

        private Show(String project, String city, String theater, String date) {
            super();
            this.theater = theater;
            this.city = city;
            this.date = date;
            this.project = project;
        }

        @JsonProperty("city")
        public String getCity() {
            return city;
        }

        @JsonProperty("date")
        public String getDate() {
            return date;
        }

        @JsonProperty("project")
        public String getProject() {
            return project;
        }

        @JsonProperty("theater")
        public String getTheater() {
            return theater;
        }

        @Override
        public String toString() {
            return "Show{" +
                    "theater='" + theater + '\'' +
                    ", project='" + project + '\'' +
                    ", city='" + city + '\'' +
                    ", date='" + date + '\'' +
                    '}';
        }
    }
}
