
import java.util.Arrays;

import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;
import de.daslaboratorium.machinelearning.classifier.core.Classifier;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RunnableExample {

    final static Classifier<String, String> bayes = new BayesClassifier<String, String>();
    private static String path;
    
    
    private static void train() {
       ArrayList<String> giangVien = new ArrayList<>();
       BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("training.giangvien.txt"));
            String line;
            while ((line = br.readLine() )!= null){
                 String []strArray = line.split("\\s");
                 for(int i=0;i<strArray.length;i++){
                     giangVien.add(strArray[i]);
                 }
                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RunnableExample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RunnableExample.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
           try {
               br.close();
           } catch (IOException ex) {
               Logger.getLogger(RunnableExample.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        
        ArrayList<String> daoTao = new ArrayList<>();
       //BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("training.daotao.txt"));
            String line;
            while ((line = br.readLine() )!= null){
                 String []strArray = line.split("\\s");
                 for(int i=0;i<strArray.length;i++){
                     daoTao.add(strArray[i]);
                 }
                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RunnableExample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RunnableExample.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
           try {
               br.close();
           } catch (IOException ex) {
               Logger.getLogger(RunnableExample.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        
        ArrayList<String>vatChat = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader("training.vatchat.txt"));
            String line;
            while ((line = br.readLine() )!= null){
                 String []strArray = line.split("\\s");
                 for(int i=0;i<strArray.length;i++){
                     vatChat.add(strArray[i]);
                 }
                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RunnableExample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RunnableExample.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
           try {
               br.close();
           } catch (IOException ex) {
               Logger.getLogger(RunnableExample.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        
        ArrayList<String>other = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader("training.other.txt"));
            String line;
            while ((line = br.readLine() )!= null){
                 String []strArray = line.split("\\s");
                 for(int i=0;i<strArray.length;i++){
                     other.add(strArray[i]);
                 }
                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RunnableExample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RunnableExample.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
           try {
               br.close();
           } catch (IOException ex) {
               Logger.getLogger(RunnableExample.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        bayes.learn("Đào tạo", daoTao);
        
        bayes.learn("Giảng viên", giangVien);
        
        bayes.learn("Vật Chật", daoTao);
        
        bayes.learn("Other", daoTao);
    }

    public static void main(String[] args) {

        /*
         * Create a new classifier instance. The context features are
         * Strings and the context will be classified with a String according
         * to the featureset of the context.
         */
 /*
         * The classifier can learn from classifications that are handed over
         * to the learn methods. Imagin a tokenized text as follows. The tokens
         * are the text's features. The category of the text will either be
         * positive or negative.
         */
        train();

        /*
         * Now that the classifier has "learned" two classifications, it will
         * be able to classify similar sentences. The classify method returns
         * a Classification Object, that contains the given featureset,
         * classification probability and resulting category.
         */
        final String[] unknownText1 = "buổi học cuối cùng trước đợt thi cuối kỳ".split("\\s");
        final String[] unknownText2 = "môn học chỉ nên có 1 giảng viên đứng lớp".split("\\s");
         final String[] unknownText3 = "cung cấp không thể sử dụng để thực hành".split("\\s");

        System.out.println( // will output "positive"
                bayes.classify(Arrays.asList(unknownText1)).getCategory());
        System.out.println( // will output "negative"
                bayes.classify(Arrays.asList(unknownText2)).getCategory());
        
        System.out.println( // will output "negative"
                bayes.classify(Arrays.asList(unknownText3)).getCategory());

        /*
         * The BayesClassifier extends the abstract Classifier and provides
         * detailed classification results that can be retrieved by calling
         * the classifyDetailed Method.
         *
         * The classification with the highest probability is the resulting
         * classification. The returned List will look like this.
         * [
         *   Classification [
         *     category=negative,
         *     probability=0.0078125,
         *     featureset=[today, is, a, sunny, day]
         *   ],
         *   Classification [
         *     category=positive,
         *     probability=0.0234375,
         *     featureset=[today, is, a, sunny, day]
         *   ]
         * ]
         */
        ((BayesClassifier<String, String>) bayes).classifyDetailed(
                Arrays.asList(unknownText1));

        /*
         * Please note, that this particular classifier implementation will
         * "forget" learned classifications after a few learning sessions. The
         * number of learning sessions it will record can be set as follows:
         */
        bayes.setMemoryCapacity(500); // remember the last 500 learned classifications
    }

}
