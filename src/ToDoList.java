import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ToDoList {
    public static void main(String[] str){
        new ToDoList();
    }
    private Map<Integer, Task> todoList = new HashMap<>();
    private int numberOfTasks=0;

    private JFrame frame;
    private JList<String> list;
    private DefaultListModel<String> defaultList;
    private JTextField textField;
    public ToDoList() {
        frame = new JFrame("ToDo List");
        defaultList = new DefaultListModel<>();
        list = new JList<>();
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(50,10, 400,300);
        frame.add(scrollPane);

        JLabel label = new JLabel("Task:");
        label.setBounds(50, 350, 40, 20);
        frame.add(label);

        textField = new JTextField();
        textField.setBounds(90, 350, 360, 20);
        frame.add(textField);

        JButton addButton = new JButton("Add");
        addButton.setBounds(225,420,50,20);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = textField.getText();
                addTask(task);
                writeList();
            }
        });
        frame.add(addButton);

//        JButton deleteButton = new JButton("Delete");
//        deleteButton.setBounds(170,420,60,20);
//        frame.add(deleteButton);
//
//        JButton completedButton = new JButton("Completed");
//        completedButton.setBounds(270,420,100,20);
//        frame.add(completedButton);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int listID = -1;
                if(!e.getValueIsAdjusting()){
                    listID = list.getSelectedIndex();
                    if(listID != -1){
                        completeTask(listID+1);
                        writeList();
                    }
                }
            }
        });

        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void writeList(){
        if(!defaultList.isEmpty()) {
            defaultList.removeAllElements();
        }

        for(var id = 1; id <= numberOfTasks; id++)
            if(todoList.containsKey(id)) {
                defaultList.addElement(todoList.get(id).formatTask(id));
            }

        textField.setText("");
        list.setModel(defaultList);
    }

    private void addTask(String description){
        numberOfTasks++;
        todoList.put(numberOfTasks, new Task(numberOfTasks, description));
    }

    private void alterTask(char option, int id){
        if(id < 1 || id > numberOfTasks)
            throw new IllegalArgumentException();
        if(option == 'c')
            completeTask(id);
        else if(option=='d')
            deleteTask(id);
    }

    private void deleteTask(int id){
        todoList.remove(id);
    }

    private void completeTask(int id){
        todoList.get(id).setCompleted();
    }

    private class Task{
        private String description;

        private boolean completed;

        private String getDescription() {
            return description;
        }

        private boolean isCompleted() {
            return completed;
        }

        private void setCompleted() {
            this.completed = !this.completed;
        }

        private Task(int id, String description) {
            this.description = description;
            completed = false;
        }

        private String formatTask(int id){
            String output = new String(id + " : " + getDescription());
            if(isCompleted())
                output = output.concat(" DONE");
            return output;
        }
    }
}
