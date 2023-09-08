package prereqchecker;

import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {
        
        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }

	  //Step 1
      helper PrerequisiteChecker = new helper();

      StdIn.setFile(args[0]);
  
      int numCourses = Integer.parseInt(StdIn.readLine());
      PrerequisiteChecker.makeCourseList(numCourses);

      int numPrereqConnections = Integer.parseInt(StdIn.readLine());
      PrerequisiteChecker.makeCoursePrereqList(numPrereqConnections);

      //Step 2
      StdIn.setFile(args[1]);
      String target = StdIn.readLine();
      int numCoursesTaken = Integer.parseInt(StdIn.readLine());
      ArrayList<String> coursesTaken = new ArrayList<String>();
      for (int i = 0; i < numCoursesTaken; i++) {
          String course = StdIn.readLine();
          coursesTaken.add(course);
      }

      //Step 3
      StdOut.setFile(args[2]);
      for(String course : PrerequisiteChecker.coursesNeededtoTake(coursesTaken,target)) {
        StdOut.println(course);
    }
    }
}
