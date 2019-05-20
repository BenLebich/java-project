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
	int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	String timeZone;
	private boolean allReqCompleted = true;

	private JsonElement getData(String range, String symbol) {

		JsonElement returnData = null;
		String reqURL = String.format(avAPI, range, symbol, avAPIKey);

		try {
			// Construct API URL
			System.out.println(reqURL);
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

		catch (java.io.UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (java.net.MalformedURLException mue) {
			mue.printStackTrace();
		} catch (java.io.IOException ioe) {
			System.out.println("ERROR: No cities match your search query");
		}

		return returnData;

	}

	public int isValid(String symbol) {
		JsonObject metaData;
		JsonElement dailyCheck = getData("TIME_SERIES_DAILY", symbol);
		if (dailyCheck.getAsJsonObject().has("Meta Data")) {
			System.out.println("Is Valid");
			metaData = dailyCheck.getAsJsonObject().get("Meta Data").getAsJsonObject();
			timeZone = metaData.getAsJsonObject().get("5. Time Zone").getAsString();
			return 1; // is valid
		} else if (dailyCheck.getAsJsonObject().has("Note")) {
			return 2; // overload
		} else {
			System.out.println("Isn't Valid");
			return 0; // not valid
		}

	}

	public String getTimeZone() {
		return timeZone;
	}

	public double[][] getDaily(String symbol) {

		JsonObject allDays;
		JsonObject currentDay;
		double[][] dailyFormated = new double[7][5];
		JsonElement dailyData = getData("TIME_SERIES_DAILY", symbol);

		if (dailyData.getAsJsonObject().has("Time Series (Daily)")) {
			allDays = dailyData.getAsJsonObject().get("Time Series (Daily)").getAsJsonObject();
			Collection<String> days = allDays.entrySet().stream().map(i -> i.getKey())
					.collect(Collectors.toCollection(ArrayList::new));

			for (int i = 0; i < 7; i++) {
				currentDay = allDays.getAsJsonObject().get((String) days.toArray()[i]).getAsJsonObject();
				dailyFormated[i][0] = currentDay.getAsJsonObject().get("1. open").getAsDouble();
				dailyFormated[i][1] = currentDay.getAsJsonObject().get("2. high").getAsDouble();
				dailyFormated[i][2] = currentDay.getAsJsonObject().get("3. low").getAsDouble();
				dailyFormated[i][3] = currentDay.getAsJsonObject().get("4. close").getAsDouble();
				dailyFormated[i][4] = currentDay.getAsJsonObject().get("5. volume").getAsDouble();
			}
		} else {
			allReqCompleted = false;
		}

		return dailyFormated;
	}

	public double[][] getWeekly(String symbol) {

		JsonObject allWeeks;
		JsonObject currentWeek;
		double[][] weeklyFormated = new double[7][5];
		JsonElement weeklyData = getData("TIME_SERIES_WEEKLY", symbol);

		if (weeklyData.getAsJsonObject().has("Weekly Time Series")) {
			allWeeks = weeklyData.getAsJsonObject().get("Weekly Time Series").getAsJsonObject();
			Collection<String> weeks = allWeeks.entrySet().stream().map(i -> i.getKey())
					.collect(Collectors.toCollection(ArrayList::new));

			for (int i = 0; i < 7; i++) {
				currentWeek = allWeeks.getAsJsonObject().get((String) weeks.toArray()[i]).getAsJsonObject();
				weeklyFormated[i][0] = currentWeek.getAsJsonObject().get("1. open").getAsDouble();
				weeklyFormated[i][1] = currentWeek.getAsJsonObject().get("2. high").getAsDouble();
				weeklyFormated[i][2] = currentWeek.getAsJsonObject().get("3. low").getAsDouble();
				weeklyFormated[i][3] = currentWeek.getAsJsonObject().get("4. close").getAsDouble();
				weeklyFormated[i][4] = currentWeek.getAsJsonObject().get("5. volume").getAsDouble();
			}
		} else {
			allReqCompleted = false;
		}

		return weeklyFormated;

	}

	public double[][] getMonthly(String symbol) {

		JsonObject allMonths;
		JsonObject currentMonth;
		double[][] monthlyFormated = new double[7][5];
		JsonElement monthlyData = getData("TIME_SERIES_MONTHLY", symbol);

		if (monthlyData.getAsJsonObject().has("Monthly Time Series")) {
			allMonths = monthlyData.getAsJsonObject().get("Monthly Time Series").getAsJsonObject();
			Collection<String> weeks = allMonths.entrySet().stream().map(i -> i.getKey())
					.collect(Collectors.toCollection(ArrayList::new));

			for (int i = 0; i < 7; i++) {
				currentMonth = allMonths.getAsJsonObject().get((String) weeks.toArray()[i]).getAsJsonObject();
				monthlyFormated[i][0] = currentMonth.getAsJsonObject().get("1. open").getAsDouble();
				monthlyFormated[i][1] = currentMonth.getAsJsonObject().get("2. high").getAsDouble();
				monthlyFormated[i][2] = currentMonth.getAsJsonObject().get("3. low").getAsDouble();
				monthlyFormated[i][3] = currentMonth.getAsJsonObject().get("4. close").getAsDouble();
				monthlyFormated[i][4] = currentMonth.getAsJsonObject().get("5. volume").getAsDouble();
			}
		} else {
			allReqCompleted = false;
		}
		return monthlyFormated;

	}

	public boolean allRequestsCompleted() {
		return allReqCompleted;
	}

}
