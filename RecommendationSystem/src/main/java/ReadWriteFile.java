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
        LinkedHashMap<String, String> coAuthor_id_map = new LinkedHashMap<>();
        LinkedHashMap<String,String> affiliation_id_map = new LinkedHashMap<>();
        LinkedHashMap<String, String> keyword_id_map = new LinkedHashMap<>();
        List<String> coauthor_name_list = new ArrayList<>();
        List<String> coauthor_id_list = new ArrayList<>();
        int counter= 0;
        int counter1 = 0;
        int counter2 = 0;
        String inputFile = "/Users/fazeletavakoli/Desktop/semCorpus/test.csv";
        WebScraper ws = new WebScraper();


        try {
            PrintWriter pw = new PrintWriter(new File(inputFile));
            StringBuilder sb = new StringBuilder();

            String sCurrentLine;
            br = new BufferedReader(new FileReader("/Users/fazeletavakoli/Desktop/semCorpus/s2-corpus-00.txt"));
            while ((sCurrentLine = br.readLine()) != null) {
                //System.out.println("Record:\t" + sCurrentLine);

                Object obj;
                try {
                    obj = parser.parse(sCurrentLine);
                    JSONObject jsonObject = (JSONObject) obj;

                    //JSONArray entities = (JSONArray) jsonObject.get("entities");
                    String venue = (String) jsonObject.get("venue");
                    if (venue.equals("AAAI") || venue.equals("ISWC")) {
                        //System.out.println(entities);
                        System.out.println(venue);
                        String title = (String) jsonObject.get("title");
                        String title_id = "T"+counter;
                        sb.append(title_id);
                        sb.append(",");
                        sb.append("has venue");
                        sb.append(",");
                        String venue_id = "";
                        if (!venue_id_map.containsValue(venue)) {
                            venue_id = "V" + counter;
                            venue_id_map.put(venue_id, venue);
                        }
                        else{
                            venue_id = getKeyByValue(venue_id_map, venue);
                        }
                        sb.append(venue_id);
                        sb.append("\n");
                        title_id_map.put(title_id, title);


                        //JSONArray values =
                        JSONArray authors_array = (JSONArray)jsonObject.get("authors");
                        for(int i=0; i< authors_array.size(); i++){
                            //JSONObject rec = authors_array.getJSONObject(i);
                            if (i == 0){
                                JSONObject firstAuthor_entity = (JSONObject)authors_array.get(i);
                                String firstAuthor_name = (String) firstAuthor_entity.get("name");
                                /*JSONArray firstAuthor_mainId_entity = (JSONArray) firstAuthor_entity.get("ids");
                                String firstAuthor_mainId = (String)firstAuthor_mainId_entity.get(i);*/
                                String firstAuthor_id = "";
                                if(!author_id_map.containsValue(firstAuthor_name)) {
                                    firstAuthor_id = "A" + counter;
                                    author_id_map.put(firstAuthor_name, firstAuthor_id);
                                }
                                else{
                                    firstAuthor_id = getKeyByValue(author_id_map, firstAuthor_name);
                                }
                                sb.append(title_id);
                                sb.append(",");
                                sb.append("has Author");
                                sb.append(",");
                                sb.append(firstAuthor_id);
                                sb.append("\n");
                            }
                            else{
                                JSONObject coAuthor_entity = (JSONObject)authors_array.get(i);
                                String coAuthor_name = (String) coAuthor_entity.get("name");
                                String coAuthor_id = "";
                                if(!coAuthor_id_map.containsValue(coAuthor_id)) {
                                    coAuthor_id = "CA"+counter1;
                                    coAuthor_id_map.put(coAuthor_name, coAuthor_id);
                                }
                                else{
                                    coAuthor_id = getKeyByValue(author_id_map, coAuthor_name);
                                }

                                sb.append(title_id);
                                sb.append(",");
                                sb.append("has coauthor");
                                sb.append(",");
                                sb.append(coAuthor_id);
                                sb.append("\n");
                                counter1++;
                            }
                        }

                        String downloadUrl = (String) jsonObject.get("s2Url");
                        //ws.semScholar_scraper(downloadUrl); //uncomment this line in the main code
                        /*try {
                            String pdfText = pdfParser("/Users/fazeletavakoli/IdeaProjects/RecommendationSystem/File.pdf");
                            sb.append(title_id);
                            sb.append(",");
                            sb.append("has text");
                            sb.append(",");
                            sb.append(pdfText);
                            sb.append("\n");
                        }catch(Exception e){

                        }*/

                        RakeMain rakeMainObject = new RakeMain();
                        List <String> keyphrases = new ArrayList<>();
                        List <String> splitted_keyphrases = new ArrayList<>();
                        List <String> keywords_id_localList = new ArrayList<>();
                        //String mypath = "/Users/fazeletavakoli/Desktop/A Graph Degeneracy-based Approach to Keyword Extraction.pdf";
                        //keyphrases = rakeMainObject.keyword_extractor(mypath);
                        keyphrases = rakeMainObject.keyword_extractor("/Users/fazeletavakoli/IdeaProjects/RecommendationSystem/File.pdf");
                        for (String phrase: keyphrases){
                            String array [] = phrase.split(" ");
                            for (String element: array) {
                                if (element.length() > 1 && !isInteger(element)) {
                                    splitted_keyphrases.add(element);
                                    String keyword_id = "";
                                    if (!keyword_id_map.containsValue(element)) {
                                        keyword_id = "K" + counter2;
                                        keyword_id_map.put(keyword_id, element);
                                        counter2++;
                                    } else {
                                        keyword_id = getKeyByValue(keyword_id_map, element);
                                    }
                                    keywords_id_localList.add(keyword_id);
                                }
                            }
                        }
                        sb.append(title_id);
                        sb.append(",");
                        sb.append("has keywords");
                        sb.append(",");
                        sb.append(keyphrases);
                        sb.append(",");
                        sb.append(splitted_keyphrases);
                        //sb.append(keywords_id_localList);
                        sb.append("\n");


                        counter++;
                        if (counter > 1){
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
            pw.write(sb.toString());
            pw.close();
            System.out.println("done!");

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
            file_path = "/Users/fazeletavakoli/Desktop/semCorpus/coAuthor_id.csv";
            write_csv_map_id(coAuthor_id_map, file_path, "coAuthor");
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


    public static void main(String[]args) throws FileNotFoundException {
        ReadWriteFile rw = new ReadWriteFile();
        //rw.write_csv();

        rw.readJsonFile();
        int a = 9;


    }

}
