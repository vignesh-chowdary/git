package controller;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class Sample extends HttpServlet
{
	public void doget(HttpServletRequest request,HttpServletResponse response)
	{
		int eid=Integer.parseInt(request.getParameter("eid"));
		String month=request.getParameter("month"); 
		String year=request.getParameter("year"); 

        out.println("""
            <html>
            <head>
                <title>Employee Payslip</title>
                <style>
                    body {font-family: 'Segoe UI', Arial, sans-serif; background-color: #f4f7fc; margin: 0; padding: 0;}
                    .container {width: 900px; margin: 40px auto; background: #fff; padding: 30px 50px;
                                box-shadow: 0 5px 20px rgba(0,0,0,0.1); border-radius: 12px;}
                    h1, h2 {text-align: center; color: #2c3e50;}
                    .employee-details {margin-bottom: 20px; font-size: 16px; color: #333;}
                    .employee-details p {margin: 6px 0;}
                    .tables {display: flex; justify-content: space-between; margin-top: 25px;}
                    table {width: 48%; border-collapse: collapse;}
                    th, td {border: 1px solid #ddd; padding: 8px; text-align: left; font-size: 15px;}
                    th {background-color: #3498db; color: white;}
                    tr:nth-child(even) {background-color: #f9f9f9;}
                    .total {font-weight: bold; color: #2c3e50;}
                    .actions {text-align: center; margin-top: 20px;}
                    .btn {display: inline-block; background: #3498db; color: white; padding: 10px 18px;
                          text-decoration: none; border-radius: 6px; margin: 10px;}
                    .btn:hover {background: #2980b9;}
                    form {text-align: center; margin: 20px;}
                    select {padding: 5px; margin: 0 10px;}
                </style>
                <script>function printPayslip(){window.print();}</script>
            </head>
            <body>
            <div class='container'>
                <h1>Employee Payslip</h1>
        """);
        try
        {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vignesh","root","root");
        	String query= """
                    SELECT e.eid, e.name, e.designation, e.dateofjoining, e.address,
                    p.basicpay, p.houserentallowances, p.specialallowances,
                    p.transport, p.pf, p.tax
             FROM employee e
             JOIN payroll p ON e.eid = p.eid
             WHERE e.eid = ?
             """;
        	PreparedStatement ps=con.prepareStatement(query);
        	ps.setInt(1, eid);
        	ResultSet rs=ps.executeQuery();
        	if(rs.next())
        	{
                double basic = rs.getDouble("basicpay");
                double hra = rs.getDouble("houserentallowances");
                double special = rs.getDouble("specialallowances");
                double transport = rs.getDouble("transport");
                double pf = rs.getDouble("pf");
                double tax = rs.getDouble("tax");

                double gross = basic + hra + special + transport;
                double deductions = pf + tax;
                double net = gross - deductions;

        	}
        	
        	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }

		
	}
}
