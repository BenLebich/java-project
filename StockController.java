import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

// stock controller class, used to get the model data
// and then put it in the view on events
public class StockController implements Initializable {

	// init vars used throughout the controlelr
	private boolean searching = false;
	private StockModel stock = new StockModel();
	double[][] dailyData;
	double[][] weeklyData;
	double[][] monthlyData;
	long tStart = System.currentTimeMillis() - 60000;
	long tEnd = 0;
	long tDelta = 0;
	double elapsedSeconds = 61;
	int symbolValid;

	@FXML
	private TableColumn<StockColumns, String> rowTitle1;
	@FXML
	private TableColumn<StockColumns, String> rowTitle2;
	@FXML
	private TableColumn<StockColumns, String> rowTitle3;

	@FXML
	private TableView<StockColumns> dailyView;
	@FXML
	private TableColumn<StockColumns, Double> day1;
	@FXML
	private TableColumn<StockColumns, Double> day2;
	@FXML
	private TableColumn<StockColumns, Double> day3;
	@FXML
	private TableColumn<StockColumns, Double> day4;
	@FXML
	private TableColumn<StockColumns, Double> day5;
	@FXML
	private TableColumn<StockColumns, Double> day6;
	@FXML
	private TableColumn<StockColumns, Double> day7;

	@FXML
	private TableView<StockColumns> weeklyView;
	@FXML
	private TableColumn<StockColumns, Double> week1;
	@FXML
	private TableColumn<StockColumns, Double> week2;
	@FXML
	private TableColumn<StockColumns, Double> week3;
	@FXML
	private TableColumn<StockColumns, Double> week4;
	@FXML
	private TableColumn<StockColumns, Double> week5;
	@FXML
	private TableColumn<StockColumns, Double> week6;
	@FXML
	private TableColumn<StockColumns, Double> week7;

	@FXML
	private TableView<StockColumns> monthlyView;
	@FXML
	private TableColumn<StockColumns, Double> month1;
	@FXML
	private TableColumn<StockColumns, Double> month2;
	@FXML
	private TableColumn<StockColumns, Double> month3;
	@FXML
	private TableColumn<StockColumns, Double> month4;
	@FXML
	private TableColumn<StockColumns, Double> month5;
	@FXML
	private TableColumn<StockColumns, Double> month6;
	@FXML
	private TableColumn<StockColumns, Double> month7;

	@FXML
	private Button btnGetSymbol;

	@FXML
	private TextField txtStockSymbol;

	@FXML
	private Text txtTimeZone;

	@FXML
	private void handleButtonAction(ActionEvent e) {

		// count the amount of time since the
		// last api request
		tEnd = System.currentTimeMillis();
		tDelta = tEnd - tStart;
		elapsedSeconds = tDelta / 1000.0;

		// gets the given stock symbol
		String symbol = txtStockSymbol.getText();
		// makes sure symbol is upper case
		symbol = symbol.toUpperCase();
		// reset the symbol to upper case
		txtStockSymbol.setText(symbol);

		// check if app is already searching and that time since last api request
		// isn't less than 60 sec
		if (!searching && elapsedSeconds > 60) {

			// reset the counter
			tStart = System.currentTimeMillis();
			// set the app to be searching
			searching = true;

			// check if the symbol is valid
			symbolValid = stock.isValid(symbol);
			if (symbolValid == 1) { // valid stock

				// get all the daily/weekly/monthly stock data
				dailyData = stock.getDaily(symbol);
				weeklyData = stock.getWeekly(symbol);
				monthlyData = stock.getMonthly(symbol);

				// check if all the requests were successful
				if (stock.allRequestsCompleted()) {

					// clear the tables
					dailyView.getItems().clear();
					weeklyView.getItems().clear();
					monthlyView.getItems().clear();

					// set the timezone
					txtTimeZone.setText("TimeZone: " + stock.getTimeZone());

					// build the row titles for the daily view
					rowTitle1.setCellValueFactory(new PropertyValueFactory<StockColumns, String>("column0"));

					// style the rows and add the text to the rows
					rowTitle1.setCellFactory(column -> {
						return new TableCell<StockColumns, String>() {
							@Override
							protected void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									switch (getIndex()) {
									case 0:
										setText("Open");
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setText("High");
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setText("Low");
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setText("Close");
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setText("Volume");
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										break;
									}
								}
							}
						};
					});

					// build the row titles for the weekly view
					rowTitle2.setCellValueFactory(new PropertyValueFactory<StockColumns, String>("column0"));

					// style the rows and add the text to the rows
					rowTitle2.setCellFactory(column -> {
						return new TableCell<StockColumns, String>() {
							@Override
							protected void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									switch (getIndex()) {
									case 0:
										setText("Open");
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setText("High");
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setText("Low");
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setText("Close");
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setText("Volume");
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										break;
									}
								}
							}
						};
					});

					// build the row titles for the monthly view
					rowTitle3.setCellValueFactory(new PropertyValueFactory<StockColumns, String>("column0"));

