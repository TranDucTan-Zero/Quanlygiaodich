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
import java.util.ArrayList;
import java.util.List;
import Domain.GiaoDichService;


public class GiaoDichManagementUI extends JFrame {
    private List<GiaoDichService> observers = new ArrayList<>();
        // Thành phần giao diện để hiển thị thông báo
                private final GiaoDichService giaoDichService;// Dịch vụ quản lý giao dịch
                private final DefaultTableModel tableModel;// Bảng dữ liệu chính hiển thị thông tin giao dịch
                private final JTable giaoDichTable;
                // ComboBox chọn loại giao dịch (Giao dịch đất hoặc Giao dịch nhà)
                private final JComboBox<String> loaiGiaoDichComboBox;
                // Trường nhập liệu cho mã giao dịch, ngày giao dịch, đơn giá, diện tích, thông tin khác
                private final JTextField maGiaoDichField, ngayGiaoDichField, donGiaField, dienTichField, thongTinKhacField;
                // Các nút chức năng (Thêm, Xóa, Sửa)
                private final JButton addButton, removeButton, editButton;
                // Trường nhập liệu cho mã giao dịch khi tìm kiếm
                private final JTextField searchMaGiaoDichField;
                // Nút thực hiện tìm kiếm
                private final JButton searchButton;
                // Bảng dữ liệu hiển thị kết quả tìm kiếm
                private final DefaultTableModel searchTableModel;
                private final JTable searchTable;
                // Vùng hiển thị kết quả tìm kiếm dưới dạng văn bản
                private final JTextArea searchResultTextArea;
                // ComboBox chọn loại đất (Loại A, Loại B, Loại C)
                private final JComboBox<String> loaiDatComboBox;
                // ComboBox chọn loại nhà (Cao cấp, Thường)
                private final JComboBox<String> loaiNhaComboBox;

                                
    public GiaoDichManagementUI(GiaoDichService giaoDichService) {
        this.giaoDichService = giaoDichService; // Khởi tạo giaoDichService để sử dụng các chức năng của dịch vụ quản lý giao dịch

            // KHởi tạo các thành thành
                String[] columnNames = { "Mã GD", "Ngày GD", "Đơn giá", "Diện tích", "Thành tiền", "Thông tin khác" };
                tableModel = new DefaultTableModel(columnNames, 0);
                giaoDichTable = new JTable(tableModel);
                
                // COmbobox Giao dịch
                loaiGiaoDichComboBox = new JComboBox<>();
                loaiGiaoDichComboBox.addItem("");
                loaiGiaoDichComboBox.addItem("Giao dịch đất");
                loaiGiaoDichComboBox.addItem("Giao dịch nhà");
                

                // ComboBox cho loại đất
                loaiDatComboBox = new JComboBox<>();
                loaiDatComboBox.addItem("");
                loaiDatComboBox.addItem("Loại A");
                loaiDatComboBox.addItem("Loại B");
                loaiDatComboBox.addItem("Loại C");
                loaiDatComboBox.setEnabled(false); //Bị vô hiệu hóa
              

                // ComboBox cho loại nhà
                loaiNhaComboBox = new JComboBox<>();
                loaiNhaComboBox.addItem("");
                loaiNhaComboBox.addItem("Cao cấp");
                loaiNhaComboBox.addItem("Thường");
                loaiNhaComboBox.setEnabled(false); // Bị vô hiệu hóa
               

                // Các trường nhập liệu kích thước
                maGiaoDichField = new JTextField(15);
                ngayGiaoDichField = new JTextField(15);
                donGiaField = new JTextField(15);
                dienTichField = new JTextField(15);
                thongTinKhacField = new JTextField(15);
                searchMaGiaoDichField = new JTextField(15);

                // Các nút chức năng
                addButton = new JButton("Thêm");
                removeButton = new JButton("Xóa");
                editButton = new JButton("Sửa");
                searchButton = new JButton("Tìm kiếm");
                
                // Bảng tìm kiếm và kết quả tìm kiếm
                searchTableModel = new DefaultTableModel(columnNames, 0);
                searchTable = new JTable(searchTableModel);
                
                searchResultTextArea = new JTextArea(10, 40); // Thêm thành phần hiển thị kết quả tìm kiếm
                searchResultTextArea.setEditable(false);
                JScrollPane resultScrollPane = new JScrollPane(searchResultTextArea);
       
        // nút hành động
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGiaoDich();
            }
        });
        loaiGiaoDichComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy loại giao dịch đã chọn từ ComboBox
                String selectedLoaiGiaoDich = (String) loaiGiaoDichComboBox.getSelectedItem();
        
                // Kiểm tra loại giao dịch đã chọn và thực hiện các thay đổi tương ứng
                if (selectedLoaiGiaoDich.equals("Giao dịch đất")) {
                    // Nếu chọn loại "Giao dịch đất"
                    loaiDatComboBox.setEnabled(true);
                    loaiNhaComboBox.setEnabled(false);
                    thongTinKhacField.setEnabled(false); // Vô hiệu hóa thông tin khác khi chọn loại đất
                    thongTinKhacField.setText(""); // Xóa nội dung thông tin khác khi chọn loại đất
                } else {
                    // Nếu chọn loại "Giao dịch nhà"
                    loaiDatComboBox.setEnabled(false);
                    loaiNhaComboBox.setEnabled(true);
                    thongTinKhacField.setEnabled(true); // Kích hoạt thông tin khác khi chọn loại nhà
                }
            }
        });
        
        loaiDatComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy loại đất đã chọn từ ComboBox
                String selectedLoaiDat = (String) loaiDatComboBox.getSelectedItem();
                // Xác định mã giao dịch dựa trên loại đất đã chọn
                String maGiaoDich = "";
                if (selectedLoaiDat.equals("Loại A")) {
                    maGiaoDich = "GDDA";
                } else if (selectedLoaiDat.equals("Loại B")) {
                    maGiaoDich = "GDDB";
                } else if (selectedLoaiDat.equals("Loại C")) {
                    maGiaoDich = "GDDC";
                }
                // Đặt mã giao dịch vào trường nhập liệu maGiaoDichField
                maGiaoDichField.setText(maGiaoDich);
            }
        });
        
        loaiNhaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy loại nhà đã chọn từ ComboBox
                String selectedLoaiNha = (String) loaiNhaComboBox.getSelectedItem();
        
                // Kiểm tra loại nhà đã chọn và thực hiện các thay đổi tên mã 
                if (selectedLoaiNha.equals("Cao cấp")) {
                    maGiaoDichField.setText("GDNCC");
                } else if (selectedLoaiNha.equals("Thường")) {
                    maGiaoDichField.setText("GDNT");
                }
            }
        });
        
        // xóa
        
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeGiaoDich();
            }
        });
