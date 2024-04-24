import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class AxieView extends JFrame {
    private DefaultListModel<AxieModel> axieListModel;
    private JList<AxieModel> axieList;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton logoutButton;

    


    public AxieView() {
        setTitle("Axie Manager");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        
        axieListModel = new DefaultListModel<>();
        axieList = new JList<>(axieListModel);
        JScrollPane scrollPane = new JScrollPane(axieList);
        add(scrollPane, BorderLayout.CENTER);

        // Create buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        logoutButton = new JButton("Logout");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }


    public void updateAxieList(List<AxieModel> models) {
        axieListModel.clear();
        for (AxieModel model : models) {
            axieListModel.addElement(model);
        }
    }


    public AxieModel getSelectedAxie() {
        return axieList.getSelectedValue();
    }


    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addEditButtonListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    
    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }


    public String getInput(String message) {
        return JOptionPane.showInputDialog(this, message);
    }


    public String getInput(String message, String initialValue) {
        return JOptionPane.showInputDialog(this, message, initialValue);
    }


    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }


    public JButton getLogoutButton() {
        return logoutButton;
    }
}
