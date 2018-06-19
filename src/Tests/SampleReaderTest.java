
import Model.GffEntry;
import Model.IO.SampleReader;
import Model.Sample;
import org.junit.Test;

import java.io.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class SampleReaderTest {

    @Test
    /**
     * Testing Reading functionality
     */
    public void readTest() throws Exception {
        String fastaFile = ">NC_007373.1 Influenza A virus (A/New York/392/2004(H3N2)) segment 1, complete sequence\n" +
                "AGCAAAAGCAGGTCAATTATATTCAGTATGGAAAGAATAAAAGAACTACGGAACCTGATGTCGCAGTCTCGCACTCGCGA";
        String gffFile = "NC_007373.1\tRefSeq\tregion\t1\t2341\t.\t+\t.\tID=id0;Dbxref=taxon:335341;collection-date=21-Dec-2004;country=USA: Tompkins County%2C NY;gbkey=Src;genome=genomic;isolation-source=gender:M%3B age:16y;lab-host=RhMK 1 passage(s);mol_type=viral cRNA;nat-host=human;segment=1;serotype=H3N2;strain=A/New York/392/2004\n" +
                "NC_007373.1\tRefSeq\tgene\t28\t2307\t.\t+\t.\tID=gene0;Dbxref=GeneID:3655161;Name=PB2;gbkey=Gene;gene=PB2;gene_biotype=protein_coding;locus_tag=FLUAVH3N2_s1p1\n" +
                "NC_007373.1\tRefSeq\tCDS\t28\t2307\t.\t+\t0\tID=cds0;Parent=gene0;Dbxref=Genbank:YP_308849.1,GeneID:3655161;Name=YP_308849.1;gbkey=CDS;gene=PB2;product=polymerase PB2;protein_id=YP_308849.1";
        String taxaFile = "NC_007373.1\t1";

        byte[] fasta = fastaFile.getBytes();
        byte[] gff = gffFile.getBytes();
        byte[] taxa = taxaFile.getBytes();

        InputStream fastaInput = new ByteArrayInputStream(fasta);
        InputStream gffInput = new ByteArrayInputStream(gff);
        InputStream taxaInput = new ByteArrayInputStream(taxa);

        InputStreamReader fastaReader = new InputStreamReader(fastaInput);
        InputStreamReader gffReader = new InputStreamReader(gffInput);
        InputStreamReader taxaReader = new InputStreamReader(taxaInput);

        Sample sample = SampleReader.read(fastaReader, gffReader, taxaReader);
        assertEquals(sample.getReads().size(), 1);
        assertEquals(sample.getReads().get(0).getGFFEntries().size(), 3);
        assertEquals(sample.getReads().get(0).getSequence(),"AGCAAAAGCAGGTCAATTATATTCAGTATGGAAAGAATAAAAGAACTACGGAACCTGATGTCGCAGTCTCGCACTCGCGA");
        assertTrue(sample.getReads().get(0).getTaxonomicId() == 1);

        for(int i = 0; i < sample.getReads().get(0).getGFFEntries().size(); i++){
            GffEntry gffEnt = sample.getReads().get(0).getGFFEntries().get(i);
            assertEquals(gffEnt.getSequence(), "NC_007373.1");
            assertEquals(gffEnt.getSource(), "RefSeq");
            switch(i){
                case 0: assertEquals(gffEnt.getFeature(),"region");
                        assertEquals(gffEnt.getStart(),1);
                        assertEquals(gffEnt.getEnd() , 2341);
                    break;
                case 1: assertEquals(gffEnt.getFeature(),"gene");
                        assertEquals(gffEnt.getStart(), 28);
                        assertEquals(gffEnt.getEnd(), 2307);
                    break;
                case 2: assertEquals(gffEnt.getFeature(), "CDS");
                        assertEquals(gffEnt.getStart(),28);
                        assertEquals(gffEnt.getEnd(), 2307);
                    break;
                default: assertTrue(false);
            }
        }

    }


    @Test
    /**
     * With wrong filepaths you shouldnt be able to read
     */
    public void wrongFilePath(){
        try {
            SampleReader.read("asdf", "asdf", "asdf");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

}