// sưa
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

       // Bố cục
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


       // Bảng đk
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
        gbc.gridwidth = 2; // Đặt gridwidth thành 2 để phù hợp với phần nhập liệu
        gbc.anchor = GridBagConstraints.LINE_START;
// tim kiếm
        inputPanel.add(new JLabel("Tìm kiếm mã giao dịch:"),gbc);
        gbc.gridx++;
        inputPanel.add(searchMaGiaoDichField, gbc);
        gbc.gridx++;
        inputPanel.add(searchButton, gbc);

    
       // Bảng dk chính
       JPanel mainPanel = new JPanel(new BorderLayout());
       
       mainPanel.add(new JScrollPane(giaoDichTable), BorderLayout.CENTER);
       mainPanel.add(inputPanel, BorderLayout.NORTH);
       
       mainPanel.add(resultScrollPane, BorderLayout.SOUTH); // Thêm thành phần hiển thị kết quả tìm kiếm

       // Thêm phần nhập liệu và tìm kiếm vào main panel
       mainPanel.add(inputPanel, BorderLayout.NORTH);

       // Tạo cửa sổ chương trình
       this.setTitle("Quản lý giao dịch nhà đất");
       setSize(800, 600); // Điều chỉnh kích thước theo mong muốn
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.add(mainPanel);
       this.pack();
       this.setVisible(true);


      
       refreshGiaoDichTable(); // Gọi phương thức này để cập nhật dữ liệu từ cơ sở dữ liệu vào giao diện
   }  
   // sujet

            public void addObserver(GiaoDichService observer) {
                observers.add(observer);
            }

            public void removeObserver(GiaoDichService observer) {
                observers.remove(observer);
            }

            private void notifyObservers(GiaoDich newGiaoDich) {
                for (GiaoDichService observer : observers) {
                    observer.update(newGiaoDich);
                }
            }

                    
  /**
 * Thêm thông tin một giao dịch mới vào cơ sở dữ liệu dựa trên dữ liệu nhập từ giao diện.
 * Nếu dữ liệu nhập không hợp lệ, sẽ hiển thị thông báo lỗi.
 */
