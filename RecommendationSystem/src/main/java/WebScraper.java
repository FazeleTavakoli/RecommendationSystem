//import jdk.nashorn.internal.parser.JSONParser;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.pdfbox.io.RandomAccessFile;

public class WebScraper {

    //these two methods can be used for some other datasets
    /*public String Scraper(String address){
        StringBuilder sb = new StringBuilder();
        WebScraper ws = new WebScraper();
        try {
            *//*URL pagelocation = new URL(address);
            Scanner in = new Scanner(pagelocation.openStream());

            while(in.hasNext()){
                String line = in.
            }*//*


            *//*System.out.println("opening connection");
            URL url = new URL(address);
            InputStream in = url.openStream();
            FileOutputStream fos = new FileOutputStream(new File("yourFile.pdf"));

            System.out.println("reading from resource and writing to file...");
            int length = -1;
            byte[] buffer = new byte[1024];// buffer for portion of data from connection
            while ((length = in.read(buffer)) > -1) {
                fos.write(buffer, 0, length);
            }
            fos.close();
            in.close();
            System.out.println("File downloaded");*//*
            /*//**************************************

            URL url = new URL(address);
            URLConnection urlCon = url.openConnection();
            BufferedReader in = null;

            if (urlCon.getHeaderField("Content-Encoding") != null
                    && urlCon.getHeaderField("Content-Encoding").equals("gzip")) {
                in = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                        urlCon.getInputStream())));
            } else {
                in = new BufferedReader(new InputStreamReader(
                        urlCon.getInputStream()));
            }
            String inputLine;
            //StringBuilder sb = new StringBuilder();

                *//*while ((inputLine = in.readLine()) != null)
                    sb.append(inputLine);
                in.close();*//*
            int counter = 0;
            while((inputLine = in.readLine()) != null){
                if (inputLine.contains("<li><a href=\"http://ceur-ws.org/Vol")){
                    String parts [] = inputLine.split(" ");
                    String part[] = parts[1].split("[\"\\”]");
                    String fetched_url = part[1];
                    ws.DownloadPDF(fetched_url, Integer.toString(counter));
                    counter++;
                }
            }
            System.out.printf("counter is: %d\n", counter);
        }catch(Exception e){
            System.out.print("Time out");
        }
        return sb.toString();
    }


    public void DownloadPDF(String address, String fileNumber){
        try {
            System.out.println("opening connection");
            URL url = new URL(address);
            InputStream in = url.openStream();
            FileOutputStream fos = new FileOutputStream(new File("File"+fileNumber+".pdf"));

            System.out.println("reading from resource and writing to file...");
            int length = -1;
            byte[] buffer = new byte[1024];// buffer for portion of data from connection
            while ((length = in.read(buffer)) > -1) {
                fos.write(buffer, 0, length);
            }
            fos.close();
            in.close();
            System.out.println("File downloaded");

        }catch(Exception e){

        }
    }*/




    public void DownloadPDF(String address){
        try {
            System.out.println("opening connection");
            URL url = new URL(address);
            InputStream in = url.openStream();
            FileOutputStream fos = new FileOutputStream(new File("File"+".pdf"));

            System.out.println("reading from resource and writing to file...");
            int length = -1;
            byte[] buffer = new byte[1024];// buffer for portion of data from connection
            while ((length = in.read(buffer)) > -1) {
                fos.write(buffer, 0, length);
            }
            //href="https://pdfs.semanticscholar.org/bdc2/b881c1b595667cfd08724258f346727ee0e8.pdf
            fos.close();
            in.close();
            System.out.println("File downloaded");

        }catch(Exception e){
            System.out.println("pdf is not accessible");
        }
    }

    public void semScholar_scraper(String address){
        StringBuilder sb = new StringBuilder();
        WebScraper ws = new WebScraper();
        try {
            URL url = new URL(address);
            URLConnection urlCon = url.openConnection();
            BufferedReader in = null;

            if (urlCon.getHeaderField("Content-Encoding") != null
                    && urlCon.getHeaderField("Content-Encoding").equals("gzip")) {
                in = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                        urlCon.getInputStream())));
            } else {
                in = new BufferedReader(new InputStreamReader(
                        urlCon.getInputStream()));
            }
            String inputLine;
            String content = "href" + "="+ "\"" +"https://pdfs";
            while((inputLine = in.readLine()) != null){
                if (inputLine.contains("View Paper") ){
                    String parts [] = inputLine.split(" ");
                    for (String part: parts){
                        if (part.contains(content)){
                            String specificPart_array [] = part.split("\"");
                            String specificPart = specificPart_array[1];
                            DownloadPDF(specificPart);
                        }
                    }

                }
            }
        }
        catch(Exception e){

        }

    }









    // PDFBox 2.0.8 require org.apache.pdfbox.io.RandomAccessRead
    // import org.apache.pdfbox.io.RandomAccessFile;
    public void extractPDF() {
        PDFTextStripper pdfStripper = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        File file = new File("/Users/fazeletavakoli/Desktop/transe_nips13.pdf");
        String filePath = "/Users/fazeletavakoli/Desktop/transe_nips13.pdf";
        try {
            // PDFBox 2.0.8 require org.apache.pdfbox.io.RandomAccessRead
            //RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            //PDFParser parser = new PDFParser(randomAccessFile);

            //PDFParser parser = new PDFParser(new FileInputStream(file));
            //parser.parse();

            //cosDoc = parser.getDocument();
            //pdfStripper = new PDFTextStripper();

            file = new File(filePath);
            PDFParser parser = new PDFParser(new RandomAccessFile(file,"r"));
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            pdDoc = new PDDocument(cosDoc);
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(5);
            String parsedText = pdfStripper.getText(pdDoc);
            System.out.println(parsedText);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }





    public static void main(String args[]){
        WebScraper ws = new WebScraper();
        /*String s = ws.Scraper("https://github.com/ceurws/lod/wiki/SemPub17_Task2");
        if (s.contains("<li><a href=\"http://ceur-ws.org/Vol")){
            System.out.print("accepted");
        }*/

        //ws.semScholar_scraper("https://www.semanticscholar.org/paper/SNIFF%3A-A-Search-Engine-for-Java-Using-Free-Form-Chatterjee-Juvekar/0b5e9f85ce77b9e4fac385cbd53907ae562b4dbb");
        ws.extractPDF();

    }
}
