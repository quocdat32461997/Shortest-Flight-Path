/**
 *Name: Dat Quoc Ngo
 * Class: CS3345
 * Section: 003
 * Semester: Fall 2018
 * Project: 6
 * ShortestFlight.java provides functions to parse input and the shortest path between two airports
 */

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.ArrayList;

public class ShortestFlight {

    //declare necessary variables
    private LinkedList<Vertex> listVertex = new LinkedList<>();
    PriorityQueue<Vertex> flights;
    private String distanceType;
    private Vertex u, v;


    /**
     * parseInput: parse string path input into vertices and edges
     * @param: path input in form of string
     */
    public void parseDataInput(String input)
    {
        int startPoint = 0, endPoint = 0;   //define pointers

        //parse initial vertex
        while(input.charAt(endPoint) != '|')
        {
            endPoint++;
        }
        String initialVertex = input.substring(startPoint, endPoint); //retrieve initial vertex
        endPoint ++; //update pointers
        startPoint = endPoint;
        endPoint++;

        //parase final vertex
        while(input.charAt(endPoint) != '|')
        {
            endPoint++;
        }
        String finalVertex = input.substring(startPoint, endPoint); //retrieve final vertex
        endPoint ++; //update pointers
        startPoint = endPoint;
        endPoint++;
        
        //parse cost
        while(input.charAt(endPoint) != '|')
            endPoint++;
        Integer cost = Integer.parseInt(input.substring(startPoint, endPoint));
        if(cost < 0)//handle negative weight
            cost = 0;

        endPoint ++;  //update pointers
        startPoint = endPoint;
        endPoint++;

        //parse time
        while(endPoint < input.length())
            endPoint++;
        Integer time = Integer.parseInt(input.substring(startPoint, endPoint));
        if(time < 0) //handle negative weight
            time = 0;

        //update existing vertices
        Iterator<Vertex> itr = listVertex.iterator();
        Vertex startVertex = null, endVertex = null;
        while(itr.hasNext())
        {
           Vertex tempVertex = itr.next();
           if(tempVertex.airport.equals(initialVertex))
               startVertex = tempVertex;
           else if(tempVertex.airport.equals(finalVertex))
               endVertex = tempVertex;
        }

        //update list of vertices
        updatePQ(initialVertex, finalVertex, startVertex, endVertex, cost, time);
    }


    /**
     * parseQueryInput: parse query
     * @param: String input
     */
    public void parseQueryInput(String input)
    {
        int startPoint = 0, endPoint = 0;   //define pointers

        //parse initial vertex
        while(input.charAt(endPoint) != '|')
            endPoint++;
        u = new Vertex(input.substring(startPoint, endPoint)); //retrieve

        endPoint ++; //update pointers
        startPoint = endPoint;
        endPoint++;

        //final vertex
        while(input.charAt(endPoint) != '|')
            endPoint++;

        v = new Vertex(input.substring(startPoint, endPoint));

        endPoint ++; //update pointers
        startPoint = endPoint;
        endPoint++;

        //parse distance type
        while(endPoint < input.length())
            endPoint++;
        distanceType = input.substring(startPoint, endPoint);

        //define priority queue
        if(distanceType.equals("C"))
            flights = new PriorityQueue<Vertex>(new CostComparator());
        else
            flights = new PriorityQueue<Vertex>(new TimeComparator());

        //add elements into PQ
        Iterator<Vertex> itr = listVertex.iterator();
        itr = listVertex.iterator();
        while(itr.hasNext())
        {
            Vertex temp = itr.next();

            //find real vertices of start and end vertices
            if(temp.airport.equals(u.airport))
                u = temp;
            else if (temp.airport.equals(v.airport))
                v = temp;

            flights.add(temp);  //add vertices to PQ
        }
    }


    /**
     * updatePQ - update the linked list
     * @param: String initialVertex
     * @param: String finalVertex
     * @param: Vertex startVertex
     * @param: Vertex endVertex
     * @param: int cost
     * @param: int time
     * @param: LinkedList<Vertex> list

     */
    private void updatePQ(String initialVertexName, String finalVertexName, Vertex startVertex, Vertex endVertex, int cost, int time)
    {
        listVertex.remove(startVertex); //remove and add changed vertices
        listVertex.remove(endVertex);   //to update list of vertices

        //define nececssary vertices
        if(startVertex == null)
            startVertex = new Vertex(initialVertexName);
        if(endVertex == null)
            endVertex = new Vertex(finalVertexName);

        //define new edge
        Edge newEdge = new Edge(startVertex, endVertex, cost, time);

        //update start vertex
        startVertex.addEdge(newEdge);
        startVertex.setCost(10000);
        startVertex.setTime(10000);

        //update end vertex
        endVertex.setCost(10000);
        endVertex.setTime(10000);

        listVertex.add(startVertex);
        listVertex.add(endVertex);
    }


