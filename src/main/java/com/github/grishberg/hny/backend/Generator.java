package com.github.grishberg.hny.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Generator {
    private final ArrayList<Employee> users = new ArrayList<>();
    private final Map<Employee, String> giftMap;

    public Generator() {
        this(new HashMap<>());
    }

    public Generator(Map<Employee, String> giftMap) {
        this.giftMap = giftMap;
    }

    public void addUser(Employee u) {
        users.add(u);
    }

    public String giftForUser(Employee u) {
        return giftMap.get(u);
    }

    public final void randomize() {
        for (int i = 0; i < 10; i++) {
            try {
                doShuffle();
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new IllegalStateException("Cant shuffle");
    }

    private void doShuffle() {
        giftMap.clear();

        for (Employee currentUser : users) {
            System.out.println(currentUser);
            List<Employee> candidates = getCandidatesForUser(currentUser);
            printList(candidates);
            int next = rand(candidates.size() - 1);
            Employee candidate = candidates.get(next);
            System.out.println("  candidate: " + candidate);
            giftMap.put(candidate, currentUser.getGiftNumber());
        }
    }

    private static void printList(List<Employee> users) {
        System.out.println("  candidates:");
        for (Employee user : users) {
            System.out.println("    item=" + user);
        }
    }

    private int rand(int max) {
        return (int) (Math.random() * ((max) + 1));
    }

    private List<Employee> getCandidatesForUser(Employee currentUser) {
        ArrayList<Employee> result = new ArrayList<>();

        for (Employee candidate : users) {
            if (shouldIgnoreUser(currentUser, candidate)) {
                continue;
            }
            result.add(candidate);
        }
        return result;
    }

    private boolean shouldIgnoreUser(Employee currentUser, Employee candidate) {
        if (currentUser == candidate) {
            return true;
        }
        if (currentUser.getGroup() != Group.EMPTY && candidate.getGroup() == currentUser.getGroup()) {
            return true;
        }
        if (giftMap.containsKey(candidate)) {
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        // TODO: Implement this method
        StringBuilder sb = new StringBuilder("{ GiftMsp:\n");
        for (Map.Entry<Employee, String> entry : giftMap.entrySet()) {
            Employee u = entry.getKey();
            String gift = entry.getValue();
            sb.append("User " + u.getName() + " takes " + gift);
            sb.append(",\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
