import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javafx.application.Application;

/**
 * Responsible for main behavior/functionality and maintaining references to data objects.
 * 
 * @author	Kamil Markiewicz
 * @version	2.0
 */
public class MainApplication {

	private static ArrayList<Patient> patientList;
	private static ArrayList<Procedure> procedureList;
	private static ArrayList<Dentist> dentistList;
	private static GUI gui;		//Allows for communication back to the GUI
	private static String dentist;
	final private static String PROC_FILE = "Procedures.txt";
	final private static String PAT_FILE = "Patients.txt";
	final private static String DENT_FILE = "Dentists.txt";

	/**
	 * Initializes ArrayLists, loads data from files and launches GUI.
	 * 
	 * @param args	Unused
	 */
	public static void main(String[] args){
		dentist = "";
		//Initialize ArrayLists
		patientList = new ArrayList<Patient>();
		procedureList = new ArrayList<Procedure>();
		dentistList = new ArrayList<Dentist>();

		//Read procedure data from file.
		readProcs();

		//Read patients from file
		readPats();

		//Read dentists from file
		readDents();

		//Launch GUI
		Application.launch(GUI.class, args);
	}

	/**
	 * Establishes reference to GUI.
	 * 
	 * @param guiRef Reference to GUI
	 */
	public static void setGUI(GUI guiRef){
		gui = guiRef;
	}

	/**
	 * Check if login is valid and logs errors or success.
	 * 
	 * @param name	Name of dentist
	 * @param pass	Password of dentist
	 * @return		Validity of login
	 */
	public static boolean checkLogIn(String name, String pass){
		boolean valid = false;	//Flag for validity of login
		boolean found = false;	//Flag for found dentist

		//Set variables
		String str = "";
		int index = -1;

		//Check if the dentist name is registered
		for(int a = 0; a < dentistList.size(); a++){
			if(dentistList.get(a).getName().equals(name)){
				found = true;
				index = a;
			}
		}

		//If name is registered check if password matches
		if(found){
			if(dentistList.get(index).getPassword().equals(pass))
				valid = true;
			if(valid)
				str += "Logged in as " + name + ".";
		}

		//If not registered or wrong password, notify user
		if(!found)
			str += "This dentist is not registered.";
		else if(found){
			if(!valid)
				str += "Incorrect Password.";
		}

		//Update log for GUI
		gui.setLog(str);

		//If valid set active dentist
		if(valid)
			dentist = name;

		return valid;
	}

	/**
	 * Checks validity of dentist registry.
	 * If valid, registers dentist to the system.
	 * 
	 * @param name		Name of dentist
	 * @param address	Address of dentist
	 * @param pass		Password of dentist
	 * @return			Validity of registry
	 */
	public static boolean checkReg(String name, String address, String pass){
		boolean valid = true;	//Flag for registry validity
		String str = "";	//Prepare log String

		//Check if dentist already exists
		for(int a = 0; a < dentistList.size(); a++){
			if(dentistList.get(a).getName().equals(name)){
				valid = false;
				str += "This Dentist already exists. ";
			}
		}

		//Check if user has not left any fields blank
		if(valid){
			if(name.length() < 1){
				valid = false;
				str += "Name is mandatory. ";
			}
			if(address.length() < 1){
				valid = false;
				str += "Address is mandatory. ";
			}
			if(pass.length() < 1){
				valid = false;
				str += "Password is mandatory. ";
			}
		}

		//If valid, register user
		if(valid){
			dentistList.add(new Dentist(name, address, pass));
			str += "Dentist " + name + " registered successfully.";
			//Save dentists to file
			writeDents();
		}

		//Update log for GUI
		gui.setLog(str);
		return valid;
	}

	/**
	 * Checks procedure validity.
	 * 
	 * @param name	Name of procedure
	 * @param cost	Cost of procedure
	 * @return		Validity of procedure
	 */
	public static boolean checkProc(String name, String cost){
		boolean valid = true;	//Flag for procedure validity
		String str = "";	//Prepare log String

		//Check if procedure already exists
		for(int a = 0; a < procedureList.size(); a++){
			if(procedureList.get(a).getProcName().equals(name)){
				valid = false;
				str += "This procedure already exists. ";
			}
		}

		//Check if user has left any fields blank
		if(valid){
			if(name.length() < 1){
				valid = false;
				str += "Name is mandatory. ";
			}
			if(cost.length() < 1){
				valid = false;
				str += "Enter 0 for a free procedure. ";
			}
		}

		//Update log for GUI
		gui.setLog(str);
		return valid;
	}

