//package com.company;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RakeMain {

    private static final String REGEX = "[a-z]";
    private static final String INPUT = "aaa";
    public static String pdfParser(String filepath) throws IOException,TikaException {

        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(new File(filepath));
        ParseContext pcontext = new ParseContext();

        //parsing the document using PDF parser
        PDFParser pdfparser = new PDFParser();
        try {
            pdfparser.parse(inputstream, handler, metadata, pcontext);
        }catch (Exception e){

        }
        //getting the content of the document
        //System.out.println("Contents of the PDF :" + handler.toString());
        String pdfText = handler.toString();

        //getting metadata of the document
        /*System.out.println("Metadata of the PDF:");
        String[] metadataNames = metadata.names();

        for(String name : metadataNames) {
            System.out.println(name+ " : " + metadata.get(name));
        }*/
        return pdfText;
    }

    public <K, V extends Comparable<V>> V maxUsingCollectionsMax(Map<K, V> map) {
        Map.Entry<K, V> maxEntry = Collections.max(map.entrySet(), new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                return e1.getValue()
                        .compareTo(e2.getValue());
            }
        });
        return maxEntry.getValue();
    }

    public List<String> keyword_extractor(String my_path){
        //RakeMain mainObject = new RakeMain();
        String path = my_path;
        String pdfText = "";
        try {
            pdfText = pdfParser(path);
        } catch (Exception e) {

        }

        //splitting on paragraphs
        String splittedText_Array[] = pdfText.split("\\n\\n");


        String languageCode = RakeLanguages.ENGLISH;
        Rake rake = new Rake(languageCode);
/*        StringBuilder sb = new StringBuilder(" ");
        try {
            String line = "";
            BufferedReader br = null;
            InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/inputText 2.txt");
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);

                //pureNoun.put(line, line);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        String text = "We operate a change of paradigm and hy- pothesize that keywords are more likely to be found among influential nodes of a graph-of- words rather than among its nodes high on eigenvector-related centrality measures. To test this hypothesis, we introduce unsuper- vised techniques that capitalize on graph de- generacy. Our methods strongly and sig- nificantly outperform all baselines on two datasets (short and medium size documents), and reach best performance on the third one (long documents).";
        List <String> final_keyphrases = new ArrayList<>();
        //Pattern p = Pattern.compile("\\w+");
        int counter1 = 0;

        /*String input = "2";
        Pattern p = Pattern.compile("[a-z]+");
        Pattern p1 = Pattern.compile("[a-zA-Z]");
        Matcher m1 = p.matcher(input);
        Matcher m2= p1.matcher(input);
        if (m1.find() || m2.find()) {
            counter1 ++;
        }*/
        //Pattern p = Pattern.compile("(^[A-Za-z]+$)");
        int counter = 0;
        for (String part:splittedText_Array) {
            counter = 0;
            //String[] splittedSentence_Array = part.split("[^a-zA-Z0-9_\\\\+/-\\\\]");
            String[] splittedSentence_Array = part.split("[\\s\\.\\n\\-]");
            for (String word : splittedSentence_Array) {
                word = word.trim();
                //Pattern p = Pattern.compile("\\W");
                Pattern p = Pattern.compile("[A-Za-z]");
                Matcher m = p.matcher(word);
                if (word.length() > 1)
                    if (m.find()) {
                        counter++;
                    }
            }
            //String test1 = "a-\nb";
            //test = test.replaceAll("-\n","");
            if (counter >= 10) {
                part = part.replaceAll("-\n", "");
                part = part.replaceAll("\\n", " ");
                LinkedHashMap<String, Double> results = rake.getKeywordsFromText(part);
                Double max_score = maxUsingCollectionsMax(results);
                //LinkedHashMap<String, Double> max_normalized_scores_hash = new LinkedHashMap<>();
                Iterator it = results.entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry me = (Map.Entry) it.next();
                    String mapKey = (String) me.getKey();
                    Double mapValue = (Double) me.getValue();
                    double max_normalized_value = mapValue / max_score;
                    //max_normalized_scores_hash.put(mapKey, max_normalized_value);
                    if (max_normalized_value >= 0.5 && max_normalized_value < 0.6) {
                        final_keyphrases.add(mapKey);
                    }
                }

            }

        }


        //LinkedHashMap<String, Double> results = rake.getKeywordsFromText(text);
        //LinkedHashMap<String, Double> results = rake.getKeywordsFromText(sb.toString());

        /*LinkedHashMap<String, Double> results = rake.getKeywordsFromText(text);
        Double max_score = mainObject.maxUsingCollectionsMax(results);

        LinkedHashMap<String, Double> max_normalized_scores_hash = new LinkedHashMap<>();
        Iterator it = results.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            String mapKey = (String) me.getKey();
            Double mapValue = (Double) me.getValue();
            double max_normalized_value = mapValue / max_score;
            max_normalized_scores_hash.put(mapKey, max_normalized_value);
        }*/

        System.out.println(final_keyphrases);
        return final_keyphrases;
    }

    public static void main(String[] args) {
        RakeMain rakeMainObject = new RakeMain();
        String path = "/Users/fazeletavakoli/Desktop/A Graph Degeneracy-based Approach to Keyword Extraction.pdf";
        List <String> keyphrases = new ArrayList<>();
        keyphrases = rakeMainObject.keyword_extractor(path);
        int y = 0;

    }
}