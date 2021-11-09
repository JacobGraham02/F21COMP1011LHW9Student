package com.example.f21comp1011w9prep.Controllers;

import com.example.f21comp1011w9prep.Models.Course;
import com.example.f21comp1011w9prep.Models.Student;
import com.example.f21comp1011w9prep.Utilities.MagicData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class StudentUpdateController implements Initializable {

    @FXML
    private TableView<Student> tableView;

    @FXML
    private TableColumn<Student, Integer> studentNumCol;

    @FXML
    private TableColumn<Student, String> firstNameCol;

    @FXML
    private TableColumn<Student, String> lastNameCol;

    @FXML
    private TableColumn<Student, String> avgGradeCol;

    @FXML
    private TableColumn<Student, Integer> numOfCoursesCol;

    @FXML
    private TextField searchTextField;

    @FXML
    private ComboBox<Course> coursesComboBox;

    @FXML
    private Spinner<Integer> gradeSpinner;

    @FXML
    private Label rowsReturnedLabel;

    @FXML
    private Label studentSelectedLabel;

    @FXML
    private Button addCourseButton;

    @FXML
    private RadioButton allStudentRadioButton;

    @FXML
    private RadioButton honourRollRadioButton;

    @FXML
    private RadioButton top10RadioButton;

    private ToggleGroup toggleGroup;
    private ArrayList<Student> allStudents;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allStudents = MagicData.getStudents();

        //configure TableView
        studentNumCol.setCellValueFactory(new PropertyValueFactory<>("studNum"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        avgGradeCol.setCellValueFactory(new PropertyValueFactory<>("avgGradeString"));
        numOfCoursesCol.setCellValueFactory(new PropertyValueFactory<>("numOfCourses"));

        //set the tableview to only select a single student at a time and add a listener
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, studentSelected)->{
            setAddCourseVisibility(studentSelected != null);
            if (studentSelected != null)
            {
                studentSelectedLabel.setText(studentSelected.toString());
            }
        });

        //load the table and the update the labels
        tableView.getItems().addAll(allStudents);
        updateLabels();

        //configure the TextField with an event listener to filter the list of students in the tableview
        searchTextField.textProperty().addListener((observable, oldValue, searchText)->{
            ArrayList<Student> filtered = new ArrayList<>();

            for (Student student : allStudents)
            {
                if (student.contains(searchText))
                    filtered.add(student);
            }
            tableView.getItems().clear();
            tableView.getItems().addAll(filtered);
            updateLabels();
        });

        //configure the RadioButtons and ToggleGroup
        toggleGroup = new ToggleGroup();
        allStudentRadioButton.setToggleGroup(toggleGroup);
        top10RadioButton.setToggleGroup(toggleGroup);
        honourRollRadioButton.setToggleGroup(toggleGroup);
        allStudentRadioButton.setSelected(true);

        toggleGroup.selectedToggleProperty().addListener((obs, oldButton, newButton)->{
            tableView.getItems().clear();
            if (newButton == honourRollRadioButton)
            {
                tableView.getItems().addAll(allStudents.stream()
                                                    .filter(student -> student.getAvgMark()>=80)
                                                    .sorted()
                                                    .collect(Collectors.toList()));
            }
            else if (newButton == top10RadioButton)
            {
                tableView.getItems().addAll(allStudents.stream()
                                                        .sorted(Comparator.comparingDouble(Student::getAvgMark).reversed())
                                                        .limit(10)
                                                        .collect(Collectors.toList()));
            }
            else
                tableView.getItems().addAll(allStudents);
            updateLabels();
        });

        //make the student selected field invisible until a student is selected
        setAddCourseVisibility(false);

        //configure the combobox with the courses
        coursesComboBox.getItems().addAll(MagicData.getCourseCodes());

        //configure the spinner object
        SpinnerValueFactory<Integer> gradeFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 75);
        gradeSpinner.setValueFactory(gradeFactory);
        gradeSpinner.setEditable(true);
        TextField spinnerEditor = gradeSpinner.getEditor();
        spinnerEditor.textProperty().addListener((observableValue, oldValue, newValue)->
        {
            try {
                Integer.parseInt(newValue);
            } catch (NumberFormatException e)
            {
                spinnerEditor.setText(oldValue);
            }
        });
    }

    /**
     * This method will update the visibility of the add course features if a student is selected
     */
    private void setAddCourseVisibility(boolean visible)
    {
        studentSelectedLabel.setVisible(visible);
        coursesComboBox.setVisible(visible);
        gradeSpinner.setVisible(visible);
        addCourseButton.setVisible(visible);
    }

    private void updateLabels()
    {
        tableView.refresh();
        rowsReturnedLabel.setText("Students in Table: " + tableView.getItems().size());
    }

    @FXML
    private void addGrade()
    {
        Student student = tableView.getSelectionModel().getSelectedItem();
        Course course = coursesComboBox.getValue();
        int grade = gradeSpinner.getValue();

        if (student != null && course != null && grade>=0 && grade <= 100)
        {
            student.addCourse(course, grade);
        }
        updateLabels();
    }

}

