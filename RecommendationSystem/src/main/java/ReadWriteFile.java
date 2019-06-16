import org.apache.commons.lang3.RandomUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.util.*;

public class ReadWriteFile {

    private static File file = new File("/Users/fazeletavakoli/Desktop/semCorpus/semOutput.txt");
    public void write_csv(Map<String, Integer> subHash, Map<String, Integer> objHash, String inputFile)
            throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(new File(inputFile));
        StringBuilder sb = new StringBuilder();
//        for (Map.Entry<String, Integer> entry : subHash.entrySet()) {
//            String key = entry.getKey();
//            Integer value = entry.getValue();
//            // now work with key and value...
//        }
        sb.append("publication");
        sb.append(',');
        sb.append("hasevent");
        sb.append(',');
        sb.append("journal");
        sb.append('\n');
//        Set set1 = new HashSet<>(subHash.entrySet());
//        Set set2 = new HashSet<>(objHash.entrySet());
//        Collection<Integer> values = subHash.values();
//        Collection<Integer> values1 = objHash.values();
        List<Integer> list1 = new ArrayList<>(subHash.values());
        List<Integer> list2 = new ArrayList<>(objHash.values());


        for (int i=0; i<subHash.size();i++){
            sb.append(list1.get(i));
            sb.append(',');
            sb.append("1");
            sb.append(',');
            sb.append(list2.get(i));
            sb.append("\n");

            //sb.append()
        }

        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }

    public void write_csv_map_id(LinkedHashMap<String, String> inputMap, String inputFile, String mapName)
            throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(new File(inputFile));
        StringBuilder sb = new StringBuilder();
