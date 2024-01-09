import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

class Employee {
    private String name;
    private int experience;
    private BigDecimal basePayment;

    public Employee(String name, int experience, BigDecimal basePayment) {
        this.name = name;
        this.experience = experience;
        this.basePayment = basePayment;
    }
    public Employee() {};

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public BigDecimal getPayment() {
        return basePayment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, experience, basePayment);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return experience == employee.experience &&
                name.equals(employee.name) &&
                basePayment.equals(employee.basePayment);
    }



    // Code
}
class Manager extends Employee {
    private double coefficient;

    public Manager(String name, int experience, BigDecimal basePayment, double coefficient) {
        super(name, experience, basePayment);
        this.coefficient = coefficient;
    }

    public Manager(double coefficient) {
        this.coefficient = coefficient;
    }

    public Manager() {};

    @java.lang.Override
    public BigDecimal getPayment() {
        return super.getPayment().multiply(new BigDecimal(coefficient));
    }

    public double getCoefficient() {
        return coefficient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), coefficient);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Manager manager = (Manager) obj;
        return Double.compare(manager.coefficient, coefficient) == 0;
    }


    // Code
}
public class MyUtils {
    public List<Employee> largestEmployees(List<Employee> workers) {
        List<Employee> result = new ArrayList<>();
        List<Employee> managerList = new ArrayList<>();
        List<Employee> employeeList = new ArrayList<>();
        for(Employee worker : workers) {
            if(worker instanceof Manager) {
                managerList.add(worker);
            } else if(worker instanceof Employee) {
                employeeList.add(worker);
            }
        }

        int maxEmployeeExperience = employeeList.stream()
                .mapToInt(employee -> employee.getExperience())
                .max()
                .orElse(0);
        result.addAll(employeeList.stream()
                .filter(employee -> employee.getExperience() == maxEmployeeExperience)
                .collect(Collectors.toList()));
        BigDecimal maxEmployeePayment = employeeList.stream()
                .map(Employee::getPayment)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
        result.addAll(employeeList.stream()
                .filter(employee -> employee.getPayment().equals(maxEmployeePayment))
                .collect(Collectors.toList()));
        int maxManagerExperience = managerList.stream()
                .mapToInt(manager -> manager.getExperience())
                .max()
                .orElse(0);
        result.addAll(managerList.stream()
                .filter(manager -> ((Manager)manager).getExperience() == maxManagerExperience)
                .collect(Collectors.toList()));
        BigDecimal maxManagerPayment = managerList.stream()
                .map(Employee::getPayment)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        result.addAll(managerList.stream()
                .filter(manager -> (((Manager)manager).getPayment()).equals(maxManagerPayment))
                .collect(Collectors.toList()));
        result = result.stream()
                .distinct()
                .collect(Collectors.toList());

        return result;

    }
}