	/**
	 * Checks if user has left any fields blank when editing procedure.
	 * 
	 * @param name	Name of procedure
	 * @param cost	Cost of procedure
	 * @return		Validity of procedure
	 */
	public static boolean checkEditProc(String name, String cost){
		boolean valid = true;
		String str = "";
		if(name.length() < 1){
			valid = false;
			str += "Name is mandatory. ";
		}
		if(cost.length() < 1){
			valid = false;
			str += "Enter 0 for a free procedure. ";
		}

		//Update log for GUI
		gui.setLog(str);
		return valid;
	}

	/**
	 * Checks payment validity.
	 * 
	 * @param amount	Amount of payment
	 * @return			Validity of payment
	 */
	public static boolean checkPay(String amount){
		boolean valid = true;	//Flag for payment validity
		String str = "";	//Prepare log String

		//Check if user left field empty
		if(amount.length() < 1){
			valid = false;
			str += "Payment cannot be 0. ";
		}

		//Update log for GUI
		gui.setLog(str);
		return valid;
	}

	/**
	 * Adds patient if details are valid
	 * 
	 * @param name		Name of patient
	 * @param address	Address of patient
	 * @param phone		Phone number of patient
	 * @return			Validity of patient details
	 */
	public static boolean addPat(String name, String address, String phone){
		boolean valid = true;	//Flag for patient validity
		String str = "";	//Prepare log String

		//Check if user left any empty fields
		if(name.length() < 1){
			valid = false;
			str += "Name is mandatory. ";
		}
		if(address.length() < 1){
			valid = false;
			str += "Address is mandatory. ";
		}
		if(phone.length() < 1){
			valid = false;
			str += "Phone number is mandatory. ";
		}

		//If valid add patient
		if(valid){
			patientList.add(new Patient(name, address, phone,dentist));
			str += "Patient " + name + " added successfully.";
		}

		//Update log for GUI
		gui.setLog(str);
		return valid;
	}

	/**
	 * Checks and adds procedure to system if valid.
	 * 
	 * @param name	Name of procedure
	 * @param cost	Cost of procedure
	 * @return		Validity of procedure
	 */
	public static boolean addProc(String name, String cost){
		boolean valid = checkProc(name, cost);	//Check if procedure is valid

		//If valid try to parse cost into double. If parsed, add procedure to system and write to file
		if(valid){
			try{
				double price = Double.parseDouble(cost);
				procedureList.add(new Procedure(name, price));
				writeProcs();
			}
			catch(Exception e){
				valid = false;
				gui.setLog("Enter a double value for cost.");
			}
		}
		return valid;
	}

	/**
	 * Checks and adds payment to patient invoice if valid.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 * @param amount	Amount of payment
	 * @return			Validity of payment
	 */
	public static boolean addPay(int index, int invoice, String amount){
		boolean valid = checkPay(amount);//Check if payment is valid

		//If valid try to parse amount into double. If parsed, add payment to patient invoice.
		if(valid){
			try{
				double price = Double.parseDouble(amount);
				patientList.get(index).addPay(invoice, price);
			}
			catch(Exception e){
				valid = false;
				gui.setLog("Enter a double value for cost.");
			}
		}
		return valid;
	}

	/**
	 * Gets active dentist.
	 * 
	 * @return	Active dentist name
	 */
	public static String getActiveDentist(){
		return dentist;
	}

	/**
	 * Gets patient count.
	 * 
	 * @return 	Number of patients in the system.
	 */
	public static int getPatCount(){
		return patientList.size();
	}

	/**
	 * Gets name of patient.
	 * 
	 * @param index	Index of patient
	 * @return		Name of patient
	 */
	public static String getPatName(int index){
		return patientList.get(index).getName();
	}

	/**
	 * Gets address of patient.
	 * 
	 * @param index	Index of patient
	 * @return		Address of patient
	 */
	public static String getPatAddress(int index){
		return patientList.get(index).getAddress();
	}

	/**
	 * Gets phone number of patient.
	 * 
	 * @param index Index of patient
	 * @return		Phone number of patient
	 */
	public static String getPatNum(int index){
		return patientList.get(index).getNum();
	}

	/**
	 * Gets dentist of patient.
	 * 
	 * @param index Index of patient
	 * @return		Dentist of patient
	 */
	public static String getPatDentist(int index){
		return patientList.get(index).getDentist();
	}

