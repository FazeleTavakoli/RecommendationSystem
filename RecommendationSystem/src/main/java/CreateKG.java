import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.*;
public class CreateKG {
    static HashMap<String, String> pyears;
    Integer uniqueId = 0;
    Integer uniqueId1 =0;
    Integer uniqueId2 = 0;
    LinkedHashMap<String, Integer> pub_id = new LinkedHashMap<>();
    LinkedHashMap<String, Integer> event_id = new LinkedHashMap<>();
    LinkedHashMap<String,Integer> year_id = new LinkedHashMap<>();


    public HashMap<String, Integer> publicationToId() {
        LinkedHashMap<String, Integer> pubToId = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> eventToId = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> yearToId = new LinkedHashMap<>();
        //System.out.println("Retrieving Years of Papers ....");
        //LinkedHashMap<String, Integer> publications = new LinkedHashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader("/Users/fazeletavakoli/Desktop/semCorpus/s2-corpus-00.txt"))) {
            String line;	int idx = 0;
            while((line = br.readLine()) != null) {
                //if(idx == 0) {
                //System.out.print(line.indexOf("base_oa_____::"));
                //System.out.print("\n");
                //if(line.indexOf("\t") != -1) {
                int title_index = line.indexOf("title");
                int titleName_index = title_index + 8;
                String title_name = line.substring(titleName_index);
                String pubOAid = line.substring(line.indexOf("title"),line.indexOf(">"));
                String[] parts = line.split("\t");
                //String name = parts[2];
                //line = line.substring(line.indexOf("\t")+1);	line = line.substring(line.indexOf("\t")+1);
                //String year = "";
                if(!pubToId.containsKey(pubOAid)){
                    Integer assignedId_pub = uniqueId;
                    pubToId.put(pubOAid,assignedId_pub);
                    uniqueId ++;
                }
                if(line.contains("journal>\t")) {

                    //String event = line.substring(line.indexOf("journal>\t")+10, line.indexOf("[\"\t\\.]"));
                    String events [] = parts[2].split("[\"\\”.]");
                    String event = events[1];
                    Integer assignedId_event = uniqueId1;
                    eventToId.put(event,assignedId_event);
                    uniqueId1 ++;
                }
                if(line.contains("year>")){
                    String years [] = parts[2].split("[\"\\”.]");
                    String year = years[1];
                    Integer assignedId_year = uniqueId2;
                    eventToId.put(year,assignedId_year);
                    uniqueId2 ++;

                }

//                        if(line.indexOf("\t") == -1)	year = line.trim();
//                        else	year = line.substring(0, line.indexOf("\t"));
//                        papers.put(pnr, year);

                //}
                //idx++;
                //}
                //else	idx = 0;
            } br.close();
        } catch(IOException e) {
            e.printStackTrace();	}
        int w = 1;
        Collection<Integer> values = pubToId.values();
        pub_id = new LinkedHashMap<>(pubToId);
        event_id = new LinkedHashMap<>(eventToId);
        return pubToId;
    }

    public void filterOnVenue(){
        String venueName1 = "ISWC";
        String venueName2 = "AAAI";
        //ReadWriteFile rwf = new ReadWriteFile();

        try {
            String line = "";
            BufferedReader br = null;
            InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/semCorpus/s2-corpus-00.txt");
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                //pureNoun.put(line, line);
                if (line.contains("venue")){
                    int venue_index = line.indexOf("venue");
                    int venueName_index = venue_index + 8;
                    String venueName_primary = line.substring(venueName_index);
                    //venueName_fetched = venueName_fetched.replaceAll("cb5", "");
                    String venueName_fetched_array [] = venueName_primary.split("[\"\\”.]");
                    String venueName_fetched = venueName_fetched_array[0];
                    if (venueName_fetched.equals(venueName1) || venueName_fetched.equals(venueName2)){
                        ReadWriteFile.writeInFile(line);
                        ReadWriteFile.writeInFile("\n");
                    }

                }
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();}
    }

    public Map<String, Integer>  getPub_id(){
        return pub_id;
    }

    public Map<String, Integer> getEvent_id(){
        return event_id;
    }

    public Map<String, Integer> getYear_id(){
        return year_id;
    }


    public static void main(String[] args) {

        CreateKG ckg = new CreateKG();
        ckg.publicationToId();
        ReadWriteFile rw = new ReadWriteFile();
        /*try {
            rw.write_csv(ckg.getPub_id(), ckg.getPub_id(), "/Users/fazeletavakoli/Desktop/test.csv");
            rw.write_csv(ckg.getYear_id(), ckg.getPub_id(), "/Users/fazeletavakoli/Desktop/test1.csv");
        }catch (Exception e){

        }*/

        ckg.filterOnVenue();

    }

}