private void addGiaoDich() {
    // Lấy thông tin từ các trường nhập liệu trên giao diện
    String loaiGiaoDich = (String) loaiGiaoDichComboBox.getSelectedItem();
    String maGiaoDich = maGiaoDichField.getText();
    String ngayGiaoDichStr = ngayGiaoDichField.getText();
    String donGiaStr = donGiaField.getText();
    String dienTichStr = dienTichField.getText();
    String thongTinKhac = thongTinKhacField.getText();

    // Kiểm tra dữ liệu nhập liệu
    if (maGiaoDich.isEmpty() || ngayGiaoDichStr.isEmpty() || donGiaStr.isEmpty() || dienTichStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin giao dịch.");
        return;
    }

    try {
        // Chuyển đổi dữ liệu chuỗi thành các kiểu dữ liệu phù hợp
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date ngayGiaoDich = dateFormat.parse(ngayGiaoDichStr);
        double donGia = Double.parseDouble(donGiaStr);
        double dienTich = Double.parseDouble(dienTichStr);

        // Tạo đối tượng GiaoDich tương ứng với loại giao dịch
        GiaoDich giaoDich;
        if (loaiGiaoDich.equals("Giao dịch đất")) {
            String loaiDat = (String) loaiDatComboBox.getSelectedItem();
            giaoDich = new GiaoDichDat(maGiaoDich, ngayGiaoDich, donGia, dienTich, loaiDat);
        } else {
            String loaiNha = (String) loaiNhaComboBox.getSelectedItem();
            giaoDich = new GiaoDichNha(maGiaoDich, ngayGiaoDich, donGia, dienTich, loaiNha);
            giaoDich.setThongTinKhac(thongTinKhac);
        }
            // Thêm giao dịch vào cơ sở dữ liệu và cập nhật thông tin khác
            giaoDichService.addGiaoDich(giaoDich);
        
       // thông báo GiaoDich
       notifyObservers(giaoDich);
        // Xóa dữ liệu từ giao diện và cập nhật lại bảng giao dịch
        maGiaoDichField.setText("");
        ngayGiaoDichField.setText("");
        donGiaField.setText("");
        dienTichField.setText("");
        thongTinKhacField.setText("");
        loaiDatComboBox.setSelectedIndex(0);
        loaiNhaComboBox.setSelectedIndex(0);

        // Xóa giao dịch cũ từ cơ sở dữ liệu và cập nhật lại bảng giao dịch
       
        refreshGiaoDichTable();
    } catch (ParseException | NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Nhập sai định dạng dữ liệu.");
    }
  
}
       
    
private void removeGiaoDich() {
    // Lấy chỉ số hàng đã chọn trên bảng giao dịch
    int selectedRow = giaoDichTable.getSelectedRow();
    if (selectedRow != -1) {
        // Lấy mã giao dịch từ dòng đã chọn và xóa giao dịch khỏi cơ sở dữ liệu
        String maGiaoDich = (String) giaoDichTable.getValueAt(selectedRow, 0);
        giaoDichService.removeGiaoDich(maGiaoDich);
        
        // Cập nhật lại bảng giao dịch sau khi xóa
        refreshGiaoDichTable();
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn giao dịch để xóa.");
    }
}
    private void editGiaoDich() {
        int selectedRow = giaoDichTable.getSelectedRow();
        if (selectedRow != -1) {
            GiaoDich selectedGiaoDich = getSelectedGiaoDich();

            // Lấy thông tin từ các trường nhập liệu
            String loaiGiaoDich = (String) loaiGiaoDichComboBox.getSelectedItem();
            String ngayGiaoDichStr = ngayGiaoDichField.getText();
            String donGiaStr = donGiaField.getText();
            String dienTichStr = dienTichField.getText();
            String thongTinKhac = thongTinKhacField.getText();

            // Kiểm tra thông tin nhập liệu
            if (ngayGiaoDichStr.isEmpty() || donGiaStr.isEmpty() || dienTichStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin giao dịch.");
                return;
            }

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date ngayGiaoDich = dateFormat.parse(ngayGiaoDichStr);
                double donGia = Double.parseDouble(donGiaStr);
                double dienTich = Double.parseDouble(dienTichStr);

                // Thực hiện chỉnh sửa dữ liệu giao dịch
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

                 // Cập nhật dữ liệu giao dịch trong cơ sở dữ liệu
                giaoDichService.editGiaoDich(selectedGiaoDich.getMaGiaoDich(), selectedGiaoDich);

                // Xóa thông tin nhập liệu
                clearInputFields();

                // Cập nhật lại bảng giao dịch sau khi chỉnh sửa
                refreshGiaoDichTable();
            } catch (ParseException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nhập sai định dạng dữ liệu.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giao dịch để sửa.");
        }
    }
    
   /**
 * Lấy thông tin giao dịch được chọn từ bảng giao dịch.
 * @return Đối tượng GiaoDich tương ứng với giao dịch được chọn.
 */
