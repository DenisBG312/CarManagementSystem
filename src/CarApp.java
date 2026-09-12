import javax.swing.*;
import java.awt.*;

public class CarApp extends JFrame {

    private JTextField tfRegNumber = new JTextField(15);
    private JTextField tfMake      = new JTextField(15);
    private JTextField tfModel     = new JTextField(15);
    private JTextField tfYear      = new JTextField(15);

    private String[] volumes = {"1.0", "1.2", "1.4", "1.6", "1.8", "2.0", "2.5", "3.0", "3.5", "4.0"};
    private JList<String> listVolume = new JList<>(volumes);

    private JButton btnAdd = new JButton("Добави");
    private JButton btnSearch = new JButton("Търси");
    private JButton btnClear = new JButton("Изчисти");

    private JTextArea taResult = new JTextArea(8, 30);

    public CarApp() {
        setTitle("Регистър на автомобили");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel input = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.anchor = GridBagConstraints.WEST;

        addRow(input, c, 0, "Рег. номер:", tfRegNumber);
        addRow(input, c, 1, "Марка:",      tfMake);
        addRow(input, c, 2, "Модел:",      tfModel);
        addRow(input, c, 3, "Година:",     tfYear);

        c.gridx = 0; c.gridy = 4;
        input.add(new JLabel("Обем (л):"), c);
        c.gridx = 1; c.gridy = 4;
        listVolume.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listVolume.setVisibleRowCount(4);
        input.add(new JScrollPane(listVolume), c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttons.add(btnAdd);
        buttons.add(btnSearch);
        buttons.add(btnClear);

        taResult.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(taResult);
        resultScroll.setBorder(BorderFactory.createTitledBorder("Резултат"));

        add(input,        BorderLayout.NORTH);
        add(buttons,      BorderLayout.CENTER);
        add(resultScroll, BorderLayout.SOUTH);

        btnAdd.addActionListener(e    -> onAdd());
        btnSearch.addActionListener(e -> onSearch());
        btnClear.addActionListener(e  -> clearInputs());

        pack();
        setLocationRelativeTo(null);
    }

    private void addRow(JPanel p, GridBagConstraints c, int row,
                        String label, JComponent field) {
        c.gridx = 0; c.gridy = row;
        p.add(new JLabel(label), c);
        c.gridx = 1; c.gridy = row;
        p.add(field, c);
    }

    private void onAdd()    { }
    private void onSearch() { }

    private void clearInputs() {
        tfRegNumber.setText("");
        tfMake.setText("");
        tfModel.setText("");
        tfYear.setText("");
        listVolume.clearSelection();
    }
}