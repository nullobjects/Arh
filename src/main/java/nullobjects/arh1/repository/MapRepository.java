package nullobjects.arh1.repository;

import nullobjects.arh1.model.MapMarker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapRepository extends JpaRepository<MapMarker, String> {
    List<MapMarker> findAllByCity(String city);
}
