package part3.salary;

import part3.implement.payday.PayCheck;
import part3.implement.transaction.AddHourlyEmployee;
import part3.implement.transaction.AddSalariedEmployee;
import part3.implement.transaction.TimeCardTransaction;
import part3.implement.transaction.PayDayTransaction;
import part3.implement.util.DateUtil;
import org.junit.Test;


import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * 支付雇员薪水测试
 * Created by ZD on 2017/10/26.
 */
public class PayrollTest {

    /**
     * 支付正常员工薪资
     */
    @Test
    public void testPaySalariedEmployee(){
        int empId = 1;
        String name = "Bob";
        String address = "Bob.home";
        double monthlyPay = 6000;

        AddSalariedEmployee addSalariedEmployee = new AddSalariedEmployee(empId,name,address,monthlyPay);
        addSalariedEmployee.execute();

        Date payDate = DateUtil.getDateFormat("2017-11-30");
        PayDayTransaction payDayTransaction = new PayDayTransaction(payDate);
        payDayTransaction.execute();

        PayCheck payCheck = payDayTransaction.getPayCheck(empId);
        assertEquals(payDate, payCheck.getPayDate());
    }


    @Test
    public void testPaySalariedEmployeeOnWrongDate(){
        int empId = 1;
        String name = "Bob";
        String address = "Bob.home";
        double monthlyPay = 6000;

        AddSalariedEmployee addSalariedEmployee = new AddSalariedEmployee(empId,name,address,monthlyPay);
        addSalariedEmployee.execute();

        Date payDate = DateUtil.getDateFormat("2017-11-26");

        PayDayTransaction payDayTransaction = new PayDayTransaction(payDate);
        payDayTransaction.execute();

        PayCheck payCheck = payDayTransaction.getPayCheck(empId);
        assertEquals(null,payCheck);

    }

    /**
     * 支付钟点工薪水
     */
    @Test
    public void testPayHourlyEmployee(){
        int empId = 1;
        String name = "Bob";
        String address = "Bob.home";
        double hourlyPay = 25;

        AddHourlyEmployee addHourlyEmployee = new AddHourlyEmployee(empId,name,address,hourlyPay);
        addHourlyEmployee.execute();

        Date payDate = DateUtil.getDateFormat("2017-11-24");

        PayDayTransaction payDayTransaction = new PayDayTransaction(payDate);
        payDayTransaction.execute();

        validatePayCheck(payDayTransaction,empId,payDate,0.0);

    }

    private void validatePayCheck(PayDayTransaction payDayTransaction, int empId, Date payDate, double v) {
        PayCheck payCheck = payDayTransaction.getPayCheck(empId);
        assertEquals(payCheck.getPayDate(),payDate);
    }

    @Test
    public void testPayHourlyEmployeeOnWrongDate(){
        int empId = 1;
        String name = "Bob";
        String address = "Bob.home";
        double hourlyPay = 25;

        AddHourlyEmployee addHourlyEmployee = new AddHourlyEmployee(empId,name,address,hourlyPay);
        addHourlyEmployee.execute();

        Date payDate = DateUtil.getDateFormat("2017-11-28");

        PayDayTransaction payDayTransaction = new PayDayTransaction(payDate);
        payDayTransaction.execute();

        PayCheck payCheck = payDayTransaction.getPayCheck(empId);
        assertNotEquals(null,payDate);
    }


    /**
     * 支付具有单一时间卡的雇员薪水
     */
    @Test
    public void testPaySingleHourlyEmployee() throws Exception {
        int empId = 1;
        String name = "Bob";
        String address = "Bob.home";
        double hourlyPay = 25;

        AddHourlyEmployee addHourlyEmployee = new AddHourlyEmployee(empId,name,address,hourlyPay);
        addHourlyEmployee.execute();

        Date payDate = new Date(2017,10,28);

        TimeCardTransaction timeCardTransaction = new TimeCardTransaction(payDate,2.0,empId);
        timeCardTransaction.execute();

        PayDayTransaction payDayTransaction = new PayDayTransaction(payDate);
        payDayTransaction.execute();
    }

    @Test
    public void testPayCommissionEmployee(){}

    @Test
    public void testPayCommissionEmployeeOnWrongDate(){}

}
