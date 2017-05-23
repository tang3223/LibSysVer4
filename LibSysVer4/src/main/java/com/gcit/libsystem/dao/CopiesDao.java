package com.gcit.libsystem.dao;

import org.springframework.jdbc.core.*;
import java.sql.*;

public class CopiesDao extends BaseDao implements ResultSetExtractor<Integer> {
	
	public Integer readCopies(Integer bookID , Integer branchID) throws SQLException{
		String readBranch  = "SELECT * FROM tbl_book_copies WHERE branchId=? AND bookId=?";
		Object[] branchInfo = {branchID, bookID};
		Integer copies = template.query(readBranch, branchInfo, this);
		return copies;
	}
	
	@Override
	public Integer extractData(ResultSet rs) throws SQLException {
		Integer copies = 0;
		while(rs.next()){
			copies = rs.getInt("noOfCopies");
		}
		return copies;
	}

}

//@Override
//public List<Branch> extractData(ResultSet rs) throws SQLException {
//	List<Branch> branchs = new ArrayList<>();
//	String readBook = "SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_copies WHERE branchId=?)";
//	String readNoOfCopies = "SELECT * FROM tbl_book_copies WHERE bookId=? AND branchId=?";
//	while(rs.next()){
//		Branch branch = new Branch();		
//		branch.setBranchID(rs.getInt("branchId"));
//		branch.setBranchName(rs.getString("branchName"));
//		branch.setBranchAddress(rs.getString("branchAddress"));
//		List<?> branchInfo = Arrays.asList(branch.getBranchID());
//		branch.setBooks(bookDao.readOnly(readBook,branchInfo));
//		for (Book book : branch.getBooks()){
//			List<?> copyInfo = Arrays.asList(book.getBookId(),branch.getBranchID());
//			Integer noOfCopies = branchDao.readSingleOnly(readNoOfCopies, copyInfo);
//			branch.setnoOfCopies(book.getBookId(), noOfCopies);
//		}
//		branchs.add(branch);
//	}
//	return branchs;
//}
//@Override
//protected Integer extractSingleData(ResultSet rs) throws SQLException {
//	while(rs.next()) {
//		return rs.getInt("noOfCopies");
//	}
//	return null;
//}

