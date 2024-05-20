// Java program to implement 
// a Simple Registration Form 
// using Java Swing 

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

class MainScreen extends JFrame { 
	
	private static final long serialVersionUID = 1L;

	// Components of the frame
	private static JFrame frame;
	private static Container container; 
	private static JTable table; 
	private static JLabel title;
	private static JLabel weekLabel; // showing the selected week
	
	// labels below the table
	private static JLabel firstDosesMon;
	private static JLabel firstDosesTue;
	private static JLabel firstDosesWed;
	private static JLabel firstDosesThu;
	private static JLabel firstDosesFri;
	private static JLabel sndDosesMon;
	private static JLabel sndDosesTue;
	private static JLabel sndDosesWed;
	private static JLabel sndDosesThu;
	private static JLabel sndDosesFri;		
	
	// buttons to switch the week of the table
	private static JButton lastWeek;
	private static JButton nextWeek;
	private static int weekOfTheYear;
	
	private static Map<Integer, String> weekMap= new HashMap<Integer, String>();
	private static Map<Integer, JLabel> labelMap= new HashMap<Integer, JLabel>();
	
	// 20 weeks * 5 days per week -> 100 columns
	// 28 appointments per day -> 28 rows
	private static Client[][] clientArray = new Client[28][100];
	
	// one row for first doses
	// one row for second doses
	private static int[][] labelArray = new int[2][100];
	
	private static void storeData() {
	
		try {
            // Converts the string into bytes
            var out = new ObjectOutputStream(new FileOutputStream("data.txt"));
              
            out.writeObject(clientArray);
    		out.writeObject(labelArray);
    		
    		
            System.out.println("Data is written to the file.");

            // Closes the output stream         
            out.close();
        }

        catch (Exception e) {
        	System.out.println("failed");
            e.getStackTrace();
        }
	}

	private static void retrieveData() {
		
		try {
			FileInputStream fileStream = new FileInputStream("data.txt");
	        // Creating an object input stream
	        ObjectInputStream objStream = new ObjectInputStream(fileStream);
	        
	        clientArray = (Client[][]) objStream.readObject();
	        labelArray = (int[][]) objStream.readObject();

	        objStream.close();     
	    }
	    catch (Exception e) {
	        e.getStackTrace();
	    }
	}

	private static void createFirstColumn(String[][] data) {
		
		int hour = 9;
		int minute = 00;
		String addedZero = "0";
		
		for (int row = 0; row < data.length; row++) {
					
			if (row % 4 == 0 && row != 0) {
				hour ++;
				minute = 0;
				addedZero = "0";
			}
			else if (row != 00){
				minute += 15;
				addedZero = "";
			}
			
			if (hour < 12) {
				data[row][0] = hour + ":" + minute + addedZero + " am" ;
			}
			else {
				data[row][0] = hour + ":" + minute + addedZero + " pm" ;
			}
		}	
	}
	
	private static void updateDoses(int doses, int col) {
		
		int newDoses = labelArray[doses - 1][(col - 1) + (weekOfTheYear - 15) * 5];
		newDoses++;
		labelArray[doses - 1][col - 1 + (weekOfTheYear - 15) * 5] = newDoses;
		
		if (doses == 1) {
			labelMap.get(col).setText("First Doses: " + newDoses);
		}
	}
	
	public static void addClient(Client client, int row, int column) {

		clientArray[row][column - 1 + (weekOfTheYear - 15) * 5] = client;
		clientArray[row + 14][column - 1 + (weekOfTheYear - 11) * 5] = client;
		
		updateDoses(1, column);
		updateDoses(2, column + 20);
		
		storeData();
	}
	
	public static Client[][] getClientArray(){
		Client[][] copy = Arrays.stream(clientArray).map(Client[]::clone).toArray(Client[][]::new);
		return copy;
	}
	  
	public static int getWeek() {
		return weekOfTheYear;
	}
	
