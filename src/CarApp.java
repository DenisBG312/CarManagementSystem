import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CarApp extends JFrame {

    private JTextField tfRegNumber = new JTextField(15);
    private JTextField tfMake = new JTextField(15);
    private JTextField tfModel = new JTextField(15);
    private JTextField tfYear = new JTextField(15);

    private String[] volumes = {"1.0", "1.2", "1.4", "1.6", "1.8", "2.0", "2.5", "3.0", "3.5", "4.0"};
    private JList<String> listVolume = new JList<>(volumes);

    private JButton btnAdd = new JButton("Добави");
    private JButton btnSearch = new JButton("Търси");
    private JButton btnShowAll = new JButton("Покажи всички");
    private JButton btnClear = new JButton("Изчисти");

    private JTextArea taResult = new JTextArea(5, 30);

    private String[] columns = {"Рег. номер", "Марка", "Модел", "Година", "Обем (л)"};
    private DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JTable table = new JTable(tableModel);

    private Car[] cars = new Car[10];
    private int count = 0;

    public CarApp() {
        setTitle("Регистър на автомобили");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel input = new JPanel(new GridBagLayout());
        input.setBorder(BorderFactory.createTitledBorder("Данни за автомобил"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;

        addRow(input, c, 0, "Рег. номер:", tfRegNumber);
        addRow(input, c, 1, "Марка:", tfMake);
        addRow(input, c, 2, "Модел:", tfModel);
        addRow(input, c, 3, "Година:", tfYear);

        c.gridx = 0; c.gridy = 4;
        input.add(new JLabel("Обем (л):"), c);
        c.gridx = 1; c.gridy = 4;
        listVolume.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listVolume.setVisibleRowCount(4);
        input.add(new JScrollPane(listVolume), c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttons.add(btnAdd);
        buttons.add(btnSearch);
        buttons.add(btnShowAll);
        buttons.add(btnClear);

        taResult.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(taResult);
        resultScroll.setBorder(BorderFactory.createTitledBorder("Резултат от справка"));

        table.setFillsViewportHeight(true);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Всички автомобили"));
        tableScroll.setPreferredSize(new Dimension(500, 150));

        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.add(input, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        JPanel bottom = new JPanel(new BorderLayout(10, 10));
        bottom.add(resultScroll, BorderLayout.NORTH);
        bottom.add(tableScroll, BorderLayout.CENTER);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.add(top, BorderLayout.NORTH);
        root.add(bottom, BorderLayout.CENTER);
        add(root);

        btnAdd.addActionListener(e -> onAdd());
        btnSearch.addActionListener(e -> onSearch());
        btnShowAll.addActionListener(e -> onShowAll());
        btnClear.addActionListener(e -> clearInputs());

        pack();
        setLocationRelativeTo(null);
    }

    private void addRow(JPanel p, GridBagConstraints c, int row, String label, JComponent field) {
        c.gridx = 0; c.gridy = row;
        p.add(new JLabel(label), c);
        c.gridx = 1; c.gridy = row;
        p.add(field, c);
    }

    private void onAdd() {
        String reg = tfRegNumber.getText().trim();
        String make = tfMake.getText().trim();
        String model = tfModel.getText().trim();
        String yearText = tfYear.getText().trim();
        String volume = listVolume.getSelectedValue();

        if (reg.isEmpty() || make.isEmpty() || model.isEmpty() || yearText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Моля, попълнете всички текстови полета.",
                    "Липсващи данни", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (volume == null) {
            JOptionPane.showMessageDialog(this,
                    "Моля, изберете обем на двигателя от списъка.",
                    "Липсващи данни", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Годината трябва да е цяло число.",
                    "Невалидни данни", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double engineVolume = Double.parseDouble(volume);

        if (count == cars.length) {
            Car[] bigger = new Car[cars.length * 2];
            for (int i = 0; i < cars.length; i++) {
                bigger[i] = cars[i];
            }
            cars = bigger;
        }

        cars[count] = new Car(reg, make, model, year, engineVolume);
        count++;

        taResult.setText("Добавен автомобил:\n" + cars[count - 1]);
        clearInputs();
    }

    private void onSearch() {
        String reg = tfRegNumber.getText().trim();

        if (reg.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Въведете рег. номер за търсене.",
                    "Празно поле", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Car found = null;
        for (int i = 0; i < count; i++) {
            if (cars[i].getRegNumber().equalsIgnoreCase(reg)) {
                found = cars[i];
                break;
            }
        }

        if (found != null) {
            taResult.setText(
                    "Рег. номер: " + found.getRegNumber() + "\n" +
                            "Марка: " + found.getMake() + "\n" +
                            "Модел: " + found.getModel() + "\n" +
                            "Година: " + found.getRegistrationYear() + "\n" +
                            "Обем: " + found.getEngineVolume() + " л");
        } else {
            taResult.setText("Няма автомобил с рег. номер \"" + reg + "\".");
        }
    }

    private void onShowAll() {
        tableModel.setRowCount(0);
        for (int i = 0; i < count; i++) {
            Car car = cars[i];
            tableModel.addRow(new Object[]{
                    car.getRegNumber(),
                    car.getMake(),
                    car.getModel(),
                    car.getRegistrationYear(),
                    car.getEngineVolume()
            });
        }
    }

    private void clearInputs() {
        tfRegNumber.setText("");
        tfMake.setText("");
        tfModel.setText("");
        tfYear.setText("");
        listVolume.clearSelection();
    }
}