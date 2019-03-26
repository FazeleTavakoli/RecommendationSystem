import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;


public class Corpus  {

    private static final String REGEXY = ".*ی$";
    public static final Pattern pY = Pattern.compile(REGEXY);
    private static final String REGEXGAN = ".*گان$";
    public static final Pattern pGan = Pattern.compile(REGEXGAN);
    private static final String REGEXAN = ".*ان$";
    public static final Pattern pAn = Pattern.compile(REGEXAN);
    private static final String REGEXAT = ".*ات$";
    public static final Pattern pAt = Pattern.compile(REGEXAT);
    private static final String REGEXYN = ".*ین$";
    public static final Pattern pYn = Pattern.compile(REGEXYN);
    private static final String REGEXOUN = ".*ون$";
    public static final Pattern pOun = Pattern.compile(REGEXOUN);
    private static final String REGEXHA = ".*ها$";
    public static final Pattern pHa = Pattern.compile(REGEXHA);
    private static final String REGEXENDHALFS = ".*\\u200c$";
    public static final Pattern pEndHalfS = Pattern.compile(REGEXENDHALFS);
    private static final String REGEXK = ".*ک$";
    public static final Pattern pK = Pattern.compile(REGEXK);
    private static final String REGEXSHENASE_M = ".*م$";
    public static final Pattern pShenaseM = Pattern.compile(REGEXSHENASE_M);
    private static final String REGEXSHENASE_T = ".*ت$";
    public static final Pattern pShenaseT = Pattern.compile(REGEXSHENASE_T);
    private static final String REGEXSHENASE_SH = ".*ش$";
    public static final Pattern pShenaseSh = Pattern.compile(REGEXSHENASE_SH);
    private static final String REGEXSHENASE_MAN = ".*مان$";
    public static final Pattern pShenaseMan = Pattern.compile(REGEXSHENASE_MAN);
    private static final String REGEXSHENASE_TAN = ".*تان$";
    public static final Pattern pShenaseTan = Pattern.compile(REGEXSHENASE_TAN);
    private static final String REGEXSHENASE_SHAN = ".*شان$";
    public static final Pattern pShenaseShan = Pattern.compile(REGEXSHENASE_SHAN);
    private static final String REGEXTARIN = ".*ترین$";
    public static final Pattern pTarin = Pattern.compile(REGEXTARIN);
    private static final String REGEXTAR = ".*تر$";
    public static final Pattern pTar = Pattern.compile(REGEXTAR);
    private static final String REGEXSEVERALHALFS = "\\u200c\\u200c+";
    public static final Pattern pSeveralHalfS = Pattern.compile(REGEXSEVERALHALFS);
    private static final String REGEXSINGLEHALFS = "\u200c";
    public static final Pattern pSingleHalfS = Pattern.compile(REGEXSINGLEHALFS);
    private static final String REGEXSEVERALNULL = "\\u0000\\u0000+";
    public static final Pattern pSeveralNull = Pattern.compile(REGEXSEVERALNULL);
    private static final String REGEXUJUST200B = "\u200b+";
    public static final Pattern pJustU200b = Pattern.compile(REGEXUJUST200B);
    private static final String REGEXSeVerALSPACE = "\\s+";
    public static final Pattern pSeveralSpace = Pattern.compile(REGEXSeVerALSPACE);
    private static final String REGEXJUSTU200C = "\u200c+";
    public static final Pattern pJustU200c = Pattern.compile(REGEXJUSTU200C);
    private static final String REGEXJUSTU200F = "\u200F+";
    public static final Pattern pJustU200f = Pattern.compile(REGEXJUSTU200F);
    private static final String REGEXSINGLESPACE = "^\\s$";
    public static final Pattern pSingleSpace = Pattern.compile(REGEXSINGLESPACE);
    private static final String REGEXEMPTY = "^$";
    public static final Pattern pEmpty = Pattern.compile(REGEXEMPTY);
    private static final String REGEXUFFFD = "\ufffd+";
    public static final Pattern pUfffd = Pattern.compile(REGEXUFFFD);
    private static final String REGEXJUSTU200A = "\u200a";
    public static final Pattern pU200a = Pattern.compile(REGEXJUSTU200A);


