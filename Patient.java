import java.util.ArrayList;
import java.util.Comparator;

/**
 * Responsible for storing invoice data.
 * Extends Person class.
 * Implements Comparable class.
 * @author	Kamil Markiewicz
 * @version	2.0
 */
public class Patient extends Person implements Comparable<Patient>{

	private int patient;
	private static int patNo = 0;
	private String phoneNo;
	private String dentist;
	private ArrayList<Invoice> p_invoiceList;

	/**
	 * Constructor that creates patient with given details.
	 * @param name		Name of patient
	 * @param address	Address of patient
	 * @param num		Phone number of patient
	 * @param dentist	Dentist of patient
	 */
	public Patient(String name, String address, String num, String dentist){
		super(name, address);
		p_invoiceList = new ArrayList<Invoice>();
		patNo++;
		patient = patNo;
		phoneNo = num;
		this.dentist = dentist;
	}

	/**
	 * Adds invoice to patient
	 * @return	Index of added invoice
	 */
	public int addInvoice(){
		p_invoiceList.add(new Invoice());
		return p_invoiceList.size()-1;
	}

	/**
	 * Removes invoice from patient
	 * @param index	Index of invoice
	 */
	public void removeInvoice(int index){
		p_invoiceList.remove(index);
	}

	/**
	 * Adds procedure to an invoice.
	 * @param index	Index of invoice
	 * @param name	Name of procedure
	 * @param cost	Cost of procedure
	 */
	public void addProcedure(int index, String name, double cost){
		p_invoiceList.get(index).addProcedure(name, cost);
	}

	/**
	 * Removes procedure from invoice.
	 * @param invoice	Index of invoice
	 * @param index		Index of procedure
	 */
	public void removeProcedure(int invoice, int index){
		p_invoiceList.get(invoice).removeProcedure(index);
	}

	/**
	 * Removes payment from invoice.
	 * @param invoice	Index of invoice
	 * @param index		Index of payment
	 */
	public void removePay(int invoice, int index){
		p_invoiceList.get(invoice).removePay(index);
	}

	/**
	 * Adds payment to invoice.
	 * @param index		Index of invoice
	 * @param amount	Amount of payment
	 */
	public void addPay(int index, double amount){
		p_invoiceList.get(index).addPay(amount);
	}

	/**
	 * Gets patient number.
	 * @return	Patient number
	 */
	public int getPatient(){
		return patient;
	}

	/**
	 * Creates a string of Invoice summary.
	 * @return	Summary of invoice
	 */
	public String getInvoices(){
		String str = "";
		for(int a = 0; a < p_invoiceList.size(); a++){
			str += a + ") Invoice Number: " + p_invoiceList.get(a).getInvoiceNo()
					+ " Amount: \u20ac" + p_invoiceList.get(a).getInvoiceAmt()
					+ " Outstanding: \u20ac" + p_invoiceList.get(a).getOutstanding() + "\n";
		}
		return str;
	}

	/**
	 * Gets procedure count from invoice.
	 * @param index	Index of invoice
	 * @return		Procedure count
	 */
	public int getProcCount(int index){
		return p_invoiceList.get(index).getProcCount();
	}

	/**
	 * Gets payment count from invoice.
	 * @param index	Index of invoice
	 * @return		Payment count
	 */
	public int getPayCount(int index){
		return p_invoiceList.get(index).getPayCount();
	}

	/**
	 * Gets phone number of patient.
	 * @return	Phone number of patient
	 */
	public String getNum(){
		return phoneNo;
	}

	/**
	 * Gets invoice count.
	 * @return	Count of invoices
	 */
	public int getInvCount(){
		return p_invoiceList.size();
	}

	/**
	 * Gets invoice number.
	 * @param index	Index of invoice
	 * @return		Number of invoice
	 */
	public int getInvNum(int index){
		return p_invoiceList.get(index).getInvoiceNo();
	}

	/**
	 * Gets outstanding amount of invoice.
	 * @param index	Index of invoice
	 * @return		Outstanding amount of invoice
	 */
	public double getInvOut(int index){
		return p_invoiceList.get(index).getOutstanding();
	}

	/**
	 * Gets procedure cost from invoice.
	 * @param index	Index of invoice
	 * @param proc	Index of procedure
	 * @return		Cost of procedure
	 */
	public double getProcCost(int index, int proc){
		return p_invoiceList.get(index).getProcCost(proc);
	}

