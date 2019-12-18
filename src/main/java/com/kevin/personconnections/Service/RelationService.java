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

        if(findNthDegreeConnections(id, degree) == null || findNthDegreeConnections(id, degree).isEmpty())
            return 0;

        return findNthDegreeConnections(id, degree).size();
    }


    public List<Integer> findNthDegreeConnections(int id, int degree) {

        List<Integer> finalList = findNthDegreeConnectionsV2(id, degree);

        System.out.println("*** Final connections for id: " + id + " with degree: " + degree + " ***");
        for (Integer i : finalList)
            System.out.println(i);


        return finalList;
    }

    public List<Integer> findNthDegreeConnectionsV2(int id, int degree) {

        System.out.println("Id: "+id+", degree:"+degree);

        Set<Integer> visited = new HashSet<>();
        HashMap<Integer, List<Integer>> idMap = new HashMap<>();

        List<Integer> idMapPutList = new ArrayList<>();
        idMapPutList.add(id);


        idMap.put(0, idMapPutList);
        visited.add(id);

        for(int i=0; i<=degree; i++) {
            List<Integer> idMapGetList = idMap.get(i);

             idMapPutList = new ArrayList<>();

            System.out.println("idMapGetList.size: "+idMapGetList.size());

            for(Integer idMapGet: idMapGetList) {
                System.out.println("idMapGet: "+idMapGet+", degree: "+i);
                List<Integer> tempList = new ArrayList<>();

                for(Integer temp: PersonData.getInstance().getPersonRelations(idMapGet)) {
                    if (!visited.contains(temp)){
                        System.out.println("Visiting "+temp);
                        visited.add(temp);
                        tempList.add(temp);
                    }
                }

                if(!tempList.isEmpty())
                    idMapPutList.addAll(tempList);
            }

            System.out.println("Adding following to map at index "+ (i+1));
            for(int x : idMapPutList)
                System.out.println(x);

            idMap.put(i+1,idMapPutList);
        }



        return idMap.get(degree);

    }

    @Deprecated
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

    public List<Integer> findCommonConnections(int idA, int idB, int degree) {
        List<Integer> idAList = new ArrayList<>();
        List<Integer> idBList = new ArrayList<>();

        idAList = findNthDegreeConnections(idA, degree);
        idBList = findNthDegreeConnections(idB, degree);
        idAList.retainAll(idBList);
        return idAList;
    }
}
