import java.util.Date;

/**
 * Responsible for storing patient data.
 * @author	Kamil Markiewicz
 * @version	2.0
 */
public class Payment {

	private int payment;
	static int paymentNo = 0;
	private double paymentAmt;
	private Date paymentDate;

	/**
	 * Constructor which creates a payment at this time with the given amount.
	 * @param amount	Amount in the payment
	 */
	public Payment(double amount){
		paymentDate = new Date();
		paymentAmt = amount;
		paymentNo++;
		payment = paymentNo;
	}

	/**Creates a string with payment data for file storage.
	 * @return	String of payment data
	 */
	public String savePayment(){
		String str = "";
		str += paymentAmt + "\n" + paymentDate.getTime();
		return str;
	}

	/**
	 * Gets payment amount.
	 * @return	Amount of payment
	 */
	public double getPaymentAmt() {
		return paymentAmt;
	}

	/**
	 * Sets payment amount.
	 * @param paymentAmt
	 */
	public void setPaymentAmt(double paymentAmt) {
		this.paymentAmt = paymentAmt;
	}


	/**
	 * Gets payment number.
	 * @return	Number of payment
	 */
	public int getPayment() {
		return payment;
	}

	/**
	 * Gets payment date.
	 * @return	Date of payment
	 */
	public Date getDate() {
		return paymentDate;
	}

	/**
	 * Sets payment date.
	 * @param date	Date of payment
	 */
	public void setDate(long date) {
		paymentDate.setTime(date);
	}

	@Override
	public String toString(){
		return paymentDate + " \t\u20ac" + paymentAmt;
	}
}