private GiaoDich getSelectedGiaoDich() {
    int selectedRow = giaoDichTable.getSelectedRow();
    String maGiaoDich = (String) giaoDichTable.getValueAt(selectedRow, 0);
    return giaoDichService.getGiaoDichByMa(maGiaoDich);
}

/**
 * Xóa dữ liệu trong các trường nhập liệu.
 */
private void clearInputFields() {
    maGiaoDichField.setText("");
    ngayGiaoDichField.setText("");
    donGiaField.setText("");
    dienTichField.setText("");
    thongTinKhacField.setText("");
    loaiDatComboBox.setSelectedIndex(0);
    loaiNhaComboBox.setSelectedIndex(0);
}

/**
 * Hiển thị thông tin của giao dịch được chọn trong các trường nhập liệu.
 * Nếu không có giao dịch nào được chọn, không thực hiện gì cả.
 */
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


   // Điền thông tin từ đối tượng GiaoDich vào các trường nhập liệu tương ứng
private void populateInputFields(GiaoDich giaoDich) {
    // Thiết lập mục được chọn trong combobox loại giao dịch
    // Tùy thuộc vào loại đối tượng GiaoDich là GiaoDichDat hay GiaoDichNha
    loaiGiaoDichComboBox.setSelectedItem(giaoDich instanceof GiaoDichDat ? "Giao dịch đất" : "Giao dịch nhà");
    // Điền thông tin mã giao dịch vào trường nhập liệu
    maGiaoDichField.setText(giaoDich.getMaGiaoDich());
    // Định dạng ngày giao dịch và điền vào trường nhập liệu ngày giao dịch
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    ngayGiaoDichField.setText(dateFormat.format(giaoDich.getNgayGiaoDich()));
    // Điền thông tin đơn giá vào trường nhập liệu đơn giá
    donGiaField.setText(String.valueOf(giaoDich.getDonGia()));
    // Điền thông tin diện tích vào trường nhập liệu diện tích
    dienTichField.setText(String.valueOf(giaoDich.getDienTich()));
    // Điền thông tin khác (địa chỉ nhà) vào trường nhập liệu thông tin khác
    thongTinKhacField.setText(giaoDich.getThongTinKhac());
}

   // Cập nhật lại dữ liệu trong bảng hiển thị giao dịch
