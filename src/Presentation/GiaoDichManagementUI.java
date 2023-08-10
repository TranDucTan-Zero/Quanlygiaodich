package Presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Domain.Model.GiaoDich;
import Domain.Model.GiaoDichDat;
import Domain.Model.GiaoDichNha;
import Domain.GiaoDichService;


public class GiaoDichManagementUI extends JFrame {

    private final GiaoDichService giaoDichService;
    private final DefaultTableModel tableModel;
    private final JTable giaoDichTable;
    private final JComboBox<String> loaiGiaoDichComboBox;
    
    private final JTextField maGiaoDichField, ngayGiaoDichField, donGiaField, dienTichField, thongTinKhacField;
    private final JButton addButton, removeButton, editButton;
    private final JTextField searchMaGiaoDichField;
    private final JButton searchButton;
    private final DefaultTableModel searchTableModel;
    private final JTable searchTable;
    private final JTextArea searchResultTextArea; // Thêm thành phần hiển thị kết quả tìm kiếm

    private final JComboBox<String> loaiDatComboBox;
    private final JComboBox<String> loaiNhaComboBox;

    public GiaoDichManagementUI(GiaoDichService giaoDichService) {
        this.giaoDichService = giaoDichService;

       // Initialize components
        String[] columnNames = { "Mã GD", "Ngày GD", "Đơn giá", "Diện tích", "Thành tiền", "Thông tin khác" };
        tableModel = new DefaultTableModel(columnNames, 0);
        giaoDichTable = new JTable(tableModel);

        loaiGiaoDichComboBox = new JComboBox<>();
        loaiGiaoDichComboBox.addItem("Giao dịch đất");
        loaiGiaoDichComboBox.addItem("Giao dịch nhà");

        loaiDatComboBox = new JComboBox<>();
        loaiDatComboBox.addItem("Loại A");
        loaiDatComboBox.addItem("Loại B");
        loaiDatComboBox.addItem("Loại C");
        loaiDatComboBox.setEnabled(false); // Initially disabled

        loaiNhaComboBox = new JComboBox<>();
        loaiNhaComboBox.addItem("Cao cấp");
        loaiNhaComboBox.addItem("Thường");
        loaiNhaComboBox.setEnabled(false); // Initially disabled

        maGiaoDichField = new JTextField(10);
        ngayGiaoDichField = new JTextField(10);
        donGiaField = new JTextField(10);
        dienTichField = new JTextField(10);
        thongTinKhacField = new JTextField(20);

        addButton = new JButton("Thêm");
        removeButton = new JButton("Xóa");
        editButton = new JButton("Sửa");
        searchMaGiaoDichField = new JTextField(10);
        searchButton = new JButton("Tìm kiếm");
    
        searchTableModel = new DefaultTableModel(columnNames, 0);
        searchTable = new JTable(searchTableModel);
        
        searchResultTextArea = new JTextArea(10, 40); // Thêm thành phần hiển thị kết quả tìm kiếm
        searchResultTextArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(searchResultTextArea);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGiaoDich();
            }
        });
        loaiGiaoDichComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoaiGiaoDich = (String) loaiGiaoDichComboBox.getSelectedItem();
                if (selectedLoaiGiaoDich.equals("Giao dịch đất")) {
                    loaiDatComboBox.setEnabled(true);
                    loaiNhaComboBox.setEnabled(false);
                    thongTinKhacField.setEnabled(false); // Disable thông tin khác khi chọn loại đất
                    thongTinKhacField.setText(""); // Clear thông tin khác khi chọn loại đất
                } else {
                    loaiDatComboBox.setEnabled(false);
                    loaiNhaComboBox.setEnabled(true);
                    thongTinKhacField.setEnabled(true); // Enable thông tin khác khi chọn loại nhà
                }
            }
        });
        loaiDatComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoaiDat = (String) loaiDatComboBox.getSelectedItem();
                if (selectedLoaiDat.equals("Loại A")) {
                    maGiaoDichField.setText("GDDA");
                } else if (selectedLoaiDat.equals("Loại B")) {
                    maGiaoDichField.setText("GDDB");
                } else if (selectedLoaiDat.equals("Loại C")) {
                    maGiaoDichField.setText("GDDC");
                }
            }
        });
        
        loaiNhaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoaiNha = (String) loaiNhaComboBox.getSelectedItem();
                if (selectedLoaiNha.equals("Cao cấp")) {
                    maGiaoDichField.setText("GDNCC");
                } else if (selectedLoaiNha.equals("Thường")) {
                    maGiaoDichField.setText("GDNT");
                }
            }
        });
        
        
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeGiaoDich();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editGiaoDich();
            }
        });
 // Thêm ActionListener cho nút tìm kiếm

 searchButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        searchGiaoDichByMa();
    }
});

        // Table selection action
        giaoDichTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedGiaoDichInfo();
            }
        });

       // Layout setup
       JPanel inputPanel = new JPanel(new GridBagLayout());
       GridBagConstraints gbc = new GridBagConstraints();
       gbc.gridx = 0;
       gbc.gridy = 0;
       gbc.anchor = GridBagConstraints.WEST;
       gbc.insets = new Insets(5, 5, 5, 5);
     // Loại giao dịch
     inputPanel.add(new JLabel("Loại giao dịch:"), gbc);
     gbc.gridx++;
     inputPanel.add(loaiGiaoDichComboBox, gbc);

     // Loại đất
     gbc.gridy++;
     gbc.gridx = 0;
     inputPanel.add(new JLabel("Loại đất (A, B, C):"), gbc);
     gbc.gridx++;
     inputPanel.add(loaiDatComboBox, gbc);

     // Loại nhà
     gbc.gridy++;
     gbc.gridx = 0;
     inputPanel.add(new JLabel("Loại nhà (Cao cấp, Thường):"), gbc);
     gbc.gridx++;
     inputPanel.add(loaiNhaComboBox, gbc);

     // Mã giao dịch
     gbc.gridy++;
     gbc.gridx = 0;
     inputPanel.add(new JLabel("Mã giao dịch:"), gbc);
     gbc.gridx++;
     inputPanel.add(maGiaoDichField, gbc);

     // Ngày giao dịch
     gbc.gridy++;
     gbc.gridx = 0;
     inputPanel.add(new JLabel("Ngày giao dịch (dd/MM/yyyy):"), gbc);
     gbc.gridx++;
     inputPanel.add(ngayGiaoDichField, gbc);

     // Đơn giá
     gbc.gridy++;
     gbc.gridx = 0;
     inputPanel.add(new JLabel("Đơn giá:"), gbc);
     gbc.gridx++;
     inputPanel.add(donGiaField, gbc);

     // Diện tích
     gbc.gridy++;
     gbc.gridx = 0;
     inputPanel.add(new JLabel("Diện tích:"), gbc);
     gbc.gridx++;
     inputPanel.add(dienTichField, gbc);

     // Thông tin khác
     gbc.gridy++;
     gbc.gridx = 0;
     inputPanel.add(new JLabel("Thông tin khác (Địa chỉ nhà):"), gbc);
     gbc.gridx++;
     inputPanel.add(thongTinKhacField, gbc);

       // Button panel
       gbc.gridy++;
       gbc.gridx = 0;
       gbc.gridwidth = 3;
       gbc.fill = GridBagConstraints.CENTER;
       JPanel buttonPanel = new JPanel();
       buttonPanel.add(addButton);
       buttonPanel.add(removeButton);
       buttonPanel.add(editButton);
       inputPanel.add(buttonPanel, gbc);

       // Thêm các thành phần tìm kiếm vào searchPanel
       gbc.gridy++;
       gbc.gridx = 0;
       gbc.gridwidth = 5;
       gbc.anchor = GridBagConstraints.LINE_START;
       JPanel searchPanel = new JPanel(new GridBagLayout());
       GridBagConstraints searchGbc = new GridBagConstraints();
       searchGbc.gridx = 0;
       searchGbc.gridy = 0;
       searchGbc.anchor = GridBagConstraints.LINE_START;

       searchPanel.add(new JLabel("Tìm kiếm mã giao dịch:"), searchGbc);
       searchGbc.gridx++;
       searchPanel.add(searchMaGiaoDichField, searchGbc);
       searchGbc.gridx++;
       searchPanel.add(searchButton, searchGbc);
       inputPanel.add(searchPanel, gbc);

       // Main panel setup
       JPanel mainPanel = new JPanel(new BorderLayout());

       mainPanel.add(new JScrollPane(giaoDichTable), BorderLayout.CENTER);
       mainPanel.add(inputPanel, BorderLayout.NORTH);
       mainPanel.add(resultScrollPane, BorderLayout.SOUTH); // Thêm thành phần hiển thị kết quả tìm kiếm

       // Thêm phần nhập liệu và tìm kiếm vào main panel
       mainPanel.add(inputPanel, BorderLayout.NORTH);

       // Tạo cửa sổ chương trình
       this.setTitle("Quản lý giao dịch nhà đất");
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.add(mainPanel);
       this.pack();
       this.setVisible(true);

       // Load existing data from service and populate the tables
       refreshGiaoDichTable();
   }
            
   private void addGiaoDich() {
    String loaiGiaoDich = (String) loaiGiaoDichComboBox.getSelectedItem();
    String maGiaoDich = maGiaoDichField.getText();
    String ngayGiaoDichStr = ngayGiaoDichField.getText();
    String donGiaStr = donGiaField.getText();
    String dienTichStr = dienTichField.getText();
    String thongTinKhac = thongTinKhacField.getText();

    if (maGiaoDich.isEmpty() || ngayGiaoDichStr.isEmpty() || donGiaStr.isEmpty() || dienTichStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin giao dịch.");
        return;
    }

    try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date ngayGiaoDich = dateFormat.parse(ngayGiaoDichStr);
        double donGia = Double.parseDouble(donGiaStr);
        double dienTich = Double.parseDouble(dienTichStr);

        GiaoDich giaoDich;
        if (loaiGiaoDich.equals("Giao dịch đất")) {
            String loaiDat = (String) loaiDatComboBox.getSelectedItem();
            giaoDich = new GiaoDichDat(maGiaoDich, ngayGiaoDich, donGia, dienTich, loaiDat);
        } else {
            String loaiNha = (String) loaiNhaComboBox.getSelectedItem();
            giaoDich = new GiaoDichNha(maGiaoDich, ngayGiaoDich, donGia, dienTich, loaiNha);
        }

        giaoDichService.addGiaoDich(giaoDich);
        giaoDich.setThongTinKhac(thongTinKhac);

        // Clear input fields
        maGiaoDichField.setText("");
        ngayGiaoDichField.setText("");
        donGiaField.setText("");
        dienTichField.setText("");
        thongTinKhacField.setText("");
        loaiDatComboBox.setSelectedIndex(0);
        loaiNhaComboBox.setSelectedIndex(0);

        // Refresh the table
        refreshGiaoDichTable();
    } catch (ParseException | NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Nhập sai định dạng dữ liệu.");
    }
}
    
    private void removeGiaoDich() {
        int selectedRow = giaoDichTable.getSelectedRow();
        if (selectedRow != -1) {
            String maGiaoDich = (String) giaoDichTable.getValueAt(selectedRow, 0);
            giaoDichService.removeGiaoDich(maGiaoDich);
            refreshGiaoDichTable();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giao dịch để xóa.");
        }
    }

    private void editGiaoDich() {
        int selectedRow = giaoDichTable.getSelectedRow();
        if (selectedRow != -1) {
            GiaoDich selectedGiaoDich = getSelectedGiaoDich();
    
            String loaiGiaoDich = (String) loaiGiaoDichComboBox.getSelectedItem();
            String ngayGiaoDichStr = ngayGiaoDichField.getText();
            String donGiaStr = donGiaField.getText();
            String dienTichStr = dienTichField.getText();
            String thongTinKhac = thongTinKhacField.getText();
    
            if (ngayGiaoDichStr.isEmpty() || donGiaStr.isEmpty() || dienTichStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin giao dịch.");
                return;
            }
    
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date ngayGiaoDich = dateFormat.parse(ngayGiaoDichStr);
                double donGia = Double.parseDouble(donGiaStr);
                double dienTich = Double.parseDouble(dienTichStr);
    
                if (selectedGiaoDich instanceof GiaoDichDat) {
                    String loaiDat = (String) loaiDatComboBox.getSelectedItem();
                    ((GiaoDichDat) selectedGiaoDich).setLoaiDat(loaiDat);
                } else if (selectedGiaoDich instanceof GiaoDichNha) {
                    String loaiNha = (String) loaiNhaComboBox.getSelectedItem();
                    ((GiaoDichNha) selectedGiaoDich).setLoaiNha(loaiNha);
                }
    
                selectedGiaoDich.setNgayGiaoDich(ngayGiaoDich);
                selectedGiaoDich.setDonGia(donGia);
                selectedGiaoDich.setDienTich(dienTich);
                selectedGiaoDich.setThongTinKhac(thongTinKhac);
    
                giaoDichService.editGiaoDich(selectedGiaoDich.getMaGiaoDich(), selectedGiaoDich);
    
                // Clear input fields
                clearInputFields();
    
                // Refresh the table
                refreshGiaoDichTable();
            } catch (ParseException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nhập sai định dạng dữ liệu.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giao dịch để sửa.");
        }
    }
    
    
    private GiaoDich getSelectedGiaoDich() {
        int selectedRow = giaoDichTable.getSelectedRow();
        String maGiaoDich = (String) giaoDichTable.getValueAt(selectedRow, 0);
        return giaoDichService.getGiaoDichByMa(maGiaoDich);
    }
    private void clearInputFields() {
        maGiaoDichField.setText("");
        ngayGiaoDichField.setText("");
        donGiaField.setText("");
        dienTichField.setText("");
        thongTinKhacField.setText("");
        loaiDatComboBox.setSelectedIndex(0);
        loaiNhaComboBox.setSelectedIndex(0);
    }
    private void showSelectedGiaoDichInfo() {
        int selectedRow = giaoDichTable.getSelectedRow();
        if (selectedRow != -1) {
            String maGiaoDich = (String) giaoDichTable.getValueAt(selectedRow, 0);
            GiaoDich giaoDich = giaoDichService.getGiaoDichByMa(maGiaoDich);
            if (giaoDich != null) {
                populateInputFields(giaoDich);
            }
        }
    }

    private void populateInputFields(GiaoDich giaoDich) {
        loaiGiaoDichComboBox.setSelectedItem(giaoDich instanceof GiaoDichDat ? "Giao dịch đất" : "Giao dịch nhà");
        maGiaoDichField.setText(giaoDich.getMaGiaoDich());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ngayGiaoDichField.setText(dateFormat.format(giaoDich.getNgayGiaoDich()));
        donGiaField.setText(String.valueOf(giaoDich.getDonGia()));
        dienTichField.setText(String.valueOf(giaoDich.getDienTich()));
        thongTinKhacField.setText(giaoDich.getThongTinKhac());

    }

    private void refreshGiaoDichTable() {
        // Clear existing data in the table
        tableModel.setRowCount(0);

        // Retrieve all giao dịch from the service and populate the table
        List<GiaoDich> giaoDichList = giaoDichService.getAllGiaoDich();
        for (GiaoDich giaoDich : giaoDichList) {
            Object[] rowData = {
                    giaoDich.getMaGiaoDich(),
                    formatDate(giaoDich.getNgayGiaoDich()),
                    giaoDich.getDonGia(),
                    giaoDich.getDienTich(),
                    giaoDich.tinhThanhTien(),
                    giaoDich.getThongTinKhac()
            };
            tableModel.addRow(rowData);
        }
    }
    
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    private void searchGiaoDichByMa() {
        String searchMaGiaoDich = searchMaGiaoDichField.getText();
        List<GiaoDich> searchResults = giaoDichService.searchGiaoDichByMa(searchMaGiaoDich);

        // Xóa dữ liệu hiện tại trong bảng tìm kiếm
        searchTableModel.setRowCount(0);

        // Thêm dữ liệu kết quả tìm kiếm vào bảng tìm kiếm
        if (!searchResults.isEmpty()) {
            for (GiaoDich giaoDich : searchResults) {
                Object[] rowData = {
                    giaoDich.getMaGiaoDich(),
                    formatDate(giaoDich.getNgayGiaoDich()),
                    giaoDich.getDonGia(),
                    giaoDich.getDienTich(),
                    giaoDich.tinhThanhTien(),
                    giaoDich.getThongTinKhac()
                };
                searchTableModel.addRow(rowData);
            }

            // Hiển thị thông tin kết quả tìm kiếm
            GiaoDich firstResult = searchResults.get(0); // Giả sử hiển thị thông tin của kết quả đầu tiên
            showSearchResultInfo(firstResult);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy giao dịch với mã " + searchMaGiaoDich);
            clearSearchResultInfo();
        }
    }
    private void showSearchResultInfo(GiaoDich giaoDich) {
        // Hiển thị thông tin của kết quả tìm kiếm trong JTextArea
        searchResultTextArea.setText("Thông tin kết quả tìm kiếm:\n");
        searchResultTextArea.append("Mã giao dịch: " + giaoDich.getMaGiaoDich() + "\n");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        searchResultTextArea.append("Ngày giao dịch: " + dateFormat.format(giaoDich.getNgayGiaoDich()) + "\n");
        
        searchResultTextArea.append("Loại giao dịch: " + (giaoDich instanceof GiaoDichDat ? "Giao dịch đất" : "Giao dịch nhà") + "\n");
        searchResultTextArea.append("Đơn giá: " + giaoDich.getDonGia() + "\n");
        searchResultTextArea.append("Diện tích: " + giaoDich.getDienTich() + "\n");
        searchResultTextArea.append("Thành tiền: " + giaoDich.tinhThanhTien() + "\n");
        searchResultTextArea.append("Thông tin khác(Địa chỉ nhà): " + giaoDich.getThongTinKhac() + "\n");
        
        if (giaoDich instanceof GiaoDichDat) {
            searchResultTextArea.append("Loại đất: " + ((GiaoDichDat) giaoDich).getLoaiDat() + "\n");
        } else if (giaoDich instanceof GiaoDichNha) {
            searchResultTextArea.append("Loại nhà: " + ((GiaoDichNha) giaoDich).getLoaiNha() + "\n");
        }        
    }
    
    private void clearSearchResultInfo() {
        // Xóa thông tin kết quả tìm kiếm trong JTextArea
        searchResultTextArea.setText("");
    }
}