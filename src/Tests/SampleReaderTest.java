
import Model.GffEntry;
import Model.IO.SampleReader;
import Model.Sample;
import org.junit.Test;

import java.io.*;

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

        byte[] fasta = fastaFile.getBytes();
        byte[] gff = gffFile.getBytes();

        InputStream fastaInput = new ByteArrayInputStream(fasta);
        InputStream gffInput = new ByteArrayInputStream(gff);

        InputStreamReader fastaReader = new InputStreamReader(fastaInput);
        InputStreamReader gffReader = new InputStreamReader(gffInput);

        Sample sample = SampleReader.read(fastaReader, gffReader);
        assert sample.getReads().size() == 1;
        assert sample.getReads().get(0).getGFFEntries().size() == 3;
        assert sample.getReads().get(0).getSequence().equals("AGCAAAAGCAGGTCAATTATATTCAGTATGGAAAGAATAAAAGAACTACGGAACCTGATGTCGCAGTCTCGCACTCGCGA");

        for(int i = 0; i < sample.getReads().get(0).getGFFEntries().size(); i++){
            GffEntry gffEnt = sample.getReads().get(0).getGFFEntries().get(i);
            assert gffEnt.getSequence().equals("NC_007373.1");
            assert gffEnt.getSource().equals("RefSeq");
            switch(i){
                case 0: assert gffEnt.getFeature().equals("region");
                        assert gffEnt.getStart() == 1;
                        assert gffEnt.getEnd() == 2341;
                    break;
                case 1: assert gffEnt.getFeature().equals("gene");
                        assert gffEnt.getStart() == 28;
                        assert gffEnt.getEnd() == 2307;
                    break;
                case 2: assert gffEnt.getFeature().equals("CDS");
                        assert gffEnt.getStart() == 28;
                        assert gffEnt.getEnd() == 2307;
                    break;
                default: assert false;
            }
        }

    }


    @Test
    /**
     * With wrong filepaths the gff-Entry should report a specific error
     */
    public void wrongFilePath(){
        try {
            SampleReader.read("asdf", "asdf");
        } catch (Exception e) {
            assert e.getMessage().equals("asdf (Das System kann die angegebene Datei nicht finden)");
        }
    }

}
