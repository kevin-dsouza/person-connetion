package com.kevin.personconnections.DataStore;

import com.kevin.personconnections.Interfaces.HasData;
import com.kevin.personconnections.Model.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonData implements HasData {

    static final String PERSON_FILE = "/Users/kdsouza1/Downloads/Person[1].txt";
    static final String RELATIONSHIP_FILE = "/Users/kdsouza1/Downloads/Relationship[1].txt";
    private static final PersonData instance = new PersonData();
    Map<Integer, List<Integer>> personRelationMap = new HashMap<>();
    Map<Integer, String> personMap = new HashMap<>();

    private PersonData() {
        generatePersonMap();
        generateRelationMap();
    }

    public static PersonData getInstance() {
        return instance;
    }

    Map<Integer, List<Integer>> getPersonRelationMap() {
        return personRelationMap;
    }

    Map<Integer, String> getPersonMap() {
        return personMap;
    }

    @Override
    public List<Integer> getPersonIdList() {
        List<Integer> personIds = new ArrayList<>();
        personIds.addAll(getPersonMap().keySet());
        return personIds;
    }

    @Override
    public Person getPerson(int id) {

        if (personMap == null)
            return null;

        return new Person(id, personMap.get(id));

    }

    @Override
    public List<Integer> getPersonRelations(int id) {
        return getPersonRelationMap().get(id);
    }

    @Override
    public List<Person> convertIdsToPerson(List<Integer> personIds) {

        List<Person> persons = new ArrayList<>();

        for (Integer id : personIds)
            persons.add(getPerson(id));


        return persons;
    }

    /**
     * Load Person file inot memory
     */
    void generatePersonMap() {
        BufferedReader reader;
        String[] wordsArray;
        ArrayList<String> words = new ArrayList<>();

        try {

            reader = new BufferedReader(new FileReader(PERSON_FILE));
            String line = "";
            while (line != null) {
                line = reader.readLine();

                if (line == null)
                    break;

                wordsArray = line.split("\t");

                int id = Integer.valueOf(wordsArray[0]);
                String firstname = wordsArray[1];

                personMap.put(id, firstname);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Load Relationship file into memory
     */
    void generateRelationMap() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(RELATIONSHIP_FILE));

            String line = "";
            while (line != null) {
                String[] wordsArray;
                ArrayList<Integer> words = new ArrayList<>();
                line = reader.readLine();

                if (line == null)
                    break;

                wordsArray = line.split(": ");

                int personId = Integer.valueOf(wordsArray[0].trim());

                wordsArray = wordsArray[1].split(", ");

                for (String each : wordsArray)
                    if (!"".equals(each))
                        words.add(Integer.valueOf(each));

                getPersonRelationMap().put(personId, words);

            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
