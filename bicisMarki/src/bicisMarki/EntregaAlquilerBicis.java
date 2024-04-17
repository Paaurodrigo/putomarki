package bicisMarki;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.Component;
import javax.swing.Box;
import java.awt.ScrollPane;
import java.awt.Color;
import java.awt.Panel;

public class EntregaAlquilerBicis {

	private JFrame frame;
	private JScrollPane scrollPane;
	private JTextField textFieldNombre;
	private JTextField textFieldEdad;
	private JTextField textFieldCuenta;
	private JTextField textFieldBici;
	private JTextField textFieldUsua;
	private JTextField textFieldDevolverBici;
	private JTextField textFieldDevolverUsu;
	private JTextField txtVal;
	private JTextField txtNomEditUsu;
	private JTextField txtEdadEditUsu;
	private JTextField txtCuenEditUsu;
	private JTextField txtIdUsuEdit;
	private JTextField txtEditBici;
	private JTextField txtidBici;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EntregaAlquilerBicis window = new EntregaAlquilerBicis();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	boolean comprobarExpReg(String nombre, String er) {
		Pattern pat = Pattern.compile(er);
		Matcher mat = pat.matcher(nombre);
		if (mat.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Create the application.
	 */
	public EntregaAlquilerBicis() {
		initialize();
	}

	public void limpiarUsuario() {
		textFieldCuenta.setText("");
		textFieldNombre.setText("");
		textFieldEdad.setText("");

	}

	public static void mostrarUsuario(DefaultTableModel modelUsuario) {
		if (modelUsuario.getRowCount() > 0) {
			modelUsuario.setRowCount(0);
		}
		try {
			Connection con = ConnectionSingleton.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario");
			while (rs.next()) {
				Object[] row = new Object[5];
				row[0] = rs.getInt("idUsuario");
				row[1] = rs.getString("nombre");
				row[2] = rs.getInt("edad");
				row[3] = rs.getString("cuentaBancaria");
				row[4] = rs.getInt("codBici");
				modelUsuario.addRow(row);
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException ex) { // Caso erróneo
			JOptionPane.showMessageDialog(null, "No se a podido cargar los datos/n" + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public static void mostrarBici(DefaultTableModel modelBici) {
		if (modelBici.getRowCount() > 0) {
			modelBici.setRowCount(0);
		}
		try {
			Connection con = ConnectionSingleton.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Bici where idBici>=1");
			while (rs.next()) {
				Object[] row = new Object[4];
				row[0] = rs.getInt("idBici");
				row[1] = rs.getInt("valoracion");
				row[2] = rs.getString("alquilada");
				row[3] = rs.getString("horaInicio");

				modelBici.addRow(row);
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException ex) { // Caso erróneo
			JOptionPane.showMessageDialog(null, "No se a podido cargar los datos/n" + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(50, 50, 1200, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblAlquilerDeBicis = new JLabel("Alquiler De Bicis");
		lblAlquilerDeBicis.setBounds(521, 6, 200, 39);
		lblAlquilerDeBicis.setFont(new Font("FreeSerif", Font.BOLD, 17));
		frame.getContentPane().add(lblAlquilerDeBicis);

		JLabel lblSegundos = new JLabel("");
		lblSegundos.setBounds(31, 365, 235, 75);
		frame.getContentPane().add(lblSegundos);

		DefaultTableModel modelUsuario = new DefaultTableModel();

		modelUsuario.addColumn("ID Usuario");
		modelUsuario.addColumn("Nombre");
		modelUsuario.addColumn("Edad");
		modelUsuario.addColumn("Cuenta Bancaria");
		modelUsuario.addColumn("Id bici");

		DefaultTableModel modelBici = new DefaultTableModel();
		JComboBox comboBoxUsuario = new JComboBox();
		comboBoxUsuario.setBounds(861, 413, 105, 27);
		frame.getContentPane().add(comboBoxUsuario);
		modelBici.addColumn("ID Bici");
		modelBici.addColumn("Valoracion");
		modelBici.addColumn("Alquilada");
		modelBici.addColumn("Hora Inicio");

		txtVal = new JTextField();
		txtVal.setEditable(false);
		txtVal.setBounds(521, 326, 130, 26);
		frame.getContentPane().add(txtVal);
		txtVal.setColumns(10);
		JCheckBox chckbxValoracion = new JCheckBox("Quieres valorar la bici?");
		chckbxValoracion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (chckbxValoracion.isSelected()) {
					txtVal.setEditable(true);
				} else {
					txtVal.setEditable(false);
				}

			}
		});

		chckbxValoracion.setBounds(493, 291, 174, 23);
		frame.getContentPane().add(chckbxValoracion);

		textFieldDevolverBici = new JTextField();
		textFieldDevolverBici.setEditable(false);
		textFieldDevolverBici.setBounds(441, 253, 130, 26);
		frame.getContentPane().add(textFieldDevolverBici);
		textFieldDevolverBici.setColumns(10);

		textFieldDevolverUsu = new JTextField();
		textFieldDevolverUsu.setEditable(false);
		textFieldDevolverUsu.setBounds(612, 253, 130, 26);
		frame.getContentPane().add(textFieldDevolverUsu);
		textFieldDevolverUsu.setColumns(10);

		textFieldBici = new JTextField();
		textFieldBici.setEditable(false);
		textFieldBici.setBounds(387, 119, 114, 26);
		frame.getContentPane().add(textFieldBici);
		textFieldBici.setColumns(10);

		textFieldUsua = new JTextField();
		textFieldUsua.setEditable(false);
		textFieldUsua.setBounds(713, 119, 130, 26);
		frame.getContentPane().add(textFieldUsua);
		textFieldUsua.setColumns(10);

		txtNomEditUsu = new JTextField();
		txtNomEditUsu.setBounds(836, 533, 130, 26);
		frame.getContentPane().add(txtNomEditUsu);
		txtNomEditUsu.setColumns(10);
		
		txtEditBici = new JTextField();
		txtEditBici.setBounds(188, 536, 114, 19);
		frame.getContentPane().add(txtEditBici);
		txtEditBici.setColumns(10);
		
		txtidBici = new JTextField();
		txtidBici.setEditable(false);
		txtidBici.setColumns(10);
		txtidBici.setBounds(188, 460, 130, 26);
		frame.getContentPane().add(txtidBici);

		txtEdadEditUsu = new JTextField();
		txtEdadEditUsu.setBounds(836, 571, 130, 26);
		frame.getContentPane().add(txtEdadEditUsu);
		txtEdadEditUsu.setColumns(10);

		txtIdUsuEdit = new JTextField();
		txtIdUsuEdit.setEditable(false);
		txtIdUsuEdit.setBounds(836, 495, 130, 26);
		frame.getContentPane().add(txtIdUsuEdit);
		txtIdUsuEdit.setColumns(10);

		txtCuenEditUsu = new JTextField();
		txtCuenEditUsu.setBounds(836, 609, 130, 26);
		frame.getContentPane().add(txtCuenEditUsu);
		txtCuenEditUsu.setColumns(10);
		mostrarUsuario(modelUsuario);
		JTable table = new JTable(modelUsuario);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int index = table.getSelectedRow();

				TableModel modelUsuario = table.getModel();

				textFieldUsua.setText(modelUsuario.getValueAt(index, 0).toString());
				textFieldDevolverUsu.setText(modelUsuario.getValueAt(index, 0).toString());
				textFieldDevolverBici.setText(modelUsuario.getValueAt(index, 4).toString());
				txtIdUsuEdit.setText(modelUsuario.getValueAt(index, 0).toString());
				txtNomEditUsu.setText(modelUsuario.getValueAt(index, 1).toString());
				txtEdadEditUsu.setText(modelUsuario.getValueAt(index, 2).toString());
				txtCuenEditUsu.setText(modelUsuario.getValueAt(index, 3).toString());
			}
		});
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(881, 38, 295, 184);
		frame.getContentPane().add(scrollPane);

		try {
			Connection con = ConnectionSingleton.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Bici where idBici>=1");
			while (rs.next()) {
				Object[] row = new Object[3];
				row[0] = rs.getInt("idBici");
				row[1] = rs.getInt("valoracion");
				row[2] = rs.getString("alquilada");

				modelBici.addRow(row);

			}
			JTable table2 = new JTable(modelBici);
			table2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int index = table2.getSelectedRow();

					TableModel modelBici = table2.getModel();

					textFieldBici.setText(modelBici.getValueAt(index, 0).toString());
					
					txtidBici.setText(modelBici.getValueAt(index, 0).toString());

				}
			});
			table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

			JScrollPane scrollPane2 = new JScrollPane(table2);

			scrollPane2.setBounds(47, 38, 306, 184);

			frame.getContentPane().add(scrollPane2);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		JComboBox comboBoxBicis = new JComboBox();
		comboBoxBicis.setBounds(57, 285, 113, 27);
		frame.getContentPane().add(comboBoxBicis);
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(891, 242, 114, 19);
		frame.getContentPane().add(textFieldNombre);
		textFieldNombre.setColumns(10);

		textFieldEdad = new JTextField();
		textFieldEdad.setBounds(891, 273, 114, 19);
		frame.getContentPane().add(textFieldEdad);
		textFieldEdad.setColumns(10);

		textFieldCuenta = new JTextField();
		textFieldCuenta.setBounds(891, 304, 114, 19);
		frame.getContentPane().add(textFieldCuenta);
		textFieldCuenta.setColumns(10);

		JButton btnAadirBici = new JButton("añadir bici");
		btnAadirBici.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement ins_pstmt = con
							.prepareStatement("insert into Bici (valoracion, alquilada) VALUES (?,?)");
					ins_pstmt.setInt(1, 5); // Primer “?”
					ins_pstmt.setString(2, ""); // Segundo “?”
					ins_pstmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Bici añadida");

					mostrarBici(modelBici);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				comboBoxBicis.removeAllItems();

				try {
					Connection con = ConnectionSingleton.getConnection();
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM Bici");
					while (rs.next()) {
						if (!"Alquilada".equals(rs.getString("alquilada"))) {

							comboBoxBicis.addItem(rs.getInt("idBici"));
						}

					}
					rs.close();
					stmt.close();
					con.close();

				} catch (SQLException ex) { // Caso erróneo
					JOptionPane.showMessageDialog(null, "No se a podido cargar los datos" + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnAadirBici.setBounds(130, 234, 117, 25);
		frame.getContentPane().add(btnAadirBici);

		JButton btnAadirUsuario = new JButton("añadir usuario");
		btnAadirUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (textFieldNombre.getText() == null || textFieldNombre.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Falta el nombre", "ERROR", JOptionPane.ERROR_MESSAGE);

				} else if (textFieldEdad.getText() == null || textFieldEdad.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Falta la edad", "ERROR", JOptionPane.ERROR_MESSAGE);

				} else if (textFieldCuenta.getText() == null || textFieldCuenta.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Falta la cuenta bancaria", "ERROR", JOptionPane.ERROR_MESSAGE);

				} else if (!comprobarExpReg(textFieldCuenta.getText(),
						"^ES\\d{2}\\s\\d{4}\\s\\d{4}\\s\\d{2}\\s\\d{10}$")) {
					JOptionPane.showMessageDialog(null,
							" Número de Cuenta Bancaria erroneo.\nEl formato correcto es ES00 0000 0000 00 0000000000",
							"Cuenta bancaria mal introducida", JOptionPane.ERROR_MESSAGE);

				} else {

					try {
						Connection con = ConnectionSingleton.getConnection();
						PreparedStatement ins_pstmt = con.prepareStatement(
								"insert into Usuario (nombre, edad,cuentaBancaria,codBici) VALUES (?,?,?,?)");
						ins_pstmt.setString(1, textFieldNombre.getText()); // Primer “?”
						ins_pstmt.setString(2, textFieldEdad.getText()); // Segundo “?”
						ins_pstmt.setString(3, textFieldCuenta.getText()); // Tercer “?”
						ins_pstmt.setInt(4, 0); // cuarto “?”
						ins_pstmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "Usuario añadido");
						limpiarUsuario();
						mostrarUsuario(modelUsuario);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					comboBoxUsuario.removeAllItems();
					try {
						Connection con = ConnectionSingleton.getConnection();
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario");

						while (rs.next()) {
							if ((int) rs.getInt("codBici") == 0) {
								comboBoxUsuario.addItem(rs.getInt("idUsuario"));
							}

						}
						rs.close();
						stmt.close();
						con.close();

					} catch (SQLException ex) { // Caso erróneo
						JOptionPane.showMessageDialog(null, "No se a podido cargar los datos/n" + ex.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnAadirUsuario.setBounds(1023, 272, 153, 53);
		frame.getContentPane().add(btnAadirUsuario);

		comboBoxBicis.removeAllItems();

		try {
			Connection con = ConnectionSingleton.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Bici");
			while (rs.next()) {
				if (!"Alquilada".equals(rs.getString("alquilada"))) {

					comboBoxBicis.addItem(rs.getInt("idBici"));
				}

			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException ex) { // Caso erróneo
			JOptionPane.showMessageDialog(null, "No se a podido cargar los datos" + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		JButton btnBorrarBici = new JButton("Borrar Bici");
		btnBorrarBici.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int cod = (int) comboBoxBicis.getSelectedItem();

				if (cod == 0) {
					JOptionPane.showMessageDialog(null, "No se puede eliminar ya que la 0 no es una bici");
				} else {

					try {
						Connection con = ConnectionSingleton.getConnection();
						PreparedStatement ins_pstmt = con.prepareStatement("delete from Bici where idBici=?");
						ins_pstmt.setInt(1, cod);
						ins_pstmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "Bici borrada correctamente");

						mostrarBici(modelBici);

					} catch (SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "No se puede eliminar una bici que esta alquilada");
					}
					try {
						Connection con = ConnectionSingleton.getConnection();
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery("SELECT * FROM Bici");
						comboBoxBicis.removeAllItems();
						while (rs.next()) {
							if (!"Alquilada".equals(rs.getString("alquilada"))) {

								comboBoxBicis.addItem(rs.getInt("idBici"));
							}
						}
						rs.close();
						stmt.close();
						con.close();

					} catch (SQLException ex) { // Caso erróneo
						JOptionPane.showMessageDialog(null, "No se a podido cargar los datos/n" + ex.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnBorrarBici.setBounds(201, 284, 117, 29);
		frame.getContentPane().add(btnBorrarBici);
		comboBoxUsuario.removeAllItems();

		try {
			Connection con = ConnectionSingleton.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario");
			while (rs.next()) {
				if ((int) rs.getInt("codBici") == 0) {
					comboBoxUsuario.addItem(rs.getInt("idUsuario"));
				}

			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException ex) { // Caso erróneo
			JOptionPane.showMessageDialog(null, "No se a podido cargar los datos/n" + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		JButton btnBorrarUsuario = new JButton("Borrar usuario");
		btnBorrarUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int cod = (int) comboBoxUsuario.getSelectedItem();
				try {
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement ins_pstmt = con.prepareStatement("delete from Usuario where idUsuario=?");
					ins_pstmt.setInt(1, cod);
					ins_pstmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Usuario borrado correctamente");

					mostrarUsuario(modelUsuario);

				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "No se puede eliminar una bici que esta alquilada");
				}
				comboBoxUsuario.removeAllItems();
				try {
					Connection con = ConnectionSingleton.getConnection();
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario");
					while (rs.next()) {
						if ((int) rs.getInt("codBici") == 0) {
							comboBoxUsuario.addItem(rs.getInt("idUsuario"));
						}

					}
					rs.close();
					stmt.close();
					con.close();

				} catch (SQLException ex) { // Caso erróneo
					JOptionPane.showMessageDialog(null, "No se a podido cargar los datos/n" + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnBorrarUsuario.setBounds(1038, 412, 117, 29);
		frame.getContentPane().add(btnBorrarUsuario);

		JLabel lblNewLabel = new JLabel("Bici");
		lblNewLabel.setBounds(420, 91, 61, 16);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setBounds(730, 91, 61, 16);
		frame.getContentPane().add(lblNewLabel_1);

		JButton btnAlquilar = new JButton("Alquilar");
		btnAlquilar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (textFieldBici.getText() == null || textFieldBici.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "No has seleccionado ningun", "Error",
							JOptionPane.ERROR_MESSAGE);

				} else if (textFieldUsua.getText() == null || textFieldUsua.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "No has seleccionado ningun usuario", "Error",
							JOptionPane.ERROR_MESSAGE);

				} else

				if ("0".equals(textFieldBici.getText())) {
					JOptionPane.showMessageDialog(null, "No se puede alquilar la bici 0 ya que no es una bici");
				} else {
					// comprobar si la persona tiene ya una bici alquilada
					int codAlqBici = Integer.parseInt(textFieldBici.getText());
					int codAlqUsu = Integer.parseInt(textFieldUsua.getText());

					int idp = 0;
					int idb = 0;
					String alq = "";

					try {
						Connection con = ConnectionSingleton.getConnection();
						PreparedStatement sel_pstmt = con.prepareStatement("SELECT * FROM Usuario WHERE idUsuario=?");
						sel_pstmt.setInt(1, codAlqUsu);

						ResultSet rs_sel = sel_pstmt.executeQuery();

						while (rs_sel.next()) {
							idp = rs_sel.getInt("idUsuario");
							idb = rs_sel.getInt("codBici");

						}

						rs_sel.close();
						sel_pstmt.close();

					} catch (SQLException e1) {
						e1.printStackTrace();

					}
					if (idb != 0) {
						JOptionPane.showMessageDialog(null, "El usuario ya ha alquilado una bici");

					} else {

						try {
							Connection con = ConnectionSingleton.getConnection();
							PreparedStatement sel_pstmt = con.prepareStatement("SELECT * FROM Bici WHERE idBici=?");
							sel_pstmt.setInt(1, codAlqBici);

							ResultSet rs_sel = sel_pstmt.executeQuery();

							while (rs_sel.next()) {
								alq = rs_sel.getString("alquilada");

							}

							rs_sel.close();
							sel_pstmt.close();

						} catch (SQLException e1) {
							e1.printStackTrace();

						}

						if ("Alquilada".equals(alq)) {
							JOptionPane.showMessageDialog(null, "La bici ya esta alquilada");
						} else {
							try {
								Connection con = ConnectionSingleton.getConnection();
								PreparedStatement ins_pstmt = con
										.prepareStatement("update Usuario set codBici=? where idUsuario=?");
								ins_pstmt.setInt(1, codAlqBici);
								ins_pstmt.setInt(2, codAlqUsu);
								ins_pstmt.executeUpdate();

								mostrarUsuario(modelUsuario);

							} catch (SQLException e1) {
								e1.printStackTrace();

							}
							LocalDateTime FechaAlq = LocalDateTime.now(); // Fecha y hora actuales

							try {
								Connection con = ConnectionSingleton.getConnection();
								PreparedStatement ins_pstmt = con
										.prepareStatement("update Bici set alquilada=?, horaInicio=? where idBici=?");
								ins_pstmt.setString(1, "Alquilada");
								ins_pstmt.setString(2, FechaAlq.toString());
								ins_pstmt.setInt(3, codAlqBici);
								ins_pstmt.executeUpdate();

								JOptionPane.showMessageDialog(null, "Bici alquilada correctamente");

								mostrarBici(modelBici);

							} catch (SQLException e1) {
								e1.printStackTrace();
								JOptionPane.showMessageDialog(null, "No se puede eliminar una bici que esta alquilada");
							}

							// HORAS

							comboBoxUsuario.removeAllItems();
							try {
								Connection con = ConnectionSingleton.getConnection();
								Statement stmt = con.createStatement();
								ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario");
								while (rs.next()) {
									if ((int) rs.getInt("codBici") == 0) {
										comboBoxUsuario.addItem(rs.getInt("idUsuario"));
									}

								}
								rs.close();
								stmt.close();
								con.close();

							} catch (SQLException ex) { // Caso erróneo
								JOptionPane.showMessageDialog(null,
										"No se a podido cargar los datos/n" + ex.getMessage(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			}
		});
		btnAlquilar.setBounds(550, 119, 117, 29);
		frame.getContentPane().add(btnAlquilar);

		JButton btnDevolver = new JButton("Devolver");
		btnDevolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (textFieldDevolverUsu.getText() == null || textFieldDevolverUsu.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "No has seleccionado ni el usuario ni la bici para devolverla",
							"Error", JOptionPane.ERROR_MESSAGE);
				} else {
					
					int codAlqUsu = Integer.parseInt(textFieldDevolverUsu.getText());
					int codAlqBic = Integer.parseInt(textFieldDevolverBici.getText());
					String fechaInicioS = "0";

					if (codAlqBic != 0) {
						if (!chckbxValoracion.isSelected()) {

							try {
								Connection con = ConnectionSingleton.getConnection();
								PreparedStatement sel_pstmt = con.prepareStatement("SELECT * FROM Bici WHERE idBici=?");
								sel_pstmt.setInt(1, codAlqBic);

								ResultSet rs_sel = sel_pstmt.executeQuery();

								while (rs_sel.next()) {

									fechaInicioS = rs_sel.getString("horaInicio");
								}

								rs_sel.close();
								sel_pstmt.close();

							} catch (SQLException e1) {
								e1.printStackTrace();

							}

							try {
								Connection con = ConnectionSingleton.getConnection();
								PreparedStatement ins_pstmt = con
										.prepareStatement("update Bici set alquilada=?,horaInicio=? where idBici=?");

								ins_pstmt.setString(1, "");
								ins_pstmt.setString(2, "");
								ins_pstmt.setInt(3, codAlqBic);
								ins_pstmt.executeUpdate();

								mostrarBici(modelBici);

							} catch (SQLException e1) {
								e1.printStackTrace();

							}

							try {
								Connection con = ConnectionSingleton.getConnection();
								PreparedStatement ins_pstmt = con
										.prepareStatement("update Usuario set codBici=? where idUsuario=?");

								ins_pstmt.setInt(1, 0);
								ins_pstmt.setInt(2, codAlqUsu);
								ins_pstmt.executeUpdate();
								JOptionPane.showMessageDialog(null, "Bici devuelta correctamente");

								mostrarUsuario(modelUsuario);

							} catch (SQLException e1) {
								e1.printStackTrace();

							}
							// act lista

							comboBoxUsuario.removeAllItems();
							try {
								Connection con = ConnectionSingleton.getConnection();
								Statement stmt = con.createStatement();
								ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario");
								while (rs.next()) {
									if ((int) rs.getInt("codBici") == 0) {
										comboBoxUsuario.addItem(rs.getInt("idUsuario"));
									}

								}
								rs.close();
								stmt.close();
								con.close();

							} catch (SQLException ex) { // Caso erróneo
								JOptionPane.showMessageDialog(null,
										"No se a podido cargar los datos/n" + ex.getMessage(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}

						} else {

							if (txtVal.getText() == null || txtVal.getText().isEmpty()) {

								JOptionPane.showMessageDialog(null, "Debes poner la valoracion", "Error",
										JOptionPane.ERROR_MESSAGE);

							} else if (Integer.parseInt(txtVal.getText()) < 0 || Integer.parseInt(txtVal.getText()) > 5) {
								
								JOptionPane.showMessageDialog(null, "La valoracion debe ser entre 0-5", "Error",JOptionPane.ERROR_MESSAGE);

							} else {

								int val = Integer.parseInt(txtVal.getText());

								try {
									Connection con = ConnectionSingleton.getConnection();
									PreparedStatement sel_pstmt = con
											.prepareStatement("SELECT * FROM Bici WHERE idBici=?");
									sel_pstmt.setInt(1, codAlqBic);

									ResultSet rs_sel = sel_pstmt.executeQuery();

									while (rs_sel.next()) {

										fechaInicioS = rs_sel.getString("horaInicio");
									}

									rs_sel.close();
									sel_pstmt.close();

								} catch (SQLException e1) {
									e1.printStackTrace();

								}
								try {
									Connection con = ConnectionSingleton.getConnection();
									PreparedStatement ins_pstmt = con.prepareStatement(
											"update Bici set alquilada=?, horaInicio=?,valoracion=? where idBici=?");
									ins_pstmt.setString(1, "");
									ins_pstmt.setString(2, "");
									ins_pstmt.setInt(3, val);
									ins_pstmt.setInt(4, codAlqBic);

									ins_pstmt.executeUpdate();

									

									mostrarBici(modelBici);

								} catch (SQLException e1) {
									e1.printStackTrace();
									JOptionPane.showMessageDialog(null,
											"No se puede eliminar una bici que esta alquilada");
								}

								try {
									Connection con = ConnectionSingleton.getConnection();
									PreparedStatement ins_pstmt = con
											.prepareStatement("update Usuario set codBici=? where idUsuario=?");
									ins_pstmt.setInt(1, 0);
									ins_pstmt.setInt(2, codAlqUsu);

									ins_pstmt.executeUpdate();

									JOptionPane.showMessageDialog(null, "Bici devuelta correctamente");

									mostrarUsuario(modelUsuario);

								} catch (SQLException e1) {
									e1.printStackTrace();
									JOptionPane.showMessageDialog(null,
											"No se puede eliminar una bici que esta alquilada");
								}
								comboBoxUsuario.removeAllItems();
								try {
									Connection con = ConnectionSingleton.getConnection();
									Statement stmt = con.createStatement();
									ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario");
									while (rs.next()) {
										if ((int) rs.getInt("codBici") == 0) {
											comboBoxUsuario.addItem(rs.getInt("idUsuario"));
										}

									}
									rs.close();
									stmt.close();
									con.close();

								} catch (SQLException ex) { // Caso erróneo
									JOptionPane.showMessageDialog(null,
											"No se a podido cargar los datos/n" + ex.getMessage(), "Error",
											JOptionPane.ERROR_MESSAGE);
								}
								LocalDateTime fechaInicio = LocalDateTime.parse(fechaInicioS);

								Duration duracion = Duration.between(fechaInicio, LocalDateTime.now());

								double precio = duracion.getSeconds() * 0.02;

								String textoSeg = Double.toString(precio);

								lblSegundos.setText("El precio es de " + textoSeg + " €");
								
							}
							
							
							
						}
						

					} else {
						JOptionPane.showMessageDialog(null, "Este usuario aun no ha alquilado ninguna bici");
					}
				}
			}
		});
		btnDevolver.setBounds(531, 358, 117, 29);
		frame.getContentPane().add(btnDevolver);

		JButton btnEditarUsu = new JButton("Editar Usuario");
		btnEditarUsu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String nombreUsu = txtNomEditUsu.getText();
				String edadUsu = txtEdadEditUsu.getText();
				String cuentaUsu = txtCuenEditUsu.getText();
				String idUsu = txtIdUsuEdit.getText();
				int id;
				if (nombreUsu == null || nombreUsu.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Falta el nombre", "ERROR", JOptionPane.ERROR_MESSAGE);

				} else if (edadUsu == null || edadUsu.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Falta la edad", "ERROR", JOptionPane.ERROR_MESSAGE);

				} else if (cuentaUsu == null || cuentaUsu.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Falta la cuenta bancaria", "ERROR", JOptionPane.ERROR_MESSAGE);

				} else if (!comprobarExpReg(cuentaUsu, "^ES\\d{2}\\s\\d{4}\\s\\d{4}\\s\\d{2}\\s\\d{10}$")) {
					JOptionPane.showMessageDialog(null,
							" Número de Cuenta Bancaria erroneo.\nEl formato correcto es ES00 0000 0000 00 0000000000",
							"Cuenta bancaria mal introducida", JOptionPane.ERROR_MESSAGE);

				} else
					try {
						Connection con = ConnectionSingleton.getConnection();
						PreparedStatement ins_pstmt = con.prepareStatement(
								"update Usuario set nombre=?,edad=?,cuentaBancaria=? where idUsuario=?");
						ins_pstmt.setString(1, nombreUsu);
						ins_pstmt.setString(2, edadUsu);
						ins_pstmt.setString(3, cuentaUsu);
						ins_pstmt.setInt(4, Integer.valueOf(idUsu));
						ins_pstmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "Usuario actualizado");

						mostrarUsuario(modelUsuario);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

			}
		});
		btnEditarUsu.setBounds(1038, 553, 117, 29);
		frame.getContentPane().add(btnEditarUsu);

		JLabel lblNewLabel_2 = new JLabel("DEVOLVER BICI");
		lblNewLabel_2.setBounds(550, 225, 105, 16);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("ALQUILAR BICI");
		lblNewLabel_3.setBounds(550, 70, 98, 16);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Nombre:");
		lblNewLabel_4.setBounds(818, 244, 61, 16);
		frame.getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Edad:");
		lblNewLabel_5.setBounds(818, 275, 61, 16);
		frame.getContentPane().add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Cuenta Bancaria:");
		lblNewLabel_6.setBounds(765, 306, 114, 16);
		frame.getContentPane().add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("BORRAR USUARIO");
		lblNewLabel_7.setBounds(914, 363, 137, 16);
		frame.getContentPane().add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("EDITAR USUARIO");
		lblNewLabel_8.setBounds(936, 452, 115, 16);
		frame.getContentPane().add(lblNewLabel_8);

		JLabel lblNewLabel_4_1 = new JLabel("Nombre:");
		lblNewLabel_4_1.setBounds(765, 538, 61, 16);
		frame.getContentPane().add(lblNewLabel_4_1);

		JLabel lblNewLabel_5_1 = new JLabel("Edad:");
		lblNewLabel_5_1.setBounds(765, 576, 61, 16);
		frame.getContentPane().add(lblNewLabel_5_1);

		JLabel lblNewLabel_6_1 = new JLabel("Cuenta Bancaria:");
		lblNewLabel_6_1.setBounds(713, 614, 114, 16);
		frame.getContentPane().add(lblNewLabel_6_1);

		JLabel lblNewLabel_9 = new JLabel("ID:");
		lblNewLabel_9.setBounds(763, 500, 61, 16);
		frame.getContentPane().add(lblNewLabel_9);
		
		
		JButton buttonEditar = new JButton("Editar Bici");
		buttonEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String valBic = txtEditBici.getText();
				String idBic = txtidBici.getText();
				int id;
				if (valBic == null || valBic.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Falta la valoracion", "ERROR", JOptionPane.ERROR_MESSAGE);

				} else if (idBic == null || idBic.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Falta el id", "ERROR", JOptionPane.ERROR_MESSAGE);

				

				} else
					try {
						Connection con = ConnectionSingleton.getConnection();
						PreparedStatement ins_pstmt = con.prepareStatement(
								"update Bici set valoracion=? where idBici=?");
						ins_pstmt.setInt(1, Integer.valueOf(valBic));
						ins_pstmt.setInt(2, Integer.valueOf(idBic));
						ins_pstmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "Usuario actualizado");

						mostrarBici(modelBici);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				
				
			}
		});
		buttonEditar.setBounds(355, 500, 117, 25);
		frame.getContentPane().add(buttonEditar);
		
		
		
		JLabel lblNewLabel_9_1 = new JLabel("ID:");
		lblNewLabel_9_1.setBounds(109, 470, 61, 16);
		frame.getContentPane().add(lblNewLabel_9_1);

	}
}
