/**
 *Name: Dat Quoc Ngo
 * Class: CS3345
 * Section: 003
 * Semester: Fall 2018
 * Project: 6
 *
 * FlightSystem.java parses and finds the shortest path between two airports
* by calling functions parseDataInput(), parseQueryInput(), and findShortestPath()
 */

import java.io.*;

public class FlightSystem {

    public static void main(String[] args) {

        try
        {
            int counter = 1;

            //define two buffered objects
            BufferedReader queryFile = new BufferedReader(new FileReader("queryFile.txt"));//args[0]
            BufferedWriter outputFile = new BufferedWriter(new FileWriter("outputFile.txt"));//args[2]

            //read query file and write output
            String query = queryFile.readLine();

            while(query != null && query != "\0")
            {
                ShortestFlight sf = new ShortestFlight();

                //re-read data file for every queue
                BufferedReader dataFile = new BufferedReader(new FileReader("dataFile.txt")); //args[1s]
                String input = dataFile.readLine();
                while(input != null && input != "\0")
                {
                    sf.parseDataInput(input);   //parse data
                    input = dataFile.readLine();
                }
                dataFile.close();

                sf.parseQueryInput(query);  //parse flight query

                //find the shortest path
                String result = sf.findShortestPath();
                if(result.equals("NO FLIGHT AVAILABLE FOR REQUEST\n"))
                    outputFile.write(result);
                else
                {
                    outputFile.write(counter + result);
                    counter++;
                }
                outputFile.newLine();

                query = queryFile.readLine();

            }

            queryFile.close();  //close buffered objects
            outputFile.close();
        }
        catch(IOException e)
        {
            System.out.println("File not found");
            System.exit(0);
        }
    }
}
