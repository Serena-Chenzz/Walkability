package org.mccaughey.testings;

import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.mccaughey.utilities.ValidationUtils;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class Config {

    public static SimpleFeature buildFeature(SimpleFeature region, Double connectivity, Double density, Double lum) {

        SimpleFeatureType sft = (SimpleFeatureType) region.getType();
        SimpleFeatureTypeBuilder stb = new SimpleFeatureTypeBuilder();
        stb.init(sft);
        stb.setName("ZScoreFeatureType");
        stb.add("Connectivity", Double.class);
        stb.add("Density", Double.class);
        stb.add("LUM", Double.class);
        SimpleFeatureType featureType = stb.buildFeatureType();
        SimpleFeatureBuilder sfb = new SimpleFeatureBuilder(featureType);
        sfb.addAll(region.getAttributes());
        sfb.add(ValidationUtils.isValidDouble(connectivity) ? connectivity : null);
        sfb.add(ValidationUtils.isValidDouble(density) ? density : null);
        sfb.add(ValidationUtils.isValidDouble(lum) ? lum : null);
        return sfb.buildFeature(region.getID());
    }

}
