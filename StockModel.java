import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class StockModel {

	// API template, fill in with date-range/symbol/key
	String avAPI = "https://www.alphavantage.co/query?function=%s&symbol=%s&apikey=%s";
	String avAPIKey = "5346GI8QPJABV5HE";

	// private vars used throughout the methods
	private String timeZone;
	private boolean allReqCompleted = true;

	// main method for making api req to alphavantage
	private JsonElement getData(String range, String symbol) {

		// init return var
		JsonElement returnData = null;
		// build the api url
		String reqURL = String.format(avAPI, range, symbol, avAPIKey);

		try {

			// construct the req url
			URL owmURL = new URL(reqURL);

			// Open the URL
			InputStream is = owmURL.openStream(); // throws an IOException
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			// Read the result into a JSON Element
			returnData = new JsonParser().parse(br);

			// Close the connection
			is.close();
			br.close();
		}

		// catch any exceptions
		catch (java.io.UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (java.net.MalformedURLException mue) {
			mue.printStackTrace();
		} catch (java.io.IOException ioe) {
			ioe.printStackTrace();
		}

		return returnData;

	}

	// main method for checking if a symbol is valid
	// returns 0,1,2 for either not-valid,valid,overload respectively
	// overload = wait 1 min for 5 more requests
	public int isValid(String symbol) {

		// init return var
		JsonObject metaData;
		// get daily data for given symbol
		JsonElement dailyCheck = getData("TIME_SERIES_DAILY", symbol);

		// check if the data has either Meta Deta, Note or neither
		if (dailyCheck.getAsJsonObject().has("Meta Data")) {
			// store time zone to use for later
			metaData = dailyCheck.getAsJsonObject().get("Meta Data").getAsJsonObject();
			timeZone = metaData.getAsJsonObject().get("5. Time Zone").getAsString();
			return 1; // is valid
		} else if (dailyCheck.getAsJsonObject().has("Note")) {
			return 2; // overload
		} else {
			return 0; // not valid
		}

	}

	// returns private var
	// must have done a validation check before getting this
	public String getTimeZone() {
		return timeZone;
	}

	// returns daily data formated in 2d array
	// 1st dimension: days
	// second dimension: open, high, low, close, volume
	public double[][] getDaily(String symbol) {

		// init vars
		JsonObject allDays;
		JsonObject currentDay;
		double[][] dailyFormated = new double[7][5];

		// get daily data
		JsonElement dailyData = getData("TIME_SERIES_DAILY", symbol);

		// check if api request was completed
		if (dailyData.getAsJsonObject().has("Time Series (Daily)")) {

			allDays = dailyData.getAsJsonObject().get("Time Series (Daily)").getAsJsonObject();

			// get all the object keys in the Time series
			Collection<String> days = allDays.entrySet().stream().map(i -> i.getKey())
					.collect(Collectors.toCollection(ArrayList::new));

			// use the keys to get the last 7 days
			for (int i = 0; i < 7; i++) {

				// build the 2d array
				currentDay = allDays.getAsJsonObject().get((String) days.toArray()[i]).getAsJsonObject();
				dailyFormated[i][0] = currentDay.getAsJsonObject().get("1. open").getAsDouble();
				dailyFormated[i][1] = currentDay.getAsJsonObject().get("2. high").getAsDouble();
				dailyFormated[i][2] = currentDay.getAsJsonObject().get("3. low").getAsDouble();
				dailyFormated[i][3] = currentDay.getAsJsonObject().get("4. close").getAsDouble();
				dailyFormated[i][4] = currentDay.getAsJsonObject().get("5. volume").getAsDouble();
			}
		} else {
			// if api request wasn't completed then
			// we have to wait 1 min for 5 new requests
			allReqCompleted = false;
		}

		// return 2d array
		return dailyFormated;
	}

	public double[][] getWeekly(String symbol) {

		// init vars
		JsonObject allWeeks;
		JsonObject currentWeek;
		double[][] weeklyFormated = new double[7][5];

		// get weekly data
		JsonElement weeklyData = getData("TIME_SERIES_WEEKLY", symbol);

		// check if api request was completed
		if (weeklyData.getAsJsonObject().has("Weekly Time Series")) {

			// get all the object keys in the Time series
			allWeeks = weeklyData.getAsJsonObject().get("Weekly Time Series").getAsJsonObject();
			Collection<String> weeks = allWeeks.entrySet().stream().map(i -> i.getKey())
					.collect(Collectors.toCollection(ArrayList::new));

			// use the keys to get the last 7 weeks
			for (int i = 0; i < 7; i++) {

				// build the 2d array
				currentWeek = allWeeks.getAsJsonObject().get((String) weeks.toArray()[i]).getAsJsonObject();
				weeklyFormated[i][0] = currentWeek.getAsJsonObject().get("1. open").getAsDouble();
				weeklyFormated[i][1] = currentWeek.getAsJsonObject().get("2. high").getAsDouble();
				weeklyFormated[i][2] = currentWeek.getAsJsonObject().get("3. low").getAsDouble();
				weeklyFormated[i][3] = currentWeek.getAsJsonObject().get("4. close").getAsDouble();
				weeklyFormated[i][4] = currentWeek.getAsJsonObject().get("5. volume").getAsDouble();
			}
		} else {
			// if api request wasn't completed then
			// we have to wait 1 min for 5 new requests
			allReqCompleted = false;
		}

		// return 2d array
		return weeklyFormated;

	}

	public double[][] getMonthly(String symbol) {

		// init vars
		JsonObject allMonths;
		JsonObject currentMonth;
		double[][] monthlyFormated = new double[7][5];

		// get monthly data
		JsonElement monthlyData = getData("TIME_SERIES_MONTHLY", symbol);

		// check if api request was completed
		if (monthlyData.getAsJsonObject().has("Monthly Time Series")) {

			// get all the object keys in the Time series
			allMonths = monthlyData.getAsJsonObject().get("Monthly Time Series").getAsJsonObject();
			Collection<String> weeks = allMonths.entrySet().stream().map(i -> i.getKey())
					.collect(Collectors.toCollection(ArrayList::new));

			// use the keys to get the last 7 weeks
			for (int i = 0; i < 7; i++) {

				// build the 2d array
				currentMonth = allMonths.getAsJsonObject().get((String) weeks.toArray()[i]).getAsJsonObject();
				monthlyFormated[i][0] = currentMonth.getAsJsonObject().get("1. open").getAsDouble();
				monthlyFormated[i][1] = currentMonth.getAsJsonObject().get("2. high").getAsDouble();
				monthlyFormated[i][2] = currentMonth.getAsJsonObject().get("3. low").getAsDouble();
				monthlyFormated[i][3] = currentMonth.getAsJsonObject().get("4. close").getAsDouble();
				monthlyFormated[i][4] = currentMonth.getAsJsonObject().get("5. volume").getAsDouble();
			}
		} else {

			// if api request wasn't completed then
			// we have to wait 1 min for 5 new requests
			allReqCompleted = false;
		}

		// return 2d array
		return monthlyFormated;

	}

	// used to check if all requests were completed successfully
	public boolean allRequestsCompleted() {
		return allReqCompleted;
	}

}
