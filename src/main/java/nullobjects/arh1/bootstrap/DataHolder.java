package nullobjects.arh1.bootstrap;

import jakarta.annotation.PostConstruct;
import nullobjects.arh1.model.MapMarker;
import nullobjects.arh1.repository.MapRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    private static List<MapMarker> mapMarkers;

    private final MapRepository mapRepository;
    public DataHolder(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
        mapMarkers = new ArrayList<>();
    }

    @PostConstruct
    public void init(){
        if (mapRepository.findAll().isEmpty()) {
            mapMarkers.add(new MapMarker("Arte", "077834150, http://www.artonline.mk/, Boulevard Mitropolit Teodosij Gologanov 49", "Skopje", "/images/ARTE Galerija.jpg", 1.5, 60, 60, 41.99491581140953, 21.42130350535809));
            mapMarkers.add(new MapMarker("Zograf", "075584909, Dame Gruev", "Skopje", "/images/Art Gallery Zograf.JPG", 1.5, 60, 60, 41.99730016371808, 21.427663092023362));
            mapMarkers.add(new MapMarker("Daleria", "070300974, Dimitrie Cupovski 3", "Skopje", "/images/Art Gallery „Daleria“.jpg", 1.5, 60, 60, 42.00195409802084, 21.42461300829461));
            mapMarkers.add(new MapMarker("Bezisten", "023117150", "Skopje", "/images/Art Gallery Bezisten.png", 1.5, 60, 60, 41.994670891767875, 21.43045730630835));
            mapMarkers.add(new MapMarker("Martin", "077566233, http://www.artonline.mk/, Ss Cyril & Methodius", "Skopje", "/images/Gallery Martin.jpg", 1.5, 60, 60, 41.99363045955053, 21.425193531866014));
            mapMarkers.add(new MapMarker("Marta", "070691251 Blvd. Saint Clement of Ohrid 45", "Skopje", "/images/Art Mgm Gallery.jpg", 1.5, 60, 60, 42.01100546381839, 21.40570773620265));
            mapMarkers.add(new MapMarker("Kol", "071866354, 11th October St.", "Skopje", "/images/Kolektiv Gallery.jpg", 1.5, 60, 60, 41.996086039694575, 21.431969833161013));
            mapMarkers.add(new MapMarker("Gral", "023230615, http://www.gral.com.mk/, Blvd. Saint Clement of Ohrid 45", "Skopje", "/images/Gral Gallery.jpeg", 1.5, 60, 60, 41.99747568687593, 21.425561383907795));
            mapMarkers.add(new MapMarker("Pioneri", "070356202, http://pioneri.net/, Mitropolit Teodosij Gologanov 74", "Skopje", "/images/Pioneri Art platform gallery.jpeg", 1.5, 60, 60, 41.996525459498166, 21.40680432224659));
            mapMarkers.add(new MapMarker("Mala", "078675271, Boulevard Ilinden 65", "Skopje", "/images/MAЛА.jpg", 1.5, 60, 60, 42.002083516549824, 21.42366768038797));
            mapMarkers.add(new MapMarker("Artida", "071719126", "Skopje", "/images/Atelje ARTIDEA.jpg", 1.5,60, 60, 41.99934938805174, 21.42391253164884));
            mapMarkers.add(new MapMarker("Citygal", "11th October St.", "Skopje", "/images/City Gallery Dzani.png", 1.5,60, 60, 41.9975394794109, 21.425443369807002));
            mapMarkers.add(new MapMarker("3D", "023073333, ", "Skopje", "/images/Atelje 3D.jpg", 1.5,60, 60, 42.00140348595888, 21.412576164042584));
            mapMarkers.add(new MapMarker("Mala stanica", "023126856, http://www.nationalgallery.mk/, 18", "Skopje", "/images/MAЛА.jpg", 1.5,60, 60, 41.99154778918674, 21.424894254800908));
            mapMarkers.add(new MapMarker("Anagor", "023224227, http://www.anagor.com.mk/, Naroden Front 21", "Skopje", "/images/Anagor - Kapishtec.jpg", 1.5,60, 60, 41.99413749653703, 21.41566834950312));
            mapMarkers.add(new MapMarker("Anju", "078515342", "Skopje", "/images/AN JU Atelje.jpg", 1.5,60, 60, 41.999952364682294, 21.497607958477726));
            mapMarkers.add(new MapMarker("Bukefal", "070232459, http://www.bukefal.com.mk/, St Clement of Ohrid 54", "Ohrid", "/images/Art Gallery Bukefal.jpg", 1.5,60, 60, 41.114552018256965, 20.80021393874093));
            mapMarkers.add(new MapMarker("Dudan", "070501573, http://anastasdudan.com.mk/, Jane Sandanski 14", "Ohrid", "/images/Atelier Anastas Dudan.jpg", 1.5,60, 60, 41.110054283305445, 20.805640196713345));
            mapMarkers.add(new MapMarker("Emanuela", "046253328, Sveti Kliment Ohridski", "Ohrid", "/images/Art Galerie Emanuela.jpg", 1.5,60, 60, 41.113900756374626, 20.799741633820744));

            mapRepository.saveAll(mapMarkers);
        }
    }
}
