package com.tigerit.exam;

import static com.tigerit.exam.IO.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * All of your application logic should be placed inside this class. Remember we
 * will load your application from our custom container. You may add private
 * method inside this class but, make sure your application's execution points
 * start from inside run method.
 */
public class Solution implements Runnable {

    @Override
    public void run() {
        // your application entry point

        // sample input process
        // String string = readLine();
        //Integer integer = readLineAsInteger();
        int test_case = readLineAsInteger();
        if (test_case < 1) {
            throw new IllegalArgumentException("can not give negative value! ");//test case can not be less than one
        }
        if (test_case > 10) {
            throw new IllegalArgumentException("number should be less or equal to 10");//test case can not be more than 10
        }
        Integer table_num = 0;
        String[][] table_name = new String[100][100];
        int colNum = 0;
        int rowNum = 0;
        int length = 0;
        String[][] sentence = new String[100][100];
        String[] word = new String[100];
        String[][][] colName = new String[100][100][100];
        double[][][][] data = new double[10][10][10][10];
        double[][][][] new_data = new double[10][10][10][10];
        String[][][] dum_column = new String[100][100][100];
        int queryNum = 0;
        String[][] query = new String[100][100];
        StringBuilder[][] dum_sentence = new StringBuilder[100][100];
        String[][][] dum_data = new String[100][100][100];
	    String[] numbers = new String[100];
        for (int a = 1; a <= test_case; a++) {

            table_num = readLineAsInteger();//table number
            if (table_num < 2) {
                throw new IllegalArgumentException("It can't be 1");//table number can not be 1 or less
            }
            if (table_num > 10) {
                throw new IllegalArgumentException("table number should be less or equal to 10"); //table number can not be more than 10
            }
            Pattern pattern = Pattern.compile("^[a-z][a-z0-9]*(?:_[a-z0-9]+)*$");//regular expression for checking table and column name;
          
            for (int i = 0; i < table_num; i++) {
                table_name[a][i] = readLine();
                Matcher matcher = pattern.matcher(table_name[a][i]);
                if (!matcher.find()) {
                    throw new IllegalArgumentException("lower case,numbers or _ are allowed");//only lower case,number and _ allowed
                }
                String input = readLine();
                numbers = input.split(" ");
                colNum = Integer.parseInt(numbers[0]);//take column number
                rowNum = Integer.parseInt(numbers[1]);//take row number

                if (colNum < 2) {
                    throw new IllegalArgumentException("more than 2 need");
                }
                if (rowNum > 100) {
                    throw new IllegalArgumentException("data can't be more than 100");
                }

                String c_input = readLine();
                colName[a][i] = c_input.split(" ");
                for (int x = 0; x < colNum; x++) {
                    Matcher match = pattern.matcher(colName[a][i][x]);
                    if (!match.find()) {
                        throw new IllegalArgumentException("lower case,numbers or _ are allowed");//only lower case,number and _ allowed
                    }
                }

                for (int j = 0; j < rowNum; j++) {
                    for (int k = 0; k < colNum / colNum; k++) {
                        dum_data[a][j][k] = readLine();
                        String[] split_data = dum_data[a][j][k].split(" ");
                        for (int y = 0; y < colNum; y++) {
                            data[a][i][j][y] = Double.parseDouble(split_data[y]);
                            if (data[a][i][j][y] < 0 && data[a][i][j][y] > 100000) {
                                throw new IllegalArgumentException("Can not be less than zero or more than 1");
                            }

                        }

                    }

                }

            }

            queryNum = readLineAsInteger();
            for (int b = 0; b < queryNum; b++) {

                dum_sentence[a][b] = new StringBuilder();
                for (int c = 0; c < 4; c++) {
                    query[b][c] = readLine();
                    dum_sentence[a][b].append(query[b][c]);
                    dum_sentence[a][b].append(" ");

                }

                sentence[a][b] = dum_sentence[a][b].toString();
                System.out.print("\n");
            }

        }

        for (int a = 1; a <= test_case; a++) {
            System.out.printf("Test:%d\n", a);

            for (int b = 0; b < queryNum; b++) {

                word = sentence[a][b].split(" ");
                for (int w = 0; w < word.length; w++) {
                    if (word[1].equals("*")) {
                        for (int i = 0; i < table_num; i++) {

                            if (word[w].equals(table_name[a][i])) {
                                for (int col = 0; col < colNum; col++) {
                                    System.out.printf("%s ", colName[a][i][col]);
                                }
                            }
                        }

                    } else {
                        if (word[w].equals("FROM")|| word[w].equals("from")) {
                        break;
                    } else if (word[w].indexOf(".") != -1) {
                        String[] dum_word = word[w].split("[.,]");
                        for (int k = 0; k < dum_word.length; k++) {
                            for (int y = 0; y < word.length; y++) {
                                for (int i = 0; i < table_num; i++) {
                                    if (word[y].equals(table_name[a][i])) {
                                        if (dum_word[k].equals(word[y + 1])) {
                                            for (int j = 0; j < colNum; j++) {
                                                if (colName[a][i][j].equals(dum_word[k + 1])) {
                                                    System.out.printf("%s ", colName[a][i][j]);
                                                    dum_column[a][i][j] = colName[a][i][j];
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
                }
                System.out.print("\n");
             
              
                for (int w = 0; w < word.length; w++) {
                    if(word[w].equals("=")){
                        String[] word1 = word[w-1].split("[.]");
                        String[] word2 = word[w+1].split("[.]");
                      
                     //   if(word[1].equals("*")){
                        for(int tab1 = 0; tab1<table_num; tab1++){
                            if(table_name[a][tab1].equals(word1[0])){
                                for(int col1=0; col1<colNum; col1++){
                                    if(colName[a][tab1][col1].equals(word1[1])){
                                        for(int tab2=0; tab2<table_num; tab2++){
                                            if(table_name[a][tab2].equals(word2[0])){
                                                for(int col2=0; col2<colNum; col2++){
                                                    if(colName[a][tab2][col2].equals(word2[1])){
                                                        for(int r1=0; r1<rowNum; r1++){
                                                           for(int r2=0; r2<rowNum; r2++){
                                                                if(data[a][tab1][r1][col1] == data[a][tab2][r2][col2]){
                                                                   for(int col3=0; col3<colNum; col3++){
                                                                       System.out.printf("%.0f ", data[a][tab1][r1][col3]);
                                                                   }
                                                                   for(int col3=0; col3<colNum; col3++){
                                                                        System.out.printf("%.0f ", data[a][tab2][r2][col3]);
                                                                   }
                                                                }
                                                            }
                                                        }System.out.print("\n");
                                                    }
                                                   }
                                                }
                                            }
                                        
                                    }
                                }
                            }
                        } // for normal query without nickname
                        for(int w1=0; w1<word.length; w1++){
                          if(word[w1].equals(word1[0])){
                             for(int tab1=0; tab1<table_num; tab1++) {
                                if(table_name[a][tab1].equals(word[w1-1])){
                                  for(int col1=0; col1<colNum; col1++){
                                    if(colName[a][tab1][col1].equals(word1[1])){
                                     for(int w2=0; w2<word.length; w2++){
                                       if(word[w2].equals(word2[0])){
                                        for(int tab2=0; tab2<table_num; tab2++){
                                            if(table_name[a][tab2].equals(word[w2-1])){
                                                for(int col2=0; col2<colNum; col2++){
                                                    if(colName[a][tab2][col2].equals(word2[1])){
                                                        for(int r1=0; r1<rowNum; r1++){
                                                           for(int r2=0; r2<rowNum; r2++){
                                                                if(data[a][tab1][r1][col1] == data[a][tab2][r2][col2]){
                                                                   for(int col3=0; col3<colNum; col3++){
                                                                       if(word[1].equals("*")){
                                                                            System.out.printf("%.0f ", data[a][tab1][r1][col3]);
                                                                       }else{
                                                                            if(colName[a][tab1][col3].equals(dum_column[a][tab1][col3]))
                                                                             System.out.printf("%.0f ", data[a][tab1][r1][col3]);
                                                                       }
                                                                      
                                                                   }
                                                                   for(int col3=0; col3<colNum; col3++){
                                                                       if(word[1].equals("*")){
                                                                        System.out.printf("%.0f ", data[a][tab2][r2][col3]);
                                                                       }else{
                                                                           if(colName[a][tab2][col3].equals(dum_column[a][tab2][col3]))
                                                                           System.out.printf("%.0f ", data[a][tab2][r2][col3]);
                                                                       }
                                                                   }
                                                                }
                                                            }System.out.print("\n");
                                                        }
                                                    }
                                                  }
                                               }
                                           }
                                       }
                                     }
                                   }
                                }
                              }
                            }
                         }
                        }//print table with nick name
                    
                  }
                }  System.out.print("\n");
            }
            System.out.print("\n");

        }
        // sample output process
        // printLine(string);
        // printLine(integer);
    }
}
