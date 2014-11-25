package mx.algorithm.baiduzhidao;

import java.io.*;
import java.util.*;

/**
 * Created by hxiong on 7/11/14.
 */
public class BigFileSort {

    static List<Integer> memorySort(String fileName){
        List<Integer> l = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(Configure.WORK_FOLDER + fileName))) {
            String line = null;

            while(null != (line = br.readLine())){
                l.add(Integer.parseInt(line));
            }
            Collections.sort(l);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            l = null;
        } catch (IOException e) {
            e.printStackTrace();
            l = null;
        }
        return l;
    }

    static MergeResult mergeSort(String fileName){
        String srcFolder = splitBigFile(fileName);
        return mergePartition(1, srcFolder);
    }

    /**
     * split big file to small files
     * each file will be sorted too in this step
     * @param fileName
     * @return
     */
    static String splitBigFile(String fileName){
        String destFolder = FileHelper.newFolder();
        int [] tmp = new int [Configure.MAX_LINE];
        try(BufferedReader br = new BufferedReader(new FileReader(Configure.WORK_FOLDER + fileName))){
            String line = null;
            SortFileWriter sfw = new SortFileWriter(0, destFolder);
            int num = 0;
            while(null != (line = br.readLine())){
                if(num >= tmp.length){
                    Arrays.sort(tmp);
                    System.out.println("After sort: tmp=" + Arrays.toString(tmp));
                    for(int i = 0; i < tmp.length; i++){
                        sfw.writeInt(tmp[i]);
                    }
                    sfw.close();
                    sfw = new SortFileWriter(sfw.getIndex() + 1, sfw.getFolder());
                    tmp = new int[Configure.MAX_LINE];
                    num = 0;
                }
                tmp[num++] = Integer.parseInt(line);
            }

            if(0 < num){
                Arrays.sort(tmp, 0, num);
                System.out.println("After sort: tmp=" + Arrays.toString(tmp));
                for(int i = 0; i < num; i++){
                    sfw.writeInt(tmp[i]);
                }
                sfw.close();
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destFolder;
    }


    /**
     * merge sorted file(recursive)
     * @param span
     * @param srcFolder
     * @return
     */
    static MergeResult mergePartition(int span, String srcFolder){
        int maxIndex = FileHelper.getFileNumber(srcFolder);
        if(span >= maxIndex){
            return new MergeResult(srcFolder);
        }
        String destFolder = FileHelper.newFolder();
        int start = 0;
        while(start < maxIndex){
            _mergePartition(start, span, srcFolder, destFolder);
            start += 2 * span;
        }
        return mergePartition(2 * span, destFolder);
    }

    /**
     * core function, merge sorted file
     * @param start
     * @param span
     * @param srcFolder
     * @param destFolder
     */
    static void _mergePartition(int start, int span, String srcFolder, String destFolder){
        int left = start;
        int right = left + span;
        int wIndex = start;
        int maxFileIndex = FileHelper.getFileNumber(srcFolder);
        SortFileWriter wsf = new SortFileWriter(wIndex, destFolder);
        SortFileReader lsf = null;
        SortFileReader rsf = null;
        lsf = getFileReader(left, srcFolder);
        rsf = getFileReader(right, srcFolder);
        if(null != lsf){
            lsf.next();
        }
        if(null != rsf){
            rsf.next();
        }
        while(true){
            if(null == lsf && null == rsf)break;

            if(null == rsf || (null != lsf && null != rsf && lsf.nextInt() < rsf.nextInt())){
                wsf = writeInt(wsf, lsf.nextInt());
                lsf = readNext(lsf, (start + span > maxFileIndex ? maxFileIndex : start + span));
            }else{
                wsf = writeInt(wsf, rsf.nextInt());
                rsf = readNext(rsf, (start + span + span > maxFileIndex ? maxFileIndex : start + span + span));
            }
        }

        wsf.close();
    }

    /**
     * help function to read next number
     * @param sf
     * @param maxIndex
     * @return null if no file exist or has reach to partition bound
     */
    static SortFileReader readNext(SortFileReader sf, int maxIndex){
        SortFileReader sfr = sf;
        if(null != sf){
            sf.next();
            if(!sf.hasNext()){
                sf.close();
                int index = sf.getIndex() + 1;
                if(maxIndex > index){
                    sfr = getFileReader(index, sf.getFolder());
//                    System.out.println("maxIndex:" + maxIndex + " index:" + index);
                    sfr.next();
                }else{
                    sfr = null;
                }
            }
        }
        return sfr;
    }

    /**
     * write number to file, if the file has reach to max size
     * close the file and create an new file to write
     * @param sf
     * @param v
     * @return
     */
    static SortFileWriter writeInt(SortFileWriter sf, int v){
        SortFileWriter wsf = sf;
        if(sf.hasFull()){
            sf.close();
            wsf = new SortFileWriter(sf.getIndex() + 1, sf.getFolder());
        }
        wsf.writeInt(v);
        return wsf;
    }

    /**
     *
     * @param index
     * @param folder
     * @return null if file not found
     */
    static SortFileReader getFileReader(int index, String folder){
        SortFileReader f = new SortFileReader(index, folder);
        if(!f.exist()){
            f = null;
        }
        return f;
    }


    //test cases

    static void test_sort(){
        System.out.println("test_sort BEGIN!");
        cleanWorkFolder();
        String fileName = createTestFile();
        MergeResult mr = mergeSort(fileName);
        System.out.println("-------test result---------");
        printSrcValue(fileName);
        printSortedValue(mr.folderName);
        System.out.println("MemSort   " + memorySort(fileName));
        System.out.println("test_sort END!");
    }

    static void test__mergePartition(){
        System.out.println("test__mergePartition BEGIN!");
        _mergePartition(0, 1, "1", "test__mergePartition");
        System.out.println("test__mergePartition END!");
    }

    static void test_mergePartition(){
        System.out.println("test_mergePartition BEGIN!");
        mergePartition(1, "1");
        System.out.println("test_mergePartition END!");
    }

    static String createTestFile(){
        String fname = "test.txt";
        File f = new File(Configure.WORK_FOLDER + fname);
        if(f.exists()){
            System.out.println("test file exist yet.");
            return fname;
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(Configure.WORK_FOLDER + fname))){
            int i = 0;
            final int MAX = 101;
            while(MAX > i++){
                bw.write((int)(Math.random() * 100) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fname;
    }

    static void cleanWorkFolder(){
        File f = new File(Configure.WORK_FOLDER);
        if(f.exists()){
            File[] fs = f.listFiles();
            for(File _f: fs){
                if(_f.isDirectory()){
                    File[] subFs = _f.listFiles();
                    for(File __f: subFs){
                        __f.delete();
                    }
                    _f.delete();
                }else{
                    _f.delete();
                }
            }
        }
    }

    static void printFolder(String folerName){
        File f = new File(Configure.WORK_FOLDER + folerName);
        File [] fs = f.listFiles();
        for(File _f: fs){
            try(BufferedReader br = new BufferedReader(new FileReader(_f))) {
                String line = null;
                System.out.print("folder[1/" + _f.getName().substring(0, _f.getName().indexOf(".")) + "][");
                while(null != (line = br.readLine())){
                    System.out.print(line + ", ");
                }
                System.out.println("]");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void printSortedValue(String folderName){
        File f = new File(Configure.WORK_FOLDER + folderName);
        System.out.println("The result are put under:" + f.getAbsolutePath());
        File [] fs = f.listFiles();
        List<Integer> tmp = new ArrayList<>();
        for(File _f: fs){
            tmp.add(Integer.parseInt(_f.getName().substring(0, _f.getName().indexOf("."))));
        }
        //must sort as number first
        Collections.sort(tmp);
        System.out.print("Actual    [");
        for(int i: tmp){
            try(BufferedReader br = new BufferedReader(new FileReader(Configure.WORK_FOLDER + folderName
                    + File.separator + i + Configure.FNAME_SUFFIX))) {
                String line = null;
                while(null != (line = br.readLine())){
                    System.out.print(line + ", ");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("]");
    }

    static void printSrcValue(String srcFileName){
        try(BufferedReader br= new BufferedReader(new FileReader(Configure.WORK_FOLDER + srcFileName))) {
            String line = null;
            System.out.print("Original  [");
            while(null != (line = br.readLine())){
                System.out.print(line + ", ");
            }
            System.out.println("]");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * main
     * @param args
     */
    public static void main(String[] args) {
//        test__mergePartition();
//        test_mergePartition();
//        printFolder("1");
        test_sort();
    }
}

/**
 * common configuration
 */
class Configure{
    /**
     * Maximum line allowed in each file, the value is according to memory size
     */
    final static int MAX_LINE = 10;
    /**
     * work space directory
     */
    final static String WORK_FOLDER = "C:\\Users\\hxiong\\Desktop\\Work\\07-09\\bigfile\\";
    /**
     * file name suffix
     */
    final static String FNAME_SUFFIX = ".sort";
}

/**
 * class encapsulate sort result
 */
class MergeResult{
    //which folder to put the sort result
    String folderName;

    MergeResult(String folderName) {
        this.folderName = folderName;
    }
}

/**
 * helper class encapsulate some file operator
 */
class FileHelper{
    private static int count = 0;

    static String getFileName(int index, String folder){
        return Configure.WORK_FOLDER + folder + File.separator + index + Configure.FNAME_SUFFIX;
    }

    static String newFolder(){
        File f = new File(Configure.WORK_FOLDER + String.valueOf(++count));
        if(f.exists()){
            System.out.println("Why folder[" + count + "] existed yet?");
            return newFolder();
        }else{
            f.mkdirs();
        }
        return f.getName();
    }

    static int getFileNumber(String folder){
        File f = new File(Configure.WORK_FOLDER + folder);
        return f.list().length;
    }
}

/**
 * helper class to read file
 */
class SortFileReader{

    private BufferedReader br = null;

    private String _line = "";
    private int index = -1;
    private String folder = null;

    public SortFileReader(int index, String folder){
        try{
            this.index = index;
            this.folder = folder;
            this.br = new BufferedReader(new FileReader(FileHelper.getFileName(index, folder)));
        }catch (FileNotFoundException e) {
            //FIXME:: uncomment here in product enviroment
//            e.printStackTrace();
        }

    }

    public boolean exist(){
        return null != this.br;
    }


    public void next(){
        try {
            this._line = this.br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //never go here;
    }

    public int nextInt(){
        try {
            return Integer.parseInt(this._line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //never go here
        throw new RuntimeException("Unreachable code reached! It may caused by illegal words in file.");
    }


    public boolean hasNext(){
        return (null != this._line);
    }

    public boolean isEnd(){
        return null == this._line;
    }

    public int getIndex(){
        return this.index;
    }

    public String getFolder(){
        return this.folder;
    }

    public void close(){
        if(null != this.br){
            try {
                this.br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

/**
 * helper class to write file
 */
class SortFileWriter{
    private BufferedWriter bw = null;

    private int maxLine = Configure.MAX_LINE;
    private int lineNum = 0;

    private int index = -1;
    private String folder = null;

    public SortFileWriter(int index, String folder){
        try{
            this.index = index;
            this.folder  = folder;
            this.bw = new BufferedWriter(new FileWriter(FileHelper.getFileName(index, folder)));
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean exist(){
        return null != this.bw;
    }



    public void writeInt(int i){
        try {
            this.bw.write(String.valueOf(i) + "\n");
            if(0 == this.lineNum){
                System.out.print("folder[" + this.getFolder() + "/" + this.getIndex() + "][");
            }else{
                System.out.print(", ");
            }
            System.out.print(i);
            this.lineNum++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //never go here
    }

    public boolean hasFull(){
        return this.lineNum >= this.maxLine;
    }

    public int getIndex(){
        return this.index;
    }

    public String getFolder(){
        return this.folder;
    }


    public void close(){
        System.out.println("]");
        if(null != this.bw){
            try {
                this.bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
