<%--
  Created by IntelliJ IDEA.
  User: yoga.wiguna
  Date: 19/07/2018
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.sql.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>PARTS LIST MSG</title>
  <link href="http://localhost:8080/webjars/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" />
  <script src="http://localhost:8080/webjars/bootstrap/4.0.0/js/bootstrap.min.js" ></script>
  <script src="http://localhost:8080/webjars/jquery/3.0.0/js/jquery.min.js" ></script>
</head>
<body>
<%ResultSet resultset = null;%>
<%Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/msg_parts?user=root&password=");%>
<%Statement statement = connection.createStatement();%>

<div class="container">
  <spring:url value="/msg/new-parts" var="tambahparts" />
  <h2>PARTS LIST</h2>
  <form:form modelAttribute="partrorm" method="post" action="${tambahparts }" cssClass="form">
    <form:hidden path="id_unit_parts"/>

    <div class="form-group">
      <lable for="part_name">Id Part Number</lable>
      <form:input path="id_part_number" cssClass="form-control" id="id_part_number" />
    </div>

    <div class="form-group select-validation">
      <label class="control-label">Unit Kategori</label>

          <%
            try{

              resultset =statement.executeQuery("SELECT unit_parts_name from mtr_stock_unit_parts") ;
          %>
            <select class="form-control select2me no-first-option" name="unit_type">
             <option value="">Pilih...
              <%  while(resultset.next()){ %>
              <option><%= resultset.getString(1)%></option>
                <% } %>
             </option>
            </select>
          <%
            }  catch(Exception e) {
              out.println("wrong entry"+e);
            }
          %>
        <i class="error-icon fa fa-warning tooltips" data-container="body" data-placement="top" data-original-title="Tipe Unit belum dipilih" style="margin-right: 13px"></i>
    </div>

    <div class="form-group">
      <lable for="brand_name">Nama Part</lable>
      <form:input path="part_name" cssClass="form-control" id="part_name" />
    </div>

    <div class="form-group select-validation">
      <label class="control-label">Nama Brand</label>
      <%
        try{
          resultset = statement.executeQuery("SELECT brand_name FROM mtr_brand") ;
      %>
      <select class="form-control select2me no-first-option" name="nama_brand">
        <option value="">Pilih...
              <%  while(resultset.next()){ %>
        <option><%= resultset.getString(1)%></option>
        <% } %>
        </option>
      </select>
      <%
        }  catch(Exception e) {
          out.println("wrong entry"+e);
        }
      %>
      <i class="error-icon fa fa-warning tooltips" data-container="body" data-placement="top" data-original-title="Tipe Unit belum dipilih" style="margin-right: 13px"></i>
    </div>

    <div class="form-group">
      <lable for="specification">Spesifikasi</lable>
      <form:input path="specification" cssClass="form-text" id="specification" />
    </div>

    <div class="form-group">
      <lable for="bad_part">Bad Part</lable>
      <select class="form-control select2me no-first-option" name="bad_part">
        <option value="">Pilih...</option>
      <option value="NO">NO</option>
      <option value="YES">YES</option>
      </select>
    </div>


    <div class="form-group">
      <lable for="created_by">Created By</lable>
      <form:input path="created_by" cssClass="form-text" id="created_by" />
    </div>

    <button type="submit" class="btn btn-primary">Save</button>
  </form:form>
</div>
</body>
</html>