package fileIOHandling;

import java.io.*;
import entity.*;


public final class ReadFlights {
    
    private static String filePath = "FinalKU_Travel_Agency_Dataset_Flights.csv"; 
    
    public static void read() {
        String line;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        	line = br.readLine(); // first line
            while ((line = br.readLine()) != null ) { 
                String[] flightInfo = line.split(","); 

                if (!createNewFlight(flightInfo)) {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + filePath);
        }
    }

    private static boolean createNewFlight(String[] flightInfo) {
        try {
            if (isDirectFlight(flightInfo)) {
                new DirectFlight(flightInfo); 
            } else if (isConnectedFlight(flightInfo)) {
                new ConnectingFlight(flightInfo);
            } else {
                throw new InvalidObjectException("Invalid Flight Type");
            }
        } catch (InvalidObjectException e) {
            return false;
        }
        return true;
    }
    
    private static boolean isDirectFlight(String[] flightInfo) {
    	return flightInfo.length == 9;
    }
    
    private static boolean isConnectedFlight(String[] flightInfo) {
    	return flightInfo.length == 15;
    }

    
}
