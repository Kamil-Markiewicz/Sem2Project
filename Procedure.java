/**
 * Responsible for storing procedure data.
 * @author Kamil Markiewicz
 * @version 2.0
 */
public class Procedure {

	private int proc;
	private static int procNo = 0;
	private String procName;
	private double procCost;

	/**
	 * Constructor that creates a procedure with given details.
	 * @param name	Name of procedure
	 * @param cost	Cost of procedure
	 */
	public Procedure(String name, double cost){
		procName = name;
		procCost = cost;
		procNo++;
		proc = procNo;
	}

	/**
	 * Gets name of procedure
	 * @return	Name of procedure
	 */
	public String getProcName() {
		return procName;
	}

	/**
	 * Sets name of procedure.
	 * @param procName	Name of procedure
	 */
	public void setProcName(String procName) {
		this.procName = procName;
	}

	/**
	 * Gets cost of procedure.
	 * @return	Cost of procedure
	 */
	public double getProcCost() {
		return procCost;
	}

	/**
	 * Sets cost of procedure.
	 * @param procCost	Cost of procedure
	 */
	public void setProcCost(double procCost) {
		this.procCost = procCost;
	}

	/**
	 * Gets number of procedure.
	 * @return	Number of procedure
	 */
	public int getProc() {
		return proc;
	}

	@Override
	public String toString(){
		return "\u20ac" + procCost + " \t" + procName;
	}
}