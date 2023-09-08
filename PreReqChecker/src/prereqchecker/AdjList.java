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
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {

    
    public void makeCoursePrereqList(int a, HashMap<String, ArrayList<String>>coursesAndPrereq, int b, ArrayList<String>prereqList ){

    }


    public static void main(String[] args) {
        
        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }
        
        helper PrerequisiteChecker = new helper();

        StdIn.setFile(args[0]);
        
        int numCourses = Integer.parseInt(StdIn.readLine());
        PrerequisiteChecker.makeCourseList(numCourses);
        
        int numPrereqConnections = Integer.parseInt(StdIn.readLine());
        PrerequisiteChecker.makeCoursePrereqList(numPrereqConnections);
        

        StdOut.setFile(args[1]);

        //toString method
        for(Object x: PrerequisiteChecker.getCoursesAndPrereq().keySet()){
            StdOut.print(x + " ");

            for(int i = 0; i < PrerequisiteChecker.getCoursesAndPrereq().get(x).size(); i++){
                ArrayList<String>prereqList = PrerequisiteChecker.getCoursesAndPrereq().get(x);
                StdOut.print(prereqList.get(i) + " ");
            }

            StdOut.println();
            
        }
    }
}
