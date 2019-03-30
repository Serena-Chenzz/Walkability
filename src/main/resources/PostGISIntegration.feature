Feature: PostGIS Integration
  As a walkability workflow user
  I would like to be able to run walkability with postgis backed feature sources for better performance

  Scenario: Network Buffer Test
    Given a Network Buffer OMS Component
    Given an Indexing Reader OMS Component
    And a set of points "points"
    And a road network "roads"
    When the "points" and "roads" are loaded into the indexing reader as "indexed-points" and "indexed-roads"
    And the Network Buffer is run with "indexed-points" and "indexed-roads"
    Then the network buffering will complete succesfully