	private static void createMap() {
		labelMap.put(1, firstDosesMon);
		labelMap.put(2, firstDosesTue);
		labelMap.put(3, firstDosesWed);
		labelMap.put(4, firstDosesThu);
		labelMap.put(5, firstDosesFri);
		labelMap.put(6, sndDosesMon);
		labelMap.put(7, sndDosesTue);
		labelMap.put(8, sndDosesWed);
		labelMap.put(9, sndDosesThu);
		labelMap.put(10, sndDosesFri);
		
		// four out of 20 weeks
		weekMap.put(15, "5. April - 9. April");
		weekMap.put(16, "12. April - 16. April");
		weekMap.put(17, "19. April - 23. April");
		weekMap.put(18, "26. April - 30. April");
		
		weekMap.put(19, "3. May - 7. May");
		weekMap.put(20, "10. May - 14. May");
		weekMap.put(21, "17. May - 21. May");
		weekMap.put(22, "24. May - 28. May");

		weekMap.put(23, "31. May - 4. June");
		weekMap.put(24, "7. June - 11. June");
		weekMap.put(25, "14. June - 18. June");
		weekMap.put(26, "21. June - 25. June");
		
		weekMap.put(27, "28. June - 2. July");
		weekMap.put(28, "5. July - 9. July");
		weekMap.put(29, "12. July - 16. July");
		weekMap.put(30, "19. July - 23. July");
		weekMap.put(31, "26. July - 30. July");
		
		weekMap.put(32, "2. August - 6. August");
		weekMap.put(33, "9. August - 13. August");
		weekMap.put(34, "16. August - 20. August");
	}
	
	public static void updateTable(String name, int row, int column) {
		table.getModel().setValueAt(name, row, column);
	}
	
	private static void initializeTable() {
		for (int row = 0; row < 28; row ++) {
			for (int col = 1; col < 6; col ++) {
				if (clientArray[row][col + (weekOfTheYear - 15) * 5 - 1] == null) {
					if (row > 13 && weekOfTheYear < 19) {
						table.getModel().setValueAt("No appointments available", row, col);
					}
					else if (row < 14 && weekOfTheYear > 30) {
						table.getModel().setValueAt("No appointments available", row, col);
					}
					else {
						table.getModel().setValueAt("", row, col);
					}
					
				}
				else {
					table.getModel().setValueAt(clientArray[row][col + (weekOfTheYear - 15) * 5 - 1].getName(), row, col);
				}
			}
		}
	}
	
	private static void initializeLabel() {
		
		for (int label = 1; label < 6; label ++) {
			System.out.println(label - 1 + (weekOfTheYear - 15) * 5);
			labelMap.get(label).setText("First Doses: " + labelArray[0][label - 1 + (weekOfTheYear - 15) * 5]);
		}
		
		for (int label = 6; label < 11; label ++) {
			System.out.println(label - 6 + (weekOfTheYear - 15) * 5);
			labelMap.get(label).setText("Second Doses: " + labelArray[1][label - 6 + (weekOfTheYear - 15) * 5]);
		}
	}
 
	
	public static void main(String args[]) {
		
		frame = new JFrame();
		
		Calendar cal = Calendar.getInstance();
	    weekOfTheYear = cal.get(Calendar.WEEK_OF_YEAR);
	    if (weekOfTheYear < 15 || weekOfTheYear > 34) {
	    	weekOfTheYear = 15;
	    }
		
	    //storeData();
	    retrieveData();

		frame.setTitle("Covid Vaccine Management System"); 
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE); 
		frame.setResizable(false); 
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //make it full screen

		container = frame.getContentPane(); 
		container.setLayout(null); 
		
		// get the size of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// get the size of the screen
		int scrW = (int)screenSize.getWidth();
		int scrH = (int)screenSize.getHeight();

		int hrzMid = scrW / 2;
		int colWidth = (scrW - 35) / 6;
		
		title = new JLabel("Covid Vaccine Management System", SwingConstants.CENTER); 
		title.setFont(new Font("Arial", Font.PLAIN, 28)); 
		title.setSize(600, 35); 
		title.setLocation(hrzMid - 300, 10); 
		container.add(title);
		
