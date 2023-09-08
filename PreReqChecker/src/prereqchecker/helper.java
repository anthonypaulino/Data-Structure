package prereqchecker;

import java.util.*;

public class helper {
    private HashMap<String, ArrayList<String>>coursesAndPrereq;    
    private HashSet<String> prereqList;
    private HashMap<String, Boolean> marked;
    private HashSet<String> finishedCoursesList;
    private HashSet<String> prereqForTargetCourse;
    private String semesterCourse;
    private HashMap<Integer, ArrayList<String>>PlanSchedule;


    //instantiate...constructors
    public helper() {
        coursesAndPrereq = new HashMap<String, ArrayList<String>>();
        prereqList = new HashSet<String>();
        marked = new HashMap<String, Boolean>();
        finishedCoursesList = new HashSet<String>();
        prereqForTargetCourse = new HashSet<String>();
        PlanSchedule = new HashMap<Integer, ArrayList<String>>();
        semesterCourse = new String();
    } 

    //getter methods
    public HashMap<String, ArrayList<String>> getCoursesAndPrereq() {return this.coursesAndPrereq;}

    public HashSet<String> getPrereqList() {return this.prereqList;}

    public HashMap<String, Boolean> getMarked() {return this.marked;}
    
    public HashSet<String> getFinishedCoursesList() {return this.finishedCoursesList;}

    public HashSet<String> getPrereqForTargetCourse() {return this.prereqForTargetCourse;}

    //builder methods
    public void makeCourseList(int numCourses){
        
        for (int i = 0; i < numCourses; i++) {
            String course = StdIn.readLine();
            coursesAndPrereq.put(course, new ArrayList<String>());
        }
    }
    
    public void makeMarkedList(){
        for (String courseId : coursesAndPrereq.keySet()) {
        marked.put(courseId, false);
        }
    }

    public void makeCoursePrereqList(int numPrereqConnections){
        
        for (int i = 0; i < numPrereqConnections; i++) {
            String[] data = StdIn.readLine().split("\\s+");
            ArrayList<String> prerequisites = coursesAndPrereq.get(data[0]);
      
            prerequisites.add(data[1]);
            coursesAndPrereq.put(data[0], prerequisites);
        }

    }
    

    //testing..functional methods
    public void makePrereqList(String CourseID){
        marked.put(CourseID,true);
        for(String prereq : coursesAndPrereq.get(CourseID)){
            if( !marked.get(prereq) ){
                prereqList.add(prereq);
                makePrereqList(prereq);
            }
        }
    }

    public boolean isPrereqListEmpty() {return prereqList.size() == 0;}

    // can CourseID be a prerequisite of the existing prerequisite list 
    //need to initialize makePrereqList first
    public String isValidPrereq(String CourseID){
        
        if (isPrereqListEmpty()) {
            return "YES";
        }

        for(String prereq : prereqList){
            if (prereq.equalsIgnoreCase(CourseID)){
                return "NO";
            }
        }
        return "YES";
    }

   
    //make a list of finished courses
    private void makefinishedList(String CourseID){
        marked.put(CourseID,true);
        for(String prereq : coursesAndPrereq.get(CourseID)){
            if( !marked.get(prereq) ){
                finishedCoursesList.add(prereq);
                makefinishedList(prereq);
            }
        } 
    }

    
    private boolean eligbleListChecker(ArrayList<String>prerequisites ){
        boolean checker = false;
        for (String prereq : prerequisites){
            if (!finishedCoursesList.contains(prereq)){
            return false;
            }
        }
        return true;
    }

    public ArrayList<String> makeEligibleList( ArrayList<String> finishedCourses){   
        ArrayList<String> eligibleList = new ArrayList<String>();
        //reset boolean list
        makeMarkedList();
        //create full list of finished courses
        for (String course : finishedCourses){
            makefinishedList(course);
        }
        for (String course : finishedCourses){
            finishedCoursesList.add(course);
        }
        //check for eligible courses to take
        for (String courseID : coursesAndPrereq.keySet()) {
        if (!finishedCoursesList.contains(courseID)){
            ArrayList<String> prerequisites = coursesAndPrereq.get(courseID);
                if(eligbleListChecker(prerequisites)){
                    eligibleList.add(courseID);
                }
            }
        }
        return eligibleList;
    }

   



    private void prereqForTargetCourse(String CourseID){
        marked.put(CourseID,true);
        for(String prereq : coursesAndPrereq.get(CourseID)){
            if( !marked.get(prereq)){
                prereqForTargetCourse.add(prereq);
                prereqForTargetCourse(prereq);
            }
        } 

    }

    public ArrayList<String> coursesNeededtoTake(ArrayList<String> finishedCourses, String targetCourse) {
        
        ArrayList<String> finalList = new ArrayList<String>();
        //reset boolean/finish list
        makeMarkedList();
        this.finishedCoursesList = new HashSet<String>();

        //create full list of finished courses
        for (String course : finishedCourses){
            makefinishedList(course);
        }
        for (String course : finishedCourses){
            finishedCoursesList.add(course);
        }
        //reset boolean list again
        makeMarkedList();

        //create full list of courses needed to be taken for target course
        prereqForTargetCourse(targetCourse);

        //check for courses to take by seeing if you have already taken them in the finishedCoursesList
        for (String courseID : prereqForTargetCourse) {
            if (!finishedCoursesList.contains(courseID)){
                finalList.add(courseID);
            }
        }
        return finalList;
    }


    private boolean eligbleChecker(HashSet<String> finishedCourses ){
       boolean checker = false;
        for (String course : prereqForTargetCourse){
            if (!finishedCourses.contains(course)){
            return false;
            }
        }
        return true;
    }

    private String eligibleCourse(String CourseID, HashSet<String> finishedCourses){   
        makeMarkedList();
        prereqForTargetCourse(CourseID);
        if(eligbleChecker(finishedCourses)){
        return CourseID;
        }
        this.prereqForTargetCourse = new HashSet<String>();
        return null;

    }

    
    public HashMap<Integer, ArrayList<String>> makeSchedulePlan( ArrayList<String> NeededtoTake, HashSet<String>finishedCourses ){
        int semester = 1;

        while (!NeededtoTake.isEmpty()){
            this.prereqForTargetCourse = new HashSet<String>();
            ArrayList<String> hitter = new ArrayList<String>();
            for(String course : NeededtoTake){
                if(eligibleCourse(course,finishedCourses) != null){
                hitter.add(course);
                }
            }
            finishedCourses.addAll(hitter);
            NeededtoTake.removeAll(hitter);
            PlanSchedule.put(semester,hitter);
            semester ++;
          }
        return PlanSchedule;
    }
    
}




   