	/**
	 * Gets procedure count.
	 * 
	 * @return	Count of procedures in the system
	 */
	public static int getProcCount(){
		return procedureList.size();
	}

	/**
	 * Gets name of procedure.
	 * 
	 * @param index	Index of procedure
	 * @return		Name of procedure
	 */
	public static String getProcName(int index){
		return procedureList.get(index).getProcName();
	}

	/**
	 * Gets procedure cost.
	 * 
	 * @param index	Index of procedure
	 * @return		Cost of procedure
	 */
	public static double getProcCost(int index){
		return procedureList.get(index).getProcCost();
	}

	/**
	 * Gets invoice count.
	 * 
	 * @param index	Index of patient
	 * @return		Count of invoices of patient
	 */
	public static int getInvCount(int index){
		return patientList.get(index).getInvCount();
	}

	/**
	 * Gets invoice number.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 * @return			Invoice number
	 */
	public static int getInvNum(int index, int invoice){
		return patientList.get(index).getInvNum(invoice);
	}

	/**
	 * Gets outstanding amount from invoice.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 * @return			Outstanding amount
	 */
	public static double getInvOut(int index, int invoice){
		return patientList.get(index).getInvOut(invoice);
	}

	/**
	 * Gets procedure count from invoice.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 * @return			Procedure count of invoice
	 */
	public static int getInvProcCount(int index, int invoice){
		return patientList.get(index).getProcCount(invoice);
	}

	/**
	 * Gets procedure cost from invoice.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 * @param proc		Index of procedure
	 * @return			Cost of procedure
	 */
	public static double getInvProcCost(int index, int invoice, int proc){
		return patientList.get(index).getProcCost(invoice, proc);
	}

	/**
	 * Gets procedure name from invoice.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 * @param proc		Index of procedure
	 * @return			Name of procedure
	 */
	public static String getInvProcName(int index, int invoice, int proc){
		return patientList.get(index).getProcName(invoice, proc);
	}

	/**
	 * Gets count of payments from invoice.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 * @return			Count of payments
	 */
	public static int getPayCount(int index, int invoice){
		return patientList.get(index).getPayCount(invoice);
	}

	/**
	 * Gets payment date from invoice.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 * @param pay		Index of payment
	 * @return			Payment date
	 */
	public static String getPayDate(int index, int invoice, int pay){
		return patientList.get(index).getPayDate(invoice, pay);
	}

	/**
	 * Gets payment amount from invoice.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 * @param pay		Index of payment
	 * @return			Payment amount
	 */
	public static double getPayAmt(int index, int invoice, int pay){
		return patientList.get(index).getPayAmt(invoice, pay);
	}

	/**
	 * Reads patients from a file and loads into ArrayList
	 */
	public static void readPats(){
		//Set variables
		int patients = 0;
		String name = "";
		String address = "";
		String phoneNo = "";
		String dent = "";
		int invoices = 0;
		long invDate = 0;
		int procedures = 0;
		String procName = "";
		double procCost = 0;
		int payments = 0;
		double payment = 0;
		long paymentDate = 0;

		//Try to read patients
		try (BufferedReader br = new BufferedReader(new FileReader(PAT_FILE))){
			String nextLine;

			//Get patient count
			nextLine = br.readLine();
			patients = Integer.parseInt(nextLine);

			//Get patient data
			for(int a = 0; a < patients; a++){
				nextLine = br.readLine();
				name = nextLine;
				nextLine = br.readLine();
				address = nextLine;
				nextLine = br.readLine();
				phoneNo = nextLine;
				nextLine = br.readLine();
				dent = nextLine;

				//Add patient using read data
				patientList.add(new Patient(name, address, phoneNo,dent));

				//Get invoice count
				nextLine = br.readLine();
				invoices = Integer.parseInt(nextLine);

				//Get invoice data
				for(int b = 0; b < invoices; b++){

					//Create new invoice
					patientList.get(a).addInvoice();
					nextLine = br.readLine();
					invDate = Long.parseLong(nextLine);
					patientList.get(a).setInvDate(b, invDate);

					//Get procedure count
					nextLine = br.readLine();
					procedures = Integer.parseInt(nextLine);

					//Get procedure data
					for(int c = 0; c < procedures; c++){
						nextLine = br.readLine();
						procName = nextLine;
						nextLine = br.readLine();
						procCost = Double.parseDouble(nextLine);

						//Add procedure to invoice
						patientList.get(a).addProcedure(b, procName, procCost);
					}

					//Get payment count
					nextLine = br.readLine();
					payments = Integer.parseInt(nextLine);

					//Get payment data
					for(int c = 0; c < payments; c++){
						nextLine = br.readLine();
						payment = Double.parseDouble(nextLine);
						nextLine = br.readLine();
						paymentDate = Long.parseLong(nextLine);

						//Add payment to invoice
						patientList.get(a).addPay(b, payment);
						patientList.get(a).setPayDate(b, c, paymentDate);
					}
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Writes patients to file for future reading.
	 */
	public static void writePats(){
		boolean success = true;		//Flag for success of save
		String str = "";	//Prepare String for writing


		str += patientList.size() + "\n";	//Print amount of patients

		//Add patient data to String
		for(int a = 0; a < patientList.size(); a++)
			str += patientList.get(a).savePatient();

		//Try to write patient data to file
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PAT_FILE), StandardCharsets.UTF_8))) {
			writer.write(str);
		}
		catch (IOException e) {
			e.printStackTrace();
			success = false;
		}

		//Notify of success of failure to the GUI
		if(success)
			gui.setLog("Patients saved.");
		else
			gui.setLog("Failed to save patients.");
	}

