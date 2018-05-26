package progproj.homework;

import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args){
        //Scanner to get the Filepaths
        Scanner s = new Scanner(System.in);

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
        fastaFile.readFastA(fastaFilePath.toString());

        //Test Methods:
        






    }
}
