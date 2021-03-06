package com.gcit.libsystem.dao;

import org.springframework.jdbc.core.*;
import java.sql.*;
import java.util.*;

import com.gcit.libsystem.entity.Publisher;

public class PublisherDao extends BaseDao implements ResultSetExtractor<List<Publisher>> {
	
	public void addPublisher(Publisher publisher) throws SQLException {
		String  addPublisher  = "INSERT INTO tbl_publisher "
				+ "(publisherName, publisherAddress, publisherPhone) VALUE (?,?,?)";
		Object[] publisherInfo = {publisher.getPublisherName(),
				publisher.getPublisherAddress(), publisher.getPublisherPhone()};
		template.update(addPublisher, publisherInfo);
	}
	
	public void updatePublisherAddress(Publisher publisher) throws SQLException {
		String   updatePublisher = "UPDATE tbl_publisher SET publisherAddress=? WHERE publisherId=?";
		Object[] publisherInfo   = {publisher.getPublisherAddress(),publisher.getPublisherId()};
		template.update(updatePublisher, publisherInfo);
	}
	
	public void updatePublisherName(Publisher publisher) throws SQLException{
		String   updatePublisher = "UPDATE tbl_publisher SET publisherName=? WHERE publisherId=?";
		Object[] publisherInfo   = {publisher.getPublisherName(),publisher.getPublisherId()};
		template.update(updatePublisher, publisherInfo);
	}
	
	public void updatePublisherPhone(Publisher publisher) throws SQLException{
		String   updatePublisher = "UPDATE tbl_publisher SET publisherPhone=? WHERE publisherId=?";
		Object[] publisherInfo   = {publisher.getPublisherPhone(),publisher.getPublisherId()};
		template.update(updatePublisher, publisherInfo);
	}
	
	public void deletePublisher(Publisher publisher) throws SQLException{
		String   deletePublisher = "DELETE FROM tbl_publisher WHERE publisherId=?";
		Object[] publisherInfo   = {publisher.getPublisherId()};
		template.update(deletePublisher, publisherInfo);
	}
	
	public List<Publisher> readAllPublisher() throws SQLException{
		String readPublisher = "SELECT * FROM tbl_publisher";
		return template.query(readPublisher, this);
	}
	
	public Publisher readPublisher(Integer publisherID) throws SQLException{
		String  readPublisher     = "SELECT * FROM tbl_publisher WHERE publisherId=?";
		Object[] publisherInfo    = {publisherID};
		List<Publisher> publisher = template.query(readPublisher, publisherInfo, this);
		if(publisher!=null && !publisher.isEmpty()){
			return publisher.get(0);
		}
		return null;
	}
	
/*	public List<Publisher> readPublisher(String publisherName) throws SQLException{
		String   readPublisher = "SELECT * FROM tbl_publisher WHERE publisherName LIKE ?";
		Object[] publisherInfo = {"%" + publisherName + "%"};
		return template.query(readPublisher, publisherInfo, this);
	}*/
	
	public Publisher readPublisherByBook(Integer bookID) throws SQLException{
		final String readPublisher = "SELECT * FROM tbl_publisher WHERE publisherId IN (SELECT pubId FROM tbl_book WHERE bookId=?)";
		Object[]     bookInfo = {(bookID)};
		List<Publisher> publishers = template.query(readPublisher, bookInfo, this);
		if(publishers!=null && !publishers.isEmpty()){
			return publishers.get(0);
		}
		return null;

	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while(rs.next()){
			Publisher publisher = new Publisher();
			publisher.setPublisherId(rs.getInt("publisherId"));
			publisher.setPublisherName(rs.getString("publisherName"));
			publisher.setPublisherAddress(rs.getString("publisherAddress"));
			publisher.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(publisher);
		}
		return publishers;
	}
}

//
//@Override
//public List<Publisher> extractData(ResultSet rs) throws SQLException {
//	BookDao bookDao = new BookDao(conn);
//	List<Publisher> publishers = new ArrayList<>();
//	String readBook = "SELECT * FROM tbl_book JOIN tbl_publisher ON pubId=publisherId WHERE publisherId=?";
//	while(rs.next()){
//		Publisher publisher = new Publisher();
//		List<?> publisherInfo = Arrays.asList(publisher.getPublisherId());
//		publisher.setPublisherId(rs.getInt("publisherId"));
//		publisher.setPublisherName(rs.getString("publisherName"));
//		publisher.setPublisherAddress(rs.getString("publisherAddress"));
//		publisher.setPublisherPhone(rs.getString("publisherPhone"));
//		publisher.setBooks(bookDao.readOnly(readBook,publisherInfo));
//		publishers.add(publisher);
//	}
//	return publishers;
//}
//@Override
//protected <T> T extractSingleData(ResultSet rs) throws SQLException {
//	return null;
//}