	/**
	 * Reads procedures from a file and loads into ArrayList.
	 */
	public static void readProcs(){
		//Set variables
		boolean costLine = false;	//Determines if name or cost is being read
		double procCost;
		String pName = "";
		String pCost = "";

		//Try to read procedure data from file.
		try (BufferedReader br = new BufferedReader(new FileReader(PROC_FILE))){
			String nextLine;	//Take in next line from file

			//Keep reading until there's no more lines in the file
			while ((nextLine = br.readLine()) != null){

				//Add characters to either procedure name or cost depending on which line is being read
				for(int a = 0; a < nextLine.length(); a++){
					if(!costLine)
						pName += nextLine.charAt(a);
					else
						pCost += nextLine.charAt(a);
				}

				//If done reading cost line parse String into double
				if(costLine){
					procCost = Double.parseDouble(pCost);

					//Add procedure to ArrayList and clear variables for next patient
					procedureList.add(new Procedure(pName, procCost));
					pName = "";
					pCost = "";
				}
				costLine = !costLine;	//Invert line flag
			}
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Writes procedures to a file for future reading.
	 */
	public static void writeProcs(){
		String str = "";	//Prepare String for writing

		//Add procedure data to String
		for(int a = 0; a < procedureList.size(); a++){
			str += procedureList.get(a).getProcName() + "\n" + procedureList.get(a).getProcCost();

			//Add a newline only if not last patient
			if(a != procedureList.size()-1)
				str += "\n";
		}

		//Try to write the String to the file
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PROC_FILE), StandardCharsets.UTF_8))) {
			writer.write(str);
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Reads dentists from a file and adds to ArrayList.
	 */
	public static void readDents(){
		//Set variables
		int next = 0;
		boolean separate = false;
		String name = "";
		String address = "";
		String password = "";

		//Try to read procedure data from file.
		try (BufferedReader br = new BufferedReader(new FileReader(DENT_FILE))){
			String nextLine;	//Take in next line from file

			//Keep reading in dentists as long as there's lines left in the file
			while ((nextLine = br.readLine()) != null){

				//Add characters to String depending on which line is being read
				for(int a = 0; a < nextLine.length(); a++){
					if(next == 0)
						name += nextLine.charAt(a);
					else if(next == 1)
						address += nextLine.charAt(a);
					else
						password += nextLine.charAt(a);
				}

				//If the line being read is not 3, increment line
				if(next < 3)
					next++;

				//If line being read is 3, set flag for separation to true
				if(next == 3)
					separate = true;

				//If all dentist data has been read, add dentist to ArrayList
				if(separate){
					dentistList.add(new Dentist(name, address, password));

					//Clear variables for next dentist
					name = "";
					address = "";
					password = "";
					separate = false;
				}

				//Reset line counter
				if(next == 3)
					next = 0;
			}
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Writes dentists to a file for future reading.
	 */
	public static void writeDents(){
		String str = "";	//Prepare String for writing

		//Add dentist data to String
		for(int a = 0; a < dentistList.size(); a++){
			str += dentistList.get(a).getName() + "\n" + dentistList.get(a).getAddress() + "\n" + dentistList.get(a).getPassword();

			//Add newline only if not last dentist
			if(a != dentistList.size()-1)
				str += "\n";
		}

		//Try to write String to the file
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DENT_FILE), StandardCharsets.UTF_8))) {
			writer.write(str);
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Writes a report on patients.
	 * Depending on mode writes report in 2 ways:
	 * 0 = Report of patients sorted in ascending order by name.
	 * 1 = Report of patients sorted by amount unpaid over 6 months in descending order.
	 * 
	 * @param mode	Mode of operation
	 * @param file	File name to write to
	 */
	public static void writeReport(int mode, String file){
		//If no name entered ask to enter name
		if(file.length() == 0)
			gui.setLog("Enter file name.");

		//If name is entered
		else{
			String str = "";	//Prepare String for writing

			//Sort patients according to mode
			if(mode == 0){	//Sort by name
				str += "Report of patients sorted by name.\n\n";
				Collections.sort(patientList);

				//Add patient data to String
				for(int a = 0; a < patientList.size(); a++){
					str += patientList.get(a).toString();

					//Add newline only if not last patient
					if(a != patientList.size()-1)
						str += "\n";
				}
			}
			else{	//Sort by unpaid
				str += "Report of patients sorted by unpaid over 6 months.\n\n";
				Collections.sort(patientList, Patient.PatientUnpaidComparator);

				long date = new Date().getTime();	//Get current time for comparing if over 6 months

				//Add patient data to String
				for(int a = 0; a < patientList.size(); a++){
					//Add patient only if he has an unpaid invoice over 6 months
					if(patientList.get(a).isUnpaid(date, 6))
						str += patientList.get(a).toString();

					//Add newline only if not last patient
					if(a != patientList.size()-1)
						str += "\n";
				}
			}

			//Try to write report to the file
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
				writer.write(str);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Removes patient from ArrayList.
	 * 
	 * @param index	Index of patient
	 */
	public static void removePat(int index){
		patientList.remove(index);
	}

	/**
	 * Removes procedure from ArrayList.
	 * 
	 * @param index	Index of procedure
	 */
	public static void removeProc(int index){
		procedureList.remove(index);
		writeProcs();
	}

	/**
	 * Edit procedure on the system.
	 * Only changes if the new details are valid.
	 * 
	 * @param index	Index of procedure
	 * @param name	New name of procedure
	 * @param cost	New cost of procedure
	 * @return		Validity of new details
	 */
	public static boolean editProc(int index, String name, String cost){
		boolean correct = checkEditProc(name, cost);	//Check if new details are valid

		//If valid try to change procedure details
		if(correct){

			//try to parse cost into a double and edit procedure details
			try{
				double price = Double.parseDouble(cost);
				procedureList.get(index).setProcName(name);
				procedureList.get(index).setProcCost(price);
				writeProcs();
			}
			catch(Exception e){
				e.printStackTrace();
				correct = false;
			}
		}
		return correct;
	}

	/**
	 * Adds invoice for a procedure to a patient.
	 * 
	 * @param index	Index of patient
	 * @param proc	Index of procedure
	 */
	public static void addInv(int index, int proc){
		int invoice = patientList.get(index).addInvoice();	//Creates a new invoice and returns its index
		patientList.get(index).addProcedure(invoice, procedureList.get(proc).getProcName(), procedureList.get(proc).getProcCost());
	}

	/**
	 * Adds procedure to invoice.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 * @param proc		Index of procedure
	 */
	public static void addInvProc(int index, int invoice, int proc){
		patientList.get(index).addProcedure(invoice, procedureList.get(proc).getProcName(), procedureList.get(proc).getProcCost());
	}

	/**
	 * Remove procedure from invoice.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 * @param proc		Index of procedure
	 */
	public static void removeInvProc(int index, int invoice, int proc){
		patientList.get(index).removeProcedure(invoice, proc);
	}

	/**
	 * Removes invoice from patient.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 */
	public static void removeInv(int index, int invoice){
		patientList.get(index).removeInvoice(invoice);
	}

	/**
	 * Removes payment from invoice.
	 * 
	 * @param index		Index of patient
	 * @param invoice	Index of invoice
	 * @param pay		Index of payment
	 */
	public static void removePay(int index, int invoice, int pay){
		patientList.get(index).removePay(invoice, pay);
	}
}