	/**
	 * Gets procedure name from invoice.
	 * @param index	Index of invoice
	 * @param proc	Index of procedure
	 * @return		Name of procedure
	 */
	public String getProcName(int index, int proc){
		return p_invoiceList.get(index).getProcName(proc);
	}

	/**
	 * Gets payment date from invoice as string.
	 * @param index	Index of invoice
	 * @param pay	Index of payment
	 * @return		Date of payment
	 */
	public String getPayDate(int index, int pay){
		return p_invoiceList.get(index).getPayDate(pay);
	}

	/**
	 * Gets payment amount from invoice.
	 * @param index	Index of invoice
	 * @param pay	Index of payment
	 * @return		Amount of payment
	 */
	public double getPayAmt(int index, int pay){
		return p_invoiceList.get(index).getPayAmt(pay);
	}

	/**
	 * Gets dentist responsible for this patient.
	 * @return	Name of dentist
	 */
	public String getDentist(){
		return dentist;
	}

	/**
	 * Creates a string of patient details for file saving.
	 * @return	Patient details
	 */
	public String savePatient(){
		String str = "";	//Prepare string

		//Add patient details to string
		str += getName() + "\n" + getAddress() + "\n" + getNum() + "\n" + dentist + "\n" + p_invoiceList.size() + "\n";

		//Add invoice details
		if(p_invoiceList.size() > 0){
			for(int a = 0; a < p_invoiceList.size(); a++)
				str += p_invoiceList.get(a).saveInvoice();
		}
		return str;
	}

	/**
	 * Sets date of invoice.
	 * @param index	Index of invoice
	 * @param date	Date of invoice
	 */
	public void setInvDate(int index, long date){
		p_invoiceList.get(index).setDate(date);
	}

	/**
	 * Sets date of payment on invoice.
	 * @param index		Index of invoice
	 * @param payment	Index of payment
	 * @param date		Date of payment
	 */
	public void setPayDate(int index, int payment, long date){
		p_invoiceList.get(index).setPayDate(payment, date);
	}

	/**
	 * Gets outstanding amount of invoice.
	 * @return	Outstanding amount of invoice
	 */
	public double getOutstanding(){
		double outstanding = 0;
		for(int a = 0; a < p_invoiceList.size(); a++)
			outstanding += p_invoiceList.get(a).getOutstanding();
		return outstanding;
	}

	/**
	 * Determines if an invoice is unpaid over a given period.
	 * @param date		Current date
	 * @param months	Time period to consider in months
	 * @return			True if unpaid
	 */
	public boolean isUnpaid(long date, int months){
		boolean unpaid = false;
		long period = 2629743000L;	//1 month is milliseconds
		period = period*months;	//Full period in milliseconds

		//Search through all invoices until an unpaid invoice is found
		for(int a = 0; a < p_invoiceList.size(); a++){
			if(!unpaid){
				if(p_invoiceList.get(a).getInvoiceTime() < (date - period)){
					if(p_invoiceList.get(a).getOutstanding() > 0)
						unpaid = true;
				}
			}
		}
		return unpaid;
	}

	@Override
	public String toString(){
		String str = "";
		str += "Patient: \t" + super.getName() + ", address: \t" + super.getAddress() + ", phone number: \t" + phoneNo + ", dentist: \t" + dentist + ".";
		if(p_invoiceList.size() > 0){
			str += ", invoices: \n";
			for(int a = 0; a < p_invoiceList.size(); a++)
				str += p_invoiceList.get(a).toString();
		}
		str+="\n***";
		return str;
	}

	/**
	 * Here from spec.
	 */
	public void print(){
		System.out.print(toString());
	}

	@Override
	public int compareTo(Patient patient) {
		//Return the value for ascending order
		return getName().compareTo(patient.getName());
	}

	/**
	 * Comparator that compares patients in terms of their outstanding amount in descending order
	 */
	public static Comparator<Patient> PatientUnpaidComparator = new Comparator<Patient>() {
		public int compare(Patient patient1, Patient patient2) {

			double unpaid1 = patient1.getOutstanding();
			double unpaid2 = patient2.getOutstanding();

			//Return the value for descending order
			double num = (unpaid2 - unpaid1);
			Math.ceil(num);
			return (int)num;
		}
	};
}