private void refreshGiaoDichTable() {
    tableModel.setRowCount(0); // Xóa dữ liệu hiện tại trong bảng
    List<GiaoDich> giaoDichList = giaoDichService.getAllGiaoDich(); // Lấy dữ liệu từ cơ sở dữ liệu

    // Lặp qua danh sách giao dịch và thêm dữ liệu vào bảng
    for (GiaoDich giaoDich : giaoDichList) {
        Object[] rowData = {
                giaoDich.getMaGiaoDich(),
                formatDate(giaoDich.getNgayGiaoDich()), // Định dạng ngày
                giaoDich.getDonGia(),
                giaoDich.getDienTich(),
                giaoDich.tinhThanhTien(), // Tính thành tiền
                giaoDich.getThongTinKhac()
        };
        tableModel.addRow(rowData); // Thêm hàng dữ liệu vào bảng
    }
}

// Định dạng ngày theo định dạng "dd/MM/yyyy"
private String formatDate(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    return dateFormat.format(date);
}


    // Tìm kiếm giao dịch theo mã
private void searchGiaoDichByMa() {
    String searchMaGiaoDich = searchMaGiaoDichField.getText(); // Lấy mã giao dịch từ trường tìm kiếm
    List<GiaoDich> searchResults = giaoDichService.searchGiaoDichByMa(searchMaGiaoDich); // Tìm kiếm trong cơ sở dữ liệu

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
   // Hiển thị thông tin kết quả tìm kiếm trong JTextArea
private void showSearchResultInfo(GiaoDich giaoDich) {
    searchResultTextArea.setText("Thông tin kết quả tìm kiếm:\n");
    searchResultTextArea.append("Mã giao dịch: " + giaoDich.getMaGiaoDich() + "\n");
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    searchResultTextArea.append("Ngày giao dịch: " + dateFormat.format(giaoDich.getNgayGiaoDich()) + "\n");
    
    // Hiển thị loại giao dịch tùy thuộc vào loại đất hoặc loại nhà
    if (giaoDich instanceof GiaoDichDat) {
        searchResultTextArea.append("Loại giao dịch: Giao dịch đất\n");
        searchResultTextArea.append("Loại đất: " + ((GiaoDichDat) giaoDich).getLoaiDat() + "\n");
    } else if (giaoDich instanceof GiaoDichNha) {
        searchResultTextArea.append("Loại giao dịch: Giao dịch nhà\n");
        searchResultTextArea.append("Loại nhà: " + ((GiaoDichNha) giaoDich).getLoaiNha() + "\n");
    }
    
    searchResultTextArea.append("Đơn giá: " + giaoDich.getDonGia() + "\n");
    searchResultTextArea.append("Diện tích: " + giaoDich.getDienTich() + "\n");
    searchResultTextArea.append("Thành tiền: " + giaoDich.tinhThanhTien() + "\n");
    searchResultTextArea.append("Thông tin khác(Địa chỉ nhà): " + giaoDich.getThongTinKhac() + "\n");
}

    private void clearSearchResultInfo() {
        // Xóa thông tin kết quả tìm kiếm trong JTextArea
        searchResultTextArea.setText("");
    }
}