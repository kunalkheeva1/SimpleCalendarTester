import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MonthView extends JPanel implements ChangeListener {


    public MonthView(JFrame closeFrame, int frameBreadth, int frameLength, int offSet, CalendarConfiguration modelCalendar) {
        this.frameBreadth = frameBreadth;
        this.frameLength = frameLength;
        this.offSet = offSet;
        this.modelCalendar = modelCalendar;
        this.todayDate = modelCalendar.actualDate();
        this.todayMonth = modelCalendar.actualMonth();
        this.todayYear = modelCalendar.actualYear();
        this.todayDatePros = modelCalendar.firstDayOfWeek() + todayDate + 5;
        this.closeFrame = closeFrame;
        this.dateString = new JTextField();
        this.calButton = new JButton[49];
        this.arrayMonths = MONTHS.values();
        draw();
    }


    private void draw() {
        String [] weekDay = {"Su", "M", "T", "W", "Th", "F", "Sa"};

        JPanel calendarPanel = new JPanel();
        calendarPanel.setPreferredSize(new Dimension(frameBreadth / 2, frameLength));
        calendarPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray,3));
        this.add(calendarPanel);

        JPanel buttonPnl = new JPanel();
        buttonPnl.setPreferredSize(new Dimension((int)calendarPanel.getPreferredSize().getWidth() - offSet,
                (int)calendarPanel.getPreferredSize().getHeight() / 4));
        buttonPnl.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3));
        calendarPanel.add(buttonPnl);

        JPanel calendarPaneContent = new JPanel();
        calendarPaneContent.setPreferredSize(new Dimension((int)calendarPanel.getPreferredSize().getWidth() * 5 / 6, (int)calendarPanel.getPreferredSize().getHeight() * 3 / 4 - offSet));
        calendarPaneContent.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        calendarPanel.add(calendarPaneContent);

        JPanel bottom_And_Upside_pnl = new JPanel();
        bottom_And_Upside_pnl.setPreferredSize(new Dimension((int)calendarPanel.getPreferredSize().getWidth() / 6 - offSet, (int)calendarPanel.getPreferredSize().getHeight() * 3 / 4 - offSet));
        bottom_And_Upside_pnl.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        calendarPanel.add(bottom_And_Upside_pnl);
        // Create add/ "create" button to add event
        // attach listener to it
        JButton jNewButton = new JButton("Create");
        jNewButton.setForeground(Color.white);
        jNewButton.setBackground(Color.RED);
        jNewButton.setOpaque(true);
        jNewButton.setBorderPainted(false);
        jNewButton.setPreferredSize(new Dimension((int)buttonPnl.getPreferredSize().getWidth() / 4 - offSet, (int)buttonPnl.getPreferredSize().getHeight() / 2));
        jNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame build_Frame = new JFrame("Create an event");
                build_Frame.setSize(new Dimension(800, 200));
                build_Frame.setLayout(new FlowLayout());
                JTextField eventName = new JTextField();
                eventName.setPreferredSize(new Dimension(800, 70));
                build_Frame.add(eventName);
                JTextField dateArea = new JTextField();
                dateArea.setPreferredSize(new Dimension(110, 70));
                dateArea.setEditable(false);
                dateArea.setHorizontalAlignment(JTextField.CENTER);
                dateArea.setText((modelCalendar.actualMonth() + 1) + "/" + modelCalendar.actualDate() + "/" + modelCalendar.actualYear());
                build_Frame.add(dateArea);
                JTextField begin_With_Field = new JTextField();
                begin_With_Field.setPreferredSize(new Dimension(150, 50));
                begin_With_Field.setHorizontalAlignment(JTextField.CENTER);
                begin_With_Field.setText("--:-- am/pm");
                build_Frame.add(begin_With_Field);
                JTextField nextField = new JTextField();
                nextField.setPreferredSize(new Dimension(40, 40));
                nextField.setHorizontalAlignment(JTextField.CENTER);
                nextField.setBorder(BorderFactory.createEmptyBorder());
                nextField.setEditable(false);
                nextField.setText("to");
                build_Frame.add(nextField);
                JTextField finalField = new JTextField();
                finalField.setPreferredSize(new Dimension(150, 50));
                finalField.setHorizontalAlignment(JTextField.CENTER);
                finalField.setText("--:-- am/pm");
                build_Frame.add(finalField);
                JButton jButton = new JButton("Save!");
                jButton.setForeground(Color.WHITE);
                jButton.setPreferredSize(new Dimension(150, 40));
                jButton.setBackground(Color.GREEN);
                jButton.setOpaque(true);
                jButton.setBorderPainted(false);
                JFrame frameHoldError = new JFrame("Time Mismatch!");
                JTextField error_Text_Field = new JTextField();
                error_Text_Field.setPreferredSize(new Dimension(500, 50));
                error_Text_Field.setEditable(false);
                frameHoldError.add(error_Text_Field);
                frameHoldError.pack();
                jButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String error;
                            if((error = modelCalendar.addEvent(eventName.getText(), dateArea.getText(), begin_With_Field.getText(), finalField.getText())) != null) {
                                error_Text_Field.setText(error);
                                frameHoldError.setVisible(true);
                            }
                            else {
                                build_Frame.dispose();
                                frameHoldError.dispose();
                            }
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                build_Frame.add(jButton);
                build_Frame.setVisible(true);
            }
        });
        buttonPnl.add(jNewButton);

