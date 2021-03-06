package org.cbioportal.genome_nexus.service.annotation;

import org.cbioportal.genome_nexus.model.VariantAnnotation;
import org.cbioportal.genome_nexus.service.mock.CanonicalTranscriptResolverMocker;
import org.cbioportal.genome_nexus.service.mock.VariantAnnotationMockData;
import org.cbioportal.genome_nexus.service.mock.VariantClassificationResolverMocker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProteinChangeResolverTest
{
    @InjectMocks
    private ProteinChangeResolver proteinChangeResolver;

    @Mock
    private VariantClassificationResolver variantClassificationResolver;

    @Mock
    private CanonicalTranscriptResolver canonicalTranscriptResolver;

    private final VariantAnnotationMockData variantAnnotationMockData = new VariantAnnotationMockData();
    private final CanonicalTranscriptResolverMocker canonicalTranscriptResolverMocker = new CanonicalTranscriptResolverMocker();
    private final VariantClassificationResolverMocker variantClassificationResolverMocker = new VariantClassificationResolverMocker();

    @Test
    public void resolveHgvspShortForCanonical() throws IOException
    {
        Map<String, VariantAnnotation> variantMockData = this.variantAnnotationMockData.generateData();
        this.canonicalTranscriptResolverMocker.mockMethods(variantMockData, this.canonicalTranscriptResolver);
        this.variantClassificationResolverMocker.mockMethods(variantMockData, this.variantClassificationResolver);

        assertEquals(
            "L431Vfs*22",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("1:g.65325832_65325833insG"))
        );

        assertEquals(
            "S124_S127del",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("3:g.14106026_14106037delCCAGCAGTAGCT"))
        );

        assertEquals(
            "H1034delinsPY",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("3:g.14940279_14940280insCAT"))
        );

        assertEquals(
            "P692Lfs*43",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("3:g.114058003_114058003delG"))
        );

        assertEquals(
            "G432delinsES",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("4:g.9784947_9784948insAGA"))
        );

        assertEquals(
            "D1450*",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("4:g.77675978_77675979insC"))
        );

        assertEquals(
            "S378Ffs*6",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("6:g.137519505_137519506delCT"))
        );

        assertEquals(
            "S378Ffs*5",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("6:g.137519505_137519506delCTinsA"))
        );

        assertEquals(
            "V600E",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("7:g.140453136A>T"))
        );

        assertEquals(
            "A765Rfs*98",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("8:g.37696499_37696500insG"))
        );

        assertEquals(
            "M209Ifs*2",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("9:g.135797242_135797242delCinsAT"))
        );

        assertEquals(
            "R646Gfs*22",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("10:g.101953779_101953779delT"))
        );

        assertEquals(
            "Q928*",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("11:g.62393546_62393547delGGinsAA"))
        );

        assertEquals(
            "G12C",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("12:g.25398285C>A"))
        );

        assertEquals(
            "F594_D600delinsSPPPH",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("13:g.28608258_28608275delCATATTCATATTCTCTGAinsGGGGTGGGGGGG"))
        );

        assertEquals(
            "Q10delinsHR",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("16:g.9057113_9057114insCTG"))
        );

        assertEquals(
            "X218_splice",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("19:g.46141892_46141893delTCinsAA"))
        );

        assertEquals(
            "K416E",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("22:g.29091840_29091841delTGinsCA"))
        );

        assertEquals(
            "E1350del",
            this.proteinChangeResolver.resolveHgvspShort(variantMockData.get("22:g.36689419_36689421delCCT"))
        );
    }

    @Test
    public void resolveHgvspShort() throws IOException
    {
        Map<String, VariantAnnotation> variantMockData = this.variantAnnotationMockData.generateData();
        this.variantClassificationResolverMocker.mockMethods(variantMockData, this.variantClassificationResolver);

        assertNull(
            this.proteinChangeResolver.resolveHgvspShort(
                variantMockData.get("8:g.37696499_37696500insG"),
                variantMockData.get("8:g.37696499_37696500insG").getTranscriptConsequences().get(0)
            )
        );

        assertNull(
            this.proteinChangeResolver.resolveHgvspShort(
                variantMockData.get("11:g.62393546_62393547delGGinsAA"),
                variantMockData.get("11:g.62393546_62393547delGGinsAA").getTranscriptConsequences().get(0)
            )
        );

        assertEquals(
            "X17_splice",
            this.proteinChangeResolver.resolveHgvspShort(
                variantMockData.get("19:g.46141892_46141893delTCinsAA"),
                variantMockData.get("19:g.46141892_46141893delTCinsAA").getTranscriptConsequences().get(0)
            )
        );

        assertEquals(
            "K373E",
            this.proteinChangeResolver.resolveHgvspShort(
                variantMockData.get("22:g.29091840_29091841delTGinsCA"),
                variantMockData.get("22:g.29091840_29091841delTGinsCA").getTranscriptConsequences().get(0)
            )
        );
    }
}
