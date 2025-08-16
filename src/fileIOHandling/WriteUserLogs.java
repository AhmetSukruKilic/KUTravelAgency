package fileIOHandling;

import java.io.*;
import reservation.*;

public class WriteUserLogs {

    private static String filePath = "userLogs.txt";

    public static void write(Reservation reservation) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(reservation.toString());
            bw.write("\r\n------------------------------------------------------------------------------------\r\n");

        } catch (IOException e) {
            System.err.println("Error writing to the file: " + filePath);
        } catch (Exception e) {
            System.err.println("An Error occurred while writing to the file: " + filePath);
        }
    }

    public static void edit(String oldReservation, Reservation newReservation) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder fileContent = new StringBuilder();
            StringBuilder reservationBlock = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains("-------------------------------------------------------------------------")) {
                    if (areWordsSame(reservationBlock.toString(), oldReservation)) {
                        // Replace the old reservation block with the new one
                        fileContent.append(newReservation.toString() + "\n")
                                    .append("------------------------------------------------------------------------------------\r\n");
                    } else {
                        // Keep the original reservation block
                        fileContent.append(reservationBlock).append("------------------------------------------------------------------------------------\r\n");;
                    }
                    reservationBlock.setLength(0); // Reset for the next block
                } else {
                    reservationBlock.append(line).append("\n");
                }
            }

            // Handle the last block if it does not end with a separator
            if (reservationBlock.length() > 0) {
                if (reservationBlock.toString().trim().equals(oldReservation.trim())) {
                    fileContent.append(newReservation.toString())
                                .append("------------------------------------------------------------------------------------\r\n");
                } else {
                    fileContent.append(reservationBlock);
                }
            }

            // Write the updated content back to the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                bw.write(fileContent.toString());
            }

        } catch (IOException e) {
            System.err.println("Error editing the file: " + filePath);
        }
    }
    private static boolean areWordsSame(String text1, String text2) {
        // Normalize the texts: remove extra spaces, tabs, and split into words
        String[] words1 = text1.replaceAll("\\s+", " ").trim().split(" ");
        String[] words2 = text2.replaceAll("\\s+", " ").trim().split(" ");
        
        // Compare the arrays
        return java.util.Arrays.equals(words1, words2);
    }

}
