package nullobjects.arh1.service;

import nullobjects.arh1.model.MapMarker;
import nullobjects.arh1.repository.MapRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MapService {
    private final MapRepository mapRepository;
    MapService(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    public List<MapMarker> getAllMarkers() {
        return mapRepository.getAllMarkers();
    }
    public MapMarker findMarkerByName(String name) {
        return mapRepository.findMarkerByName(name);
    }
    public boolean delete(String name) {
        return mapRepository.delete(name);
    }

    public MapMarker findMarkerByCity(String city) {
        return mapRepository.findMarkerByCity(city);
    }

    public void add(String name,String disc,String city,String image,int start,int end,double x,double y){
        mapRepository.add(name, disc, city, image, start, end, x, y);
    }

    public void addComment(String name, String username, String comment) {
        mapRepository.InsertMarkerComment(name, username, comment);
    }

    public List<String> getComments(String name) {
        return mapRepository.GetMarkerComments(name);
    }
}