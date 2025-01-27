package com.augusto.test.web.controller;

import com.augusto.test.spring.version.VersionedResource;
import com.augusto.test.web.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@VersionedResource(media = "application/vnd.app.resource")
@RequestMapping("/arthur")
public class PeopleController {
    private Logger logger = LoggerFactory.getLogger(PeopleController.class);

    @RequestMapping(value = {"/people/{id}"}, method = RequestMethod.GET)
    @VersionedResource(from = "1.0")
    @ResponseBody
    public Person getPeople_v1(@PathVariable("id") Integer id) {
        logger.info("called /people with v1.0");

        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAge(27 + id);

        return person;
    }

    @RequestMapping(value = {"/people/{id}"}, method = RequestMethod.GET)
    @VersionedResource(from = "2.0")
    @ResponseBody
    public Person getPeople_v2(@PathVariable("id") Integer id) {
        logger.info("called /people with v2.0");

        Person person = new Person();
        person.setFirstName("arthur");
        person.setLastName("Doe");
        person.setAge(27 + id);

        return person;
    }

    @RequestMapping(value = {"/people/{id}"}, method = RequestMethod.GET)
    @VersionedResource(from = "2.1")
    @ResponseBody
    public Person getPeople_v21(@PathVariable("id") Integer id) {
        logger.info("called /people with v2.1");

        Person person = new Person();
        person.setFirstName("Tim");
        person.setLastName("Doe");
        person.setAge(27 + id);

        return person;
    }

    @RequestMapping(value = {"/person/{id}"}, method = RequestMethod.GET)
    @VersionedResource(from = "2.0")
    @ResponseBody
    public Person getPerson_v2(@PathVariable("id") Integer id) {
        logger.info("called /people with v2.0");

        Person person = new Person();
        person.setFirstName("Carl");
        person.setLastName("Marx");
        person.setAge(55 + id);

        return person;
    }

    @RequestMapping(value = {"/person/{id}"}, method = RequestMethod.GET)
    @VersionedResource(from = "2.1")
    @ResponseBody
    public Person getPerson_latest() {
        logger.info("called /people latest");

        Person person = new Person();
        person.setFirstName("Douglas");
        person.setLastName("Adams");
        person.setAge(42);

        return person;
    }

}