		lastWeek = new JButton("Last Week"); 
		lastWeek.setFont(new Font("Arial", Font.PLAIN, 15)); 
		lastWeek.setSize(colWidth, 25); 
		lastWeek.setLocation(10, 70); 
		lastWeek.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) { 
		        // here you can do any necessary actions for the "No" button,
		        // like calling a specific method of the outer class which handles the event
		    	if (weekOfTheYear > 15) {
					weekOfTheYear --;
					weekLabel.setText(weekMap.get(weekOfTheYear));
					initializeTable();
					initializeLabel();
				}
				if (weekOfTheYear == 15) {
					lastWeek.setVisible(false);
				}
				if (weekOfTheYear == 33) {
					nextWeek.setVisible(true);
				}
		    }
		});
		container.add(lastWeek); 
		
		nextWeek = new JButton("Next Week"); 
		nextWeek.setFont(new Font("Arial", Font.PLAIN, 15)); 
		nextWeek.setSize(colWidth, 25); 
		nextWeek.setLocation(scrW - 25 - colWidth, 70); 
		nextWeek.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) { 
		        // here you can do any necessary actions for the "No" button,
		        // like calling a specific method of the outer class which handles the event
		    	if (weekOfTheYear < 34) {
					weekOfTheYear ++;
					weekLabel.setText(weekMap.get(weekOfTheYear));
					initializeTable();
					initializeLabel();
				}
				if (weekOfTheYear == 34) {
					nextWeek.setVisible(false);
				}
				if (weekOfTheYear == 16) {
					lastWeek.setVisible(true);
				}
		    }
		});
		container.add(nextWeek); 
		
		if (weekOfTheYear == 15) {
			lastWeek.setVisible(false);
		}
		if (weekOfTheYear == 34) {
			nextWeek.setVisible(false);
		}
		
		
		firstDosesMon = new JLabel();
		firstDosesMon.setLocation(colWidth + 10, scrH - 70);
		firstDosesMon.setSize(colWidth, 20);
		container.add(firstDosesMon);
		
		firstDosesTue = new JLabel();
		firstDosesTue.setLocation(colWidth * 2 + 10, scrH - 70);
		firstDosesTue.setSize(colWidth, 20);
		container.add(firstDosesTue);
		
		firstDosesWed = new JLabel();
		firstDosesWed.setLocation(colWidth * 3 + 10, scrH - 70);
		firstDosesWed.setSize(colWidth, 20);
		container.add(firstDosesWed);
		
		firstDosesThu = new JLabel();
		firstDosesThu.setLocation(colWidth * 4 + 10, scrH - 70);
		firstDosesThu.setSize(colWidth, 20);
		container.add(firstDosesThu);
		
		firstDosesFri = new JLabel();
		firstDosesFri.setLocation(colWidth * 5 + 10, scrH - 70);
		firstDosesFri.setSize(colWidth, 20);
		container.add(firstDosesFri);
		
		sndDosesMon = new JLabel();
		sndDosesMon.setLocation(colWidth + 10, scrH - 50);
		sndDosesMon.setSize(colWidth, 20);
		container.add(sndDosesMon);
		
		sndDosesTue = new JLabel();
		sndDosesTue.setLocation(colWidth * 2 + 10, scrH - 50);
		sndDosesTue.setSize(colWidth, 20);
		container.add(sndDosesTue);
		
		sndDosesWed = new JLabel();
		sndDosesWed.setLocation(colWidth * 3 + 10, scrH - 50);
		sndDosesWed.setSize(colWidth, 20);
		container.add(sndDosesWed);
		
		sndDosesThu = new JLabel();
		sndDosesThu.setLocation(colWidth * 4 + 10, scrH - 50);
		sndDosesThu.setSize(colWidth, 20);
		container.add(sndDosesThu);
		
		sndDosesFri = new JLabel();
		sndDosesFri.setLocation(colWidth * 5 + 10, scrH - 50);
		sndDosesFri.setSize(colWidth, 20);
		container.add(sndDosesFri);
		
		
		createMap();
		
		weekLabel = new JLabel(weekMap.get(weekOfTheYear), SwingConstants.CENTER); 
		weekLabel.setFont(new Font("Arial", Font.PLAIN, 20)); 
		weekLabel.setSize(400, 25); 
		weekLabel.setLocation(hrzMid - 200, 70); 
		container.add(weekLabel);
		
		String data[][] = new String[28][6];
		String columnNames[] = {"Times", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
		createFirstColumn(data);
		
		table = new JTable(data, columnNames) {
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
	    
		table.setRowHeight(50);
		table.getTableHeader().setResizingAllowed(false);
			
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		 for(int i = 0 ; i < 6; i++){
	         table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
	        }
		
		table.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
				  
			    if (e.getClickCount() == 1) {
			      JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
			      int column = target.getSelectedColumn();
			     // do some stuff
			      
			      if (clientArray[row][column - 1 + (weekOfTheYear - 15) * 5] == null && row < 14 && weekOfTheYear < 31) {
			    	  new Regristration(row, column, weekOfTheYear);
			      }
			      else if (clientArray[row][column - 1 + (weekOfTheYear - 15) * 5] != null){
			    	  System.out.println("Hello");
			      }
			      
			    }
			  }
			});
		
		
		
		JScrollPane sp = new JScrollPane(table);
		sp.getVerticalScrollBar().setPreferredSize(new Dimension(15, Integer.MAX_VALUE));
		sp.setSize(scrW - 20, scrH - 175); 
		sp.setLocation(10, 105); 
		container.add(sp);
		
		initializeTable();
		initializeLabel();
		
		frame.setVisible(true); 
	} 
	
} 