    //*******************verbs regex***************************
/*    private static final String REGEXCONNECTEDV = ".*ست$";
    public static final Pattern pConnectedV = Pattern.compile(REGEXCONNECTEDV);
    private static final String REGEXZAMIR_M = ".*م$";
    public static final Pattern pZamirM = Pattern.compile(REGEXZAMIR_M);
    private static final String REGEXZAMIR_Y = ".*ی$";
    public static final Pattern pZamirY = Pattern.compile(REGEXZAMIR_Y);
    private static final String REGEXZAMIR_D = ".*د$";
    public static final Pattern pZamirD = Pattern.compile(REGEXZAMIR_D);
    private static final String REGEXZAMIR_YM = ".*یم$";
    public static final Pattern pZamirYm = Pattern.compile(REGEXZAMIR_YM);
    private static final String REGEXZAMIR_YD = ".*ید$";
    public static final Pattern pZamirYd = Pattern.compile(REGEXZAMIR_YD);
    private static final String REGEXZAMIR_ND = ".*ند$";
    public static final Pattern pZamirNd = Pattern.compile(REGEXZAMIR_ND);
    private static final String REGEXCONTINIOUS_MY = "^می.+";
    public static final Pattern pContiniousMy = Pattern.compile(REGEXCONTINIOUS_MY);
    private static final String REGEXCONTZAMIR_AM = ".*ام$";
    public static final Pattern pContZamirAm = Pattern.compile(REGEXCONTZAMIR_AM);
    private static final String REGEXCONTZAMIR_Y = ".*ای$";
    public static final Pattern pContZamirY = Pattern.compile(REGEXCONTZAMIR_Y);
    private static final String REGEXCONTZAMIR_AST = ".*است$";
    public static final Pattern pContZamirAst = Pattern.compile(REGEXCONTZAMIR_AST);
    private static final String REGEXCONTZAMIR_YM = ".*ایم$";
    public static final Pattern pContZamirYm = Pattern.compile(REGEXCONTZAMIR_YM);
    private static final String REGEXCONTZAMIR_YD = ".*اید$";
    public static final Pattern pContZamirYd = Pattern.compile(REGEXCONTZAMIR_YD);
    private static final String REGEXCONTZAMIR_AND = ".*اند$";
    public static final Pattern pContZamirAnd = Pattern.compile(REGEXCONTZAMIR_AND);
    private static final String REGEXBEGINHALFS  = "^\\u200c.*";
    public static final Pattern pBeginHalfS = Pattern.compile(REGEXBEGINHALFS);
    private static final String REGEXHALFS = ".*\\u200c";
    public static final Pattern pHalfS = Pattern.compile(REGEXHALFS);
    private static final String REGEXBUDAM = ".*بودم$";
    public static final Pattern pBudam = Pattern.compile(REGEXBUDAM);
    private static final String REGEXBUDY = ".*بودی$";
    public static final Pattern pBudy = Pattern.compile(REGEXBUDY);
    private static final String REGEXBUD = ".*بود$";
    public static final Pattern pBud = Pattern.compile(REGEXBUD);
    private static final String REGEXBUDIM = ".*بودیم$";
    public static final Pattern pBudim = Pattern.compile(REGEXBUDIM);
    private static final String REGEXBUDID = ".*بودید$";
    public static final Pattern pBudid = Pattern.compile(REGEXBUDID);
    private static final String REGEXBUDAND = ".*بودند$";
    public static final Pattern pBudand = Pattern.compile(REGEXBUDAND);
    private static final String REGEXBASHAM = ".*باشم$";
    public static final Pattern pBasham = Pattern.compile(REGEXBASHAM);
    private static final String REGEXBASHY = ".*باشی$";
    public static final Pattern pBashy = Pattern.compile(REGEXBASHY);
    private static final String REGEXBASHAD = ".*باشد$";
    public static final Pattern pBashad = Pattern.compile(REGEXBASHAD);
    private static final String REGEXBASHIM = ".*باشیم$";
    public static final Pattern pBashim = Pattern.compile(REGEXBASHIM);
    private static final String REGEXBASHID = ".*باشید$";
    public static final Pattern pBashid = Pattern.compile(REGEXBASHID);
    private static final String REGEXBASHAND = ".*باشند$";
    public static final Pattern pBashand = Pattern.compile(REGEXBASHAND);
    private static final String REGEXEND_E = ".*ه$";
    public static final Pattern pEndE = Pattern.compile(REGEXEND_E);
    private static final String REGEXSIngle_AST = "^است$";
    public static final Pattern pSingleAst = Pattern.compile(REGEXSIngle_AST);
    private static final String REGEXKHAH = "^خواه.*";
    public static final Pattern pKhah = Pattern.compile(REGEXKHAH);
    private static final String REGEXFUZAMIR_M = "^.{4}م.+";
    public static final Pattern pFuZamirM = Pattern.compile(REGEXFUZAMIR_M);
    private static final String REGEXFUZAMIR_Y = "^.{4}ی.+";
    public static final Pattern pFuZamirY = Pattern.compile(REGEXFUZAMIR_Y);
    private static final String REGEXFUZAMIR_D = "^.{4}د.+";
    public static final Pattern pFuZamirD = Pattern.compile(REGEXFUZAMIR_D);
    private static final String REGEXFUZAMIR_YM = "^.{4}یم.+";
    public static final Pattern pFuZamirYm = Pattern.compile(REGEXFUZAMIR_YM);
    private static final String REGEXFUZAMIR_YD = "^.{4}ید.+";
    public static final Pattern pFuZamirYd = Pattern.compile(REGEXFUZAMIR_YD);
    private static final String REGEXFUZAMIR_ND = "^.{4}ند.+";
    public static final Pattern pFuZamirNd = Pattern.compile(REGEXFUZAMIR_ND);
    private static final String REGEXBEGIN_B = "^ب.+";
    public static final Pattern pBeginB = Pattern.compile(REGEXBEGIN_B);
    private static final String REGEXBEGIN_N = "^ن.+";
    public static final Pattern pBeginN = Pattern.compile(REGEXBEGIN_N);
    private static final String REGEXBEGIN_Y = "^ی.+";
    public static final Pattern pBeginY = Pattern.compile(REGEXBEGIN_Y);*/


