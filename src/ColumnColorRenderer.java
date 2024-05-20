import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

//Customize the code to set the background and foreground color for each column of a JTable
class ColumnColorRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Color backgroundColor, foregroundColor;
	public ColumnColorRenderer(Color backgroundColor, Color foregroundColor) {
	   super();
	   this.backgroundColor = backgroundColor;
	   this.foregroundColor = foregroundColor;
	}
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,   boolean hasFocus, int row, int column) {
	   Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	   cell.setBackground(backgroundColor);
	   cell.setForeground(foregroundColor);
	   return cell;
	}
}

//-----------------------------------------------------------------------------------------------
/**
 *	private static TableColumn tColumn;
 *	tColumn = table.getColumnModel().getColumn(1);
 *	tColumn.setCellRenderer(new ColumnColorRenderer(Color.lightGray, Color.black));
 *
 *  ------------------ Regristration---------------
 *  Client client = new Client(tname.getText());
 *	MainScree.updateTable(tname.getText(), row, column);
 *	MainScree.addClient(client, row, column);
 *	setVisible(false); //you can't see me!
 *	dispose(); 
 *
 *	SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy");
 *	Calendar cal = Calendar.getInstance();
 *  cal.set(Calendar.WEEK_OF_YEAR, 15);
 *  cal.set(Calendar.DAY_OF_WEEK, 2);
 * 	cal.set(Calendar.YEAR,2021);
 *  System.out.println(sdf.format(cal.getTime()));
 *  
 *  ----- Client -----
 *  Make client serializable for storing in object ouput stream
 *  
 *  ------ Main Screen -------
 *  
 *  public void setButtonsActionListener(ActionListener listener){
 * 		sub.addActionListener(listener);
 * 	}
 * 
 *  //necessary for first time running to get object from file
 *  storeClientArray();
 *  storeLabelArray();
 *  
 *  r.setButtonsActionListener(new ActionListener(){
 *		@Override
 * 		public void actionPerformed(ActionEvent e){
 * 			// do something when button in regristration class is clicked
 *  	}
 * 	});
 */

/**
 * 
 * 
 */
//private static void storeLabelArray() {
//	
//	try {
//
//        // Converts the string into bytes
//        var out = new ObjectOutputStream(new FileOutputStream("labels.txt"));
//          
//        out.writeObject(clientArray);
//		out.writeObject(labelArray);
//		
//		
//        System.out.println("Data is written to the file.");
//
//        // Closes the output stream         
//        out.close();
//    }
//
//    catch (Exception e) {
//    	System.out.println("failed");
//        e.getStackTrace();
//    }
//}
//
//private static int[][] getStoredLabelArray() {
//	
//	// get the stored data
//	try {
//		System.out.println("Create File Input");
//		FileInputStream fileStream = new FileInputStream("labels.txt");
//        // Creating an object input stream
//		System.out.println("Create Object Input");
//        ObjectInputStream objStream = new ObjectInputStream(fileStream);
//        
//       
//        System.out.println("Read Object");
//        int[][] copyArray = (int[][]) objStream.readObject();
//                
//        
//        objStream.close();
//        
//        System.out.println("Return");
//        return copyArray;
//    }
//
//    catch (Exception e) {
//        e.getStackTrace();
//        return null;
//    }
//}

//private static Client[][] getStoredClientArray() {
//	
//	// get the stored data
//	try {
//		System.out.println("Create File Input");
//		FileInputStream fileStream = new FileInputStream("clients.txt");
//        // Creating an object input stream
//		System.out.println("Create Object Input");
//        ObjectInputStream objStream = new ObjectInputStream(fileStream);
//        
//       
//        System.out.println("Read Object");
//        Client[][] copyArray = (Client[][]) objStream.readObject();
//                
//        
//        objStream.close();
//        
//        System.out.println("Return");
//        return copyArray;
//    }
//
//    catch (Exception e) {
//        e.getStackTrace();
//        return null;
//    }
//}
//private static void storeClientArray() {
//
//try {
//
//    // Converts the string into bytes
//    var out = new ObjectOutputStream(new FileOutputStream("clients.txt"));
//                
//	out.writeObject(clientArray);
//	
//    System.out.println("Data is written to the file.");
//
//    // Closes the output stream         
//    out.close();
//}
//
//catch (Exception e) {
//	System.out.println("failed");
//    e.getStackTrace();
//}
//}
//

