/*

Can you design an algorithm to find the minimum stop path from city A to B?

```
A - C - B
|     /
D - E

Return [A, C, B]

```
*/

import java.util.Arrays;
import java.util.HashMap;

/**
 * A very basic HashMap implementation of the Traveling Salesmen problem,
 * which only solves for graphs where each vertex has 2 edges and each edge is of identical length.
 */
public class TravelingSalesmanLite {
    private String startCity;
    private String endCity;
    private String[] firstPath;
    private String[] secondPath;
    private HashMap<String, String[]> cities;

    /**
     * Initializes this instance with a small hard-coded example.
     * The example data looks like:
     * A - C - B
     * |     /
     * D - E
     */
    public TravelingSalesmanLite() {
        cities = new HashMap<String, String[]>();
        populateExampleData();
    }

    /**
     * Initializes this instance with a graph of cities, the starting city, and the ending city.
     * None of the provided parameters should be null or empty, and the start/end city should be in the HashMap.
     * @param cities A HashMap where the key is the name of a city, and the value is an array of its connecting cities
     * @param startCity A string representing which city the path should begin in (and is a key in the HashMap)
     * @param endCity A string representing which city the path should end in (and is a key in the HashMap)
     */
    public TravelingSalesmanLite(HashMap<String, String[]> cities, String startCity, String endCity) throws IllegalArgumentException {
        if (cities == null || cities.size() < 1
                || isStringNullOrEmpty(startCity) || isStringNullOrEmpty(endCity)
                || !cities.containsKey(startCity) || !cities.containsKey(endCity))
            throw new IllegalArgumentException();

        this.cities = cities;
        this.startCity = startCity;
        this.endCity = endCity;
    }

    /**
     * Adds a small amount of hard-coded data to the cities HashMap.
     */
    public String getMinimumStopPath() {
        if (cities.size() == 1)
            return convertPathArrayToString(new String[] {startCity});

        firstPath = createPathsMapping(0);
        secondPath = createPathsMapping(1);

        return convertPathArrayToString(findShortestPath());
    }

    /**
     * Finds and returns the smaller of the two paths taken.
     * If the paths are equal distance, it arbitrarily chooses the first path calculated.
     * @return The shorter path, or the first path calculated if they are equal.
     */
    private String[] findShortestPath() {
        if (firstPath.length <= secondPath.length)
            return firstPath;
        return secondPath;
    }

    /**
     * Converts an array of strings into its string representation.
     * @returns A string representing the concatenation of all cities in the path, wrapped in brackets
     */
    private String convertPathArrayToString(String[] path) {
        StringBuilder builder = new StringBuilder();

        if (path == null)
            return "null";
        if (path.length == 0)
            return "[]";

        builder.append('[');
        for (int i = 0; i < path.length; i++) {
            builder.append(path[i]);
            if (i == path.length - 1)
                builder.append(']');
            else
                builder.append(", ");
        }

        return builder.toString();
    }

    /**
     * Returns a String array that represents a path from the startCity to the endCity.
     * @param firstStop 0 for the first connectedCity, or 1 for the second connectedCity
     * @returns A String containing a path from the startCity to the endCity
     */
    private String[] createPathsMapping(int firstStop) {
        String[] currentPath = new String[cities.size() / 2 + 1];
        String currentCity = cities.get(startCity)[firstStop];
        int cityNumber = 2;

        currentPath[0] = startCity;
        currentPath[1] = currentCity;

        while (!currentCity.equals(endCity) && cityNumber < currentPath.length)
        {
            currentCity = cities.get(currentCity)[1];
            currentPath[cityNumber] = currentCity;
            cityNumber++;
        }

        return currentPath;
    }

    /**
     * Adds a small amount of hard-coded data to the cities HashMap.
     */
    private void populateExampleData() {
        addCity("A", new String[] {"C", "D"});
        addCity("B", new String[] {"C", "E"});
        addCity("C", new String[] {"A", "B"});
        addCity("D", new String[] {"A", "E"});
        addCity("E", new String[] {"D", "B"});

        startCity = "A";
        endCity = "B";
    }

    /**
     * Validates the cityName and connectedCities data, and if no issues were found it adds the city to the cities HashMap.
     * @param  cityName A String containing the name of the city to be added
     * @param  connectedCities An array of Strings containing all the cities that this city will be connected to
     */
    private void addCity(String cityName, String[] connectedCities) throws IllegalArgumentException {
        if (cities == null || isStringNullOrEmpty(cityName)
                || isConnectedCitiesInvalid(connectedCities)
                || (Arrays.stream(connectedCities).anyMatch(cityName::equals) && cityName != startCity))
            throw new IllegalArgumentException();

        cities.put(cityName, connectedCities);
    }

    /**
     * Checks whether the connectedCities parameter:
     * 1. Is null
     * 2. Has length >= 2
     * 3. Has at least two populated indices
     * 4. If there are two connected cities, they are not equal to each other.
     * @param connectedCities A String array with a length of 2 or more
     * @returns True if the connectedCities parameter is invalid, false if it is valid.
     */
    private boolean isConnectedCitiesInvalid(String[] connectedCities) {
        return connectedCities == null || connectedCities.length < 2
                || isStringNullOrEmpty(connectedCities[0]) || isStringNullOrEmpty(connectedCities[1])
                || connectedCities[0].equals(connectedCities[1]);
    }

    private boolean isStringNullOrEmpty(String str){
        return str == null || str.equals("");
    }
}