    public static Map<String,String> pluralAt  = new HashMap<String,String>();
    public static Map<String,String> pluralAn = new HashMap<String,String>();
    public static Map<String,String> pluralGan = new HashMap<String,String>();
    public static Map<String,String> pluralUnIn = new HashMap<String,String>();
    public static Map<String,String> pureNoun = new HashMap<String,String>();
    public static Map<String,String> nounPrepSharingTar = new HashMap<String,String>();
    public static Map<String,String> pastV = new HashMap<String,String>();
    public static Map<String,String> presentV = new HashMap<String,String>();
    public static Map<String,String> preposition = new HashMap<String,String>();
    public static Map<String,String> unknown = new HashMap<String,String>();
    public static Map<String,String> halfSpace = new HashMap<String,String>();
    public static Map<String,String> halfSpaceVerbs = new HashMap<String,String>();
    public static Map<String,String> compoundVerb = new HashMap<String,String>();
    public static Map<String,String> noun_prepositionIntersect = new HashMap<>();
    public static Map<String,String> prep_nounIntersec = new HashMap<>();
    public static Map <String,String> modifedPluralAn = new HashMap<String,String>();
    //for english keyword extractor version
    public static Map<String,String> keywordsList_original = new HashMap<>(); //contains original(human assigned) keywords to measure accuracy
    public static Map<String,String> englishStopWords = new HashMap<String,String>();



    private static File file = new File("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Input/txtOutput.txt");
    private static File file1 = new File("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Input/Noun11.txt");
    private static File file2 = new File("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Input/serajiReplacedOutput.txt");
    private List<Integer> repeatedPhrasesIndexes = new ArrayList<Integer>(); //contains indexes of repeated phrases
    private String finalResult = ""; //static is removed
    private int printedCounter = 0; //newly added
    //private static List<String> removedNouns = new ArrayList<String>();
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static ReentrantLock reentrantLock2 = new ReentrantLock();

    public static Map getPureNoun(){
        if (pureNoun.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Noun11.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    pureNoun.put(line, line);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return pureNoun;

    }

    public static Map getMapAt (){
        String line1 = "";
        String line2 = "";
        if (pluralAt.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/AtPNouns.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    if (line.contains("$")) {
                        int index = line.indexOf('$');
                        if (line != null) {
                            line1 = line.substring(0, index);
                            line2 = line.substring(index + 1);
                            //writeInFile(line,file1);
                        }
                    }
                    pluralAt.put(line1, line2);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}

        }

        return pluralAt;
    }

