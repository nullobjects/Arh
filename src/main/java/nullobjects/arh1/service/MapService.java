package nullobjects.arh1.service;

import nullobjects.arh1.repository.MapRepository;
import org.springframework.stereotype.Service;

@Service
public class MapService {
    private MapRepository mapRepository;

    MapService(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }
}