//        for (Map.Entry<String, Integer> entry : subHash.entrySet()) {
//            String key = entry.getKey();
//            Integer value = entry.getValue();
//            // now work with key and value...
//        }
        //String mapName_array [] = mapName.split("_");
        sb.append("mapName");
        sb.append(',');
        sb.append("id");
        sb.append('\n');

        /*List<Integer> list1 = new ArrayList<>(subHash.values());
        List<Integer> list2 = new ArrayList<>(objHash.values());*/

        Iterator it = inputMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            sb.append(pair.getKey());
            sb.append(',');
            sb.append(pair.getValue());
            sb.append("\n");
            it.remove(); // avoids a ConcurrentModificationException
        }

        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }


    public static void writeInFile(String fileInput) {
        BufferedWriter bw = null;
        try {
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(fileInput);
            bw.write("\r\n");
            System.out.println("File written Successfully");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (Exception ex) {
                System.out.println("Error in closing the BufferedWriter" + ex);
            }

        }
    }


    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        Set<T> keys = new HashSet<T>();
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }


    public  void readJsonFile() {

        BufferedReader br = null;
        JSONParser parser = new JSONParser();
        LinkedHashMap<String,String> title_id_map = new LinkedHashMap<>();
        LinkedHashMap<String,String> venue_id_map = new LinkedHashMap<>();
        LinkedHashMap<String,String> author_id_map = new LinkedHashMap<>();
        //LinkedHashMap<String, String> coAuthor_id_map = new LinkedHashMap<>();
        LinkedHashMap<String,String> affiliation_id_map = new LinkedHashMap<>();
        LinkedHashMap<String, String> keyword_id_map = new LinkedHashMap<>();
        List<String> coauthor_name_list = new ArrayList<>();
        List<String> coauthor_id_list = new ArrayList<>();
        int topic_counter= 0;
        int venue_counter = 0;
        int author_counter = 0;
        int keyword_counter = 0;
        //int counter3 = 0;
        int counter_test_tv = 0; //counter to decide which "title-venue" triple should be written in the test data
        int counter_test_ta = 1; //counter to decide which "title-author" triple should be written in the test data
        int counter_test_aa = 2; //counter to decide which "author-author" triple should be written in the test data
        String inputFile_1 = "/Users/fazeletavakoli/Desktop/semCorpus/train.csv";
        String inputFile_2 = "/Users/fazeletavakoli/Desktop/semCorpus/test.csv";
        WebScraper ws = new WebScraper();
        Boolean pdf_existed = false;

        try {
            PrintWriter pw_1 = new PrintWriter(new File(inputFile_1));
            PrintWriter pw_2 = new PrintWriter(new File(inputFile_2));
            StringBuilder sb_1 = new StringBuilder();
            StringBuilder sb_2 = new StringBuilder();
            String sCurrentLine;
            for (int corpus = 0; corpus < 5; corpus++) {
                br = new BufferedReader(new FileReader("/Users/fazeletavakoli/Desktop/semCorpus/s2-corpus-0" + corpus + ".txt"));
                while ((sCurrentLine = br.readLine()) != null) {
                    boolean venue_accepted = false;
                    Object obj;
                    try {
                        obj = parser.parse(sCurrentLine);
                        JSONObject jsonObject = (JSONObject) obj;
                        //JSONArray entities = (JSONArray) jsonObject.get("entities");
                        String venue = (String) jsonObject.get("venue");

                        if (venue.contains("AAAI")) {
                            venue = "AAAI";
                            venue_accepted = true;
                        } else if (venue.contains("ISWC")) {
                            venue = "ISWC";
                            venue_accepted = true;
                        } else if (venue.contains("ESWC")) {
                            venue = "ESWC";
                            venue_accepted = true;
                        } else if (venue.contains("NIPS")) {
                            venue = "NIPS";
                            venue_accepted = true;
                        } else if (venue.contains("KCAP")) {
                            venue = "KCAP";
                            venue_accepted = true;
                        } else if (venue.contains("CIKM")) {
                            venue = "CIKM";
                            venue_accepted = true;
                        } else if (venue.contains("ACI")) {
                            venue = "ACI";
                            venue_accepted = true;
                        } else if (venue.contains("HCAI")) {
                            venue = "HCAI";
                            venue_accepted = true;
                        }

                        if (venue_accepted) {
                            System.out.println(venue);
                            String title = (String) jsonObject.get("title");
                            String title_id = "T" + topic_counter;
                            String venue_id = "";
                            if (!venue_id_map.containsValue(venue)) {
                                venue_id = "V" + venue_counter;
                                venue_id_map.put(venue_id, venue);
                                venue_counter ++;
                            } else {
                                venue_id = getKeyByValue(venue_id_map, venue);
                            }
                            if (counter_test_tv % 6 == 0) {
                                sb_2.append(title_id);
                                sb_2.append(",");
                                sb_2.append("has venue");
                                sb_2.append(",");
                                sb_2.append(venue_id);
                                sb_2.append("\n");

                            } else {
                                sb_1.append(title_id);
                                sb_1.append(",");
                                sb_1.append("has venue");
                                sb_1.append(",");
                                sb_1.append(venue_id);
                                sb_1.append("\n");
                            }

                            title_id_map.put(title_id, title);



                            /*JSONArray authors_array = (JSONArray) jsonObject.get("authors");
                            for (int i = 0; i < authors_array.size(); i++) {
                                //JSONObject rec = authors_array.getJSONObject(i);
                                if (i == 0) {
                                    JSONObject firstAuthor_entity = (JSONObject) authors_array.get(i);
                                    String firstAuthor_name = (String) firstAuthor_entity.get("name");
                                *//*JSONArray firstAuthor_mainId_entity = (JSONArray) firstAuthor_entity.get("ids");
                                String firstAuthor_mainId = (String)firstAuthor_mainId_entity.get(i);*//*
                                    String firstAuthor_id = "";
                                    if (!author_id_map.containsValue(firstAuthor_name)) {
                                        firstAuthor_id = "A" + counter;
                                        author_id_map.put(firstAuthor_name, firstAuthor_id);
                                    } else {
                                        firstAuthor_id = getKeyByValue(author_id_map, firstAuthor_name);
                                    }
                                    sb_1.append(title_id);
                                    sb_1.append(",");
                                    sb_1.append("has Author");
                                    sb_1.append(",");
                                    sb_1.append(firstAuthor_id);
                                    sb_1.append("\n");
                                } else {
                                    JSONObject coAuthor_entity = (JSONObject) authors_array.get(i);
                                    String coAuthor_name = (String) coAuthor_entity.get("name");
                                    String coAuthor_id = "";
                                    if (!coAuthor_id_map.containsValue(coAuthor_id)) {
                                        coAuthor_id = "CA" + counter1;
                                        coAuthor_id_map.put(coAuthor_name, coAuthor_id);
                                    } else {
                                        coAuthor_id = getKeyByValue(author_id_map, coAuthor_name);
                                    }

                                    sb_1.append(title_id);
                                    sb_1.append(",");
                                    sb_1.append("has coauthor");
                                    sb_1.append(",");
                                    sb_1.append(coAuthor_id);
                                    sb_1.append("\n");
                                    counter1++;
                                }
                            }*/

                            JSONArray authors_array = (JSONArray) jsonObject.get("authors");
                            for (int i = 0; i < authors_array.size(); i++) {
                                JSONObject author_entity = (JSONObject) authors_array.get(i);
                                String author_name = (String) author_entity.get("name");
                                String author_id = "";
                                if (!author_id_map.containsValue(author_name)) {
                                    author_id = "A" + author_counter;
                                    author_id_map.put(author_id, author_name);
                                    author_counter++;
                                } else {
                                    author_id = getKeyByValue(author_id_map, author_name);
                                }
                                int randomNumber_1 = RandomUtils.nextInt(0, authors_array.size());
                                if (counter_test_ta % 6 == 0 && i == randomNumber_1 && authors_array.size() > 1) {
                                    sb_2.append(title_id);
                                    sb_2.append(",");
                                    sb_2.append("has Author");
                                    sb_2.append(",");
                                    sb_2.append(author_id);
                                    sb_2.append("\n");
                                } else {
                                    sb_1.append(title_id);
                                    sb_1.append(",");
                                    sb_1.append("has Author");
                                    sb_1.append(",");
                                    sb_1.append(author_id);
                                    sb_1.append("\n");
                                }
                            }

                            for (int i = 0; i < authors_array.size() - 1; i++) {
                                JSONObject coAuthor_entity_1 = (JSONObject) authors_array.get(i);
                                String coAuthor_name_1 = (String) coAuthor_entity_1.get("name");
                                String coAuthor_id_1 = "";
                                coAuthor_id_1 = getKeyByValue(author_id_map, coAuthor_name_1);
                                for (int j = i + 1; j < authors_array.size(); j++) {
                                    JSONObject coAuthor_entity_2 = (JSONObject) authors_array.get(j);
                                    String coAuthor_name_2 = (String) coAuthor_entity_2.get("name");
                                    String coAuthor_id_2 = "";
                                    coAuthor_id_2 = getKeyByValue(author_id_map, coAuthor_name_2);

                                    int randomNumber_2 = RandomUtils.nextInt(i + 1, authors_array.size());
                                    if (counter_test_aa % 10 == 0 && j == randomNumber_2) {
                                        sb_2.append(coAuthor_id_1);
                                        sb_2.append(",");
                                        sb_2.append("has coAuthor");
                                        sb_2.append(",");
                                        sb_2.append(coAuthor_id_2);
                                        sb_2.append("\n");
                                    } else {
                                        sb_1.append(coAuthor_id_1);
                                        sb_1.append(",");
                                        sb_1.append("has coAuthor");
                                        sb_1.append(",");
                                        sb_1.append(coAuthor_id_2);
                                        sb_1.append("\n");
                                    }
                                }

                            }


                            JSONArray pdfUrl_array = (JSONArray) jsonObject.get("pdfUrls");
                            String pdfUrl = "";
                            if (!pdfUrl_array.isEmpty())
                                pdfUrl = (String) pdfUrl_array.get(0);
                            if (!pdfUrl.isEmpty()) {
                                //URL url = new URL(pdfUrl);
                                pdf_existed = ws.saveFile(pdfUrl, "/Users/fazeletavakoli/IdeaProjects/RecommendationSystem/File.pdf");
                                //pdf_existed = true;
                            } else if (pdfUrl.isEmpty() || !pdf_existed) {

                                String downloadUrl = (String) jsonObject.get("s2Url");
                                pdf_existed = ws.semScholar_scraper(downloadUrl, false); //uncomment this line in the main code
                            /*if (!pdf_existed){
                                int a = 0;
                            }*/
                            }

                        /*try {
                            String pdfText = pdfParser("/Users/fazeletavakoli/IdeaProjects/RecommendationSystem/File.pdf");
                            sb_1.append(title_id);
                            sb_1.append(",");
                            sb_1.append("has text");
                            sb_1.append(",");
                            sb_1.append(pdfText);
                            sb_1.append("\n");
                        }catch(Exception e){

                        }*/

                            if (pdf_existed) {
                                RakeMain rakeMainObject = new RakeMain();
                                List<String> keyphrases = new ArrayList<>();
                                List<String> splitted_keyphrases = new ArrayList<>();
                                List<String> keywords_id_localList = new ArrayList<>();
                                //String mypath = "/Users/fazeletavakoli/Desktop/A Graph Degeneracy-based Approach to Keyword Extraction.pdf";
                                //keyphrases = rakeMainObject.keyword_extractor(mypath);
                                keyphrases = rakeMainObject.keyword_extractor("/Users/fazeletavakoli/IdeaProjects/RecommendationSystem/File.pdf");
                                for (String phrase : keyphrases) {
                                    String array[] = phrase.split(" ");
                                    for (String element : array) {
                                        if (element.length() > 1 && !isInteger(element)) {
                                            splitted_keyphrases.add(element);
                                            String keyword_id = "";
                                            if (!keyword_id_map.containsValue(element)) {
                                                keyword_id = "K" + keyword_counter;
                                                keyword_id_map.put(keyword_id, element);
                                                keyword_counter++;
                                            } else {
                                                keyword_id = getKeyByValue(keyword_id_map, element);
                                            }
                                            keywords_id_localList.add(keyword_id); //storing the id of a single keyword in a list
                                        }
                                    }
                                }
                                keyphrases.addAll(splitted_keyphrases); //joining two lists of keyphrases and splitted_keyphrases together
                                sb_1.append(title_id);
                                sb_1.append(",");
                                sb_1.append("has keywords");
                                sb_1.append(",");
                                sb_1.append(keyphrases);
                                sb_1.append("\n");
                            }


                            topic_counter++;
                            System.out.println(topic_counter);
                            System.out.println("\n");
                            counter_test_tv++;
                            counter_test_ta++;
                            counter_test_aa++;
                            if (topic_counter > 5000) {
                                break;
                            }

                        }

                    /*String start = (String) jsonObject.get("start");
                    System.out.println(start);

                    String end = (String) jsonObject.get("end");
                    System.out.println(end);*/

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
            pw_1.write(sb_1.toString());
            pw_1.close();
            System.out.println("train done!");

            pw_2.write(sb_2.toString());
            pw_2.close();
            System.out.println("test done!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        String file_path = "";
        try {
            file_path = "/Users/fazeletavakoli/Desktop/semCorpus/title_id.csv";
            write_csv_map_id(title_id_map, file_path, "title");
            file_path = "/Users/fazeletavakoli/Desktop/semCorpus/venue_id.csv";
            write_csv_map_id(venue_id_map, file_path, "venue");
            file_path = "/Users/fazeletavakoli/Desktop/semCorpus/author_id.csv";
            write_csv_map_id(author_id_map, file_path, "author");
            //file_path = "/Users/fazeletavakoli/Desktop/semCorpus/coAuthor_id.csv";
            //write_csv_map_id(coAuthor_id_map, file_path, "coAuthor");
            file_path = "/Users/fazeletavakoli/Desktop/semCorpus/keywords.csv";
            write_csv_map_id(keyword_id_map, file_path, "keywords");
        }catch (Exception e){

        }

    }

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

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }


    public void getUniqueEntity () {
        int venue_counter = 0;
        int author_counter = 0;
        int coauthor_counter = 0;
        try {
            String line = "";
            BufferedReader br = null;
            InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/semCorpus/train.csv");
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                if (line.contains("has venue"))
                    venue_counter++;
                else if (line.contains("has coAuthor"))
                    coauthor_counter++;
                else if (line.contains("has Author"))
                    author_counter++;
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("number of author:" + author_counter + "\nnumber of coauthor:" +
                coauthor_counter + "\nnumber of venue:" + venue_counter);
    }

    public static void main(String[]args) throws FileNotFoundException {
        ReadWriteFile rw = new ReadWriteFile();
        rw.readJsonFile();

        //rw.getUniqueEntity();



    }

}
