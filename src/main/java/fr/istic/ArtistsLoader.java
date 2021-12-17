package fr.istic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ArtistsLoader {

    public List<ArtistData> load() {
        return load(ArtistsLoader.class.getClassLoader().getResourceAsStream("database/out.json"));
    }

    public List<ArtistData> load(InputStream stream) {
        try {
            final ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return om.readValue(stream, new TypeReference<List<Data>>() {})
                    .stream()
                    .map(Data::getArtistData)
                    .collect(Collectors.toUnmodifiableList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String... args) throws IOException {
        final var loader = new ArtistsLoader();
        final var list = loader.load();
        /*list.stream().collect(Collectors.groupingBy(ArtistData::getName))
                .entrySet().stream().map(e -> Tuple3.of(e.getKey(), e.getValue().size(), e.getValue()))
                .filter(t -> t._2 > 1)
                .forEach(ad -> System.out.println(ad));*/
        var filteredList = list.stream().filter(ad -> ad.getEdition() != null)
                .collect(Collectors.toList());
        System.out.println(list.size());

        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);;
        objectMapper.writeValue(new File("result.json"), filteredList);
    }

}
