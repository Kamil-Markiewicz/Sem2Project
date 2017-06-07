import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Responsible for storing invoice data.
 * @author	Kamil Markiewicz
 * @version	2.0
 */
public class Invoice {

	private int invoiceNo;
	static private int invoiceNum = 0;
	private double invoiceAmt;
	private Date invoiceDate;
	private boolean isPaid;
	private ArrayList<Procedure> in_procList;
	private ArrayList<Payment> in_paymentList;

	/**
	 * Constructor.
	 */
	public Invoice(){
		in_procList = new ArrayList<Procedure>();
		in_paymentList = new ArrayList<Payment>();
		invoiceDate = new Date();
		invoiceNum++;
		invoiceNo = invoiceNum;
		invoiceAmt = 0;
		isPaid = false;
	}

	/**
	 * Adds procedure to the invoice.
	 * @param name	Name of procedure
	 * @param cost	Cost of procedure
	 */
	public void addProcedure(String name, double cost){
		in_procList.add(new Procedure(name, cost));
		invoiceAmt += cost;
		if(cost != 0)
			isPaid = false;
	}

	/**
	 * Removes procedure from invoice.
	 * @param index	Index of procedure
	 */
	public void removeProcedure(int index){
		invoiceAmt = invoiceAmt - in_procList.get(index).getProcCost();
		if(invoiceAmt <= 0)
			isPaid = true;
		in_procList.remove(index);
	}

	/**
	 * Removes payment from invoice.
	 * @param index	Index of payment
	 */
	public void removePay(int index){
		invoiceAmt += in_paymentList.get(index).getPaymentAmt();
		if(invoiceAmt > 0)
			isPaid = false;
		in_paymentList.remove(index);
	}

	/**
	 * Adds payment to invoice.
	 * @param amount	Amount of payment
	 */
	public void addPay(double amount){
		in_paymentList.add(new Payment(amount));
		invoiceAmt = invoiceAmt - amount;
		if(invoiceAmt <= 0)
			isPaid = true;
	}

	/**
	 * Creates a string of invoice data for file storage.
	 * @return	String of invoice data
	 */
	public String saveInvoice(){
		String str = "";	//Prepare string

		//Add invoice details to string
		str += invoiceDate.getTime() + "\n"
				+ in_procList.size() + "\n";

		//Add procedure details to string
		if(in_procList.size() > 0){
			for(int a = 0; a < in_procList.size(); a++)
				str += in_procList.get(a).getProcName() + "\n" + in_procList.get(a).getProcCost() +"\n";
		}

		str += in_paymentList.size() + "\n";
		//Add payment details to string
		if(in_paymentList.size() > 0){
			for(int a = 0; a < in_paymentList.size(); a++){
				str += in_paymentList.get(a).savePayment() +"\n";
			}
		}
		return str;
	}

	/**
	 * Gets invoice number.
	 * @return	Number of invoice
	 */
	public int getInvoiceNo(){
		return invoiceNo;
	}

	/**
	 * Gets invoice amount.
	 * @return	Amount of invoice
	 */
	public double getInvoiceAmt(){
		return invoiceAmt;
	}

	/**
	 * Gets invoice date.
	 * @return	Date of invoice.
	 */
	public Date getInvoiceDate(){
		return invoiceDate;
	}

	/**
	 * Gets invoice time.
	 * @return	Time of invoice
	 */
	public long getInvoiceTime(){
		return invoiceDate.getTime();
	}

	/**
	 * Gets outstanding amount of invoice.
	 * @return	Outstanding amount of invoice
	 */
	public double getOutstanding(){
		return invoiceAmt;
	}

	/**
	 * Gets amount of procedures on the invoice.
	 * @return	Procedure count
	 */
	public int getProcCount(){
		return in_procList.size();
	}

	/**
	 * Gets amount of payments on the invoice.
	 * @return	Payment count
	 */
	public int getPayCount(){
		return in_paymentList.size();
	}

	/**
	 * Gets cost of a procedure.
	 * @param 	index	Index of procedure
	 * @return	Cost of procedure
	 */
	public double getProcCost(int index){
		return in_procList.get(index).getProcCost();
	}

	/**
	 * Gets name of a procedure.
	 * @param 	index	Index of procedure
	 * @return	Name of procedure
	 */
	public String getProcName(int index){
		return in_procList.get(index).getProcName();
	}

	/**
	 * Gets date of payment in dd/MM/yyyy format.
	 * @param index	Index of payment
	 * @return	dd/MM/yyyy date of payment
	 */
	public String getPayDate(int index){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = sdf.format(in_paymentList.get(index).getDate());
		return strDate;
	}

	/**
	 * Gets a payment amount.
	 * @param index	Index of payment
	 * @return		Amount of payment
	 */
	public double getPayAmt(int index){
		return in_paymentList.get(index).getPaymentAmt();
	}

	/**
	 * Gets paid status of invoice.
	 * @return	True when paid
	 */
	public boolean getPaid(){
		return isPaid;
	}

	/**
	 * Sets date of invoice.
	 * @param date	Date of invoice
	 */
	public void setDate(long date){
		invoiceDate.setTime(date);
	}

	/**
	 * Sets payment date.
	 * @param index	Index of payment
	 * @param date	Date of payment
	 */
	public void setPayDate(int index, long date){
		in_paymentList.get(index).setDate(date);
	}

	@Override
	public String toString(){
		String str = "Invoice number \t" + invoiceNo + ", date \t" + invoiceDate + ", outstanding \t\u20ac" + invoiceAmt + ":\n";
		if(in_procList.size() > 0){
			str += "Procedures:\n";
			for(int a = 0; a < in_procList.size(); a++)
				str += in_procList.get(a).toString() + "\n";
		}
		if(in_paymentList.size() > 0){
			str += "Payments:\n";
			for(int a = 0; a < in_paymentList.size(); a++)
				str += in_paymentList.get(a).toString() + "\n";
		}
		return str;
	}
}