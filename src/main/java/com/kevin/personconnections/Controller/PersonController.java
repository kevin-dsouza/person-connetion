package com.kevin.personconnections.Controller;

import com.kevin.personconnections.DataStore.PersonData;
import com.kevin.personconnections.Model.Person;
import com.kevin.personconnections.Service.RelationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PersonController {

    private RelationService relationService = new RelationService();

    @GetMapping
    @RequestMapping("/rest/hello")
    public String hello() {
        return "Hello Human!!";
    }

    // Get a user by id
    // eg http://localhost:8080/rest/persons/3
    @GetMapping
    @RequestMapping(value = "/rest/persons/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> getPerson(@PathVariable("id") int id) {

        return ResponseEntity.status(HttpStatus.OK).body(PersonData.getInstance().getPerson(id));
    }


    // Get the connections from user id=X
    // eg http://localhost:8080/rest/personRelations?id=1&degree=4
    @GetMapping
    @RequestMapping(value = "/rest/personRelations", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> getPersonRelations(@RequestParam Map<String, String> customQuery) {

        Integer id = Integer.valueOf(customQuery.get("id"));
        Integer degree = Integer.valueOf(customQuery.get("degree"));

        List<Integer> personIds = relationService.findNthDegreeConnections(id, degree);
        return ResponseEntity.status(HttpStatus.OK).body(PersonData.getInstance().convertIdsToPerson(personIds));

    }

    //  How many total connections  does user id=X has?
    //  eg:  http://localhost:8080/rest/personRelations/total?id=1&degree=2
    @GetMapping
    @RequestMapping(value = "/rest/personRelations/total", method = RequestMethod.GET)
    public ResponseEntity<Integer> getPersonRelationsTotal(@RequestParam Map<String, String> customQuery) {

        Integer id = Integer.valueOf(customQuery.get("id"));
        Integer degree = Integer.valueOf(customQuery.get("degree"));

        return ResponseEntity.status(HttpStatus.OK).body(relationService.findNthDegreeConnectionTotal(id, degree));
    }

    // Who can introduce user id=X to user id=Y?
    // eg: http://localhost:8080/rest/commonRelations?firstPerson=1&secondPerson=2
    @GetMapping
    @RequestMapping(value = "/rest/commonRelations", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> getCommonRelations(@RequestParam Map<String, String> customQuery) {

        List<Integer> personIds = relationService.findCommonRelations(Integer.valueOf(customQuery.get("firstPerson")), Integer.valueOf(customQuery.get("secondPerson")));
        return ResponseEntity.status(HttpStatus.OK).body(PersonData.getInstance().convertIdsToPerson(personIds));
    }

    // Which user has the most connections?
    // http://localhost:8080/rest/personRelations/max/?degree=4
    @GetMapping
    @RequestMapping(value = "/rest/personRelations/max", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> getPersonRelationsMax(@RequestParam Map<String, String> customQuery) {

        Integer degree = Integer.valueOf(customQuery.get("degree"));

        List<Integer> personIds = relationService.findPersonWithMostLeastConnections(degree, true);
        return ResponseEntity.status(HttpStatus.OK).body(PersonData.getInstance().convertIdsToPerson(personIds));
    }

    // 	Which user has the least connections?
    // http://localhost:8080/rest/personRelations/min/?degree=4
    @GetMapping
    @RequestMapping(value = "/rest/personRelations/min", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> getPersonRelationsMin(@RequestParam Map<String, String> customQuery) {

        Integer degree = Integer.valueOf(customQuery.get("degree"));

        List<Integer> personIds = relationService.findPersonWithMostLeastConnections(degree, false);
        return ResponseEntity.status(HttpStatus.OK).body(PersonData.getInstance().convertIdsToPerson(personIds));
    }
}


