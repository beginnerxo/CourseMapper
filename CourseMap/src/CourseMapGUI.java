import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class CourseMapGUI extends CourseManager{

    private JFrame frame;

    public CourseMapGUI() {
        frame = new JFrame("Course Map Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JButton generateCourseButton = new JButton("Generate Course Map");
        generateCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateCourseMap();
            }
        });

        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourse();
            }
        });

        JButton deleteCourseButton = new JButton("Delete Course");
        deleteCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourse();
            }
        });

        JLabel infoLabel = new JLabel("Click the button to generate the course map.");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(generateCourseButton, BorderLayout.EAST);
        panel.add(addCourseButton, BorderLayout.WEST);
        panel.add(deleteCourseButton, BorderLayout.NORTH);
        panel.add(infoLabel, BorderLayout.SOUTH);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CourseMapGUI();
            }
        });
    }
}
