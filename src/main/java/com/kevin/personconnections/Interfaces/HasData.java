package com.kevin.personconnections.Interfaces;

import com.kevin.personconnections.Model.Person;

import java.util.List;

public interface HasData {

    Person getPerson(int id);

    List<Integer> getPersonRelations(int id);

    List<Person> convertIdsToPerson(List<Integer> personIds);

    List<Integer> getPersonIdList();
}