//        JButton jButtonDelete = new JButton("Delete");
//        jButtonDelete.setPreferredSize(new Dimension((int)buttonPnl.getPreferredSize().getWidth() / 4 - offSet, (int)buttonPnl.getPreferredSize().getHeight() / 2));
//        jButtonDelete.setForeground(Color.white);
//        jButtonDelete.setBackground(Color.BLACK);
//        jButtonDelete.setOpaque(true);
//        jButtonDelete.setBorderPainted(false);
//        buttonPnl.add(jButtonDelete);
//
//        jButtonDelete.addActionListener(new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFrame jFrameDelete = new JFrame("Delete an event");
//                jFrameDelete.setSize(new Dimension(750, 150));
//                jFrameDelete.setLayout(new FlowLayout());
//                JTextField eventHeading = new JTextField();
//                eventHeading.setPreferredSize(new Dimension(700, 50));
//                jFrameDelete.add(eventHeading);
//                JTextField jTextDateField = new JTextField();
//                jTextDateField.setPreferredSize(new Dimension(100, 50));
//                jTextDateField.setEditable(false);
//                jTextDateField.setHorizontalAlignment(JTextField.CENTER);
//                jTextDateField.setText((modelCalendar.actualMonth() + 1) + "/" + modelCalendar.actualDate() + "/" + modelCalendar.actualYear());
//                jFrameDelete.add(jTextDateField);
//                JTextField beginInitialField = new JTextField();
//                beginInitialField.setPreferredSize(new Dimension(150, 50));
//                beginInitialField.setHorizontalAlignment(JTextField.CENTER);
//                beginInitialField.setText("--:-- am/pm");
//                jFrameDelete.add(beginInitialField);
//                JTextField destinyField = new JTextField();
//                destinyField.setPreferredSize(new Dimension(40, 40));
//                destinyField.setHorizontalAlignment(JTextField.CENTER);
//                destinyField.setBorder(BorderFactory.createEmptyBorder());
//                destinyField.setEditable(false);
//                destinyField.setText("to");
//                jFrameDelete.add(destinyField);
//                JTextField sourceField = new JTextField();
//                sourceField.setPreferredSize(new Dimension(150, 50));
//                sourceField.setHorizontalAlignment(JTextField.CENTER);
//                sourceField.setText("--:-- am/pm");
//                jFrameDelete.add(sourceField);
//                JButton jDeleteButton = new JButton("Delete!!");
//                jDeleteButton.setForeground(Color.white);
//                jDeleteButton.setPreferredSize(new Dimension(150, 40));
//                jDeleteButton.setBackground(Color.BLACK);
//                jDeleteButton.setOpaque(true);
//                jDeleteButton.setBorderPainted(false);
//
//
//                jDeleteButton.addActionListener(new ActionListener() {
//                    public void actionPerformed(ActionEvent e) {
//                        modelCalendar.deleteChosenEvent(jTextDateField.getText(), beginInitialField.getText());
//                        jFrameDelete.dispose();
//
//                    }
//                });
//                jFrameDelete.add(jDeleteButton);
//                jFrameDelete.setVisible(true);
//            }
//
//        });


        JButton jQuitButton = new JButton("Quit!!");
        jQuitButton.setPreferredSize(new Dimension((int)buttonPnl.getPreferredSize().getWidth() / 4 - offSet, (int)buttonPnl.getPreferredSize().getHeight() / 2));
        jQuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    modelCalendar.saveEvents();
                    closeFrame.dispose();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        jQuitButton.setPreferredSize(new Dimension(150,75));
        jQuitButton.setBackground(Color.GREEN);
        jQuitButton.setForeground(Color.white);
        jQuitButton.setOpaque(true);
        jQuitButton.setBorderPainted(false);



        buttonPnl.add(jQuitButton);
        JButton jPreviousButton = new JButton("<Previous");
        jPreviousButton.setPreferredSize(new Dimension((int)buttonPnl.getPreferredSize().getWidth() / 2 - offSet, (int)buttonPnl.getPreferredSize().getHeight() / 2 - offSet));
        jPreviousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(modelCalendar.checkEvent((modelCalendar.actualMonth() + 1) + "/" + modelCalendar.actualDate() + "/" + modelCalendar.actualYear()))
                    calButton[modelCalendar.firstDayOfWeek() + 5 + todayDate].setBackground(Color.CYAN);
                else
                    calButton[modelCalendar.firstDayOfWeek() + 5 + todayDate].setBackground(null);
                modelCalendar.buttonUpdate(-1, 0);
            }
        });
        buttonPnl.add(jPreviousButton);

        JButton jNextButton = new JButton("Next>");
        jNextButton.setPreferredSize(new Dimension((int)buttonPnl.getPreferredSize().getWidth() / 2 - offSet, (int)buttonPnl.getPreferredSize().getHeight() / 2 - offSet));
        jNextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(modelCalendar.checkEvent((modelCalendar.actualMonth() + 1) + "/" + modelCalendar.actualDate() + "/" + modelCalendar.actualYear()))
                    calButton[modelCalendar.firstDayOfWeek() + 5 + todayDate].setBackground(Color.CYAN);
                else
                    calButton[modelCalendar.firstDayOfWeek() + 5 + todayDate].setBackground(null);
                modelCalendar.buttonUpdate(1, 0);
            }
        });
        buttonPnl.add(jNextButton);


        JButton upperJButton = new JButton("Up");
        upperJButton.setPreferredSize(new Dimension((int)bottom_And_Upside_pnl.getPreferredSize().getWidth() - offSet / 2, (int)bottom_And_Upside_pnl.getPreferredSize().getHeight() / 2 - offSet));
        upperJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calButton[modelCalendar.firstDayOfWeek() + 5 + todayDate].setBackground(null);
                modelCalendar.buttonUpdate(0, -1);
            }
        });
        bottom_And_Upside_pnl.add(upperJButton);

        JButton jBottomButton = new JButton("Bottom");
        jBottomButton.setPreferredSize(new Dimension((int)bottom_And_Upside_pnl.getPreferredSize().getWidth() - offSet / 2, (int)bottom_And_Upside_pnl.getPreferredSize().getHeight() / 2 - offSet));
        jBottomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calButton[modelCalendar.firstDayOfWeek() + 5 + todayDate].setBackground(null);
                modelCalendar.buttonUpdate(0, 1);
            }
        });
        bottom_And_Upside_pnl.add(jBottomButton);

        dateString.setPreferredSize(new Dimension((int)calendarPaneContent.getPreferredSize().getWidth() - offSet / 3, (int)calendarPaneContent.getPreferredSize().getHeight() / 8 - offSet / 3));
        dateString.setEditable(false);
        dateString.setHorizontalAlignment(JTextField.CENTER);
        calendarPaneContent.add(dateString);

        final int CAL_BUTTON_WIDTH = (int)calendarPaneContent.getPreferredSize().getWidth() / 7 - offSet / 3;
        final int CAL_BUTTON_HEIGHT = (int)calendarPaneContent.getPreferredSize().getHeight() / 8 - offSet / 3;

        ActionListener onClick = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(modelCalendar.checkEvent((modelCalendar.actualMonth() + 1) + "/" + modelCalendar.actualDate() + "/" + modelCalendar.actualYear()))
                    calButton[modelCalendar.firstDayOfWeek() + 5 + todayDate].setBackground(Color.CYAN);
                else
                    calButton[modelCalendar.firstDayOfWeek() + 5 + todayDate].setBackground(null);
                JButton clickedButton = (JButton)(e.getSource());
                modelCalendar.setDate(clickedButton.getText());
            }
        };

        for(int i = 0; i < calButton.length; i++) {
            if (i < 7)
                calButton[i] = new JButton(weekDay[i]);
            else {
                calButton[i] = new JButton();
                calButton[i].addActionListener(onClick);
            }
            calButton[i].setPreferredSize(new Dimension(CAL_BUTTON_WIDTH, CAL_BUTTON_HEIGHT));
            calendarPaneContent.add(calButton[i]);
        }

        inhabitCal();
        calButton[todayDatePros].setBorder(BorderFactory.createTitledBorder("C"));
        calButton[todayDatePros].setBackground(Color.ORANGE);
    }

    private void inhabitCal() {
        int ist_Week_Day = modelCalendar.firstDayOfWeek() + 6;
        int monthEndDay = modelCalendar.lastDayOfMonth();

        dateString.setText(arrayMonths[modelCalendar.actualMonth()].toString() + ' ' + modelCalendar.actualYear());
        for(int i = 7; i < ist_Week_Day; i++)
            calButton[i].setText(null);
        for(int i = ist_Week_Day + monthEndDay; i < calButton.length; i++)
            calButton[i].setText(null);
        for(int i = 0; i < monthEndDay; i++) {
            if(todayMonth == modelCalendar.currentMonth() && todayYear == modelCalendar.currentYear())
                calButton[todayDatePros].setBorder(BorderFactory.createTitledBorder("C"));
            if(modelCalendar.checkEvent((modelCalendar.actualMonth() + 1) + "/" + Integer.toString(i + 1) + "/" + modelCalendar.actualYear()))
                calButton[modelCalendar.firstDayOfWeek() + 5 + i + 1].setBackground(Color.CYAN);
            else
                calButton[modelCalendar.firstDayOfWeek() + 5 + i + 1].setBackground(null);
            calButton[ist_Week_Day].setText(Integer.toString(i + 1));
            ist_Week_Day++;
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(todayMonth != modelCalendar.actualMonth()) {
            todayMonth = modelCalendar.actualMonth();
            todayYear = modelCalendar.actualYear();
            calButton[todayDatePros].setBorder(UIManager.getBorder("Button.border"));
            inhabitCal();
        }
        todayDate = modelCalendar.actualDate();
        calButton[modelCalendar.firstDayOfWeek() + 5 + todayDate].setBackground(Color.ORANGE);
    }
    private static final long serialVersionUID = 1L;

    private int frameBreadth;
    private int frameLength;
    private int offSet;
    private int todayDate;
    private int todayMonth;
    private int todayYear;
    private int todayDatePros;

    private CalendarConfiguration modelCalendar;
    private JFrame closeFrame;
    private JButton [] calButton;
    private JTextField dateString;
    private MONTHS [] arrayMonths;
}