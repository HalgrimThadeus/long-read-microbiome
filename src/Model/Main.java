package Model;

import Model.IO.SampleReader;
import Model.*;
import Model.Tax.TaxIO;


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
            Sample sample = SampleReader.read("res/FastAFiles/GCF_000865085.1_ViralMultiSegProj15622_genomic.fasta", "res/GffFile/GCF_000865085.1_ViralMultiSegProj15622_genomic.gff");

            for (int i= 0; i < sample.getReads().size(); i++){
                System.out.println(sample.getReads().get(i).getGFFEntries().size());
                System.out.println(sample.getReads().get(i).getGFFEntries());
                //System.out.println(sample.getReads().get(sample.getReads().size() - 1).getGFFEntries());

            }
            //TaxIO treeReader = new TaxIO();
            //treeReader.readInTaxTree("C:\\Users\\manug\\Desktop\\nodes.dmp", "C:\\Users\\manug\\Desktop\\names.dmp");

            //test.allFastAEntriesAreInSample("src/FastAFiles/GCF_000865085.1_ViralMultiSegProj15622_genomic.fasta", "src/GffFile/GCF_000865085.1_ViralMultiSegProj15622_genomic.gff");

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        ReadTest test2 = new ReadTest();
        test2.idShouldBeProcessedOutOfTheHeader();
        test2.gffEntriesShouldntBeNull();
        */



    }
}
