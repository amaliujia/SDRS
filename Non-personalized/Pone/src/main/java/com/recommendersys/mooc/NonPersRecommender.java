package com.recommendersys.mooc;
import org.grouplens.lenskit.baseline.BaselineScorer;
import org.grouplens.lenskit.core.LenskitConfiguration;
import org.grouplens.lenskit.data.dao.EventDAO;
import org.grouplens.lenskit.transform.normalize.BaselineSubtractingUserVectorNormalizer;

import com.recommendersys.mooc.Searching;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Hashtable;

/*
    36658
    22
    9741
 */

class NonPersRecommender {
    static int Movie_1 = 36658;
    static int Movie_2 = 22;
    static int Movie_3 = 9741;
    static String Movie_s_1 = "36658";
    static String Movie_s_2 = "22";
    static String Movie_s_3 = "9741";
    public static void main(String[] args) {
        ArrayList name = new ArrayList();
        ArrayList movies = new ArrayList();
        ArrayList ratings = new ArrayList();
        ArrayList UniqueMovie = new ArrayList();

    try {
        File csv = new File("recsys-data-ratings.csv");
        BufferedReader br = new BufferedReader(new FileReader(csv));
        String line="";
        int i = 1;
        while ((line = br.readLine())!=null){
            StringTokenizer  st = new StringTokenizer(line,",");
            while (st.hasMoreTokens()){
              if (i == 1){
                  name.add(st.nextToken());
                  i += 1;
              }else if(i ==2){
                 movies.add(st.nextToken());
                  i += 1;
              }else{
                  ratings.add(st.nextToken());
                  i = 1;
              }
            }
        }
        br.close();

        File csvc = new File("recsys-data-movie-titles.csv");
        BufferedReader brc = new BufferedReader(new FileReader(csvc));
        String linec="";
        int j = 1;
        while ((linec = brc.readLine())!=null){
            StringTokenizer  stc = new StringTokenizer(linec,",");
            while (stc.hasMoreTokens()){
                if (j == 1){
                    UniqueMovie.add(stc.nextToken());
                    j++;
                }else{
                    stc.nextToken();
                    j=1;
                }
            }
        }
        brc.close();
//////////////////////////////////////

//////////////////////////////////////


    }catch (Exception e) {
        System.out.println(e.getMessage());
    }
        AdvancedSearching s = new AdvancedSearching();
        //Searching s = new Searching();
        s.SingleFormuler(Movie_s_1,name,movies,ratings,UniqueMovie);
        s.SingleFormuler(Movie_s_2,name,movies,ratings,UniqueMovie);
        s.SingleFormuler(Movie_s_3,name,movies,ratings,UniqueMovie);
//    // Movies array contains the movie IDs of the top 5 movies.
//	int movies[] = new int[5];
//
//	// Write the top 5 movies, one per line, to a text file.
//	try {
//	    PrintWriter writer = new PrintWriter("pa1-result.txt","UTF-8");
//
//	    for (int movieId : movies) {
//		writer.println(movieId);
//	    }
//
//	    writer.close();
//
//	} catch (Exception e) {
//	    System.out.println(e.getMessage());
//	}
   }



}