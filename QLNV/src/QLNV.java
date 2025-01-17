import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;

public class QLNV extends JFrame {

	private JPanel contentPane;
	private static JTextField txtMaNV;
	private static JTextField txtTenNV;
	private static JTextField txtDiaChi;
	private static JTextField txtSDT;
	private static JTable table;
	private static JCheckBox checkPC;
	private static JComboBox cbPB;
	private static JRadioButton radNam;
	private static JRadioButton radNu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QLNV frame = new QLNV();
					frame.setVisible(true);
					loadData();//goi ham load du lieu
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	static DefaultTableModel tbn = new DefaultTableModel(); // khai bao model cho table
	private static JTextField txtMaPB;
	private static JComboBox cbPB_1;
	private static JRadioButton radNam_1;
	private static JRadioButton radNu_1;
	private JTextField txtTK;
	public static void loadData() {
		try {
			Connect a = new Connect();//lay ket noi toi co du lieu
			Connection conn = a.getConnection();
			int number;
			Vector row,column;//khai bao thu vien vector
			row = new Vector(); // khoi tạo row và column
			column = new Vector();
			Statement st = conn.createStatement(); //tao đoi tuong statement sql thuc thi câu lenh
			//ResultSet rs = st.executeQuery("Select lg.MaNV,nv.MaPB, nv.TenNV,nv.DiaChi, nv.SDT,nv.phongban,nv.GioiTinh from NhanVien nv right join login lg on nv.MaNV = lg.MaNV");
			ResultSet rs = st.executeQuery("select lg.MaNV,nv.MaPB, nv.TenNV,nv.DiaChi, nv.SDT,nv.phongban,nv.GioiTinh from NhanVien nv right join login lg on nv.MaNV = lg.MaNV");
			Statement stpb = conn.createStatement();
			ResultSet rspb = stpb.executeQuery("select TenPB from QLPB"); // cau thuc thi lenh lay toan bo du lieu tu bang
			ResultSetMetaData metadata = rs.getMetaData();//tao đoi tuong lay sieu du lieu 
			number = metadata.getColumnCount(); // tra ve so cac cot
			//System.out.println(rs.next());//tesstttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttrs.next()
			for(int i = 1;i <= number;i++){// vong lap lay tat ca cac cot cua bang
				column.add(metadata.getColumnName(i));//lay ra tieu de cua cac cot
			}
			tbn.setColumnIdentifiers(column);//đinh danh tieu đe cho các cot
			while(rs.next()) { // truong hop co so du lieu tro toi dong tiep theo
				row = new Vector();
				for(int i = 1;i<=number;i++) {
					row.addElement(rs.getString(i));//them vao hang du lieu tu vi tri i
				}
				tbn.addRow(row);//them cac gia tri vua lay vao hang 
				table.setModel(tbn); //gan gia tri của model vao bang
				
			}
			
			while(rspb.next()) {
				//Vector<String> data = new Vector<String>();
				//data.addElement(rspb.getString("TenPB"));
				System.out.println(rspb.getString("TenPB"));
				//cbPB.setModel(new DefaultComboBoxModel(data));
				String name = rspb.getString("TenPB");
				cbPB_1.addItem(name);	
			}
			
			// hien thi table len textfield
			table.getSelectionModel().addListSelectionListener( new ListSelectionListener() {//lay thay doi moi tu selection bang
				//lay lua chon khi click vao lua chon trong bang
				@Override //ghi đe
				public void valueChanged(ListSelectionEvent e) {
					// TODO Auto-generated method stub
					if(table.getSelectedRow() >=0) { //neu lua chon gia tri trong hang lon hon 0
						txtMaNV.setText(table.getValueAt(table.getSelectedRow(), 0)+ "");//gan gia tri  vao textfield cac tuong ung tu vi tri lua chon trong bang
						txtMaPB.setText(table.getValueAt(table.getSelectedRow(), 1)+ "");
						txtTenNV.setText(table.getValueAt(table.getSelectedRow(), 2)+"");
						txtDiaChi.setText(table.getValueAt(table.getSelectedRow(), 3)+"");
						txtSDT.setText(table.getValueAt(table.getSelectedRow(), 4)+"");
						cbPB_1.setSelectedItem(table.getValueAt(table.getSelectedRow(),5));
						System.out.println(table.getValueAt(table.getSelectedRow(),6).equals("Nam"));//bao khi co loi
						if(table.getValueAt(table.getSelectedRow(),6).equals("Nam") == true) {
							radNam_1.setSelected(true);
						}else {
							radNu_1.setSelected(true);
						}
					}
				}});
		}catch(Exception ex) {
			System.out.println(ex.toString());//bao khi co loi
		}
	}
	/**
	 * Create the frame.
	 */
	public QLNV() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 754, 707);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mã Nhân Viên");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(61, 126, 113, 32);
		contentPane.add(lblNewLabel);
		
		JLabel lblTenNhanVien = new JLabel("Tên Nhân Viên");
		lblTenNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTenNhanVien.setBounds(61, 184, 113, 32);
		contentPane.add(lblTenNhanVien);
		
		txtMaNV = new JTextField();
		txtMaNV.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtMaNV.setBounds(184, 127, 171, 32);
		contentPane.add(txtMaNV);
		txtMaNV.setColumns(10);
		
		txtTenNV = new JTextField();
		txtTenNV.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTenNV.setColumns(10);
		txtTenNV.setBounds(184, 183, 171, 32);
		contentPane.add(txtTenNV);
		
		JLabel lblDiaChi = new JLabel("Địa Chỉ");
		lblDiaChi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDiaChi.setBounds(392, 78, 113, 32);
		contentPane.add(lblDiaChi);
		
		JLabel lblSdt = new JLabel("SDT");
		lblSdt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSdt.setBounds(392, 127, 113, 32);
		contentPane.add(lblSdt);
		
		txtDiaChi = new JTextField();
		txtDiaChi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDiaChi.setColumns(10);
		txtDiaChi.setBounds(515, 79, 171, 32);
		contentPane.add(txtDiaChi);
		
		txtSDT = new JTextField();
		txtSDT.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSDT.setColumns(10);
		txtSDT.setBounds(515, 127, 171, 32);
		contentPane.add(txtSDT);
		
		JLabel lblBoPhan = new JLabel("Phòng Ban");
		lblBoPhan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBoPhan.setBounds(61, 237, 113, 32);
		contentPane.add(lblBoPhan);
		
		JLabel lblGioiTinh = new JLabel("Gioi Tinh");
		lblGioiTinh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGioiTinh.setBounds(392, 184, 113, 32);
		contentPane.add(lblGioiTinh);
		
		cbPB_1 = new JComboBox();
		cbPB_1.setBounds(184, 245, 128, 24);
		contentPane.add(cbPB_1);
		/*cbBoPhan.addItem("NhanSu");//them item vao combobox
		cbBoPhan.addItem("IT");
		cbBoPhan.addItem("Tester");
		cbBoPhan.addItem("VanPhong");
		cbBoPhan.addItem("them");*/
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(61, 388, 625, 177);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"MaNV", "TenNV", "DiaChi", "SDT", "BoPhan", "GioiTinh", "PhuCap"
			}
		));
		scrollPane.setViewportView(table);
		
		JButton btnThem = new JButton("Thêm");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connect a = new Connect();//lay ket noi tu csdl
					Connection conn = a.getConnection();
					//khoi tao đoi tuong prepare stament chen cau lenh insert du lieu vao bang sql
					PreparedStatement ps2 = conn.prepareStatement("select MaPB from QLPB where TenPB =  '"+cbPB_1.getSelectedItem().toString()+"'");
					ResultSet rs2 = ps2.executeQuery();
					PreparedStatement ps = conn.prepareStatement("insert into NhanVien values(?,?,?,?,?,?,?)");
					ps.setString(1, txtMaNV.getText());
					while(rs2.next()) {
						ps.setString(2, rs2.getString(1));//gan gia tri lay tu textfield vao vi tri xac dinh  trong cau lenh insert	
					}
					ps.setString(3, txtTenNV.getText());
					ps.setString(4, txtDiaChi.getText());
					ps.setString(5, txtSDT.getText());
					ps.setString(6, cbPB_1.getSelectedItem().toString());//gán giá trị từ item chọn từ combobox vao cau lenh inse
					if(radNam_1.isSelected()) {
						ps.setString(7, radNam_1.getText());// lay gia tri tu radio button	
					} else ps.setString(7, radNu_1.getText());
					/*if(checkPC.isSelected()) {//checkbox check thuc thi lenh trong if 
						ps.setString(7, "Co");
					}else ps.setString(7, "Khong");*/
					int themtc = ps.executeUpdate(); //khai bao ham truy van hoat đong khi csdl đuoc thay đoi
					if(themtc > 0) {
						JOptionPane.showMessageDialog(rootPane, "them thanh cong");//tao mot hop thoai thong bao 
						tbn.setRowCount(0);//dat vi tri hang ve 0 trc khi load lai du lieu tranh trung du lieu
						loadData();//load lai du lieu
					}
				}catch(SQLException ex) {
					if(ex.toString().equals("com.microsoft.sqlserver.jdbc.SQLServerException: The INSERT statement conflicted with the FOREIGN KEY constraint \"fk_lg\". The conflict occurred in database \"JavaBTGK\", table \"dbo.login\", column 'MaNV'.") == true) {
						JOptionPane.showMessageDialog(rootPane, "chua khoi tao user");	
					} else if(ex.toString().contains("com.microsoft.sqlserver.jdbc.SQLServerException: Violation of PRIMARY KEY constraint 'pk_NV'. Cannot insert duplicate key in object 'dbo.NhanVien'.") == true) {
						JOptionPane.showMessageDialog(rootPane, "trung ma nhan vien");
					}
					
					System.out.println(ex.toString());
					
				} 
			}
		});
		btnThem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnThem.setBounds(255, 305, 100, 44);
		contentPane.add(btnThem);
		
		JButton btnSua = new JButton("Sửa");
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connect a = new Connect(); //lay ket noi toi csdl
					Connection conn = a.getConnection();
					
					// khoi tao đoi tuong thuc thi cau lenh update csdl
					PreparedStatement ps2 = conn.prepareStatement("Update NhanVien set TenNV=?, DiaChi=?, SDT=?, phongban=? , GioiTinh=?,MaPB=?  where MaNV=?");
					Statement stpb = conn.createStatement();
					ResultSet rspb = stpb.executeQuery("select MaPB from QLPB where TenPB = '" +cbPB_1.getSelectedItem().toString()+"'");
					ps2.setString(7, txtMaNV.getText());//gan gia tri lay tu textfield vao vi tri xac đinh trong cau lenh update
					ps2.setString(1, txtTenNV.getText());
					ps2.setString(2, txtDiaChi.getText());
					ps2.setString(3, txtSDT.getText());
					ps2.setString(4, cbPB_1.getSelectedItem().toString());//gan gia tri lay tu item đuoc chon cua combobox
					if(radNam_1.isSelected()) {
						ps2.setString(5, radNam_1.getText());// lay gia tri tu radio button	
					} else ps2.setString(5, radNu_1.getText());
					while(rspb.next()) {
					ps2.setString(6, rspb.getString("MaPB"));	
					}
					/*if(checkPC.isSelected()) {
						ps2.setString(6, "Co");
					}else ps2.setString(6, "Khong");*/
					int suatc = ps2.executeUpdate();//truy van hoat đong thay đoi tu csdl
					
						tbn.setRowCount(0);//set lai so hang ve 0
						loadData();//load lai du lieu
					
				}catch (Exception ex) {
					System.out.println(ex.toString()); //bao khi co loi
				}
				}
			
		});
		btnSua.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSua.setBounds(405, 305, 100, 44);
		contentPane.add(btnSua);
		
		JButton btnXoa = new JButton("Xóa");
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connect a = new Connect();//lay ket noi toi csdl
					Connection conn = a.getConnection();
					PreparedStatement ps3 = conn.prepareStatement("delete NhanVien where MaNV=?");
					ps3.setString(1, txtMaNV.getText());//lay gia tri tu textfield manv truyen vao đieu kien cau lenh xoa
					if(JOptionPane.showConfirmDialog(rootPane, "Xac Nhan Xoa?","Confirm",
							// tao mot hop thoai xac nhan neu chon xac nhan thi cau lenh xoa đuoc thuc thi
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						int xoatc = ps3.executeUpdate();//truy van hoat dong thay doi tu csdl
						tbn.setRowCount(0);//set lai so hang ve 0
						loadData();//load lai du  lieu
					}
									
					
				}catch(Exception ex){
					System.out.println(ex.toString());// bao khi co loi
				}
			}
		});
		btnXoa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnXoa.setBounds(553, 305, 100, 44);
		contentPane.add(btnXoa);
		
		JPanel panel = new JPanel();
		panel.setBounds(515, 184, 163, 32);
		contentPane.add(panel);
		ButtonGroup bg = new ButtonGroup();
		radNam_1 = new JRadioButton("Nam");
		radNam_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bg.add(radNam_1);
		
		radNu_1 = new JRadioButton("Nu");
		radNu_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bg.add(radNu_1);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(radNam_1)
					.addGap(18)
					.addComponent(radNu_1, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(29, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(radNam_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(radNu_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		txtMaPB = new JTextField();
		txtMaPB.setEditable(false);
		txtMaPB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtMaPB.setColumns(10);
		txtMaPB.setBounds(184, 79, 171, 32);
		contentPane.add(txtMaPB);
		
		JLabel lblMaPhongBan = new JLabel("Mã Phong Ban");
		lblMaPhongBan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMaPhongBan.setBounds(61, 79, 113, 32);
		contentPane.add(lblMaPhongBan);
		
		JButton btnSearch = new JButton("Tìm Kiếm");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connect com = new Connect();
					Connection conn = com.getConnection();
					PreparedStatement ps1 = conn.prepareStatement("select * from NhanVien where TenNV=?");
					ps1.setString(1, txtTK.getText());
					ResultSet tk = ps1.executeQuery();
					if(tk.next()){
						JOptionPane.showMessageDialog(rootPane, "Tìm thành công");
						for(int i = 0; i < table.getRowCount();i++) {
							System.out.println(txtTK.getText().toLowerCase().contains(table.getValueAt(i, 2).toString().toLowerCase()));
							if(table.getValueAt(i, 2).toString().toLowerCase().contains(txtTK.getText().toLowerCase())) {
								txtMaNV.setText(table.getValueAt(i, 0).toString());
								txtMaPB.setText(table.getValueAt(i, 1).toString());
								txtTenNV.setText(table.getValueAt(i, 2).toString());
								txtDiaChi.setText(table.getValueAt(i, 3).toString());
								txtSDT.setText(table.getValueAt(i, 4).toString());
								cbPB_1.setSelectedItem(table.getValueAt(i, 5).toString());
								if(table.getValueAt(i, 6).equals("Nam")) {
									radNam_1.setSelected(true);
								}else {
									radNu_1.setSelected(true);
								}
								/*cbTenPB.setSelectedItem(table.getValueAt(i, 3).toString());
								if((tableDA.getValueAt(i, 4)).equals("Có")) {
									chbN.setSelected(true);
								}		
								else {
									chbN.setSelected(false);
								}*/
							}
						}
					}
					else {
						JOptionPane.showMessageDialog(rootPane, "Không tìm được");
					}
				}
				catch(SQLException ex){
					System.out.println(ex.toString());
					JOptionPane.showMessageDialog(rootPane, "Lỗi!");
				}
				txtTK.setText("");
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSearch.setBounds(515, 37, 139, 32);
		contentPane.add(btnSearch);
		
		txtTK = new JTextField();
		txtTK.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTK.setColumns(10);
		txtTK.setBounds(184, 37, 317, 32);
		contentPane.add(txtTK);
		
		JLabel lblNewLabel_1 = new JLabel("Quản Lý Nhân Viên");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(255, 0, 283, 32);
		contentPane.add(lblNewLabel_1);
		
		
		
		
	}
}
