package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Hashtable;

/**
 * Created by Maia on 4/26/2017.
 */
public class HVACForm extends JFrame {
    private JPanel rootPane;
    private JTabbedPane ServiceCallPane;
    private JPanel addCallPanel;
    private JComboBox serviceCmbo;
    private JTextField addrText;
    private JTextField descText;
    private JComboBox typeCmbo;
    private JLabel tmaLabel;
    private JButton addBtn;
    private JPanel outstandingCallsPanel;
    private JList openCallsList;
    private JButton resolveBtn;
    private JPanel resolvedCallsJPanel;
    private JList resolvedCallsList;

    public Hashtable<String, String> calls = new Hashtable<String, String>();


    //listModel to allow open calls Jlist editable
    @SuppressWarnings("Since15")
    private DefaultListModel<String> listModel;

    protected HVACForm() {
        //create and set form
        setContentPane(rootPane);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);

        //Using combobox to determine which category the call is about.
        serviceCmbo.addItem("AC Unit");
        serviceCmbo.addItem("Furnace");
        serviceCmbo.addItem("Water Heater");

        serviceCmbo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serviceCmbo.getSelectedItem().equals("Furnace")) {
                    typeCmbo.setEditable(false);
                    typeCmbo.addItem("FORCED_AIR");
                    typeCmbo.addItem("BOILER");
                    typeCmbo.addItem("GRAVITY");
                    tmaLabel.setText("Type");
                } else if ((serviceCmbo.getSelectedItem().equals("AC Unit")) ||
                        (serviceCmbo.getSelectedItem().equals("Water Heater"))) {

                    //If AC Unit or Water Heater is selected then the combobox
                    //allows editing and deletes the Furnace options
                    //the label is also changed.

                    typeCmbo.removeAllItems();
                    typeCmbo.setEditable(true);
                    if (serviceCmbo.getSelectedItem().equals("AC Unit")) {
                        tmaLabel.setText("Model");
                    } else {
                        tmaLabel.setText("Age");
                    }
                }
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String address = addrText.getText();
                String problem = descText.getText();
                if (serviceCmbo.getSelectedItem().equals("Furnace")) {
                    Furnace.FurnaceType type = Furnace.FurnaceType.valueOf(typeCmbo.getSelectedItem().toString());
                    Furnace f = new Furnace(address, problem, new Date(), type);
                    f.setName("Furnace Service Call");
                    System.out.println("Added the following furnace to list of calls:\n" + f);
                    ServiceCallManager.todayServiceCalls.add(f);

                } else if (serviceCmbo.getSelectedItem().equals("AC Unit")) {
                    String model = typeCmbo.getSelectedItem().toString();
                    CentralAC ac = new CentralAC(address, problem, new Date(), model);
                    ac.setName("Central AC Unit Service Call");
                    System.out.println("Added the following AC unit to list of calls:\n" + ac);
                    ServiceCallManager.todayServiceCalls.add(ac);

                } else if (serviceCmbo.getSelectedItem().equals("Water Heater")) {
                    Double age = Double.valueOf(typeCmbo.getSelectedItem().toString());
                    WaterHeater wh = new WaterHeater(address, problem, new Date(), age);
                    wh.setName("Water Heater Service Call");
                    System.out.println("Added the following AC unit to list of calls:\n" + wh);
                    ServiceCallManager.todayServiceCalls.add(wh);
                }
                //TODO add verification popup window add validation after adding.

                //Add calls to list

                //noinspection Since15
                listModel = new DefaultListModel<String>();
                openCallsList.setModel(listModel);
                //clear list each time because readding each time
                listModel.clear();

                int callCount = 1;
                //Adds calls to hashmap to allow for a form to be created with more information.
                for (ServiceCall call : ServiceCallManager.todayServiceCalls) {
                    String sKey = "Service Call " + callCount++ + ": " + call.getName();
                    calls.put(sKey, call.toString());
                    listModel.addElement(sKey);
                }
            }
        });

        //Form created on double click to display more information about the service call.
        openCallsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                openCallsList = (JList)e.getSource();
                if (e.getClickCount() == 2){
                    //double click detected
                    int index = openCallsList.locationToIndex(e.getPoint());
                    System.out.println(index);

                    JFrame frame = new JFrame();
                    frame.setTitle("More Details");
                    JTextArea callDescription = new JTextArea();
                    //get the text of the selected index which is the key
                    //return the value of the key
                    callDescription.setText(calls.get(listModel.get(index)));
                    frame.add(callDescription);
                    frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                    callDescription.setVisible(true);
                    callDescription.setEditable(false);
                }
            }
        });

        resolveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = openCallsList.getSelectedIndex();
                ServiceCall c = ServiceCallManager.todayServiceCalls.get(index);
                String resolution = JOptionPane.showInputDialog("Enter resolution for ticket");

                c.setResolution(resolution);
                c.setResolvedDate(new Date());

                ServiceCall resolvedCall = c;
                ServiceCallManager.resolvedServiceCalls.add(resolvedCall);
                listModel.remove(index);


                //noinspection Since15
                listModel = new DefaultListModel<String>();
                resolvedCallsList.setModel(listModel);
                //clear list each time because reading each time
                listModel.clear();

                int callCount = 1;
                //Adds calls to hashmap to allow for a form to be created with more information.
                for (ServiceCall call : ServiceCallManager.resolvedServiceCalls) {
                    String sKey = "Service Call " + callCount++ + ": " + call.getName();
                    listModel.addElement(sKey);
                }
            }
        });
    }

}
