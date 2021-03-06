package org.cbioportal.genome_nexus.service.internal;

import org.cbioportal.genome_nexus.model.Hotspot;
import org.cbioportal.genome_nexus.model.IntegerRange;
import org.cbioportal.genome_nexus.model.TranscriptConsequence;
import org.cbioportal.genome_nexus.model.VariantAnnotation;
import org.cbioportal.genome_nexus.service.annotation.ProteinPositionResolver;
import org.cbioportal.genome_nexus.service.annotation.VariantClassificationResolver;
import org.cbioportal.genome_nexus.util.Numerical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class HotspotFilter
{
    private final VariantClassificationResolver variantClassificationResolver;
    private final ProteinPositionResolver proteinPositionResolver;

    @Autowired
    public HotspotFilter(VariantClassificationResolver variantClassificationResolver,
                         ProteinPositionResolver proteinPositionResolver)
    {
        this.variantClassificationResolver = variantClassificationResolver;
        this.proteinPositionResolver = proteinPositionResolver;
    }

    public Boolean filter(Hotspot hotspot, TranscriptConsequence transcript, VariantAnnotation annotation)
    {
        IntegerRange proteinPos = this.proteinPositionResolver.resolve(annotation, transcript);

        return (
            proteinPos != null &&
            // filter by protein position:
            // only include the hotspot if the protein change position overlaps with the current transcript
            Numerical.overlaps(hotspot.getResidue(), proteinPos.getStart(), proteinPos.getEnd()) &&
            // filter by mutation type:
            // only include the hotspot if the variant classification matches the hotspot type
            this.typeMatches(hotspot.getType(), this.variantClassificationResolver.resolveAll(annotation, transcript))
        );
    }

    public Boolean typeMatches(String hotspotType, Set<String> variantClassifications)
    {
        Boolean typeMatches = false;

        for (String variantClassification : variantClassifications)
        {
            // just one match is enough
            if (typeMatches(hotspotType, variantClassification)) {
                typeMatches = true;
                break;
            }
        }

        return typeMatches;
    }

    public Boolean typeMatches(String hotspotType, String variantClassification)
    {
        Boolean typeMatches = true;

        // for single residue hotspots, filter out anything but missense mutations
        if (hotspotType.contains("single residue")) {
            typeMatches = variantClassification.toLowerCase().contains("missense");
        }
        else if (hotspotType.contains("splice site")) {
            typeMatches = variantClassification.toLowerCase().contains("splice");
        }
        // for in-frame indel hotspots, filter out anything but in-frame mutations
        else if (hotspotType.contains("in-frame"))  {
            typeMatches = variantClassification.toLowerCase().contains("inframe") ||
                variantClassification.toLowerCase().contains("in_frame");
        }
        else if (hotspotType.contains("3d"))  {
            typeMatches = variantClassification.toLowerCase().contains("missense");
        }

        return typeMatches;
    }
}
