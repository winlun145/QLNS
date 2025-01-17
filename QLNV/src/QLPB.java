	import java.awt.BorderLayout;
	import java.awt.EventQueue;
	
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.border.EmptyBorder;
	import javax.swing.event.ListSelectionEvent;
	import javax.swing.event.ListSelectionListener;
	import javax.swing.JTextField;
	import java.awt.Font;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.AbstractButton;
	import javax.swing.JButton;
	import javax.swing.JScrollPane;
	import javax.swing.JTable;
	import javax.swing.table.DefaultTableModel;
	import java.awt.event.ActionListener;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.ResultSetMetaData;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.Vector;
	import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
	
	public class QLPB extends JFrame {
	
		private JPanel contentPane;
		private JTextField txtTK;
		private static JTextField txtTenPB;
		private static JTable tablePB;
		private static JTextField txtNgayTL;
		private static JTextField txtSoNV;
		private static JTextField txtID;	
		/**
		 * Launch the application.
		 */
		public static void main(String[] args){
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						QLPB frame = new QLPB();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			LoadData();
		}
	
		/**
		 * Create the frame.
		 */
		static DefaultTableModel tbn = new DefaultTableModel();
		private static JComboBox cbDA;
		public static void LoadData(){
			try {
			int number;
			Connect com = new Connect();
			Connection conn = com.getConnection();
			Vector column,row;
			column = new Vector();
			row = new Vector();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from QLPB");
			Statement stda = conn.createStatement();
			ResultSet rsda = stda.executeQuery("select TenDA from QLDA");
			ResultSetMetaData metadata = rs.getMetaData();
			number = metadata.getColumnCount();
			for(int i = 1; i <=number; i++) {
				column.add(metadata.getColumnName(i));
			}
			tbn.setColumnIdentifiers(column);
			while(rs.next()) {
				row = new Vector();
				for(int i = 1; i<=number;i++) {
					row.addElement(rs.getString(i));
				}
				tbn.addRow(row);
				tablePB.setModel(tbn);
			}
			while(rsda.next()) {
				System.out.println(rsda.getString("TenDA"));
				String name = rsda.getString("TenDA");
				cbDA.addItem(name);
				}
			tablePB.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if(tablePB.getSelectedRow() >= 0) {
						txtID.setText(tablePB.getValueAt(tablePB.getSelectedRow(), 0)+"");
						txtTenPB.setText(tablePB.getValueAt(tablePB.getSelectedRow(), 1)+"");
						txtNgayTL.setText(tablePB.getValueAt(tablePB.getSelectedRow(), 2)+"");
						txtSoNV.setText(tablePB.getValueAt(tablePB.getSelectedRow(), 3)+"");
						cbDA.setSelectedItem(tablePB.getValueAt(tablePB.getSelectedRow() ,4));
					}
				}
			});
			} 
			catch(Exception ex) {
				System.out.println(ex.toString());
			}
		}
		public QLPB() {
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 735, 621);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			txtTK = new JTextField();
			txtTK.setFont(new Font("Tahoma", Font.PLAIN, 13));
			txtTK.setBounds(123, 119, 330, 34);
			contentPane.add(txtTK);
			txtTK.setColumns(10);
			
			JLabel lblNewLabel = new JLabel("Qu\u1EA3n L\u00FD Ph\u00F2ng Ban");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblNewLabel.setBounds(247, 28, 214, 31);
			contentPane.add(lblNewLabel);
			
			txtTenPB = new JTextField();
			txtTenPB.setBounds(216, 219, 171, 34);
			contentPane.add(txtTenPB);
			txtTenPB.setColumns(10);
			
			JButton btnTK = new JButton("T\u00ECm Ki\u1EBFm");
			btnTK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Connect com = new Connect();
						Connection conn = com.getConnection();
						PreparedStatement ps1 = conn.prepareStatement("select *from QLPB where TenPB=?");
						ps1.setString(1, txtTK.getText());
						ResultSet tk = ps1.executeQuery();
						if(tk.next()){
							JOptionPane.showMessageDialog(rootPane, "Tìm thành công");
							txtTK.removeAll();
							for(int i = 0; i < tablePB.getRowCount();i++) {
								if(tablePB.getValueAt(i, 1).toString().toLowerCase().contains(txtTK.getText().toLowerCase())) {
									txtID.setText(tablePB.getValueAt(i, 0).toString());
									txtTenPB.setText(tablePB.getValueAt(i, 1).toString());
									txtNgayTL.setText(tablePB.getValueAt(i, 2).toString());
									txtSoNV.setText(tablePB.getValueAt(i, 3).toString());
									if(tablePB.getValueAt(i, 4) == null) {
										//cbDA.setSelectedItem("sdf".toString());
										cbDA.setSelectedIndex(0);
									} else {
										cbDA.setSelectedItem(tablePB.getValueAt(i, 4).toString());
									}
									
								}
							}
						}
						else {
							JOptionPane.showMessageDialog(rootPane, "Không tìm được");
						}
					}
					catch(SQLException ex){
						JOptionPane.showMessageDialog(rootPane, "Lỗi!");
					}
					txtTK.setText("");
				}
				
			});
			btnTK.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnTK.setBounds(463, 118, 159, 35);
			contentPane.add(btnTK);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(102, 437, 550, 134);
			contentPane.add(scrollPane);
			
			tablePB = new JTable();
			tablePB.setRowSelectionAllowed(false);
			tablePB.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null},
					{null, null, null, null, null},
				},
				new String[] {
					"M\u00E3 Ph\u00F2ng Ban", "T\u00EAn Ph\u00F2ng Ban", "Ng\u00E0y Th\u00E0nh L\u1EADp", "S\u1ED1 L\u01B0\u1EE3ng Nh\u00E2n Vi\u00EAn", "D\u1EF1 \u00C1n \u0110ang Nh\u1EADn"
				}
			));
			tablePB.setFont(new Font("Tahoma", Font.PLAIN, 13));
			scrollPane.setViewportView(tablePB);
			
			JLabel lblNewLabel_1 = new JLabel("T\u00EAn Ph\u00F2ng Ban");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewLabel_1.setBounds(93, 217, 113, 36);
			contentPane.add(lblNewLabel_1);
			
			JLabel lblNewLabel_1_1 = new JLabel("S\u1ED1 Nh\u00E2n Vi\u00EAn");
			lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewLabel_1_1.setBounds(400, 219, 113, 34);
			contentPane.add(lblNewLabel_1_1);
			
			JLabel lblNewLabel_1_2 = new JLabel("Ng\u00E0y Th\u00E0nh L\u1EADp");
			lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_1_2.setBounds(95, 291, 120, 36);
			contentPane.add(lblNewLabel_1_2);
			
			JLabel lblNewLabel_1_3 = new JLabel("D\u1EF1 \u00C1n");
			lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewLabel_1_3.setBounds(400, 291, 80, 36);
			contentPane.add(lblNewLabel_1_3);
			
			txtNgayTL = new JTextField();
			txtNgayTL.setColumns(10);
			txtNgayTL.setBounds(216, 291, 171, 39);
			contentPane.add(txtNgayTL);
			
			txtSoNV = new JTextField();
			txtSoNV.setColumns(10);
			txtSoNV.setBounds(490, 219, 162, 34);
			contentPane.add(txtSoNV);
			
			JButton btnThem = new JButton("Th\u00EAm");
			btnThem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Connect com = new Connect();
						Connection conn = com.getConnection();
						PreparedStatement ps = conn.prepareStatement("insert into QLPB values(?,?,?,?,?)");
						ps.setString(1, txtID.getText());
						ps.setString(2, txtTenPB.getText());
						ps.setString(3, txtNgayTL.getText());
						ps.setString(4, txtSoNV.getText());
						if(cbDA.getSelectedItem() == null) {
							ps.setString(5, null);
						}else {
							ps.setString(5, cbDA.getSelectedItem().toString());	
						}
						
						int themtc = ps.executeUpdate();
						if(themtc > 0) {
							JOptionPane.showMessageDialog(rootPane, "Thêm Thành Công");
							tbn.setRowCount(0);
							LoadData();
						}
					}
					catch(SQLException ex) {
						JOptionPane.showMessageDialog(rootPane, "Không Thêm Được");
					}
					txtID.setText("");
					txtTenPB.setText("");
					txtNgayTL.setText("");
					txtSoNV.setText("");
				}
			});
			btnThem.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnThem.setBounds(102, 374, 125, 39);
			contentPane.add(btnThem);
			JButton btnCP = new JButton("Sửa");
			btnCP.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Connect com = new Connect();
						Connection conn = com.getConnection();
						PreparedStatement ps2 = conn.prepareStatement("update QLPB set TenPB=?, NgayTL=?, SLNV=?, DuAn=? where MaPB=?");
						Statement st = conn.createStatement();
						ResultSet rs = st.executeQuery("Select MaDA from QLDA where TenDA ='"+cbDA.getSelectedItem().toString()+"'");
						ps2.setString(5, txtID.getText());
						ps2.setString(1, txtTenPB.getText());
						ps2.setString(2, txtNgayTL.getText());
						ps2.setString(3, txtSoNV.getText());
						ps2.setString(4, cbDA.getSelectedItem().toString());
						int suatc = ps2.executeUpdate();
						if(suatc >0) {
							JOptionPane.showMessageDialog(rootPane, "Sửa Thành Công");
							tbn.setRowCount(0);
							LoadData();			
						}
					}
					catch(SQLException ex) {
						System.out.println(ex);
						JOptionPane.showMessageDialog(rootPane, "Không sửa được!");
					}
				}
			});
			btnCP.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnCP.setBounds(304, 374, 120, 39);
			contentPane.add(btnCP);
			
			JButton btnXoa = new JButton("X\u00F3a");
			btnXoa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Connect com = new Connect();
						Connection conn = com.getConnection();
						PreparedStatement ps3 = conn.prepareStatement("delete QLPB where MaPB=?");
						ps3.setString(1, txtID.getText());
						if(JOptionPane.showConfirmDialog(rootPane, "Xác Nhận Xóa?","Đồng Ý",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							int xoatc = ps3.executeUpdate();
							tbn.setRowCount(0);
							LoadData();
						}
					}
					catch(Exception ex) {
						System.out.println(ex.toString());
					}
				}
			});
			btnXoa.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnXoa.setBounds(497, 374, 125, 39);
			contentPane.add(btnXoa);
			
			txtID = new JTextField();
			txtID.setBounds(333, 176, 105, 33);
			contentPane.add(txtID);
			txtID.setColumns(10);
			
			JLabel ID = new JLabel("Mã Phòng Ban");
			ID.setFont(new Font("Tahoma", Font.PLAIN, 14));
			ID.setBounds(216, 177, 107, 32);
			contentPane.add(ID);
			
			cbDA = new JComboBox();
			cbDA.setBounds(479, 291, 173, 39);
			contentPane.add(cbDA);
		}
	}
