import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class DayView extends JPanel implements ChangeListener {

//    private static final long SERIAL_VERSION_UID = 1L;
    private int frameBreadth;
    private int frameLength;
    private CalendarConfiguration modelCalendar;
    private JTextField jDateArea;
    private JTextArea eventListArea;
    private DAYS [] day;
    private MONTHS [] month;

    /**
     * DayView constructor
     * @param frameBreadth
     * - breadth of the frame since its a rectangle shape
     * @param frameLength
     * - length of the frame.
     * @param modelCalendar
     * - object referring ot the CalendarConfiguration
     */
    public DayView(int frameBreadth, int frameLength, CalendarConfiguration modelCalendar) {
        this.frameBreadth = frameBreadth;
        this.frameLength = frameLength;
        this.modelCalendar = modelCalendar;
        this.day = DAYS.values();
        this.month = MONTHS.values();
        jDateArea = new JTextField();
        eventListArea = new JTextArea();
        drawPanel();
    }

    private void inhabitFilling() {
        jDateArea.setText(day[modelCalendar.actualDayOfWeek() - 1].toString() + ", " + month[modelCalendar.actualMonth()].toString() + ' ' + modelCalendar.actualDate() + ", " + modelCalendar.actualYear());
        eventListArea.setText(modelCalendar.getEventList());
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        inhabitFilling();
    }

    public void drawPanel() {
        JPanel viewPanel = new JPanel();
        viewPanel.setPreferredSize(new Dimension(frameBreadth / 2, frameLength));
        viewPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE,3));
        viewPanel.setLayout(new BorderLayout());
        jDateArea.setEditable(false);
        jDateArea.setHorizontalAlignment(JTextField.CENTER);
        eventListArea.setEditable(false);
        JScrollPane eventListScroll = new JScrollPane(eventListArea);
        viewPanel.add(jDateArea, BorderLayout.NORTH);
        viewPanel.add(eventListScroll, BorderLayout.CENTER);
        inhabitFilling();

        this.add(viewPanel);
    }
}

