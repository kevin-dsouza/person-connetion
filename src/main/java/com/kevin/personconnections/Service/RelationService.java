package com.kevin.personconnections.Service;

import com.kevin.personconnections.DataStore.PersonData;

import java.util.*;

public class RelationService {

    public RelationService() {
    }

    public List<Integer> findCommonRelations(int firstPersonId, int secondPersonId) {


        HashSet<Integer> firstPersonRelation = new HashSet<>(PersonData.getInstance().getPersonRelations(firstPersonId));
        HashSet<Integer> secondPersonRelation = new HashSet<>(PersonData.getInstance().getPersonRelations(secondPersonId));
        firstPersonRelation.retainAll(secondPersonRelation);

        return new ArrayList<>(firstPersonRelation);
    }


    public int findNthDegreeConnectionTotal(int id, int degree) {
        return findNthDegreeConnections(id, degree).size();
    }


    public List<Integer> findNthDegreeConnections(int id, int degree) {

        Set<Integer> mySet = new HashSet<>(findNthDegreeConnections(id, degree, new ArrayList<>()));

        //Get back the array without duplicates
        List<Integer> finalList = new ArrayList<>();
        finalList.addAll(mySet);

        System.out.println("*** Final connections for id: " + id + " with degree: " + degree + " ***");
        for (Integer i : finalList)
            System.out.println(i);


        return finalList;
    }

    public List<Integer> findNthDegreeConnections(int id, int degree, List<Integer> visitedList) {
        LinkedList<Integer> queue = new LinkedList<>();
        List<Integer> connections = new ArrayList<>();


//        System.out.println("--- id: "+id+", degree: "+degree);
//        System.out.println("--- Adding "+id+" to visited--- ");
        visitedList.add(id);

        List<Integer> firstDegreeConnections = PersonData.getInstance().getPersonRelations(id);

        if (firstDegreeConnections == null)
            return new ArrayList<Integer>();

//        System.out.println("--- "+id+" has following connections --- ");
//        for(Integer i: firstDegreeConnections)
//            System.out.println(i);
//
//        System.out.println();


        if (degree - 1 == 0)
            return firstDegreeConnections;

        queue.addAll(firstDegreeConnections);

        while (!queue.isEmpty()) {
            Integer queueId = queue.pop();
            connections.addAll(findNthDegreeConnections(queueId, degree - 1, visitedList));
        }

        connections.removeAll(visitedList);
        return connections;
    }


    public List<Integer> findPersonWithMostLeastConnections(int degree, boolean needMax) {

        List<Integer> idList = new ArrayList<>();
        int connections = needMax ? 0 : 9999999;
        int total;

        for (Integer id : PersonData.getInstance().getPersonIdList()) {

            total = findNthDegreeConnectionTotal(id, degree);

            System.out.println("-- id: " + id + ", total: " + total + ", connections: " + connections);

            if ((needMax && total > connections) || (!needMax && total < connections)) {
                idList = new ArrayList<>();
                idList.add(id);
                connections = total;
            } else if (total == connections) {
                idList.add(id);
            }
        }

        return idList;
    }
}
