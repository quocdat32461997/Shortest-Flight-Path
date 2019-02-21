/**
 *Name: Dat Quoc Ngo
 * Class: CS3345
 * Section: 003
 * Semester: Fall 2018
 * Project: 6
 * Vertex.java defines the vertex class that provides properties of every airport
 */

import java.util.ArrayList;

public class Vertex {
    String airport;
    ArrayList<Edge> adjacentAirport = new ArrayList<> ();

    boolean known;
    int cost, time;
    Vertex path;

    /**
     * Vertex -  constructor
     * @param: name the vertex's name
     */
    public Vertex(String name)
    {
        this.airport = name;
        this.cost = 0;
        this.time = 0;
        this.path = null;
    }

    /**
     * addEdge - add a new edge to current vertex
     * @param: e the new edge
     */
    public void addEdge(Edge e)
    {
        adjacentAirport.add(e);
    }

    /**
     * setPath - assign value to path
     * @param: p path leading to this vertex
     */
    public void setPath(Vertex p)
    {
        path = p;
    }

    /**
     * setCost - assign value to cost
     * @param: c the cost value
     */
    public void setCost(int c)
    {
        cost = c;
    }

    /**
     * setTime - assign value to time
     * @param: c the time value
     */
    public void setTime(int t)
    {
        time = t;
    }
}
