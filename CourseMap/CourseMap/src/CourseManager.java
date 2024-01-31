import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseManager {

    static ArrayList<String> courseList = new ArrayList<>();

    public static void addCourse() {
        boolean done = false;
         //Continue collecting name until Done is typef
        while (!done) {
            System.out.println("Type the name of the course, Type DONE to complete:");
            Scanner scanner = new Scanner(System.in);
            String courseName = scanner.nextLine();
            if (courseName.equalsIgnoreCase("DONE")) {
                done = true;
            } else {
                courseList.add(courseName);
            }
        }

        updateCourseListFile();
    }

    public static void removeCourse() {
        Scanner scanner = new Scanner(System.in);
        boolean done = false;

        while (!done) {           //loop will run collected names if courses to remove until Done is typed.
            System.out.println("What courses do you want to remove. Type Done to Finish:");
            String courseName = scanner.nextLine();

            if (!courseName.equalsIgnoreCase("done")) {

                if (courseList.remove(courseName)) {        //this checks whether the courseName actually exists
                    System.out.println(courseName + " has been removed from the List");     // then removes
                } else {
                    System.out.println(courseName + " not found in the List");// else-- cannot be found
                }
            } else {
                done = true;
            }
        }

        updateCourseListFile(); //finally updates/writes list of courses inArraylist in the txt file
    }

    public void generateCourseMap() {
        try {
            // Your existing code for generating the course map
            BufferedReader reader = new BufferedReader(new FileReader("/Users/alex/IdeaProjects/CourseMap/src/Courses.txt"));

            String line;
            List<String> courseList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }

                // Split the line using a comma as the delimiter
                String[] courses = line.split(",");

                // Add each course to the ArrayList
                for (String course : courses) {
                    courseList.add(course.trim());  // Trim to remove leading/trailing whitespaces
                }
            }

            // Close the file reader
            reader.close();

            // Create a DOT language representation of the graph
            StringBuilder dotGraph = new StringBuilder();
            dotGraph.append("digraph CourseGraph {\n");
            dotGraph.append("node [\n" +
                    "\n" +
                    "        shape=box  \n" +
                    "        width=1.2\n" +
                    "        height=1.2\n" +
                    "        fillcolor=\"pink\"\n" +
                    "        fontcolor=\"Helvetica\"\n" +
                    "        fontsize=18\n" +
                    "        style=\"filled\"\n" +
                    "        color=\"#aaaaaa\"\n]"
            );

            // Add nodes to the graph
            for (String course : courseList) {
                dotGraph.append("  \"").append(course).append("\"\n");
            }

            // Add edges (in this example, connecting each course to the next one)
            for (int i = 0; i < courseList.size() - 1; i++) {
                dotGraph.append("  \"").append(courseList.get(i)).append("\" -> \"").append(courseList.get(i + 1)).append("\"\n");
            }

            dotGraph.append("}\n");

            // Save the DOT language representation to a file
            try (PrintWriter writer = new PrintWriter("/Users/alex/IdeaProjects/CourseMap/src/course_graph.dot")) {
                writer.println(dotGraph.toString());
            }

            // Run the Graphviz dot command to convert DOT to PNG and open it
            ProcessBuilder processBuilder = new ProcessBuilder("dot", "-Tpng", "course_graph.dot", "-o", "course_graph.png");
            processBuilder.directory(new File("/Users/alex/IdeaProjects/CourseMap/src/"));

            Process process = processBuilder.start();
            process.waitFor(); // Wait for the process to complete

            // Open the PNG file
            ProcessBuilder openProcessBuilder = new ProcessBuilder("open", "course_graph.png");
            openProcessBuilder.directory(new File("/Users/alex/IdeaProjects/CourseMap/src/"));
            openProcessBuilder.start();

            // Print a message indicating where the DOT file is saved
            System.out.println("DOT file saved: /Users/alex/IdeaProjects/CourseMap/src/course_graph.dot");
            System.out.println("PNG file saved: /Users/alex/IdeaProjects/CourseMap/src/course_graph.png");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateCourseListFile() {
        try {
            // Create a text file with course names separated by commas
            FileWriter writer = new FileWriter("/Users/alex/IdeaProjects/CourseMap/src/newCourseList.txt");
            for (String course : courseList) {
                writer.write(course + " ,");
            }
            writer.close();
            System.out.println("Course list saved to newCourseList.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating/updating course list file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        addCourse();
        removeCourse();
    }
}
