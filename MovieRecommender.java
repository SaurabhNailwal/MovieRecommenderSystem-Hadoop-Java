/*
 * @author Saurabh Nailwal
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MovieRecommender {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int k = 0, customerId = 0;
		String dataPath = "/home/saurabh/MovieRecommenderData/";

		// Get the input K value(1-100) and customer id(1-943) from user
		Scanner input = new Scanner(System.in);

		// To get a valid k value
		do {
			try {
				System.out
						.println("Please enter the number of top movies to print(upto 100): ");
				k = input.nextInt();
				if (k > 100 || k == 0) {
					System.out.println("Error: Enter a number from 1 to 100");
				}

			} catch (InputMismatchException e) {
				System.out.println("Error: Enter a valid number upto 100");
			}
			input.nextLine();

		} while (k < 1 || k > 100);

		// To get a valid Customer Id value

		do {
			try {

				System.out
						.println("Please enter Customer Id(between 1 and 943): ");
				customerId = input.nextInt();

				if (customerId > 943 || customerId == 0) {
					System.out
							.println("Error: Enter a Customer Id between 1 to 943");
				}

			} catch (InputMismatchException e) {
				System.out.println("Error: Enter a valid Customer Id ");
			}

			input.nextLine();

		} while (customerId > 943 || customerId < 1);

		input.close();

		// Get all the customer provided movie and ratings list from file
		// 'CIdAndMratings'

		FileReader movieFile = null;
		BufferedReader bufferedReader = null;
		String line = "";
		String[] split = null;
		HashMap<String, String> custMovieRatings = new HashMap<String, String>();

		try {

			movieFile = new FileReader(dataPath + "CIdAndMratings.txt");
			bufferedReader = new BufferedReader(movieFile);

			while ((line = bufferedReader.readLine()) != null) {
				split = line.split("\t");
				custMovieRatings.put(split[0], split[1]);
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (movieFile != null) {
				try {
					movieFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Find movies that were not rated for the customer Id

		HashMap<String, String> ratedMovies = new HashMap<String, String>();
		String custMovieRating = custMovieRatings.get(String
				.valueOf(customerId));

		String[] splitMRating = custMovieRating.split(" ");
		String[] splitMR = null;

		for (int i = 0; i < splitMRating.length; i++) {

			splitMR = splitMRating[i].split(",");
			ratedMovies.put(splitMR[0], splitMR[1]);

		}

		ArrayList<Integer> notRated = new ArrayList<Integer>();

		for (int j = 1; j <= 1682; j++) {
			if (ratedMovies.get(String.valueOf(j)) == null) {
				notRated.add(j);
			}
		}

		// Create the matrix for storing similarity from file 'Similarity'

		FileReader similarityFile = null;
		BufferedReader bufferedReaderSim = null;
		String lineSim = "";
		String[] splitSim = null;
		String[] moviesSplit =null;

		double[][] similarityMatrix = new double[1682][1682];
		int movie1 = 0, movie2 = 0;
		try {

			similarityFile = new FileReader(dataPath + "Similarity.txt");
			bufferedReader = new BufferedReader(similarityFile);

			while ((lineSim = bufferedReader.readLine()) != null) {
				splitSim = lineSim.split("\t");

				moviesSplit = splitSim[0].split(",");
				movie1 = Integer.parseInt(moviesSplit[0]);
				movie2 = Integer.parseInt(moviesSplit[1]);
	
				similarityMatrix[movie1 - 1][movie2 - 1] = Double
						.valueOf(splitSim[1]);

			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			if (bufferedReaderSim != null) {
				try {
					bufferedReaderSim.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (similarityFile != null) {
				try {
					similarityFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Calculate the Prediction for movies that were not rated by customer
		double simValue = 0.0, p1 = 0.0, p2 = 0.0, prediction = 0.0;
		HashMap<Integer, Double> moviePrediction = new HashMap<Integer, Double>();

		for (int value : notRated) {

			for (String rated : ratedMovies.keySet()) {

				simValue = similarityMatrix[value - 1][Integer.parseInt(rated) - 1];
				p1 = p1 + simValue * Double.valueOf(ratedMovies.get(rated));
				p2 = p2 + simValue;

			}

			if (p1 != 0.0) {
				prediction = p1 / p2;
			}

			// Putting the non-rated movie and its prediction in map
			moviePrediction.put(value, prediction);

		}

		// Sort the Predictions and give the top k movies for customer Id

		List<Map.Entry<Integer, Double>> movPred = new LinkedList<Map.Entry<Integer, Double>>(
				moviePrediction.entrySet());
		Collections.sort(movPred, new Comparator<Map.Entry<Integer, Double>>() {

			public int compare(Map.Entry<Integer, Double> map1,
					Map.Entry<Integer, Double> map2) {

				return (map2.getValue()).compareTo(map1.getValue());
			}

		});

		// Printing the first k movies 
	
		int counter = 1;
		
		System.out.println("\n The top "+ k +" movies that are recommended for the customer "+ customerId+" are: \n");
	
		for(Iterator<Map.Entry<Integer, Double>> iterator = movPred.iterator();iterator.hasNext();) {
		 
		  Map.Entry<Integer, Double> entry = iterator.next();
		  
		  System.out.println("Movie # "+entry.getKey()+ " "+ entry.getValue());
		  
		  if(counter == k){
			  break; 
		  }
		  
		  counter++;
		 }
		 

		
	}
}