					// style the rows and add the text to the rows
					rowTitle3.setCellFactory(column -> {
						return new TableCell<StockColumns, String>() {
							@Override
							protected void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									switch (getIndex()) {
									case 0:
										setText("Open");
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setText("High");
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setText("Low");
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setText("Close");
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setText("Volume");
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										break;
									}
								}
							}
						};
					});

					// map the days to their respective columns
					day1.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column1"));
					day2.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column2"));
					day3.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column3"));
					day4.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column4"));
					day5.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column5"));
					day6.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column6"));
					day7.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column7"));

					// add the data to the columns using the StockColumns class
					for (int i = 0; i < 5; i++) {
						dailyView.getItems().add(new StockColumns("temp", dailyData[0][i], dailyData[1][i],
								dailyData[2][i], dailyData[3][i], dailyData[4][i], dailyData[5][i], dailyData[6][i]));
					}

					// style and clean up data
					day1.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					day2.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					day3.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					day4.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					day5.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					day6.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					day7.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					// map the weeks to their respective columns
					week1.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column1"));
					week2.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column2"));
					week3.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column3"));
					week4.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column4"));
					week5.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column5"));
					week6.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column6"));
					week7.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column7"));

					// add the data to the columns using the StockColumns class
					for (int i = 0; i < 5; i++) {
						weeklyView.getItems()
								.add(new StockColumns("temp", weeklyData[0][i], weeklyData[1][i], weeklyData[2][i],
										weeklyData[3][i], weeklyData[4][i], weeklyData[5][i], weeklyData[6][i]));
					}

					// style and clean up data
					week1.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow;");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					week2.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					week3.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					week4.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					week5.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					week6.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					week7.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					// map the months to their respective columns
					month1.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column1"));
					month2.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column2"));
					month3.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column3"));
					month4.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column4"));
					month5.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column5"));
					month6.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column6"));
					month7.setCellValueFactory(new PropertyValueFactory<StockColumns, Double>("column7"));

					// add the data to the columns using the StockColumns class
					for (int i = 0; i < 5; i++) {
						monthlyView.getItems()
								.add(new StockColumns("temp", monthlyData[0][i], monthlyData[1][i], monthlyData[2][i],
										monthlyData[3][i], monthlyData[4][i], monthlyData[5][i], monthlyData[6][i]));
					}

					// style and clean up data
					month1.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow;");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					month2.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow;");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					month3.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow;");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					month4.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow;");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					month5.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow;");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					month6.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow;");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					month7.setCellFactory(column -> {
						return new TableCell<StockColumns, Double>() {
							@Override
							protected void updateItem(Double item, boolean empty) {
								super.updateItem(item, empty);
								if (item == null || empty) {
									setText(null);
									setStyle("");
								} else {
									setText(Double.toString(item));
									switch (getIndex()) {
									case 0:
										setStyle("-fx-background-color: yellow;");
										break;
									case 1:
										setStyle("-fx-background-color: green");
										setTextFill(Color.WHITE);
										break;
									case 2:
										setStyle("-fx-background-color: red");
										setTextFill(Color.WHITE);
										break;
									case 3:
										setStyle("-fx-background-color: yellow");
										break;
									case 4:
										setStyle("-fx-background-color: blue");
										setTextFill(Color.WHITE);
										setText(String.format("%.0f", item));
										break;
									}
								}
							}
						};
					});

					// make sure the columns aren't sortable
					// since each column holds 5 differen't types of data
					day1.setSortable(false);
					day2.setSortable(false);
					day3.setSortable(false);
					day4.setSortable(false);
					day5.setSortable(false);
					day6.setSortable(false);
					day7.setSortable(false);

					week1.setSortable(false);
					week2.setSortable(false);
					week3.setSortable(false);
					week4.setSortable(false);
					week5.setSortable(false);
					week6.setSortable(false);
					week7.setSortable(false);

					month1.setSortable(false);
					month2.setSortable(false);
					month3.setSortable(false);
					month4.setSortable(false);
					month5.setSortable(false);
					month6.setSortable(false);
					month7.setSortable(false);

				} else {
					// all requests weren't completed
					// have user wait 1 min
					txtTimeZone.setText("All API Requests not completed due to limit\nLimit: 5 requests per min");
				}
				// searching completed
				searching = false;

			} else if (symbolValid == 2) {
				// validation failed check due to overload
				// have user wait 1 min
				txtTimeZone.setText("All API Requests not completed due to limit\nLimit: 5 requests per min");
				// searching completed
				searching = false;
			} else { // invalid stock
				// stock isn't valid
				txtTimeZone.setText("Invalid Stock Symbol!");
				// searching completed
				searching = false;
			}
		} else {
			// have user wait 1 min between each request
			// this prevents overload
			// display the time left
			txtTimeZone.setText(String.format("Must wait 1 minute inbetween requests\nTime Remaining: (%.0fs)",
					60 - elapsedSeconds));
		}

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

}