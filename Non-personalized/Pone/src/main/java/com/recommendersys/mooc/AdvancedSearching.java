package com.recommendersys.mooc;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Hashtable;
import java.util.*;
import java.math.*;
class AdvancedSearching{
    public void SingleFormuler(String aMoiveID,ArrayList name,ArrayList movies,ArrayList ratings,ArrayList UniqueMovie){
        ArrayList conjunction = new ArrayList();
        ArrayList disjunction = new ArrayList();

        int size = name.size();
        for (int i =0; i<size;i++){
            if (movies.get(i).equals(aMoiveID)){
                conjunction.add(name.get(i));
            }else{
                disjunction.add(name.get(i));
            }
        }
        Hashtable result = new Hashtable();
        int size_2 = conjunction.size();
        int size_3 = disjunction.size();
        int count = 0;
        int discount=0;
        int loops = UniqueMovie.size();
        for(int i=0;i<loops;i++){
            for(int j=0;j<size;j++){
                if (movies.get(j).equals(UniqueMovie.get(i))){
//                   float aRating = ratings.get(j);
                    String temp = name.get(j).toString();
                    int index = conjunction.indexOf(temp);
                    if (index != -1){
                        count ++;
                    }else{
                        discount++;
                    }
                }
            }

            //  System.out.println(size_2 + " " + count + " " +l1);
            float l =  count;
            float l1 = l/size_2;
            float t =  size_3 + discount;
            float t1 = t/size_3;
            float qqpp = l1/t1;
            result.put(UniqueMovie.get(i),qqpp);
            count = 0;
            discount = 0;
        }
        //System.out.println("Pass Through!");
        try {
            File csv = new File("AdvancedFormula.csv"); // CSV文件
            // 追记模式
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
            // 新增一行数据
            bw.newLine();
            bw.write(aMoiveID);
            int a = 0;
            for (Iterator it = result.keySet().iterator(); it.hasNext();){
                String key = (String)it.next();
                Object value = result.get(key);
                float p = Float.parseFloat(value.toString());

                BigDecimal b = new BigDecimal(p);
                float f1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();

                bw.write(","+key + ","+f1);
                if (a==5){
                    break;
                }else{
                    a+=1;
                }
            }



            bw.close();
        } catch (FileNotFoundException e) {
            // 捕获File对象生成时的异常
            e.printStackTrace();
        } catch (IOException e) {
            // 捕获BufferedWriter对象关闭时的异常
            e.printStackTrace();
        }

    }


}