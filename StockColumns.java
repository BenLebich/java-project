
// stock column class used to
// build the columns in the table view
public class StockColumns {

	// row lables
	private String column0 = null;

	// days/weeks/months (1-7)
	private Double column1 = null;
	private Double column2 = null;
	private Double column3 = null;
	private Double column4 = null;
	private Double column5 = null;
	private Double column6 = null;
	private Double column7 = null;

	public StockColumns(String column0, Double column1, Double column2, Double column3, Double column4, Double column5,
			Double column6, Double column7) {
		this.column0 = column0;
		this.column1 = column1;
		this.column2 = column2;
		this.column3 = column3;
		this.column4 = column4;
		this.column5 = column5;
		this.column6 = column6;
		this.column7 = column7;
	}

	public String getColumn0() {
		return column0;
	}

	public void setColumn0(String column0) {
		this.column0 = column0;
	}

	public Double getColumn1() {
		return column1;
	}

	public void setColumn1(Double column1) {
		this.column1 = column1;
	}

	public Double getColumn2() {
		return column2;
	}

	public void setColumn2(Double column2) {
		this.column2 = column2;
	}

	public Double getColumn3() {
		return column3;
	}

	public void setColumn3(Double column3) {
		this.column3 = column3;
	}

	public Double getColumn4() {
		return column4;
	}

	public void setColumn4(Double column4) {
		this.column4 = column4;
	}

	public Double getColumn5() {
		return column5;
	}

	public void setColumn5(Double column6) {
		this.column6 = column6;
	}

	public Double getColumn6() {
		return column6;
	}

	public void setColumn6(Double column7) {
		this.column7 = column7;
	}

	public Double getColumn7() {
		return column7;
	}

	public void setColumn7(Double column7) {
		this.column7 = column7;
	}

}
