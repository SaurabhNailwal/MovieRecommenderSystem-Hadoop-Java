Movie Recommender System Using Item-based Collaborative filtering
-----------------------------------------------------------------
I have used item-based collaboratie filtering for making an efficient movie recommender system for users based on available movie rating data(MovieLens Data).

The MapReduce program will give us similarity matrix(used cosine similarity for calculation) and 


Now you will have to run the 'MovieRecommender.java' file to get the top k movies 

======================================================================================================================================================


Changes required before running:


(i) You need to store the reducer1 output into a file named as 'CIdAndMratings.txt'.


(ii) You need to store the reducer2 output into a file named as 'Similarity.txt'.


(iii) Store both files at a common location. You need to provide the path where you store these files in variable 'dataPath'.


(iv) After doing all this, run the 'MovieRecommender.java' to get the recommendations for customers.