    public static Map getMapAn (){
        if (pluralAn.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/pNounAn.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    pluralAn.put(line, line);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return pluralAn;
    }

    public static Map getMapGan (){
        if (pluralGan.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/pNounGan.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    pluralGan.put(line, line);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return pluralGan;
    }

    public static Map getMapUnIn (){
        if (pluralUnIn.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/unInPNouns.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    pluralUnIn.put(line, line);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return pluralUnIn;
    }

    public static Map getMapNounPrepSharingTar(){
        if (nounPrepSharingTar.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/NounPrepSharingTar.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    nounPrepSharingTar.put(line, line);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return nounPrepSharingTar;
    }




    public static Map getMapPastV(){
        if (pastV.isEmpty()){
            try{
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/pastV.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    pastV.put(line, line);
                inputStream.close();
            }catch (IOException e ){
                e.printStackTrace();
            }
        }
        return pastV;
    }

    public static Map getMapPresentV(){
        if(presentV.isEmpty()){
            try{
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/presentV.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    presentV.put(line,line);
                inputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return presentV;
    }

    public static Map getMapPreposition(){
        if(preposition.isEmpty()){
            try{
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/prepV.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    preposition.put(line,line);
                inputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return preposition;
    }

    public static Map getMapUnknown(){
        if(unknown.isEmpty()){
            try{
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/unknownWords.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    unknown.put(line,line);
                inputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return unknown;
    }

    public static Map getMapHalfSpace(){
        if (halfSpace.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Noun11.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    if (line.contains("\u200c")){
                        halfSpace.put(line, line);
                    }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return halfSpace;
    }



    public static Map getMapHalfSpaceVerbs(){
        if (halfSpaceVerbs.isEmpty()){
            try {
                String singleWord = "";
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/infinitive.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    String splittedLine[] = line.split("@");
                    singleWord = splittedLine[4];
                    singleWord = singleWord.trim();
                    if (singleWord.contains(" ")) {
                        singleWord = singleWord.replaceAll(" ", "\u200c");
                        halfSpaceVerbs.put(singleWord, singleWord);
                    }
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return halfSpaceVerbs;
    }

    public static Map getMapCompoundVerb (){
        if (compoundVerb.isEmpty()){
            try {
                String pastRoot = "";
                String presentRoot = "";
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/infinitive.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    String splittedLine[] = line.split("@");
                    presentRoot = splittedLine[6];
                    pastRoot = splittedLine[5];
                    presentRoot = presentRoot.trim();
                    pastRoot = pastRoot.trim();
                    if (presentRoot.contains(" "))
                        presentRoot = presentRoot.replaceAll(" ","\u200c");
                    if (pastRoot.contains(" "))
                        pastRoot = pastRoot.replaceAll(" ","\u200c");
                    compoundVerb.put(pastRoot,pastRoot);
                    compoundVerb.put(presentRoot,presentRoot);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return compoundVerb;
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

    /*public  void writeInFile(String fileInput) {
        BufferedWriter bw = null;
        try {
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            if (printedCounter == 0 && !fileInput.isEmpty()) {
                bw.write("$" + fileInput);
            }
            else if (printedCounter != 0 && !fileInput.isEmpty() && !fileInput.equals("\r\n") && !fileInput.equals("") )
                bw.write("*" + fileInput);
            else if (fileInput.equals("\r\n"))
                bw.write(fileInput);
            printedCounter += 1;
            //bw.write("\r\n");
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
    }*/

    public static void writeInConllFile(){

        int m = 0;
        int lineCounter = 0;
        int line1Counter = 0;
        BufferedWriter bw = null;
        StringBuilder sb;
        String minReplaced = "";
        try {
            FileWriter fw = new FileWriter(file2,true);
            bw = new BufferedWriter(fw);
            String splittedConllOutput[];
            String splittedConllInput[];
            String line = "";
            String line1 = "";
            BufferedReader br = null;
            BufferedReader br1 = null;
            InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Input/output.txt");
            InputStream inputStream1 = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Input/Seraji treebank/train.conll");
            br = new BufferedReader(new InputStreamReader(inputStream));
            br1 = new BufferedReader(new InputStreamReader(inputStream1));
            while ((line = br.readLine()) != null) {
                //line = line.replaceAll("[\uFEFF]", " ");
                lineCounter += 1;
                splittedConllOutput = line.split("[\\*\\$]");
                if ((splittedConllOutput.length == 3) ) {
                    //  && (!splittedConllOutput[1].equals(splittedConllOutput[2]))
                    //FileWriter fw = new FileWriter(file2,true);
                    //bw = new BufferedWriter(fw);
                    /*while (line1Counter != lineCounter) {
                        line1 = br1.readLine();
                        if (!line1.equals("\r\n") && !line1.equals(""))
                            line1Counter += 1;
                    }*/
                    line1 = br1.readLine();
                    if (line1 != null && !line1.equals("\r\n") && !line1.equals("")) {
                        // if (!line1.equals("\r\n")) {
                        int index = line1.indexOf("_");
                        //bw.write(splittedConllOutput[2],index-5,1);
                        sb = new StringBuilder(line1);
                        sb.insert(index, splittedConllOutput[2]);
                        sb.deleteCharAt(splittedConllOutput[2].length() + index);
                        //sb.replace(index,splittedConllOutput[2].length() + index,splittedConllOutput[2]);
                        bw.append(sb);
                        bw.append("\r\n");
                        System.out.println(sb);
                        /*if (bw != null)
                            bw.close();*/

                        //bw.write("hello");
                        //m+=1;
                        //splittedConllInput[2] = splittedConllOutput[1];
                        //  }
                        //else
                        // line1 = br1.readLine();
                    }
                    else {
                        line1 = br1.readLine();
                        int index = line1.indexOf("_");
                        //bw.write(splittedConllOutput[2],index-5,1);
                        sb = new StringBuilder(line1);
                        sb.insert(index,splittedConllOutput[2]);
                        sb.deleteCharAt(splittedConllOutput[2].length() + index);
                        //sb.replace(index,splittedConllOutput[2].length() + index,splittedConllOutput[2]);
                        bw.append(sb);
                        bw.append("\r\n");
                        System.out.println(sb);
                    }

                }
                else {
                    minReplaced = splittedConllOutput[2];
                    for (int i=2; i<(splittedConllOutput.length)-1; i++){
                        if (splittedConllOutput[i].length() > splittedConllOutput[i+1].length())
                            minReplaced = splittedConllOutput[i+1];

                    }
                    line1 = br1.readLine();
                    if (line1 != null && line1 != "\r\n" && !line1.equals("")) {
                        int index = line1.indexOf("_");
                        sb = new StringBuilder(line1);
                        sb.insert(index,minReplaced);
                        sb.deleteCharAt(minReplaced.length() + index);
                        //bw = new BufferedWriter(fw);
                        bw.append(sb);
                        bw.append("\r\n");
                        System.out.println(sb);
                    /*if (bw != null)
                        bw.close();*/
                    } else {
                        line1 = br1.readLine();
                        int index = line1.indexOf("_");
                        sb = new StringBuilder(line1);
                        sb.insert(index,minReplaced);
                        sb.deleteCharAt(minReplaced.length() + index);
                        bw.append(sb);
                        bw.append("\r\n");
                        System.out.println(sb);
                    }

                }

            }
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




        /*try {
            Files.write(Paths.get("output.txt"), fileInput.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException ioe) {
            ioe.printStackTrace();
        }*/


    public static void FileCleaner(){
        // empty the current content
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("");
            fw.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }



    public  void writeInString (String newWord){
        finalResult = finalResult + " " + newWord;

    }

    public  String getFinalString (){
        return finalResult;
    }

    public  void removeWhiteSpace(){
        finalResult = finalResult.replaceAll("( )+"," ");
        finalResult = finalResult.trim();
    }

    public static void writeOtherNounFile(){
        try {
            String line = "";
            BufferedReader br = null;
            InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Noun1.txt");
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '\u200c')
                    line = line.substring(1,line.length());
                else if (line.charAt(line.length()-1) == '\u200c')
                    line = line.substring(0,line.length()-1);
                BufferedWriter bw = null;
                try {
                    FileWriter fw = new FileWriter(file1, true);
                    bw = new BufferedWriter(fw);
                    bw.write(line);
                    bw.write("\r\n");

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
        } catch (IOException e) {
            e.printStackTrace();}
    }




    public static Map getIntersect(Map map1 , Map map2){
        Map <String,String> removedMap = new WeakHashMap <String,String>(map1);
        Map <String,String> removingMap = new WeakHashMap <String,String>(map2);
        //List<String> removedNouns = new ArrayList<String>();
        //prepHashMap.entrySet().retainAll(nounHashMap.entrySet());

        reentrantLock.lock();
        try {
            for (String key : removingMap.keySet()) {
                if (removedMap.containsKey(key)) {
                    removedMap.remove(key);
                    //removedNouns.add(key);
                }
            }
        } finally {
            reentrantLock.unlock();
        }

        return removedMap;
    }

    public static Map getNoun_PrepIntersect (){
        if (noun_prepositionIntersect.isEmpty())
            noun_prepositionIntersect = new HashMap<>(getIntersect(getPureNoun(),getMapPreposition()));
        return noun_prepositionIntersect;
    }

    public static Map getPrep_NounIntersect (){
        if (prep_nounIntersec.isEmpty())
            prep_nounIntersec = new HashMap<>(getIntersect(getMapPreposition() , getPureNoun()));
        return prep_nounIntersec;
    }




    public  List distinctDetector(List<String> inputList) {
        /*String phrase1 = "";
          String phrase2 = "";
          boolean similar = false;*/

        Map<String, Integer> hashed = new HashMap<>();
        for (int i = 0; i < inputList.size(); i++) {
            if (!hashed.containsKey(inputList.get(i))) {
                hashed.put(inputList.get(i), i);
            } else {
                inputList.set(i, "a");
                repeatedPhrasesIndexes.add(i);
            }
        }
            /*for (int i = 0; i < inputList.size() - 1; i++) {
                phrase1 = inputList.get(i);
                for (int j = i + 1; j < inputList.size(); j++) {
                    phrase2 = inputList.get(j);
                    similar = true;
                    for (int k = 0; k < phrase1.length(); k++) {
                        if (phrase1.charAt(k) != phrase2.charAt(k)) {
                            similar = false;
                            break;
                        }
                    }
                    if (similar) {
                        phrasesIndexes.add(j);
                        //inputList.remove(j);
                        inputList.set(j, "a");
                        //j --;
                    }
                }
            }
*/

        hashed = null;
        return inputList;
    }

    public void setPhrasesIndexes(int i){
        repeatedPhrasesIndexes.add(i);
    }

    public List getPhrasesIndexes(){
        return repeatedPhrasesIndexes;
    }

    public static Map<String,String> getModifiedMapAn() {

        if (modifedPluralAn.isEmpty()) {
            modifedPluralAn = new HashMap<String,String>(getMapAn());
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Noun&PrepIntersect.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    modifedPluralAn.remove(line);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return modifedPluralAn;
    }


    public static void setHash_Null(){
        pureNoun = null;
        pluralAn = null;
        pluralAt = null;
        pluralGan = null;
        pluralUnIn = null;
        pastV = null;
        presentV = null;
        preposition = null;
        unknown = null;
        halfSpace = null;
        halfSpaceVerbs = null;
        compoundVerb = null;
        noun_prepositionIntersect = null;
        prep_nounIntersec = null;

    }

    /////This part is related to keyword extraction of english documents
    /*public static Map getOriginalKeywords(){
        if (keywordsList_original.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/KeywordInput_NLPLab/art_and_culture-20893614_test.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    keywordsList_original.put(line, line);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return keywordsList_original;

    }*/

    public static List<String> MarujoFileSelector_InputDocument(){

        File folder = new File("/Users/fazeletavakoli/IdeaProjects/stanford-corenlp/testData");
        File[] listOfFiles = folder.listFiles();
        int counter = 1;
        List <String> desiredFiles = new ArrayList<>();
        boolean isTrue = false; //for ignoring a .DS_Store file in the folder, which is located at the beginning of the folder

        for (File file : listOfFiles) {
            if (!file.getPath().contains(".DS_Store")) {
                if (counter % 4 == 0) {

                    String s = file.getPath();
                    desiredFiles.add(s);
                }
                counter ++;
            }


        }
        return desiredFiles;
    }

    public static List<String> MarujoFileSelector_Test(){

        File folder = new File("/Users/fazeletavakoli/IdeaProjects/stanford-corenlp/testData");
        File[] listOfFiles = folder.listFiles();
        int counter = 1;
        List <String> desiredFiles = new ArrayList<>();
        boolean isTrue = false; //for ignoring a .DS_Store file in the folder, which is located at the beginning of the folder

        for (File file : listOfFiles) {

            if(file.getName().contains(".key")){
                String s = file.getPath();
                desiredFiles.add(s);
            }



        }

        return desiredFiles;
    }

    //this method converts .key files to .txt files
    public static String convertKeyToTxt(File inputFile) {
        int index = inputFile.getPath().indexOf(".");
        String primaryName = inputFile.getPath().substring(0,index);
        //use file.renameTo() to rename the file
        inputFile.renameTo(new File(primaryName +"."+"txt"));
        return inputFile.getPath();

    }

    public static void writeInFile_version2(String fileInput,File inputFile) {
        BufferedWriter bw = null;
        try {
            FileWriter fw = new FileWriter(inputFile, true);
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

    public static void FileCleaner_version2(File inputFile){
        // empty the current content
        try {
            FileWriter fw = new FileWriter(inputFile);
            fw.write("");
            fw.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public static void computeAvgPrecRecal(String filePath){
        int index=0;
        int index1=0;
        Double precisionDouble =0.0;
        Double recallDouble = 0.0;
        Double counter = 0.0;
        try {
            String line = "";
            BufferedReader br = null;
            InputStream inputStream = new FileInputStream(filePath);
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((line = br.readLine()) != null) {
                if (!line.equals("")) {
                    if (line.contains("precision")) {
                        index = line.indexOf("precision");
                    }
                    if (line.contains("Recall")) {
                        index1 = line.indexOf("Recall");
                    }
                    String precisionStr = line.substring(index + 11, index1 - 2);
                    precisionDouble += Double.parseDouble(precisionStr);
                    String recallStr = line.substring(index1 + 8);
                    recallDouble += Double.parseDouble(recallStr);
                    counter++;
                }
            }
            Double avgPrecision = precisionDouble/counter;
            Double avgRecall = recallDouble/counter;
            System.out.println("average precision: "+ avgPrecision +"\n" + "average recall: " + avgRecall);
        }catch (Exception e){

        }
    }


    public static Map getEnglishStopWOrds(){
        if (englishStopWords.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/IdeaProjects/stanford-corenlp/stopwordsList.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    englishStopWords.put(line, line);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return englishStopWords;

    }

    //////////////////////end of keyword extraction for English documents///////////////////


/*//Modifying ATPnouns
    public static void writeInFile(String fileInput, File file1){
        BufferedWriter bw=null;
        try{
        FileWriter fw=new FileWriter(file1);
        bw=new BufferedWriter(fw);
        bw.write(fileInput);
        bw.write("\r\n");
        System.out.println("File written Successfully");

        }catch(IOException ioe){
        ioe.printStackTrace();
        }finally{
        try{
        if(bw!=null)
        bw.close();
        }catch(Exception ex){
        System.out.println("Error in closing the BufferedWriter"+ex);
        }

        }
        }



    public static void modifyAtPNounsFile(File file1){
        try {
            String line = "";
            String text = " ";
            BufferedReader br = null;
            InputStream inputStream = new FileInputStream("D:\\Irsa\\mainInput\\AtPNouns2.txt");
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null)
                if (line.contains("$")) {
                    int index = line.indexOf('$');
                    if (line != null) {
                        line = line.substring(0, index);
                        writeInFile(line,file1);
                    }
                }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }*/





/*    public static List getWordList(String filePath){
        List <String> wordList = new ArrayList<String>();
            try{
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream(filePath);
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    wordList.add(line);
            }catch (IOException e){
                e.printStackTrace();
            }
        return wordList;
    }

    public static List getNounList(){
        List <String> pureNounList = new ArrayList<String>(getWordList("D:\\Irsa\\mainInput\\Noun11.txt"));
        return pureNounList;
    }

    public static List getPrepList(){
        List <String> prepList = new ArrayList<String>(getWordList("D:\\Irsa\\mainInput\\prepV.txt"));
        return prepList;
    }

    public static List getunknownList(){
        List <String> unknownList = new ArrayList <String>(getWordList("D:\\Irsa\\mainInput\\unknownWords.txt"));
                return unknownList;
    }

    public static List getIntersect(List list1, List list2){
        List <String> removedList = new ArrayList <String>(list1);
        List <String> removingList = new ArrayList <String>(list2);
        //prepHashMap.entrySet().retainAll(nounHashMap.entrySet());
        for (int i=0; i< removingList.size(); i++ ){
            if (removedList.contains(removingList.get(i))){
                removedList.remove(removingList.get(i));
                i = i-1;
            }
        }
        return removedList;
    }

    public static Map convertListToMap(List <String> list, Map <String,String> map){
        for (String item: list)
            map.put(item,item);
        return map;
    }*/




}