    /**
     * findShortestPath - find the shortest path from a vertex to another vertex
     */
    public String findShortestPath()
    {
        Vertex stop = u;
        stop.setTime(0); //start vertex has no cost and time
        stop.setCost(0);



        //perform edge relaxation
        edgeRelaxation(stop);

        return backtrackPath(v, distanceType) + "\n";
    }

    /**
     * edgeRelaxation - update distance of edges and adjacent vertices
     * @param: middleVertex the stop between to locations/vertices
     */
    private void edgeRelaxation(Vertex middleVertex)
    {
        //perform relaxation

            while(!flights.isEmpty())
            {
                ArrayList<Edge> tempEdgeList = middleVertex.adjacentAirport;
                middleVertex.known = true; //set visited

                Iterator<Edge> EdgeListItr = tempEdgeList.iterator();

                while(EdgeListItr.hasNext()) //each adjacent vertex to initialVertex
                {
                    //udpate distnace for every adjacent vertices
                    Edge tempEdge = EdgeListItr.next();

                    if(flights.contains(tempEdge.finalAirport)) //check visited
                    {
                        //middle vertex leads to less distance, update new distance and relabel path
                        if(distanceType.equals("C"))
                        {
                            if(tempEdge.initialAirport.cost + tempEdge.cost < tempEdge.finalAirport.cost)
                            {
                                //remove and add changed vertices to re-order PQ
                               flights.remove(tempEdge.finalAirport);

                               //assign new values to cost, time, and path
                               tempEdge.finalAirport.setTime(tempEdge.initialAirport.time + tempEdge.time);
                               tempEdge.finalAirport.setCost(tempEdge.initialAirport.cost + tempEdge.cost);
                               tempEdge.finalAirport.setPath(tempEdge.initialAirport);

                               //Vertex newVertex = tempEdge.finalAirport;
                               flights.add(tempEdge.finalAirport);
                            }
                        }//end relaxation for C
                        else
                        {
                            if(tempEdge.initialAirport.cost + tempEdge.cost < tempEdge.finalAirport.cost)
                                {
                                    //remove and add changed vertices to re-order PQ
                                   flights.remove(tempEdge.finalAirport);

                                   //assign new values to cost, time, and path
                                   tempEdge.finalAirport.setTime(tempEdge.initialAirport.time + tempEdge.time);
                                   tempEdge.finalAirport.setCost(tempEdge.initialAirport.cost + tempEdge.cost);
                                   tempEdge.finalAirport.setPath(tempEdge.initialAirport);

                                   //Vertex newVertex = tempEdge.finalAirport;
                                   flights.add(tempEdge.finalAirport);
                                }
                        }//end relaxation for T
                    } //finish updating adjacent vertices
                }//PQ is empty

                middleVertex = flights.poll();  //select the next verted that has the shortest distance
            }
    }


    /**
     * backtrackPath - print out stops between two airports/vertices
     * @param: vertex  the destination of the flight
     * @param: dT the distance type either C or T
     */
    private String backtrackPath(Vertex vertex, String dT)
    {
        Vertex temp = vertex;
        String path = "";

        if(dT.equals("Cost"))
        {
           while(temp.airport != u.airport)
            {
                if(temp.path == null)
                    return "NO FLIGHT AVAILABLE FOR REQUEST";

                path = temp.airport.concat("|" + path);

                temp = temp.path;
            }
            path = u.airport.concat("|" + path);
            path = path.concat(vertex.cost + "|" + vertex.time);
        }
        else
        {
            while(temp.airport != u.airport)
            {
                if(temp.path == null)
                    return "NO FLIGHT AVAILABLE FOR REQUEST";

                path = temp.airport.concat("|" + path);

                temp = temp.path;
            }
            path = u.airport.concat("|" + path);
            path = path.concat(vertex.cost + "|" + vertex.time);
        }
        return "|" + path;
    }


    private class CostComparator implements Comparator<Vertex>
    {
                public int compare(Vertex u, Vertex v)
                {
                  if(u.cost < v.cost)
                      return -1;
                  else if(u.cost > v.cost)
                      return 1;
                  else return 0;
                }
    }


    private class TimeComparator implements Comparator<Vertex>
    {
                public int compare(Vertex u, Vertex v)
                {
                  if(u.time < v.time)
                      return -1;
                  else if(u.time > v.time)
                      return 1;
                  else return 0;
                }
    }
}
