import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

/**
* Recursive Binary Search.
*
* @author  Atri Sarker
* @version 1.0
* @since   2026-01-09
*/
public final class RecBinSearch {
  /**
   * Private constructor to satisfy style checker.
   * @exception IllegalStateException for the utility class.
   * @see IllegalStateException
   */
  private RecBinSearch() {
    // Prevents illegal states.
    throw new IllegalStateException("Utility class.");
  }

  /**
   * Function that searches for an int in an int[].
   * Using Recursive Binary Search.
   *
   * @param arr    The array that is going to be searched.
   * @param target The int to be searched for in the array.
   * @param low min bound.
   * @param high max bound.
   * @return The index of the target in the array. -1 if not found.
   */
  public static int recBin(final int[] arr,
        final int target, final int low, final int high) {
    // Get midpoint
    int mid = low + (high - low) / 2;
    if (low > high) {
      // impossible boundaries = target not found
      return -1;
    } else if (arr[mid] < target) {
      // If the midpoint is less than the target,
      // Limit the search range to everything after the midpoint
      return recBin(arr, target, mid + 1, high);
    } else if (arr[mid] > target) {
      // If the midpoint is greater than the target,
      // Limit the search range to everything before the midpoint
      return recBin(arr, target, low, mid - 1);
    } else {
      // If the midpoint is neither less than or greater than the target,
      // It must be the target.
      // Thus, return.
      return mid;
    }
  }

/**
   * Entrypoint of the program.
   *
   * @param args For command line arguments.
   */
  public static void main(final String[] args) {
    // First argument is the path to the input file.
    final String inputFilePath = args[0];
    // Second argument is the path to the output file.
    final String outputFilePath = args[1];
    // Print arguments
    System.out.println("Input file: " + inputFilePath);
    System.out.println("Output file: " + outputFilePath);
    try {
      // Access the input file and create a File object.
      File inputFile = new File(inputFilePath);
      // Access the output file and create a File object.
      File outputFile = new File(outputFilePath);
      // Scanner that will read the File Object.
      Scanner fileReader = new Scanner(inputFile);
      // Create list to store all the ranges
      ArrayList<ArrayList<Integer>> listOfRanges = new ArrayList<>();
      // Create list to store all the targets
      ArrayList<Integer> listOfTargets = new ArrayList<>();
      // Loop through all available lines
      while (fileReader.hasNextLine()) {
        // Add the range to the list
        // Convert line to a string array
        String[] numsAsStrings = fileReader.nextLine().split(" ");
        // Create new range
        ArrayList<Integer> newRange = new ArrayList<>();
        try {
          // Go through every number and add it as an int
          for (String numAsString : numsAsStrings) {
            // Cast to int
            int num = Integer.parseInt(numAsString);
            // Add to range
            newRange.add(num);
          }
        } catch (NumberFormatException error) {
          // If a string can't be converted to an integer,
          // the program just ignores the line and continues.
          continue;
        }
        // Add range to list
        listOfRanges.add(newRange);
        // The next line is the search target
        try {
          int target = Integer.parseInt(fileReader.nextLine());
          // Add target to the list
          listOfTargets.add(target);
        } catch (NumberFormatException error) {
          // If the line can't be converted to an integer,
          // the program just ignores the line and continues.
          continue;
        }
      }
      // Close the file reader
      fileReader.close();
      // Convert the lists to arrays
      int[][] arrOfRanges = new int[listOfRanges.size()][];
      for (int rangeIndex = 0;
         rangeIndex < listOfRanges.size();
       rangeIndex++) {
        int[] arrRange = new int[listOfRanges.get(rangeIndex).size()];
        for (int index = 0;
           index < listOfRanges.get(rangeIndex).size();
            index++) {
          arrRange[index] = listOfRanges.get(rangeIndex).get(index);
        }
        // Sort it
        Arrays.sort(arrRange);
        arrOfRanges[rangeIndex] = arrRange;
      }
      int[] arrOfTargets = new int[listOfTargets.size()];
      for (int index = 0; index < listOfTargets.size(); index++) {
        arrOfTargets[index] = listOfTargets.get(index);
      }
      // String to hold all output
      String output = "";
      // Go through every range-target pair and
      // add the result to the output string
      for (int pairIndex = 0; pairIndex < arrOfTargets.length; pairIndex++) {
        // Get search range
        int[] searchRange = arrOfRanges[pairIndex];
        // Get target
        int target = arrOfTargets[pairIndex];
        // Perform search
        int targetIndex = recBin(searchRange, target, 0, searchRange.length);
        // Write results
        // Write the array
        output += Arrays.toString(searchRange);
        // Newline
        output += "\n";
        // Write the result
        if (targetIndex != -1) {
          // If found,
          output += String.format("%d found at index %d", target, targetIndex);
        } else {
          // If not found,
          output += String.format("%d not found in array", target);
        }
        // Add a newline
        output += "\n";
      }
      // Print output
      System.out.println(output);
      // Write output to output file
      try {
        // Create a FileWriter object to write to the file
        FileWriter writer = new FileWriter(outputFile);
        // Write the output to the file
        writer.write(output);
        // Close the writer
        writer.close();
      } catch (IOException error) {
        System.out.println(error);
      }
    } catch (IOException error) {
      System.out.println(error);
    }
    // Completion message
    System.out.println("DONE!");
  }
}

