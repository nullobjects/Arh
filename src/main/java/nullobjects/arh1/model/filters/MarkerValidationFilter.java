package nullobjects.arh1.model.filters;

import nullobjects.arh1.model.Filter;
import nullobjects.arh1.model.MapMarker;

/**
 * A filter implementation to validate a MapMarker.
 */
public class MarkerValidationFilter implements Filter<MapMarker> {

    /**
     * Executes the filter by validating the provided MapMarker.
     * @param mapMarker The MapMarker to be validated.
     * @return The validated MapMarker.
     * @throws IllegalArgumentException if the map marker does not have a valid name.
     */
    @Override
    public MapMarker execute(MapMarker mapMarker) {
        // Check if the map marker has a valid name
        if (mapMarker.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("The map marker does not have a valid name.");
        }

        // Clamp the x and y coordinates of the map marker to ensure they are within valid bounds
        mapMarker.setX_coord(Math.clamp(mapMarker.getX_coord(), 0, 100));
        mapMarker.setY_coord(Math.clamp(mapMarker.getY_coord(), 0, 100));

        return mapMarker;
    }
}
