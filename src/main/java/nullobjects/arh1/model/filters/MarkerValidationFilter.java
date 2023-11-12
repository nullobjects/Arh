package nullobjects.arh1.model.filters;

import nullobjects.arh1.model.Filter;
import nullobjects.arh1.model.MapMarker;

public class MarkerValidationFilter implements Filter<MapMarker> {

    @Override
    public MapMarker execute(MapMarker mapMarker) {
        if (mapMarker.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("The map marker does not have a valid name.");
        }
        mapMarker.setX_coord(Math.clamp(mapMarker.getX_coord(), 0, 100));
        mapMarker.setY_coord(Math.clamp(mapMarker.getY_coord(), 0, 100));

        return mapMarker;
    }
}
