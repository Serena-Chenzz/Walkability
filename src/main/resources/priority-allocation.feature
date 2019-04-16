Feature: Priority Allocation
  As a walkability workflow user
  I would like to be able to allocate land parcels based on a prioritised list of land uses

  Scenario: Simple Priority Allocation using OMS component
    Given a priority allocation OMS component
    Given a "regions" shapefile dataset from "/idealised_data/servicearea.shp"
    And a "parcels" shapefile dataset from "/idealised_data/cadastre.shp"
    And a "points" shapefile dataset from "/idealised_data/lut_join_tenure.shp"
    And a priority list of land uses
    And a land use attribute "pluc"
    When the OMS component is asked allocate
    Then 2 allocated parcels will be returned

  Scenario: Simple Priority Allocation with String priorities
    Given a priority allocation OMS component
    Given a "regions" shapefile dataset from "/idealised_data/servicearea.shp"
    And a "parcels" shapefile dataset from "/idealised_data/cadastre.shp"
    And a "points" shapefile dataset from "/idealised_data/lut_join_tenure.shp"
    And a priority list of land uses with String type
    And a land use attribute "pluc"
    When the OMS component is asked allocate
    Then 2 allocated parcels will be returned

  Scenario: Simple Priority Allocation with Long prioroities
    Given a priority allocation OMS component
    Given a "regions" shapefile dataset from "/idealised_data/servicearea.shp"
    And a "parcels" shapefile dataset from "/idealised_data/cadastre.shp"
    And a "points" shapefile dataset from "/idealised_data/lut_join_tenure.shp"
    And a priority list of land uses with Long type
    And a land use attribute "pluc"
    When the OMS component is asked allocate
    Then 2 allocated parcels will be returned
