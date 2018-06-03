package progproj.homework;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Scanner to get the Filepaths
        /**Scanner s = new Scanner(System.in);

        //Gff-Filepath
        System.out.println("Enter GFF-Filepath:");
        Path gffFilePath = Paths.get(s.next());


        System.out.println(gffFilePath.toString());
        while (Files.notExists(gffFilePath)){
            System.out.println("Invalid Filepath, enter a corrent Gff-Filepath:");
            gffFilePath = Paths.get(s.next());
        }
        //Fasta Filepath
        System.out.println("Enter FASTA-Filepath:");
        Path fastaFilePath = Paths.get(s.next());
        while (Files.notExists(gffFilePath)){
            System.out.println("Invalid Filepath, enter a corrent Fasta-Filepath:");
            fastaFilePath = Paths.get(s.next());
        }

        //Create GffIO
        GffIO gffFile = new GffIO();
        gffFile.readGff(gffFilePath.toString());
        //Create FastaIO
        FastAIO fastaFile = new FastAIO();
        fastaFile.readFastA("src/FastAFiles/GCF_000865085.1_ViralMultiSegProj15622_genomic.fasta");
         PushTest
        **/
        //Test Methods for Test purposes preinitialized paths:
        try {
            Sample sample = SampleReader.read("src/FastAFiles/GCF_000865085.1_ViralMultiSegProj15622_genomic.fasta", "src/GffFile/GCF_000865085.1_ViralMultiSegProj15622_genomic.gff");
            //.out.println(SampleReader.getSample().getReads().get(0).getId());
            System.out.println(sample.getReads().get(0).getId());
            System.out.println(sample.getReads().get(0).getGFFEntries());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
