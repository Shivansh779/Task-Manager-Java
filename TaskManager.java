import java.util.*;
import java.io.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class TaskManager {
    private static final Logger logger = Logger.getLogger(TaskManager.class.getName());
    public static Scanner sc = new Scanner(System.in);
    public static void newTasks() {
        try {
            FileWriter writer = new FileWriter("tasks.txt");
            System.out.print("Enter number of tasks: ");
            int numberOfTask = sc.nextInt();
            sc.nextLine();
            String[] task = new String[numberOfTask];
            for(int i = 0;i < task.length; i++) {
                System.out.print("Enter task " + (i +1) + ": ");
                task[i] = sc.nextLine();
            }
            for (String work : task) {
                writer.write(work + "\n");
            }
            writer.close();
            System.out.println("Task written in File");
        } catch (IOException e) {
            System.out.println("Error Occurred");
            logger.log(Level.SEVERE, "An error occurred", e);
        }
    }
    public static void addTasks() {
        try {
            FileWriter writer = new FileWriter("tasks.txt", true);
            System.out.print("Enter number of task: ");
            int numberOfTask = sc.nextInt();
            sc.nextLine();
            String[] task = new String[numberOfTask];
            for (int i = 0; i < task.length; i++) {
                System.out.print("Enter task: ");
                task[i] = sc.nextLine();
            }
            for (String s : task) {
                writer.write(s + "\n");
            }
            writer.close();
            System.out.println("Tasks added to file");
        } catch (IOException e) {
            System.out.println("Error Occurred");
            logger.log(Level.SEVERE, "An error occurred", e);
        }
    }
    public static void readTasks() {
          try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
              String line;
              while ((line = reader.readLine()) != null) {
                  System.out.println(line);
              }
          } catch (IOException e) {
              System.out.println("An Error Occurred");
              logger.log(Level.SEVERE, "An error occurred", e);
          }
    }
    public static void taskDone() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("Tasks_Done.txt"))) {
            String line;
            int j = 0;
            while ((line = reader.readLine()) != null) {
                j++;
                System.out.println(j + " " + line);
            }
            System.out.print("Enter number of tasks done: ");
            int numOfTaskDone = sc.nextInt();
            int[] taskIndexes = new int[numOfTaskDone];
            System.out.println("Enter task numbers (e.g. 1, 3, 5): ");
            for (int i = 0; i < numOfTaskDone; i++) {
                System.out.print("Task number " + (i + 1) + ": ");
                taskIndexes[i] = sc.nextInt() - 1; // zero-based indexing
            }

            // Re-read all tasks into a list
            List<String> allTasks = new ArrayList<>();
            try (BufferedReader reader2 = new BufferedReader(new FileReader("tasks.txt"))) {
                String taskLine;
                while ((taskLine = reader2.readLine()) != null) {
                    allTasks.add(taskLine);
                }
            }

            // Write done tasks to Tasks_Done.txt and mark them for removal
            try (BufferedWriter doneWriter = new BufferedWriter(new FileWriter("Tasks_Done.txt"))) {
                for (int index : taskIndexes) {
                    if (index >= 0 && index < allTasks.size()) {
                        doneWriter.write(allTasks.get(index) + "\n");
                        allTasks.set(index, null); // mark for removal
                    }
                }
            }

            // Rewrite tasks.txt with remaining tasks
            try (BufferedWriter taskWriter = new BufferedWriter(new FileWriter("tasks.txt"))) {
                for (String task : allTasks) {
                    if (task != null) {
                        taskWriter.write(task + "\n");
                    }
                }
            }

            System.out.println("Done tasks written and removed from 'tasks.txt'");
        } catch (IOException e) {
            System.out.println("An error occurred");
            logger.log(Level.SEVERE, "AN ERROR OCCURRED", e);
        }
    }
    public static void main(String[] args) {
        boolean loop = true;
        while(loop) {
            System.out.print("Task Manager \n1. New Tasks \n2. Add Tasks \n3. Read Tasks \n4. Mark Tasks Done \nEnter your choice: ");
            int userChoice = sc.nextInt();
            switch (userChoice) {
                case 1: newTasks(); break;
                case 2: addTasks(); break;
                case 3: readTasks(); break;
                case 4: taskDone(); break;
                default:
                    System.out.println("Invalid Choice");
            }
            System.out.println("Do you want to continue?");
            String cont = sc.next();
            if (cont.equalsIgnoreCase("no")) {
                System.out.println("Have a Good Day");
                loop = false;
            }
        }
        System.out.println("\nProgram Terminated");
        sc.close();
    }
}
