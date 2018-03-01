package part3.implement.transaction;

import part3.implement.database.PayrollDatabase;
import part3.implement.entity.Employee;

/**
 * Created by ZD on 2017/10/24.
 */
public abstract class ChangeEmployeeTransaction implements Transaction {

    private int empID;

    public ChangeEmployeeTransaction(int empID) {
        this.empID = empID;
    }

    public ChangeEmployeeTransaction(){}

    public void execute() {
        Employee e = PayrollDatabase.getPayrollDatabase().getEmployee(empID);
        if (e != null){
            change(e);
        }
    }

    public abstract void change(Employee e);
}
