package com.projects.universityapiconsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import com.projects.universityapiconsumer.utils.ApiUtils;

@SpringBootTest
public class UniversityApiConsumerApplicationTests {

      @Autowired
      private ApiUtils utils;

      @Autowired
      private UniversityApiTestUtils testUtils;

      @Test
      public void testGetAllUniversitiesWithDefaultPageable() {
          assertEquals(utils.getPageFromList(PageRequest.of(0, 20), true, testUtils.createUniversityList()).getNumber(),
                  testUtils.createUniversitiesResponseForDefaultPageable().getNumber());
          assertEquals(utils.getPageFromList(PageRequest.of(0, 20), true, testUtils.createUniversityList()).getNumberOfElements(),
                  testUtils.createUniversitiesResponseForDefaultPageable().getNumberOfElements());
          assertEquals(utils.getPageFromList(PageRequest.of(0, 20), true, testUtils.createUniversityList()).getSize(),
                  testUtils.createUniversitiesResponseForDefaultPageable().getSize());
          assertEquals(utils.getPageFromList(PageRequest.of(0, 20), true, testUtils.createUniversityList()).getTotalPages(),
                  testUtils.createUniversitiesResponseForDefaultPageable().getTotalPages());
      }

      @Test
      public void testGetAllUniversitiesWithPage2Size1() {
          assertEquals(utils.getPageFromList(PageRequest.of(1, 2), true, testUtils.createUniversityList()).getNumber(),
                  testUtils.createUniversitiesResponseForPage1Size2().getNumber());
          assertEquals(utils.getPageFromList(PageRequest.of(1, 2), true, testUtils.createUniversityList()).getNumberOfElements(),
                  testUtils.createUniversitiesResponseForPage1Size2().getNumberOfElements());
          assertEquals(utils.getPageFromList(PageRequest.of(1, 2), true, testUtils.createUniversityList()).getSize(),
                  testUtils.createUniversitiesResponseForPage1Size2().getSize());
          assertEquals(utils.getPageFromList(PageRequest.of(1, 2), true, testUtils.createUniversityList()).getTotalPages(),
                  testUtils.createUniversitiesResponseForPage1Size2().getTotalPages());
      }

      @Test
      public void testGetAllUniversitiesWithPage0Size1() {
          assertEquals(utils.getPageFromList(PageRequest.of(0, 1), true, testUtils.createUniversityList()).getNumber(),
                  testUtils.createUniversitiesResponseForPage0Size1().getNumber());
          assertEquals(utils.getPageFromList(PageRequest.of(0, 1), true, testUtils.createUniversityList()).getNumberOfElements(),
                  testUtils.createUniversitiesResponseForPage0Size1().getNumberOfElements());
          assertEquals(utils.getPageFromList(PageRequest.of(0, 1), true, testUtils.createUniversityList()).getSize(),
                  testUtils.createUniversitiesResponseForPage0Size1().getSize());
          assertEquals(utils.getPageFromList(PageRequest.of(0, 1), true, testUtils.createUniversityList()).getTotalPages(),
                  testUtils.createUniversitiesResponseForPage0Size1().getTotalPages());
      }

}
