package nullobjects.arh1.service;

import nullobjects.arh1.model.MapMarker;
import nullobjects.arh1.repository.MapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapService {
    private MapRepository mapRepository;
    MapService(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    public List<MapMarker> getAllMarkers() {
        return mapRepository.getAllMarkers();
    }
    public String findByName(String name){
        return mapRepository.findByName(name);
    }
}