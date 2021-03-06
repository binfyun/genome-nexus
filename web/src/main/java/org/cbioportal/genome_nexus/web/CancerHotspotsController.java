package org.cbioportal.genome_nexus.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.cbioportal.genome_nexus.model.GenomicLocation;
import org.cbioportal.genome_nexus.model.Hotspot;
import org.cbioportal.genome_nexus.model.AggregatedHotspots;
import org.cbioportal.genome_nexus.service.CancerHotspotService;
import org.cbioportal.genome_nexus.service.exception.CancerHotspotsWebServiceException;
import org.cbioportal.genome_nexus.service.exception.VariantAnnotationNotFoundException;
import org.cbioportal.genome_nexus.service.exception.VariantAnnotationWebServiceException;
import org.cbioportal.genome_nexus.web.config.InternalApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@InternalApi
@RestController // shorthand for @Controller, @ResponseBody
@CrossOrigin(origins="*") // allow all cross-domain requests
@RequestMapping(value= "/")
@Api(tags = "cancer-hotspots-controller", description = "Cancer Hotspots Controller")
public class CancerHotspotsController
{
    private final CancerHotspotService hotspotService;

    @Autowired
    public CancerHotspotsController(CancerHotspotService hotspotService)
    {
        this.hotspotService = hotspotService;
    }

    @ApiOperation(value = "Retrieves hotspot annotations for a specific variant",
        nickname = "fetchHotspotAnnotationByHgvsGET")
    @RequestMapping(value = "/cancer_hotspots/hgvs/{variant:.+}",
        method = RequestMethod.GET,
        produces = "application/json")
    public List<Hotspot> fetchHotspotAnnotationByHgvsGET(
        @ApiParam(value="A variant. For example 7:g.140453136A>T",
            required = true,
            allowMultiple = true)
        @PathVariable String variant)
        throws VariantAnnotationNotFoundException, VariantAnnotationWebServiceException,
        CancerHotspotsWebServiceException
    {
        return this.hotspotService.getHotspotAnnotationsByVariant(variant);
    }

    @ApiOperation(value = "Retrieves hotspot annotations for the provided list of variants",
        nickname = "fetchHotspotAnnotationByHgvsPOST")
    @RequestMapping(value = "/cancer_hotspots/hgvs",
        method = RequestMethod.POST,
        produces = "application/json")
    public List<AggregatedHotspots> fetchHotspotAnnotationByHgvsPOST(
        @ApiParam(value="List of variants. For example [\"7:g.140453136A>T\",\"12:g.25398285C>A\"]",
            required = true,
            allowMultiple = true)
        @RequestBody List<String> variants) throws CancerHotspotsWebServiceException
    {
        return this.hotspotService.getHotspotAnnotationsByVariants(variants);
    }

    @ApiOperation(value = "Retrieves hotspot annotations for a specific genomic location",
        nickname = "fetchHotspotAnnotationByGenomicLocationGET")
    @RequestMapping(value = "/cancer_hotspots/genomic/{genomicLocation:.+}",
        method = RequestMethod.GET,
        produces = "application/json")
    public List<Hotspot> fetchHotspotAnnotationByGenomicLocationGET(
        @ApiParam(value="A genomic location. For example 7,140453136,140453136,A,T",
            required = true,
            allowMultiple = true)
        @PathVariable String genomicLocation)
        throws VariantAnnotationNotFoundException, VariantAnnotationWebServiceException,
        CancerHotspotsWebServiceException
    {
        return this.hotspotService.getHotspotAnnotationsByGenomicLocation(genomicLocation);
    }

    @ApiOperation(value = "Retrieves hotspot annotations for the provided list of genomic locations",
        nickname = "fetchHotspotAnnotationByGenomicLocationPOST")
    @RequestMapping(value = "/cancer_hotspots/genomic",
        method = RequestMethod.POST,
        produces = "application/json")
    public List<AggregatedHotspots> fetchHotspotAnnotationByGenomicLocationPOST(
        @ApiParam(value="List of genomic locations.",
            required = true,
            allowMultiple = true)
        @RequestBody List<GenomicLocation> genomicLocations) throws CancerHotspotsWebServiceException
    {
        return this.hotspotService.getHotspotAnnotationsByGenomicLocations(genomicLocations);
    }
}
