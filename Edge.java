/**
 *Name: Dat Quoc Ngo
 * Class: CS3345
 * Section: 003
 * Semester: Fall 2018
 * Project: 6
 * Edge.java defines the edge class
 */

public class Edge {
    Vertex initialAirport;
    Vertex finalAirport;

    int cost;
    int time;

    /**
     * Edge - constructor
     * @param: u - initial vertex
     * @param: v - final vertex
     * @param: c - cost
     * @param: t - time
     */
    public Edge(Vertex u, Vertex v, int c, int t)
    {
        initialAirport = u;
        finalAirport = v;
        cost = c;
        time = t;
    }
}
