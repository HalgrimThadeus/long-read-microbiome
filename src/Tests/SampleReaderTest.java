
import Model.IO.SampleReader;
import Model.Sample;
import org.junit.Test;

import java.io.*;

public class SampleReaderTest {

    @Test
    public void allFastAEntriesAreInSample() {
        try {
            File fasta = File.createTempFile("FastA", ".fasta");
            File gff = File.createTempFile("Gff", ".gff");

            BufferedWriter fastaWriter = new BufferedWriter(new FileWriter(fasta));
            BufferedWriter gffWriter = new BufferedWriter(new FileWriter(gff));

            fastaWriter.write(">NC_007373.1 Influenza A virus (A/New York/392/2004(H3N2)) segment 1, complete sequence\n" +
                    "AGCAAAAGCAGGTCAATTATATTCAGTATGGAAAGAATAAAAGAACTACGGAACCTGATGTCGCAGTCTCGCACTCGCGA");
            gffWriter.write("NC_007373.1\tRefSeq\tregion\t1\t2341\t.\t+\t.\tID=id0;Dbxref=taxon:335341;collection-date=21-Dec-2004;country=USA: Tompkins County%2C NY;gbkey=Src;genome=genomic;isolation-source=gender:M%3B age:16y;lab-host=RhMK 1 passage(s);mol_type=viral cRNA;nat-host=human;segment=1;serotype=H3N2;strain=A/New York/392/2004\n" +
                    "NC_007373.1\tRefSeq\tgene\t28\t2307\t.\t+\t.\tID=gene0;Dbxref=GeneID:3655161;Name=PB2;gbkey=Gene;gene=PB2;gene_biotype=protein_coding;locus_tag=FLUAVH3N2_s1p1\n" +
                    "NC_007373.1\tRefSeq\tCDS\t28\t2307\t.\t+\t0\tID=cds0;Parent=gene0;Dbxref=Genbank:YP_308849.1,GeneID:3655161;Name=YP_308849.1;gbkey=CDS;gene=PB2;product=polymerase PB2;protein_id=YP_308849.1");
            fastaWriter.close();
            gffWriter.close();

            Sample sample = SampleReader.read(fasta.getAbsolutePath(), gff.getAbsolutePath());
            assert sample.getReads().size() == 1;
            assert sample.getReads().get(0).getGFFEntries().size() == 3;
            //assert sample.getReads().get(0).getSequence().equals("AGCAAAAGCAGGTCAATTATATTCAGTATGGAAAGAATAAAAGAACTACGGAACCTGATGTCGCAGTCTCGCACTCGCGA");

            fasta.delete();
            gff.delete();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void wrongFilePath(){
        try {
            SampleReader.read("asdf", "asdf");
        } catch (Exception e) {
            assert e.getMessage().equals("asdf (Das System kann die angegebene Datei nicht finden)");
        }
    }

}
