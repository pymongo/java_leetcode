package com.leetcode;

import org.junit.jupiter.api.Test;
import java.util.ArrayDeque;
import java.util.List;

public class Easy {
    static class Employee {
        public int id;
        public int importance;
        public List<Integer> subordinates;
    }

    // https://leetcode.com/problems/employee-importance/
    /*
    ```
    // bad code, segfault on test_case: [[1,2,[2]], [2,3,[]]]
    vector<Employee> map;
    map.reserve(2000);
    for (Employee *employee : employees) {
        map[employee->id] = *employee;
    }

    // consider change map.reverse(2000) to `Employee map[2000]` or `vector<Employee> map(10)` or `array<Employee, 2000> map`
    ```
    */
    public static int employee_importance(final List<Employee> employees, final int id) {
        var map = new Employee[2000];
        for (final var each : employees) {
            map[each.id] = each;
        }
        int total_importance = 0;
        var queue = new ArrayDeque<Integer>();
        queue.push(id);
        while (!queue.isEmpty()) {
            final var employee_id = queue.removeFirst();
            final var employee = map[employee_id];
            total_importance += employee.importance;
            for (var subordinate_id : employee.subordinates) {
                queue.addLast(subordinate_id);
            }
        }
        return total_importance;
    }
}
